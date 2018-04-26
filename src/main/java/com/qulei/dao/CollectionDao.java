package com.qulei.dao;

import com.qulei.bean.entity.art_Collection;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollectionDao {
    int add(art_Collection artCollection);

    int update(art_Collection artCollection);

    List<art_Collection> getList(@Param("user_id") String user_id);

    int delete(art_Collection artCollection);

    String getNameById(@Param("id") String collection_id);
}
