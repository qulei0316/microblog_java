package com.qulei.controller;


import com.qulei.bean.dto.UserDto;
import com.qulei.bean.entity.User;
import com.qulei.common.util.ResultVOUtil;
import com.qulei.service.UserService;
import com.qulei.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public ResultVO login(@RequestBody UserDto userDto) {
        User user = userService.login(userDto);
        return ResultVOUtil.success(user);
    }

    /**
     * 用户注册
     */
    @PostMapping("/signup")
    public ResultVO signup(@RequestBody UserDto user){
        userService.signup(user);
        return ResultVOUtil.success();
    }


    /**
     * 请求验证码
     */
    @GetMapping("/sendcode")
    public ResultVO sendcode(@RequestParam("user")String user){
        userService.sendCode(user);
        return ResultVOUtil.success();
    }

}
