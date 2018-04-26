package com.qulei.controller;

import com.qulei.bean.dto.ArticleDto;
import com.qulei.bean.entity.Article;
import com.qulei.common.util.ResultVOUtil;
import com.qulei.service.ArticleService;
import com.qulei.vo.ArticleListVO;
import com.qulei.vo.ArticleVO;
import com.qulei.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 保存文章或草稿
     */
    @PostMapping("/save")
    public ResultVO save(@RequestBody ArticleDto article, @RequestParam("token") String token){
        articleService.save(article,token);
        return ResultVOUtil.success();
    }

    /**
     * 根据id查询文章
     */
    @GetMapping("/findbyid")
    public ResultVO findbyid(@RequestParam("id")String id,
                             @RequestParam("user_id")String user_id,
                             @RequestParam("token") String token){
        ArticleVO article = articleService.getArticleById(id,token,user_id);
        return ResultVOUtil.success(article);
    }

    /**
     * 根据关键词搜索文章列表
     */
    @PostMapping("/findbykeyword")
    public ResultVO findbykeyword(@RequestBody ArticleDto articleDto,
                                  @RequestParam("token") String token){
        ArticleListVO articleListVO = articleService.getArticleListBykeywords(articleDto,token);
        return ResultVOUtil.success(articleListVO);
    }

    /**
     * 根据类型搜索文章列表
     */
    @PostMapping("/findbytype")
    public ResultVO findbytype(@RequestBody ArticleDto articleDto,@RequestParam("token")String token){
        ArticleListVO articleListVO  = articleService.getArticleListByType(articleDto,token);
        return ResultVOUtil.success(articleListVO);
    }

    /**
     * 修改文章
     */
    @PostMapping("/edit")
    public ResultVO edit(@RequestBody ArticleDto articleDto, @RequestParam("token")String token){
        articleService.edit(articleDto,token);
        return ResultVOUtil.success();
    }

    /**
     * 移动至回收站或还原等等
     */
    @PostMapping("/changetype")
    public ResultVO changetype(@RequestBody  ArticleDto articleDto, @RequestParam("token")String token){
        articleService.changeType(articleDto,token);
        return ResultVOUtil.success();
    }

    /**
     * 移动文集
     */
    @PostMapping("/move")
    public ResultVO move(@RequestBody ArticleDto articleDto, @RequestParam("token")String token){
        articleService.move(articleDto,token);
        return  ResultVOUtil.success();
    }
}
