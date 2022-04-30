package pt.isec.pa.apoio_poe.ui.text;

import pt.isec.pa.apoio_poe.model.Manager;
import pt.isec.pa.apoio_poe.utils.PAInput;

import java.util.ArrayList;
import java.util.List;

public class TextUI {
    private Manager m;
    private boolean finish;

    public TextUI(Manager m) {
        this.m = m;
        this.finish = false;
    }

    private void stageOneUI() {
        System.out.println("STAGE ONE, " + m.getState());
        switch(PAInput.chooseOption("What do you pretend to do?", "Change configuration mode", "View students list", "View teachers list", "View proposals list", "Close stage", "Advance to next stage", "Save application state", "Quit")){
            //TODO: ADD OPTIONS TO VIEW DATA (STUDENT, TEACHER, PROPOSALS)
            case 1 -> m.changeConfigurationMode(PAInput.chooseOption("Choose a configuration mode", "Students", "Teachers", "Proposals"));
            case 2 -> System.out.println(m.viewStudents());
            case 3 -> System.out.println(m.viewTeachers());
            case 4 -> System.out.println(m.viewProposals());
            case 5 -> m.closeStage();
            case 6 -> m.advanceStage();
            // TODO case 7 -> save();
            default -> finish = true;
        }
    }

    private void proposalManagerUI() {
        System.out.println("PROPOSAL MANAGER, " + m.getState());
        switch(PAInput.chooseOption("What do you pretend to do?", "Change configuration mode", "View proposals list", "Import data from CSV file", "Export data to CSV file", "Close stage", "Advance to next stage", "Save application state", "Quit")){
            case 1 -> m.changeConfigurationMode(PAInput.chooseOption("Choose a configuration mode", "Students", "Teachers"));
            case 2 -> m.viewProposals();
            case 3 -> System.out.println(m.importProposalsCSV(PAInput.readString("Introduce the name of the file to read: ", true)));
            case 4 -> System.out.println(m.exportProposalsCSV(PAInput.readString("Introduce the name of the file to write: ", true)));
            case 5 -> m.closeStage();
            case 6 -> m.advanceStage();
            // TODO case 7 -> save();
            default -> finish = true;
        }
    }

    private void studentManagerUI() {
        System.out.println("STUDENT MANAGER, " + m.getState());
        switch(PAInput.chooseOption("What do you pretend to do?", "Change configuration mode", "View students list", "Import data from CSV file", "Export data to CSV file", "Close stage", "Advance to next stage", "Save application state", "Quit")){
            case 1 -> m.changeConfigurationMode(PAInput.chooseOption("Choose a configuration mode", "Teachers", "Proposals"));
            case 2 -> m.viewStudents();
            case 3 -> System.out.println(m.importStudentsCSV(PAInput.readString("Introduce the name of the file to read: ", true)));
            case 4 -> System.out.println(m.exportStudentsCSV(PAInput.readString("Introduce the name of the file to write: ", true)));
            case 5 -> m.closeStage();
            case 6 -> m.advanceStage();
            // TODO case 7 -> save();
            default -> finish = true;
        }
    }

    private void teacherManagerUI() {
        System.out.println("TEACHER MANAGER, " + m.getState());
        switch(PAInput.chooseOption("What do you pretend to do?", "Change configuration mode", "View teachers list", "Import data from CSV file", "Export data to CSV file", "Close stage", "Advance to next stage", "Save application state", "Quit")){
            case 1 -> m.changeConfigurationMode(PAInput.chooseOption("Choose a configuration mode", "Students", "Proposals"));
            case 2 -> m.viewTeachers();
            case 3 -> System.out.println(m.importTeachersCSV(PAInput.readString("Introduce the name of the file to read: ", true)));
            case 4 -> System.out.println(m.exportTeachersCSV(PAInput.readString("Introduce the name of the file to write: ", true)));
            case 5 -> m.closeStage();
            case 6 -> m.advanceStage();
            // TODO case 7 -> save();
            default -> finish = true;
        }
    }

    private void closedStageUI() {
        System.out.println("CLOSED STAGE, " + m.getState());
        switch(PAInput.chooseOption("What do you pretend to do?", "View students list", "View teachers list", "View proposals list", "Advance to next stage", "Quit")){
            case 1 -> m.viewStudents();
            case 2 -> m.viewTeachers();
            case 3 -> m.viewProposals();
            case 4 -> m.advanceStage();
            // TODO case 5 -> save();
            default -> finish = true;
        }
    }

    private void stageTwoUI() {
        System.out.println("STAGE TWO, " + m.getState());
        switch(PAInput.chooseOption("What do you pretend to do?", "Import data from CSV file", "View students list with self-proposals", "View students with registered application", "View students without registered application", "View applications list", "Export data to CSV file","Close Stage", "Return to previous stage", "Advance to next stage", "Quit")){
            //TODO: ADD OPTIONS TO VIEW DATA (STUDENT, TEACHER, PROPOSALS)
            case 1 -> System.out.println(m.viewStudentsSelfProposals());
            case 2 -> System.out.println(m.viewStudentsWithApplication());
            case 3 -> System.out.println(m.viewStudentsWithoutApplication());
            case 4 -> viewProposalsWithFilters();
            case 5 -> System.out.println(m.importApplicationsCSV(PAInput.readString("Introduce the name of the file to read: ", true)));
            case 6 -> System.out.println(m.exportApplicationsCSV(PAInput.readString("Introduce the name of the file to write: ", true)));
            case 7 -> m.closeStage();
            case 8 -> m.returnStage();
            case 9 -> m.advanceStage();
            // TODO case 9 -> save();
            default -> finish = true;
        }
        System.out.println("STAGE TWO, " + m.getState());
    }

    private void viewProposalsWithFilters(){
        int choice = 0;
        ArrayList<Integer> filters = new ArrayList<>();

        while (!finish){
            choice = PAInput.chooseOption("Please choose a filter, and after that confirm the choices","Students Self-Proposals", "Teachers Proposals", "Proposals with Application", "Proposals without Application", "Confirm filters");

            if(choice < 5) {
                if(!filters.contains(choice)) {
                    System.out.println("Filter " + choice + " added!");
                    filters.add(choice);
                } else {
                    System.out.println("Filter " + choice + " removed!");
                    filters.remove(choice);
                }
            } else
                break;
        }
        m.filterProposals(filters.toArray());
    }

    private void stageThreeUI() {
        System.out.println("STAGE THREE, " + m.getState());
    }

    private void conflictStateUI() {
        System.out.println("CONFLICT STATE, " + m.getState());
    }

    private void stageFourUI() {
        System.out.println("STAGE FOUR, " + m.getState());
    }

    private void stageFiveUI() {
        System.out.println("STAGE FIVE, " + m.getState());
    }

    public void start(){
        while (!finish) {
            System.out.println("\nCurrent Stage: " + m.getStage());
            switch (m.getState()){
                case CONFIGURATIONS_STATE_STAGE_ONE -> stageOneUI();
                case CONFIGURATIONS_STATE_PROPOSAL_MANAGER -> proposalManagerUI();
                case CONFIGURATIONS_STATE_STUDENT_MANAGER -> studentManagerUI();
                case CONFIGURATIONS_STATE_TEACHER_MANAGER -> teacherManagerUI();
                case CLOSED_STAGE -> closedStageUI();
                case APPLICATION_OPTIONS_STAGE_TWO -> stageTwoUI();
                case PROPOSAL_ATTRIBUTION_STAGE_THREE -> stageThreeUI();
                case CONFLICT_STATE -> conflictStateUI();
                case ADVISOR_ATTRIBUTION_STAGE_FOUR -> stageFourUI();
                case VIEW_DATA_STAGE_FIVE -> stageFiveUI();
            }
        }
    }


}
