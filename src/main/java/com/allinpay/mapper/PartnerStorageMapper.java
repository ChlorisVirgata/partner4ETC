package com.allinpay.mapper;

import com.allinpay.entity.PartnerStorage;
import org.apache.ibatis.annotations.Param;

public interface PartnerStorageMapper {

    int insert(@Param("storage") PartnerStorage storage);

    PartnerStorage selectOne(@Param("partnerId") String partnerId);

    int delete(@Param("partnerId") String partnerId);

    int update(@Param("storage") PartnerStorage storage);
}
