package com.waterelephant.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @program: cms
 * @Description:
 * @Date: 2019/3/8 11:45
 * @Author: 齐腾杰
 */
public enum ArticleStatusEnum {
    UNPUBLISHED(1, "未发布"),
    DRAFT(2, "草稿"),
    PUBLISHED(3, "已发布");

    private final Integer id;
    private final String name;
    public static final List<ArticleStatusEnum> ALL = Collections.unmodifiableList(Arrays.asList(values()));

    ArticleStatusEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
