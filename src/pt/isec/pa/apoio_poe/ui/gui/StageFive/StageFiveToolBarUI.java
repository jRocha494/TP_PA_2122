package pt.isec.pa.apoio_poe.ui.gui.StageFive;

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

public class StageFiveToolBarUI extends ToolBar {
    private final Manager manager;
    Button btnExit, btnExportData;
    MenuButton btnListStudents, btnListProposals;
    MenuItem mniStudentsAssigned, mniStudentsUnassignedWithApplication, mniProposalsAvailable, mniProposalsAssigned;

    public StageFiveToolBarUI(Manager manager) {
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        btnListStudents = new MenuButton("List Students");
        mniStudentsAssigned = new MenuItem("Students Assigned");
        mniStudentsUnassignedWithApplication = new MenuItem("Students Unassigned with Applications");
        btnListStudents.getItems().addAll(mniStudentsAssigned, mniStudentsUnassignedWithApplication);

        btnListProposals = new MenuButton("List Proposals");
        mniProposalsAvailable = new MenuItem("Proposals Available");
        mniProposalsAssigned = new MenuItem("Proposals Assigned");
        btnListProposals.getItems().addAll(mniProposalsAvailable, mniProposalsAssigned);

        btnExportData = new Button("Export Data");
        btnExit = new Button("Quit");

        this.setBackground(new Background(new BackgroundFill(Color.TURQUOISE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.getItems().addAll(btnListStudents, btnListProposals, btnExportData, btnExit);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(Manager.STATE, evt -> update());
        manager.addPropertyChangeListener(Manager.DATA, evt -> update());

        mniStudentsAssigned.setOnAction(actionEvent -> manager.setListingType(ListingType.STUDENTS_ASSIGNED_STAGE_FIVE));
        mniStudentsUnassignedWithApplication.setOnAction(actionEvent -> manager.setListingType(ListingType.STUDENTS_UNASSIGNED_WITH_APPLICATION));

        mniProposalsAvailable.setOnAction(actionEvent -> manager.setListingType(ListingType.PROPOSALS_AVAILABLE));
        mniProposalsAssigned.setOnAction(actionEvent -> manager.setListingType(ListingType.PROPOSALS_ASSIGNED));

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
    }

    private void update() {
    }
}
