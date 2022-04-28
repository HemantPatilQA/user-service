package com.selflearning.user;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.LambdaDsl;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.PactProviderRule;
import au.com.dius.pact.consumer.junit.PactVerification;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.selflearning.user.VO.Department;
import com.selflearning.user.service.DepartmentServiceClient;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = "department_provider.base-url:http://localhost:${RANDOM_PORT}",
        classes = DepartmentServiceClient.class)
public class DepatmentServiceGetContractTest {
    private static final int DEPARTMENT_ID = 1;
    private static final String DEPARTMENT_NAME = "IT";
    private static final String DEPARTMENT_ADDRESS = "Aundh";
    private static final String DEPARTMENT_CODE = "IT-001";

    @Rule
    public PactProviderRule provider = new PactProviderRule("department_provider", null,
            RandomPort.getInstance().getPort(), this);

    @Autowired
    private DepartmentServiceClient departmentServiceClient;

    @Pact(consumer = "customer_consumer")
    public RequestResponsePact pactForGetExistingAddressId(PactDslWithProvider builder) {

        DslPart requestBody = LambdaDsl.newJsonBody((o) -> o
                .stringType("departmentName", DEPARTMENT_NAME)
                .stringType("departmentAddress", DEPARTMENT_ADDRESS)
                .stringType("departmentCode", DEPARTMENT_CODE)
        ).build();

        DslPart responseBody = LambdaDsl.newJsonBody((o) -> o
                .integerType("departmentId", DEPARTMENT_ID)
                .stringType("departmentName", DEPARTMENT_NAME)
                .stringType("departmentAddress", DEPARTMENT_ADDRESS)
                .stringType("departmentCode", DEPARTMENT_CODE)
        ).build();

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return builder.given(
                        "Department GET: the department ID matches an existing department ID")
                .uponReceiving("A request for Department data")
                .path(String.format("/departments/%s", DEPARTMENT_ID))
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(responseBody)
                .toPact();
    }

    @PactVerification(fragment = "pactForGetExistingAddressId")
    @Test
    public void testFor_GET_existingDepartmentId_shouldYieldExpectedDepartmentData() {

        final Department department = departmentServiceClient.getDepartment(""+DEPARTMENT_ID);

        assertThat(department.getDepartmentId()).isEqualTo(DEPARTMENT_ID);
        assertThat(department.getDepartmentName()).isEqualTo(DEPARTMENT_NAME);
        assertThat(department.getDepartmentAddress()).isEqualTo(DEPARTMENT_ADDRESS);
        assertThat(department.getDepartmentCode()).isEqualTo(DEPARTMENT_CODE);
    }
}
