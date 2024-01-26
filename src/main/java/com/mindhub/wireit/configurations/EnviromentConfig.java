package com.mindhub.wireit.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("file:src/main/resources/etc/secrets/.env")
public class EnviromentConfig {
}
