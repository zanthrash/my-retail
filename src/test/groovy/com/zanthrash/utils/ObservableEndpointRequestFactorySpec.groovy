package com.zanthrash.utils

import com.zanthrash.Application
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import rx.Observable
import spock.lang.Specification

@ContextConfiguration(classes = Application.class)
class ObservableEndpointRequestFactorySpec extends Specification {

    @Autowired
    ObservableEndpointRequestFactory observableEndpointRequestFactory

    def "create an observable asyn enpoint request for repos"() {

        when:
            URI uri = new URI("https://api.github.com/repo")
            Observable result = observableEndpointRequestFactory.createGetRequestToFetchRepos(uri)
        then:
            result != null
            result instanceof Observable
    }

    def "create an observable asyn enpoint request for pull requests"() {

        when:
            URI uri = new URI("https://api.github.com/pull")
            Observable result = observableEndpointRequestFactory.createGetRequestToFetchPullRequests(uri)
        then:
            result != null
            result instanceof Observable
    }
}

