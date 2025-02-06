package csxdddocker.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
public class DataSourceConfig {
/*
    @Value("${spring.datasource}")
    public String dbDriver;

    @Value("${spring.datasource.url}")
    public String dbUrl;

    @Value("${db.username}")
    public String dbUserName;

    @Value("${db.password}")
    public String dbPassword;

*/

    @Autowired
    private Environment environment;
@Bean("dataSource")
    public DataSource getConfig() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        //*
        //dataSource.setUrl("jdbc:postgresql://mb-eee-postgres/mbdb");
        //dataSource.setUsername("me");
        //dataSource.setPassword("sera");
    String [] activeProfiles = this.environment.getActiveProfiles();
    if(Arrays.asList(activeProfiles).contains("dev")) {
        //* local version
        dataSource.setUrl("jdbc:postgresql://localhost:5432/csx-db");
        dataSource.setUsername("postgres");
        dataSource.setPassword("sera");
    }
    else if(Arrays.asList(activeProfiles).contains("prod")) {
        //* Dockerised version
        dataSource.setUrl("jdbc:postgresql://csx-postgres-server:5432/csx-db"); //* connection to db in Docker not localhost
        dataSource.setUsername("csx-user");
        dataSource.setPassword("csx-pw");
    }
    else{}
    //===dataSource.setUrl("jdbc:postgresql://csx-postgres-server:5433/csx-db");


        return dataSource;
    }
}
