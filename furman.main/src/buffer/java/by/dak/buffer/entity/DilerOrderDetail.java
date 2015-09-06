package by.dak.buffer.entity;

import by.dak.persistence.entities.OrderFurniture;
import by.dak.persistence.entities.PersistenceEntity;
import by.dak.persistence.entities.PriceAware;
import by.dak.persistence.entities.Priced;
import by.dak.persistence.entities.predefined.Side;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 05.11.2010
 * Time: 10:54:36
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Proxy(lazy = true)
@Table(name = "DILER_ORDER_DETAIL")
public class DilerOrderDetail extends PersistenceEntity {
	public static final String PROPERTY_dilerOrderItem = "dilerOrderItem";

	@ManyToOne(targetEntity = DilerOrderItem.class)
	@JoinColumns({@JoinColumn(name = "DILER_ORDER_ITEM_ID", nullable = false, referencedColumnName = "ID")})
	private DilerOrderItem dilerOrderItem;

	@Column(name = "DISCRIMINATOR", nullable = false, updatable = true, insertable = true)
	private String discriminator;

	@Column(name = "NUMBER", nullable = true)
	private Long number = 0L;

	@Column(name = "NAME", nullable = false, length = 255)
	private String name = "";//Constants.DEFAULT_DETAIL_NAME;

	@Column(name = "AMOUNT", nullable = true, columnDefinition = "bigint")
	private Integer amount;

	@Column(name = "SIZE", nullable = true)
	private Double size = 1d;

	@Column(name = "FPRIMARY", nullable = true, columnDefinition = "BIT", length = 1)
	private Boolean primary;

	@Column(name = "PRICE", nullable = true)
	private Double price;

	@Column(name = "LENGTH", nullable = true)
	private Long length;

	@Column(name = "WIDTH", nullable = true)
	private Long width;

	@Column(name = "ROTATABLE", nullable = true, columnDefinition = "BIT", length = 1)
	private Boolean rotatable;

	@Column(name = "GLUEING", nullable = true, columnDefinition = "LONGTEXT")
	private String glueing;

	//фрезеровка
	@Column(name = "MILLING", nullable = true, columnDefinition = "LONGTEXT")
	private String milling;

	//Паз
	@Column(name = "GROOVE", nullable = true, columnDefinition = "LONGTEXT")
	private String groove;

	//угол пропила
	@Column(name = "ANGLE45", nullable = true, columnDefinition = "LONGTEXT")
	private String angle45;

	@Column(name = "DRILLING", nullable = true, columnDefinition = "LONGTEXT")
	private String drilling;

	@Column(name = "CUTOFF", nullable = true, columnDefinition = "LONGTEXT")
	private String cutoff;

	@Column(name = "SIDE", nullable = true)
	@Enumerated(EnumType.STRING)
	private Side side = Side.up;

	@Column(name = "TYPE", nullable = true)
	private String type;

	@ManyToOne(targetEntity = PriceAware.class)
	@JoinColumns({@JoinColumn(name = "FURNITURE_TYPE_ID", nullable = true, referencedColumnName = "ID")})
	private PriceAware furnitureType;

	@ManyToOne(targetEntity = PriceAware.class)
	@JoinColumns({@JoinColumn(name = "COMLEX_BOARD_DEF_ID", nullable = true, referencedColumnName = "ID")})
	private PriceAware comlexFurnitureType;

	@ManyToOne(targetEntity = Priced.class)
	@JoinColumns({@JoinColumn(name = "FURNITURE_CODE_ID", nullable = true, referencedColumnName = "ID")})
	private Priced furnitureCode;

	@ManyToOne(targetEntity = OrderFurniture.class)
	@JoinColumns({@JoinColumn(name = "SECOND_BOARD_ID", nullable = true, referencedColumnName = "ID")})
	private OrderFurniture second;

	/**
	 * для установки связи dilerOrderDetailt -> dilerOrderItem
	 */
	@Transient
	private Long orderItemId;

	public DilerOrderItem getDilerOrderItem() {
		return dilerOrderItem;
	}

	public void setDilerOrderItem(DilerOrderItem dilerOrderItem) {
		this.dilerOrderItem = dilerOrderItem;
	}

	public boolean isRotatable() {
		return rotatable;
	}

	public String getDiscriminator() {
		return discriminator;
	}

	public Long getNumber() {
		return number;
	}

	public String getName() {
		return name;
	}

	public Integer getAmount() {
		return amount;
	}

	public Double getSize() {
		return size;
	}

	public Boolean getPrimary() {
		return primary;
	}

	public Double getPrice() {
		return price;
	}

	public Long getLength() {
		return length;
	}

	public Long getWidth() {
		return width;
	}

	public boolean getRotatable() {
		return rotatable;
	}

	public String getGlueing() {
		return glueing;
	}

	public String getMilling() {
		return milling;
	}

	public String getGroove() {
		return groove;
	}

	public String getAngle45() {
		return angle45;
	}

	public String getDrilling() {
		return drilling;
	}

	public String getCutoff() {
		return cutoff;
	}

	public Side getSide() {
		return side;
	}

	public String getType() {
		return type;
	}

	public OrderFurniture getSecond() {
		return second;
	}

	public PriceAware getFurnitureType() {
		return furnitureType;
	}

	public PriceAware getComlexFurnitureType() {
		return comlexFurnitureType;
	}

	public Priced getFurnitureCode() {
		return furnitureCode;
	}

	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public void setDiscriminator(String discriminator) {
		this.discriminator = discriminator;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public void setSize(Double size) {
		this.size = size;
	}

	public void setPrimary(Boolean primary) {
		this.primary = primary;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	public void setWidth(Long width) {
		this.width = width;
	}

	public void setRotatable(Boolean rotatable) {
		this.rotatable = rotatable;
	}

	public void setGlueing(String glueing) {
		this.glueing = glueing;
	}

	public void setMilling(String milling) {
		this.milling = milling;
	}

	public void setGroove(String groove) {
		this.groove = groove;
	}

	public void setAngle45(String angle45) {
		this.angle45 = angle45;
	}

	public void setDrilling(String drilling) {
		this.drilling = drilling;
	}

	public void setCutoff(String cutoff) {
		this.cutoff = cutoff;
	}

	public void setSide(Side side) {
		this.side = side;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setFurnitureType(PriceAware furnitureType) {
		this.furnitureType = furnitureType;
	}

	public void setComlexFurnitureType(PriceAware comlexFurnitureType) {
		this.comlexFurnitureType = comlexFurnitureType;
	}

	public void setFurnitureCode(Priced furnitureCode) {
		this.furnitureCode = furnitureCode;
	}

	public void setSecond(OrderFurniture second) {
		this.second = second;
	}
}
