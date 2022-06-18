package pt.isec.pa.apoio_poe.ui.gui.StageTwo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import pt.isec.pa.apoio_poe.model.Manager;
import pt.isec.pa.apoio_poe.ui.gui.util.ToastMessage;

import java.util.Arrays;

public class DialogAddApplication extends Dialog{
    private final Manager manager;
    ChoiceBox studentCB;
    ChoiceBox proposalCB1, proposalCB2, proposalCB3, proposalCB4, proposalCB5, proposalCB6;
    //Dialog dialog;
    GridPane grid;

    public DialogAddApplication(Manager manager) {
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
//        dialog = new Dialog();
        this.setTitle("Add a new Application");
//            dialog.setHeaderText("Insert Student data");
        //ButtonType btApply = new ButtonType("Apply");
        this.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);

        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        studentCB = new ChoiceBox();
        studentCB.getItems().addAll(manager.getAvailableStudentsWithoutApplication());
        proposalCB1 = new ChoiceBox();
        proposalCB1.getItems().addAll(manager.getAvailableProposals());
        proposalCB2 = new ChoiceBox();
        proposalCB2.getItems().addAll(manager.getAvailableProposals());
        proposalCB3 = new ChoiceBox();
        proposalCB3.getItems().addAll(manager.getAvailableProposals());
        proposalCB4 = new ChoiceBox();
        proposalCB4.getItems().addAll(manager.getAvailableProposals());
        proposalCB5 = new ChoiceBox();
        proposalCB5.getItems().addAll(manager.getAvailableProposals());
        proposalCB6 = new ChoiceBox();
        proposalCB6.getItems().addAll(manager.getAvailableProposals());

        grid.add(new Label("Student"), 0,0);
        grid.add(studentCB, 1, 0);
        grid.add(new Label("1st Choice"), 0,1);
        grid.add(proposalCB1, 1, 1);
        grid.add(new Label("2st Choice"), 2,1);
        grid.add(proposalCB2, 3, 1);
        grid.add(new Label("3st Choice"), 0,2);
        grid.add(proposalCB3, 1, 2);
        grid.add(new Label("4st Choice"), 2,2);
        grid.add(proposalCB4, 3, 2);
        grid.add(new Label("5st Choice"), 0,3);
        grid.add(proposalCB5, 1, 3);
        grid.add(new Label("6st Choice"), 2,3);
        grid.add(proposalCB6, 3, 3);

        this.getDialogPane().setContent(grid);
    }

    private void registerHandlers() {
        final Button btnApply = (Button) this.getDialogPane().lookupButton(ButtonType.APPLY);
        btnApply.addEventFilter(ActionEvent.ACTION, event -> {
            if(!buttonsAreSelected()) {
                event.consume();
                ToastMessage.show(grid.getScene().getWindow(), "Some buttons are not correctly selected.");
            } else if(!manager.add(
                    (String) studentCB.getValue(),
                    (String) proposalCB1.getValue(),
                    (String) proposalCB2.getValue(),
                    (String) proposalCB3.getValue(),
                    (String) proposalCB4.getValue(),
                    (String) proposalCB5.getValue(),
                    (String) proposalCB6.getValue()
            )) {
                event.consume();
                ToastMessage.show(grid.getScene().getWindow(), "Incorrect parameters.");
            }
        });
    }

    private void update() {
    }

    private boolean buttonsAreSelected(){

        if(studentCB.getValue() == null)
            return false;
        else if(proposalCB2.getValue() != null && proposalCB1.getValue() == null)
            return false;
        else if(proposalCB3.getValue() != null &&
                (proposalCB2.getValue() == null || proposalCB1 == null))
            return false;
        else if(proposalCB4.getValue() != null &&
                (proposalCB3.getValue() ==null || proposalCB2.getValue() == null || proposalCB1 == null))
            return false;
        else if(proposalCB5.getValue() != null &&
                (proposalCB4.getValue() == null || proposalCB3.getValue() == null || proposalCB2.getValue() == null || proposalCB1 == null))
            return false;
        else if(proposalCB6.getValue() != null &&
                (proposalCB5.getValue() == null || proposalCB4.getValue() == null || proposalCB3.getValue() == null || proposalCB2.getValue() == null || proposalCB1 == null))
            return false;

        return true;
    }
}
