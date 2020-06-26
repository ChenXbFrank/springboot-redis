package com.frank.cache.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.frank.cache.entity.Article;
import com.frank.cache.mapper.ArticleMapper;
import com.frank.cache.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 小石潭记
 * @date 2020/6/26 11:27
 * @Description: service层引入cache缓存
 *               使用redis的set方式设置缓存
 */
@Service
@Slf4j
public class ArticleRedisService {

    private AtomicInteger count =new AtomicInteger(0);

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 增加一篇文章 每次就进行缓存
     * @return
     */
    public Integer addArticle(Article article){
        int insert = articleMapper.insert(article);
        if (insert > 0) {
            return insert;
        }
        return null;
    }

    /**
     * 获取文章  以传入的id为键，当state为0的时候不进行缓存
     * @param id 文章id
     * @return
     */
    public Article getArticle(Integer id) {
        String key = "article_" + id;
        boolean hasKey = redisUtil.exists(key);
        if (hasKey){
            String articleStr = redisUtil.get(key);
            JSONObject jsonObject = JSONObject.parseObject(articleStr);
            Article article = jsonObject.toJavaObject(Article.class);
            log.info("从缓存redis中获取的数据：{}", article);
            return article;
        }
        try {
            //模拟耗时操作
            Thread.sleep(5000);
            Article article = articleMapper.selectById(id);
            log.info("--执行数据库查询操作"+count.incrementAndGet()+"次"+"id:"+id);
            // 将查询的结果保存至redis缓存里
            redisUtil.set(key, article);
            return article;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 自定义的sql方法
     * @return
     */
    public Object getAllArticles() {
        try {
            String key = "getAllArticles";
            boolean hasKey = redisUtil.exists(key);
            if (hasKey) {
                String str = redisUtil.get(key);
                return str;
            }
            //模拟耗时操作
            Thread.sleep(5000);
            List<Article> allArticles = articleMapper.getAllArticles();
            redisUtil.set(key, allArticles);
            return allArticles;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过id更新内容 清除以id作为键的缓存
     */
    public Integer update(Article article) {
        try {
            log.info("==========数据库中更新文章信息=========");
            UpdateWrapper<Article> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", 1);
            articleMapper.update(article, updateWrapper);
            String key = "article_" + article.getId();
            boolean hasKey = redisUtil.exists(key);
            if (hasKey) {
                redisUtil.remove(key);
                log.info("删除缓存中的key=========>" + key);
            }
            // 再将更新后的数据加入缓存
            redisUtil.set(key,  JSON.toJSONString(article));
            return 1;
        }catch (Exception e) {
            return 0;
        }
    }

    /**
     * 通过id移除文章
     * @param id  清除以id作为键的缓存
     * @return
     */
    public Integer removeArticleById(Integer id){
        int result = articleMapper.deleteById(id);
        log.info("执行删除操作,id:"+id);
        String key = "article_" + id;
        redisUtil.remove(key);
        return result;
    }
}
