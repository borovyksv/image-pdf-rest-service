package hello.repository;

import hello.pojo.DocumentWithTextPages;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocumentWithTextPagesRepository extends MongoRepository<DocumentWithTextPages, String> {

}
