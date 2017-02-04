package me.larsnorlander.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

/**
 * Created by larsjosephnorlander on 7/25/16.
 * Allows schools to set their own weights to
 * different factors.
 */
@Configuration
@ConfigurationProperties('multiplier')
class MultiplierConfig {

    int grades

    int ncae

    int awards

}
