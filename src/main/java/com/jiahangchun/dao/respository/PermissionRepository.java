package com.jiahangchun.dao.respository;

import com.jiahangchun.dao.PermissionDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/9/6 下午11:43
 **/
public interface PermissionRepository extends JpaRepository<PermissionDO, Long> {

    public PermissionDO findByPermissionEquals(String permissionName);

    public List<PermissionDO> findAllByIdIn(List<Long> permissionIdList);
}
