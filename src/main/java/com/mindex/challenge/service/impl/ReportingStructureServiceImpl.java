package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeService employeeService;

    @Override
    public ReportingStructure createReportingStructure(String id) {
        Objects.requireNonNull(id);
        Employee emp = employeeService.read(id);
        return new ReportingStructure(emp, getAllReportCount(emp));
    }

    @Override
    public int getAllReportCount(Employee employee) {
        int countOfReports = 0;
        int numOfReports = 0;
        List<Employee> directReports = employee.getDirectReports();

        if(directReports != null) {
//            countOfReports += directReports.size();

//            for (Employee emp: directReports) {
//                countOfReports += getAllReportCount(employeeService.read(emp.getEmployeeId())); // Change this to iteration
//            }

            Queue<Employee> employeeQueue = new LinkedList();
            employeeQueue.addAll(directReports);

            while(!employeeQueue.isEmpty()) {
                int size = employeeQueue.size();
                numOfReports += size;
                for (int i=0; i<size; i++) {
                    Employee emp = employeeService.read(employeeQueue.poll().getEmployeeId());
                    List<Employee> listOfEmployees = emp.getDirectReports();
                    if (listOfEmployees != null) {
                        for (Employee direct: listOfEmployees) {
                            employeeQueue.add(employeeService.read(direct.getEmployeeId()));
                        }
                    }
                }
            }

            LOG.debug("Number of reporting employees for "+employee.getEmployeeId()+" : "+numOfReports);
            return numOfReports;
        }

        return countOfReports;
    }
}
