package csxdddocker.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

//@Component
@Configuration
@Slf4j
public class TodoConfig {

    //===@Bean("todo")
   //=== @Primary
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource primaryDataSource() {
        //DataSource ds=DataSourceBuilder.create()..build();
        return DataSourceBuilder.create().build();


/*
        try {
            log.info("DSDSADDDDDDD++++++++++++++++++++++++++++++"+ds.getConnection().getSchema());
        } catch (SQLException e) {
            log.info("MSSSSSSSSSSSSSSSSSSSSSS----------------"+e.getMessage());
        }
        return ds;
*/
    }
}