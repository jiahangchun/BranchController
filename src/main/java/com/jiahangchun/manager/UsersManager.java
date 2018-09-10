package com.jiahangchun.manager;

import com.jiahangchun.dao.UsersDO;
import com.jiahangchun.dao.respository.UsersRepository;
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
    private UsersRepository usersRepository;

    public void test(){
        UsersDO user=new UsersDO();
        user.setName("jiahangcun");
        usersRepository.save(user);
    }
}
