package hello.repository;

import hello.pojo.selectors.Selector;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SelectorRepository extends MongoRepository<Selector, String> {
}
