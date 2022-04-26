package DocumentManager.Project;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="documents")
@NoArgsConstructor
public class Document {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length = 512, nullable=false, unique=true)
	private String name;
	
	private Long size;
	
	@Column(name="upload_time")
	private Date UploadTime;
	
	private byte[] content;

	public Document(int id, String name, Long size) {
		super();
		this.id = id;
		this.name = name;
		this.size = size;
	}
	
	
}
