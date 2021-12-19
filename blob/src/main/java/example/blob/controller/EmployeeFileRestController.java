package example.blob.controller;


import example.blob.dto.ResponseFile;
import example.blob.dto.ResponseMessage;
import example.blob.entity.EmployeeFile;
import example.blob.service.EmployeeFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/files")
public class EmployeeFileRestController {

    @Autowired
    EmployeeFileService employeeFileService;

    @PostMapping
    public ResponseEntity<ResponseMessage> upload(
            @RequestPart String description,
            @RequestPart MultipartFile file,
            @RequestPart String employeeId,
            @RequestPart String type)  {
        try {
            employeeFileService.upload(description, file, employeeId, type);
            return new ResponseEntity<>(new ResponseMessage("file uploaded successfully"), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("failed to up uploaded file"), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping
    public ResponseEntity<List<ResponseFile>> downloadAll() {
        List<ResponseFile> files = employeeFileService.downloadAll().map(
                dbFile -> {
                    String fileDownloadUri = ServletUriComponentsBuilder
                            .fromCurrentRequestUri()
                            .path(String.format("/%s",dbFile.getId()))
                            .toUriString();

                    return new ResponseFile(dbFile.getName(), fileDownloadUri, dbFile.getExtension());
                }
        ).collect(Collectors.toList());

        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> download(@PathVariable Long id) {
        EmployeeFile file = employeeFileService.download(id);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", file.getName()))
                .body(file.getData());
    }



}
