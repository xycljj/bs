package com.lyh.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyh.dao.*;
import com.lyh.entity.*;
import com.lyh.entity.vo.CountVo;
import com.lyh.entity.vo.UserInfo;
import com.lyh.enums.DelEnum;
import com.lyh.service.UserService;
import com.lyh.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lyh
 * @ClassName UserServiceImpl
 * @createTime 2021/12/16 11:00:00
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private AdministratorOperationInformationMapper administratorOperationInformationMapper;

    @Resource
    private QuestionAnswerMapper questionAnswerMapper;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private SkillFieldMapper skillFieldMapper;

    @Autowired
    private ArticleMapper articleMapper;


    @Override
    public User findUserById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int addUser(User user) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("username",user.getUsername());
        List<User> users = userMapper.selectByExample(example);
        if(users.size() == 0){
            user.setIsDel(DelEnum.IS_NOT_DEL.getValue());
            user.setCreateTime(new Date());
            return userMapper.insertSelective(user);
        }
        return 0;
    }

    @Override
    public List<User> findUserList(String username, String phone) {
        return userMapper.findUserList(username, phone);
    }

    @Override
    public User findUserByUsername(String username) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("username", username).andEqualTo("isDel", DelEnum.IS_NOT_DEL.getValue());
        return userMapper.selectOneByExample(example);
    }

    @Override
    public int delUser(Long id) {
        User user = new User();
        user.setId(id);
        user.setIsDel(DelEnum.IS_DEL.getValue());
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User editUser(User user, Admin admin) {
        if (admin == null) {
            userMapper.updateByPrimaryKeySelective(user);
        }else{
            userMapper.updateByPrimaryKeySelective(user);
            //添加操作记录
            AdministratorOperationInformation administratorOperationInformation = new AdministratorOperationInformation();
            administratorOperationInformation.setAdminId(admin.getId());
            administratorOperationInformation.setAdminName(admin.getAdminName());
            administratorOperationInformation.setMessage(admin.getAdminName() + " 修改了 " + user.getName() + " 的信息");
            administratorOperationInformationMapper.insert(administratorOperationInformation);
        }
        return user;
    }

    @Override
    public User login(User user) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("username", user.getUsername())
                .andEqualTo("password", user.getPassword())
                .andEqualTo("isDel", DelEnum.IS_NOT_DEL.getValue());
        User user1 = userMapper.selectOneByExample(example);
        if (user1 == null) {
            return null;
        }
        return user1;
    }

    @Override
    public void changeUserInfo(Long userId, String url) {
        User user = userMapper.selectByPrimaryKey(userId);
        user.setAvatar(url);
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public List<User> getCloudList(List<Long> list) {
        return userMapper.selectCloudList(list);
    }

    @Override
    public List<User> findMyFocusUsers(Long userId) {
        return userMapper.selectMyFocusUsers(userId);
    }

    @Override
    public List<User> findUserBySearchStr(String searchStr) {
        Example example = new Example(User.class);
        example.createCriteria().orLike("username",searchStr).andEqualTo("isDel",DelEnum.IS_NOT_DEL.getValue());
        return userMapper.selectByExample(example);
    }

    @Override
    public Integer collectionCount(Long userId) {
        Object o = redisUtil.get("user:collection:count" + userId);
        if(null == o){
            return 0;
        }
        return Integer.parseInt(String.valueOf(o));
    }

    @Override
    public Integer getCreditToCount(Long userId) {
        Object o = redisUtil.get("getCreditTo" + userId);
        if(null == o){
            return 0;
        }
        return Integer.parseInt(String .valueOf(o));
    }

    @Override
    public PageInfo<UserInfo> getConsultantList(String username, Long skillFieldId, Integer pageIndex, Integer pageSize) {
        Page<UserInfo> page = PageHelper.startPage(pageIndex, pageSize);
        List<UserInfo> list = userMapper.selectConsultantList(username,skillFieldId);
        for(UserInfo userInfo: list){
            List<SkillField> skillFields = skillFieldMapper.selectByIds(userInfo.getSkillField());
            List<String> strings = new ArrayList<>();
            for(SkillField skillField1 : skillFields){
                strings.add(skillField1.getValue());
            }
            userInfo.setSkills(strings);
        }
        PageInfo<UserInfo> pageInfo = page.toPageInfo();
        pageInfo.setList(list);
        return pageInfo;
    }

    @Override
    public boolean cancellation(Long userId) {
        int i = delUser(userId);
        return i==1;
    }

    @Override
    public CountVo findReplyLikeCollectCounts(Long userId) {
        CountVo countVo = new CountVo();
        // 收藏量
        Object o = redisUtil.get("user:collection:count" + userId);
        if(null == o){
           countVo.setCollectCount(0);
        }else {
            countVo.setCollectCount(Integer.parseInt(String.valueOf(o)));
        }
        // 回答数
        Example example = new Example(QuestionAnswer.class);
        example.createCriteria().andEqualTo("fromUserId", userId).andEqualTo("isDel",DelEnum.IS_NOT_DEL.getValue());
        countVo.setReplyCount(questionAnswerMapper.selectCountByExample(example));

        // 获赞数
        Object o1 = redisUtil.get("getCreditTo" + userId);
        if(null == o1){
            countVo.setLikeCount(0);
        }else{
            countVo.setLikeCount(Integer.parseInt(String.valueOf(o1)));
        }

        // 文章数
        Example example1 = new Example(Article.class);
        example1.createCriteria().andEqualTo("userId", userId).andEqualTo("isDel",DelEnum.IS_NOT_DEL.getValue());
        countVo.setArticleCount(articleMapper.selectCountByExample(example1));
        return countVo;
    }
}
