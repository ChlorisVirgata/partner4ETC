package com.allinpay.service.impl;

import com.allinpay.controller.query.OrgModifyQuery;
import com.allinpay.core.common.PageVO;
import com.allinpay.core.util.PageVOUtil;
import com.allinpay.entity.PartnerAudit;
import com.allinpay.mapper.PartnerAuditMapper;
import com.allinpay.mapper.PartnerStorageMapper;
import com.allinpay.service.IOrgModifyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrgModifyServiceImpl implements IOrgModifyService {
    @Autowired
    private PartnerAuditMapper auditMapper;
    @Autowired
    private PartnerStorageMapper storageMapper;

    @Override
    public PageVO<PartnerAudit> selectByCondition(OrgModifyQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<PartnerAudit> partnerAuditList = auditMapper.selectModifyList(query);
        PageVO<PartnerAudit> pageVO = PageVOUtil.convert(new PageInfo<>(partnerAuditList));
        PageVO<Object> pageVO1;
        return pageVO;
    }
}
