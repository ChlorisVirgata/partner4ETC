package com.allinpay.service.impl;

import com.allinpay.controller.query.OrgModifyQuery;
import com.allinpay.core.common.PageVO;
import com.allinpay.core.constant.CommonConstant;
import com.allinpay.core.constant.enums.BizEnums;
import com.allinpay.core.exception.AllinpayException;
import com.allinpay.core.util.FileUtil;
import com.allinpay.core.util.PageVOUtil;
import com.allinpay.entity.PartnerAudit;
import com.allinpay.entity.PartnerInfo;
import com.allinpay.entity.PartnerStorage;
import com.allinpay.mapper.PartnerAuditMapper;
import com.allinpay.mapper.PartnerInfoMapper;
import com.allinpay.mapper.PartnerStorageMapper;
import com.allinpay.service.IOrgModifyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@PropertySource("classpath:resource.properties")
public class OrgModifyServiceImpl implements IOrgModifyService {
    @Autowired
    private PartnerAuditMapper auditMapper;
    @Autowired
    private PartnerStorageMapper storageMapper;
    @Autowired
    private PartnerInfoMapper infoMapper;
    @Value("${orgDir}")
    private String orgDir;
    @Value("${tempDir}")
    private String tempDir;

    @Override
    public PageVO<PartnerAudit> selectByCondition(OrgModifyQuery query) {
        log.info("查询机构信息变更列表入参：{}", query);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<PartnerAudit> partnerAuditList = auditMapper.selectModifyList(query);
        PageVO<PartnerAudit> pageVO = PageVOUtil.convert(new PageInfo<>(partnerAuditList));
        return pageVO;
    }

    @Override
    public void editOrg(MultipartHttpServletRequest request, PartnerStorage storage) {
        try {
            String partnerId = storage.getPartnerId();
            String sysUser = "";
            //非 未提交、审核成功、审核失败三种状态返回失败
            if (!(CommonConstant.STATUS_TEMP.equals(storage.getStatus())
                    || CommonConstant.STATUS_SUCCESS.equals(storage.getStatus())
                    || CommonConstant.STATUS_FAIL.equals(storage.getStatus()))) {
                log.error("机构状态有误，机构已在审核中");
                throw new AllinpayException(BizEnums.ORG_MODIFY_AUDITED.getCode(), BizEnums.ORG_MODIFY_AUDITED.getMsg());
            }

            storage.setIdFront(FileUtil.getFileName(request.getFile(CommonConstant.FRONT_FILE),
                    tempDir + partnerId + CommonConstant.SUB_DIR_FRONT));
            storage.setAgreement(FileUtil.getFileName(request.getFile(CommonConstant.AGREEMENT_FILE),
                    tempDir + partnerId + CommonConstant.SUB_DIR_AGREEMENT));
            storage.setLicense(FileUtil.getFileName(request.getFile(CommonConstant.LICENSE_FILE),
                    tempDir + partnerId + CommonConstant.SUB_DIR_LICENSE));
            storage.setIdBack(FileUtil.getFileName(request.getFile(CommonConstant.BACK_FILE),
                    tempDir + partnerId + CommonConstant.SUB_DIR_BACK));
            log.info("机构信息编辑图片上传成功");
            storage.setSysUser(sysUser);
            if (CommonConstant.STATUS_TEMP.equals(storage.getStatus())) {
                //新增未提交审核 更新临时表记录
                storageMapper.update(storage);
                log.info("机构信息编辑更新成功");
            } else {
                //已审核记录（成功、失败） 临时表生成一条新数据
                PartnerAudit audit = auditMapper.selectOne(partnerId, null);
                if (StringUtils.isBlank(storage.getLicense())) {
                    storage.setLicense(audit.getLicense());
                }
                if (StringUtils.isBlank(storage.getIdFront())) {
                    storage.setIdFront(audit.getIdFront());
                }
                if (StringUtils.isBlank(storage.getIdBack())) {
                    storage.setIdBack(audit.getIdBack());
                }
                if (StringUtils.isBlank(storage.getAgreement())) {
                    storage.setAgreement(audit.getAgreement());
                }
                storage.setCreateTime(new Date());
                storage.setStatus(CommonConstant.STATUS_TEMP);
                storageMapper.insert(storage);

                // 删除审核表中的记录
                auditMapper.delete(partnerId);
                log.info("删除已有审核记录，新增一条机构信息成功");
            }
        } catch (AllinpayException all) {
            throw all;
        } catch (Exception e) {
            log.error("机构信息变更失败", e);
            throw new AllinpayException(BizEnums.ORG_MODIFY_FAIL.getCode(), BizEnums.ORG_MODIFY_FAIL.getMsg());
        }
    }

    @Override
    public void sendOrgAudit(MultipartHttpServletRequest request, PartnerAudit audit) {
        try {
            String partnerId = audit.getPartnerId();
            String sysUser = "";
            //非 未提交、审核成功、审核失败三种状态返回失败
            if (!(CommonConstant.STATUS_TEMP.equals(audit.getStatus())
                    || CommonConstant.STATUS_SUCCESS.equals(audit.getStatus())
                    || CommonConstant.STATUS_FAIL.equals(audit.getStatus()))) {
                throw new AllinpayException(BizEnums.ORG_MODIFY_AUDITED.getCode(), BizEnums.ORG_MODIFY_AUDITED.getMsg());
            }

            audit.setIdFront(FileUtil.getFileName(request.getFile(CommonConstant.FRONT_FILE),
                    tempDir + partnerId + CommonConstant.SUB_DIR_FRONT));
            audit.setAgreement(FileUtil.getFileName(request.getFile(CommonConstant.AGREEMENT_FILE),
                    tempDir + partnerId + CommonConstant.SUB_DIR_AGREEMENT));
            audit.setLicense(FileUtil.getFileName(request.getFile(CommonConstant.LICENSE_FILE),
                    tempDir + partnerId + CommonConstant.SUB_DIR_LICENSE));
            audit.setIdBack(FileUtil.getFileName(request.getFile(CommonConstant.BACK_FILE),
                    tempDir + partnerId + CommonConstant.SUB_DIR_BACK));
            log.info("机构信息提交审核图片上传成功");
            audit.setSysUser(sysUser);
            if (CommonConstant.STATUS_TEMP.equals(audit.getStatus())) {
                audit.setStatus(CommonConstant.STATUS_AUDIT);
                //新增一条审核中记录
                PartnerStorage storage = storageMapper.selectOne(partnerId);
                if (StringUtils.isBlank(audit.getIdFront())) {
                    audit.setIdFront(storage.getIdFront());
                }
                if (StringUtils.isBlank(audit.getIdBack())) {
                    audit.setIdBack(storage.getIdBack());
                }
                if (StringUtils.isBlank(audit.getAgreement())) {
                    audit.setAgreement(storage.getAgreement());
                }
                if (StringUtils.isBlank(audit.getLicense())) {
                    audit.setLicense(storage.getLicense());
                }
                audit.setCreateTime(new Date());
                auditMapper.insert(audit);
                //删除临时表信息
                storageMapper.delete(partnerId);

                //机构表中新增一条审核中记录
                PartnerInfo partnerInfo = new PartnerInfo();
                BeanUtils.copyProperties(audit, partnerInfo);
                FileUtils.copyDirectory(new File(tempDir + partnerId), new File(orgDir + partnerId));
                infoMapper.insert(partnerInfo);
                log.info("删除临时表信息和图片，新增一条审核中记录,新增数据同步到机构表成功");
            } else {
                //更新审核信息，状态置为审核中
                audit.setStatus(CommonConstant.STATUS_AUDIT);
                auditMapper.update(audit);
                log.info("机构信息更新成功");
            }
        } catch (IOException io) {
            throw new AllinpayException(BizEnums.FILE_OPERATE_EXCEPTION.getCode(), BizEnums.FILE_OPERATE_EXCEPTION.getMsg());
        } catch (AllinpayException all) {
            throw all;
        } catch (Exception e) {
            log.error("机构信息提交审核失败", e);
            throw new AllinpayException(BizEnums.ORG_SENDAUDIT_FAIL.getCode(), BizEnums.ORG_SENDAUDIT_FAIL.getMsg());
        }
    }
}
