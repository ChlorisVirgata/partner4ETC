package com.allinpay.service.impl;

import com.allinpay.core.constant.CommonConstant;
import com.allinpay.core.constant.enums.BizEnums;
import com.allinpay.core.exception.AllinpayException;
import com.allinpay.core.util.FileUtil;
import com.allinpay.entity.PartnerAudit;
import com.allinpay.entity.PartnerInfo;
import com.allinpay.entity.PartnerStorage;
import com.allinpay.mapper.PartnerAuditMapper;
import com.allinpay.mapper.PartnerInfoMapper;
import com.allinpay.mapper.PartnerStorageMapper;
import com.allinpay.service.IOrgEnterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.util.Date;

@Service
@Slf4j
@PropertySource("classpath:resource.properties")
public class OrgEnterServiceImpl implements IOrgEnterService {
    @Autowired
    private PartnerStorageMapper storageMapper;
    @Autowired
    private PartnerInfoMapper infoMapper;
    @Autowired
    private PartnerAuditMapper auditMapper;
    @Value("${orgDir}")
    private String orgDir;
    @Value("${tempDir}")
    private String tempDir;

    @Override
    public void addOrg(MultipartHttpServletRequest request, PartnerStorage storage) {
        String partnerId = FileUtil.generatePartnerId();
        String sysUser = "";
        //上传文件，设置路径值
        storage.setLicense(FileUtil.getFileName(request.getFile(CommonConstant.LICENSE_FILE),
                tempDir + partnerId + CommonConstant.SUB_DIR_LICENSE));
        storage.setIdFront(FileUtil.getFileName(request.getFile(CommonConstant.FRONT_FILE),
                tempDir + partnerId + CommonConstant.SUB_DIR_FRONT));
        storage.setIdBack(FileUtil.getFileName(request.getFile(CommonConstant.BACK_FILE),
                tempDir + partnerId + CommonConstant.SUB_DIR_BACK));
        storage.setAgreement(FileUtil.getFileName(request.getFile(CommonConstant.AGREEMENT_FILE),
                tempDir + partnerId + CommonConstant.SUB_DIR_AGREEMENT));
        log.info("机构录入图片上传成功");
        storage.setSysUser(sysUser);
        storage.setCreateTime(new Date());
        storage.setStatus(CommonConstant.STATUS_TEMP);
        storage.setPartnerId(partnerId);
        //保存机构信息到临时表
        storageMapper.insert(storage);
        log.info("保存机构基本信息成功,生成机构号：{}", partnerId);
    }

    @Override
    public void sendOrgAudit(MultipartHttpServletRequest request, PartnerAudit audit) {
        String partnerId = FileUtil.generatePartnerId();
        //信息存入审核表，状态置为审核中
        audit.setLicense(FileUtil.getFileName(request.getFile(CommonConstant.LICENSE_FILE),
                tempDir + partnerId + CommonConstant.SUB_DIR_LICENSE));
        audit.setIdBack(FileUtil.getFileName(request.getFile(CommonConstant.BACK_FILE),
                tempDir + partnerId + CommonConstant.SUB_DIR_BACK));
        audit.setIdFront(FileUtil.getFileName(request.getFile(CommonConstant.FRONT_FILE),
                tempDir + partnerId + CommonConstant.SUB_DIR_FRONT));
        audit.setAgreement(FileUtil.getFileName(request.getFile(CommonConstant.AGREEMENT_FILE),
                tempDir + partnerId + CommonConstant.SUB_DIR_AGREEMENT));
        log.info("机构录入图片上传成功");
        audit.setSysUser("");
        audit.setCreateTime(new Date());
        audit.setStatus(CommonConstant.STATUS_AUDIT);
        audit.setPartnerId(partnerId);
        auditMapper.insert(audit);
        log.info("保存机构基本信息成功{}", partnerId);

        //信息存入机构表，状态置为审核中
        PartnerInfo partnerInfo = new PartnerInfo();
        BeanUtils.copyProperties(audit, partnerInfo);
        try {
            FileUtils.copyDirectory(new File(tempDir + partnerId), new File(orgDir + partnerId));
        } catch (Exception e) {
            log.error("文件拷贝失败", e);
            throw new AllinpayException(BizEnums.FILE_COPY_EXCEPTION.getCode(), BizEnums.FILE_COPY_EXCEPTION.getMsg());
        }
        infoMapper.insert(partnerInfo);
        log.info("新增数据同步到机构表成功{}", partnerId);
    }
}
