package com.qulei.vo;


import java.util.List;

public class ArticleListVO {
    private List<ArticleVO> articleList;
    private int totalPage;

    public List<ArticleVO> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<ArticleVO> articleList) {
        this.articleList = articleList;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
