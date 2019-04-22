package com.waterelephant.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CmsMenu {
    private Integer id;

    private String name;

    private Integer parentId;

    private String title;

    private String keywords;

    private String description;

    private String directory;

    private String url;

    private Integer num;

    private Integer deleted;

    private Date createTime;

    private Date updateTime;

    private CmsMenu parent;

}