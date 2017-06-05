package hello.repository;

import hello.pojo.DocumentWithTextPages;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface DocumentWithTextPagesRepository extends MongoRepository<DocumentWithTextPages, String> {

    @Query("{'vendor':?0, 'model':?1, 'year':?2, 'pages.text':{$regex:?3,$options:'i'}}")
    List<DocumentWithTextPages> search(String vendor, String model, Integer year, String keyword);

    @Query("{'options.options':?0, 'pages.text':{$regex:?1,$options:'i'}}")
    List<DocumentWithTextPages> search(String vehicle, String keyword);

    @Query("{\"options.options\":\"Savana\"}")
    DocumentWithTextPages getSavana();


    @Query("{'options.options':?0}, {'_id':0, 'name':1, 'bookmarks':1, 'options':1}")
    List<DocumentWithTextPages> getDocsByVehicle(String vehicle);

}
