package by.dak.cutting.configuration;

import by.dak.cutting.CuttingApp;


import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Класс содержит конфигурационные константы для данного проекта.
 * !!!!! Описывать все константы
 * User: akoyro
 * Date: 13.03.2009
 * Time: 20:54:16
 */
public interface Constants {
    static ResourceBundle resourceBundle(Class aClass) {
        return ResourceBundle.getBundle(aClass.getPackage().getName() + ".resources." + aClass.getSimpleName());
    }
    static Properties properties(Class aClass) {
        Properties properties = new Properties();
        try {
            properties.load(aClass.getResourceAsStream("resources/" + aClass.getSimpleName() + ".properties"));
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    static Properties properties() {
        return properties(CuttingApp.class);
    }

    static ResourceBundle resourceBundle() {
        return resourceBundle(CuttingApp.class);
    }

    Properties properties = properties();
    ResourceBundle resourceBundle = resourceBundle();
    /**
     * На сколько должен быть увеличин размер составных частей комплексной детали
     */
    int COMPEXT_FURNITURE_INCREASE = Integer.parseInt(resourceBundle.getString("Application.default.OrderFurniture.complex.increas"));

    /**
     * Used only for tests
     *
     * @deprecated
     */
    long DEFAULT_SHEET_LENGTH = 2800;

    /**
     * Used only for tests
     *
     * @deprecated
     */
    long DEFAULT_SHEET_WIDTH = 2070;

    /**
     * Дефолтное значение градуса угла
     */


    /*
     Дефолтное значение градуса угла
     */
    String DEFAULT_A45_VALUE = resourceBundle.getString("Application.default.a45.value");


    /**
     * Минимальная длинна материала
     */
    int MIN_BORDER_LENGTH = Integer.parseInt(resourceBundle.getString("Application.border.length.min.value"));

    int MIN_MATERIAL_LENGTH = Integer.parseInt(resourceBundle.getString("Application.mat.length.min.value"));

    int MIN_MATERIAL_WIDTH = Integer.parseInt(resourceBundle.getString("Application.mat.width.min.value"));

    int MIN_DETAIL_LENGTH = Integer.parseInt(resourceBundle.getString("Application.detail.length.min.value"));

    int MIN_DETAIL_WIDTH = Integer.parseInt(resourceBundle.getString("Application.detail.width.min.value"));

    int DETAIL_LENGTH_MAX = Integer.parseInt(resourceBundle.getString("Application.detail.length.max.value"));

    int DETAIL_WIDTH_MAX = Integer.parseInt(resourceBundle.getString("Application.detail.width.max.value"));


    int MAX_COUNT_DETAIL_VALUE = Integer.parseInt(resourceBundle.getString("Application.detail.max.count.value"));

    int MIN_COUNT_DETAIL_VALUE = Integer.parseInt(resourceBundle.getString("Application.detail.min.count.value"));

    String DEFAULT_DETAIL_NAME = resourceBundle.getString("Application.default.OrderFurniture.name");


    /**
     * todo перенести в систему настроек
     * Минимальный площать куска материал котрый может использоватся в дальнейшем
     */
    float MIN_USING_AREA = 700000;

    //todo константа опрделяет вращать или нет лист.
    boolean ROTATE_SHEET = true;

    //todo время для оптимизации одного листа
    public static long TIME_FOR_STRIP = 15;


    boolean IS_PUT_REST_TO_STORE = Boolean.parseBoolean(resourceBundle.getString("Application.default.isPutRestToStore"));


}
