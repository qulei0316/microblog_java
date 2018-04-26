package com.qulei.service;

import com.qulei.bean.app.ResultCode;
import com.qulei.bean.dto.ArticleDto;
import com.qulei.bean.entity.Article;
import com.qulei.bean.entity.Label;
import com.qulei.common.enums.ExceptionEnum;
import com.qulei.common.exception.MyException;
import com.qulei.common.util.AuthorizeUtil;
import com.qulei.common.util.CommonUtils;
import com.qulei.dao.ArticleDao;
import com.qulei.dao.CollectionDao;
import com.qulei.dao.LabelDao;
import com.qulei.vo.ArticleListVO;
import com.qulei.vo.ArticleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private AuthorizeUtil authorizeUtil;

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private CollectionDao collectionDao;

    /**
     * 保存文章或草稿
     */
    @Transactional
    public void save(ArticleDto article, String token) {
        String user_id = article.getUser_id();
        //鉴权
        if (user_id == "" || token == "" || user_id==null || token == null){
            throw new MyException(ResultCode.AUTHORIZATION_ERROR,"Connection timed out,please login again");
        }
        if (!authorizeUtil.verify(user_id,token)){
            throw new MyException(ResultCode.AUTHORIZATION_ERROR,"Connection timed out,please login again");
        }

        if (CommonUtils.isStringEmpty(article.getTitle())){
            throw new MyException(ExceptionEnum.TITLE_EMPTY_ERROR);
        }

        if(article.getTitle().length()>32){
            throw new MyException(ExceptionEnum.TITLE_TOO_LONG_ERROR);
        }

        if (article.getCollection_id()!=null){
            String collection_name = collectionDao.getNameById(article.getCollection_id());
            article.setCollection_name(collection_name);
        }

        //保存文章
        String id = CommonUtils.createUUID();
        article.setId(id);
        article.setCreate_time(System.currentTimeMillis());
        int i = articleDao.saveArticle(article);
        if (i==0){
            throw new MyException(ExceptionEnum.SAVE_ERROR);
        }

        //保存标签
        List<String> labelList = article.getLabel_list();
        if (labelList.size()!=0 && labelList!=null){
            for(String label : labelList){
                Label labelDto = new Label();
                String label_id = CommonUtils.createUUID();
                labelDto.setArticle_id(id);
                labelDto.setId(label_id);
                labelDto.setContent(label);
                int j = labelDao.addlabel(labelDto);
                if (j==0){
                    throw new MyException(ExceptionEnum.SAVE_ERROR);
                }
            }
        }
    }

    /**
     * 根据id查询文章
     * @param id
     * @param token
     * @return
     */
    @Transactional
    public ArticleVO getArticleById(String id, String token, String user_id) {
        ArticleVO articleVO = new ArticleVO();
        //鉴权
        if (user_id == "" || token == "" || user_id==null || token == null){
            throw new MyException(ResultCode.AUTHORIZATION_ERROR,"Connection timed out,please login again");
        }
        if (user_id == "" || token == "" || user_id==null || token == null){
            throw new MyException(ResultCode.AUTHORIZATION_ERROR,"Connection timed out,please login again");
        }
        if (!authorizeUtil.verify(user_id,token)){
            throw new MyException(ResultCode.AUTHORIZATION_ERROR,"Connection timed out,please login again");
        }

        //查询文章
        Article article = articleDao.getArticleById(id,user_id);
        if (article==null){
            throw new MyException(ExceptionEnum.ARTICLE_OPEN_ERROR);
        }
        //查询标签
        List<Label> labelList = labelDao.getLabelList(id);

        articleVO.setArticle(article);
        articleVO.setLabelList(labelList);

        return articleVO;
    }


    /**
     * 根据类型获取文章列表
     * @return
     */
    @Transactional
    public ArticleListVO getArticleListByType(ArticleDto articleDto,String token) {
        ArticleListVO vo = new ArticleListVO();
        String user_id = articleDto.getUser_id();
        //鉴权
        if (user_id == "" || token == "" || user_id==null || token == null){
            throw new MyException(ResultCode.AUTHORIZATION_ERROR,"Connection timed out,please login again");
        }
        if (!authorizeUtil.verify(user_id,token)){
            throw new MyException(ResultCode.AUTHORIZATION_ERROR,"Connection timed out,please login again");
        }

        //设置分页
        articleDto.setStartIndex((articleDto.getPageIndex()-1)*8);
        List<ArticleVO> articleVOList = new ArrayList<>();
        List<Article> articleList = articleDao.getArticleListByType(articleDto);
        articleList.stream().forEach(e ->{
            ArticleVO articleVO = new ArticleVO();
            String article_id = e.getId();
            List<Label> labelList = labelDao.getLabelList(article_id);
            articleVO.setArticle(e);
            articleVO.setLabelList(labelList);
            articleVOList.add(articleVO);
        });
        int totalSize = articleDao.getTotalSizeByType(articleDto);
        int re = totalSize%8;
        int pageSize;
        if (totalSize <= 8){
            pageSize = 1;
        }else if (re == 0){
            pageSize = totalSize/8;
        }else {
            pageSize = totalSize/8 + 1;
        }

        vo.setTotalPage(pageSize);
        vo.setArticleList(articleVOList);
        return vo;
    }

    /**
     * 根据关键字搜索
     * @param articleDto
     * @param token
     * @return
     */
    @Transactional
    public ArticleListVO getArticleListBykeywords(ArticleDto articleDto, String token) {
        ArticleListVO vo = new ArticleListVO();
        String user_id = articleDto.getUser_id();
        //鉴权
        if (user_id == "" || token == "" || user_id==null || token == null){
            throw new MyException(ResultCode.AUTHORIZATION_ERROR,"Connection timed out,please login again");
        }
        if (!authorizeUtil.verify(user_id,token)){
            throw new MyException(ResultCode.AUTHORIZATION_ERROR,"Connection timed out,please login again");
        }
        if (CommonUtils.isStringEmpty(articleDto.getKeywords())){
            throw new MyException(ExceptionEnum.KEYWORD_EMPTY_ERROR);
        }

        //设置分页
        articleDto.setStartIndex((articleDto.getPageIndex()-1)*8);
        List<ArticleVO> articleVOList = new ArrayList<>();
        List<Article> articleList = articleDao.getArticleListByKeyWord(articleDto);
        articleList.stream().forEach(e ->{
            ArticleVO articleVO = new ArticleVO();
            String article_id = e.getId();
            List<Label> labelList = labelDao.getLabelList(article_id);
            articleVO.setArticle(e);
            articleVO.setLabelList(labelList);
            articleVOList.add(articleVO);
        });
        int totalSize = articleDao.getTotalSizeByKeyword(articleDto);
        int re = totalSize%8;
        int pageSize;
        if (totalSize <= 8){
            pageSize = 1;
        }else if (re == 0){
            pageSize = totalSize/8;
        }else {
            pageSize = totalSize/8 + 1;
        }

        vo.setTotalPage(pageSize);
        vo.setArticleList(articleVOList);
        return vo;
    }


    /**
     * 修改文章
     * @param articleDto
     * @param token
     */
    @Transactional
    public void edit(ArticleDto articleDto, String token) {
        String user_id = articleDto.getUser_id();
        //鉴权
        if (user_id == "" || token == "" || user_id==null || token == null){
            throw new MyException(ResultCode.AUTHORIZATION_ERROR,"Connection timed out,please login again");
        }
        if (!authorizeUtil.verify(user_id,token)){
            throw new MyException(ResultCode.AUTHORIZATION_ERROR,"Connection timed out,please login again");
        }

        if (CommonUtils.isStringEmpty(articleDto.getTitle())){
            throw new MyException(ExceptionEnum.TITLE_EMPTY_ERROR);
        }
        if (CommonUtils.isStringEmpty(articleDto.getContent())){
            throw new MyException(ExceptionEnum.CONTENT_EMPTY_ERROR);
        }
        if (CommonUtils.isStringEmpty(articleDto.getCollection_id())){
            throw new MyException(ExceptionEnum.COLLECTION_EMPTY_ERROR);
        }

        String article_id = articleDto.getId();

        //修改文章
        int i = articleDao.edit(articleDto);
        if (i==0){
            throw new MyException(ExceptionEnum.ARTICLE_EDIT_ERROR);
        }

        //修改标签
        List<Label> labelList = labelDao.getLabelList(article_id);
        if (labelList!=null && labelList.size()>0){
            int j = labelDao.delete(article_id);
            if (j==0){
                throw new MyException(ExceptionEnum.ARTICLE_EDIT_ERROR);
            }
        }
        List<String> labelListDto = articleDto.getLabel_list();
        if (labelListDto.size()!=0 && labelList!=null){
            for(String label : labelListDto){
                Label labelDto = new Label();
                String label_id = CommonUtils.createUUID();
                labelDto.setArticle_id(article_id);
                labelDto.setId(label_id);
                labelDto.setContent(label);
                int j = labelDao.addlabel(labelDto);
                if (j==0){
                    throw new MyException(ExceptionEnum.ARTICLE_EDIT_ERROR);
                }
            }
        }
    }



    /**
     * 修改type
     * @param articleDto
     * @param token
     */
    @Transactional
    public void changeType(ArticleDto articleDto, String token) {
        String user_id = articleDto.getUser_id();
        //鉴权
        if (user_id == "" || token == "" || user_id==null || token == null){
            throw new MyException(ResultCode.AUTHORIZATION_ERROR,"Connection timed out,please login again");
        }
        if (!authorizeUtil.verify(user_id,token)){
            throw new MyException(ResultCode.AUTHORIZATION_ERROR,"Connection timed out,please login again");
        }

        int i = articleDao.changeType(articleDto);
        if (i==0){
            throw new MyException(ExceptionEnum.ACTION_ERROR);
        }
    }


    /**
     * 移动文集
     * @param articleDto
     * @param token
     */
    @Transactional
    public void move(ArticleDto articleDto, String token) {
        String user_id = articleDto.getUser_id();
        //鉴权
        if (user_id == "" || token == "" || user_id==null || token == null){
            throw new MyException(ResultCode.AUTHORIZATION_ERROR,"Connection timed out,please login again");
        }
        if (!authorizeUtil.verify(user_id,token)){
            throw new MyException(ResultCode.AUTHORIZATION_ERROR,"Connection timed out,please login again");
        }

        int i = articleDao.move(articleDto);
        if (i==0){
            throw new MyException(ExceptionEnum.ACTION_ERROR);
        }
    }
}
