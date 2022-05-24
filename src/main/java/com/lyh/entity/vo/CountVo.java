package com.lyh.entity.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lyh
 * @ClassName CountVo
 * @createTime 2022/5/24
 */
@Data
@NoArgsConstructor
@Accessors(chain = true) // 链式写法
public class CountVo implements Serializable {
    private Integer replyCount;
    private Integer collectCount;
    private Integer likeCount;
    private Integer articleCount;

    private static final long serialVersionUID = 1L;
}
