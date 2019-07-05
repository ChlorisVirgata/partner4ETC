package com.allinpay.mapper;

import com.allinpay.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 机构信息、通行记录、用户发卡数据查询，机构冻结、解冻、注销处理
 * Mapper
 *
 * @author xuming
 * @date: 2019-07-02 16:58
 */
public interface QueryMapper {
    List queryOrgInfo(@Param("orgque") OrgQueryVo orgque);

    void blockOrg(String departid);

    List<PartnerInfo> selectNormalList();
    void blockOrg(@Param("orgque") OrgQueryVo orgque);

    List<PassageMoneyBack> queryPassagemoney(@Param("passm") OrgQueryVo orgque);

    List<UserhairpinBack> queryUserhairpin(@Param("usrh") UserhairpinVo usrh);
}
