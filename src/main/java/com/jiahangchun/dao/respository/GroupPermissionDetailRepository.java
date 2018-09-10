package com.jiahangchun.dao.respository;

import com.jiahangchun.dao.GroupPermissionDetailDO;
import com.jiahangchun.dao.PermissionDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/9/6 下午11:41
 **/
public interface GroupPermissionDetailRepository extends JpaRepository<GroupPermissionDetailDO, Long> {

    public List<GroupPermissionDetailDO> findAllByGroupIdEquals(Long groupId);

    public void deleteAllByGroupIdEqualsAndPermissionIdEquals(Long groupId,Long permissionId);

    public List<GroupPermissionDetailDO> findAllByGroupIdIn(List<Long> groupIdList);
}
