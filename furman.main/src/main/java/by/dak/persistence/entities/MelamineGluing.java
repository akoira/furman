package by.dak.persistence.entities;

import by.dak.persistence.convert.MelamineGluing2StringConverter;
import by.dak.persistence.entities.predefined.Unit;
import by.dak.utils.convert.StringValue;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import static by.dak.persistence.entities.predefined.MaterialType.gluingSize;

@Entity
@DiscriminatorValue(value = "MelamineGluing")
@DiscriminatorOptions(force = true)

@StringValue(converterClass = MelamineGluing2StringConverter.class)
public class MelamineGluing extends PriceAware {

    public MelamineGluing() {
        setType(gluingSize);
        setUnit(Unit.linearMetre);
    }

}