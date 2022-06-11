package pt.isec.pa.apoio_poe.ui.gui;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import pt.isec.pa.apoio_poe.model.Manager;
import pt.isec.pa.apoio_poe.model.fsm.AppState;

public class StageOneUI extends BorderPane {
    Manager manager;
    Button btnAdvance;
    Button btnExit;

    public StageOneUI(Manager manager) {
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        btnAdvance = new Button("Next Stage");
        btnExit = new Button("Quit");
        HBox hBox = new HBox(btnAdvance,btnExit);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        this.setCenter(hBox);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(Manager.STATE, evt -> update());
        btnAdvance.setOnAction(event -> manager.advanceStage());
        btnExit.setOnAction(event -> Platform.exit());
    }

    private void update() {
        this.setVisible(manager.getState() == AppState.CONFIGURATIONS_STATE_STAGE_ONE);
    }
}
