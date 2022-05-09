package by.dak.report.jasper;

import by.dak.cutting.currency.persistence.entity.Currency;
import by.dak.cutting.currency.persistence.entity.CurrencyType;
import by.dak.cutting.cut.base.Dimension;
import by.dak.cutting.cut.base.RestBoardDimension;
import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.cut.guillotine.Strips;
import by.dak.cutting.cut.guillotine.helper.BoardDimensionsHelper;
import by.dak.cutting.cut.guillotine.helper.DimensionsHelper;
import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.MainFacade;
import by.dak.persistence.entities.*;
import by.dak.persistence.entities.predefined.Unit;
import by.dak.persistence.entities.types.FurnitureCode;
import by.dak.report.jasper.common.data.CommonData;
import by.dak.utils.convert.Converter;
import by.dak.utils.convert.StringValueAnnotationProcessor;
import by.dak.utils.convert.TimeUtils;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import static by.dak.cutting.configuration.Constants.MIN_USING_AREA;

/**
 * Created by IntelliJ IDEA.
 * User: akoyro
 * Date: Mar 19, 2009
 * Time: 11:18:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class ReportUtils
{
    public static final String EMPTY_STRING = Converter.EMPTY_STRING;
    public final static String PATTERN_FURNITURE_STRING = "{0}:{1}x{2}";
    private final static String GLUEING_VALUE_PATTERN = "{0} {1}";
    private final static String COMPLEX_DIMENSION_PATTERN = "{0} ({1})";
    private final static String TEXTURE_PATTERN = "{0} ({1})";
    private final static String FURNITURE_CODE_PATTERN = "{0} ({1})";


    /**
     * Вычисляем метр погонный для
     *
     * @param size
     * @return
     */
    public static double calcLinear(double size)
    {
        return size / 1000d;
    }

    /**
     * Вычисляем метр квадратный для
     *
     * @param size
     * @return
     */
    public static double calcSquare(long size)
    {
        return (double) size / 1000000d;
    }

    /**
     * Вычисляем метр квадратный для
     *
     * @param size
     * @return
     */
    public static double calcSquare(double size)
    {
        return size / 1000000d;
    }


    public static long calcPaidArea(Cutter cutter, Strips strips)
    {
        long usedArea = getUsedArea(cutter, strips);

        usedArea -= getFreeArea(strips);
        return usedArea;
    }

    private static long getUsedArea(Cutter cutter, Strips strips)
    {
        if (strips == null)
        {
            return 0;
        }
        long usedArea = strips.getUsedArea();
        List<Segment> segments = strips.getSegments();
        //расчет площади торцовки
        for (Segment segment : segments)
        {
            usedArea += getCutterWasteArea(cutter, segment);
        }
        return usedArea;
    }

    public static long getCutterWasteArea(Cutter cutter, Segment segment)
    {
        return segment.getMaterialLength() * cutter.getCutSizeTop() +
                segment.getMaterialLength() * cutter.getCutSizeBottom() +
                segment.getMaterialWidth() * cutter.getCutSizeLeft() +
                segment.getMaterialWidth() * cutter.getCutSizeRight();
    }

    /**
     * Возвражает повторно используемую площадь(не включаемую в стоймость) для данного Strips
     * todo должен быть вынесен из этих utils
     *
     * @param strips
     * @return
     */
    private static long getFreeArea(Strips strips)
    {

        long freeArea = 0l;
        if (strips != null)
        {
            DimensionsHelper freeDimensions = new BoardDimensionsHelper();
            List<RestBoardDimension> dimensions = freeDimensions.getRestDimensions(strips);
            for (Dimension dimension : dimensions)
            {
                if (isUsefulDimension(dimension))
                {
                    freeArea += dimension.getArea();
                }
            }
        }
        return freeArea;
    }


    public static long getFreeLength(Segment redSegment)
    {
        return redSegment.getFreeLength();
    }


    /**
     * Является ли елемент для повторного использования
     * todo должен быть вынесен из этих utils
     *
     * @param dimension
     * @return
     */
    public static boolean isUsefulDimension(Dimension dimension)
    {
        return dimension.getArea() >= MIN_USING_AREA;
    }


    public static String getReadyDateForReport(Date date)
    {
        return date != null ?
                SimpleDateFormat.getDateInstance(DateFormat.SHORT).format(TimeUtils.getDayBefore(date)) : EMPTY_STRING;

    }

    public static String getFurnitureName(OrderFurniture furniture)
    {
        String text = MessageFormat.format(PATTERN_FURNITURE_STRING, furniture.getNumber().toString(), furniture.getLength().toString(), furniture.getWidth().toString());
        return text;
    }

    public static void paintFurnitureName(OrderFurniture furniture, Rectangle2D rectangle, Graphics2D graphics2D)
    {
        String text = getFurnitureName(furniture);
        int textL = graphics2D.getFontMetrics().stringWidth(text);
        graphics2D.drawString(text, (float) ((rectangle.getWidth() - textL) / 2), (float) (rectangle.getHeight() / 2));
    }

    /**
     * Used in reports //(by.dak.report.jasper.ReportUtils.getPriceBy($F{PRICE_PRICE},$F{PRICE_CURRENCY_TYPE}))
     *
     * @param price
     * @param currencyTypeName
     * @return
     */
    public static double getPriceBy(Double price, String currencyTypeName, MainFacade mainFacade) {
        return getPriceBy(price, currencyTypeName, null, mainFacade);
    }

    public static double getPriceBy(Double price, String currencyTypeName, AOrder order, MainFacade mainFacade)
    {
        return getPriceBy(price, CurrencyType.valueOf(currencyTypeName), order, mainFacade);
    }

    public static double getPriceBy(Double price, CurrencyType currencyType, AOrder order, MainFacade mainFacade)
    {
        if (price != null)
        {
            Dailysheet dailysheet =  MainFacade.dailysheet.apply(mainFacade).apply(order);
            Currency selected = mainFacade.getCurrencyFacade().getSelected(dailysheet);
            Currency current = mainFacade.getCurrencyFacade().findCurrentBy(currencyType, dailysheet, false);
            return price * current.getPrice() * selected.getPrice();
        }
        else
        {
            return 0;
        }
    }

    public static double getPriceBy(PriceEntity price, boolean isPriceDealer, AOrder order, MainFacade mainFacade)
    {
		if (price == null)
			return 0.0;
		else
            return getPriceBy(isPriceDealer ? price.getPriceDealer() : price.getPrice(), price.getCurrencyType(), order, mainFacade);
	}

    //берем все boards котрые прикремлены к заказу и считаем их колличество
    public static final Function<Strips, Double> segment_area = strips ->
        strips.getSegments().stream().map(s -> s.getMaterialLength() * s.getMaterialWidth())
                .reduce(0L, Long::sum).doubleValue();


    public static double getPaidValueBy(TextureBoardDefPair pair, Strips strips)
    {
        double value = 0.0;
        Unit unit = pair.getBoardDef().getUnit();
        switch (unit)
        {
            case squareMetre:
                if (pair.getTexture().isInSize())
                    value = calcSquare(calcPaidArea(pair.getBoardDef().getCutter(), strips));
                else
                    value = calcSquare(segment_area.apply(strips));
                break;
            case piece:
                value = calcSquare(segment_area.apply(strips));
                break;
            case linearMetre:
                //takes lenght of the board matirials which ara used in the cutting
                for (int i = 0; i < strips.getSegmentsCount(); i++)
                {
                    Segment segment = strips.getSegment(i);
                    value += segment.getMaterialLength();
                }
                value = ReportUtils.calcLinear(value);
                break;
        }
        return value;
    }

    public static void fillPrice(CommonData commonData, PriceEntity price, AOrder order, MainFacade mainFacade)
    {
        commonData.setDialerPrice(getPriceBy(price, true, order, mainFacade));
        commonData.setPrice(getPriceBy(price, false, order, mainFacade));
    }


    public static String formatGlueingValue(BorderDefEntity borderDef, TextureEntity texture)
    {
        return MessageFormat.format(GLUEING_VALUE_PATTERN, StringValueAnnotationProcessor.getProcessor().convert(borderDef),
                StringValueAnnotationProcessor.getProcessor().convert(texture));
    }

    public static String formatDimension(Long pureWidth, Long dirtyWidth)
    {
        return MessageFormat.format(COMPLEX_DIMENSION_PATTERN, pureWidth.toString(), dirtyWidth.toString());
    }

    public static String formatTexture(TextureEntity texture)
    {
        return MessageFormat.format(TEXTURE_PATTERN, texture.getName(), texture.getCode().toString());
    }

    public static String formatGlueingValue(String material, TextureEntity texture)
    {
        return MessageFormat.format(GLUEING_VALUE_PATTERN, material, texture.getName());
    }

    public static String formatFurnitureCode(FurnitureCode furnitureCode)
    {
        return MessageFormat.format(FURNITURE_CODE_PATTERN, furnitureCode.getName(), furnitureCode.getCode());
    }
}
