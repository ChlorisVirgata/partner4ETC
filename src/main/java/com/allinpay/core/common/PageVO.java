package com.allinpay.core.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


/**
 * 返回页面分页信息数据对象
 */
@Setter
@Getter
@ToString
public class PageVO<T> {

    /**
     * 页码
     */
    private Integer pageNum;

    /**
     * 每页数量
     */
    private Integer pageSize;

    /**
     * 当前页数量
     */
    private Integer size;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 总页数
     */
    private Integer pages;

    /**
     * 首页页码
     */
    private Integer firstPage;

    /**
     * 上一页页码
     */
    private Integer prePage;

    /**
     * 下一页页码
     */
    private Integer nextPage;

    /**
     * 末页页码
     */
    private Integer lastPage;

    /**
     * 订单数量
     */
    private Integer orderCount = 0;

    /**
     * 总金额
     */
    private String totalAmount = "0.00";

    /**
     * 数据
     */
    private List<T> list;
}