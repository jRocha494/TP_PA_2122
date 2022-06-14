package pt.isec.pa.apoio_poe.ui.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import pt.isec.pa.apoio_poe.model.Manager;
import pt.isec.pa.apoio_poe.ui.gui.util.ToastMessage;

public class DialogAddStudent extends Dialog{
    private final Manager manager;
    TextField studentNumber, studentName, studentEmail, studentClassification;
    ChoiceBox studentCourse, studentBranch, studentInternshipAccess;
    //Dialog dialog;
    GridPane grid;

    public DialogAddStudent(Manager manager) {
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
//        dialog = new Dialog();
        this.setTitle("Add a new Student");
//            dialog.setHeaderText("Insert Student data");
        //ButtonType btApply = new ButtonType("Apply");
        this.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);

        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        studentNumber = new TextField();
        studentNumber.setPromptText("Student Number");
        studentName = new TextField();
        studentName.setPromptText("Name");
        studentEmail = new TextField();
        studentEmail.setPromptText("Email");
        studentCourse = new ChoiceBox();
        studentCourse.getItems().addAll("LEI", "LEI-PL");
        studentBranch = new ChoiceBox();
        studentBranch.getItems().addAll("DA", "RAS", "SI");
        studentClassification = new TextField();
        studentClassification.setPromptText("Classification");
        studentInternshipAccess = new ChoiceBox();
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
        Platform.runLater(() -> studentNumber.requestFocus());

        final Button btnApply = (Button) this.getDialogPane().lookupButton(ButtonType.APPLY);
        btnApply.addEventFilter(ActionEvent.ACTION, event -> {
            if(!manager.add(
                    studentNumber.getText(),
                    studentName.getText(),
                    studentEmail.getText(),
                    (String) studentCourse.getValue(),
                    (String) studentBranch.getValue(),
                    studentClassification.getText(),
                    (String) studentInternshipAccess.getValue()
            )) {
                event.consume();
                ToastMessage.show(grid.getScene().getWindow(), "Incorrect parameters.");
            }
        });
    }

    private void update() {
    }
}
