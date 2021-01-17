package com.config.order;

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
@MapperScan(basePackages = "com.order", sqlSessionTemplateRef = "orderSqlSessionTemplate" )
public class OrderDataSourceConfig {
    // @Configuration 相当于MemberDataSourceConfig.xml
    // 创建会员的dataSource
    @Bean(name = "orderDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.order")
    public DataSource orderDataSource () {
        return DataSourceBuilder.create().build();
    }

    // 创建订单的SqlSessionFactory
    @Bean("orderSqlSessionFactory")
    public SqlSessionFactory orderSqlSessionFactory(
            @Qualifier("orderDataSource" )  DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(orderDataSource());
        return sqlSessionFactoryBean.getObject();
    }

    // 创建会员事务管理器
    @Bean(name = "orderTransactionManager")
    public DataSourceTransactionManager orderTransactionManager (
            @Qualifier("orderDataSource" )  DataSource dataSource) throws Exception{
        return new DataSourceTransactionManager(dataSource);

    }
    //创建会员sqlSession模板
    @Bean("orderSqlSessionTemplate")
    public SqlSessionTemplate orderSqlSessionTemplate (
            @Qualifier("orderSqlSessionFactory") SqlSessionFactory sqlSessionFactory)  throws Exception{
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}