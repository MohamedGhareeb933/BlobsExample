package example.blob.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.blob.controller.DbFileRestController;
import example.blob.controller.PrerequisiteRestController;
import example.blob.dao.DBFileRepository;
import example.blob.dao.PrerequisiteRepository;
import example.blob.entity.DBFile;
import example.blob.entity.Prerequisite;
import org.hibernate.EntityMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PrerequisiteServiceImple {

    @Autowired
    PrerequisiteRepository prerequisiteRepository;

    @Autowired
    DbRestServiceImple dbRestServiceImple;

    @Autowired
    DbRestService dbRestService;

    public ResponseEntity<CollectionModel<Prerequisite>> getAll() {

        List<Prerequisite> prerequisiteList = prerequisiteRepository.findAll();

        CollectionModel collectionModel =
                CollectionModel.of(prerequisiteList)
                        .add(linkTo(methodOn(DbFileRestController.class).downloadAll()).withRel("Attachments"))
                        .add(linkTo(methodOn(PrerequisiteRestController.class).getAll()).withRel("Prerequisites"));

        return new ResponseEntity<>(collectionModel, HttpStatus.OK);

    }

    public ResponseEntity<EntityModel<Prerequisite>> get(Long id) {

        Prerequisite prerequisite = prerequisiteRepository.findById(id).get();

        prerequisite.getAttachment().stream().map(
                attach -> {
                   return  EntityModel.of(attach)
                           .add(linkTo(methodOn(DbFileRestController.class)
                                   .download(attach.getId())).withSelfRel());
                }
        ).collect(Collectors.toList());


        EntityModel entityModel = EntityModel.of(prerequisite)
                .add(linkTo(methodOn(PrerequisiteRestController.class).get(id)).withSelfRel());

        return ResponseEntity.ok(entityModel);
    }

    public ResponseEntity<EntityModel<Prerequisite>> post(String prerequisite , MultipartFile file) {


        DBFile dbFile = new DBFile();
        Prerequisite prerequisiteFromFormFile = new Prerequisite();

        try {
            if (file != null)
                dbFile =  dbRestServiceImple.upload(file);

            ObjectMapper mapper =  new ObjectMapper();

            prerequisiteFromFormFile = mapper.readValue(prerequisite, Prerequisite.class);
            prerequisiteFromFormFile.add(dbFile);
            Prerequisite prerequisite1 =  prerequisiteRepository.save(prerequisiteFromFormFile);

            EntityModel entityModel = EntityModel.of(prerequisite1)
                    .add(linkTo(methodOn(DbFileRestController.class).download(dbFile.getId())).withSelfRel())
                    .add(linkTo(methodOn(PrerequisiteRestController.class).getAll()).withRel("prerequisites"));

            return new ResponseEntity<>(entityModel, HttpStatus.OK);


        }catch (Exception exc) {
            throw new RuntimeException("Cannot upload the file");
        }

    }

}

