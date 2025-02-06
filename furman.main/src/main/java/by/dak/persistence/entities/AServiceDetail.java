package by.dak.persistence.entities;

import javax.persistence.*;

@MappedSuperclass
public abstract class AServiceDetail extends AOrderDetail<PriceAware, Service> {
    public static String PROPERTY_priceAware = "priceAware";
    public static String PROPERTY_service = "service";


    @Column(name = "SIZE", nullable = false)
    private Double size = 1d;

    public AServiceDetail() {
    }


    public PriceAware getPriceAware() {
        return super.getPriceAware();
    }

    public void setPriceAware(PriceAware priceAware) {
        PriceAware old = getPriceAware();
        super.setPriceAware(priceAware);
        support.firePropertyChange(PROPERTY_priceAware, old, priceAware);
    }

    public Service getService() {
        return getPriced();
    }

    public void setService(Service service) {
        Service old = getPriced();
        setPriced(service);
        support.firePropertyChange(PROPERTY_service, old, service);
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AServiceDetail that = (AServiceDetail) o;

        if (getSize() != null ? !getSize().equals(that.getSize()) : that.getSize() != null) return false;
        if (getService() != null ? !getService().equals(that.getService()) : that.getService() != null)
            return false;
        if (getPriceAware() != null ? !getPriceAware().equals(that.getPriceAware()) : that.getPriceAware() != null)
            return false;
        if (getOrderItem() != null ? !getOrderItem().equals(that.getOrderItem()) : that.getOrderItem() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getPriceAware() != null ? getPriceAware().hashCode() : 0);
        result = 31 * result + (getService() != null ? getService().hashCode() : 0);
        result = 31 * result + (getOrderItem() != null ? getOrderItem().hashCode() : 0);
        result = 31 * result + (getSize() != null ? getSize().hashCode() : 0);
        return result;
    }

}
