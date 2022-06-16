package pt.isec.pa.apoio_poe.ui.gui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pa.apoio_poe.model.Manager;
import pt.isec.pa.apoio_poe.model.fsm.AppState;
import pt.isec.pa.apoio_poe.model.fsm.ListingType;

public class ToolBarUI extends ToolBar {
    private final Manager manager;
    //private final BorderPane root;
    private Button btnClose, btnAdvance, btnExit, btnListStudents, btnListTeachers, btnListProposals, btnImportData, btnExportData, btnAdd;
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

        btnChangeMode = new MenuButton("Change Mode");
        mniStudent = new MenuItem("Students");
        mniTeacher = new MenuItem("Teachers");
        mniProposal = new MenuItem("Proposals");
        btnChangeMode.getItems().addAll(mniStudent, mniTeacher, mniProposal);

        this.setBackground(new Background(new BackgroundFill(Color.TURQUOISE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.getItems().addAll(btnAdd, btnClose, btnAdvance, btnListStudents, btnListTeachers, btnListProposals, btnChangeMode, btnImportData, btnExportData, btnExit);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(Manager.STATE, evt -> update());

        btnClose.setOnAction(actionEvent -> manager.closeStage());
        btnAdvance.setOnAction(actionEvent -> manager.advanceStage());
        btnExit.setOnAction(actionEvent -> Platform.exit());
        btnListStudents.setOnAction(actionEvent -> {
            manager.setListingType(ListingType.STUDENTS);
        });
        btnListTeachers.setOnAction(actionEvent -> {
            manager.setListingType(ListingType.TEACHERS);
        });
        btnListProposals.setOnAction(actionEvent -> {
            manager.setListingType(ListingType.PROPOSALS);
        });
        mniStudent.setOnAction(actionEvent -> manager.changeConfigurationMode(AppState.CONFIGURATIONS_STATE_STUDENT_MANAGER.ordinal()));
        mniTeacher.setOnAction(actionEvent -> manager.changeConfigurationMode(AppState.CONFIGURATIONS_STATE_TEACHER_MANAGER.ordinal()));
        mniProposal.setOnAction(actionEvent -> manager.changeConfigurationMode(AppState.CONFIGURATIONS_STATE_PROPOSAL_MANAGER.ordinal()));

        btnAdd.setOnAction(actionEvent -> {
            Dialog dialog = new DialogAddStudent(manager);
            dialog.showAndWait();
        });
    }

    private void update() {
        //btnAdd.setDisable(manager.getState() == AppState.CONFIGURATIONS_STATE_STAGE_ONE);
        switch (manager.getState()){
            case CONFIGURATIONS_STATE_STAGE_ONE -> {
                btnAdd.setDisable(true);
                btnListTeachers.setDisable(false);
                btnListProposals.setDisable(false);
                mniStudent.setDisable(false);
                btnImportData.setDisable(true);
                btnExportData.setDisable(true);
            }
            case CONFIGURATIONS_STATE_STUDENT_MANAGER -> {
                btnAdd.setDisable(false);
                btnListTeachers.setDisable(true);
                btnListProposals.setDisable(true);
                mniStudent.setDisable(true);
                btnImportData.setDisable(false);
                btnExportData.setDisable(false);
            }
        }
    }
}
