package example.blob.service;

import example.blob.dao.EmployeeRepository;
import example.blob.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeRestService {

    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }


    public Employee find(Long id) {
        return employeeRepository.findById(id).get();
    }


    public Employee post(Employee employee) {
        return employeeRepository.save(employee);
    }
}
