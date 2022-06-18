package pt.isec.pa.apoio_poe.ui.gui;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import pt.isec.pa.apoio_poe.model.Manager;
import pt.isec.pa.apoio_poe.model.data.Proposal;
import pt.isec.pa.apoio_poe.model.data.Student;
import pt.isec.pa.apoio_poe.model.data.Teacher;
import pt.isec.pa.apoio_poe.model.fsm.AppState;
import pt.isec.pa.apoio_poe.ui.gui.StageOne.DialogAddStudent;
import pt.isec.pa.apoio_poe.ui.gui.StageOne.DialogStudent;

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
            if(mouseEvent.getClickCount() == 2 && manager.getState() != AppState.CONFIGURATIONS_STATE_STAGE_ONE){
                Object object = this.getSelectionModel().getSelectedItem();
                if (object != null) {
                    switch(manager.getListingType()){
                        case STUDENTS -> {
                            Dialog dialog = new DialogStudent(manager, (Student) object);
                            dialog.showAndWait();
                        }
//                        case TEACHERS -> {
//                            Dialog dialog = new DialogTeacher(manager, (Teacher) object);
//                            dialog.showAndWait();
//                        }
//                        case PROPOSALS -> {
//                            Dialog dialog = new DialogProposal(manager, (Proposal) object);
//                            dialog.showAndWait();
//                        }
                    }
                    //manager.delete(this.getSelectionModel().getSelectedItem());
                }
//                manager.remove(this.getSelectionModel().getSelectedIndex());
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
            case PROPOSALS_FILTERS -> this.getItems().addAll(manager.getProposalsWithFilters());
            default -> this.setVisible(false);
        }
    }
}
