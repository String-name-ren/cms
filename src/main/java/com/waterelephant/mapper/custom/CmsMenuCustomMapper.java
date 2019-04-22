package com.waterelephant.mapper.custom;

import com.waterelephant.dto.CmsMenuCondDto;
import com.waterelephant.entity.CmsMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: cms
 * @Description:栏目自定义方法
 * @Date: 2019/3/13 11:14
 * @Author: 齐腾杰
 */
public interface CmsMenuCustomMapper {

    /**
     * 根据父id查询子集合
     * @param parentId
     * @return
     */
    List<CmsMenu> findMenuListByParentId(@Param("parentId") Integer parentId);

    /**
     * 根据条件查询栏目
     * @param menuDto
     * @return
     */
    List<CmsMenu> selectMenuByCond(CmsMenuCondDto menuDto);

    int updateNum(Integer id);

    int getNum(Integer id);

    int deleteSubMenu(Integer parentId);

    public CmsMenu getMenuByName(@Param("menuName") String menuName);

}
