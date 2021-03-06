/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Helper;

import GUI.MainController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 *
 * @author ltrask
 */
public class IconHelper {

    public static final String SVG_STR_PREV = "M 25 0 L 25 50 L 0 25 z";

    public static final String SVG_STR_RIGHTARROW_SMALL = "M 0 0 L 0 10 L 5 5 z";

    public static final String SVG_STR_NEXT = "M 0 0 L 0 50 L 25 25 z";

    public static final String SVG_STR_PREV_SMALL = "M 25 0 L 25 25 L 0 12 z";

    public static final String SVG_STR_NEXT_SMALL = "M 0 0 L 0 25 L 25 12 z";

    public static final Image TREE_NODE_PROJ_CLOSE = new Image(IconHelper.class.getResourceAsStream("/GUI/Icon/folder_close_48.png"), 16, 16, true, true);

    public static final Image TREE_NODE_PROJ_OPEN = new Image(IconHelper.class.getResourceAsStream("/GUI/Icon/folder_open_48.png"), 16, 16, true, true);

    public static final Image TREE_NODE_STEP_COMPLETE = new Image(IconHelper.class.getResourceAsStream("/GUI/Icon/check16.png"));

    public static final Image SPLASH_SCREEN = new Image(IconHelper.class.getResourceAsStream("/GUI/Icon/splash v2.PNG"));

    public static final Image NAV_HELPER = new Image(IconHelper.class.getResourceAsStream("/GUI/Icon/navigatorHelp.PNG"));

    public static final Image FIG_FLOW_ALL_STEPS = new Image(IconHelper.class.getResourceAsStream("/GUI/Icon/all_steps.PNG"));

    public static final Image FIG_FLOW_STEP_1 = new Image(IconHelper.class.getResourceAsStream("/GUI/Icon/step_1.png"));

    public static final Image FIG_FLOW_STEP_2 = new Image(IconHelper.class.getResourceAsStream("/GUI/Icon/step_2.png"));

    public static final Image FIG_FLOW_STEP_3 = new Image(IconHelper.class.getResourceAsStream("/GUI/Icon/step_3.png"));

    public static final Image FIG_FLOW_STEP_4 = new Image(IconHelper.class.getResourceAsStream("/GUI/Icon/step_4.png"));

    public static final Image FIG_FLOW_STEP_5 = new Image(IconHelper.class.getResourceAsStream("/GUI/Icon/step_5.png"));

    public static final Image FIG_FLOW_STEP_6 = new Image(IconHelper.class.getResourceAsStream("/GUI/Icon/step_6.png"));

    public static final Image PROJ_IMAGE = new Image(IconHelper.class.getResourceAsStream("/GUI/Icon/wz.jpg"));

    public static FontIcon createIcon(Ikon iconId, Color iconColor, int size) {
        FontIcon icon = new FontIcon(iconId);
        icon.setIconColor(iconColor);
        icon.setIconSize(size);
        return icon;
    }
}
