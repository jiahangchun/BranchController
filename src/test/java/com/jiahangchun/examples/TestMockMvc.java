package com.jiahangchun.examples;

import com.jiahangchun.controller.SampleController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
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
                andDo(document("home"));
                //生成http文档的位置target/snippets/home下面
                //这个完全可以自定义的
    }

    @Test
    public void shouldReturnDefaultMessage2() throws Exception {
        this.mockMvc.perform(get("/2")).
                andDo(print()).
                andExpect(status().isOk()).
                andExpect(content().string(containsString("Hello, World"))).
                andDo(document("{class_name}_{method_name}"));//生成http文档的位置target/snippets/home下面
    }


    /**
     * 这是将所有分散的文档组合成一个整体文档的方法
     * @throws IOException
     */
    @Test
    public void adocBuild() throws IOException {
        String appDir = System.getProperty("user.dir");
        String adocPath = appDir + "//src//docs//asciidoc//hello.adoc";
        StringBuilder content = new StringBuilder();
        content.append("include::" + appDir + "//src//docs//asciidoc//preview.adoc[]").append(System.getProperty("line.separator")).append(System.getProperty("line.separator"));
        File apidirs = new File(appDir + "//target//snippets");
        for (File apidir : apidirs.listFiles()) {
            String apiName = apidir.getName();
            content.append("== " + apiName + System.getProperty("line.separator"));
            fileAppend(content, apidir + "//http-request.adoc", ".http-request");
            fileAppend(content, apidir + "//request-headers.adoc", ".request-headers 请求头说明");
            fileAppend(content, apidir + "//request-parameters.adoc", ".request-parameters 请求参数说明");
            fileAppend(content, apidir + "//request-body.adoc", ".request-body 请求体说明");
            fileAppend(content, apidir + "//http-response.adoc", ".http-response");
            fileAppend(content, apidir + "//response-fields.adoc", ".response-fields 返回值说明");
            content.append(System.getProperty("line.separator"));

        }
        File file = new File(adocPath);
        writeStringToFile(file, content.toString(), "UTF-8");
    }

    private void writeStringToFile(File file, String content, String character) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file);
        OutputStreamWriter osw = new OutputStreamWriter(fos, character);
        osw.write(content);
        osw.flush();
    }

    private void fileAppend(StringBuilder content, String include, String title) {
        File file = new File(include);
        if (file.exists()) {
            content.append(title).append(System.getProperty("line.separator"));
            content.append("include::").append(include).append("[]").append(System.getProperty("line.separator"));
        }
    }

}
