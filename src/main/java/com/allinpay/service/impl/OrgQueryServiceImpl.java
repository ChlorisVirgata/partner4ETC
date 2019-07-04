package com.allinpay.service.impl;

import com.allinpay.core.common.PageVO;
import com.allinpay.core.util.PageVOUtil;
import com.allinpay.entity.OrgQueryBack;
import com.allinpay.entity.OrgQueryVo;
import com.allinpay.entity.PartnerAudit;
import com.allinpay.mapper.QueryMapper;
import com.allinpay.service.IOrgQueryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrgQueryServiceImpl implements IOrgQueryService {
    @Autowired
    private QueryMapper queryMapper;

    @Override
    public PageVO<OrgQueryBack> queryorginfo(OrgQueryVo orgque) {

        PageHelper.startPage(orgque.getPageNum(), orgque.getPageSize());
        List<OrgQueryBack> partnerAuditList = queryMapper.queryOrgInfo(orgque);
        PageVO<OrgQueryBack> pageVO = PageVOUtil.convert(new PageInfo(partnerAuditList));
        return pageVO;

//        return queryMapper.queryOrgInfo(orgque);
    }
}
