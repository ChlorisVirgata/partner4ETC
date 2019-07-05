package com.allinpay.mapper;

import com.allinpay.entity.PartnerInfo;
import org.apache.ibatis.annotations.Param;

public interface PartnerInfoMapper {
    int updateRefuseStatus(@Param("partnerId") String partnerId);

    int updateApproveStatus(@Param("partnerId") String partnerId);

    int updateApproveData(@Param("partnerInfo") PartnerInfo partnerInfo);

    int insert(@Param("partnerInfo") PartnerInfo partnerInfo);
}
