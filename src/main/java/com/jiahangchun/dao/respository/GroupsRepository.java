package com.jiahangchun.dao.respository;

import com.jiahangchun.dao.GroupsDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/9/6 下午11:42
 **/
public interface GroupsRepository extends JpaRepository<GroupsDO, Long> {

    public GroupsDO findByNameEquals(String groupName);

    public void deleteByNameEquals(String groupName);

}
