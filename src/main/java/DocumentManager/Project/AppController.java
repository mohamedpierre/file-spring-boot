package DocumentManager.Project;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@Controller
//@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AppController {
	@Autowired
	private DocumentRepository documentRepository;
	
	@GetMapping("/")
//	@RequestMapping(value="/controller")
//	@ResponseBody
	public String TestHome(Model model) {
		List<Document> listDocs = documentRepository.findAll();
		model.addAttribute("listDocs",listDocs);
		
		return "test";
	}
	
	
	
	@GetMapping("/allFiles")
	public Iterable<Document> allDocs(){
		return documentRepository.findAll();
	}
	
//	@GetMapping("/allFiles/id")
//	public Optional<Document> aDocument(@PathVariable("id") int id){
//		return documentRepository.findById(id);
//	}
	
	@PostMapping("/upload")
	public void UploadingFile(@RequestParam("file") MultipartFile multipartFile,
			RedirectAttributes ra) throws IOException {
		String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		
		 Document doc = new Document();
		 doc.setName(filename);
		 
		 doc.setContent(multipartFile.getBytes());
		 doc.setSize(multipartFile.getSize());
		 doc.setUploadTime(new Date());
		 documentRepository.save(doc);
		 ra.addFlashAttribute("message", "uploaded succefully");
		 
//		return "<a href=\"http://localhost:4200/\">here</a>;a.click()";
	}
	
	@GetMapping("/download")
	public void downloadFile(@Param("id") int id,HttpServletResponse response) throws IOException {
		Optional<Document> document = documentRepository.findById(id);
		Document doc = document.isPresent() ? document.get():null;
		response.setContentType("aplication/octet-stream");
		String headrKey = "Content-Disposition";
		String headerValue = "attachement; filename="+doc.getName();
		response.setHeader(headrKey, headerValue);
		ServletOutputStream sos = response.getOutputStream();
		sos.write(doc.getContent());
		sos.close();
	}
}
