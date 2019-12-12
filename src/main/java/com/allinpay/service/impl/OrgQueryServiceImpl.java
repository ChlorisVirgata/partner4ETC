package com.allinpay.service.impl;

import com.allinpay.core.common.PageVO;
import com.allinpay.core.util.PageVOUtil;
import com.allinpay.core.util.ServerConfig;
import com.allinpay.entity.*;
import com.allinpay.mapper.QueryMapper;
import com.allinpay.service.IOrgQueryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
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

    @Autowired
    private ServerConfig serverConfig;

    @Override
    public PageVO<OrgQueryBack> queryorginfo(OrgQueryVo orgque) {
        PageHelper.startPage(orgque.getPageNum(), orgque.getPageSize());
        String url= serverConfig.getUrl();
        orgque.setUrl(url);
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
        TEtcSysUser user = getUser();//获取登录用户信息
        passm.setUserPartnerId(user.getPartnerId());
        PageHelper.startPage(passm.getPageNum(), passm.getPageSize());
        List<PassageMoneyBack> partnerAuditList = queryMapper.queryPassagemoney(passm);
        PageVO<PassageMoneyBack> pageVO = PageVOUtil.convert(new PageInfo(partnerAuditList));
        return pageVO;
    }

    @Override
    public PageVO<UserhairpinBack> queryUserhairpin(UserhairpinVo usrh) {
        TEtcSysUser user = getUser();//获取登录用户信息
        usrh.setUserPartnerId(user.getPartnerId());
        PageHelper.startPage(usrh.getPageNum(), usrh.getPageSize());
        List<UserhairpinBack> partnerAuditList = queryMapper.queryUserhairpin(usrh);
        PageVO<UserhairpinBack> pageVO = PageVOUtil.convert(new PageInfo(partnerAuditList));
        return pageVO;
    }


    @Override
    public List<PartnerInfo> selectByNormalStatus() {
        return queryMapper.selectNormalList();
    }

    /**
     *  合作银行obu激活信息查询
     * @param activationVo
     * @return
     */
    @Override
    public PageVO<ActivationBack> queryActivation(ActivationVo activationVo) {
        TEtcSysUser user = getUser();//获取登录用户信息
        activationVo.setUserPartnerId(user.getPartnerId());
        PageHelper.startPage(activationVo.getPageNum(), activationVo.getPageSize());
        List<ActivationBack> partnerAuditList = queryMapper.queryActivation(activationVo);
        PageVO<ActivationBack> pageVO = PageVOUtil.convert(new PageInfo(partnerAuditList));
        return pageVO;
    }

    /**
     *  获取登录用户信息
     *
     * @return TEtcSysUser
     */
    private TEtcSysUser getUser(){
        return  (TEtcSysUser) SecurityUtils.getSubject().getPrincipal();
    }
}
