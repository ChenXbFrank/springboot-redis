package com.frank.cache.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 小石潭记
 * @date 2020/6/26 11:20
 * @Description: ${todo}
 */
@Data
public class Article implements Serializable {
    private int id;
    private String title;
    private String content;
    private String author;
    private String fileName;
    private String state;
}
