package pt.isec.pa.apoio_poe.ui.gui.StageThree;

import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import pt.isec.pa.apoio_poe.model.Manager;
import pt.isec.pa.apoio_poe.model.fsm.AppState;

public class ConflictStageUI extends BorderPane {
    private final Manager manager;
    ChoiceBox cbConflict = new ChoiceBox();

    public ConflictStageUI(Manager manager) {
        this.manager = manager;
//        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
//        cbConflict = new ChoiceBox();
        cbConflict.setValue(manager.getConflictedProposal());
        cbConflict.getItems().addAll(manager.getConflictedCases());
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(Manager.STATE, evt -> update());

        cbConflict.setOnAction(actionEvent -> {
            manager.resolveConflictedCases(cbConflict.getSelectionModel().getSelectedIndex());
        });
    }
    private void update() {
        if (manager.getState() == AppState.CONFLICT_STAGE) {
            this.setVisible(true);
            createViews();
        }
        else
            this.setVisible(false);
    }
}
