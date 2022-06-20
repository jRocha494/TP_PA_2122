package pt.isec.pa.apoio_poe.ui.gui.StageThree;

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

public class StageThreeToolBarUI extends ToolBar {
    private final Manager manager;
    //private final BorderPane root;
    private Button btnClose, btnAdvance, btnExit, btnReturn, btnExportData,
            btnSelfProposalAttribution, btnRemoveAssignement, btnRemoveAllAssignments,
            btnListStudentsWithSelfProposals, btnListStudentsWithApplication,
            btnListStudentsAssigned, btnListStudentsUnassigned, btnManuallyAssign, btnAutomaticAssign;
    MenuButton btnListProposalsWithFilters;
    MenuItem mniApplyFilters;
    CustomMenuItem filter1, filter2, filter3, filter4;
    CheckBox[] filterCbs;
    boolean[] filters;

    public StageThreeToolBarUI(Manager manager/*, BorderPane root*/) {
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
        btnExportData = new Button("Export Data");
        btnExit = new Button("Quit");
        btnSelfProposalAttribution = new Button("Automatic assign self-proposals/proposals with a pre-defined student");
        btnRemoveAssignement = new Button("Remove an assignment");
        btnRemoveAllAssignments = new Button("Remove all assignments");
        btnListStudentsWithSelfProposals = new Button("View students list with self-proposals");
        btnListStudentsWithApplication = new Button("View students with registered application");
        btnListStudentsAssigned = new Button("View students assigned to proposal");
        btnListStudentsUnassigned = new Button("View students unassigned");
        btnManuallyAssign = new Button("Manually assign proposal to student");
        btnAutomaticAssign = new Button("Automatic assign a student to an available proposal");

        btnListProposalsWithFilters = new MenuButton("List Proposals (With Filters)");
        filterCbs[0] = new CheckBox("Students Self-Proposals");
        filterCbs[1] = new CheckBox("Teachers Proposals");
        filterCbs[2] = new CheckBox("Proposals Unassigned");
        filterCbs[3] = new CheckBox("Proposals Assigned");
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
        this.getItems().addAll(btnClose, btnAdvance, btnReturn, btnSelfProposalAttribution, btnRemoveAssignement,
                btnRemoveAllAssignments, btnListStudentsWithSelfProposals, btnListStudentsWithApplication,
                btnListStudentsAssigned, btnListStudentsUnassigned, btnListProposalsWithFilters, btnManuallyAssign,
                btnAutomaticAssign, btnExportData, btnExit);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(Manager.STATE, evt -> update());

        btnClose.setOnAction(actionEvent -> manager.closeStage());
        btnAdvance.setOnAction(actionEvent -> manager.advanceStage());
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

        btnSelfProposalAttribution.setOnAction(actionEvent -> manager.automaticAssignmentSelfProposals());
        btnRemoveAssignement.setOnAction(actionEvent -> {
            ChoiceBox choiceBox = new ChoiceBox();
            choiceBox.getItems().addAll(manager.getAssignments());

            GridPane gridPane = new GridPane();
            gridPane.add(new Label("Assignment"), 0, 0);
            gridPane.add(choiceBox, 1, 0);

            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);
            alert.setHeaderText("Remove Assignment");
            alert.getDialogPane().setContent(gridPane);

            final Button btnApply = (Button) alert.getDialogPane().lookupButton(ButtonType.APPLY);
            btnApply.addEventFilter(ActionEvent.ACTION, event -> {
                if(!manager.removeAssignment(choiceBox.getSelectionModel().getSelectedIndex() + 1)){
                    event.consume();
                    ToastMessage.show(gridPane.getScene().getWindow(), "Something went wrong");
                }
            });

            alert.showAndWait();
        });
        btnRemoveAllAssignments.setOnAction(actionEvent -> manager.removeAllAssignments());

        btnListStudentsWithSelfProposals.setOnAction(actionEvent -> manager.setListingType(ListingType.STUDENTS_SELFPROPOSALS));
        btnListStudentsWithApplication.setOnAction(actionEvent -> manager.setListingType(ListingType.PROPOSALS_STUDENTS_WITH_APPLICATION));
        btnListStudentsAssigned.setOnAction(actionEvent -> manager.setListingType(ListingType.STUDENTS_ASSIGNED));
        btnListStudentsUnassigned.setOnAction(actionEvent -> manager.setListingType(ListingType.STUDENTS_UNASSIGNED));

        filterCbs[0].setOnAction(actionEvent -> { filters[0] = filterCbs[0].isSelected(); });
        filterCbs[1].setOnAction(actionEvent -> { filters[1] = filterCbs[1].isSelected(); });
        filterCbs[2].setOnAction(actionEvent -> { filters[2] = filterCbs[2].isSelected(); });
        filterCbs[3].setOnAction(actionEvent -> { filters[3] = filterCbs[3].isSelected(); });

        mniApplyFilters.setOnAction(actionEvent -> {
            manager.setFiltersStageThree(filters);
            manager.setListingType(ListingType.PROPOSALS_FILTERS_STAGE_THREE);
        });

        btnManuallyAssign.setOnAction(actionEvent -> {
            ChoiceBox studentCB = new ChoiceBox();
            studentCB.getItems().addAll(manager.getAvailableStudents());
            ChoiceBox proposalCB = new ChoiceBox();
            proposalCB.getItems().addAll(manager.getAvailableProposals());

            GridPane gridPane = new GridPane();
            gridPane.add(new Label("Student"), 0, 0);
            gridPane.add(studentCB, 1, 0);
            gridPane.add(new Label("Proposal"), 0, 1);
            gridPane.add(proposalCB, 1, 1);

            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);
            alert.setHeaderText("Manually Assign");
            alert.getDialogPane().setContent(gridPane);

            final Button btnApply = (Button) alert.getDialogPane().lookupButton(ButtonType.APPLY);
            btnApply.addEventFilter(ActionEvent.ACTION, event -> {
                if(!manager.manuallyAssign((String) studentCB.getValue(), (String) proposalCB.getValue())){
                    event.consume();
                    ToastMessage.show(gridPane.getScene().getWindow(), "Something went wrong");
                }
            });

            alert.showAndWait();
        });
        btnAutomaticAssign.setOnAction(actionEvent -> manager.automaticAssignment());
    }

    private void update() {
        if (manager.isStageClosed("Stage2")) {
            btnClose.setDisable(true);
            btnAutomaticAssign.setDisable(false);
        } else {
            btnClose.setDisable(false);
            btnAutomaticAssign.setDisable(true);
        }
    }
}
