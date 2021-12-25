package example.blob.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.*;


@Entity
@Table(name = "employee_file")
public class EmployeeFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "data")
    @Lob
    @JsonIgnore
    private byte[] data;

    @Column(name = "extension")
    private String extension;

    @Column(name = "description")
    private String description;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    @JsonBackReference
    private Employee employee;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private FileType fileType;

    @Transient
    private String uri;

    public EmployeeFile(MultipartFile file, String description, String type)  {

        try {

            this.description = description;
            this.name = StringUtils.cleanPath(file.getOriginalFilename());
            this.data = file.getBytes();
            this.extension = file.getContentType();
            this.fileType = FileType.valueOf(type.toUpperCase());

        }catch (Exception exc) {
            throw new RuntimeException("cannot create new instance for File" + exc);
        }

    }

    public EmployeeFile(MultipartFile file)  {

        try {
            this.name = StringUtils.cleanPath(file.getOriginalFilename());
            this.data = file.getBytes();
            this.extension = file.getContentType();
        }catch (Exception exc) {
            throw new RuntimeException("cannot create new instance for File" + exc);
        }

    }

    public EmployeeFile() {
    }

    public EmployeeFile(Long id, String name, byte[] data, String extension, String description, Employee employee, FileType fileType) {
        this.id = id;
        this.name = name;
        this.data = data;
        this.extension = extension;
        this.description = description;
        this.employee = employee;
        this.fileType = fileType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public String getUri() {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(String.format("/files/%s" ,this.id))
                .toUriString();
    }

    public void setFile(MultipartFile file) {

        try {
            this.name = StringUtils.cleanPath(file.getOriginalFilename());
            this.data = file.getBytes();
            this.extension = file.getContentType();
        }catch (Exception exc) {
            throw new RuntimeException("cannot create new instance for File" + exc);
        }

    }

}
