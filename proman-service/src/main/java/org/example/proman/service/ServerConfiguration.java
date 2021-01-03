package org.example.proman.service;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.example.proman.service")
@EntityScan("org.example.proman.service.Entity")
public class ServerConfiguration {
}
