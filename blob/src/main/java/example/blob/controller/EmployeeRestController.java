package example.blob.controller;


import example.blob.dto.ResponseFile;
import example.blob.entity.Employee;
import example.blob.entity.EmployeeFile;
import example.blob.service.EmployeeRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;
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

        /*employee.getFiles().forEach(
                employeeFile -> {
                    employeeModel.add(linkTo(methodOn(EmployeeFileRestController.class).download(employeeFile.getId())).withSelfRel());
                }
        );*/

        employee.getFiles().forEach(
                employeeFile -> { new ResponseFile(employeeFile);
                }
        );

        return ResponseEntity.ok(employeeModel);
    }

    @PostMapping
    public Employee post(@RequestBody Employee employee) {
        return employeeRestService.post(employee);
    }

    private Set<EntityModel<EmployeeFile>> employeeFileEntityModel(Set<EmployeeFile> employeeFiles) {

        if (employeeFiles == null)
            Collections.emptySet();

        return employeeFiles.stream().map(
                employeeFile -> {
                    return EntityModel.of(employeeFile)
                            .add(linkTo(methodOn(EmployeeFileRestController.class).download(employeeFile.getId())).withSelfRel());
                }
        ).collect(Collectors.toSet());
    }

}
