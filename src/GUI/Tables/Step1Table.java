/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Tables;

import core.Application;
import core.QuestionYN;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author ltrask
 */
public class Step1Table {

    private static final int STEP_INDEX = 0;

    public static TableView createPageTable(int page, int questionsPerPage) {

        int startRow = page * questionsPerPage;
        int endRow = Math.min((page + 1) * questionsPerPage, TableHelper.getNumberOfQuestionsByStep(STEP_INDEX));

        final TableView<QuestionYN> table = new TableView();
        table.setEditable(true);

        // Setting up table columns
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn indexCol = new TableColumn("#");
        indexCol.setCellValueFactory(new PropertyValueFactory<>("idx"));
        indexCol.setPrefWidth(25);
        indexCol.setMaxWidth(25);
        indexCol.setMinWidth(25);
        indexCol.getStyleClass().add("col-style-center");

        TableColumn goalCol = new TableColumn("WZITS Goal Category");
        goalCol.setCellValueFactory(new PropertyValueFactory<>("goal"));
        goalCol.setPrefWidth(200);
        goalCol.setMaxWidth(200);
        goalCol.setMinWidth(200);
        goalCol.getStyleClass().add("col-style-center");

        TableColumn questionCol = new TableColumn("Input Question");
        questionCol.setCellValueFactory(new PropertyValueFactory<>("questionText"));

        TableColumn responseCol = new TableColumn("User Response");
        responseCol.setPrefWidth(150);
        responseCol.setMaxWidth(150);
        responseCol.setMinWidth(150);
        responseCol.setCellValueFactory(new PropertyValueFactory<>("responseIdx"));
//        responseCol.setCellFactory(ChoiceBoxTableCell.forTableColumn(Question.yesNoConverter, FXCollections.observableArrayList(-1, 0, 1)));
//        responseCol.setOnEditCommit(new EventHandler<CellEditEvent<Question, Integer>>() {
//            @Override
//            public void handle(CellEditEvent<Question, Integer> t) {
//                ((Question) t.getTableView().getItems().get(t.getTablePosition().getRow())).setResponseIdx(t.getNewValue());
//            }
//        });

        final TableColumn yesCol = new TableColumn<QuestionYN, Boolean>("Yes");
        yesCol.setCellValueFactory(new PropertyValueFactory<>("answerIsYes"));
        yesCol.setCellFactory(CheckBoxTableCell.forTableColumn(yesCol));
//        yesCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Question, Boolean>>() {
//            @Override
//            public void handle(TableColumn.CellEditEvent<Question, Boolean> t) {
//                ((Question) t.getTableView().getItems().get(t.getTablePosition().getRow())).setResponseIdx(1);
//            }
//        });
        TableColumn noCol = new TableColumn("No");
        noCol.setCellValueFactory(new PropertyValueFactory<>("answerIsNo"));
        noCol.setCellFactory(CheckBoxTableCell.forTableColumn(noCol));
//        noCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Question, Boolean>>() {
//            @Override
//            public void handle(TableColumn.CellEditEvent<Question, Boolean> t) {
//
//            }
//        });

        yesCol.setPrefWidth(75);
        yesCol.setMaxWidth(75);
        yesCol.setMinWidth(75);
        noCol.setPrefWidth(75);
        noCol.setMaxWidth(75);
        noCol.setMinWidth(75);
        responseCol.getColumns().addAll(yesCol, noCol);

        table.getColumns().addAll(indexCol, goalCol, questionCol, responseCol);

        // Setting Table Content
        final ObservableList<QuestionYN> stepQuestions = TableHelper.getStepQuestions(STEP_INDEX);
        table.setItems(FXCollections.observableArrayList(stepQuestions.subList(startRow, endRow)));
        table.getStyleClass().add("step-one-table");

        table.setSelectionModel(null);

        ContextMenu cMenu = new ContextMenu();
        MenuItem fillAllYesMenuItem = new MenuItem("Fill All Yes");
        fillAllYesMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                for (QuestionYN q : stepQuestions) {
                    q.setAnswerIsYes(Boolean.TRUE);
                }
            }
        });
        MenuItem fillAllNoMenuItem = new MenuItem("Fill All No");
        fillAllNoMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                for (QuestionYN q : stepQuestions) {
                    q.setAnswerIsNo(Boolean.TRUE);
                }
            }
        });
        Menu fillByTemplateMenu = new Menu("Fill By Template");
        MenuItem fillUrbanMenuItem = new MenuItem("Urban Template");
        MenuItem fillRuralMenuItem = new MenuItem("Rural Template");
        fillByTemplateMenu.getItems().addAll(fillUrbanMenuItem, fillRuralMenuItem);
        cMenu.getItems().addAll(fillAllYesMenuItem, fillAllNoMenuItem, fillByTemplateMenu);

        table.setContextMenu(cMenu);

        return table;

    }

    public static int getPageCount(int stepIdx, int questionsPerPage) {
        return Math.floorDiv(TableHelper.getNumberOfQuestionsByStep(stepIdx), questionsPerPage);
    }

    public static int getPageCount(int stepIdx) {
        return getPageCount(stepIdx, 10);
    }

}
