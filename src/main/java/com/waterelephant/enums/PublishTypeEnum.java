package com.waterelephant.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @program: cms
 * @Description:文章发布类型
 * @Date: 2019/3/13 19:07
 * @Author: 齐腾杰
 */
public enum PublishTypeEnum {
    MANUAL(1, "手动发布"),
    AUTOMATIC(2, "自动发布"),
    TIMING(3, "定时发布");

    private final Integer id;
    private final String name;
    public static final List<PublishTypeEnum> ALL = Collections.unmodifiableList(Arrays.asList(values()));

    PublishTypeEnum(Integer id, String name) {
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
