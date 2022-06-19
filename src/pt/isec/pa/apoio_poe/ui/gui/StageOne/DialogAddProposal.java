package pt.isec.pa.apoio_poe.ui.gui.StageOne;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import pt.isec.pa.apoio_poe.model.Manager;
import pt.isec.pa.apoio_poe.model.data.Student;
import pt.isec.pa.apoio_poe.model.data.Teacher;
import pt.isec.pa.apoio_poe.ui.gui.util.ToastMessage;

import java.util.ArrayList;
import java.util.List;

public class DialogAddProposal extends Dialog {
    private final Manager manager;
    TextField proposalID, proposalTitle, proposalHostingEntity;
    ChoiceBox proposalType, proposalAssignedStudent, proposalAssignedTeacher;
    GridPane grid;

    MenuButton btnProposalBranches;
//    CustomMenuItem cmniDA, cmniSI, cmniRAS;
    CustomMenuItem[] cmniBranches;
    CheckBox[] cbsBranches;

    String proposalTypeName;

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
        this.proposalTypeName = "Internship";


        grid.add(new Label("Proposal Type"), 0,0);
        grid.add(proposalType, 1,0);

        this.getDialogPane().setContent(grid);
        this.addInternship();
    }

    private void registerHandlers() {
        // Requests focus on the student number field by default
        //Platform.runLater(() -> teacherEmail.requestFocus());

        proposalType.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            this.proposalTypeName = (String) newValue;
            if (newValue.equals("Internship"))
                this.addInternship();
            else if(newValue.equals("Project"))
                this.addProject();
            else if(newValue.equals("Self Proposal"))
                this.addSelfProposal();
        });

        final Button btnApply = (Button) this.getDialogPane().lookupButton(ButtonType.APPLY);
        btnApply.addEventFilter(ActionEvent.ACTION, actionEvent -> {
            if (this.proposalTypeName.equalsIgnoreCase("Internship")) {
                Student studentAssigned = proposalAssignedStudent.getValue() instanceof Student ? (Student) proposalAssignedStudent.getValue() : null;
                List<String> branchesSelected = new ArrayList<>();
                for (CheckBox cb : cbsBranches) {
                    if (cb.isSelected()) {
                        branchesSelected.add(cb.getText());
                    }
                }
                if (!manager.add(
                        this.proposalTypeName,
                        proposalID.getText(),
                        proposalTitle.getText(),
                        studentAssigned,
                        branchesSelected,
                        proposalHostingEntity.getText()
                )) {
                    actionEvent.consume();
                    ToastMessage.show(grid.getScene().getWindow(), "Incorrect parameters.");
                }
            }
            else if(this.proposalTypeName.equalsIgnoreCase("Project")) {
                Student studentAssigned = proposalAssignedStudent.getValue() instanceof Student ? (Student) proposalAssignedStudent.getValue() : null;
                Teacher proposingTeacher = proposalAssignedTeacher.getValue() instanceof Teacher ? (Teacher) proposalAssignedTeacher.getValue() : null;
                List<String> branchesSelected = new ArrayList<>();
                for (CheckBox cb : cbsBranches) {
                    if (cb.isSelected()) {
                        branchesSelected.add(cb.getText());
                    }
                }
                if (!manager.add(
                        this.proposalTypeName,
                        proposalID.getText(),
                        proposalTitle.getText(),
                        studentAssigned,
                        branchesSelected,
                        proposingTeacher
                )) {
                    actionEvent.consume();
                    ToastMessage.show(grid.getScene().getWindow(), "Incorrect parameters.");
                }
            }
            else if(this.proposalTypeName.equalsIgnoreCase("Self Proposal")) {
                Student studentAssigned = proposalAssignedStudent.getValue() instanceof Student ? (Student) proposalAssignedStudent.getValue() : null;
                if (!manager.add(
                        this.proposalTypeName,
                        proposalID.getText(),
                        proposalTitle.getText(),
                        studentAssigned
                )) {
                    actionEvent.consume();
                    ToastMessage.show(grid.getScene().getWindow(), "Incorrect parameters.");
                }
            }
        });
    }

    private void update() {}

    private void removeChildNodes() {
        List<Node> nodesToRemove = new ArrayList<>();
        for(Node node : grid.getChildren())
            if (grid.getRowIndex(node) != 0)
                nodesToRemove.add(node);
        grid.getChildren().removeAll(nodesToRemove);
    }

    private void addSelfProposal() {
        this.removeChildNodes();
        proposalID = new TextField();
        proposalID.setPromptText("Proposal ID");
        proposalTitle = new TextField();
        proposalTitle.setPromptText("Title");

        proposalAssignedStudent = new ChoiceBox();
        List<Student> studentsList = manager.getStudentsWithoutProposal();
        if (studentsList.size() > 0) {
            proposalAssignedStudent.getItems().add("---");
            proposalAssignedStudent.getItems().addAll(studentsList);
        }
        else
            proposalAssignedStudent.getItems().addAll("---");
        proposalAssignedStudent.setValue("---");


        grid.add(new Label("Proposal ID"), 0,1);
        grid.add(proposalID, 1, 1);
        grid.add(new Label("Title"), 0,2);
        grid.add(proposalTitle, 1, 2);
        grid.add(new Label("Assigned Student"), 0,3);
        grid.add(proposalAssignedStudent, 1, 3);
    }

    private void addProject() {
        this.removeChildNodes();
        proposalID = new TextField();
        proposalID.setPromptText("Proposal ID");
        proposalTitle = new TextField();
        proposalTitle.setPromptText("Title");

        proposalAssignedStudent = new ChoiceBox();
        List<Student> studentsList = manager.getStudentsUnassigned();
        if (studentsList.size() > 0) {
            proposalAssignedStudent.getItems().add("None");
            proposalAssignedStudent.getItems().addAll(studentsList);
        }
        else
            proposalAssignedStudent.getItems().addAll("None");
        proposalAssignedStudent.setValue("None");

        btnProposalBranches = new MenuButton("Destined Branches");
        String[] branches = manager.getBranches();
        cbsBranches = new CheckBox[branches.length];
        cmniBranches = new CustomMenuItem[branches.length];
        int i = 0;
        for(String branchName : branches){
            cbsBranches[i] = new CheckBox(branchName);
            cmniBranches[i] = new CustomMenuItem(cbsBranches[i]);
            cmniBranches[i].setHideOnClick(false);
            i++;
        }
        btnProposalBranches.getItems().addAll(cmniBranches);

        proposalAssignedTeacher = new ChoiceBox();
        List<Teacher> teachersList = manager.getTeachers();
        if (teachersList.size() > 0) {
            proposalAssignedTeacher.getItems().add("---");
            proposalAssignedTeacher.getItems().addAll(teachersList);
        }
        else
            proposalAssignedTeacher.getItems().addAll("---");
        proposalAssignedTeacher.setValue("---");


        grid.add(new Label("Proposal ID"), 0,1);
        grid.add(proposalID, 1, 1);
        grid.add(new Label("Title"), 0,2);
        grid.add(proposalTitle, 1, 2);
        grid.add(new Label("Assigned Student"), 0,3);
        grid.add(proposalAssignedStudent, 1, 3);
        grid.add(new Label("Destined Branches"), 0,4);
        grid.add(btnProposalBranches, 1, 4);
        grid.add(new Label("Proposing Teacher"), 0,5);
        grid.add(proposalAssignedTeacher, 1, 5);
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
        List<Student> studentsList = manager.getStudentsForInternships();
        if (studentsList.size() > 0) {
            proposalAssignedStudent.getItems().add("None");
            proposalAssignedStudent.getItems().addAll(studentsList);
        }
        else
            proposalAssignedStudent.getItems().addAll("None");
        proposalAssignedStudent.setValue("None");

        btnProposalBranches = new MenuButton("Destined Branches");
        String[] branches = manager.getBranches();
        cbsBranches = new CheckBox[branches.length];
        cmniBranches = new CustomMenuItem[branches.length];
        int i = 0;
        for(String branchName : branches){
            cbsBranches[i] = new CheckBox(branchName);
            cmniBranches[i] = new CustomMenuItem(cbsBranches[i]);
            cmniBranches[i].setHideOnClick(false);
            i++;
        }
        btnProposalBranches.getItems().addAll(cmniBranches);


        grid.add(new Label("Proposal ID"), 0,1);
        grid.add(proposalID, 1, 1);
        grid.add(new Label("Title"), 0,2);
        grid.add(proposalTitle, 1, 2);
        grid.add(new Label("Hosting Entity"), 0,3);
        grid.add(proposalHostingEntity, 1, 3);
        grid.add(new Label("Assigned Student"), 0,4);
        grid.add(proposalAssignedStudent, 1, 4);
        grid.add(new Label("Destined Branches"), 0,5);
        grid.add(btnProposalBranches, 1, 5);
    }
}
