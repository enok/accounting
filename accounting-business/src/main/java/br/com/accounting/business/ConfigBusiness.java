package br.com.accounting.business;

import br.com.accounting.core.ConfigCore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;

@EnableAutoConfiguration
@Configuration
@ComponentScan("br.com.accounting.business")
@PropertySource(value = {"file:application.properties", "classpath:application.properties"}, ignoreResourceNotFound = true)
@Import({ConfigCore.class})
@Lazy
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ConfigBusiness {
}
