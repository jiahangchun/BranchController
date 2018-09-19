package com.jiahangchun;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.helper.BuildConsoleStreamListener;
import com.offbytwo.jenkins.model.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/8/28 上午9:15
 **/
public class JenkinsApiTest {

    private static JenkinsServer jenkins = null;

    static {
        try {
            jenkins = new JenkinsServer(new URI("http://localhost:8080"), "test", "test");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
//        JenkinsApiTest jenkinsApiTest = new JenkinsApiTest();
//////        jenkinsApiTest.startJob();
////
//////        jenkinsApiTest.printlog();
////        jenkinsApiTest.createJob();


        String a="test";
        String b="ff";
        String c="ta";
        String d="es";

        System.out.println(a.contains(b));
        System.out.println(a.contains(c));
        System.out.println(a.contains(d));
    }

    /**
     * 开始任务
     *
     * @throws IOException
     */
    private void startJob() throws IOException {
        Job job = jenkins.getJob("test");
        job.build(true);//没参数的启动方式
//        job.build(new HashMap<>(),true);//有参数的启动方式
    }

    /**
     * 删除任务
     * @throws IOException
     */
    private void deleteJob() throws IOException {
        jenkins.deleteJob("test",true);
    }

    private void createJob() throws IOException {
        String jobXml =jenkins.getJobXml("test");
        System.out.println(jobXml);
        jenkins.createJob("test2",jobXml,true);
    }

    /**
     * 启动记录
     * @throws IOException
     */
    private void getBuilds() throws IOException {
        List<Build> list= jenkins.getJob("test").getAllBuilds();
        for(Build build:list){
            System.out.println(build.details().getConsoleOutputText());
        }
    }

    /**
     * 打印日志
     * @throws IOException
     * @throws InterruptedException
     */
    private void printlog() throws IOException, InterruptedException {
        Job job = jenkins.getJob("test");
        BuildWithDetails build =((JobWithDetails) job).getLastBuild().details();
        // Get log with initial offset
//        int offset = 40;
//        String output = build.getConsoleOutputText(offset).getConsoleLog();
        // Stream logs (when build is in progress)
        BuildConsoleStreamListener buildListener = new PartiallyGetConsole();
        build.streamConsoleOutput(buildListener, 1, 420);


        //还是那个crumb问题
        //估计写的这个人一开始就没打算用crumb=true的方式，希望用手动设置的方式设置jenkins.可以从issus中看出
//        Build lastCompletedBuild = job.details().getLastCompletedBuild();
//        TestReport testReport = lastCompletedBuild.getTestReport();
//        System.out.println(testReport.getTotalCount());
    }


    public class PartiallyGetConsole implements BuildConsoleStreamListener{

        @Override
        public void onData(String s) {
            System.out.println(s);
        }

        @Override
        public void finished() {
            System.out.println("finished");
        }
    }

}
