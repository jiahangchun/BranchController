package com.jiahangchun.dubbo.consumer;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.dubbo.config.annotation.Reference;
//import com.mockuai.wdzg.clubcenter.common.api.ConditionService;
//import com.mockuai.wdzg.clubcenter.common.api.helper.Response;
//import com.mockuai.wdzg.clubcenter.common.domain.dto.ConditionDTO;
//import com.mockuai.wdzg.clubcenter.common.domain.qto.ConditionQTO;
import com.jiahangchun.dubbo.provide.BranchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2019/1/9 上午11:00
 **/
@RestController
@Slf4j
public class ConsumerController {

//    @Reference(check = false)
//    private ConditionService conditionService;
//
//    @RequestMapping("/branch")
//    public String branch() {
//        log.info("test log");
//        System.out.println("-----dubbo服务测试方法-----同时调用多个dubbo服务测试-----");
//        Response<ConditionDTO> conditionDTOResponse = conditionService.getCondition(new ConditionQTO(), com.mockuai.wdzg.clubcenter.common.api.helper.CallerInfo.getInstance("sssss"));
//        return conditionDTOResponse.getMsg();
//    }

    @Reference(check = false)
    private BranchService branchService;

    @RequestMapping("/branch")
    public String branch() {
        log.info("test log");
        System.out.println("-----dubbo服务测试方法-----同时调用多个dubbo服务测试-----");
        return branchService.a();
    }



    /**
     * 其中 p 代表通过的请求,
     * block 代表被阻止的请求,
     * s 代表成功执行完成的请求个数,
     * e 代表用户自定义的异常,
     * rt 代表平均响应时长。
     *
     * @return
     */
    @RequestMapping("/flow")
    public void flowTest() {
        initFlowRules();
        Entry entry = null;
        try {
            entry = SphU.entry("HelloWorld");
            // 资源中的逻辑.
            System.out.println("hello world");
        } catch (BlockException e1) {
            System.out.println("blocked!");
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }
    }

    @RequestMapping("/multi/flow")
    public void multiFlowTest() {
        for (int i = 0; i < 1000; i++) {
            System.out.println("处理" + i + "个");
            flowTest();
        }
    }


    @SentinelResource("HelloWorld")
    public void helloWorld() {
        // 资源中的逻辑
        System.out.println("test hello world");
    }

    private static void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("HelloWorld");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS to 20.
        rule.setCount(10);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

}
