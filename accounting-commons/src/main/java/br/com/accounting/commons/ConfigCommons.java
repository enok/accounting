package br.com.accounting.commons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan("br.com.accounting")
@PropertySource(value = {"classpath:application.properties"}, ignoreResourceNotFound = true)
@Lazy
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ConfigCommons {
    @Autowired
    private Environment env;

    @Bean
    public String getDiretorio() {
        return env.getProperty("diretorio.url", "./arquivos");
    }
}
