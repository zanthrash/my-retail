package com.zanthrash.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = 'github')
class GitHubProperties {
    String token
}
