package com.jiahangchun.service.view;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/10/17 下午3:31
 **/
@Controller
public class ViewController {
    @RequestMapping("/view")
    public String index() {
        return "index";
    }
}
