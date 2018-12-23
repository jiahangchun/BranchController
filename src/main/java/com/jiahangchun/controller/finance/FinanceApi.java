package com.jiahangchun.controller.finance;

import com.jiahangchun.DO.FinanceChangeDO;
import com.jiahangchun.DO.FinanceChangeQTO;
import com.jiahangchun.DO.TaskDO;
import com.jiahangchun.DO.UserDO;
import com.jiahangchun.common.util.JsonUtil;
import com.jiahangchun.repository.FinanceChangeRepository;
import com.jiahangchun.repository.TaskRepository;
import com.jiahangchun.repository.UserRepository;
import com.jiahangchun.service.finance.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/12/21 下午2:44
 **/
@RestController
@RequestMapping("/finance")
public class FinanceApi {

    @Autowired
    private FinanceChangeRepository financeChangeRepository;
    @Autowired
    private FinanceService financeService;
    @Autowired
    private TaskRepository taskRepository;

    /**
     * 测试
     *
     * @return
     */
    @RequestMapping("/test")
    public String get() {
        Iterable<FinanceChangeDO> iterable = financeChangeRepository.findAll();
        List<FinanceChangeDO> financeChangeDOS = new ArrayList<FinanceChangeDO>();
        iterable.forEach(new Consumer<FinanceChangeDO>() {
            @Override
            public void accept(FinanceChangeDO financeChangeDO) {
                financeChangeDOS.add(financeChangeDO);
            }
        });
        return JsonUtil.toJson(financeChangeDOS);
    }

    /**
     * 上传文件
     *
     * @param filePath
     * @return
     */
    @RequestMapping("/upload")
    public String uploadByFilePath(@RequestParam(value = "path") String filePath) {
        financeService.saveFile(filePath);
        return "success";
    }

    /**
     * 查询
     *
     * @param bsSn
     * @return
     */
    @RequestMapping("/query")
    public String query(@RequestParam(value = "bs_sn") String bsSn) {
        FinanceChangeQTO financeChangeQTO = new FinanceChangeQTO();
        financeChangeQTO.setBsSn(bsSn);
        List<FinanceChangeDO> financeChangeDOS = financeService.query(financeChangeQTO);
        return JsonUtil.toJson(financeChangeDOS);
    }


    /**
     * 导出
     * @return
     */
    @RequestMapping("/download")
    public String download() {
        Iterable<TaskDO> iterable =  taskRepository.findAll();
        Iterator iterator=iterable.iterator();
        List<TaskDO> taskDOS=new ArrayList<>();
        while(iterator.hasNext()){
            TaskDO taskDO= (TaskDO) iterator.next();
            taskDOS.add(taskDO);
        }
        return JsonUtil.toJson(taskDOS);
    }

}
