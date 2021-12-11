package example.blob.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "prerequisite")
public class Prerequisite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;

   @OneToMany(mappedBy = "prerequisite",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   @JsonManagedReference
   private Set<DBFile> attachment = new HashSet<>();

    public Prerequisite() {
    }

    public Prerequisite(String name, String description, Set<DBFile> attachment) {
        this.name = name;
        this.description = description;
        this.attachment = attachment;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<DBFile> getAttachment() {
        return attachment;
    }

    public void setAttachment(Set<DBFile> attachment) {
        this.attachment = attachment;
    }

    public void add(DBFile dbFile) {

       if (attachment == null) {
           attachment = new HashSet<>();
       }

       attachment.add(dbFile);
       dbFile.setPrerequisite(this);
   }
}
