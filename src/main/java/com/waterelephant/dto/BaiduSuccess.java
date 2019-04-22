package com.waterelephant.dto;

import lombok.Data;

import java.util.List;

/**
 * 描述
 *
 * @author: renpenghui
 * @date: 2019-03-28 11:26
 **/
@Data
public class BaiduSuccess {

    private int success;

    private int remain;

    private List<String> not_same_site;

    private List<String> not_valid;

}
