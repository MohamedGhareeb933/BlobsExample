package example.blob.service;

import example.blob.controller.DbFileRestController;
import example.blob.dao.DBFileRepository;
import example.blob.entity.DBFile;
import org.hibernate.EntityMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.parser.Entity;
import java.util.stream.Stream;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class DbRestServiceImple implements DbRestService{

    @Autowired
    DBFileRepository dbFileRepository;

    @Override
    public DBFile upload(MultipartFile file) throws Exception {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        DBFile dbFile = new DBFile(fileName, file.getBytes(), file.getContentType());

        return dbFileRepository.save(dbFile);
    }

    @Override
    public DBFile download(Long id) {
        return dbFileRepository.findById(id).get();
    }

    @Override
    public Stream<DBFile> downloadAll() {
        return dbFileRepository.findAll().stream();
    }
}
