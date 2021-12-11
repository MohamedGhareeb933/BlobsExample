package example.blob.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "file")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DBFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "data")
    @Lob
    @JsonIgnore
    private byte[] data;

    @Column(name = "type")
    private String type;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "prerequisite_id")
    @JsonBackReference
    private Prerequisite prerequisite;

    public DBFile(String name, byte[] data, String type) {
        this.name = name;
        this.data = data;
        this.type = type;
    }
}
