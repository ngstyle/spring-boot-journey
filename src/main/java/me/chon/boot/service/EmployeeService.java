package me.chon.boot.service;


import me.chon.boot.bean.Employee;
import me.chon.boot.bean.EmployeeExample;
import me.chon.boot.dao.EmployeeMapper;
import me.chon.boot.exception.BootException;
import me.chon.boot.exception.ExceptionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    /**
     * 查询所有员工
     * @return
     */
    public List<Employee> getAll() {
        return employeeMapper.selectByExampleWithDept(null);
    }

    @Transactional
    public int addEmp(Employee employee) {
        return employeeMapper.insertSelective(employee);
    }

    public boolean isUserNameExist(String empName) {
        EmployeeExample employeeExample = new EmployeeExample();
        EmployeeExample.Criteria criteria = employeeExample.createCriteria();
        criteria.andEmpNameEqualTo(empName);

        long count = employeeMapper.countByExample(employeeExample);
        return count > 0;
    }

    public Employee getEmp(Integer id)  {
        Employee employee = employeeMapper.selectByPrimaryKeyWithDept(id);

        if (employee.getdId() == null) {
            throw new BootException(ExceptionEnum.DEPT_EMPTY);
        }

        return employee;
    }

    @Transactional
    public int updateEmp(Employee employee) {
        return employeeMapper.updateByPrimaryKeySelective(employee);
    }

    /**
     * 根据empId 删除
     * @param empId
     * @return
     */
    @Transactional
    public int delEmpById(int empId) {
        return employeeMapper.deleteByPrimaryKey(empId);
    }

    /**
     * 批量删除
     * @param empIds
     * @return
     */
    @Transactional
    public int delEmpByIds(List<Integer> empIds) {

        EmployeeExample employeeExample = new EmployeeExample();
        EmployeeExample.Criteria criteria = employeeExample.createCriteria();
        criteria.andEmpIdIn(empIds);

        return employeeMapper.deleteByExample(employeeExample);
    }
}
