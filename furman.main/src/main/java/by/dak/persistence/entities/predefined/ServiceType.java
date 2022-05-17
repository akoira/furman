package by.dak.persistence.entities.predefined;

import by.dak.lang.Named;
import by.dak.persistence.convert.ServiceType2StringConverter;
import by.dak.utils.convert.StringValue;

/**
 * User: akoyro
 * Date: 24.08.2009
 * Time: 19:51:21
 */
@StringValue(converterClass = ServiceType2StringConverter.class)
public enum ServiceType implements Named
{
    cutting(MaterialType.board), //распил
    directGluing(MaterialType.border), //оклейка прямолинейная
    curveGlueing(MaterialType.border), //оклейка криволинейная
    milling(MaterialType.board), //фрезеровка
    groove(MaterialType.board), //пропил
    angle(MaterialType.board), // угол
    patch(MaterialType.board), //склейка
    cutoff(MaterialType.board), //срез
    zfacade(MaterialType.zprofile), //zfacade
    agtfacade(MaterialType.agtprofile), //agtfacade
    drilling(MaterialType.board), //drilling
    drillingForLoop(MaterialType.board), //drillingForLoop
    drillingForHandle(MaterialType.board), //drillingForHandle
    plasticPatch(MaterialType.board); //склейка пластиком

    private MaterialType materialType;

    ServiceType(MaterialType materialType)
    {
        this.materialType = materialType;
    }

    public MaterialType getMaterialType()
    {
        return materialType;
    }
}
