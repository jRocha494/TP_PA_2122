package pt.isec.pa.apoio_poe.ui.gui.StageThree;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pa.apoio_poe.model.Manager;
import pt.isec.pa.apoio_poe.model.fsm.AppState;

public class ConflictStageUI extends DialogPane {
    private final Manager manager;
    ChoiceBox cbConflict; //= new ChoiceBox();

    public ConflictStageUI(Manager manager) {
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        cbConflict = new ChoiceBox();
        cbConflict.setValue(manager.getConflictedProposal());
        cbConflict.getItems().addAll(manager.getConflictedCases());

        grid.add(new Label("Conflict Cases"), 0,0);
        grid.add(cbConflict, 1, 0);
        grid.setBackground(new Background(new BackgroundFill(Color.KHAKI, CornerRadii.EMPTY, Insets.EMPTY)));

        this.getButtonTypes().addAll(ButtonType.APPLY);
        this.setContent(grid);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(Manager.STATE, evt -> update());
        manager.addPropertyChangeListener(Manager.DATA, evt -> update());

        final Button btnApply = (Button) this.lookupButton(ButtonType.APPLY);
        btnApply.addEventFilter(ActionEvent.ACTION, actionEvent -> {
            manager.resolveConflictedCases(cbConflict.getSelectionModel().getSelectedIndex());
        });

        /*cbConflict.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                System.out.println(cbConflict.getItems().get((Integer) number2));
                manager.resolveConflictedCases((Integer) number2);
            }
        });*/
        /*cbConflict.setOnAction(actionEvent -> {
            manager.resolveConflictedCases(cbConflict.getSelectionModel().getSelectedIndex()+1);
        });*/
    }
    private void update() {
        if (manager.getState() == AppState.CONFLICT_STAGE) {
            cbConflict.getItems().clear();
            cbConflict.setValue(manager.getConflictedProposal());
            cbConflict.getItems().addAll(manager.getConflictedCases());
            this.setVisible(true);
            //createViews();
        }
        else
            this.setVisible(false);
    }
}
