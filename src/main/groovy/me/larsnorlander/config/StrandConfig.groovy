package me.larsnorlander.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties('strand')
class StrandConfig {

    Map<String, List<String>> subjects, ncae

}
