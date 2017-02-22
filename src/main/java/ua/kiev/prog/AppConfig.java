package ua.kiev.prog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan("ua.kiev.prog")
@EnableTransactionManagement
@EnableWebMvc
@Import({SecurityConfig.class})
public class AppConfig extends WebMvcConfigurerAdapter {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory 		//фабрика которая создает ЕМ всем кто попросит
            (DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {	//содаем фабрику на основе переданых параметров (бинов)
        Properties jpaProp = new Properties();					//
        jpaProp.put("hibernate.hbm2ddl.auto", "create");			//создать с нуля и удалить при завершении

        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactory.setJpaProperties(jpaProp);				//задает доп настройки
        entityManagerFactory.setPackagesToScan("ua.kiev.prog");
        return entityManagerFactory;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) { 
        return new JpaTransactionManager(emf);					//реализация интерфейса для транзакций
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() { 				//по сути заменяет часть persistence.xml
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(true);
        adapter.setGenerateDdl(true);						//сгенерировать бд самостоятельно
        adapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");

        return adapter;
    }

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/brainNoteDB");
        ds.setUsername("root");
        ds.setPassword("24071998");

        return ds;
    }

    @Bean
    public UrlBasedViewResolver setupViewResolver() {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setPrefix("/dynamic/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        resolver.setOrder(1);
        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/**")
                .addResourceLocations("/static/");
    }
}
