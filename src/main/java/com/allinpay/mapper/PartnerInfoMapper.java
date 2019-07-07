package com.allinpay.mapper;

import com.allinpay.entity.PartnerInfo;
import org.apache.ibatis.annotations.Param;

public interface PartnerInfoMapper {
    int updateRefuseStatus(@Param("partnerId") String partnerId,
                           @Param("sysUser") String sysUser);

    int updateApproveStatus(@Param("partnerId") String partnerId,
                            @Param("sysUser") String sysUser,
                            @Param("secretKey") String secretKey);

    int updateApproveData(@Param("partnerInfo") PartnerInfo partnerInfo);

    int insert(@Param("partnerInfo") PartnerInfo partnerInfo);

    PartnerInfo selectOne(@Param("partnerId") String partnerId);
}
