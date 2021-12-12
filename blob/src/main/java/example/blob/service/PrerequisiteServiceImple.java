package example.blob.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.blob.controller.DbFileRestController;
import example.blob.controller.PrerequisiteRestController;
import example.blob.dao.PrerequisiteRepository;
import example.blob.entity.DBFile;
import example.blob.entity.Prerequisite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
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

    public ResponseEntity<CollectionModel> getAll() {

        List<EntityModel> modelList = prerequisiteRepository.findAll().stream().map(
                prerequisite -> {
                    return EntityModel.of(prerequisite)
                            .add(linkTo(methodOn(PrerequisiteRestController.class).get(prerequisite.getId())).withSelfRel());
                }
        ).collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(modelList));

    }

    public ResponseEntity<CollectionModel> get(Long id) {

        Prerequisite prerequisite = prerequisiteRepository.findById(id).get();

        if (prerequisite.getAttachment().size() > 0 && prerequisite.getAttachment() != null){
            List<EntityModel> models = getEntityModels(prerequisite);
            return ResponseEntity.ok(CollectionModel.of(models));

        }else {
            return ResponseEntity.ok(CollectionModel.of(Arrays.asList(prerequisite)));
        }

    }

    private List<EntityModel> getEntityModels(Prerequisite prerequisite) {

        return prerequisite.getAttachment().stream().map(
                attach -> {
                    return  EntityModel.of(prerequisite)
                            .add(linkTo(methodOn(PrerequisiteRestController.class).get(prerequisite.getId())).withSelfRel())
                            .add(linkTo(methodOn(DbFileRestController.class).download(attach.getId())).withSelfRel());
                }
        ).collect(Collectors.toList());

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


