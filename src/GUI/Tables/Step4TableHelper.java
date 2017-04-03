/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Tables;

import core.Project;
import javafx.scene.Node;
import javafx.scene.control.TableView;

/**
 *
 * @author ltrask
 */
public class Step4TableHelper extends TableView {

    private static final int STEP_INDEX = 3;

    private static final String STEP4_TABLE_CSS = "step-one-table";

    public static Node createDirectIndirectNode(Project proj) {
        //return TableHelper.createQuestionYNTable(proj.getQGen().qDirectIndirectList, new TableHelper.Options(STEP4_TABLE_CSS));
        return TableHelper.createCommentPage(proj.getQGen().qDirectIndirectList);
    }

    public static Node createMechanismNode(Project proj) {
        //return TableHelper.createQuestionYNTable(proj.getQGen().qMechanismList, new TableHelper.Options(STEP4_TABLE_CSS));
        return TableHelper.createCommentPage(proj.getQGen().qMechanismList);
    }

    public static Node createRFPNode(Project proj) {
        //return TableHelper.createQuestionYNTable(proj.getQGen().qRFPList, new TableHelper.Options(STEP4_TABLE_CSS));
        return TableHelper.createCommentPage(proj.getQGen().qRFPList);
    }

    public static Node createVendorNode(Project proj) {
        //return TableHelper.createQuestionYNTable(proj.getQGen().qVendorSelectionList, new TableHelper.Options(STEP4_TABLE_CSS));
        return TableHelper.createCommentPage(proj.getQGen().qVendorSelectionList);
    }

}