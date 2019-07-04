package com.allinpay.mapper;

import com.allinpay.entity.OrgQueryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QueryMapper {
    List queryOrgInfo(@Param("orgque") OrgQueryVo orgque);
}
