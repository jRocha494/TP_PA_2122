package pt.isec.pa.apoio_poe.ui.gui.StageFour;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import pt.isec.pa.apoio_poe.model.Manager;
import pt.isec.pa.apoio_poe.model.fsm.ListingType;
import pt.isec.pa.apoio_poe.ui.gui.StageTwo.DialogAddApplication;
import pt.isec.pa.apoio_poe.ui.gui.util.ToastMessage;

import java.util.Arrays;

public class StageFourToolBarUI extends ToolBar {
    private final Manager manager;
    //private final BorderPane root;
    private Button btnClose, btnExit, btnReturn, btnExportData, btnAssignAdvisors,
            btnListAssignments, btnListAssignStudentsWithAdvisor, btnListAssignStudentsWithoutAdvisor;

    public StageFourToolBarUI(Manager manager/*, BorderPane root*/) {
        this.manager = manager;
        //this.root = root;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        btnClose = new Button("Close Stage");
        btnReturn = new Button("Previous Stage");
        btnExportData = new Button("Export Data");
        btnAssignAdvisors = new Button("Automatic assign teachers");
        btnExit = new Button("Quit");
        btnListAssignments = new Button("List Assignments");
        btnListAssignStudentsWithAdvisor = new Button("View students assigned to proposals with advisors");
        btnListAssignStudentsWithoutAdvisor = new Button("View students assigned to proposals without advisors");
        btnListAssignments = new Button("List Assignments");

        this.setBackground(new Background(new BackgroundFill(Color.TURQUOISE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.getItems().addAll(btnClose, btnReturn, btnAssignAdvisors, btnListAssignments,
                btnListAssignStudentsWithAdvisor, btnListAssignStudentsWithoutAdvisor, btnExportData, btnExit);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(Manager.STATE, evt -> update());

        btnClose.setOnAction(actionEvent -> manager.closeStage());
        btnReturn.setOnAction(actionEvent -> manager.returnStage());
        btnExit.setOnAction(actionEvent -> Platform.exit());

        btnExportData.setOnAction(actionEvent -> {
            TextField filename = new TextField();

            GridPane gridPane = new GridPane();
            gridPane.add(new Label("File name:"), 0, 0);
            gridPane.add(filename, 1, 0);

            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);
            alert.setHeaderText("Export Data");
            alert.getDialogPane().setContent(gridPane);

            final Button btnApply = (Button) alert.getDialogPane().lookupButton(ButtonType.APPLY);
            btnApply.addEventFilter(ActionEvent.ACTION, event -> {
                if(filename.getText().isEmpty() || !manager.boolExportCSV(filename.getText())){
                    event.consume();
                    ToastMessage.show(gridPane.getScene().getWindow(), "Something went wrong");
                }
            });

            alert.showAndWait();
        });

        btnAssignAdvisors.setOnAction(actionEvent -> manager.automaticAssignmentAdvisors());
        btnListAssignments.setOnAction(actionEvent -> manager.setListingType(ListingType.ASSIGNMENTS));
        btnListAssignStudentsWithAdvisor.setOnAction(actionEvent -> manager.setListingType(ListingType.STUDENTS_ASSIGNED_WITH_ADVISOR));
        btnListAssignStudentsWithoutAdvisor.setOnAction(actionEvent -> manager.setListingType(ListingType.STUDENTS_ASSIGNED_WITHOUT_ADVISOR));
    }

    private void update() {
    }
}
