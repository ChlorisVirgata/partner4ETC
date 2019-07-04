package com.allinpay.service;

import com.allinpay.core.common.PageVO;
import com.allinpay.entity.OrgQueryBack;
import com.allinpay.entity.OrgQueryVo;

/**
 * 机构信息查询接口
 */
public interface IOrgQueryService {
    PageVO<OrgQueryBack> queryorginfo(OrgQueryVo orgque);

    void blockOrg(String departid);
}
