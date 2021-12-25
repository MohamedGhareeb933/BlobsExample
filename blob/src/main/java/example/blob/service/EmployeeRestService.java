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
        return employeeRepository.findById(id).orElseThrow( () ->
                new RuntimeException("cannot find Employee")
        );
    }


    public Employee post(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee put(Employee fromPayload, Long id) {

        return employeeRepository.findById(id).map(
                fromPersist -> {
                    if (fromPayload.getFirstName() != null)
                        fromPersist.setFirstName(fromPayload.getFirstName());

                    if (fromPayload.getLastName() != null)
                        fromPersist.setLastName(fromPayload.getLastName());

                    if (fromPayload.getEmail() !=null)
                        fromPersist.setEmail(fromPayload.getEmail());

                    if (fromPayload.getAge() != 0)
                        fromPersist.setAge(fromPayload.getAge());

                    return employeeRepository.save(fromPersist);
                }
        ).orElseThrow( () ->
                new RuntimeException("cannot update Employee")
        );
    }

}
