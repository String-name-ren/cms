package com.waterelephant.service.impl;

import com.waterelephant.common.utils.CommUtils;
import com.waterelephant.common.utils.Tree;
import com.waterelephant.common.utils.TreeModel;
import com.waterelephant.dto.CmsMenuCondDto;
import com.waterelephant.entity.CmsMenu;
import com.waterelephant.enums.DelFlagEnum;
import com.waterelephant.mapper.CmsMenuMapper;
import com.waterelephant.mapper.custom.CmsMenuCustomMapper;
import com.waterelephant.service.CmsMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: cms
 * @Description:
 * @Date: 2019/3/13 10:53
 * @Author: 齐腾杰
 */
@Slf4j
@Service
public class CmsMenuServiceImpl implements CmsMenuService {

    @Autowired
    CmsMenuMapper cmsMenuMapper;

    @Autowired
    CmsMenuCustomMapper cmsMenuCustomMapper;

    @Override
    public List<Tree> getMenuTree(Integer parentId) {
        //根据父id查询子集合
        List<CmsMenu> menuList = this.findMenuListByParentId(parentId);
        List<Tree> treeList = null;
        if (!CommUtils.isNull(menuList)){
            treeList = new ArrayList<Tree>();
            //遍历子集合中的元素，以元素id为父id，查询子集合
            for (CmsMenu menu:menuList){
                List<Tree> children = getMenuTree(menu.getId());
                Tree tree = new Tree();
                tree.setId(String.valueOf(menu.getId()));
                tree.setPId(String.valueOf(parentId));
                tree.setName(menu.getName());
                tree.setMenuName(menu.getDirectory());
                tree.setChildren(children == null ? new ArrayList<Tree>() : children);
                treeList.add(tree);
            }
        }
        return treeList;
    }

    @Override
    public List<TreeModel> findMenuTree(Boolean isRecursion) {
        List<TreeModel> menuTree = null;
        if (isRecursion){
            menuTree = this.findMenuTreeByParentId(0);
        }else{
            List<CmsMenu> menuList = cmsMenuCustomMapper.selectMenuByCond(new CmsMenuCondDto());
            if (!CommUtils.isNull(menuList)){
                menuTree= new ArrayList<>();
                for (CmsMenu menu:menuList){
                    TreeModel tree = new TreeModel();
                    tree.setId(String.valueOf(menu.getId()));
                    tree.setName(menu.getName());
                    tree.setParentId(menu.getParentId()+"");
                    tree.setDirectory(menu.getDirectory());
                    menuTree.add(tree);
                }
            }
        }
        return menuTree;
    }

    @Override
    public List<CmsMenu> findMenuListByParentId(Integer parentId) {
        return cmsMenuCustomMapper.findMenuListByParentId(parentId);
    }

    @Override
    public CmsMenu selectByPrimaryKey(Integer id) {
        return cmsMenuMapper.selectByPrimaryKey(id);
    }

    @Override
    public void insertSelective(CmsMenu menu) {
        cmsMenuMapper.insertSelective(menu);
    }

    @Override
    public int updateByPrimaryKeySelective(CmsMenu menu) {
        return cmsMenuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public int deleteMenuByLogic(Integer id) {
        CmsMenu menu = new CmsMenu();
        menu.setId(id);
        menu.setDeleted(DelFlagEnum.YES.getId());
        int result = cmsMenuMapper.updateByPrimaryKeySelective(menu);
        if(result>0){
            //删除子栏目
            cmsMenuCustomMapper.deleteSubMenu(id);
        }
        return cmsMenuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public int updateMenuNum(Integer id) {
        //先做更新
        cmsMenuCustomMapper.updateNum(id);
        //查询返回
        return cmsMenuCustomMapper.getNum(id);
    }

    @Override
    public CmsMenu getMenuByName(String menuName) {
        return cmsMenuCustomMapper.getMenuByName(menuName);
    }

    /**
     * 递归查询树
     * @param parentId
     * @return
     */
    private List<TreeModel> findMenuTreeByParentId(Integer parentId){
        //根据父id查询子集合
        List<CmsMenu> menuList = this.findMenuListByParentId(parentId);
        List<TreeModel> treeList = null;
        if (!CommUtils.isNull(menuList)){
            treeList = new ArrayList<TreeModel>();
            //遍历子集合中的元素，以元素id为父id，查询子集合
            for (CmsMenu menu:menuList){
                List<TreeModel> children = findMenuTreeByParentId(menu.getId());
                TreeModel tree = new TreeModel();
                tree.setId(String.valueOf(menu.getId()));
                tree.setParentId(String.valueOf(parentId));
                tree.setName(menu.getName());
                tree.setChildren(children);
                treeList.add(tree);
            }
        }
        return treeList;
    }


}
