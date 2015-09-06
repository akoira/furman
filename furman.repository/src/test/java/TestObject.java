import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "testobjects")
public class TestObject {

	private String id;

	private String name;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
