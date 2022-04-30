package com.lyh.service;

import com.lyh.entity.QaReply;
import com.lyh.entity.vo.QaReplyVo;

/**
 * @author lyh
 * @ClassName QaReplyService
 * @createTime 2022/4/27
 */
public interface QaReplyService {
    QaReplyVo sendComment(QaReply qaReply);
}
