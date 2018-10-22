package com.jiahangchun.jmeter;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/10/19 下午2:13
 **/
public class JmeterClass extends AbstractJavaSamplerClient {

    private long start = 0L, end = 0L;
    private static final String param = "jmeter";

    public JmeterClass() {
        super();
    }

    @Override
    public void setupTest(JavaSamplerContext context) {
        super.setupTest(context);
    }


    @Override
    public void teardownTest(JavaSamplerContext context) {
        super.teardownTest(context);
    }

    @Override
    public Arguments getDefaultParameters() {
        Arguments arguments = new Arguments();
        arguments.addArgument("param", param);
        return arguments;
    }

    public SampleResult runTest(JavaSamplerContext javaSamplerContext) {
        SampleResult sampleResult = new SampleResult();
        try {
            sampleResult.setResponseMessage("方法执行成功！");
            sampleResult.setSuccessful(true);
            sampleResult.setResponseData(new TestClass().say(param), null);
            sampleResult.setDataType(SampleResult.TEXT);
        } catch (Exception e) {
            sampleResult.setSuccessful(false);
        } finally {
            sampleResult.sampleEnd();
        }
        return sampleResult;
    }
}
