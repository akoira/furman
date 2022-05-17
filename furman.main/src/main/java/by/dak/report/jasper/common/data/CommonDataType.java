package by.dak.report.jasper.common.data;

import by.dak.lang.Named;
import by.dak.persistence.entities.predefined.MaterialType;
import by.dak.persistence.entities.predefined.ServiceType;

/**
 * User: akoyro
 * Date: 24.11.2010
 * Time: 14:50:14
 */
public enum CommonDataType
{

    cutting(ServiceType.cutting),
    directGlueing(ServiceType.directGluing),
    curveGlueing(ServiceType.curveGlueing),
    milling(ServiceType.milling),
    drilling(ServiceType.drilling),
    drillingForLoop(ServiceType.drillingForLoop),
    drillingForHandle(ServiceType.drillingForHandle),
    groove(ServiceType.groove),
    angle(ServiceType.angle),
    patch(ServiceType.patch),
    cutoff(ServiceType.cutoff),
    zfacadeService(ServiceType.zfacade),
    agtfacadeService(ServiceType.agtfacade),
    board(MaterialType.board),
    border(MaterialType.border),
    furniture(MaterialType.furniture),
    facadeFurniture(MaterialType.furniture),
    zfacade(MaterialType.zprofile),
    agtfacade(MaterialType.agtprofile),
    additional(null),
    plasticPatch(ServiceType.plasticPatch);

    private Named parentType;

    CommonDataType(Named parentType)
    {
        this.parentType = parentType;
    }

    public static CommonDataType[] materialTypes()
    {
        CommonDataType[] commonDataType = new CommonDataType[]{board, border, furniture};
        return commonDataType;
    }

    public static CommonDataType[] serviceTypes()
    {
        CommonDataType[] commonDataType = new CommonDataType[]{cutting,
                directGlueing,
                curveGlueing,
                milling,
                drilling,
                drillingForLoop,
                drillingForHandle,
                groove,
                angle,
                patch,
                cutoff,
                zfacadeService,
                agtfacadeService,
                plasticPatch
        };
        return commonDataType;
    }

    public static CommonDataType valueOf(Named parentType)
    {
        for (int i = 0; i < values().length; i++)
        {
            CommonDataType commonDataType = values()[i];
            if (commonDataType.getParentType() == parentType)
            {
                return commonDataType;
            }
        }
        throw new IllegalArgumentException();
    }

    public Named getParentType()
    {
        return parentType;
    }

    public static CommonDataType[] dialerTypes()
    {
        CommonDataType[] commonDataType = new CommonDataType[]{cutting,
                directGlueing,
                curveGlueing,
                milling,
                drilling,
                drillingForLoop,
                drillingForHandle,
                groove,
                angle,
                patch,
                cutoff,
                zfacadeService,
                agtfacadeService,
                board,
                border,
                furniture,
                zfacade,
                agtfacade,
                additional,
                plasticPatch
        };
        return commonDataType;
    }


    public static CommonDataType[] productionTypes()
    {
        CommonDataType[] commonDataType = new CommonDataType[]{cutting,
                directGlueing,
                curveGlueing,
                milling,
                drilling,
                drillingForLoop,
                drillingForHandle,
                groove,
                angle,
                patch,
                cutoff,
                zfacadeService,
                agtfacadeService,
                board,
                border,
                furniture,
                facadeFurniture,
                additional,
                plasticPatch
        };
        return commonDataType;
    }

}

