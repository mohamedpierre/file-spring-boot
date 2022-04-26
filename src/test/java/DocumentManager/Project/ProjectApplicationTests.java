package DocumentManager.Project;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ProjectApplicationTests {
	
	@Autowired
	private DocumentRepository docr;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	@Rollback(false)
	@Transactional
	void testInsertDocument() throws IOException {
		String path="C:\\Users\\LENOVO\\Downloads\\Docs\\List_mini projet.pdf";
		File file = new File(path);
		Document doc = new Document();
		doc.setName(file.getName());
		byte[] bytes = Files.readAllBytes(file.toPath());
		doc.setContent(bytes);
//		doc.setSize(bytes.length);
		doc.setUploadTime(new Date());
		Document doc2 = docr.save(doc);
		
		Document existDoc = entityManager.find(Document.class, doc2.getSize());
		assertThat(existDoc.getSize()).isEqualTo(bytes.length);
	}

}
