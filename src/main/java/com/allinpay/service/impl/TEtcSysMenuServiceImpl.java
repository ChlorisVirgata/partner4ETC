package com.allinpay.service.impl;

import com.allinpay.core.common.ResponseBean;
import com.allinpay.entity.TEtcSysMenu;
import com.allinpay.mapper.TEtcMenuMapper;
import com.allinpay.service.ITEtcSysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuchao
 * @since 2019-07-05
 */
@Service
public class TEtcSysMenuServiceImpl extends ServiceImpl<TEtcMenuMapper, TEtcSysMenu> implements ITEtcSysMenuService {

    @Autowired
    private TEtcMenuMapper sysMenuMapper;

    @Override
    public ResponseBean queryPage(Integer pageNo, Integer pageSize, HashMap map) {
        Page page = PageHelper.startPage(pageNo, pageSize);
        List<TEtcSysMenu> roleList = (List) sysMenuMapper.selectByMap(map);
        PageInfo<TEtcSysMenu> pageInfo = new PageInfo<TEtcSysMenu>(roleList);
        return  ResponseBean.ok(roleList,pageInfo.getTotal());
    }
}
