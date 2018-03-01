package com.harmony2.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harmony2.dao.EmployeeDAO;
import com.harmony2.model.Employee;
import com.harmony2.service.EmployeeService;
/**
 * Service Impl class for Employee to perform CRUD operation.
 * @version 1.0
 * @since Feb 02, 2018
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired  
    private EmployeeDAO employeeDAO;

    /**
     * Default Constructor
     */
    public EmployeeServiceImpl() {
        super();    
    }

    @Override   
    public Employee createEmployee(Employee employee) {     
        return employeeDAO.createEmployee(employee);
    }

    @Override   
    public Employee getEmployee(int id) {       
        return employeeDAO.getEmployee(id);
    }

    @Override   
    public Employee updateEmployee(Employee employee) {     
        return employeeDAO.updateEmployee(employee);
    }

    @Override   
    public void deleteEmployee(int id) {        
        employeeDAO.deleteEmployee(id);
    }

    @Override   
    public List<Employee> getAllEmployees() {       
        return employeeDAO.getAllEmployees();
    }

}
