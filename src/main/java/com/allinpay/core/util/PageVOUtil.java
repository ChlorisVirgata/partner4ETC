package com.allinpay.core.util;

import com.allinpay.core.common.PageVO;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

/**
 * 分页查询结果对象转换工具类
 */
@Component
public class PageVOUtil {

    private PageVOUtil() {
    }

    /**
     * 将pageInfo中的分页信息转换到PageVO中
     */
    public static <K, E> PageVO<K> convert(PageInfo<E> pageInfo) {
        PageVO<K> pageVO = new PageVO<>();
        pageVO.setPageNum(pageInfo.getPageNum());
        pageVO.setPageSize(pageInfo.getPageSize());
        pageVO.setSize(pageInfo.getSize());
        pageVO.setTotal(pageInfo.getTotal());
        pageVO.setPages(pageInfo.getPages());
        pageVO.setFirstPage(pageInfo.getNavigateFirstPage());
        pageVO.setPrePage(pageInfo.getPrePage());
        pageVO.setNextPage(pageInfo.getNextPage());
        pageVO.setLastPage(pageInfo.getNavigateLastPage());
        return pageVO;
    }
}