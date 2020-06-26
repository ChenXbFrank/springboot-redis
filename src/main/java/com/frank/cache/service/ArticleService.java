package com.frank.cache.service;

import com.frank.cache.entity.Article;
import com.frank.cache.mapper.ArticleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 小石潭记
 * @date 2020/6/26 11:27
 * @Description: service层引入cache缓存
 */
@Service
@CacheConfig(cacheNames = "articleCache")
@Slf4j
public class ArticleService {

    private AtomicInteger count =new AtomicInteger(0);

    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 增加一篇文章 每次就进行缓存
     * @return
     */
    @CachePut
    public Integer addArticle(Article article){
        int insert = articleMapper.insert(article);
        if (insert>0) {
            return insert;
        }
        return null;
    }

    /**
     * 获取文章  以传入的id为键，当state为0的时候不进行缓存
     * @param id 文章id
     * @return
     */
    @Cacheable(key = "#id",unless = "#result.state==0")
    public Article getArticle(Integer id) {
        try {
            //模拟耗时操作
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Article article = articleMapper.selectById(id);
        log.info("--执行数据库查询操作"+count.incrementAndGet()+"次"+"id:"+id);
        return article;
    }

    /**
     * 自定义的sql方法
     * @return
     */
    @Cacheable()
    public List<Article> getAllArticles() {
        try {
            //模拟耗时操作
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Article> allArticles = articleMapper.getAllArticles();
        return allArticles;
    }

    /**
     * 通过id更新内容 清除以id作为键的缓存
     *
     * @param id
     * @return
     */
    @CacheEvict(key = "#id")
    public Integer updateContentById(String content, Integer id) {
        Article article = articleMapper.selectById(id);
        article.setContent(content);
        int result = articleMapper.updateById(article);
        log.info("--执行更新操作id:--"+id);
        return result;
    }

    /**
     * 通过id移除文章
     * @param id  清除以id作为键的缓存
     * @return
     */
    @CacheEvict(key = "#id")
    public Integer removeArticleById(Integer id){
        int result = articleMapper.deleteById(id);
        log.info("执行删除操作,id:"+id);
        return result;
    }
}
