package com.allinpay.controller;

import com.allinpay.core.common.PageVO;
import com.allinpay.core.common.ResponseData;
import com.allinpay.entity.*;
import com.allinpay.service.IOrgQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 机构信息、通行记录、用户发卡数据查询，机构冻结、解冻、注销处理
 * Controller
 * @author xuming
 * @date: 2019-07-02 16:58
 */
@RestController
@RequestMapping("/query")
public class OrgQueryController {

    @Autowired
    private IOrgQueryService orgquery;

    /**
     * 机构信息查询
     *
     * @param orgque
     * @return
     */
    @GetMapping("/org/getList")
    public ResponseData queryOrgInfo(OrgQueryVo orgque) {
        PageVO<OrgQueryBack> querylist = orgquery.queryorginfo(orgque);
        return ResponseData.success().setData(querylist);
    }

    /**
     * 通行费记录查询
     *
     * @param passm
     * @return
     */
    @GetMapping("/passagemoney/getList")
    public ResponseData queryPassagemoney(PassageMoneyVo passm) {
        PageVO<PassageMoneyBack> querylist = orgquery.queryPassagemoney(passm);
        return ResponseData.success().setData(querylist);
    }

    /**
     * 用户发卡数据查询
     *
     * @param usrh
     * @return
     */
    @GetMapping("/userhairpin/getList")
    public ResponseData queryUserhairpin(UserhairpinVo usrh) {
        PageVO<UserhairpinBack> querylist = orgquery.queryUserhairpin(usrh);
        return ResponseData.success().setData(querylist);
    }

    /**
     * 冻结、解冻、注销机构
     *
     * @param orgque
     * @return
     */
    @PostMapping("/org/blockOrg")
    public ResponseData blockOrg(OrgQueryVo orgque) {
//        String user = SecurityUtils.getSubject().getPrincipal().toString();
//        orgque.setSysUser(user);
        orgquery.blockOrg(orgque);
        return ResponseData.success().setData(null);
    }

    @GetMapping("/org/normalOrgList")
    public ResponseData normalOrgList() {
        List<PartnerInfo> partnerInfoList = orgquery.selectByNormalStatus();
        return ResponseData.success().setData(partnerInfoList);
    }

}
