package com.qulei.vo;

import com.qulei.bean.entity.Article;
import com.qulei.bean.entity.Label;

import java.util.List;

public class ArticleVO {
    private Article article;
    private List<Label> labelList;

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public List<Label> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<Label> labelList) {
        this.labelList = labelList;
    }
}
