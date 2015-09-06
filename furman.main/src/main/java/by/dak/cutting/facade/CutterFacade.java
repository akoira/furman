package by.dak.cutting.facade;

import by.dak.cutting.cut.base.Dimension;
import by.dak.persistence.entities.Cutter;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CutterFacade extends BaseFacade<Cutter>
{

    Cutter loadByName(String name);

    Cutter getDefault();

    Dimension trim(Dimension sheet, Cutter cutter);

    Dimension untrim(Dimension sheet, Cutter cutter);

    String getDefaultName();

    void setDefaultName(String defaultName);

    void setDefaultLinearName(String defaultLinearName);

    String getDefaultLinearName();

    public Cutter getDefaultLinearCutter();
}
