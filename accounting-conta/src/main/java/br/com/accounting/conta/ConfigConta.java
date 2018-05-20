package br.com.accounting.conta;

import br.com.accounting.commons.ConfigCommons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan("br.com.accounting.conta")
@PropertySource(value = {"classpath:application.properties"}, ignoreResourceNotFound = true)
@Import({ConfigCommons.class})
@Lazy
public class ConfigConta {
    @Autowired
    private Environment env;

    @Bean
    public String getDiretorio() {
        return env.getProperty("diretorio.url", "./arquivos");
    }
}
