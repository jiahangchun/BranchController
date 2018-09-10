package com.jiahangchun.dao.respository;

import com.jiahangchun.dao.UsersDO;
import com.jiahangchun.dao.dto.UserPermissionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/9/3 上午9:06
 **/
public interface UsersRepository extends JpaRepository<UsersDO, Long> {

    public UsersDO findByNameEquals(String name);

}
