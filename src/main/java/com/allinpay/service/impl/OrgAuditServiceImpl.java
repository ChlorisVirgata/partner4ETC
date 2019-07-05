package com.allinpay.service.impl;

import com.allinpay.controller.query.OrgAuditQuery;
import com.allinpay.core.common.PageVO;
import com.allinpay.core.constant.CommonConstant;
import com.allinpay.core.constant.enums.BizEnums;
import com.allinpay.core.exception.AllinpayException;
import com.allinpay.core.util.PageVOUtil;
import com.allinpay.entity.PartnerAudit;
import com.allinpay.entity.PartnerInfo;
import com.allinpay.mapper.PartnerAuditMapper;
import com.allinpay.mapper.PartnerInfoMapper;
import com.allinpay.service.IOrgAuditService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class OrgAuditServiceImpl implements IOrgAuditService {
    @Autowired
    private PartnerAuditMapper auditMapper;
    @Autowired
    private PartnerInfoMapper infoMapper;
    @Value("${orgDir}")
    private String orgDir;
    @Value("${auditDir}")
    private String auditDir;
    @Value("${tempDir}")
    private String tempDir;

    @Override
    public PageVO<PartnerAudit> selectByCondition(OrgAuditQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<PartnerAudit> partnerAuditList = auditMapper.selectList(query);
        PageVO<PartnerAudit> pageVO = PageVOUtil.convert(new PageInfo(partnerAuditList));
        return pageVO;
    }

    @Override
    public PartnerAudit selectByPartnerId(String partnerId) {
        PartnerAudit partnerAudit = auditMapper.selectOne(partnerId);
        if (Objects.isNull(partnerAudit)) {
            log.error("机构编号{}信息不存在！", partnerId);
            throw new AllinpayException("10001", "待审核机构信息不存在");
        }
        return partnerAudit;
    }

    @Override
    public void auditRefuse(String partnerId, String failReason) {
        //先判断该机构是否存在
        PartnerAudit partnerAudit = auditMapper.selectOne(partnerId);
        if (Objects.isNull(partnerAudit)) {
            log.error("机构编号{}信息不存在！", partnerId);
            throw new AllinpayException(BizEnums.ORG_NOT_EXIST.getCode(), BizEnums.ORG_NOT_EXIST.getMsg());
        }
        //todo 操作人
        auditMapper.updateRefuseStatus(partnerId, failReason);
        // todo 根据是否颁发了秘钥判断,新增记录需要更新状态，编辑记录不需要同步状态
        if (Objects.isNull(partnerAudit.getFailReason())) {
            infoMapper.updateRefuseStatus(partnerId);
        }
    }

    @Override
    public void auditApprove(PartnerAudit audit) {
        String partnerId = audit.getPartnerId();
        //先判断该机构是否存在
        PartnerAudit partnerAudit = auditMapper.selectOne(partnerId);
        if (Objects.isNull(partnerAudit)) {
            log.error("机构编号{}信息不存在！", audit.getPartnerId());
            throw new AllinpayException(BizEnums.ORG_NOT_EXIST.getCode(), BizEnums.ORG_NOT_EXIST.getMsg());
        }
        auditMapper.updateApproveStatus(partnerId, "审核已通过");
        //新增机构通过状态 机构秘钥是否存在判断
        if (Objects.isNull(partnerAudit.getFailReason())) {
            //更改机构表中的记录为正常，更改用户名sysUser
            infoMapper.updateApproveStatus(partnerId);
        } else {
            //审核通过数据同步
            PartnerInfo partnerInfo = new PartnerInfo();
            BeanUtils.copyProperties(audit, partnerInfo);
            partnerInfo.setStatus(CommonConstant.STATUS_NORMAL);
            audit.setFailReason("审核已通过");
            //获取当前登录用户
            partnerInfo.setSysUser("");
            partnerInfo.setModifyTime(new Date());
            infoMapper.updateApproveData(partnerInfo);
            //同步审核通过的图片到机构中
//            FileUtils.deleteDirectory(new File(orgDir + partnerId));
//            FileUtils.copyDirectory(new File(auditDir + partnerId),new File(orgDir + partnerId));
        }

        //生成MD5秘钥
        generateMD5Key();
    }

    private String generateMD5Key() {
        return null;
    }


}
