/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Step;

import GUI.IconHelper;
import GUI.MainController;
import GUI.Tables.Step1Table;
import GUI.Tables.TableHelper;
import core.Question;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import GUI.Helper.ProgressIndicatorBar;
import GUI.MainWindow;
import static GUI.MainWindow.COLOR_SUB_STEP;
import static GUI.MainWindow.COLOR_SUB_STEP_HL;
import java.util.ArrayList;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import javafx.util.Duration;

/**
 *
 * @author ltrask
 */
public class Step1Panel extends BorderPane {

    private final int numSubSteps = 4;

    private final MainController control;

    private final int stepIndex = 0;

    private final SimpleIntegerProperty activeSubStep = new SimpleIntegerProperty(-1);

    private final VBox mainVBox = new VBox();

    private final GridPane allSubStepsPane = new GridPane();

    private final VBox pVBox = new VBox();

    private final Pagination pagination;

    private final ProgressIndicatorBar pb;

    private final SimpleDoubleProperty progress = new SimpleDoubleProperty(0.0);

    private final GridPane subStepToolGrid = new GridPane();
    private final BorderPane subStepToolBar = new BorderPane();
    private final Button subStep0Button = new Button("Intro");
    private final Button subStep1Button = new Button("Project Info & WZ Metadata");
    private final Button subStep2Button = new Button("User Needs");
    private final Button subStep3Button = new Button("User Needs Supplemental");
    private final Button subStep4Button = new Button("User Needs Supplemental2");
    private final Button subStep5Button = new Button("Summary");
    private final Button prevSubStepButton = new Button();
    private final Button nextSubStepButton = new Button();

    private final GridPane stepIntroGrid = new GridPane();
    private final GridPane projInfoGrid = new GridPane();
    private final BorderPane genInfoPane = new BorderPane();
    private final GridPane genInfoGrid;
    private final BorderPane wzMetaDataPane = new BorderPane();
    private final GridPane wzMetaDataGrid;
    private final BorderPane unSuppPane = new BorderPane();
    private final BorderPane unSupp2Pane = new BorderPane();
    private final BorderPane stepSummaryPane = new BorderPane();

    public Step1Panel(MainController control) {

        this.control = control;

        // Creating and styling sub step toolbar
        subStep0Button.getStyleClass().add("sub-flow-step-start");
        subStep0Button.setStyle("-fx-background-color: " + MainWindow.COLOR_SUB_STEP_HL);
        subStep1Button.getStyleClass().add("sub-flow-step");
        subStep2Button.getStyleClass().add("sub-flow-step");
        subStep3Button.getStyleClass().add("sub-flow-step");
        subStep4Button.getStyleClass().add("sub-flow-step");
        subStep5Button.getStyleClass().add("sub-flow-step-end");
        prevSubStepButton.getStyleClass().add("sub-flow-prev");
        nextSubStepButton.getStyleClass().add("sub-flow-next");

        // Setting the layout of the Flow ToolBar
        subStepToolGrid.add(subStep0Button, 0, 0);
        subStepToolGrid.add(subStep1Button, 1, 0);
        subStepToolGrid.add(subStep2Button, 2, 0);
        subStepToolGrid.add(subStep3Button, 3, 0);
        subStepToolGrid.add(subStep4Button, 4, 0);
        subStepToolGrid.add(subStep5Button, 5, 0);
        for (int flowIdx = 0; flowIdx < numSubSteps + 2; flowIdx++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / (numSubSteps + 2));
            colConst.setFillWidth(true);
            subStepToolGrid.getColumnConstraints().add(colConst);
        }
        subStepToolBar.setLeft(prevSubStepButton);
        subStepToolBar.setCenter(subStepToolGrid);
        subStepToolBar.setRight(nextSubStepButton);
        BorderPane.setMargin(subStepToolGrid, new Insets(0, 3, 0, 3));

        ArrayList<Button> buttonList = new ArrayList();
        buttonList.add(prevSubStepButton);
        buttonList.add(subStep0Button);
        buttonList.add(subStep1Button);
        buttonList.add(subStep2Button);
        buttonList.add(subStep3Button);
        buttonList.add(subStep4Button);
        buttonList.add(subStep5Button);
        buttonList.add(nextSubStepButton);

        for (Button b : buttonList) {
            b.setWrapText(true);
            b.setTextAlignment(TextAlignment.CENTER);
            b.setMaxWidth(MainController.MAX_WIDTH);
            b.setMaxHeight(MainController.MAX_HEIGHT);
        }

        SVGPath prevSVG = new SVGPath();
        prevSVG.setContent(IconHelper.SVG_STR_PREV_SMALL);
        SVGPath nextSVG = new SVGPath();
        nextSVG.setContent(IconHelper.SVG_STR_NEXT_SMALL);
        prevSubStepButton.setGraphic(prevSVG);
        nextSubStepButton.setGraphic(nextSVG);

        mainVBox.setFillWidth(true);
        Label startLabel = new Label("Step");
        startLabel.setAlignment(Pos.BOTTOM_CENTER);
        startLabel.setMaxHeight(MainController.MAX_HEIGHT);
        startLabel.setMaxWidth(MainController.MAX_WIDTH);
        Label infoLabel = new Label("1");
        infoLabel.setMaxHeight(MainController.MAX_HEIGHT);
        infoLabel.setMaxWidth(MainController.MAX_WIDTH);
        infoLabel.setAlignment(Pos.TOP_CENTER);
        Label instructionLabel = new Label("Assessment of Needs");
        instructionLabel.setWrapText(true);
        instructionLabel.setTextAlignment(TextAlignment.CENTER);
        instructionLabel.setMaxHeight(MainController.MAX_HEIGHT);
        instructionLabel.setMaxWidth(MainController.MAX_WIDTH);
        instructionLabel.setAlignment(Pos.CENTER);
        startLabel.getStyleClass().add("launch-title-label-top");
        infoLabel.getStyleClass().add("launch-title-label-bottom");
        instructionLabel.getStyleClass().add("intro-instructions");

        stepIntroGrid.add(startLabel, 0, 0);
        stepIntroGrid.add(infoLabel, 0, 1);
        stepIntroGrid.add(instructionLabel, 1, 1);
        stepIntroGrid.add(new ImageView(IconHelper.FIG_FLOW_STEP_1), 1, 0);
        stepIntroGrid.setStyle("-fx-background-color: white");

        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(50);
        row1.setVgrow(Priority.ALWAYS);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(50);
        row2.setVgrow(Priority.ALWAYS);
        stepIntroGrid.getRowConstraints().addAll(row1, row2);
        ColumnConstraints colConst1 = new ColumnConstraints(150, 150, 150);
        ColumnConstraints colConst2 = new ColumnConstraints(1, 150, MainController.MAX_WIDTH, Priority.ALWAYS, HPos.CENTER, true);
        stepIntroGrid.getColumnConstraints().addAll(colConst1, colConst2);
        GridPane.setHgrow(instructionLabel, Priority.ALWAYS);

        Label genInfoTitleLabel = new Label("General Information");
        genInfoTitleLabel.setMaxWidth(MainController.MAX_WIDTH);
        genInfoTitleLabel.getStyleClass().add("launch-title-label-top");
        genInfoGrid = createGeneralInfoGrid();
        genInfoPane.setTop(genInfoTitleLabel);
        genInfoPane.setCenter(genInfoGrid);

        Label wzMetaDataTitleLabel = new Label("Work Zone Meta Data");
        wzMetaDataTitleLabel.setMaxWidth(MainController.MAX_WIDTH);
        wzMetaDataTitleLabel.getStyleClass().add("launch-title-label-top");
        wzMetaDataGrid = createWZInputGrid();
        wzMetaDataPane.setTop(wzMetaDataTitleLabel);
        wzMetaDataPane.setCenter(wzMetaDataGrid);

        projInfoGrid.add(genInfoPane, 0, 0);
        projInfoGrid.add(wzMetaDataPane, 1, 0);
        projInfoGrid.setHgap(5);
        projInfoGrid.setPadding(new Insets(0, 5, 0, 5));
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        projInfoGrid.getColumnConstraints().addAll(col1, col2);

        // Old JavaFX ProgressBarCode
        //pb = new ProgressBar(0);
        //
        // New ProgressIndicatorBar Code
        pb = new ProgressIndicatorBar(progress, 1.0, "%.0f%%", true);
        pb.setMaxWidth(MainController.MAX_WIDTH);

        pagination = new Pagination(Step1Table.getPageCount(0));
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                //return Step1Table.createSummaryTable();
                return Step1Table.createPageTable(pageIndex, 10);
            }
        });
        //pagination.getStylesheets().add(this.getClass().getResource("/GUI/Step/step1Pane.css").toExternalForm());
        pagination.getStyleClass().add("step-subpagination");

        // NOTE: Use stack pane to add label to progress bar
        for (Question q : TableHelper.getStepQuestions(0)) {
            q.responseIdxProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
                    updateProgressBar();
                }
            });
        }
        pVBox.getChildren().addAll(pagination, pb);
        //mainVBox.getChildren().addAll(subStepToolBar, pagination, pb);
        mainVBox.getChildren().addAll(subStepToolBar, allSubStepsPane);
        VBox.setMargin(subStepToolBar, new Insets(5, 5, 5, 5));
        allSubStepsPane.add(stepIntroGrid, 0, 0);
        allSubStepsPane.add(projInfoGrid, 1, 0);
        allSubStepsPane.add(pVBox, 2, 0);
        allSubStepsPane.add(unSuppPane, 3, 0);
        allSubStepsPane.add(unSupp2Pane, 4, 0);
        allSubStepsPane.add(stepSummaryPane, 5, 0);
        //allSubStepsPane.add(subStepSummaryPane, 2, 0);

        int numPanes = numSubSteps + 2;
        for (int colIdx = 0; colIdx < numPanes; colIdx++) {
            ColumnConstraints tcc = new ColumnConstraints();
            tcc.setPercentWidth(100.0 / numPanes);
            allSubStepsPane.getColumnConstraints().add(tcc);
        }

        pagination.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(pagination, Priority.ALWAYS);

        GridPane.setHgrow(subStep0Button, Priority.ALWAYS);
        GridPane.setHgrow(subStep1Button, Priority.ALWAYS);
        GridPane.setHgrow(subStep2Button, Priority.ALWAYS);
        GridPane.setHgrow(subStep3Button, Priority.ALWAYS);
        GridPane.setHgrow(subStep4Button, Priority.ALWAYS);
        GridPane.setHgrow(subStep5Button, Priority.ALWAYS);
        GridPane.setVgrow(subStep0Button, Priority.ALWAYS);
        GridPane.setVgrow(subStep1Button, Priority.ALWAYS);
        GridPane.setVgrow(subStep2Button, Priority.ALWAYS);
        GridPane.setVgrow(subStep3Button, Priority.ALWAYS);
        GridPane.setVgrow(subStep4Button, Priority.ALWAYS);
        GridPane.setVgrow(subStep5Button, Priority.ALWAYS);

        this.setCenter(mainVBox);

        setupActionListeners();
        setupPropertyBindings();

    }

    private void updateProgressBar() {
        int sum = 0;
        for (Question q : TableHelper.getStepQuestions(0)) {
            if (q.getResponseIdx() > -1) {
                sum++;
            }
        }
        //pb.setProgress(sum / (double) TableHelper.getNumberOfQuestionsByStep(0));
        progress.set(sum / (double) TableHelper.getNumberOfQuestionsByStep(0));
    }

    private void setupActionListeners() {
        // Flow control actions
        prevSubStepButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                activeSubStep.set(activeSubStep.get() - 1);
            }
        });

        nextSubStepButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                activeSubStep.set(activeSubStep.get() + 1);
            }
        });

        subStep0Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                activeSubStep.set(-1);
            }
        });

        subStep1Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                activeSubStep.set(0);
            }
        });

        subStep2Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                activeSubStep.set(1);
            }
        });

        subStep3Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                activeSubStep.set(2);
            }
        });

        subStep4Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                activeSubStep.set(3);
            }
        });

        subStep5Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                activeSubStep.set(4);
            }
        });

    }

    private void setupPropertyBindings() {
        this.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldWidth, Number newWidth) {
                if (allSubStepsPane != null && allSubStepsPane.isVisible()) {
                    allSubStepsPane.setMinWidth((numSubSteps + 2) * (control.getAppWidth() - 220));
                    allSubStepsPane.setMaxWidth((numSubSteps + 2) * (control.getAppWidth() - 220));
                    subStepToolBar.setMinWidth(stepIntroGrid.getWidth());
                    subStepToolBar.setMaxWidth(stepIntroGrid.getWidth());
                    subStepToolBar.setMinHeight(60);
                    subStepToolBar.setMaxHeight(60);
                    moveScreen((activeSubStep.get() + 1) * stepIntroGrid.getWidth(), 0, false);
                }
            }
        });

        this.activeSubStep.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o, Object oldVal, Object newVal) {
                selectSubStep(activeSubStep.get());
                subStep0Button.setStyle("-fx-background-color: " + (activeSubStep.get() == -1 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP));
                subStep1Button.setStyle("-fx-background-color: " + (activeSubStep.get() == 0 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP));
                subStep2Button.setStyle("-fx-background-color: " + (activeSubStep.get() == 1 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP));
                subStep3Button.setStyle("-fx-background-color: " + (activeSubStep.get() == 2 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP));
                subStep4Button.setStyle("-fx-background-color: " + (activeSubStep.get() == 3 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP));
                subStep5Button.setStyle("-fx-background-color: " + (activeSubStep.get() == 4 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP));

                control.getProject().setSubStepStarted(stepIndex, activeSubStep.get(), true);
                control.getProject().setSubStepComplete(stepIndex, activeSubStep.get() - 1, true);
                //if (activeSubStep.get() == (numSubSteps)) {
                //    control.getProject().setStepComplete(stepIndex, true);
                //}

                if (activeSubStep.get() == numSubSteps) {
                    stepSummaryPane.setCenter(control.getProject().getGoalNeedsMatrix().createSummaryTable());
                }

                prevSubStepButton.setDisable(activeSubStep.get() == -1);
                if (activeSubStep.get() == numSubSteps) {
                    nextSubStepButton.setDisable(true);
                } else if (activeSubStep.get() == 1 && progress.get() < 1.0) {
                    nextSubStepButton.setDisable(true);
                }
                control.checkProceed();
            }
        });

        progress.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
                nextSubStepButton.setDisable((activeSubStep.get() == 1 && progress.get() < 1.0));
            }
        });

        // General Info Bindings
        genInfoTF1.textProperty().bindBidirectional(control.getProject().getAgencyProperty());
        genInfoTF2.textProperty().bindBidirectional(control.getProject().getAnalystProperty());
        genInfoTF3.textProperty().bindBidirectional(control.getProject().getNameProperty());
        genInfoTF4.textProperty().bindBidirectional(control.getProject().getUrlLinkProperty());
        genInfoTA2.textProperty().bindBidirectional(control.getProject().getDescriptionProperty());
        genInfoTA1.textProperty().bindBidirectional(control.getProject().getLimitsProperty());

        subStep1Button.disableProperty().bind(control.getProject().getStep(0).getSubStep(0).stepStartedProperty().not());
        subStep2Button.disableProperty().bind(control.getProject().getStep(0).getSubStep(1).stepStartedProperty().not());
        subStep3Button.disableProperty().bind(control.getProject().getStep(0).getSubStep(2).stepStartedProperty().not());
        subStep4Button.disableProperty().bind(control.getProject().getStep(0).getSubStep(3).stepStartedProperty().not());
        subStep5Button.disableProperty().bind(control.getProject().getStep(0).getSubStep(3).stepFinishedProperty().not());
    }

    private GridPane createGeneralInfoGrid() {
        // Setting up styles
        genInfoLabel1.setMaxWidth(MainController.MAX_WIDTH);
        genInfoLabel2.setMaxWidth(MainController.MAX_WIDTH);
        genInfoLabel3.setMaxWidth(MainController.MAX_WIDTH);
        genInfoLabel4.setMaxWidth(MainController.MAX_WIDTH);
        genInfoLabel5.setMaxWidth(MainController.MAX_WIDTH);
        genInfoLabel6.setMaxWidth(MainController.MAX_WIDTH);
        genInfoLabel7.setMaxWidth(MainController.MAX_WIDTH);
        genInfoLabel8.setMaxWidth(MainController.MAX_WIDTH);

        genInfoLabel1.getStyleClass().add("gen-info-label-style");
        genInfoLabel2.getStyleClass().add("gen-info-label-style");
        genInfoLabel3.getStyleClass().add("gen-info-label-style");
        genInfoLabel4.getStyleClass().add("gen-info-label-style");
        genInfoLabel5.getStyleClass().add("gen-info-label-style");
        genInfoLabel6.getStyleClass().add("gen-info-label-style");
        genInfoLabel7.getStyleClass().add("gen-info-label-style");
        genInfoLabel8.getStyleClass().add("gen-info-label-style");

        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        genInfoDateToday.setText(df.format(today));

        genInfoTA1.setWrapText(true);
        genInfoTA2.setWrapText(true);

        genInfoPicGrid.add(genInfoPicLabel, 0, 0);
        genInfoPicGrid.add(genInfoButton1, 1, 0);
        genInfoButton1.setMaxWidth(125);
        genInfoButton1.setDisable(true);
        genInfoPicLabel.setMaxWidth(MainController.MAX_WIDTH);
        genInfoPicGrid.getColumnConstraints().add(0, new ColumnConstraints(1, 125, MainController.MAX_WIDTH, Priority.ALWAYS, HPos.LEFT, true));
        genInfoPicGrid.getColumnConstraints().add(1, new ColumnConstraints(75, 75, 75, Priority.NEVER, HPos.RIGHT, true));

        GridPane grid = new GridPane();

        grid.add(genInfoLabel1, 0, 1);
        grid.add(genInfoLabel2, 0, 2);
        grid.add(genInfoLabel3, 0, 3);
        grid.add(genInfoLabel4, 0, 4);
        grid.add(genInfoLabel5, 0, 5);
        grid.add(genInfoLabel6, 0, 6);
        grid.add(genInfoLabel7, 0, 7);
        grid.add(genInfoLabel8, 0, 8);

        grid.add(genInfoTF1, 1, 1);
        grid.add(genInfoTF2, 1, 2);
        grid.add(genInfoDateToday, 1, 3);
        grid.add(genInfoTF3, 1, 4);
        grid.add(genInfoTA1, 1, 5);
        grid.add(genInfoTA2, 1, 6);
        grid.add(genInfoTF4, 1, 7);
        grid.add(genInfoPicGrid, 1, 8);

        double leftColsplit = 40;

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(leftColsplit);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(100 - leftColsplit);

        grid.getColumnConstraints().addAll(col1, col2);

        return grid;
    }

    private GridPane createWZInputGrid() {

        wzInputLabel1.setMaxWidth(MainController.MAX_WIDTH);
        wzInputLabel2.setMaxWidth(MainController.MAX_WIDTH);
        wzInputLabel3.setMaxWidth(MainController.MAX_WIDTH);
        wzInputLabel4.setMaxWidth(MainController.MAX_WIDTH);
        wzInputLabel5.setMaxWidth(MainController.MAX_WIDTH);
        wzInputLabel6.setMaxWidth(MainController.MAX_WIDTH);
        wzInputLabel7.setMaxWidth(MainController.MAX_WIDTH);
        wzInputLabel8.setMaxWidth(MainController.MAX_WIDTH);
        wzInputLabel9.setMaxWidth(MainController.MAX_WIDTH);
        wzInputChoice1.setMaxWidth(MainController.MAX_WIDTH);
        wzInputChoice2.setMaxWidth(MainController.MAX_WIDTH);

        wzInputLabel1.getStyleClass().add("wz-input-label-style");
        wzInputLabel2.getStyleClass().add("wz-input-label-style");
        wzInputLabel3.getStyleClass().add("wz-input-label-style");
        wzInputLabel4.getStyleClass().add("wz-input-label-style");
        wzInputLabel5.getStyleClass().add("wz-input-label-style");
        wzInputLabel6.getStyleClass().add("wz-input-label-style");
        wzInputLabel7.getStyleClass().add("wz-input-label-style");
        wzInputLabel8.getStyleClass().add("wz-input-label-style");
        wzInputLabel9.getStyleClass().add("wz-input-label-style");

        wzInputSpin1.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(25000, 125000, 50000, 5000));
        wzInputSpin2.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.1, 50, 2, 0.1));
        wzInputSpin3.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 8, 3, 1));
        wzInputSpin4.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 25, 10, 0.5));
        wzInputSpin5.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 100, 55, 5));
        wzInputSpin6.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 8, 1, 1));
        wzInputSpin7.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 24, 6, 1));

        wzInputChoice1.getSelectionModel().selectFirst();
        wzInputChoice2.getSelectionModel().selectFirst();

        proceedButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                control.getProject().getStep(1).setStepStarted(true);
            }
        });

        GridPane inputGrid = new GridPane();
        inputGrid.add(wzInputLabel1, 0, 0);
        inputGrid.add(wzInputLabel2, 0, 1);
        inputGrid.add(wzInputLabel3, 0, 2);
        inputGrid.add(wzInputLabel4, 0, 3);
        inputGrid.add(wzInputLabel5, 0, 4);
        inputGrid.add(wzInputLabel6, 0, 5);
        inputGrid.add(wzInputLabel7, 0, 6);
        inputGrid.add(wzInputLabel8, 0, 7);
        inputGrid.add(wzInputLabel9, 0, 8);

        inputGrid.add(wzInputSpin1, 1, 0);
        inputGrid.add(wzInputChoice1, 1, 1);
        inputGrid.add(wzInputSpin2, 1, 2);
        inputGrid.add(wzInputChoice2, 1, 3);
        inputGrid.add(wzInputSpin3, 1, 4);
        inputGrid.add(wzInputSpin4, 1, 5);
        inputGrid.add(wzInputSpin5, 1, 6);
        inputGrid.add(wzInputSpin6, 1, 7);
        inputGrid.add(wzInputSpin7, 1, 8);
        inputGrid.add(proceedButton, 1, 9);

        double leftColsplit = 65;
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(leftColsplit);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(100 - leftColsplit);

        inputGrid.getColumnConstraints().addAll(col1, col2);

        GridPane.setHgrow(wzInputLabel1, Priority.ALWAYS);
        GridPane.setHgrow(wzInputLabel2, Priority.ALWAYS);
        GridPane.setHgrow(wzInputLabel3, Priority.ALWAYS);
        GridPane.setHgrow(wzInputLabel4, Priority.ALWAYS);
        GridPane.setHgrow(wzInputLabel5, Priority.ALWAYS);
        GridPane.setHgrow(wzInputLabel6, Priority.ALWAYS);
        GridPane.setHgrow(wzInputLabel7, Priority.ALWAYS);
        GridPane.setHgrow(wzInputLabel8, Priority.ALWAYS);
        GridPane.setHgrow(wzInputLabel9, Priority.ALWAYS);

        GridPane.setHgrow(wzInputSpin1, Priority.ALWAYS);
        GridPane.setHgrow(wzInputSpin2, Priority.ALWAYS);
        GridPane.setHgrow(wzInputSpin3, Priority.ALWAYS);
        GridPane.setHgrow(wzInputSpin4, Priority.ALWAYS);
        GridPane.setHgrow(wzInputSpin5, Priority.ALWAYS);
        GridPane.setHgrow(wzInputSpin6, Priority.ALWAYS);
        GridPane.setHgrow(wzInputSpin7, Priority.ALWAYS);
        GridPane.setHgrow(wzInputChoice1, Priority.ALWAYS);
        GridPane.setHgrow(wzInputChoice2, Priority.ALWAYS);

        return inputGrid;
    }

    private void selectSubStep(int stepIndex) {
        moveScreen((stepIndex + 1) * stepIntroGrid.getWidth(), 0);
    }

    private void moveScreen(double toX, double toY) {
        moveScreen(toX, toY, true);
    }

    private void moveScreen(double toX, double toY, boolean animated) {
        if (animated) {
            TranslateTransition moveMe = new TranslateTransition(Duration.seconds(0.1), allSubStepsPane);
            moveMe.setToX(-1 * toX);
            moveMe.setToY(toY);
            moveMe.play();
        } else {
            allSubStepsPane.setTranslateX(-1 * toX);
        }
    }

    private final Label genInfoLabel1 = new Label("State Agency");
    private final Label genInfoLabel2 = new Label("Analyst");
    private final Label genInfoLabel3 = new Label("Date");
    private final Label genInfoLabel4 = new Label("Project Name");
    private final Label genInfoLabel5 = new Label("Project Description");
    private final Label genInfoLabel6 = new Label("Project Limits");
    private final Label genInfoLabel7 = new Label("Link to Project Website");
    private final Label genInfoLabel8 = new Label("Upload Photo");

    private final TextField genInfoTF1 = new TextField("");
    private final TextField genInfoTF2 = new TextField("");
    private final Label genInfoDateToday = new Label();
    private final TextField genInfoTF3 = new TextField("");
    private final TextArea genInfoTA1 = new TextArea("");
    private final TextArea genInfoTA2 = new TextArea("");
    private final TextField genInfoTF4 = new TextField("");
    private final Label genInfoPicLabel = new Label("Select file...");
    private final Button genInfoButton1 = new Button("Browse");
    private final GridPane genInfoPicGrid = new GridPane();

    private final Label wzInputLabel1 = new Label("Annual Average Daily Traffic (AADT)");
    private final Label wzInputLabel2 = new Label("Functional Class of Roadway:");
    private final Label wzInputLabel3 = new Label("Work Zone Length (mi):");
    private final Label wzInputLabel4 = new Label("Work Zone Type:");
    private final Label wzInputLabel5 = new Label("Number of Roadway Lanes (1 Direction):");
    private final Label wzInputLabel6 = new Label("Shoulder Width (ft):");
    private final Label wzInputLabel7 = new Label("Posted Speed Limit (mph)");
    private final Label wzInputLabel8 = new Label("Number of Lanes to be Closed:");
    private final Label wzInputLabel9 = new Label("Duration of Activity (hr)");

    private final Spinner wzInputSpin1 = new Spinner();
    private final Spinner wzInputSpin2 = new Spinner();
    private final Spinner wzInputSpin3 = new Spinner();
    private final Spinner wzInputSpin4 = new Spinner();
    private final Spinner wzInputSpin5 = new Spinner();
    private final Spinner wzInputSpin6 = new Spinner();
    private final Spinner wzInputSpin7 = new Spinner();

    private final ChoiceBox wzInputChoice1 = new ChoiceBox(FXCollections.observableArrayList("Select", "Freeway", "Arterial, Local"));
    private final ChoiceBox wzInputChoice2 = new ChoiceBox(FXCollections.observableArrayList("Select", "Mobile", "Permanent"));

    private final Button proceedButton = new Button("Proceed");

}
