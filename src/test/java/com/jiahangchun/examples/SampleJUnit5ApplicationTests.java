//package com.jiahangchun.examples;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.restdocs.ManualRestDocumentation;
//import org.springframework.restdocs.RestDocumentationContextProvider;
//import org.springframework.restdocs.RestDocumentationExtension;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
//
//import java.lang.reflect.Method;
//
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
//import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
///**
// * @descritpion
// * @date Created at 2018/7/23 下午6:08
// **/
//@SpringBootTest
//public class SampleJUnit5ApplicationTests {
//    private final ManualRestDocumentation restDocumentation = new ManualRestDocumentation();
//
//    @Autowired
//    private WebApplicationContext context;
//
//    private MockMvc mockMvc;
//
//    @BeforeMethod
//    public void setUp(Method method) {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
//                .apply(documentationConfiguration(this.restDocumentation)).build();
//        this.restDocumentation.beforeTest(getClass(), method.getName());
//    }
//
//    @AfterMethod
//    public void tearDown() {
//        this.restDocumentation.afterTest();
//    }
//
//    @Test
//    public void sample() throws Exception {
//        this.mockMvc.perform(get("/"))
//                .andExpect(status().isOk())
//                .andDo(document("sample"));
//    }
//}
