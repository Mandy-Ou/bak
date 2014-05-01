package com.cmw.service.inter.funds;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.service.IService;
import com.cmw.entity.funds.BackReceiptEntity;


/**
 * 回款收条表  Service接口
 * @author 郑符明
 * @date 2014-02-20T00:00:00
 */
@Description(remark="回款收条表业务接口",createDate="2014-02-20T00:00:00",author="郑符明")
public interface BackReceiptService extends IService<BackReceiptEntity, Long> {
}
