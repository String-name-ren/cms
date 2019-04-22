/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.waterelephant.common.utils;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hui Wang
 */
@Data
public class TreeModel {

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
    private String parentId;
    /**
     * 打开的样式
     */
    private String iconOpen;
    /**
     * 关闭的样式
     */
    private String iconClose;
    /**
     * 是否选中
     */
    private String checked;
    /**
     * 是否展开
     */
    private String open;
    /**
     * 是否是父节点
     */
    private String isParent;
    /**
     * 图片路径
     */
    private String imgPath;
    /**
     * url
     */
    private String url;
    /**
     * 打开url位置
     */
    private String target;
    /**
     * 子节点
     */
    private List<TreeModel> children = new ArrayList<TreeModel>();

    //目录
    private String directory;
}
