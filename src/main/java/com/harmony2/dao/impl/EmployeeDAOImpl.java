package com.harmony2.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.harmony2.dao.EmployeeDAO;
import com.harmony2.model.Employee;
import com.harmony2.util.CassandraDAOTemplate;

/**
 * DAOImpl class for Employee to perform CRUD operation.
 * @version 1.0
 * @since Feb 02, 2018
 */
@Repository
public class EmployeeDAOImpl implements EmployeeDAO {
    
    @Autowired
    private CassandraDAOTemplate cassandraDAOTemplate;
    
    @Override
    public Employee createEmployee(Employee employee) {     
        return cassandraDAOTemplate.create(employee);
    }
    
    @Override
    public Employee getEmployee(int id) {       
        return cassandraDAOTemplate.findById(id, Employee.class);
    }
    
    @Override
    public Employee updateEmployee(Employee employee) {     
        return cassandraDAOTemplate.update(employee, Employee.class);
    }

    @Override
    public void deleteEmployee(int id) {        
    	cassandraDAOTemplate.deleteById(id, Employee.class);
    }

    @Override
    public List<Employee> getAllEmployees() {       
        return cassandraDAOTemplate.findAll(Employee.class);
    }
}

