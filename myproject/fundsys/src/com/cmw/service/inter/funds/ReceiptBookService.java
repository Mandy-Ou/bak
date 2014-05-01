package com.cmw.service.inter.funds;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.service.IService;
import com.cmw.entity.funds.ReceiptBookEntity;


/**
 * 汇票转让承诺书表  Service接口
 * @author 郑符明
 * @date 2014-02-20T00:00:00
 */
@Description(remark="汇票转让承诺书表业务接口",createDate="2014-02-20T00:00:00",author="郑符明")
public interface ReceiptBookService extends IService<ReceiptBookEntity, Long> {
}
