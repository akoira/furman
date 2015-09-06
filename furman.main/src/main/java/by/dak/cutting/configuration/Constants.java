package by.dak.cutting.configuration;

import org.jdesktop.application.Application;

/**
 * Класс содержит конфигурационные константы для данного проекта.
 * !!!!! Описывать все константы
 * User: akoyro
 * Date: 13.03.2009
 * Time: 20:54:16
 */
public interface Constants
{
    /**
     * На сколько должен быть увеличин размер составных частей комплексной детали
     */
    public final static int COMPEXT_FURNITURE_INCREASE = Application.getInstance().getContext().getResourceMap().getInteger("Application.default.OrderFurniture.complex.increas");

    /**
     * Used only for tests
     *
     * @deprecated
     */
    public static final long DEFAULT_SHEET_LENGTH = 2800;

    /**
     * Used only for tests
     *
     * @deprecated
     */
    public static final long DEFAULT_SHEET_WIDTH = 2070;

    /**
     * Дефолтное значение градуса угла
     */


    /*
     Дефолтное значение градуса угла
     */
    public final static String DEFAULT_A45_VALUE = Application.getInstance().getContext().getResourceMap().getString("Application.default.a45.value");


    /**
     * Минимальная длинна материала
     */
    public final static int MIN_BORDER_LENGTH = Application.getInstance().getContext().getResourceMap().getInteger("Application.border.length.min.value");

    public final static int MIN_MATERIAL_LENGTH = Application.getInstance().getContext().getResourceMap().getInteger("Application.mat.length.min.value");

    public final static int MIN_MATERIAL_WIDTH = Application.getInstance().getContext().getResourceMap().getInteger("Application.mat.width.min.value");

    public final static int MIN_DETAIL_LENGTH = Application.getInstance().getContext().getResourceMap().getInteger("Application.detail.length.min.value");

    public final static int MIN_DETAIL_WIDTH = Application.getInstance().getContext().getResourceMap().getInteger("Application.detail.width.min.value");

    public final static int DETAIL_LENGTH_MAX = Application.getInstance().getContext().getResourceMap().getInteger("Application.detail.length.max.value");

    public final static int DETAIL_WIDTH_MAX = Application.getInstance().getContext().getResourceMap().getInteger("Application.detail.width.max.value");


    public final static int MAX_COUNT_DETAIL_VALUE = Application.getInstance().getContext().getResourceMap().getInteger("Application.detail.max.count.value");

    public final static int MIN_COUNT_DETAIL_VALUE = Application.getInstance().getContext().getResourceMap().getInteger("Application.detail.min.count.value");

    public final static String DEFAULT_DETAIL_NAME = Application.getInstance().getContext().getResourceMap().getString("Application.default.OrderFurniture.name");


    /**
     * todo перенести в систему настроек
     * Минимальный площать куска материал котрый может использоватся в дальнейшем
     */
    public final static float MIN_USING_AREA = 700000;

    //todo константа опрделяет вращать или нет лист.
    public final static boolean ROTATE_SHEET = true;

    //todo время для оптимизации одного листа
    public static long TIME_FOR_STRIP = 15;


    boolean IS_PUT_REST_TO_STORE = Application.getInstance().getContext().getResourceMap().getBoolean("Application.default.isPutRestToStore");


}
