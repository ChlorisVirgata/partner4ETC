package com.allinpay.service;

import com.allinpay.core.common.PageVO;
import com.allinpay.entity.*;

import java.util.List;

/**
 * 机构信息、通行记录、用户发卡数据查询，机构冻结、解冻、注销处理
 * Service接口
 * @author xuming
 * @date: 2019-07-02 16:58
 */
public interface IOrgQueryService {
    PageVO<OrgQueryBack> queryorginfo(OrgQueryVo orgque);

    void blockOrg(OrgQueryVo orgque);

    PageVO<PassageMoneyBack> queryPassagemoney(PassageMoneyVo passm);

    PageVO<UserhairpinBack> queryUserhairpin(UserhairpinVo usrh);

    void blockOrg(String departid);

    /**
     * @Description: 查询正常机构状态的列表
     * @Param: 无
     * @Return: List<PartnerInfo>
     */
    List<PartnerInfo> selectByNormalStatus();
}
