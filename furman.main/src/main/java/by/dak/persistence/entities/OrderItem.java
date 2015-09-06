package by.dak.persistence.entities;

import by.dak.persistence.convert.OrderItem2StringConverter;
import by.dak.persistence.entities.predefined.OrderItemType;
import by.dak.persistence.entities.validator.OrderItemValidator;
import by.dak.utils.convert.StringValue;
import by.dak.utils.validator.Validator;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORDER_ITEM")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DISCRIMINATOR")
@DiscriminatorValue(value = "OrderItem")
@DiscriminatorOptions(force = true)

@Validator(validatorClass = OrderItemValidator.class)
@StringValue(converterClass = OrderItem2StringConverter.class)
public class OrderItem extends PersistenceEntity {
	public static final String DEFAULT_NAME = "Default";

	public static final String PROPERTY_details = "details";
	public static final String PROPERTY_name = "name";
	public static final String PROPERTY_description = "description";
	public static final String PROPERTY_fileUuid = "fileUuid";
	public static final String PROPERTY_amount = "amount";
	public static final String PROPERTY_order = "order";

	public static final String PROPERTY_type = "type";
	public static final String PROPERTY_number = "number";

	@Column(name = "TYPE", nullable = false)
	@Enumerated(EnumType.STRING)
	private OrderItemType type;


	@Column(name = "NUMBER", nullable = false)
	private Long number = 1L;

	@Column(name = "DESIGN", nullable = true, columnDefinition = "longtext")
	private String design;

	@Column(name = "DISCRIMINATOR", nullable = false, updatable = false, insertable = false)
	private String discriminator;


	@Column(name = "NAME", nullable = false, length = 255)
	private String name;

	/**
	 * Description of the Order Item
	 */
	@Column(name = "DESCRIPTION", nullable = true, columnDefinition = "longtext")
	private String description;

	/**
	 * File uuid with representation of the order item saved in repository
	 */
	@Column(name = "FILE_UUID", nullable = true)
	private String fileUuid;

	@OneToMany(mappedBy = "orderItem", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<AOrderDetail> details = new ArrayList<AOrderDetail>();

	@Column(name = "AMOUNT", nullable = false, columnDefinition = "bigint")
	private Integer amount = 1;

	@ManyToOne
	@JoinColumn(name = "ORDER_ID", nullable = false)
	private AOrder order;

	@ManyToOne
	private OrderItem source;

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}


	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDetails(List<AOrderDetail> details) {
		this.details = details;
	}

	public List<AOrderDetail> getDetails() {
		return details;
	}

	public void addDetail(AOrderDetail detail) {
		detail.setOrderItem(this);
		getDetails().add(detail);
	}


	public String getDiscriminator() {
		return discriminator;
	}

	public void setDiscriminator(String discriminator) {
		this.discriminator = discriminator;
	}

	public AOrder getOrder() {
		return order;
	}

	public void setOrder(AOrder order) {
		this.order = order;
	}


	public String getDesign() {
		return design;
	}

	public void setDesign(String design) {
		this.design = design;
	}

	public OrderItemType getType() {
		return type;
	}

	public void setType(OrderItemType type) {
		this.type = type;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public static OrderItem valueOf(OrderItem orderItems) {
		return null;
	}


	public static List<OrderItem> valueOf(List<OrderItem> orderItems) {
		return null;
	}

	public OrderItem getSource() {
		return source;
	}

	public void setSource(OrderItem source) {
		this.source = source;
	}
}