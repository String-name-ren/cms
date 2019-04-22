package com.waterelephant.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CmsMenuCondDto {

    private Integer id;

    private String name;

    private Integer parentId;

    private String title;

    private String keywords;

    private String description;

    private String directory;

    private Byte deleted;


}