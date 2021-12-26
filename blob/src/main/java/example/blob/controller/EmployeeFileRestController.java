package example.blob.controller;


import example.blob.dto.ResponseMessage;
import example.blob.entity.EmployeeFile;
import example.blob.service.EmployeeFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


/**
 * Employee File Rest Controller Layer
 *  has four Http Method upload/update download/all
 *
 *  upload method get 4 argument from url parameters
 *  form-data file, Description, id, type Parameters
 *
 *  download all is getting a list of the files
 *
 *  download file is using Content disposition header to download the file
 *
 *  update accept multiple parameter for setters update the persisted data
 *
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/files")
public class EmployeeFileRestController {

    @Autowired
    EmployeeFileService employeeFileService;

    @PostMapping
    public ResponseEntity<ResponseMessage> upload(
            @RequestPart MultipartFile file,
            @RequestParam("description") String description,
            @RequestParam("id") String employeeId,
            @RequestParam("type") String fileType)  {
        try {
            employeeFileService.upload(description, file, employeeId, fileType);
            return new ResponseEntity<>(new ResponseMessage("file uploaded successfully"), HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("failed to upload the file"), HttpStatus.FORBIDDEN);
        }
    }


    @GetMapping
    public ResponseEntity<CollectionModel<EmployeeFile>> downloadAll() {

        CollectionModel<EmployeeFile> fileCollection =
                CollectionModel.of(employeeFileService.downloadAll().collect(Collectors.toList()))
                        .add(linkTo(methodOn(EmployeeFileRestController.class).downloadAll()).withSelfRel());

        return new ResponseEntity<>(fileCollection, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<byte[]> download(@PathVariable Long id) {

        EmployeeFile file = employeeFileService.download(id);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", file.getName()))
                .body(file.getData());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> update(@RequestPart MultipartFile file,@PathVariable Long id ,@RequestParam String description,@RequestParam String type) {

        try {
            employeeFileService.update(file, id, description, type);
            return new ResponseEntity<>(new ResponseMessage("File Updated"), HttpStatus.ACCEPTED);
        } catch (Exception exception) {
            return new ResponseEntity<>(new ResponseMessage("failed to update the file"), HttpStatus.ACCEPTED);
        }
    }
}
