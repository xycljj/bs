package com.lyh.entity.vo;

import com.lyh.entity.Article;
import com.lyh.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author lyh
 * @ClassName GlobalSearchVo
 * @createTime 2022/5/25
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class GlobalSearchVo implements Serializable {
    private List<User> userList;
    private List<Article> articleList;
}
