package com.jiahangchun.repository;

import com.jiahangchun.DO.TaskDO;
import org.springframework.data.repository.CrudRepository;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/12/21 下午5:41
 **/
public interface TaskRepository extends CrudRepository<TaskDO, Long> {
}
