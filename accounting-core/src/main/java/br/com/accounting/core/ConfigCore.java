package br.com.accounting.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan("br.com.accounting.core")
@PropertySource(value = {"file:application.properties", "classpath:application.properties"}, ignoreResourceNotFound = true)
@Lazy
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ConfigCore {
    @Autowired
    private Environment env;

    @Bean
    public String getDiretorio() {
        return env.getProperty("diretorio.url", "./arquivos");
    }
}
