package example.blob.service;

import example.blob.entity.DBFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Stream;

public interface DbRestService {

    DBFile upload(MultipartFile file) throws Exception;

    DBFile download(Long id);

    Stream<DBFile> downloadAll();

}
