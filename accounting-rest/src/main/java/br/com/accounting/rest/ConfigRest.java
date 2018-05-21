package br.com.accounting.rest;

import br.com.accounting.business.ConfigBusiness;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;

@EnableAutoConfiguration
@Configuration
@ComponentScan("br.com.accounting")
@PropertySource(value = {"file:application.properties", "classpath:application.properties"}, ignoreResourceNotFound = true)
@Import({ConfigBusiness.class})
@Lazy
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ConfigRest {
}
