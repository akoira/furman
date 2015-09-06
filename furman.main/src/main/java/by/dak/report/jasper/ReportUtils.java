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
import by.dak.persistence.entities.*;
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
    private static Dailysheet DAILYSHEET = null;
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


    public static long calcSawSheetSegment(Segment segment, Cutter cutter)
    {
        long result = segment.getSawLength();
        if (cutter.getCutSizeBottom() > 0)
        {
            result += segment.getMaterialLength();
        }

        if (cutter.getCutSizeTop() > 0)
        {
            result += segment.getMaterialLength();
        }
        if (cutter.getCutSizeLeft() > 0)
        {
            result += segment.getMaterialWidth();
        }
        if (cutter.getCutSizeRight() > 0)
        {
            result += segment.getMaterialWidth();
        }
        return result;
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
    public static double getPriceBy(Double price, String currencyTypeName)
    {

        return getPriceBy(price, CurrencyType.valueOf(currencyTypeName));
    }

    public static double getPriceBy(Double price, CurrencyType currencyType)
    {
        if (DAILYSHEET == null)
        {
            DAILYSHEET = FacadeContext.getDailysheetFacade().loadCurrentDailysheet();
        }
        if (price != null)
        {
            Currency currency = FacadeContext.getCurrencyFacade().findCurrentBy(currencyType,
                    DAILYSHEET,
                    false);
            return price / currency.getPrice();
        }
        else
        {
            return 0;
        }
    }

    public static double getPriceBy(PriceEntity price, boolean isPriceDealer)
    {
		if (price == null)
			return 0.0;
		else
			return getPriceBy(isPriceDealer ? price.getPriceDealer() : price.getPrice(), price.getCurrencyType());
	}

    public static double getPaidValueBy(TextureBoardDefPair pair, Strips strips)
    {
        double value = 0.0;
        switch (pair.getBoardDef().getUnit())
        {
            case squareMetre:
                value = calcSquare(calcPaidArea(pair.getBoardDef().getCutter(), strips));
                break;
            case piece:
                //берем все boards котрые прикремлены к заказу и считаем их колличество
                for (int i = 0; i < strips.getSegmentsCount(); i++)
                {
                    Segment segment = strips.getSegment(i);
                    value += segment.getMaterialLength() * segment.getMaterialWidth();
                }
                value = calcSquare(value);
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

    public static void fillPrice(CommonData commonData, PriceEntity price)
    {
        commonData.setDialerPrice(getPriceBy(price, true));
        commonData.setPrice(getPriceBy(price, false));
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
