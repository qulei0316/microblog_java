package com.qulei.common.exception;


import com.qulei.bean.app.ResultCode;
import com.qulei.common.util.LogUtil;
import com.qulei.vo.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger;
    public GlobalExceptionHandler() {
        //初始化日志
        logger = LoggerFactory.getLogger("microblog");
    }

    /**
     * 自定义错误处理
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(MyException.class)
    @ResponseBody
    public ResultVO<String> handleryException(HttpServletRequest request, MyException ex){
        //打印错误日志
        String exceptionInfo = "==========================================错误信息=======================================\n";
        exceptionInfo += "请求出错的api=>"+request.getMethod()+"    "+request.getRequestURI()+"\n";
        exceptionInfo += LogUtil.getExceptionStackTrace(ex);
        logger.error(exceptionInfo);

        //将错误信息返回给前端
        ResultVO<String> resultData = new ResultVO<>();
        resultData.setCode(ex.getCode());
        resultData.setMsg(ex.getMessage());
        return resultData;
    }

    /**
     * 其他错误处理
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultVO<String> handlerException(HttpServletRequest request, Exception ex){
        //打印错误日志
        String exceptionInfo = "==========================================错误信息=======================================\n";
        exceptionInfo += "请求出错的api=>"+request.getMethod()+"    "+request.getRequestURI()+"\n";
        exceptionInfo += LogUtil.getExceptionStackTrace(ex);
        logger.error(exceptionInfo);

        //将错误信息返回给前端
        ResultVO<String> resultData = new ResultVO<>();
        resultData.setCode(ResultCode.NORMAL_ERROR);
        resultData.setMsg("Unknow error occured,please try again later...");
        return resultData;
    }
}
