package pt.isec.pa.apoio_poe.ui.gui.StageTwo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import pt.isec.pa.apoio_poe.model.Manager;
import pt.isec.pa.apoio_poe.model.data.Application;
import pt.isec.pa.apoio_poe.model.data.Proposal;
import pt.isec.pa.apoio_poe.model.data.Student;
import pt.isec.pa.apoio_poe.model.fsm.AppState;
import pt.isec.pa.apoio_poe.ui.gui.util.ToastMessage;

import java.util.List;

public class DialogApplication extends Dialog {
    private final Manager manager;
    private Application selectedApplication;

    ChoiceBox applicationStudent;
    ChoiceBox[] applicationProposals;
    GridPane grid;
    ButtonType btnEdit, btnDelete;


    public DialogApplication(Manager manager, Object selectedApplication) {
        this.manager = manager;
        this.selectedApplication = (Application) selectedApplication;
        createViews();
        registerHandlers();
        update();
    }


    private void createViews() {
        this.setTitle("Edit Application");
        btnEdit = new ButtonType("Edit");
        btnDelete = new ButtonType("Delete");
        if (manager.isStageClosed("Stage2")) {
            this.getDialogPane().getButtonTypes().addAll(btnEdit, btnDelete, ButtonType.CANCEL);
        } else
            this.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);

        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        applicationStudent = new ChoiceBox();
        List<Student> studentsList = manager.getAvailableStudentsWithoutApplication();
        if (studentsList.size() > 0){
            applicationStudent.getItems().add("---");
            applicationStudent.getItems().addAll(studentsList);
        }
        else
            applicationStudent.getItems().add("---");
        Student s = selectedApplication.getStudent();
        if (s!=null)
            applicationStudent.setValue(s);
        else
            applicationStudent.setValue("---");

        applicationProposals = new ChoiceBox[6];
        List<Proposal> proposalsList = manager.getAvailableProposalsList();
        for(int i=0; i<applicationProposals.length-1; i++){
            if(proposalsList.size() > 0){
                applicationProposals[i].getItems().add("None");
                applicationProposals[i].getItems().addAll(proposalsList);
            }else
                applicationProposals[i].getItems().add("None");
            Proposal p = selectedApplication.getChosenProposals().get(i);
            if (p!=null)
                applicationProposals[i].setValue(p);
            else
                applicationProposals[i].setValue("None");
        }

        grid.add(new Label("Student"), 0,0);
        grid.add(applicationStudent, 1, 0);
        grid.add(new Label("1st Choice"), 0,1);
        grid.add(applicationProposals[0], 1, 1);
        grid.add(new Label("2nd Choice"), 2,1);
        grid.add(applicationProposals[1], 3, 1);
        grid.add(new Label("3rd Choice"), 0,2);
        grid.add(applicationProposals[2], 1, 2);
        grid.add(new Label("4th Choice"), 2,2);
        grid.add(applicationProposals[3], 3, 2);
        grid.add(new Label("5th Choice"), 0,3);
        grid.add(applicationProposals[4], 1, 3);
        grid.add(new Label("6th Choice"), 2,3);
        grid.add(applicationProposals[5], 3, 3);

        this.getDialogPane().setContent(grid);
    }

    private void registerHandlers() {
        // Requests focus on the student number field by default
        Platform.runLater(() -> applicationStudent.requestFocus());

        this.getDialogPane().lookupButton(btnDelete).addEventFilter(ActionEvent.ACTION, actionEvent -> {
            manager.delete(selectedApplication);
        });

        this.getDialogPane().lookupButton(btnEdit).addEventFilter(ActionEvent.ACTION, actionEvent -> {
            if(!buttonsAreSelected()) {
                actionEvent.consume();
                ToastMessage.show(grid.getScene().getWindow(), "Some buttons are not correctly selected.");
            } else {
                Proposal[] chosenProposals = new Proposal[6];
                for (int i = 0; i < applicationProposals.length - 1; i++) {
                    chosenProposals[i] = applicationProposals[i].getValue() instanceof Proposal ? (Proposal) applicationProposals[i].getValue() : null;
                }
                if (!manager.update(
                        applicationStudent.getValue(),
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
//        System.out.println(manager.getState());
//        switch (manager.getState()) {
//            case CONFIGURATIONS_STATE_STAGE_ONE -> {
//                System.out.println("NO CASE DIALOG STUDENT");
//                this.getDialogPane().getButtonTypes().removeAll();
//            }
//        }
    }

    private boolean buttonsAreSelected(){
        if(applicationStudent.getValue() == null)
            return false;
        else if(applicationProposals[1].getValue() != null && applicationProposals[0].getValue() == null)
            return false;
        else if(applicationProposals[2].getValue() != null &&
                (applicationProposals[1].getValue() == null || applicationProposals[0] == null))
            return false;
        else if(applicationProposals[3].getValue() != null &&
                (applicationProposals[2].getValue() ==null || applicationProposals[1].getValue() == null || applicationProposals[0] == null))
            return false;
        else if(applicationProposals[4].getValue() != null &&
                (applicationProposals[3].getValue() == null || applicationProposals[2].getValue() == null || applicationProposals[1].getValue() == null || applicationProposals[0] == null))
            return false;
        else if(applicationProposals[5].getValue() != null &&
                (applicationProposals[4].getValue() == null || applicationProposals[3].getValue() == null || applicationProposals[2].getValue() == null || applicationProposals[1].getValue() == null || applicationProposals[0] == null))
            return false;
        return true;
    }
}
