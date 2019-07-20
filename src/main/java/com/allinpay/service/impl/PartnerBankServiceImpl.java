package com.allinpay.service.impl;

import com.allinpay.controller.query.BankQuery;
import com.allinpay.core.common.PageVO;
import com.allinpay.core.constant.CommonConstant;
import com.allinpay.core.constant.enums.BizEnums;
import com.allinpay.core.exception.AllinpayException;
import com.allinpay.core.util.PageVOUtil;
import com.allinpay.entity.BankInfo;
import com.allinpay.entity.PartnerBank;
import com.allinpay.entity.PartnerInfo;
import com.allinpay.mapper.BankInfoMapper;
import com.allinpay.mapper.PartnerBankMapper;
import com.allinpay.mapper.PartnerInfoMapper;
import com.allinpay.service.IPartnerBankService;
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
public class PartnerBankServiceImpl implements IPartnerBankService {
    @Autowired
    private PartnerBankMapper partnerBankMapper;
    @Autowired
    private PartnerInfoMapper partnerInfoMapper;
    @Autowired
    private BankInfoMapper bankInfoMapper;

    @Override
    public PageVO<PartnerBank> selectByCondition(BankQuery bankQuery) {
        PageHelper.startPage(bankQuery.getPageNum(), bankQuery.getPageSize());
        List<PartnerBank> partnerBankList = partnerBankMapper.selectList(bankQuery);
        return PageVOUtil.convert(new PageInfo<>(partnerBankList));
    }

    @Override
    public void addOrgBank(PartnerBank partnerBank) {
        String partnerId = partnerBank.getPartnerId();
        String bankId = partnerBank.getBankId();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        PartnerInfo partnerInfo = partnerInfoMapper.selectOne(partnerId);
        //判断机构状态是否正常 需要是正常状态机构
        if (Objects.isNull(partnerInfo)) {
            log.warn("机构信息不存在：{}", partnerId);
            throw new AllinpayException(BizEnums.ORG_FORMAL_NOT_EXIST.getCode(), BizEnums.ORG_FORMAL_NOT_EXIST.getMsg());
        }
        if (!CommonConstant.STATUS_NORMAL.equals(partnerInfo.getStatus())) {
            log.warn("机构状态有误：{}", partnerId);
            throw new AllinpayException(BizEnums.ORG_STATUS_EXCEPTION.getCode(), BizEnums.ORG_STATUS_EXCEPTION.getMsg());
        }
        //判断银行基础信息是否存在
        BankInfo bankInfo = bankInfoMapper.selectOne(bankId);
        if (Objects.isNull(bankInfo)) {
            log.warn("银行基础信息不存在：{}", bankId);
            throw new AllinpayException(BizEnums.BANK_NOT_EXIST.getCode(), BizEnums.BANK_NOT_EXIST.getMsg());
        }
        //判断该记录是否已存在
        PartnerBank queryPartnerBank = partnerBankMapper.selectOne(partnerBank);
        if (Objects.nonNull(queryPartnerBank)) {
            log.warn("机构银行映射信息已存在：{}", partnerId);
            throw new AllinpayException(BizEnums.PARTNER_BANK_EXIST.getCode(), BizEnums.PARTNER_BANK_EXIST.getMsg());
        }
        partnerBank.setInsertTime(dateFormat.format(new Date()));
        partnerBank.setStatus(CommonConstant.BANK_STATUS_NORMAL);
        partnerBankMapper.insert(partnerBank);
    }

    @Override
    public void updateStatus(PartnerBank partnerBank) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        partnerBank.setModifyTime(dateFormat.format(new Date()));
        partnerBankMapper.updateStatus(partnerBank);
    }
}
