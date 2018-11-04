package com.wenchao.boottest.config;


import io.shardingsphere.core.api.yaml.YamlShardingDataSourceFactory;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;


@Configuration
@MapperScan(basePackages = { "com.wenchao.boottest.mapper" }, sqlSessionFactoryRef = "mybatisSqlSessionFactory")
public class DataSourceConfig {

    @Value("classpath:sharding-rule.yaml")
//	@Value("classpath:application-sharding-rule.yml")
    private File databaseFile;

    @Bean(name = "dataSource")
    public DataSource getDataSource() throws SQLException, IOException {
//        URL url = getClass().getClassLoader().getResource("sharding-rule.yaml");
//        DataSource dataSource = YamlShardingDataSourceFactory.createDataSource(new File(url.getFile()));
       
    	DataSource dataSource = YamlShardingDataSourceFactory.createDataSource(databaseFile);
        return dataSource;
    }

   
    /**
     * Sharding-jdbc的事务支持
     *
     * @return
     */
    @Bean(name = "mybatisTransactionManager")
    public DataSourceTransactionManager mybatisTransactionManager(@Qualifier("dataSource") DataSource dataSource) throws SQLException {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "mybatisSqlSessionFactory")
    public SqlSessionFactory mybatisSqlSessionFactory(@Qualifier("dataSource") DataSource mybatisDataSource)
            throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(mybatisDataSource); 
        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
        	sessionFactory.setMapperLocations(resolver.getResources("classpath*:mapping/*.xml"));
            return sessionFactory.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

  

}
