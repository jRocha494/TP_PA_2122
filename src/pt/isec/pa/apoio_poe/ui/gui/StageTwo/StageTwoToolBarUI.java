package pt.isec.pa.apoio_poe.ui.gui.StageTwo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pa.apoio_poe.model.Manager;
import pt.isec.pa.apoio_poe.model.fsm.ListingType;
import pt.isec.pa.apoio_poe.ui.gui.util.ToastMessage;

import java.util.Arrays;

public class StageTwoToolBarUI extends ToolBar {
    private final Manager manager;
    //private final BorderPane root;
    private Button btnClose, btnAdvance, btnExit, btnReturn, btnImportData, btnExportData, btnAdd, btnListApplications, btnListStudentsWithSelfProposals,
            btnListStudentsWithApplication, btnListStudentsWithoutApplication;
    MenuButton btnListProposalsWithFilters;
    MenuItem mniApplyFilters;
    CustomMenuItem filter1, filter2, filter3, filter4;
    CheckBox[] filterCbs;
    boolean[] filters;

    public StageTwoToolBarUI(Manager manager/*, BorderPane root*/) {
        this.manager = manager;
        filterCbs = new CheckBox[4];
        filters = new boolean[4];
        Arrays.fill(filters, false);
        //this.root = root;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        btnClose = new Button("Close Stage");
        btnAdvance = new Button("Next Stage");
        btnReturn = new Button("Previous Stage");
        btnImportData = new Button("Import Data");
        btnExportData = new Button("Export Data");
        btnAdd = new Button("Add Data");
        btnExit = new Button("Quit");
        btnListApplications = new Button("List Applications");
        btnListStudentsWithSelfProposals = new Button("View students list with self-proposals");
        btnListStudentsWithApplication = new Button("View students with registered application");
        btnListStudentsWithoutApplication = new Button("View students without registered application");

        btnListProposalsWithFilters = new MenuButton("List Proposals (With Filters)");
        filterCbs[0] = new CheckBox("Students Self-Proposals");
        filterCbs[1] = new CheckBox("Teachers Proposals");
        filterCbs[2] = new CheckBox("Proposals with Application");
        filterCbs[3] = new CheckBox("Proposals without Application");
        filter1 = new CustomMenuItem(filterCbs[0]);
        filter2 = new CustomMenuItem(filterCbs[1]);
        filter3 = new CustomMenuItem(filterCbs[2]);
        filter4 = new CustomMenuItem(filterCbs[3]);
        mniApplyFilters = new MenuItem("Apply Filters");
        filter1.setHideOnClick(false);
        filter2.setHideOnClick(false);
        filter3.setHideOnClick(false);
        filter4.setHideOnClick(false);
        btnListProposalsWithFilters.getItems().setAll(filter1, filter2, filter3, filter4, new SeparatorMenuItem(), mniApplyFilters);

        this.setBackground(new Background(new BackgroundFill(Color.TURQUOISE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.getItems().addAll(btnAdd, btnClose, btnAdvance, btnReturn, btnListApplications,
                btnListStudentsWithSelfProposals, btnListStudentsWithApplication, btnListStudentsWithoutApplication,
                btnListProposalsWithFilters, btnImportData, btnExportData, btnExit);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(Manager.STATE, evt -> update());

        btnClose.setOnAction(actionEvent -> manager.closeStage());
        btnAdvance.setOnAction(actionEvent -> manager.advanceStage());
        btnReturn.setOnAction(actionEvent -> manager.returnStage());
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

        btnAdd.setOnAction(actionEvent -> {
            Dialog dialog = new DialogAddApplication(manager);
            dialog.showAndWait();
        });

        btnListApplications.setOnAction(actionEvent -> {
            manager.setListingType(ListingType.APPLICATIONS);
        });

        btnListStudentsWithSelfProposals.setOnAction(actionEvent -> manager.setListingType(ListingType.STUDENTS_SELFPROPOSALS));
        btnListStudentsWithApplication.setOnAction(actionEvent -> manager.setListingType(ListingType.PROPOSALS_STUDENTS_WITH_APPLICATION));
        btnListStudentsWithoutApplication.setOnAction(actionEvent -> manager.setListingType(ListingType.PROPOSALS_STUDENTS_WITHOUT_APPLICATION));

        filterCbs[0].setOnAction(actionEvent -> { filters[0] = filterCbs[0].isSelected(); });
        filterCbs[1].setOnAction(actionEvent -> { filters[1] = filterCbs[1].isSelected(); });
        filterCbs[2].setOnAction(actionEvent -> { filters[2] = filterCbs[2].isSelected(); });
        filterCbs[3].setOnAction(actionEvent -> { filters[3] = filterCbs[3].isSelected(); });

        mniApplyFilters.setOnAction(actionEvent -> {
            manager.setFiltersStageTwo(filters);
            manager.setListingType(ListingType.PROPOSALS_FILTERS_STAGE_TWO);
        });
    }

    private void update() {
        if (manager.isStageClosed("Stage2")) {
            btnClose.setDisable(true);
            btnAdd.setDisable(true);
            btnImportData.setDisable(true);
        } else {
            btnClose.setDisable(false);
            btnAdd.setDisable(false);
            btnImportData.setDisable(false);
        }
    }
}
