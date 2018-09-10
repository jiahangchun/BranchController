package com.jiahangchun.respository;

import com.jiahangchun.App;
import com.jiahangchun.dao.respository.UserGroupDetailRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/9/7 上午7:35
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class DaoTest {

    @Autowired
    private UserGroupDetailRepository userGroupDetailRepository;

    @Test
    public void testUserGroupDetailRepository(){
        userGroupDetailRepository.findAllByGroupNameLike("a");
    }
}
