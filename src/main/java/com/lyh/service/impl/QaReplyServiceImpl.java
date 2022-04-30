package com.lyh.service.impl;

import com.lyh.dao.QaReplyMappers;
import com.lyh.entity.QaReply;
import com.lyh.entity.vo.QaReplyVo;
import com.lyh.enums.DelEnum;
import com.lyh.service.QaReplyService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author lyh
 * @ClassName QaReplyServiceImpl
 * @createTime 2022/4/27
 */
@Service
public class QaReplyServiceImpl implements QaReplyService {

    @Resource
    private QaReplyMappers qaReplyMappers;

    @Override
    public QaReplyVo sendComment(QaReply qaReply) {
        qaReply.setCreateTime(new Date());
        qaReply.setIsDel(DelEnum.IS_NOT_DEL.getValue());
        qaReplyMappers.insert(qaReply);

        return qaReplyMappers.selectRecentlyComment(qaReply.getUserId());
    }


}
