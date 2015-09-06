package by.dak.report.jasper.cutting;

import by.dak.cutting.swing.order.data.A45;
import by.dak.cutting.swing.order.data.Groove;
import by.dak.cutting.swing.xml.XstreamHelper;
import by.dak.persistence.entities.AOrderBoardDetail;
import by.dak.utils.convert.Converter;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * @author Denis Koyro
 * @version 0.1 18.03.2009
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public class CuttingReportConverter implements Converter<AOrderBoardDetail, CardDetailsData>
{
    private static final String DIMENSION_PATTERN = "{0}x{1}";
    private static final String GROOVE_PATTERN = "{0}-{1},{2}";
    private static final String ANGLE_PATTERN = "{0}-{1}";

    private ResourceBundle resourceBundle;
    private static final String LEFT_BUNDLE_KEY = "left";
    private static final String UP_BUNDLE_KEY = "up";
    private static final String RIGHT_BUNDLE_KEY = "right";
    private static final String DOWN_BUNDLE_KEY = "down";

    public CuttingReportConverter(ResourceBundle resourceBundle)
    {
        this.resourceBundle = resourceBundle;
    }

    public CardDetailsData convert(AOrderBoardDetail source)
    {
        Groove groove = (Groove) XstreamHelper.getInstance().fromXML(source.getGroove());
        A45 a45 = (A45) XstreamHelper.getInstance().fromXML(source.getAngle45());

        return new CardDetailsData(source.getNumber().toString(), formatDimension(source.getLength(), source.getWidth()),
                groove != null && groove.isLeft() ? formatGroove(source.getWidth(), groove.getDepthLeft(), groove.getDistanceLeft()) : EMPTY_STRING,
                groove != null && groove.isUp() ? formatGroove(source.getLength(), groove.getDepthUp(), groove.getDistanceUp()) : EMPTY_STRING,
                groove != null && groove.isRight() ? formatGroove(source.getWidth(), groove.getDepthRight(), groove.getDistanceRight()) : EMPTY_STRING,
                groove != null && groove.isDown() ? formatGroove(source.getLength(), groove.getDepthDown(), groove.getDistanceDown()) : EMPTY_STRING,
                a45 != null && a45.isLeft() ? formatAngle(LEFT_BUNDLE_KEY, a45.getLeftValue()) : EMPTY_STRING,
                a45 != null && a45.isUp() ? formatAngle(UP_BUNDLE_KEY, a45.getUpValue()) : EMPTY_STRING,
                a45 != null && a45.isRight() ? formatAngle(RIGHT_BUNDLE_KEY, a45.getRightValue()) : EMPTY_STRING,
                a45 != null && a45.isDown() ? formatAngle(DOWN_BUNDLE_KEY, a45.getDownValue()) : EMPTY_STRING, getDetailsCount(source));
    }

    private String getDetailsCount(AOrderBoardDetail source)
    {
        return source.getAmount().toString();
    }

    private String formatDimension(Long length, Long width)
    {
        return MessageFormat.format(DIMENSION_PATTERN, length.toString(), width.toString());
    }

    private String formatGroove(Long value, int depth, int distance)
    {
        return MessageFormat.format(GROOVE_PATTERN, value.toString(), depth, distance);
    }

    private String formatAngle(String bundleKey, String angleValue)
    {
        return MessageFormat.format(ANGLE_PATTERN, resourceBundle.getString(bundleKey), angleValue);
    }
}
