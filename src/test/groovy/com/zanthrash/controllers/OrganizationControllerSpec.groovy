package com.zanthrash.controllers

import com.zanthrash.services.ReactiveService
import org.spockframework.spring.ScanScopedBeans
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.client.RestTemplate
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.context.request.async.DeferredResult
import spock.lang.Ignore
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@ScanScopedBeans
@SpringBootTest
class OrganizationControllerSpec extends Specification {

    ReactiveService mockReactiveService

    @Autowired
    WebApplicationContext context

    @Autowired
    RestTemplate restTemplate

    @Autowired
    OrganizationController organizationController

//    @Autowired
//    WebApplicationContext webApplicationContext

    @Autowired
    MockMvc mockMvc

    def setup() {
//        restTemplate = new RestTemplate()
//        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
        mockReactiveService = Mock(ReactiveService)
        organizationController.reactiveService = mockReactiveService
    }

    def "should boot up without errors"() {
        expect:
        context != null
    }

    def "test the controllers calls with collaborators with default top count"() {
        given: "we have an valid org name"
            String orgName = 'netfilx'
            Integer top = 5
        when:
            DeferredResult<List> result = organizationController.reposRankedByPullRequest(orgName, top )

        then:
            1 * mockReactiveService.getTopPullRequests(orgName, _ as DeferredResult, top)
            result != null
    }


    def "call the RESTful endpoint successfully"() {
        expect:
           mockMvc.perform(get("/org/netfilx/repos"))
                .andExpect(status().isOk())
    }
}
