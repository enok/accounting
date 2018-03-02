package br.com.accounting.business;

import br.com.accounting.core.ConfigCore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;

@EnableAutoConfiguration
@Configuration
@ComponentScan("br.com.accounting.business")
@PropertySource(value = "file:application.properties", ignoreResourceNotFound = true)
@Import({ConfigCore.class})
@Lazy
public class ConfigBusiness {
}
