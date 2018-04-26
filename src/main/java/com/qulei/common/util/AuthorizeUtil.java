package com.qulei.common.util;

import com.qulei.bean.entity.User;
import com.qulei.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorizeUtil {

    @Autowired
    private UserDao userDao;

    /**
     * 验证用户id和token是否匹配
     * @param user_id
     * @param token
     * @return
     */
    public boolean verify(String user_id,String token){
        User user = userDao.getUserById(user_id);
        if (token.equals(user.getToken())){
            return true;
        }
        return false;
    }
}
