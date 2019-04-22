package com.waterelephant.service;

import com.waterelephant.entity.SysUuid;

public interface SysUuidService {

    int insert(Long userId,String uuid);

    String getUuid(Long userId);

}
