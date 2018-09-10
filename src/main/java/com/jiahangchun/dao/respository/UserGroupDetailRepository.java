package com.jiahangchun.dao.respository;

import com.jiahangchun.dao.UserGroupDetailDO;
import com.jiahangchun.dao.UsersDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/9/6 下午11:44
 **/
public interface UserGroupDetailRepository extends JpaRepository<UserGroupDetailDO, Long> {

    @Query(value = "select users.name from users where id in (select ug.user_id from groups g,user_group_detail ug where g.id=ug.group_id and g.name like %?1%)",nativeQuery=true)
    public List<UsersDO> findAllByGroupNameLike(String groupName);

    public UserGroupDetailDO findByGroupIdEqualsAndUserIdEquals(Long groupId,Long userId);

    public List<UserGroupDetailDO> findAllByUserIdEquals(Long userId);
}
