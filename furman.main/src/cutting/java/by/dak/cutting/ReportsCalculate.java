package by.dak.cutting;

import by.dak.cutting.cut.gui.CutSettings;
import by.dak.cutting.cut.gui.cuttingApp.CSawyer;
import by.dak.cutting.cut.gui.cuttingApp.IndividualSawyer;
import by.dak.cutting.cut.guillotine.Strips;
import by.dak.cutting.swing.cut.CuttingModel;
import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.cutting.swing.report.ReportsPanel;
import by.dak.persistence.MainFacade;
import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.Order;
import by.dak.report.model.ReportsModel;
import by.dak.report.model.impl.ReportModelCreator;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import java.util.Map;

import static by.dak.cutting.configuration.Constants.TIME_FOR_STRIP;

public class ReportsCalculate {

    public interface func {
    }

    public static void main(String[] args) {

    }
}
