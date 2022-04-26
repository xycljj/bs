package com.lyh.controller;

import com.lyh.entity.PTag;
import com.lyh.entity.SubTag;
import com.lyh.entity.vo.TagVo;
import com.lyh.service.TagService;
import com.lyh.utils.Result;
import com.lyh.utils.ResultUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lyh
 * @ClassName TagController
 * @createTime 2022/4/26
 */

@RestController
@RequestMapping("tag")
public class TagController {

    @Resource
    private TagService tagService;


    /**
    * @return
    * @Author lyh
    * @Description  添加问题标签1
    * @Param
    * @Date 2022/4/26
    **/
    @PostMapping("addTag1")
    public Result<Boolean> addTag1(@RequestBody PTag pTag){
        boolean isExist = tagService.checkTagName1IsExist(pTag.getName());
        if(isExist){
            return ResultUtil.fail("该标签名已存在");
        }
        boolean flag = tagService.addTag1(pTag);
        return ResultUtil.ok(flag);
    }

    /**
     * @return
     * @Author lyh
     * @Description  添加问题标签2
     * @Param
     * @Date 2022/4/26
     **/
    @PostMapping("addTag2")
    public Result<Boolean> addTag2(@RequestBody SubTag subTag){
        boolean isExist = tagService.checkTagName2IsExist(subTag.getName());
        if(isExist){
            return ResultUtil.fail("该标签名已存在");
        }
        boolean flag = tagService.addTag2(subTag);
        return ResultUtil.ok(flag);
    }

    /**
    * @return
    * @Author lyh
    * @Description 删除问题标签1
    * @Param
    * @Date 2022/4/26
    **/
    @GetMapping("delTag1")
    public Result<Boolean> delTag1(Long id){
        boolean flag = tagService.delTag1(id);
        return ResultUtil.ok();
    }

    /**
     * @return
     * @Author lyh
     * @Description 删除问题标签2
     * @Param
     * @Date 2022/4/26
     **/
    @GetMapping("delTag2")
    public Result<Boolean> delTag2(Long id){
        boolean flag = tagService.delTag2(id);
        return ResultUtil.ok();
    }

    /**
    * @return
    * @Author lyh
    * @Description tag1列表
    * @Param
    * @Date 2022/4/26
    **/
    @GetMapping("getTag1List")
    public Result<List<PTag>> getTag1List(String tagName){
        List<PTag> list =tagService.findTag1ListByTagName(tagName);
        return ResultUtil.ok(list);
    }

    /**
     * @return
     * @Author lyh
     * @Description tag2列表
     * @Param
     * @Date 2022/4/26
     **/
    @GetMapping("getTag2List")
    public Result<List<TagVo>> getTag2List(String tagName){
        List<TagVo> list =tagService.findTag2ListByTagName(tagName);
        return ResultUtil.ok(list);
    }

    /**
    * @return
    * @Author lyh
    * @Description  发布提问中显示所有标签2
    * @Param
    * @Date 2022/4/26
    **/
    @GetMapping("getSubTagList")
    public Result<List<SubTag>> getSubTagList(){
        List<SubTag> list = tagService.findSubTagList();
        return ResultUtil.ok(list);
    }
}
