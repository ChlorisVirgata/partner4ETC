package com.allinpay.service.impl;

import com.allinpay.core.common.PageVO;
import com.allinpay.core.util.PageVOUtil;
import com.allinpay.entity.PartnerAudit;
import com.allinpay.mapper.PartnerAuditMapper;
import com.allinpay.service.IOrgAuditService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrgAuditServiceImpl implements IOrgAuditService {
    @Autowired
    private PartnerAuditMapper auditMapper;

    @Override
    public PageVO<PartnerAudit> selectByCondition(PartnerAudit audit) {
        PageHelper.startPage(audit.getPageNum(), audit.getPageSize());
        List<PartnerAudit> partnerAuditList = auditMapper.selectList(audit);
        PageVO<PartnerAudit> pageVO = PageVOUtil.convert(new PageInfo(partnerAuditList));
        return pageVO;
    }
}
