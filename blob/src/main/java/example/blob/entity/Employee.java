package example.blob.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


/**
 * Entity for Employee that has Employee info like Name Age and Email
 * Employee can possibly has many files like resume, National ID or Graduate certificate
 */
@Entity(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "age")
    private short age;

   @OneToMany(mappedBy = "employee",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   @JsonManagedReference
   private Set<EmployeeFile> files = new HashSet<>();

    public Employee() {
    }

    public Employee(Long id, String firstName, String lastName, String email, short age, Set<EmployeeFile> files) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.files = files;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public short getAge() {
        return age;
    }

    public void setAge(short age) {
        this.age = age;
    }

    public Set<EmployeeFile> getFiles() {
        return files;
    }

    public void setFiles(Set<EmployeeFile> files) {
        this.files = files;
    }

    public void add(EmployeeFile resume) {
       if (files == null) {
           files = new HashSet<>();
       }
       files.add(resume);
       resume.setEmployee(this);
   }


}
