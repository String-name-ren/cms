package com.waterelephant.entity;

import lombok.Data;

import java.util.Date;
@Data
public class CmsArticle {
    private Long id;

    private String title;

    private Integer menuId;

    private String menuName;

    private String menuNameCn;

    private String author;

    private String abstracts;

    private String source;

    private String keyWord;

    private Integer status;

    private Integer delFlag;

    private Date createTime;

    private Date updateTime;

    private Date deployTime;

    private String articleAddress;

    private Long browseCount;

    private Integer publishType;

    private String seoTitle;

    private String seoDescription;

    private String imgurl;

    private Integer articleId;

    private Integer successed;

    private String content;

}