package com.cmw.service.inter.crm;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.service.IService;
import com.cmw.entity.crm.EbankBorrEntity;


/**
 * 银行借款情况  Service接口
 * @author pdh
 * @date 2012-12-26T00:00:00
 */
@Description(remark="银行借款情况业务接口",createDate="2012-12-26T00:00:00",author="pdh")
public interface EbankBorrService extends IService<EbankBorrEntity, Long> {
}