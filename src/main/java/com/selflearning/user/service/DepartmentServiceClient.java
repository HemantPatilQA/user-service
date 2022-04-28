package com.selflearning.user.service;

import com.selflearning.user.VO.Department;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DepartmentServiceClient {

    private final RestTemplate restTemplate;

    public DepartmentServiceClient(@Value("${department_provider.base-url}") String baseUrl) {
        this.restTemplate = new RestTemplateBuilder()
                .rootUri(baseUrl)
                .defaultHeader("Connection", "close")
                .build();
    }

    public Department getDepartment(String departmentId) {
        return restTemplate.getForObject(String.format("/departments/%s", departmentId), Department.class);
    }
}