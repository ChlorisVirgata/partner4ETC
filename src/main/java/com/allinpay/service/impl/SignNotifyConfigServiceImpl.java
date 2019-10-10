package com.allinpay.service.impl;

import com.allinpay.core.common.PageVO;
import com.allinpay.core.constant.CommonConstant;
import com.allinpay.core.constant.enums.BizEnums;
import com.allinpay.core.exception.AllinpayException;
import com.allinpay.core.util.PageVOUtil;
import com.allinpay.entity.PartnerAudit;
import com.allinpay.entity.PartnerSecretInfo;
import com.allinpay.mapper.PartnerAuditMapper;
import com.allinpay.mapper.PartnerSecretInfoMapper;
import com.allinpay.service.ISignNotifyConfigService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class SignNotifyConfigServiceImpl implements ISignNotifyConfigService {
    @Autowired
    private PartnerSecretInfoMapper partnerSecretInfoMapper;
    @Autowired
    private PartnerAuditMapper auditMapper;

    @Override
    public PageVO getByPartnerId(String partnerId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<PartnerSecretInfo> infoList = partnerSecretInfoMapper.selectByPartnerId(partnerId);
        return PageVOUtil.convert(new PageInfo<>(infoList));
    }

    @Override
    public void addNotifyResultConfigInfo(PartnerSecretInfo secretInfo) {
        String partnerId = secretInfo.getPartnerId();
        PartnerAudit audit = auditMapper.selectOne(partnerId, CommonConstant.STATUS_AUDIT);
        if (Objects.isNull(audit)) {
            log.warn("机构信息不存在：{}", partnerId);
            throw new AllinpayException(BizEnums.ORG_NOT_EXIST.getCode(), BizEnums.ORG_NOT_EXIST.getMsg());
        }

        List<PartnerSecretInfo> infoList = partnerSecretInfoMapper.selectByPartnerId(partnerId);
        if (infoList.isEmpty()) {
            partnerSecretInfoMapper.insert(secretInfo);
        } else {
            log.warn("机构{}签约结果通知配置信息已存在", partnerId);
            throw new AllinpayException(BizEnums.ORG_SIGN_RESULT_NOTIFY_CONFIG_EXIST.getCode(), BizEnums.ORG_SIGN_RESULT_NOTIFY_CONFIG_EXIST.getMsg());
        }
    }

    @Override
    public void editNotifyResultConfigInfo(PartnerSecretInfo secretInfo) {
        partnerSecretInfoMapper.updateInfo(secretInfo);
    }

    @Override
    public PartnerSecretInfo getByPartnerId(String partnerId) {
        return partnerSecretInfoMapper.selectOne(partnerId);
    }
}
