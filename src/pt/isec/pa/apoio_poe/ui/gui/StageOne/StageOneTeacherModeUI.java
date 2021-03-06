package pt.isec.pa.apoio_poe.ui.gui.StageOne;

import javafx.scene.control.MenuBar;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import pt.isec.pa.apoio_poe.model.Manager;
import pt.isec.pa.apoio_poe.model.fsm.AppState;
import pt.isec.pa.apoio_poe.model.fsm.ListingType;
import pt.isec.pa.apoio_poe.ui.gui.ListPane;
import pt.isec.pa.apoio_poe.ui.gui.MenuBarUI;

public class StageOneTeacherModeUI extends BorderPane {
    private final Manager manager;

    public StageOneTeacherModeUI(Manager manager) {
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        manager.setListingType(ListingType.TEACHERS);

        MenuBar menuBar = new MenuBarUI(manager);
        this.setTop(menuBar);

        ToolBar toolBar = new StageOneToolBarUI(manager);
        this.setBottom(toolBar);

        StackPane centerPane = new StackPane(new ListPane(manager));
        this.setCenter(centerPane);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(Manager.STATE, evt -> update());
        manager.addPropertyChangeListener(Manager.DATA, evt -> update());
    }

    private void update() {
        this.setVisible(manager.getState() == AppState.CONFIGURATIONS_STATE_TEACHER_MANAGER);
    }
}
