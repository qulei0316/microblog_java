package com.qulei.dao;

import com.qulei.bean.entity.Label;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LabelDao {
    //新增标签
    int addlabel(Label labelDto);

    //查询标签列表
    List<Label> getLabelList(@Param("article_id") String article_id);

    //删除标签
    int delete(@Param("article_id") String article_id);
}
