package com.jiahangchun.respository;

import com.jiahangchun.dao.Users;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/9/3 上午9:06
 **/
public interface UsersRespository extends JpaRepository<Users,Long> {
}
