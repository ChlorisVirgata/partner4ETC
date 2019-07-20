package com.allinpay.service.impl;

import com.allinpay.core.common.PageVO;
import com.allinpay.core.constant.enums.BizEnums;
import com.allinpay.core.exception.AllinpayException;
import com.allinpay.core.util.PageVOUtil;
import com.allinpay.entity.PartnerDeposit;
import com.allinpay.entity.PartnerInfo;
import com.allinpay.mapper.PartnerDepositMapper;
import com.allinpay.mapper.PartnerInfoMapper;
import com.allinpay.service.IOrgDepositService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class OrgDepositServiceImpl implements IOrgDepositService {
    @Autowired
    private PartnerDepositMapper depositMapper;
    @Autowired
    private PartnerInfoMapper infoMapper;

    @Override
    public PageVO<PartnerDeposit> selectByCondition(String partnerId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<PartnerDeposit> depositList = depositMapper.selectList(partnerId);
        return PageVOUtil.convert(new PageInfo<>(depositList));
    }

    @Override
    public void addOrgDeposit(PartnerDeposit deposit) {
        String partnerId = deposit.getPartnerId();
        String kid = UUID.randomUUID().toString().replace("-", "");
        PartnerInfo partnerInfo = infoMapper.selectOne(partnerId);
        if (Objects.isNull(partnerInfo)) {
            log.warn("机构信息【{}】不存在，请检查机构编码", partnerId);
            throw new AllinpayException(BizEnums.ORG_FORMAL_NOT_EXIST.getCode(), BizEnums.ORG_FORMAL_NOT_EXIST.getMsg());
        }
        PartnerDeposit partnerDeposit = depositMapper.selectOne(partnerId);
        if (Objects.nonNull(partnerDeposit)) {
            log.warn("机构【{}】已配置保证金信息", partnerId);
            throw new AllinpayException(BizEnums.ORG_DEPOSIT_EXIST.getCode(), BizEnums.ORG_DEPOSIT_EXIST.getMsg());
        }
        if (deposit.getDeposit() < deposit.getMinDeposit()) {
            log.warn("签约保证金金额不能小于最低保证金金额！");
            throw new AllinpayException(BizEnums.ORG_DEPOSIT_AMOUNT_EXCEPTION.getCode(), BizEnums.ORG_DEPOSIT_AMOUNT_EXCEPTION.getMsg());
        }
        deposit.setKid(kid);
        depositMapper.insert(deposit);
    }

    @Override
    public void editOrgDeposit(PartnerDeposit deposit) {
        if (deposit.getDeposit() < deposit.getMinDeposit()) {
            log.warn("签约保证金金额不能小于最低保证金金额！");
            throw new AllinpayException(BizEnums.ORG_DEPOSIT_AMOUNT_EXCEPTION.getCode(), BizEnums.ORG_DEPOSIT_AMOUNT_EXCEPTION.getMsg());
        }
        depositMapper.update(deposit);
    }
}
