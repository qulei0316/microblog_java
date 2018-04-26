package com.qulei.service;

import com.qulei.bean.app.ResultCode;
import com.qulei.bean.dto.UserDto;
import com.qulei.bean.entity.User;
import com.qulei.common.enums.ExceptionEnum;
import com.qulei.common.exception.MyException;
import com.qulei.common.util.AuthorizeUtil;
import com.qulei.common.util.CommonUtils;
import com.qulei.common.util.MailUtil;
import com.qulei.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
public class UserService {


    @Autowired
    private UserDao userDao;

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 登录
     */
    @Transactional
    public User login(UserDto userDto){
        //判空
        if (CommonUtils.isStringEmpty(userDto.getPassword()) || CommonUtils.isStringEmpty(userDto.getUsername())){
            throw new MyException(ExceptionEnum.EMPTY_ERROR);
        }

        User user = new User();
        String username = userDto.getUsername();
        String password = userDto.getPassword();

        //判断是用户名还是邮箱还是手机
        String phoneReg = "^\\d{11}$";
        String emailReg = "^\\w+@\\w+\\.\\w+$";

        if (username.matches(phoneReg)){
            user = userDao.getUserByphone(username);
        }else if (username.matches(emailReg)){
            user = userDao.getUserByemail(username);
        }

        //判断用户
        if (user == null){
            throw new MyException(ExceptionEnum.LOGIN_ERROR);
        }else if (user.getPassword() == password){
            throw new MyException(ExceptionEnum.LOGIN_ERROR);
        }

        //更新授权
        String token = CommonUtils.createUUID();
        userDto.setId(user.getId());
        userDto.setToken(token);
        int i = userDao.updateUser(userDto);
        if (i == 0){
            throw new MyException(ExceptionEnum.LOGIN_ERROR);
        }

        user.setPassword(null);
        user.setToken(token);
        return user;
    }


    /**
     * 注册
     */
    @Transactional
    public void signup(UserDto dto){
        if (CommonUtils.isStringEmpty(dto.getUsername()) || CommonUtils.isStringEmpty(dto.getPassword()) || CommonUtils.isStringEmpty(dto.getCode())){
            throw new MyException(ExceptionEnum.EMPTY_ERROR);
        }

        //判断是用户名还是邮箱还是手机
        String phoneReg = "^\\d{11}$";
        String emailReg = "^\\w+@\\w+\\.\\w+$";
        String username = dto.getUsername();

        //查找是否有重复
        User user = userDao.getUserByname(username);
        if (user!=null){
            throw new MyException(ExceptionEnum.HAVE_SIGNUP);
        }

        //判断验证码
        String code = dto.getCode();
        String v_code = redisTemplate.opsForValue().get(dto.getUsername());
        if (!code.equals(v_code)){
            throw new MyException(ExceptionEnum.CODE_NOT_RIGHT);
        }
        if (username.matches(phoneReg)){
            dto.setPhone(username);
        }else if (username.matches(emailReg)){
            dto.setEmail(username);
        }else {
            throw new MyException(ExceptionEnum.CONTENT_ERROR);
        }

        //生成主键
        String id = CommonUtils.createUUID();
        dto.setId(id);

        //添加用户
        int i = userDao.addUser(dto);
        if (i==0){
            throw new MyException(ExceptionEnum.SIGNUP_ERROR);
        }
    }


    /**
     * 发送验证码
     */
    @Transactional
    public void sendCode(String user) {
        //判别类型
        String phoneReg = "^\\d{11}$";
        String emailReg = "^\\w+@\\w+\\.\\w+$";
        if (user.matches(phoneReg)){

        }else if (user.matches(emailReg)) {
            //生成验证码
            String v_code = String.valueOf((int)(Math.random()*8999+1000));
            redisTemplate.opsForValue().set(user,v_code,180, TimeUnit.SECONDS);
            //发送邮件

            try {
                mailUtil.setMailhtml(user,v_code);
            } catch (Exception e) {
                throw new MyException(ExceptionEnum.CODE_ERROR);
            }
        }else {
            throw new MyException(ExceptionEnum.CONTENT_ERROR);
        }
    }
}
