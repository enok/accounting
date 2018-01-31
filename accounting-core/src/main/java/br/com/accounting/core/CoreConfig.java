package br.com.accounting.core;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;

@EnableAutoConfiguration
@Configuration
@ComponentScan("br.com.accounting.core")
@PropertySource(value = "file:application.properties", ignoreResourceNotFound = true)
//@Import({ DBConfig.class, WSConfig.class })
@Lazy
public class CoreConfig {
}
