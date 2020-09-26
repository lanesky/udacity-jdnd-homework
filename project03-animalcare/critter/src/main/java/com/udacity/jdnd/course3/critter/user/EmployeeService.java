package com.udacity.jdnd.course3.critter.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Transactional
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees(){

        return employeeRepository.findAll();
    }

    public Employee getEmployee(Long id) {

        return  employeeRepository.getOne(id);
    }

    public Long save (Employee employee) {

        return employeeRepository.save(employee).getId();
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee employee = employeeRepository.getOne(employeeId);
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
    }

    public List<Employee> findEmployeesForService(Set<EmployeeSkill> employeeSkills, LocalDate date) {
        ArrayList<Employee> employees = new ArrayList<Employee>();
        employeeRepository.findAll().forEach(x-> {
            if (x.getSkills().containsAll(employeeSkills) &&
                    x.getDaysAvailable().contains(date.getDayOfWeek()))
            employees.add(x);
        });

        return employees;
    }
}
