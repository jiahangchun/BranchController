package com.jiahangchun.examples;

import com.jiahangchun.controller.SampleController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/7/23 下午11:15
 **/

@RunWith(SpringRunner.class)
@WebMvcTest(SampleController.class) //指定要测试的控制器
@AutoConfigureRestDocs(outputDir = "target/snippets") //指定文档生成的位置
public class TestMockMvc {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/")).
                andDo(print()).
                andExpect(status().isOk()).
                andExpect(content().string(containsString("Hello, World"))).
                andDo(document("home"));//生成http文档的位置target/snippets/home下面
    }


}
