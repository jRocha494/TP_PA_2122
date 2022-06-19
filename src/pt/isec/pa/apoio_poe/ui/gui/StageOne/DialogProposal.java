package pt.isec.pa.apoio_poe.ui.gui.StageOne;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import pt.isec.pa.apoio_poe.model.Manager;
import pt.isec.pa.apoio_poe.model.data.Proposal;
import pt.isec.pa.apoio_poe.model.data.Student;
import pt.isec.pa.apoio_poe.model.data.Teacher;
import pt.isec.pa.apoio_poe.model.data.tiposProposta.Internship;
import pt.isec.pa.apoio_poe.model.data.tiposProposta.Project;
import pt.isec.pa.apoio_poe.model.data.tiposProposta.SelfProposal;
import pt.isec.pa.apoio_poe.model.fsm.AppState;
import pt.isec.pa.apoio_poe.ui.gui.util.ToastMessage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DialogProposal extends Dialog {
    private final Manager manager;
    private Proposal selectedProposal;
    ButtonType btnEdit, btnDelete;
    GridPane grid;
    TextField proposalID, proposalTitle, proposalHostingEntity;
    ChoiceBox proposalAssignedStudent, proposalAssignedTeacher;
    MenuButton btnProposalBranches;
    CheckBox[] cbsBranches;
    CustomMenuItem[] cmniBranches;

    public DialogProposal(Manager manager, Object selectedProposal) {
        this.manager = manager;
        this.selectedProposal = (Proposal) selectedProposal;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setTitle(String.format("Edit %s", selectedProposal.getClass().getSimpleName()));
        btnEdit = new ButtonType("Edit");
        btnDelete = new ButtonType("Delete");
        if (manager.getState() != AppState.CONFIGURATIONS_STATE_STAGE_ONE) {
            this.getDialogPane().getButtonTypes().addAll(btnEdit, btnDelete, ButtonType.CANCEL);
        } else
            this.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);

        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        System.out.println(selectedProposal.getClass());
        if (this.selectedProposal instanceof Internship){
            System.out.println("INTERNSHIP");
            proposalID = new TextField(selectedProposal.getId());
            proposalID.setPromptText("Proposal ID");
            proposalID.setEditable(false);
            proposalTitle = new TextField(selectedProposal.getTitle());
            proposalTitle.setPromptText("Title");
            proposalHostingEntity = new TextField(((Internship) selectedProposal).getHostingEntity());
            proposalHostingEntity.setPromptText("Hosting Entity");
            proposalAssignedStudent = new ChoiceBox();
            List<Student> studentsList = manager.getStudentsForInternships();
            if (studentsList.size() > 0) {
                proposalAssignedStudent.getItems().add("None");
                proposalAssignedStudent.getItems().addAll(studentsList);
            }
            else
                proposalAssignedStudent.getItems().addAll("None");
            if (selectedProposal.getAssignedStudent() != null) {
                proposalAssignedStudent.getItems().addAll(selectedProposal.getAssignedStudent());
                proposalAssignedStudent.setValue(selectedProposal.getAssignedStudent());
            }
            else
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
                System.out.println("BRANCHES: " + ((Internship) selectedProposal).getDestinedBranch());
                if (((Internship) selectedProposal).getDestinedBranch().contains(branchName))
                    cbsBranches[i].setSelected(true);
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

        } else if (this.selectedProposal instanceof Project){
            proposalID = new TextField(selectedProposal.getId());
            proposalID.setPromptText("Proposal ID");
            proposalID.setEditable(false);
            proposalTitle = new TextField(selectedProposal.getTitle());
            proposalTitle.setPromptText("Title");

            proposalAssignedStudent = new ChoiceBox();
            List<Student> studentsList = manager.getStudentsUnassigned();
            if (studentsList.size() > 0) {
                proposalAssignedStudent.getItems().add("None");
                proposalAssignedStudent.getItems().addAll(studentsList);
            }
            else
                proposalAssignedStudent.getItems().addAll("None");
            if (selectedProposal.getAssignedStudent() != null) {
                proposalAssignedStudent.getItems().addAll(selectedProposal.getAssignedStudent());
                proposalAssignedStudent.setValue(selectedProposal.getAssignedStudent());
            }
            else
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
                if (((Project) selectedProposal).getDestinedBranch().contains(branchName))
                    cbsBranches[i].setSelected(true);
                i++;
            }
            btnProposalBranches.getItems().addAll(cmniBranches);

            proposalAssignedTeacher = new ChoiceBox();
            List<Teacher> teachersList = manager.getTeachers();
            if (teachersList.size() > 0) {
                proposalAssignedTeacher.getItems().addAll(teachersList);
            }
            else
                proposalAssignedTeacher.getItems().addAll("---");
            if (((Project) selectedProposal).getProposingTeacher() != null) {
                proposalAssignedTeacher.setValue(((Project) selectedProposal).getProposingTeacher());
            }
            else
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

        } else if (this.selectedProposal instanceof SelfProposal){
            proposalID = new TextField(selectedProposal.getId());
            proposalID.setPromptText("Proposal ID");
            proposalID.setEditable(false);
            proposalTitle = new TextField(selectedProposal.getTitle());
            proposalTitle.setPromptText("Title");

            proposalAssignedStudent = new ChoiceBox();
            List<Student> studentsList = manager.getStudentsWithoutProposal();
            if (studentsList.size() > 0) {
                proposalAssignedStudent.getItems().addAll(studentsList);
            }
            else
                proposalAssignedStudent.getItems().addAll("---");
            if (selectedProposal.getAssignedStudent() != null) {
                proposalAssignedStudent.getItems().addAll(selectedProposal.getAssignedStudent());
                proposalAssignedStudent.setValue(selectedProposal.getAssignedStudent());
            }
            else
                proposalAssignedStudent.setValue("---");


            grid.add(new Label("Proposal ID"), 0,1);
            grid.add(proposalID, 1, 1);
            grid.add(new Label("Title"), 0,2);
            grid.add(proposalTitle, 1, 2);
            grid.add(new Label("Assigned Student"), 0,3);
            grid.add(proposalAssignedStudent, 1, 3);
        }
        this.getDialogPane().setContent(grid);
    }

    private void registerHandlers() {
        // Requests focus on the student number field by default
        Platform.runLater(() -> proposalTitle.requestFocus());

        this.getDialogPane().lookupButton(btnDelete).addEventFilter(ActionEvent.ACTION, actionEvent -> {
            manager.delete(selectedProposal);
        });

        this.getDialogPane().lookupButton(btnEdit).addEventFilter(ActionEvent.ACTION, actionEvent -> {
            if (this.selectedProposal instanceof Internship) {
                Student studentAssigned = proposalAssignedStudent.getValue() instanceof Student ? (Student) proposalAssignedStudent.getValue() : null;
                List<String> branchesSelected = new ArrayList<>();
                for (CheckBox cb : cbsBranches) {
                    if (cb.isSelected()) {
                        branchesSelected.add(cb.getText());
                    }
                }
                if(!manager.update(
                        "Internship",
                        proposalID.getText(),
                        proposalTitle.getText(),
                        studentAssigned,
                        branchesSelected,
                        proposalHostingEntity.getText()
                )){
                    actionEvent.consume();
                    ToastMessage.show(grid.getScene().getWindow(), "Incorrect parameters.");
                }
            } else if (this.selectedProposal instanceof Project){
                Student studentAssigned = proposalAssignedStudent.getValue() instanceof Student ? (Student) proposalAssignedStudent.getValue() : null;
                Teacher proposingTeacher = proposalAssignedTeacher.getValue() instanceof Teacher ? (Teacher) proposalAssignedTeacher.getValue() : null;
                List<String> branchesSelected = new ArrayList<>();
                for (CheckBox cb : cbsBranches) {
                    if (cb.isSelected()) {
                        branchesSelected.add(cb.getText());
                    }
                }
                if(!manager.update(
                        "Project",
                        proposalID.getText(),
                        proposalTitle.getText(),
                        studentAssigned,
                        branchesSelected,
                        proposingTeacher
                )){
                    actionEvent.consume();
                    ToastMessage.show(grid.getScene().getWindow(), "Incorrect parameters.");
                }

            } else if (this.selectedProposal instanceof SelfProposal){
                Student studentAssigned = proposalAssignedStudent.getValue() instanceof Student ? (Student) proposalAssignedStudent.getValue() : null;
                if (!manager.add(
                        "Self Proposal",
                        proposalID.getText(),
                        proposalTitle.getText(),
                        studentAssigned
                )) {
                    actionEvent.consume();
                    ToastMessage.show(grid.getScene().getWindow(), "Incorrect parameters.");
                }
            }

//                if(!manager.update(
//                    teacherEmail.getText(),
//                    teacherName.getText()
//            )){
//                actionEvent.consume();
//                ToastMessage.show(grid.getScene().getWindow(), "Incorrect parameters.");
//            }
        });
    }

    private void update() {
    }
}
