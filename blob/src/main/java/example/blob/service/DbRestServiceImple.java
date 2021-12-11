package example.blob.service;

import example.blob.dao.DBFileRepository;
import example.blob.entity.DBFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Stream;

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
