package com.waterelephant.service.impl;

import com.waterelephant.entity.CmsGlobalField;
import com.waterelephant.mapper.CmsGlobalFieldMapper;
import com.waterelephant.service.CmsGlobalFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述
 *
 * @author: renpenghui
 * @date: 2019-03-22 15:28
 **/
@Service
public class CmsGlobalFieldServiceImpl implements CmsGlobalFieldService {

    @Autowired
    private CmsGlobalFieldMapper globalFieldMapper;

    @Override
    public String getGlobalTitle() {
        CmsGlobalField globalField = globalFieldMapper.getGlobalField();
        return globalField == null ? "" : globalField.getTitle();
    }

    @Override
    public String getGlobalKeyword() {
        CmsGlobalField globalField = globalFieldMapper.getGlobalField();
        return globalField == null ? "" : globalField.getKeyword();
    }

    @Override
    public String getGlobalDescription() {
        CmsGlobalField globalField = globalFieldMapper.getGlobalField();
        return globalField == null ? "" : globalField.getDescription();
    }

    @Override
    public CmsGlobalField get() {
        return globalFieldMapper.getGlobalField();
    }

    @Override
    public int update(CmsGlobalField globalField) {
        return globalFieldMapper.updateByPrimaryKeySelective(globalField);
    }
}
