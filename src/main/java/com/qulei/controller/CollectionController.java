package com.qulei.controller;


import com.qulei.bean.entity.art_Collection;
import com.qulei.common.util.ResultVOUtil;
import com.qulei.service.CollectionService;
import com.qulei.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collection")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    /**
     * 增加文集
     */
    @PostMapping("/add")
    public ResultVO add(@RequestBody art_Collection art_Collection, @RequestParam("token")String token){
        collectionService.add(art_Collection,token);
        return ResultVOUtil.success();
    }


    /**
     * 删除文集
     */
    @PostMapping("/delete")
    public ResultVO delete(@RequestBody art_Collection artCollection, @RequestParam("token")String token){
        collectionService.delete(artCollection,token);
        return ResultVOUtil.success();
    }


    /**
     * 查询文集
     */
    @GetMapping("/getlist")
    public ResultVO getlist(@RequestParam("user_id")String user_id,@RequestParam("token")String token){
        List<art_Collection> artCollectionList = collectionService.getList(user_id,token);
        return ResultVOUtil.success(artCollectionList);
    }

    /**
     * 修改文集
     */
    @PostMapping("edit")
    public ResultVO edit(@RequestBody art_Collection artCollection, @RequestParam("token")String token){
        return ResultVOUtil.success();
    }
}
