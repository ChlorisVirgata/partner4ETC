package com.allinpay.controller;

import com.allinpay.core.common.PageVO;
import com.allinpay.core.common.ResponseData;
import com.allinpay.entity.OrgQueryBack;
import com.allinpay.entity.OrgQueryVo;
import com.allinpay.service.IOrgQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 机构信息查询
 */
@RestController
@RequestMapping("/query")
public class OrgQueryController {

    @Autowired
    private IOrgQueryService orgquery;
    @GetMapping("/org/getList")
    public ResponseData queryOrgInfo(OrgQueryVo orgque){
        PageVO<OrgQueryBack> querylist=orgquery.queryorginfo(orgque);
        return ResponseData.success().setData(querylist);
    }

    /**
     * 冻结机构
     *
     * @param departid
     * @return
     */
    @GetMapping("/org/blockorg")
    public ResponseData blockOrg(String departid) {
        orgquery.blockOrg(departid);
        return ResponseData.success().setData(null);
    }

}
