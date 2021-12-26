package example.blob.service;

import example.blob.dao.EmployeeFileRepository;
import example.blob.entity.Employee;
import example.blob.entity.EmployeeFile;
import example.blob.entity.FileType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Stream;


/**
 * EmployeeFile service Layer
 * has upload method that save the file in the database
 * accept for argument and set the file properties based on these arguments
 *
 * upload API is also add a file Entity to Employee Entity  by the Id we passed in the url parameter
 *
 * download is get the file from db
 *
 * upload is set the file from persist to the file from the form data
 * then set each of file Properties to the parameters passed in the url argument if they exist.
 *
 * download all is get all file from db.
 */
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
        return employeeFileRepository.findById(id)
                .orElseThrow( () -> new RuntimeException("cannot Download the Employee file ") );
    }

    public Stream<EmployeeFile> downloadAll() {
        return employeeFileRepository.findAll().stream();
    }

    public EmployeeFile update(MultipartFile formDatafile, Long fileId , String fileDescription, String fileType) {
        return employeeFileRepository.findById(fileId).map(
                persistFile -> {
                    if (formDatafile != null)
                           persistFile.setFile(formDatafile);

                    if (fileDescription != null)
                        persistFile.setDescription(fileDescription);

                    if (fileType != null) {
                        persistFile.setFileType(FileType.valueOf(fileType.toUpperCase()));
                    }

                    return employeeFileRepository.save(persistFile);
                }
        ).orElseThrow(
                () -> new RuntimeException("can not update the file")
        );
    }
}
