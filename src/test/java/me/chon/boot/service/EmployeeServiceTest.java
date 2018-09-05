package me.chon.boot.service;

import me.chon.boot.bean.Employee;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceTest {

    @Autowired
    EmployeeService employeeService;

    @Test
    public void getEmp() {
        Employee employee = employeeService.getEmp(1);
        Assert.assertEquals(Integer.toUnsignedLong(employee.getdId()),1L);
    }
}