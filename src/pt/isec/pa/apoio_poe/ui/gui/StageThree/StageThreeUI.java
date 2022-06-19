package pt.isec.pa.apoio_poe.ui.gui.StageThree;

import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import pt.isec.pa.apoio_poe.model.Manager;
import pt.isec.pa.apoio_poe.model.fsm.AppState;
import pt.isec.pa.apoio_poe.model.fsm.ListingType;
import pt.isec.pa.apoio_poe.ui.gui.ListPane;
import pt.isec.pa.apoio_poe.ui.gui.MenuBarUI;
import pt.isec.pa.apoio_poe.ui.gui.StageTwo.StageTwoToolBarUI;

public class StageThreeUI extends BorderPane {
    private final Manager manager;

    public StageThreeUI(Manager manager) {
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        manager.setListingType(ListingType.NONE);

        MenuBar menuBar = new MenuBarUI(manager);
        this.setTop(menuBar);

        StageThreeToolBarUI toolBar = new StageThreeToolBarUI(manager);
        this.setBottom(toolBar);

        StackPane centerPane = new StackPane(new ListPane(manager));
        this.setCenter(centerPane);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(Manager.STATE, evt -> update());
        manager.addPropertyChangeListener(Manager.DATA, evt -> update());
    }

    private void update() {
        this.setVisible(manager.getState() == AppState.PROPOSAL_ATTRIBUTION_PREV_OPEN_STAGE_THREE || manager.getState() == AppState.PROPOSAL_ATTRIBUTION_PREV_CLOSED_STAGE_THREE);
    }
}
