package com.waterelephant.service;

import com.waterelephant.common.utils.Tree;
import com.waterelephant.common.utils.TreeModel;
import com.waterelephant.entity.CmsMenu;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @program: cms
 * @Description:
 * @Date: 2019/3/13 10:52
 * @Author: 齐腾杰
 */
public interface CmsMenuService {

    List<Tree> getMenuTree(Integer parentId);

    /**
     * 查询栏目树
     *
     * @param isRecursion 是否使用递归
     * @return
     */
    List<TreeModel> findMenuTree(Boolean isRecursion);

    /**
     * 根据父id查询子集合
     *
     * @param parentId
     * @return
     */
    List<CmsMenu> findMenuListByParentId(Integer parentId);


    /**
     * 根据主键查询栏目对象
     *
     * @param id
     * @return
     */
    CmsMenu selectByPrimaryKey(Integer id);


    /**
     * 保存
     *
     * @param menu
     */
    void insertSelective(CmsMenu menu);

    /**
     * 更新
     *
     * @param menu
     * @return
     */
    int updateByPrimaryKeySelective(CmsMenu menu);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    int deleteMenuByLogic(Integer id);

    //获取栏目的数字
    int updateMenuNum(Integer id);


    public CmsMenu getMenuByName(String menuName);
}
