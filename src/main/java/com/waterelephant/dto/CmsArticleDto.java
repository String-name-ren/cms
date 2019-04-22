package com.waterelephant.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("文章实体")
public class CmsArticleDto {

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("栏目ID")
    private Integer menuId;

    @ApiModelProperty("栏目名称")
    private String menuName;

    @ApiModelProperty("作者")
    private String author;

    @ApiModelProperty("摘要")
    private String abstracts;

    @ApiModelProperty("来源")
    private String source;

    @ApiModelProperty("关键字")
    private String keyWord;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("删除状态")
    private Integer delFlag;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("发布时间")
    private Date deployTime;

    @ApiModelProperty("上传地址")
    private String uploadAddress;

    @ApiModelProperty("页码")
    private Integer pageNum;

    @ApiModelProperty("页大小")
    private Integer pageSize;

    @ApiModelProperty("浏览次数")
    private Long browseCount;

    @ApiModelProperty("发布类型")
    private Integer publishType;

    @ApiModelProperty("seo标题")
    private String seoTitle;

    @ApiModelProperty("seo描述")
    private String seoDescription;

    @ApiModelProperty("第一个图片地址")
    private String imgurl;

}