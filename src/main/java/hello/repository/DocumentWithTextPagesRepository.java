package hello.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocumentWithTextPagesRepository extends MongoRepository<DocumentWithTextPagesRepository, String> {
}
