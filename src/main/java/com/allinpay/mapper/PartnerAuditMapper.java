package com.allinpay.mapper;

import com.allinpay.entity.PartnerAudit;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PartnerAuditMapper {
    List<PartnerAudit> selectList(@Param("audit") PartnerAudit audit);
}
