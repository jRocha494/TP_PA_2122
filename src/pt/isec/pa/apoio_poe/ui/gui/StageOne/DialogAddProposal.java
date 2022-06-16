package pt.isec.pa.apoio_poe.ui.gui.StageOne;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import pt.isec.pa.apoio_poe.model.Manager;
import pt.isec.pa.apoio_poe.ui.gui.util.ToastMessage;

public class DialogAddProposal extends Dialog {
    private final Manager manager;
    TextField proposalID, proposalTitle, proposalHostingEntity;
    ChoiceBox proposalType, proposalAssignedStudent;
    GridPane grid;

    public DialogAddProposal(Manager manager) {
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setTitle("Add a new Proposal");
        this.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);

        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        grid.setPrefHeight(300);

        proposalType = new ChoiceBox();
        proposalType.getItems().addAll("Internship", "Project", "Self Proposal");
        proposalType.setValue("Internship");

        grid.add(new Label("Proposal Type"), 0,0);
        grid.add(proposalType, 1,0);

        this.getDialogPane().setContent(grid);
    }

    private void registerHandlers() {
        // Requests focus on the student number field by default
        //Platform.runLater(() -> teacherEmail.requestFocus());

        proposalType.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.equals("Internship"))
                this.addInternship();
            else if(newValue.equals("Project"))
                this.addProject();
            else if(newValue.equals("Self Proposal"))
                this.addSelfProposal();
        });
    }

    private void removeChildNodes() {
        for(Node node : grid.getChildren()){
            if(grid.getRowIndex(node) != 0)
                grid.getChildren().remove(node);
        }
    }

    private void addSelfProposal() {
        this.removeChildNodes();
    }

    private void addProject() {
        this.removeChildNodes();

    }

    private void addInternship() {
        this.removeChildNodes();
        proposalID = new TextField();
        proposalID.setPromptText("Proposal ID");
        proposalTitle = new TextField();
        proposalTitle.setPromptText("Title");
        proposalHostingEntity = new TextField();
        proposalHostingEntity.setPromptText("Hosting Entity");
        proposalAssignedStudent = new ChoiceBox();
        proposalAssignedStudent.getItems().addAll(manager.getStudents());


        grid.add(new Label("Proposal ID"), 0,1);
        grid.add(proposalID, 1, 1);
        grid.add(new Label("Title"), 0,2);
        grid.add(proposalTitle, 1, 2);
        grid.add(new Label("Hosting Entity"), 0,3);
        grid.add(proposalHostingEntity, 1, 3);
        grid.add(new Label("Assigned Student"), 0,4);
        grid.add(proposalAssignedStudent, 1, 4);
    }

    private void update() {
    }
}
