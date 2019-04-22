package com.waterelephant.common.utils;

import lombok.Data;

import java.util.List;

/**
 * 描述
 *
 * @author: renpenghui
 * @date: 2019-04-02 18:01
 **/
@Data
public class Tree {

    /**
     * 节点ID
     */
    private String id;
    /**
     * 节点名称
     */
    private String name;
    /**
     * 父节点ID
     */
    private String pId;

    private String menuName;

    private List<Tree> children;

}
