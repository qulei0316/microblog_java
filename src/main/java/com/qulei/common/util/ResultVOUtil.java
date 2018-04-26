package com.qulei.common.util;

import com.qulei.vo.ResultVO;

public class ResultVOUtil {


    /**
     * 成功返回
     */
    public static ResultVO success(Object object){
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        return resultVO;
    }

    public static ResultVO success(){
        return success(null);
    }

}
