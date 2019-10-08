package com.allinpay.service;

import com.allinpay.core.common.PageVO;
import com.allinpay.entity.PartnerSecretInfo;

public interface ISignNotifyConfigService {

    /**
     * 查询指定机构签约结构通知配置信息，为空则查询所有
     *
     * @param partnerId
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageVO getByPartnerId(String partnerId, int pageNum, int pageSize);

    /**
     * 增加合作结构通知结果配置信息
     *
     * @param secretInfo
     */
    void addNotifyResultConfigInfo(PartnerSecretInfo secretInfo);

    /**
     * 编辑合作机构通知结果配置信息
     *
     * @param secretInfo
     */
    void editNotifyResultConfigInfo(PartnerSecretInfo secretInfo);
}
