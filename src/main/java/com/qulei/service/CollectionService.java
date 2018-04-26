package com.qulei.service;

import com.qulei.bean.app.ResultCode;
import com.qulei.bean.dto.ArticleDto;
import com.qulei.bean.entity.art_Collection;
import com.qulei.common.enums.ArticleTypeEnum;
import com.qulei.common.enums.ExceptionEnum;
import com.qulei.common.exception.MyException;
import com.qulei.common.util.AuthorizeUtil;
import com.qulei.common.util.CommonUtils;
import com.qulei.dao.ArticleDao;
import com.qulei.dao.CollectionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CollectionService {

    @Autowired
    private AuthorizeUtil authorizeUtil;

    @Autowired
    private CollectionDao collectionDao;

    @Autowired
    private ArticleDao articleDao;

    /**
     * 添加文集
     * @param artCollection
     */
    @Transactional
    public void add(art_Collection artCollection, String token) {
        String user_id = artCollection.getUser_id();
        //鉴权
        if (user_id == "" || token == "" || user_id==null || token == null){
            throw new MyException(ResultCode.AUTHORIZATION_ERROR,"Connection timed out,please login again");
        }
        if (!authorizeUtil.verify(user_id,token)){
            throw new MyException(ResultCode.AUTHORIZATION_ERROR,"Connection timed out,please login again");
        }

        String id = CommonUtils.createUUID();
        artCollection.setId(id);
        int i = collectionDao.add(artCollection);
        if (i==0){
            throw new MyException(ExceptionEnum.COLLECTION_ADD_ERROR);
        }
    }

    /**
     * 修改文集
     */
    @Transactional
    public void edit(art_Collection artCollection, String token){
        String user_id = artCollection.getUser_id();
        //鉴权
        if (user_id == "" || token == "" || user_id==null || token == null){
            throw new MyException(ResultCode.AUTHORIZATION_ERROR,"Connection timed out,please login again");
        }
        if (!authorizeUtil.verify(user_id,token)){
            throw new MyException(ResultCode.AUTHORIZATION_ERROR,"Connection timed out,please login again");
        }

        int i = collectionDao.update(artCollection);
        if (i==0){
            throw new MyException(ExceptionEnum.COLLECTION_UPDATE_ERROR);
        }
    }


    /**
     * 获取文集列表
     * @return
     */
    @Transactional
    public List<art_Collection> getList(String user_id, String token) {
        //鉴权
        if (user_id == "" || token == "" || user_id==null || token == null){
            throw new MyException(ResultCode.AUTHORIZATION_ERROR,"Connection timed out,please login again");
        }
        if (!authorizeUtil.verify(user_id,token)){
            throw new MyException(ResultCode.AUTHORIZATION_ERROR,"Connection timed out,please login again");
        }

        List<art_Collection> artCollectionList = collectionDao.getList(user_id);
        return artCollectionList;
    }


    /**
     * 删除文集
     * @param artCollection
     * @param token
     */
    @Transactional
    public void delete(art_Collection artCollection, String token) {
        String user_id = artCollection.getUser_id();
        //鉴权
        if (user_id == "" || token == "" || user_id==null || token == null){
            throw new MyException(ResultCode.AUTHORIZATION_ERROR,"Connection timed out,please login again");
        }
        if (!authorizeUtil.verify(user_id,token)){
            throw new MyException(ResultCode.AUTHORIZATION_ERROR,"Connection timed out,please login again");
        }

        //文章放入回收站
        String collection_id = artCollection.getId();
        ArticleDto articleDto = new ArticleDto();
        articleDto.setCollection_id(collection_id);
        articleDto.setType(ArticleTypeEnum.RECYCLE.getCode());
        int i = articleDao.changeType(articleDto);
        if (i==0){
            throw new MyException(ExceptionEnum.DELETE_ERROR);
        }

        //删除文集
        int j = collectionDao.delete(artCollection);
        if (j==0){
            throw new MyException(ExceptionEnum.DELETE_ERROR);
        }
    }
}
