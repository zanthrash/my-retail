package com.zanthrash.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import spock.lang.Specification

@SpringBootTest
class BootupSpec extends Specification{

    @Autowired
    ApplicationContext context

    def "test context loaded"() {
        expect:
        context != null
    }

}
