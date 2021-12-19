package example.blob.controller;


import example.blob.entity.Employee;
import example.blob.service.EmployeeRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/employees")
public class EmployeeRestController {

    @Autowired
    EmployeeRestService employeeRestService;

    @GetMapping
    public List<Employee> findAll() {
        return employeeRestService.findAll();
    }

    @GetMapping("/{id}")
    public Employee find(@PathVariable Long id) {
        return employeeRestService.find(id);
    }

    @PostMapping
    public Employee post(@RequestBody Employee employee) {
        return employeeRestService.post(employee);
    }

}
