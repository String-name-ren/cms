package com.waterelephant.service;

import com.waterelephant.entity.CmsGlobalField;

public interface CmsGlobalFieldService {

    String getGlobalTitle();
    String getGlobalKeyword();
    String getGlobalDescription();
    CmsGlobalField get();
    int update(CmsGlobalField globalField);
}
