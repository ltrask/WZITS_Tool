/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.Helper.ColorHelper;
import GUI.Helper.IOHelper;
import core.Project;

import java.io.File;
import java.util.HashMap;
import java.util.Optional;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author ltrask
 */
public class MainController {

    private MainWindow mainWindow;

    private static Stage stage;

    private static StackPane rootStackPane;

    private Project proj;

    /**
     * Indicates if the project has been started, bound to the activeStep
     * property
     */
    private final SimpleBooleanProperty projectStarted = new SimpleBooleanProperty(false);
    /**
     * Index of the step currently active.
     */
    private final SimpleIntegerProperty activeStep = new SimpleIntegerProperty(-1);
    /**
     * Index of the sub step currently active.
     */
    private final SimpleIntegerProperty[] activeSubStep = new SimpleIntegerProperty[Project.NUM_STEPS];

    /**
     * Mapping to store the last utilized save location for file types
     */
    public static final HashMap<String, Object> lastSaveLocations = new HashMap<>();


    public MainController(Stage stage) {
        this.stage = stage;
        proj = new Project("WZITS Project");
        for (int stepIdx = 0; stepIdx < activeSubStep.length; stepIdx++) {
            activeSubStep[stepIdx] = new SimpleIntegerProperty(-2);
        }

        activeStep.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
                if (!projectStarted.get() && (int) newVal >= 0) {
                    projectStarted.set(true);
                }
            }
        });

    }

    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public MainWindow getMainWindow() {
        return mainWindow;
    }

    public static Stage getStage() {
        return stage;
    }

    public static Window getWindow() {
        //return stage.getOwner();
        return stage.getOwner();
    }

    public static void setRootStackPane(StackPane rootStackPane) {
        MainController.rootStackPane = rootStackPane;
    }

    public static StackPane getRootStackPane() {
        return MainController.rootStackPane;
    }

    public void setMainWindowTitleLabel(String newLabelText) {
        mainWindow.setTitleLabel(newLabelText, true);
    }

    public void updateMainWindowTitle() {
        if (getActiveStep() < 0) {
            setMainWindowTitleLabel(INTRO_TITLE);
        } else if (getActiveStep() == Project.NUM_STEPS) {
            setMainWindowTitleLabel(SUMMARY_TITLE);
        } else {
            setMainWindowTitleLabel("Step " + String.valueOf(getActiveStep() + 1) + ": " + STEP_TITLES[getActiveStep()]);
        }
    }

    public int getActiveStep() {
        return this.activeStep.get();
    }

    public void setActiveStep(int stepIdx) {
        this.activeStep.set(stepIdx);
    }

    public SimpleIntegerProperty activeStepProperty() {
        return activeStep;
    }

    public SimpleBooleanProperty projectStartedProperty() {
        return projectStarted;
    }

    public ReadOnlyDoubleProperty stageWidthProperty() {
        return stage.widthProperty();
    }

    /**
     * Check the active substep for a given step.
     *
     * @param stepIdx
     * @return
     */
    public int getActiveSubStep(int stepIdx) {
        if (stepIdx >= 0 && stepIdx < activeSubStep.length) {
            return activeSubStep[stepIdx].get();
        } else {
            return -1;
        }
    }

    /**
     * Set the active substep for a given step.
     *
     * @param stepIdx
     * @param subStepIdx
     */
    public void setActiveSubStep(int stepIdx, int subStepIdx) {
        if (stepIdx >= 0 && stepIdx < Project.NUM_STEPS) {
            this.activeSubStep[stepIdx].set(subStepIdx);
        }
    }

    /**
     * Getter for the active substep property for a given step.
     *
     * @param stepIdx
     * @return
     */
    public SimpleIntegerProperty activeSubStepProperty(int stepIdx) {
        return activeSubStep[stepIdx];
    }

    public void selectStep(int stepIdx) {
        selectStep(stepIdx, -1);
    }

    public void selectStep(int stepIdx, int subStepIdx) {
        setActiveStep(stepIdx);
        setActiveSubStep(stepIdx, subStepIdx);
    }

    public void stepBack() {
        if (activeStep.get() < 0) {
            //selectStep(-1, -1);
        } else if (activeStep.get() == 0 && activeSubStep[activeStep.get()].get() < 0) {
            selectStep(-1, -1);
        } else {
            if (activeSubStep[activeStep.get()].get() >= 0) {
                selectStep(activeStep.get(), activeSubStep[activeStep.get()].get() - 1);
            } else {
                selectStep(activeStep.get() - 1, activeSubStep[activeStep.get() - 1].get());
            }
        }
    }

    public void stepForward() {
        if (activeStep.get() < 0) {
            selectStep(0, -1);
        } else if (activeStep.get() < Project.NUM_STEPS) {
            if (activeSubStep[activeStep.get()].get() < Project.NUM_SUB_STEPS[activeStep.get()]) {
                selectStep(activeStep.get(), activeSubStep[activeStep.get()].get() + 1);
            } else {
                selectStep(activeStep.get() + 1, -1);
            }
        } else {
            //selectStep(-1, -1);
        }
    }

    public void begin() {
        stage.hide();
        stage.setMaximized(true);
        mainWindow.begin();
        stage.show();
        //stage.setMinWidth(mainWindow.getMinWidth());
    }

    public void newProjectOpened() {
        stage.hide();
        Alert al = new Alert(Alert.AlertType.NONE);
        al.initOwner(getWindow());
        al.setTitle("Please Wait");
        al.setHeaderText("Loading WZITS Project...");
        ProgressBar ipb = new ProgressBar(1.0);
        ipb.setStyle("-fx-accent: " + ColorHelper.WZ_ORANGE);
        al.setGraphic(ipb);
        al.getButtonTypes().add(ButtonType.OK);
        Button cancelButton = (Button) al.getDialogPane().lookupButton(ButtonType.OK);
        cancelButton.setText("Loading...");
        cancelButton.setDisable(true);
        al.show();
        stage.setMaximized(true);
        Scene newScene = new Scene(new MainWindow(this, false));
        newScene.getStylesheets().add(getClass().getResource("/GUI/CSS/globalStyle.css").toExternalForm());
        stage.setScene(newScene);
        stage.show();
        cancelButton.setDisable(false);
        cancelButton.fire();
        selectStep(-1);
        selectStep(0, 0);

    }

    public double getAppWidth() {
        return stage.getWidth();
    }

    public Project getProject() {
        return proj;
    }

    public void checkProceed() {
        //mainWindow.enableProceed(proj.isStepComplete(stepIndex));
        mainWindow.checkProceed();
    }

    public static Tooltip getTooltip(int stepIdx, int subStepIdx) {
        switch (stepIdx) {
            default:
                return null;
            case 0:
                if (subStepIdx >= 0 && subStepIdx < STEP_1_TOOLTIPS.length) {
                    return new Tooltip(STEP_1_TOOLTIPS[subStepIdx]);
                } else {
                    return new Tooltip("Step 1: Assessment of Needs");
                }
            case 1:
                if (subStepIdx >= 0 && subStepIdx < STEP_2_TOOLTIPS.length) {
                    return new Tooltip(STEP_2_TOOLTIPS[subStepIdx]);
                } else {
                    return new Tooltip("Step 2: Concept Development and Feasibility");
                }
        }
    }

    public void newProject() {
        Alert al = new Alert(Alert.AlertType.CONFIRMATION,
                "Save Current Project?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        al.setTitle("WZITS Tool");
        al.setHeaderText("WZITS Tool");
        Optional<ButtonType> result = al.showAndWait();
        if (result.isPresent()) {
            if (result.get() != ButtonType.CANCEL) {
                if (result.get() == ButtonType.YES) {
                    int saveResult = saveProject();
                    IOHelper.confirm(saveResult);
                }
                this.proj.setFromProject(new Project());
                this.newProjectOpened();
                MainController.updateProgramHeader(this.proj);
            }
        }
    }

    public void openProject() {
        Alert al = new Alert(Alert.AlertType.CONFIRMATION,
                "Save Current Project?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        al.setTitle("WZITS Tool");
        al.setHeaderText("WZITS Tool");
        Optional<ButtonType> result = al.showAndWait();
        if (result.isPresent()) {
            if (result.get() != ButtonType.CANCEL) {
                if (result.get() == ButtonType.YES) {
                    int saveResult = saveProject();
                    IOHelper.confirm(saveResult);
                }
                Project openedProj = IOHelper.openProject(this);
                if (openedProj != null) {
                    this.proj.setFromProject(openedProj);
                    this.newProjectOpened();
                    MainController.updateProgramHeader(this.proj);
                }
            }
        }
    }

    public int saveProject() {
        int res = IOHelper.saveProject(this, proj);
        if (res == IOHelper.SAVE_COMPLETED) {
            MainController.updateProgramHeader(this.proj);
        }
        return res;
    }

    public int saveAsProject() {
        int res = IOHelper.saveAsProject(this, proj);
        if (res == IOHelper.SAVE_COMPLETED) {
            MainController.updateProgramHeader(this.proj);
        }
        return res;
    }

    public void exitProgram() {
        Alert al = new Alert(Alert.AlertType.CONFIRMATION,
                "Save Project Before Exiting?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        al.setTitle("Exiting WZITS Tool");
        al.setHeaderText("WZITS Tool");
        Optional<ButtonType> result = al.showAndWait();
        if (result.isPresent()) {
            if (result.get() == ButtonType.NO) {
                stage.close();
            } else if (result.get() == ButtonType.YES) {
                int saveResult = IOHelper.saveProject(this, proj);
                if (saveResult == IOHelper.SAVE_COMPLETED) {
                    stage.close();
                }
            } else {
                // Cancelled by user, do nothing
            }
        }
    }

    public static void updateProgramHeader(Project p) {
        String titleString = "Work Zone Intelligent Transportations Systems Tool";
        titleString = titleString + (p.getName() != null ? " - " + p.getName() : "");
        titleString = titleString + (p.getSaveFile() != null ? " (" + p.getSaveFile().getAbsolutePath() + ")" : "");
        stage.setTitle(titleString);
    }

    public Node goToFactSheet(int factSheetIdx, boolean useSummary) {
        if (!useSummary) {
            return mainWindow.goToFactSheet(factSheetIdx);
        } else {
            return mainWindow.goToSummaryFactSheet(factSheetIdx, false);
        }
    }

    public static void setLastSaveLocation(String fileType, String directory) {
        MainController.lastSaveLocations.put(fileType, directory);
    }

    public static String getLastSaveLocation(String fileType) {
        Object lastDir = MainController.lastSaveLocations.getOrDefault(fileType, null);
        if (lastDir != null) {
            return lastDir.toString();
        }
        return null;
    }

    public static String getResFolderLocation() {
        String location = MainController.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        location = location.replaceAll("%20", " ");
        if (location.contains("/build/classes")) {
            location = location.substring(0, location.lastIndexOf("/build")) + "/"; // + "resources" + "/";
        }
        location = location.substring(0, location.lastIndexOf("/")) + "/" + "pdfres";
        File pdfresFolder = new File(location);
        if (!pdfresFolder.exists()) {
            //pdfresFolder.mkdirs();
        }
        return location + "/";
    }

    public static final int MAX_WIDTH = 999999;
    public static final int MAX_HEIGHT = 999999;

    public static final String[] STEP_1_TOOLTIPS = new String[]{
        "Project Info and Work Zone Metadata",
        "User Needs",
        "User Needs Supplemental",
        "System Goals",
        "Step Summary"};
    public static final String[] STEP_2_TOOLTIPS = new String[]{
        "Project Info and Work Zone Metadata",
        "User Needs",
        "User Needs Supplemental",
        "System Goals",
        "Step Summary"};

    public static final String[] STEP_TITLES = new String[]{
        "Assessment of Needs & Feasibility",
        "Concept Development",
        "System Planning & Design",
        "Procurement",
        "System Deployment",
        "System Operation, Maintenance & Evaluation"
    };

    public static final String INTRO_TITLE = "Project Introduction";

    public static final String SUMMARY_TITLE = "Project Summary";

    public static final int FADE_TIME = 50;

    public static final String VERSION = "2.0.0";

}
