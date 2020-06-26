package com.frank.cache.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.frank.cache.entity.Article;

import java.util.List;

/**
 * @author 小石潭记
 * @date 2020/6/26 11:23
 * @Description: ${todo}
 */
public interface ArticleMapper extends BaseMapper<Article> {
    /**
     * 自定义sql查询
     */
    List<Article> getAllArticles();
}
