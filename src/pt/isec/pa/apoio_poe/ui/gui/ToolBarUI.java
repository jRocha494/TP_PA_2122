package pt.isec.pa.apoio_poe.ui.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pa.apoio_poe.model.Manager;
import pt.isec.pa.apoio_poe.model.fsm.AppState;
import pt.isec.pa.apoio_poe.model.fsm.ListingType;
import pt.isec.pa.apoio_poe.ui.gui.StageOne.DialogAddProposal;
import pt.isec.pa.apoio_poe.ui.gui.StageOne.DialogAddStudent;
import pt.isec.pa.apoio_poe.ui.gui.StageOne.DialogAddTeacher;
import pt.isec.pa.apoio_poe.ui.gui.util.ToastMessage;

public class ToolBarUI extends ToolBar {
    private final Manager manager;
    //private final BorderPane root;
    private Button btnClose, btnAdvance, btnExit, btnListStudents, btnListTeachers, btnListProposals, btnImportData, btnExportData, btnAdd, btnReturn;
    MenuButton btnChangeMode;
    MenuItem mniStudent, mniTeacher, mniProposal;

    public ToolBarUI(Manager manager/*, BorderPane root*/) {
        this.manager = manager;
        //this.root = root;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        btnClose = new Button("Close Stage");
        btnAdvance = new Button("Next Stage");
        btnListStudents = new Button("List Students");
        btnListTeachers = new Button("List Teachers");
        btnListProposals = new Button("List Proposals");
        btnImportData = new Button("Import Data");
        btnExportData = new Button("Export Data");
        btnAdd = new Button("Add Data");
        btnExit = new Button("Quit");
        btnReturn = new Button("Return");

        btnChangeMode = new MenuButton("Change Mode");
        mniStudent = new MenuItem("Students");
        mniTeacher = new MenuItem("Teachers");
        mniProposal = new MenuItem("Proposals");
        btnChangeMode.getItems().addAll(mniStudent, mniTeacher, mniProposal);

        this.setBackground(new Background(new BackgroundFill(Color.TURQUOISE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.getItems().addAll(btnAdd, btnClose, btnAdvance, btnListStudents, btnListTeachers, btnListProposals, btnChangeMode, btnImportData, btnExportData, btnReturn, btnExit);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(Manager.STATE, evt -> update());

        btnClose.setOnAction(actionEvent -> manager.closeStage());
        btnAdvance.setOnAction(actionEvent -> manager.advanceStage());
        btnExit.setOnAction(actionEvent -> Platform.exit());

        btnImportData.setOnAction(actionEvent -> {
            TextField filename = new TextField();

            GridPane gridPane = new GridPane();
            gridPane.add(new Label("File name:"), 0, 0);
            gridPane.add(filename, 1, 0);

            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);
            alert.setHeaderText("Import Data");
            alert.getDialogPane().setContent(gridPane);

            final Button btnApply = (Button) alert.getDialogPane().lookupButton(ButtonType.APPLY);
            btnApply.addEventFilter(ActionEvent.ACTION, event -> {
                if(!manager.boolImportCSV(filename.getText())){
                    event.consume();
                    ToastMessage.show(gridPane.getScene().getWindow(), "Something went wrong");
                }
            });

            alert.showAndWait();
        });

        btnListStudents.setOnAction(actionEvent -> {
            manager.setListingType(ListingType.STUDENTS);
        });
        btnListTeachers.setOnAction(actionEvent -> {
            manager.setListingType(ListingType.TEACHERS);
        });
        btnListProposals.setOnAction(actionEvent -> {
            manager.setListingType(ListingType.PROPOSALS);
        });
        mniStudent.setOnAction(actionEvent -> {
            manager.changeConfigurationMode(AppState.CONFIGURATIONS_STATE_STUDENT_MANAGER.ordinal());
            manager.setListingType(ListingType.NONE);
        });
        mniTeacher.setOnAction(actionEvent -> {
            manager.changeConfigurationMode(AppState.CONFIGURATIONS_STATE_TEACHER_MANAGER.ordinal());
            manager.setListingType(ListingType.NONE);
        });
        mniProposal.setOnAction(actionEvent -> {
            manager.changeConfigurationMode(AppState.CONFIGURATIONS_STATE_PROPOSAL_MANAGER.ordinal());
            manager.setListingType(ListingType.NONE);
        });

        btnReturn.setOnAction(actionEvent -> manager.returnStage());

        btnAdd.setOnAction(actionEvent -> {
            switch (manager.getState()){
                case CONFIGURATIONS_STATE_STUDENT_MANAGER -> new DialogAddStudent(manager).showAndWait();
                case CONFIGURATIONS_STATE_TEACHER_MANAGER -> new DialogAddTeacher(manager).showAndWait();
                case CONFIGURATIONS_STATE_PROPOSAL_MANAGER -> new DialogAddProposal(manager).showAndWait();
            }
        });

    }

    private void update() {
        //btnAdd.setDisable(manager.getState() == AppState.CONFIGURATIONS_STATE_STAGE_ONE);
        switch (manager.getState()){
            case CONFIGURATIONS_STATE_STAGE_ONE -> {
                btnAdd.setDisable(true);
                btnListStudents.setDisable(false);
                btnListTeachers.setDisable(false);
                btnListProposals.setDisable(false);
                mniStudent.setDisable(false);
                mniTeacher.setDisable(false);
                mniProposal.setDisable(false);
                btnImportData.setDisable(true);
                btnExportData.setDisable(true);
                btnReturn.setDisable(true);
            }
            case CONFIGURATIONS_STATE_STUDENT_MANAGER -> {
                btnAdd.setDisable(false);
                btnListStudents.setDisable(false);
                btnListTeachers.setDisable(true);
                btnListProposals.setDisable(true);
                mniStudent.setDisable(true);
                mniTeacher.setDisable(false);
                mniProposal.setDisable(false);
                btnImportData.setDisable(false);
                btnExportData.setDisable(false);
                btnReturn.setDisable(false);
            }
            case CONFIGURATIONS_STATE_TEACHER_MANAGER -> {
                btnAdd.setDisable(false);
                btnListStudents.setDisable(true);
                btnListTeachers.setDisable(false);
                btnListProposals.setDisable(true);
                mniStudent.setDisable(false);
                mniTeacher.setDisable(true);
                mniProposal.setDisable(false);
                btnImportData.setDisable(false);
                btnReturn.setDisable(false);
            }
            case CONFIGURATIONS_STATE_PROPOSAL_MANAGER -> {
                btnAdd.setDisable(false);
                btnListStudents.setDisable(true);
                btnListTeachers.setDisable(true);
                btnListProposals.setDisable(false);
                mniStudent.setDisable(false);
                mniTeacher.setDisable(false);
                mniProposal.setDisable(true);
                btnImportData.setDisable(false);
                btnReturn.setDisable(false);
            }
            case CLOSED_STAGE -> {
                btnClose.setDisable(true);
                btnAdd.setDisable(true);
                btnListStudents.setDisable(false);
                btnListTeachers.setDisable(false);
                btnListProposals.setDisable(false);
                mniStudent.setDisable(true);
                mniTeacher.setDisable(true);
                mniProposal.setDisable(true);
                btnImportData.setDisable(true);
                btnReturn.setDisable(true);
            }
        }
    }
}
