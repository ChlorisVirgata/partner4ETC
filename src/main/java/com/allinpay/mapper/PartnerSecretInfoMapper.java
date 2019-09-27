package com.allinpay.mapper;

import com.allinpay.entity.PartnerSecretInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PartnerSecretInfoMapper {
    List<PartnerSecretInfo> selectByPartnerId(@Param("partnerId") String partnerId);

    int insert(@Param("secretInfo") PartnerSecretInfo secretInfo);

    int updateInfo(@Param("secretInfo") PartnerSecretInfo secretInfo);
}
