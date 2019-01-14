package com.jiahangchun.tool;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chunchun
 * @descritpion 获取mysql字段
 * @date Created at 2019/1/11 上午9:47
 **/
@Service
@Scope("singleton")
@Slf4j
public class GenMysqlColumnComment implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 获取对应的方法
     */
    public List<MethodInfo> getHandlerMapping() {
        List<MethodInfo> methodInfos=new ArrayList<>();
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        map.forEach((info, method) -> {
            PatternsRequestCondition p = info.getPatternsCondition();
            String className=method.getMethod().getDeclaringClass().getName();
            String methodName=method.getMethod().getName();
            for (String url : p.getPatterns()) {
                MethodInfo methodInfo=new MethodInfo();
                methodInfo.setClassName(className);
                methodInfo.setMethod(methodName);
                methodInfo.setUrl(url);
                methodInfos.add(methodInfo);
            }
            RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
            for (RequestMethod requestMethod : methodsCondition.getMethods()) {
                System.out.println(JSON.toJSONString(requestMethod));
            }
        });
        return methodInfos;
    }


    /**
     * 获取数据库的信息
     *
     * @throws Exception
     */
    public Map<String, PropertiesObjectValue> getMeta() throws Exception {
        DataSource dataSource = getApplicationContext().getBean("dataSource", DataSource.class);

        //H2 例子测试
        Map<String, PropertiesObjectValue> map = new HashMap<>();
        List<PropertiesObjectValue> propertiesObjectValues = new ArrayList<>();
        List<String> tables = getTables(dataSource);
        tables.forEach((table) -> {
            propertiesObjectValues.addAll(getDataMetaFromSql(dataSource, table));
        });
        propertiesObjectValues.forEach((value) -> {
            map.put(genkey(value), value);
        });
        return map;
    }


    /**
     * 生成Key的策略
     *
     * @param value
     * @return
     */
    private String genkey(PropertiesObjectValue value) {
        return value.getTableName() + "#" + value.getDataName();
    }

    /**
     * 获取所有的表名称
     *
     * @param dataSource
     * @return
     */
    private List<String> getTables(DataSource dataSource) {
        List<String> tableNames = new ArrayList<>();
        Connection connection = null;
        Statement stm = null;
        ResultSet rs = null;
        try {
            String showTables = "show tables;";
            connection = dataSource.getConnection();
            stm = connection.createStatement();
            rs = stm.executeQuery(showTables);
            while (rs.next()) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < 10; i++) {
                    try {
                        switch (i) {
                            case 1:
                                sb.append(rs.getString(i));
                                break;
                            default:
                                break;
                        }
                    } catch (Exception e) {
                    }
                }
                System.out.println(sb.toString());
                tableNames.add(sb.toString());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                stm.close();
            } catch (Exception e) {
            }
            try {
                connection.close();
            } catch (Exception e) {
            }
        }
        return tableNames;
    }

    /**
     * 获取某个表的所有字段属性
     *
     * @param dataSource
     * @param tableName
     * @return
     */
    private List<PropertiesObjectValue> getDataMetaFromSql(DataSource dataSource, String tableName) {
        List<PropertiesObjectValue> propertiesObjectValues = new ArrayList<>();
        Connection connection = null;
        Statement stm = null;
        ResultSet rs = null;
        try {
            String showDetail = "select * from INFORMATION_SCHEMA.Columns where table_name='" + tableName + "';";
            connection = dataSource.getConnection();
            stm = connection.createStatement();
            rs = stm.executeQuery(showDetail);
            while (rs.next()) {
                StringBuilder sb = new StringBuilder();
                PropertiesObjectValue propertiesObjectValue = new PropertiesObjectValue();
                for (int i = 0; i < 50; i++) {
                    try {
                        sb.append(i + ":" + rs.getString(i) + "--");
                        switch (i) {
                            case 1:
                                propertiesObjectValue.setDatabaseName(rs.getString(i));
                                break;
                            case 3:
                                propertiesObjectValue.setTableName(rs.getString(i));
                                break;
                            case 4:
                                propertiesObjectValue.setDataName(rs.getString(i));
                                break;
                            case 16:
                                propertiesObjectValue.setDataType(rs.getString(i));
                                break;
                            case 22:
                                propertiesObjectValue.setCommnets(rs.getString(i));
                                break;
                            default:
                                break;
                        }

                    } catch (Exception e) {
                    }
                }
                propertiesObjectValues.add(propertiesObjectValue);
                System.out.println(sb.toString());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                stm.close();
            } catch (Exception e) {
            }
            try {
                connection.close();
            } catch (Exception e) {
            }
        }
        return propertiesObjectValues;
    }


    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
