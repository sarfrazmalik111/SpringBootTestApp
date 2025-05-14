package com.test.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
//@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    RequestInterceptor requestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(requestInterceptor);
        //registry.addInterceptor(Your_Interceptor);
    }

//   Media-Type-Configuration: to return response in JSON / XML format
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer
                .favorParameter(true)
                .parameterName("mediaType")
                .defaultContentType(MediaType.APPLICATION_JSON) // Set the default media type
                .mediaType("json", MediaType.APPLICATION_JSON)   // Map "json" to JSON
                .mediaType("xml", MediaType.APPLICATION_XML);     // Map "xml" to XML

//        http://localhost:8080/api/users/test-xml  --- JSON-Response
//        http://localhost:8080/api/users/test-xml?mediaType=json   --- JSON-Response
//        http://localhost:8080/api/users/test-xml?mediaType=xml    ---- XML-Response
//        OR
//        Pass 'Accept = application/json or application/xml' in REQUEST-HEADER

    }

}
