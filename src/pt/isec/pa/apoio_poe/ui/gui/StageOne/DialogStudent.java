package pt.isec.pa.apoio_poe.ui.gui.StageOne;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import pt.isec.pa.apoio_poe.model.Manager;
import pt.isec.pa.apoio_poe.model.data.Student;
import pt.isec.pa.apoio_poe.model.fsm.AppState;
import pt.isec.pa.apoio_poe.ui.gui.util.ToastMessage;

public class DialogStudent extends Dialog {
    private final Manager manager;
    private Student selectedStudent;
    TextField studentNumber, studentName, studentEmail, studentClassification;
    ChoiceBox studentCourse, studentBranch, studentInternshipAccess;
    //Dialog dialog;
    GridPane grid;
    ButtonType btnEdit, btnDelete;


    public DialogStudent(Manager manager, Object selectedStudent) {
        this.manager = manager;
        this.selectedStudent = (Student) selectedStudent;
        createViews();
        registerHandlers();
        update();
    }


    private void createViews() {
//        dialog = new Dialog();
        this.setTitle("Edit Student");
//            dialog.setHeaderText("Insert Student data");
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

        studentNumber = new TextField(String.valueOf(selectedStudent.getStudentNumber()));
        studentNumber.setPromptText("Student Number");
        studentNumber.setEditable(false);
        studentName = new TextField(selectedStudent.getName());
        studentName.setPromptText("Name");
        studentEmail = new TextField(selectedStudent.getEmail());
        studentEmail.setPromptText("Email");
        studentCourse = new ChoiceBox();
        studentCourse.setValue(selectedStudent.getCourse());
        studentCourse.getItems().addAll("LEI", "LEI-PL");
        studentBranch = new ChoiceBox();
        studentBranch.setValue(selectedStudent.getBranch());
        studentBranch.getItems().addAll("DA", "RAS", "SI");
        studentClassification = new TextField(String.valueOf(selectedStudent.getClassification()));
        studentClassification.setPromptText("Classification");
        studentInternshipAccess = new ChoiceBox();
        studentInternshipAccess.setValue(selectedStudent.hasInternshipAccess() ? "Yes" : "No");
        studentInternshipAccess.getItems().addAll("Yes", "No");

        grid.add(new Label("Student Number"), 0,0);
        grid.add(studentNumber, 1, 0);
        grid.add(new Label("Name"), 0,1);
        grid.add(studentName, 1, 1);
        grid.add(new Label("Email"), 0,2);
        grid.add(studentEmail, 1, 2);
        grid.add(new Label("Course"), 0,3);
        grid.add(studentCourse, 1, 3);
        grid.add(new Label("Branch"), 0,4);
        grid.add(studentBranch, 1, 4);
        grid.add(new Label("Classification"), 0,5);
        grid.add(studentClassification, 1, 5);
        grid.add(new Label("Internship Access"), 0,6);
        grid.add(studentInternshipAccess, 1, 6);

        this.getDialogPane().setContent(grid);
    }

    private void registerHandlers() {
        // Requests focus on the student number field by default
        Platform.runLater(() -> studentName.requestFocus());

        if (this.getDialogPane().lookupButton(btnDelete) != null)
            this.getDialogPane().lookupButton(btnDelete).addEventFilter(ActionEvent.ACTION, actionEvent -> {
                manager.delete(selectedStudent);
            });

        if (this.getDialogPane().lookupButton(btnEdit) != null)
            this.getDialogPane().lookupButton(btnEdit).addEventFilter(ActionEvent.ACTION, actionEvent -> {
            System.out.println("studentInternshipAccess: " + studentInternshipAccess.getValue());
            if(!manager.update(
                    studentNumber.getText(),
                    studentName.getText(),
                    studentEmail.getText(),
                    studentCourse.getValue(),
                    studentBranch.getValue(),
                    studentClassification.getText(),
                    studentInternshipAccess.getValue()
            )){
                actionEvent.consume();
                ToastMessage.show(grid.getScene().getWindow(), "Incorrect parameters.");
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
}
