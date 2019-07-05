package com.allinpay.service.impl;

import com.allinpay.core.common.PageVO;
import com.allinpay.core.util.PageVOUtil;
import com.allinpay.entity.*;
import com.allinpay.mapper.QueryMapper;
import com.allinpay.service.IOrgQueryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 机构信息、通行记录、用户发卡数据查询，机构冻结、解冻、注销处理
 * Service实现
 *
 * @author xuming
 * @date: 2019-07-02
 */
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
    }

    @Override
    public void blockOrg(OrgQueryVo orgque) {
        queryMapper.blockOrg(orgque);
    }

    @Override
    public PageVO<PassageMoneyBack> queryPassagemoney(PassageMoneyVo passm) {
        PageHelper.startPage(passm.getPageNum(), passm.getPageSize());
        List<PassageMoneyBack> partnerAuditList = queryMapper.queryPassagemoney(passm);
        PageVO<PassageMoneyBack> pageVO = PageVOUtil.convert(new PageInfo(partnerAuditList));
        return pageVO;
    }

    @Override
    public PageVO<UserhairpinBack> queryUserhairpin(UserhairpinVo usrh) {
        PageHelper.startPage(usrh.getPageNum(), usrh.getPageSize());
        List<UserhairpinBack> partnerAuditList = queryMapper.queryUserhairpin(usrh);
        PageVO<UserhairpinBack> pageVO = PageVOUtil.convert(new PageInfo(partnerAuditList));
        return pageVO;
    }

}
