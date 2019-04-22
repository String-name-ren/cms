package com.waterelephant.common.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果
 */
@Data
public class PageResultDto implements Serializable {
    private static final long serialVersionUID = -5409126181391187528L;

    /**
     * 总页数
     */
    private Integer pageSize;
    /**
     * 总个数
     */
    private Integer totalCount;
    /**
     * 当前页数
     */
    private Integer pageNo;
    /**
     * 结果列表
     */
    private List result;
}
