package com.example.project.DBconfig;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:/application.properties")
public class DBconfig {
    @Autowired
    ApplicationContext applicationContext;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariConfig hikariConfig(){
        return new HikariConfig();

    }


    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }


    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {


        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();

        factoryBean.setDataSource( dataSource() );
        factoryBean.setMapperLocations( applicationContext.getResources("classpath:mybatis/mapper/**/*Mapper.xml") );
        factoryBean.setConfigLocation( applicationContext.getResource("classpath:mybatis/config/config.xml"));

        return factoryBean.getObject();


    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception{
        return new SqlSessionTemplate(sqlSessionFactory);
    }


}
