package com.allinpay.mapper;

import com.allinpay.controller.query.BankQuery;
import com.allinpay.entity.BankInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BankInfoMapper {
    List<BankInfo> selectList(@Param("bankQuery") BankQuery bankQuery);

    int insert(@Param("bankInfo") BankInfo bankInfo);

    BankInfo selectOne(@Param("bankId") String bankId);

    int update(@Param("bankInfo") BankInfo bankInfo);

    int updateStatus(@Param("bankId") String bankId,
                     @Param("status") String status);
}
