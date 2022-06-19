package pt.isec.pa.apoio_poe.ui.gui.StageTwo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import pt.isec.pa.apoio_poe.model.Manager;
import pt.isec.pa.apoio_poe.model.data.Proposal;
import pt.isec.pa.apoio_poe.model.data.Student;
import pt.isec.pa.apoio_poe.ui.gui.util.ToastMessage;

import java.util.Arrays;
import java.util.List;

public class DialogAddApplication extends Dialog{
    private final Manager manager;
    ChoiceBox studentCB;
    ChoiceBox[] proposal;
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
        List<Student> studentsList = manager.getAvailableStudentsWithoutApplication();
        if (studentsList.size() > 0){
            studentCB.getItems().add("---");
            studentCB.getItems().addAll(studentsList);
        } else
            studentCB.getItems().addAll("---");
        studentCB.setValue("---");
        //studentCB.getItems().addAll(manager.getAvailableStudentsWithoutApplication());

        proposal = new ChoiceBox[6];
        for (int i = 0; i < 6; i++) {
            proposal[i] = new ChoiceBox();
        }
        List<Proposal> proposalsList = manager.getAvailableProposalsList();
        for(int i=0; i<proposal.length; i++){
            if(proposalsList.size() > 0){
                proposal[i].getItems().add("None");
                proposal[i].getItems().addAll(proposalsList);
            }else
                proposal[i].getItems().add("None");
            proposal[i].setValue("None");
        }

        grid.add(new Label("Student"), 0,0);
        grid.add(studentCB, 1, 0);
        grid.add(new Label("1st Choice"), 0,1);
        grid.add(proposal[0], 1, 1);
        grid.add(new Label("2nd Choice"), 2,1);
        grid.add(proposal[1], 3, 1);
        grid.add(new Label("3rd Choice"), 0,2);
        grid.add(proposal[2], 1, 2);
        grid.add(new Label("4th Choice"), 2,2);
        grid.add(proposal[3], 3, 2);
        grid.add(new Label("5th Choice"), 0,3);
        grid.add(proposal[4], 1, 3);
        grid.add(new Label("6th Choice"), 2,3);
        grid.add(proposal[5], 3, 3);

        this.getDialogPane().setContent(grid);
    }

    private void registerHandlers() {
        final Button btnApply = (Button) this.getDialogPane().lookupButton(ButtonType.APPLY);
        btnApply.addEventFilter(ActionEvent.ACTION, actionEvent -> {
            if(!buttonsAreSelected()) {
                actionEvent.consume();
                ToastMessage.show(grid.getScene().getWindow(), "Some buttons are not correctly selected.");
            } else{
                Proposal[] chosenProposals = new Proposal[6];
                for (int i=0; i< proposal.length-1;i++){
                    chosenProposals[i] = proposal[i].getValue() instanceof Proposal ? (Proposal) proposal[i].getValue() : null;
                }
                if(!manager.add(
                        studentCB.getValue(),
                        chosenProposals[0],
                        chosenProposals[1],
                        chosenProposals[2],
                        chosenProposals[3],
                        chosenProposals[4],
                        chosenProposals[5]
                )) {
                    actionEvent.consume();
                    ToastMessage.show(grid.getScene().getWindow(), "Incorrect parameters.");
                }
            }
        });
    }

    private void update() {
    }

    private boolean buttonsAreSelected(){

        if(studentCB.getValue() == null)
            return false;
        else if(proposal[1].getValue() != null && proposal[0].getValue() == null)
            return false;
        else if(proposal[2].getValue() != null &&
                (proposal[1].getValue() == null || proposal[0] == null))
            return false;
        else if(proposal[3].getValue() != null &&
                (proposal[2].getValue() ==null || proposal[1].getValue() == null || proposal[0] == null))
            return false;
        else if(proposal[4].getValue() != null &&
                (proposal[3].getValue() == null || proposal[2].getValue() == null || proposal[1].getValue() == null || proposal[0] == null))
            return false;
        else if(proposal[5].getValue() != null &&
                (proposal[4].getValue() == null || proposal[3].getValue() == null || proposal[2].getValue() == null || proposal[1].getValue() == null || proposal[0] == null))
            return false;
        return true;
    }
}