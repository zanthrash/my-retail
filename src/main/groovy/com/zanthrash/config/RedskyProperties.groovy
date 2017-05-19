package com.zanthrash.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = 'redsky')
class RedskyProperties {
    String baseUrl
}
