package pt.isec.pa.apoio_poe.ui.gui.StageOne;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import pt.isec.pa.apoio_poe.model.Manager;
import pt.isec.pa.apoio_poe.ui.gui.util.ToastMessage;

public class DialogAddTeacher extends Dialog {
    private final Manager manager;
    TextField teacherEmail, teacherName;
    GridPane grid;

    public DialogAddTeacher(Manager manager) {
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setTitle("Add a new Teacher");
        this.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);

        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        teacherEmail = new TextField();
        teacherEmail.setPromptText("Email");
        teacherName = new TextField();
        teacherName.setPromptText("Name");

        grid.add(new Label("Email"), 0,0);
        grid.add(teacherEmail, 1, 0);
        grid.add(new Label("Name"), 0,1);
        grid.add(teacherName, 1, 1);

        this.getDialogPane().setContent(grid);
    }

    private void registerHandlers() {
        // Requests focus on the teacher email field by default
        Platform.runLater(() -> teacherEmail.requestFocus());

        final Button btnApply = (Button) this.getDialogPane().lookupButton(ButtonType.APPLY);
        btnApply.addEventFilter(ActionEvent.ACTION, event -> {
            if(!manager.add(
                    teacherEmail.getText(),
                    teacherName.getText()
            )) {
                event.consume();
                ToastMessage.show(grid.getScene().getWindow(), "Incorrect parameters.");
            }
        });
    }

    private void update() {
    }
}
