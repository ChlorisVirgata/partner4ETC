package com.allinpay.mapper;

import com.allinpay.controller.query.BankQuery;
import com.allinpay.entity.PartnerBank;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PartnerBankMapper {
    List<PartnerBank> selectList(@Param("bankQuery") BankQuery bankQuery);

    void insert(@Param("partnerBank") PartnerBank partnerBank);

    PartnerBank selectOne(@Param("partnerBank") PartnerBank partnerBank);

    int updateStatus(@Param("partnerBank") PartnerBank partnerBank);
}
