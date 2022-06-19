package pt.isec.pa.apoio_poe.ui.gui.StageOne;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import pt.isec.pa.apoio_poe.model.Manager;
import pt.isec.pa.apoio_poe.model.data.Teacher;
import pt.isec.pa.apoio_poe.model.fsm.AppState;
import pt.isec.pa.apoio_poe.ui.gui.util.ToastMessage;

public class DialogTeacher extends Dialog {
    private final Manager manager;
    private Teacher selectedTeacher;
    ButtonType btnEdit, btnDelete;
    GridPane grid;
    TextField teacherEmail, teacherName;

    public DialogTeacher(Manager manager, Object selectedTeacher) {
        this.manager = manager;
        this.selectedTeacher = (Teacher) selectedTeacher;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setTitle("Edit Teacher");
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

        teacherEmail = new TextField(selectedTeacher.getEmail());
        teacherEmail.setPromptText("Email");
        teacherEmail.setEditable(false);
        teacherName = new TextField(selectedTeacher.getName());
        teacherName.setPromptText("Name");

        grid.add(new Label("Email"), 0,0);
        grid.add(teacherEmail, 1, 0);
        grid.add(new Label("Name"), 0,1);
        grid.add(teacherName, 1, 1);

        this.getDialogPane().setContent(grid);
    }

    private void registerHandlers() {
        // Requests focus on the student number field by default
        Platform.runLater(() -> teacherName.requestFocus());

        this.getDialogPane().lookupButton(btnDelete).addEventFilter(ActionEvent.ACTION, actionEvent -> {
            manager.delete(selectedTeacher);
        });

        this.getDialogPane().lookupButton(btnEdit).addEventFilter(ActionEvent.ACTION, actionEvent -> {
            if(!manager.update(
                    teacherEmail.getText(),
                    teacherName.getText()
            )){
                actionEvent.consume();
                ToastMessage.show(grid.getScene().getWindow(), "Incorrect parameters.");
            }
        });
    }

    private void update() {
    }
}
