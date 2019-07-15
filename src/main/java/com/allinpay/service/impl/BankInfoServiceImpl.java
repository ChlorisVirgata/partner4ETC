package com.allinpay.service.impl;

import com.allinpay.controller.query.BankQuery;
import com.allinpay.core.common.PageVO;
import com.allinpay.core.constant.CommonConstant;
import com.allinpay.core.constant.enums.BizEnums;
import com.allinpay.core.exception.AllinpayException;
import com.allinpay.core.util.PageVOUtil;
import com.allinpay.entity.BankInfo;
import com.allinpay.mapper.BankInfoMapper;
import com.allinpay.service.IBankInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class BankInfoServiceImpl implements IBankInfoService {
    @Autowired
    private BankInfoMapper bankInfoMapper;

    @Override
    public PageVO<BankInfo> selectByCondition(BankQuery bankQuery) {
        PageHelper.startPage(bankQuery.getPageNum(), bankQuery.getPageSize());
        List<BankInfo> bankInfoList = bankInfoMapper.selectList(bankQuery);
        return PageVOUtil.convert(new PageInfo<>(bankInfoList));
    }

    @Override
    public void addBank(String bankId, String bankName) {
        BankInfo bankInfo = bankInfoMapper.selectOne(bankId);
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (Objects.nonNull(bankInfo)) {
            log.warn("银行基础信息已存在：{}", bankId);
            throw new AllinpayException(BizEnums.BANK_EXIST.getCode(), BizEnums.BANK_EXIST.getMsg());
        }
        bankInfo = new BankInfo();
        bankInfo.setBankId(bankId);
        bankInfo.setBankName(bankName);
        bankInfo.setStatus(CommonConstant.BANK_STATUS_NORMAL);
        bankInfo.setInsertTime(simpleDateFormat.format(new Date()));
        bankInfoMapper.insert(bankInfo);
    }

    @Override
    public void editBank(String bankId, String bankName) {
        BankInfo bankInfo = bankInfoMapper.selectOne(bankId);
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (Objects.isNull(bankInfo)) {
            log.error("银行基础信息不存在：{}", bankId);
            throw new AllinpayException(BizEnums.BANK_NOT_EXIST.getCode(), BizEnums.BANK_NOT_EXIST.getMsg());
        }
        bankInfo.setBankName(bankName);
        bankInfo.setModifyTime(simpleDateFormat.format(new Date()));
        bankInfoMapper.update(bankInfo);
    }

    @Override
    public void updateStatus(String bankId, String status) {
        BankInfo bankInfo = bankInfoMapper.selectOne(bankId);
        if (Objects.isNull(bankInfo)) {
            log.error("银行基础信息不存在：{}", bankId);
            throw new AllinpayException(BizEnums.BANK_NOT_EXIST.getCode(), BizEnums.BANK_NOT_EXIST.getMsg());
        }
        bankInfoMapper.updateStatus(bankId, status);
    }
}
