package hello.repository;

import hello.pojo.DocumentWithTextPages;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface DocumentWithTextPagesRepository extends MongoRepository<DocumentWithTextPages, String> {

//    @Query("{$text:{$search:'?0'}}")
//    @Query("{'options.options':?0, 'pages.text':?1}")
    @Query("{'options.options':?0, 'pages.text':{$regex:?1,$options:'i'}}")
    List<DocumentWithTextPages> search(String vehicle, String keyword);

    @Query("{\"options.options\":\"Savana\"}")
    DocumentWithTextPages getSavana();

}
