package com.frank.cache.controller;

import com.frank.cache.entity.Article;
import com.frank.cache.entity.ResultVo;
import com.frank.cache.mapper.ArticleMapper;
import com.frank.cache.service.ArticleRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 小石潭记
 * @date 2020/6/26 11:30
 * @Description: ${todo}
 */
@RestController
@Slf4j
public class ArticleController {

    @Autowired
    private ArticleRedisService service;

    @Autowired
    ArticleMapper articleMapper;

    /**
     * 添加文章
     */
    @PostMapping("/add")
    public ResultVo addArticle(@RequestBody Article article) {
        log.info(article.toString());
        Integer result = service.addArticle(article);
        if (result >= 0) {
            return ResultVo.success(article);
        }
        return ResultVo.fail();
    }

    /**
     * 获取一篇文章
     */
    @GetMapping("/get")
    public ResultVo getArticle(@RequestParam("id") Integer id) {
        Long start = System.currentTimeMillis();
        Article article = service.getArticle(id);
        Long end = System.currentTimeMillis();
        log.info("耗时："+ (end-start));
        if (null != article)
            return ResultVo.success(article);
        return ResultVo.fail();
    }

    @GetMapping("/getAll")
    public ResultVo getAllArticles() {
        Long start = System.currentTimeMillis();
        Object articles = service.getAllArticles();
        Long end = System.currentTimeMillis();
        log.info("耗时："+(end-start));
        if (null != articles) {
            return ResultVo.success(articles);
        }
        return ResultVo.fail();
    }

    /**
     * 更新一篇文章
     */
    @PostMapping("/update")
    public ResultVo update(@RequestBody Article article) {
        final Integer result = service.update(article);
        if (result > 0) {
            return ResultVo.success(result);
        } else {
            return ResultVo.fail();
        }
    }

    /**
     * 删除一篇文章
     */
    @GetMapping("/remove")
    public ResultVo remove(@RequestParam("id") Integer id) {
        final Integer result = service.removeArticleById(id);
        if (result > 0) {
            return ResultVo.success(result);
        } else {
            return ResultVo.fail();
        }
    }
}
