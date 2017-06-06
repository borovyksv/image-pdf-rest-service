package hello.repository;

import hello.pojo.DocumentWithTextPages;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface DocumentWithTextPagesRepository extends MongoRepository<DocumentWithTextPages, String> {

    @Cacheable("documents")
    @Query("{'vendor':?0, 'model':?1, 'year':?2, 'pages.text':{$regex:?3,$options:'i'}}")
    List<DocumentWithTextPages> search(String vendor, String model, Integer year, String keyword);

    @Query("{'options.options':?0, 'pages.text':{$regex:?1,$options:'i'}}")
    List<DocumentWithTextPages> search(String vehicle, String keyword);


    @Query("{'vendor':?0, 'model':?1, 'year':?2}, {'_id':0, '_class':0, 'folder':'0', 'pages':0}")
    List<DocumentWithTextPages> findBookmarks(String vendor, String vehicle, Integer year);

    @Cacheable("ids")
    DocumentWithTextPages findById(String id);
}
