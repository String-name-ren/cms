package com.waterelephant.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 描述
 *
 * @author: renpenghui
 * @date: 2019-03-28 11:46
 **/
public enum BaiduTypeEnum {

    save(1, "新增"),
    update(2, "更新"),
    del(3,"删除");
    private final int id;
    private final String name;
    public static final List<BaiduTypeEnum> ALL = Collections.unmodifiableList(Arrays.asList(values()));
    BaiduTypeEnum(Integer id, String name) {
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
