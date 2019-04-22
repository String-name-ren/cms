package com.waterelephant.service.impl;

import com.waterelephant.entity.SysUuid;
import com.waterelephant.mapper.SysUuidMapper;
import com.waterelephant.service.SysUuidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述
 *
 * @author: renpenghui
 * @date: 2019-03-20 16:52
 **/
@Service
public class SysUuidServiceImpl implements SysUuidService {

    @Autowired
    private SysUuidMapper uuidMapper;

    @Override
    public int insert(Long userId, String uuid) {
        SysUuid record = new SysUuid();
        record.setUuid(uuid);
        record.setUserId(userId);
        return uuidMapper.insertOrUpdate(record);
    }

    @Override
    public String getUuid(Long userId) {
        return uuidMapper.getUuid(userId);
    }


}
