package com.qulei.dao;

import com.qulei.bean.dto.ArticleDto;
import com.qulei.bean.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleDao {
    //保存文章
    int saveArticle(ArticleDto article);

    //根据id查询文章
    Article getArticleById(@Param("id") String id,@Param("user_id")String user_id);

    //根据类型和文集搜索文张列表
    List<Article> getArticleListByType(ArticleDto articleDto);

    //修改文章
    int edit(ArticleDto articleDto);

    //修改type
    int changeType(ArticleDto articleDto);

    //移动文集
    int move(ArticleDto articleDto);

    List<Article> getArticleListByKeyWord(ArticleDto articleDto);

    int getTotalSizeByType(ArticleDto articleDto);

    int getTotalSizeByKeyword(ArticleDto articleDto);
}
