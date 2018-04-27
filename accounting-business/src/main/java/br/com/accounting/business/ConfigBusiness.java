package br.com.accounting.business;

import br.com.accounting.core.ConfigCore;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan("br.com.accounting.business")
@PropertySource(value = {"classpath:application.properties"}, ignoreResourceNotFound = true)
@Import({ConfigCore.class})
@Lazy
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ConfigBusiness {
}
