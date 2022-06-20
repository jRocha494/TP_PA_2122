package pt.isec.pa.apoio_poe.ui.gui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import pt.isec.pa.apoio_poe.model.Manager;
import pt.isec.pa.apoio_poe.model.data.Student;
import pt.isec.pa.apoio_poe.model.fsm.AppState;
import pt.isec.pa.apoio_poe.ui.gui.StageOne.DialogAddStudent;
import pt.isec.pa.apoio_poe.ui.gui.StageOne.DialogProposal;
import pt.isec.pa.apoio_poe.ui.gui.StageOne.DialogStudent;
import pt.isec.pa.apoio_poe.ui.gui.StageOne.DialogTeacher;
import pt.isec.pa.apoio_poe.ui.gui.StageTwo.DialogApplication;

public class ListPane extends ListView<Object> {
    private final Manager manager;

    public ListPane(Manager manager) {
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setBackground(new Background(new BackgroundFill(Color.CRIMSON, CornerRadii.EMPTY, Insets.EMPTY)));
        //this.setContextMenu(new ContextMenu(new MenuItem("Delete"), new MenuItem("Edit")));
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(Manager.LISTING, evt -> update());
        manager.addPropertyChangeListener(Manager.DATA, evt -> update());

        this.setOnMouseClicked(mouseEvent -> {
            System.out.println(manager.getState());
            if(mouseEvent.getClickCount() == 2/* && manager.getState() != AppState.CLOSED_STAGE*/){
                Object object = this.getSelectionModel().getSelectedItem();
                if (object != null) {
                    switch(manager.getListingType()){
                        case STUDENTS -> {
                            Dialog dialog = new DialogStudent(manager, object);
                            dialog.showAndWait();
                        }
                        case TEACHERS -> {
                            Dialog dialog = new DialogTeacher(manager, object);
                            dialog.showAndWait();
                        }
                        case PROPOSALS -> {
                            Dialog dialog = new DialogProposal(manager, object);
                            dialog.showAndWait();
                        }
                        case APPLICATIONS -> {
                            Dialog dialog = new DialogApplication(manager, object);
                            dialog.showAndWait();
                        }
                    }
                }
            }
        });
    }

    private void update() {
        this.getItems().clear();
        this.setVisible(true);

        switch(manager.getListingType()){
            case STUDENTS -> this.getItems().addAll(manager.getStudents());
            case TEACHERS -> this.getItems().addAll(manager.getTeachers());
            case PROPOSALS -> this.getItems().addAll(manager.getProposals());
            case APPLICATIONS -> this.getItems().addAll(manager.getApplications());
            case STUDENTS_SELFPROPOSALS -> this.getItems().addAll(manager.getStudentsSelfProposals());
            case PROPOSALS_STUDENTS_WITH_APPLICATION -> this.getItems().addAll(manager.getStudentsWithApplication());
            case PROPOSALS_STUDENTS_WITHOUT_APPLICATION -> this.getItems().addAll(manager.getStudentsWithoutApplication());
            case PROPOSALS_FILTERS_STAGE_TWO -> this.getItems().addAll(manager.getProposalsWithFiltersStageTwo());
            case PROPOSALS_FILTERS_STAGE_THREE -> this.getItems().addAll(manager.getProposalsWithFiltersStageThree());
            case STUDENTS_ASSIGNED -> this.getItems().addAll(manager.getStudentsAssigned());
            case STUDENTS_UNASSIGNED -> this.getItems().addAll(manager.getListStudentsUnassigned());
            case ASSIGNMENTS -> this.getItems().addAll(manager.getAssignments());
            case STUDENTS_ASSIGNED_WITH_ADVISOR -> this.getItems().addAll(manager.getStudentsAssignedWithAdvisor());
            case STUDENTS_ASSIGNED_WITHOUT_ADVISOR -> this.getItems().addAll(manager.getStudentsAssignedWithoutAdvisor());
            case STUDENTS_ASSIGNED_STAGE_FIVE -> this.getItems().addAll(manager.viewStudentsAssigned());
            case STUDENTS_UNASSIGNED_WITH_APPLICATION -> this.getItems().addAll(manager.viewStudentsUnassignedWithApplications());
            case PROPOSALS_AVAILABLE -> this.getItems().addAll(manager.viewProposalsUnassigned());
            case PROPOSALS_ASSIGNED -> this.getItems().addAll(manager.viewProposalsAssigned());
            default -> this.setVisible(false);
        }
    }
}
