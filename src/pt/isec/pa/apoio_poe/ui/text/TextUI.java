package pt.isec.pa.apoio_poe.ui.text;

import pt.isec.pa.apoio_poe.model.Manager;
import pt.isec.pa.apoio_poe.utils.PAInput;

public class TextUI {
    private Manager m;
    private boolean finish = false;

    public TextUI(Manager m) {
        this.m = m;
    }

    private void stageOneUI() {
        String filename;

        switch(PAInput.chooseOption("Options:", "Import Proposals", "Export Proposals", "Import Students", "Export Students", "Import Teachers", "Export Teachers", "Quit")){
            case 1:
                break;
            case 2:
                break;
            case 3:
                filename = PAInput.readString("Introduce the name of the file to read: ", true);
                System.out.println(m.importStudentsCSV(filename));
                break;
            case 4:
                break;
            case 5:
                filename = PAInput.readString("Introduce the name of the file to read: ", true);
                System.out.println(m.importTeachersCSV(filename));
                break;
            case 6:
                filename = PAInput.readString("Introduce the name of the file to write: ", true);
                System.out.println(m.exportTeachersCSV(filename));
                break;
            case 7:
                finish = true;
        }
    }

    private void proposalManagerUI() {
        System.out.println("PROPOSAL MANAGER, " + m.getState());
    }

    private void studentManagerUI() {
        System.out.println("STUDENT MANAGER, " + m.getState());
    }

    private void teacherManagerUI() {
        System.out.println("TEACHER MANAGER, " + m.getState());
    }

    private void closedStageUI() {
        System.out.println("CLOSED STAGE, " + m.getState());
    }

    private void stageTwoUI() {
        System.out.println("STAGE TWO, " + m.getState());
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
                case CANDIDATURE_OPTIONS_STAGE_TWO -> stageTwoUI();
                case PROPOSAL_ATTRIBUTION_STAGE_THREE -> stageThreeUI();
                case CONFLICT_STATE -> conflictStateUI();
                case ADVISOR_ATTRIBUTION_STAGE_FOUR -> stageFourUI();
                case VIEW_DATA_STAGE_FIVE -> stageFiveUI();
            }
        }
    }


}
