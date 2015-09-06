package by.dak.category;

import by.dak.category.converter.Category2StringConverter;
import by.dak.category.validator.CategoryValidator;
import by.dak.persistence.entities.PersistenceEntity;
import by.dak.utils.convert.StringValue;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.List;

/**
 * User: akoyro
 * Date: 24.03.11
 * Time: 12:53
 */

@Entity
@Proxy(lazy = false)

@Table(name = "CATEGORY")
@StringValue(converterClass = Category2StringConverter.class)
@by.dak.utils.validator.Validator(validatorClass = CategoryValidator.class)

public class Category extends PersistenceEntity {
	public static final String PROPERTY_name = "name";
	public static final String PROPERTY_parent = "parent";

	@Column(nullable = false)
	private String name;

	@Column(columnDefinition = "longtext")
	private String description;


	@Column(name = "FILE_UUID")
	private String fileUuid;

	@ManyToOne
	private Category parent;

	@OneToMany
	@JoinColumn(name = "parent_id")
	private List<Category> children;

	public String getFileUuid() {
		return fileUuid;
	}

	public void setFileUuid(String fileUuid) {
		this.fileUuid = fileUuid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public List<Category> getChildren() {
		return children;
	}

	public void setChildren(List<Category> children) {
		this.children = children;
	}
}
