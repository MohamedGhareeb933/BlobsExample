package example.blob.dto;

import example.blob.entity.EmployeeFile;
import example.blob.entity.FileType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseFile {

    private String name;

    private String url;

    private String extension;

    private FileType type;

    private String description;

    public ResponseFile(EmployeeFile employeeFile) {
        this.name = employeeFile.getName();
        this.extension =  employeeFile.getExtension();
        this.type =  employeeFile.getFileType();
        this.description = employeeFile.getDescription();

        this.url = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path(String.format("/%s", employeeFile.getId()))
                .toUriString();
    }

}
