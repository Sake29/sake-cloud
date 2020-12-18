package com.sake.sakecloud.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * 文件树目录节点
 *
 * @author WSY
 * @date 2020/12/16
 */
@Data
public class ZtreeNodeVo {
    private String id;
    private String pid;
    private String name;
    private List<ZtreeNodeVo> children;


}