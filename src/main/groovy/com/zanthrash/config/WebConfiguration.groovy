package com.zanthrash.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

import javax.servlet.*
import javax.servlet.http.HttpServletResponse

@Configuration
@ComponentScan(basePackages = 'com.zanthrash.controllers')
class WebConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    Filter simpleCORSFilter() {
        new Filter() {
            public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
                HttpServletResponse response = (HttpServletResponse) res;
                response.setHeader("Access-Control-Allow-Origin", "*");
                response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
                response.setHeader("Access-Control-Max-Age", "3600");
                response.setHeader("Access-Control-Allow-Headers", "x-requested-with, content-type");
                chain.doFilter(req, res);
            }

            public void init(FilterConfig filterConfig) {}

            public void destroy() {}
        }
    }

    @Override
    void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/swagger-ui.html").setKeepQueryParams(true)
    }
}

