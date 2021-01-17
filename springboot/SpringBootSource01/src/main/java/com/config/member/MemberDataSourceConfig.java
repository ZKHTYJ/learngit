package com.config.member;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
// 扫包、引入模板
@MapperScan(basePackages = "com.member", sqlSessionTemplateRef = "memberSqlSessionTemplate" )
public class MemberDataSourceConfig {
    // @Configuration 相当于MemberDataSourceConfig.xml
    // 创建会员的dataSource
    @Bean(name = "memberDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.member")
    public DataSource memberDataSource () {
        return DataSourceBuilder.create().build();
    }

    // 创建会员的SqlSessionFactory
    @Bean("memberSqlSessionFactory")
    public SqlSessionFactory memberSqlSessionFactory(
            @Qualifier("memberDataSource" )  DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(memberDataSource());
        return sqlSessionFactoryBean.getObject();
    }

    // 创建会员事务管理器
    @Bean(name = "memberTransactionManager")
    public DataSourceTransactionManager memberTransactionManager (
            @Qualifier("memberDataSource" )  DataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);

    }
    //创建会员sqlSession模板
    @Bean("memberSqlSessionTemplate")
    public SqlSessionTemplate memberSqlSessionTemplate (
            @Qualifier("memberSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
