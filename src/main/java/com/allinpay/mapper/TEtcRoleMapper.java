package com.allinpay.mapper;

import com.allinpay.entity.TEtcSysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wuchao
 * @since 2019-07-05
 */
@Repository
@Mapper
public interface TEtcRoleMapper extends BaseMapper<TEtcSysRole> {

}
