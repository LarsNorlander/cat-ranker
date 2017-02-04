package me.larsnorlander.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties('tracks')
class TracksConfig {

    Map<String, Map<String, List<String>>> academic

}
