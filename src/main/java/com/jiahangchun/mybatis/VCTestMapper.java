package com.jiahangchun.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2019/1/7 下午4:45
 **/
@Mapper
public interface VCTestMapper {
    @Select("SELECT * FROM VC WHERE v = #{vs}")
    VCDO findByState(@Param("vs") String vs);

}
