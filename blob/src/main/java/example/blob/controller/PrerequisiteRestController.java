package example.blob.controller;

import example.blob.dto.ResponseFile;
import example.blob.dto.ResponseMessage;
import example.blob.entity.DBFile;
import example.blob.entity.Prerequisite;
import example.blob.service.PrerequisiteServiceImple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping("/prerequisites")
public class PrerequisiteRestController {

    @Autowired
    private PrerequisiteServiceImple prerequisiteServiceImple;

    @Autowired
    private DbFileRestController dbFileRestController;

    @GetMapping
    public ResponseEntity<CollectionModel<Prerequisite>> getAll() {
        return prerequisiteServiceImple.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Prerequisite>> get(@PathVariable Long id) {
        return prerequisiteServiceImple.get(id);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Prerequisite>> post(@RequestPart String prerequisite , @RequestPart MultipartFile file) {

         return prerequisiteServiceImple.post(prerequisite , file);

    }


}
