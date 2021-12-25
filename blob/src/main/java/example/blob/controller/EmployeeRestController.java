package example.blob.controller;


import example.blob.entity.Employee;
import example.blob.service.EmployeeRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping("/employees")
public class EmployeeRestController {

    @Autowired
    EmployeeRestService employeeRestService;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Employee>>> findAll() {

        List<EntityModel<Employee>> employees = employeeRestService.findAll().stream().map(
                employee -> {
                    return EntityModel.of(employee)
                            .add(linkTo(methodOn(EmployeeRestController.class).find(employee.getId())).withSelfRel());
                }
        ).collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(employees));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Employee>> find(@PathVariable Long id) {

        Employee employee = employeeRestService.find(id);

        EntityModel<Employee> employeeModel = EntityModel.of(employee)
                .add(linkTo(methodOn(EmployeeRestController.class).find(id)).withSelfRel())
                .add(linkTo(methodOn(EmployeeRestController.class).findAll()).withSelfRel());

        return ResponseEntity.ok(employeeModel);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Employee>> post(@RequestBody Employee employee) {

        EntityModel employeeModel = EntityModel.of(employeeRestService.post(employee));

        return new ResponseEntity<>(employeeModel, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Employee>> update(@RequestBody Employee employee, @PathVariable Long id) {
        EntityModel employeeModel = EntityModel.of(employeeRestService.put(employee, id));

        return new ResponseEntity<>(employeeModel, HttpStatus.ACCEPTED);
    }

}
