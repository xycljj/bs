package com.lyh.entity.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author lyh
 * @ClassName PageVo
 * @createTime 2021/12/16 14:33:00
 */
@JsonIgnoreProperties({"size", "prePage", "nextPage", "isFirstPage", "isLastPage", "hasPreviousPage",
        "hasNextPage", "navigatePages", "navigatepageNums", "navigateFirstPage", "navigateLastPage", "startRow", "endRow"})
public class PageVo<T> extends PageInfo<T> {

    public static <T> PageVo<T> emptyPage(int pageIndex, int pageSize) {
        PageVo<T> p = new PageVo<>();
        p.setPageNum(pageIndex);
        p.setPageSize(pageSize);
        return p;
    }

    public static <E> PageVo<E> buildFromPageInfo(PageInfo<E> pageInfo) {
        PageVo<E> vo = new PageVo<>();
        vo.setTotal(pageInfo.getTotal());
        vo.setPageNum(pageInfo.getPageNum());
        vo.setPageSize(pageInfo.getPageSize());
        vo.setPages(pageInfo.getPages());
        vo.setList(pageInfo.getList());
        return vo;
    }

    public static <E> PageVo<E> buildFromPageInfo(PageInfo<?> pageInfo, List<E> list) {

        PageVo<E> vo = new PageVo<>();
        vo.setTotal(pageInfo.getTotal());
        vo.setPageNum(pageInfo.getPageNum());
        vo.setPageSize(pageInfo.getPageSize());
        vo.setPages(pageInfo.getPages());
        vo.setList(list);
        return vo;
    }
}
