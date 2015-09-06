package by.dak.report.jasper.cutting.data;

import by.dak.persistence.entities.AOrder;

import java.util.List;

/**
 * Обеъек для получение данных для отчета распила.
 * Поля:
 * Заказ N :  Order.orderNumber
 * Заказчик :  Order.designerEntity
 * Готовность :   Order.dailySheet + 1 день
 * <p/>
 * Номер Карты
 *
 * @author akoyro
 * @version 0.1 02.02.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public interface CuttedReportData
{
    AOrder getOrder();

    List<MaterialCuttedData> getMaterials();
}
