package pt.isec.pa.apoio_poe.ui.text;

import pt.isec.pa.apoio_poe.model.Manager;
import pt.isec.pa.apoio_poe.utils.PAInput;

public class TextUI {
    private Manager m;
    private boolean finish;

    public TextUI(Manager m) {
        this.m = m;
        this.finish = false;
    }

    private void stageOneUI() {
        //TODO: SEPARAR PELOS ESTADOS DA FASE 1
        System.out.println("STAGE ONE, " + m.getState());
        switch(PAInput.chooseOption("What do you pretend to do?", "Change configuration mode", "Close stage", "Advance to next stage", "Save application state", "Quit")){
            //TODO: ADD OPTIONS TO VIEW DATA (STUDENT, TEACHER, PROPOSALS)
            case 1 -> m.changeConfigurationMode(PAInput.chooseOption("Choose a configuration mode", "Students", "Teachers", "Proposals"));
            case 2 -> m.closeStage();
            case 3 -> m.advanceStage();
            // TODO case 4 -> save();
            default ->finish = true;
        }
    }

    private void proposalManagerUI() {
        System.out.println("PROPOSAL MANAGER, " + m.getState());
        switch(PAInput.chooseOption("What do you pretend to do?", "Import data from CSV file", "Export data to CSV file", "Change configuration mode", "Close stage", "Advance to next stage", "Save application state", "Quit")){
            case 1 -> System.out.println(m.importProposalsCSV(PAInput.readString("Introduce the name of the file to read: ", true)));
            case 2 -> System.out.println(m.exportProposalsCSV(PAInput.readString("Introduce the name of the file to write: ", true)));
            case 3 -> m.changeConfigurationMode(PAInput.chooseOption("Choose a configuration mode", "Students", "Teachers", "Proposals"));
            case 4 -> m.closeStage();
            case 5 -> m.advanceStage();
            // TODO case 6 -> save();
            default ->finish = true;
        }
    }

    private void studentManagerUI() {
        System.out.println("STUDENT MANAGER, " + m.getState());
        switch(PAInput.chooseOption("What do you pretend to do?", "Import data from CSV file", "Export data to CSV file", "Change configuration mode", "Close stage", "Advance to next stage", "Save application state", "Quit")){
            case 1 -> System.out.println(m.importStudentsCSV(PAInput.readString("Introduce the name of the file to read: ", true)));
            case 2 -> System.out.println(m.exportStudentsCSV(PAInput.readString("Introduce the name of the file to write: ", true)));
            case 3 -> m.changeConfigurationMode(PAInput.chooseOption("Choose a configuration mode", "Students", "Teachers", "Proposals"));
            case 4 -> m.closeStage();
            case 5 -> m.advanceStage();
            // TODO case 6 -> save();
            default ->finish = true;
        }
    }

    private void teacherManagerUI() {
        System.out.println("TEACHER MANAGER, " + m.getState());
        switch(PAInput.chooseOption("What do you pretend to do?", "Import data from CSV file", "Export data to CSV file", "Change configuration mode", "Close stage", "Advance to next stage", "Save application state", "Quit")){
            case 1 -> System.out.println(m.importTeachersCSV(PAInput.readString("Introduce the name of the file to read: ", true)));
            case 2 -> System.out.println(m.exportTeachersCSV(PAInput.readString("Introduce the name of the file to write: ", true)));
            case 3 -> m.changeConfigurationMode(PAInput.chooseOption("Choose a configuration mode", "Students", "Teachers", "Proposals"));
            case 4 -> m.closeStage();
            case 5 -> m.advanceStage();
            // TODO case 6 -> save();
            default ->finish = true;
        }
    }

    private void closedStageUI() {
        System.out.println("CLOSED STAGE, " + m.getState());
        switch(PAInput.chooseOption("What do you pretend to do?", "Advance to next stage", "Quit")){
            //TODO: ADD OPTIONS TO VIEW DATA (STUDENT, TEACHER, PROPOSALS)
            case 1 -> m.advanceStage();
        }
    }

    private void stageTwoUI() {
        System.out.println("STAGE TWO, " + m.getState());
        switch(PAInput.chooseOption("What do you pretend to do?", "Close Stage", "Return to previous stage", "Advance to next stage", "Quit")){
            //TODO: ADD OPTIONS TO VIEW DATA (STUDENT, TEACHER, PROPOSALS)
            case 1 -> m.closeStage();
            case 2 -> m.returnStage();
            case 3 -> m.advanceStage();
        }
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
