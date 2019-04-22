package com.waterelephant.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * @Auther: 李刚
 * @Date: 2019/3/6 18:55
 * @Description:
 */
@Configuration
@EnableWebMvc
@EnableSwagger2
@ComponentScan(basePackages = "com.waterelephant.controller.api")
public class SwaggerConfig{

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.waterelephant.controller.api"))
                .paths(PathSelectors.any())
                .build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("水象CMS-api")
                //备注信息
//                .description("powered by By-Health")
//                .termsOfServiceUrl("http://www.sxfq.com/")
                //.contact(contact)
                .version("1.0.0")
                .build();
    }
}