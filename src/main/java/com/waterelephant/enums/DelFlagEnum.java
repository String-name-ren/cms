package com.waterelephant.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum DelFlagEnum {
    NO(0, "未删除"),
    YES(1, "已删除");
    private final Integer id;
    private final String name;
    public static final List<DelFlagEnum> ALL = Collections.unmodifiableList(Arrays.asList(values()));
    DelFlagEnum(Integer id, String name) {
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
