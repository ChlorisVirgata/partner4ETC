package com.allinpay.mapper;

import com.allinpay.entity.PartnerDeposit;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PartnerDepositMapper {
    List<PartnerDeposit> selectList(@Param("partnerId") String partnerId);

    void insert(@Param("deposit") PartnerDeposit deposit);

    void update(@Param("deposit") PartnerDeposit deposit);
}
