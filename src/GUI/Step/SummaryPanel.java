/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Step;

import GUI.MainController;
import GUI.Tables.Step1TableHelper;
import GUI.Tables.Step2TableHelper;
import GUI.Tables.Step3TableHelper;
import GUI.Tables.Step4TableHelper;
import GUI.Tables.Step5TableHelper;
import GUI.Tables.Step6TableHelper;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author ltrask
 */
public class SummaryPanel {

    public static Node createStepSummary(MainController mc) {
        Tab fs1 = new Tab();
        fs1.setText("Fact Sheet 1");
        fs1.setContent(Step1TableHelper.createStepSummary1(mc));
        Tab fs2 = new Tab();
        fs2.setText("Fact Sheet 2");
        fs2.setContent(Step1TableHelper.createStepSummary2(mc));
        Tab fs3 = new Tab();
        fs3.setText("Fact Sheet 3");
        fs3.setContent(Step2TableHelper.createStepSummary1(mc));
        Tab fs4 = new Tab();
        fs4.setText("Fact Sheet 4");
        fs4.setContent(Step2TableHelper.createStepSummary2(mc));
        Tab fs5 = new Tab();
        fs5.setText("Fact Sheet 5");
        fs5.setContent(Step3TableHelper.createStepSummary(mc));
        Tab fs6 = new Tab();
        fs6.setText("Fact Sheet 6");
        fs6.setContent(Step4TableHelper.createStepSummary(mc));
        Tab fs7 = new Tab();
        fs7.setText("Fact Sheet 7");
        fs7.setContent(Step5TableHelper.createStepSummary(mc));
        Tab fs8 = new Tab();
        fs8.setText("Fact Sheet 8");
        fs8.setContent(Step6TableHelper.createStepSummary(mc));

        final TabPane summaryTabPane = new TabPane();
        summaryTabPane.getStyleClass().add("custom-subtitle-tab-pane");
        summaryTabPane.getTabs().addAll(fs1, fs2, fs3, fs4, fs5, fs6, fs7, fs8);
        for (Tab tab : summaryTabPane.getTabs()) {
            tab.setStyle("-fx-padding: 0 15 0 15");
        }
        summaryTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        BorderPane wrapperPane = new BorderPane();
        wrapperPane.getStylesheets().add("GUI/CSS/globalStyle.css");
        wrapperPane.setCenter(summaryTabPane);
        wrapperPane.setMinHeight(225);

        return wrapperPane;
    }
}
