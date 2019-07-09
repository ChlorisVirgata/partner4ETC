package com.allinpay.mapper;

import com.allinpay.controller.query.OrgAuditQuery;
import com.allinpay.controller.query.OrgModifyQuery;
import com.allinpay.entity.PartnerAudit;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PartnerAuditMapper {
    List<PartnerAudit> selectList(@Param("query") OrgAuditQuery query);

    PartnerAudit selectOne(@Param("partnerId") String partnerId,
                           @Param("status") Integer status);

    int updateRefuseStatus(@Param("partnerId") String partnerId,
                           @Param("failReason") String failReason,
                           @Param("sysUser") String sysUser);

    int updateApproveStatus(@Param("partnerId") String partnerId,
                            @Param("failReason") String failReason,
                            @Param("sysUser") String sysUser);

    List<PartnerAudit> selectModifyList(@Param("query") OrgModifyQuery query);

    int insert(@Param("audit") PartnerAudit audit);

    int delete(@Param("partnerId") String partnerId);

    int update(@Param("audit") PartnerAudit audit);
}
