package com.jiahangchun.manager;

import com.jiahangchun.dao.Users;
import com.jiahangchun.respository.UsersRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/9/3 上午9:08
 **/
@Service
@Transactional
public class UsersManager {

    @Autowired
    private UsersRespository usersRespository;

    public void test(){
        Users user=new Users();
        user.setName("jiahangcun");
        usersRespository.save(user);
    }
}
