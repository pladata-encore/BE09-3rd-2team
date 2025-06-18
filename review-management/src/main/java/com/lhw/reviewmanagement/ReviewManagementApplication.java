package com.lhw.reviewmanagement;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@OpenAPIDefinition(
        info = @Info(title = "Review API", version = "v1", description = "리뷰 서비스 API 명세서")
)
@SpringBootApplication
public class ReviewManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReviewManagementApplication.class, args);
    }

}
