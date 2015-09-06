package by.dak.persistence.entities;

import by.dak.persistence.cutting.entities.AStripsEntity;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Entity
@Proxy(lazy = false)

@Table(name = "STORAGE_ELEMENT_LINK")

public class StorageElementLink extends PersistenceEntity
{
    public static final String PROPERTY_stripsEntity = "stripsEntity";
    public static final String PROPERTY_amount = "amount";
    public static final String PROPERTY_storeElement = "storeElement";

    @Column(name = "AMOUNT", nullable = false, columnDefinition = "bigint")
    private Integer amount = 0;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumns({@JoinColumn(name = "STRIPS_ID", referencedColumnName = "ID")})
    private AStripsEntity stripsEntity;

	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumns({@JoinColumn(name = "FURNITURE_ID", referencedColumnName = "ID")})
    private AStoreElement storeElement;

    public final static StorageElementLink NULL = new StorageElementLink();

    public AStoreElement getStoreElement()
    {
        return storeElement;
    }

    public void setStoreElement(AStoreElement storeElement)
    {
        this.storeElement = storeElement;
    }


    public Integer getAmount()
    {
        return amount;
    }

    public void setAmount(Integer amount)
    {
        this.amount = amount;
    }

    public AStripsEntity getStripsEntity()
    {
        return stripsEntity;
    }

    public void setStripsEntity(AStripsEntity stripsEntity)
    {
        this.stripsEntity = stripsEntity;
    }
}
