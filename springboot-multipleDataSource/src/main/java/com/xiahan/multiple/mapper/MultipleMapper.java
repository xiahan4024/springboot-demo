package com.xiahan.multiple.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.xiahan.multiple.annotation.DataSource;
import com.xiahan.multiple.entity.Student;
import com.xiahan.multiple.util.MyDataSourceType;

/**
 * 
 * @author xiahan
 * @dateTime 2019年10月30日 下午9:06:36
 */
@Mapper
public interface MultipleMapper {
	
	@Select("select * from tb_student")
	Student noDatasource();

	@Select("select * from tb_student")
	@DataSource
	Student primekeyNoValue();

	@Select("select * from tb_student")
	@DataSource(MyDataSourceType.primekey)
	Student primekey();

	@Select("select * from tb_student")
	@DataSource(MyDataSourceType.datasourceOne)
	Student datasource1();

	@Select("select * from tb_student")
	@DataSource(MyDataSourceType.datasourceTwo)
	Student datasource2();

}
