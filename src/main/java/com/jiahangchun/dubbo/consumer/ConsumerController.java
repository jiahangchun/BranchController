package com.jiahangchun.dubbo.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mockuai.wdzg.clubcenter.common.api.ConditionService;
import com.mockuai.wdzg.clubcenter.common.api.helper.Response;
import com.mockuai.wdzg.clubcenter.common.domain.dto.ConditionDTO;
import com.mockuai.wdzg.clubcenter.common.domain.qto.ConditionQTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2019/1/9 上午11:00
 **/
@RestController
public class ConsumerController {

    @Reference(check = false)
    private ConditionService conditionService;

    @RequestMapping("/branch")
    public String branch() {
        System.out.println("-----dubbo服务测试方法-----同时调用多个dubbo服务测试-----");
        Response<ConditionDTO> conditionDTOResponse = conditionService.getCondition(new ConditionQTO(), com.mockuai.wdzg.clubcenter.common.api.helper.CallerInfo.getInstance("sssss"));
        return conditionDTOResponse.getMsg();
    }
}
