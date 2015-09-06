package by.dak.persistence.entities;

import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Date;

/**
 * @author dkoyro
 * @version 0.1 26.09.2008
 * @introduced [Furniture constructor | Iteration 1]
 * @since 2.0.0
 */
@MappedSuperclass
public abstract class PersistenceEntity {
	public static final String PROPERTY_id = "id";
	public static final String PROPERTY_deleted = "deleted";
	public static final String PROPERTY_created = "created";
	public static final String PROPERTY_modified = "modified";

	@Id
	@SequenceGenerator(name = "IDGenerator", sequenceName = "HIBERNATE_SEQUENCE")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "IDGenerator")
	@Column(name = "ID", nullable = false, insertable = true, updatable = false, unique = true)
	private Long id;

	@Column(nullable = false, columnDefinition = "bit default 0")
	private boolean deleted = false;

	@Column
	private Date created;

	@Column
	private Date modified;

	@Transient
	protected PropertyChangeSupport support = new PropertyChangeSupport(this);

	public Long getId() {
		return id;
	}

	public void setId(Long value) {
		this.id = value;
	}

	public boolean isInstanceOf(Class entityClass) {
		Class thisClass = this instanceof HibernateProxy ? ((HibernateProxy) this).writeReplace().getClass()
				: getClass();
		return entityClass.isAssignableFrom(thisClass);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null) {
			return false;
		}

		Class c1 = o instanceof HibernateProxy ? ((HibernateProxy) o).writeReplace().getClass() : o.getClass();
		if (c1 != this.getClass()) {
			return false;
		}

		final PersistenceEntity that = (PersistenceEntity) o;
		return getId() != null && getId() > 0 && getId().equals(that.getId());
	}

	@Override
	public int hashCode() {
		return getId() != null ? getId().hashCode() : super.hashCode();
	}

	public boolean hasId() {
		return getId() != null && getId() > 0;
	}


	public void addPropertyChangeListener(
			PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(
			String propertyName,
			PropertyChangeListener listener) {
		support.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(
			PropertyChangeListener listener) {
		support.removePropertyChangeListener(listener);

	}

	public synchronized void removePropertyChangeListener(
			String propertyName,
			PropertyChangeListener listener) {
		support.removePropertyChangeListener(propertyName, listener);
	}


	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}
}