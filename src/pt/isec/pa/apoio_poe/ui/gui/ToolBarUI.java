package pt.isec.pa.apoio_poe.ui.gui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pa.apoio_poe.model.Manager;
import pt.isec.pa.apoio_poe.model.fsm.AppState;
import pt.isec.pa.apoio_poe.model.fsm.ListingType;

public class ToolBarUI extends ToolBar {
    private final Manager manager;
    //private final BorderPane root;
    private Button btnClose, btnAdvance, btnExit, btnListStudents, btnListTeachers, btnListProposals;
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
        btnExit = new Button("Quit");

        btnChangeMode = new MenuButton("Change Mode");
        mniStudent = new MenuItem("Students");
        mniTeacher = new MenuItem("Teachers");
        mniProposal = new MenuItem("Proposals");
        btnChangeMode.getItems().addAll(mniStudent, mniTeacher, mniProposal);

        this.setBackground(new Background(new BackgroundFill(Color.TURQUOISE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.getItems().addAll(btnClose, btnAdvance, btnListStudents, btnListTeachers, btnListProposals, btnChangeMode, btnExit);
    }

    private void registerHandlers() {
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
    }

    private void update() {
    }


}
