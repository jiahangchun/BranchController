package com.jiahangchun.controller;

import com.jiahangchun.DO.UserDO;
import com.jiahangchun.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/10/9 上午11:44
 **/
@RestController
public class H2Controller {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/h2/test")
    public String get() {
        StringBuilder stringBuilder = new StringBuilder();
        Iterable<UserDO> iterable = userRepository.findAll();
        Iterator<UserDO> iterator=iterable.iterator();
        while (iterator.hasNext()) {
            UserDO userDO = iterator.next();
            stringBuilder.append(userDO.getMobile()).append(";");
        }
        return stringBuilder.toString();
    }

}
