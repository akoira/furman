package by.dak.buffer.entity;

import by.dak.persistence.entities.Customer;
import by.dak.persistence.entities.Dailysheet;
import by.dak.persistence.entities.OrderStatus;
import by.dak.persistence.entities.PersistenceEntity;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 05.11.2010
 * Time: 10:53:33
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "DILER_ORDER")
public class DilerOrder extends PersistenceEntity {
	public static String PROPERTY_customer = "customer";
	public static String PROPERTY_created_dailysheet = "createdDailySheet";

	@Column(name = "NAME", nullable = true, length = 255)
	private String name;

	@Column(name = "DESCRIPTION", nullable = true, length = 255, columnDefinition = "longtext")
	private String description;

	@Column(name = "READY_DATE", nullable = true)
	private Date readyDate;

	@Column(name = "COST", nullable = true)
	private Double cost;

	@Column(name = "STATUS", nullable = true)
	@Enumerated(EnumType.STRING)
	private OrderStatus status = OrderStatus.design;

	@ManyToOne(targetEntity = Dailysheet.class)
	@JoinColumns(
			{
					@JoinColumn(name = "CREATED_DAILY_SHEET_ID", nullable = true, referencedColumnName = "ID")
			}
	)
	private Dailysheet createdDailySheet;

	@ManyToOne(targetEntity = Customer.class)
	@JoinColumns(
			{
					@JoinColumn(name = "CUSTOMER_ID", nullable = true, referencedColumnName = "ID")
			}
	)
	private Customer customer;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dilerOrder")
	private List<DilerOrderItem> dilerOrderItems;


	public List<DilerOrderItem> getDilerOrderItems() {
		return dilerOrderItems;
	}


	public void setDilerOrderItems(List<DilerOrderItem> dilerOrderItems) {
		this.dilerOrderItems = dilerOrderItems;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setReadyDate(Date readyDate) {
		this.readyDate = readyDate;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public Date getReadyDate() {
		return readyDate;
	}

	public Double getCost() {
		return cost;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Dailysheet getCreatedDailySheet() {
		return createdDailySheet;
	}

	public void setCreatedDailySheet(Dailysheet createdDailySheet) {
		this.createdDailySheet = createdDailySheet;
	}
}


