package DocumentManager.Project;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

//import antlr.collections.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer>{
	
	@Query("from Document order by Upload_time desc")
	List<Document> findAll();
}
