package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.ReportingStructure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {
    private String reportingUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        reportingUrl = "http://localhost:" + port + "/reporting/{id}";
    }

    @Test
    public void testNumberOfReports() {
        String employeeID = "16a596ae-edd3-4847-99fe-c4518e82c86f";

        // Create checks
        ReportingStructure reportedEmployee = restTemplate.getForEntity(reportingUrl, ReportingStructure.class,employeeID).getBody();

        assertNotNull(reportedEmployee.getEmployee().getEmployeeId()); // Test reporting employee response is not null
        assertEquals(reportedEmployee.getNumberOfReports(), 4); // Test the total number of reporting employees is correct
    }
}
