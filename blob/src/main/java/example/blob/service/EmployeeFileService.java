package example.blob.service;

import example.blob.dao.EmployeeFileRepository;
import example.blob.entity.Employee;
import example.blob.entity.EmployeeFile;
import example.blob.entity.FileType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
public class EmployeeFileService {

    @Autowired
    EmployeeFileRepository employeeFileRepository;

    @Autowired
    EmployeeRestService employeeRestService;

    public EmployeeFile upload(String description, MultipartFile file, String id, String type) {

        EmployeeFile employeeFile = new EmployeeFile(file, description, type);
        Employee persistEmployee = findEmployee(id);
        persistEmployee.add(employeeFile);

        return employeeFileRepository.save(employeeFile);
    }

    private Employee findEmployee(String id) {
        return employeeRestService.find(Long.valueOf(id));
    }

    public EmployeeFile download(Long id) {
        return employeeFileRepository.findById(id).get();
    }

    public Stream<EmployeeFile> downloadAll() {
        return employeeFileRepository.findAll().stream();
    }

    public EmployeeFile update(MultipartFile file, Long id , String description, String type) {
        return employeeFileRepository.findById(id).map(
                fileFromPersist -> {
                    if (file != null)
                           fileFromPersist.setFile(file);

                    if (description != null)
                        fileFromPersist.setDescription(description);

                    if (type != null) {
                        fileFromPersist.setFileType(FileType.valueOf(type.toUpperCase()));
                    }

                    return employeeFileRepository.save(fileFromPersist);
                }
        ).orElseThrow(
                () -> new RuntimeException("can not update the file")
        );
    }
}
