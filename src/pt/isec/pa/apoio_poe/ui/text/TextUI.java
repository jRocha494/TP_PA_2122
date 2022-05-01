package pt.isec.pa.apoio_poe.ui.text;

import pt.isec.pa.apoio_poe.model.Manager;
import pt.isec.pa.apoio_poe.utils.PAInput;

import java.util.ArrayList;

public class TextUI {
    private Manager m;
    private boolean finish;

    public TextUI(Manager m) {
        this.m = m;
        this.finish = false;
    }

    private String viewProposalsWithFiltersStageTwo(){
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
                    filters.remove(filters.indexOf(choice));
                }
            } else
                break;
        }
        return m.filterProposals(filters.toArray(new Integer[0]));
    }

    private String viewProposalsWithFiltersStageThree(){
        int choice = 0;
        ArrayList<Integer> filters = new ArrayList<>();

        while (!finish){
            choice = PAInput.chooseOption("Please choose a filter, and after that confirm the choices","Students Self-Proposals", "Teachers Proposals", "Proposals Unassigned", "Proposals assigned", "Confirm filters");

            if(choice < 5) {
                if(!filters.contains(choice)) {
                    System.out.println("Filter " + choice + " added!");
                    filters.add(choice);
                } else {
                    System.out.println("Filter " + choice + " removed!");
                    filters.remove(filters.indexOf(choice));
                }
            } else
                break;
        }
        return m.filterProposals(filters.toArray(new Integer[0]));
    }

    private void manuallyAssign() {
        String[] availableProposals = m.getAvailableProposals();
        String[] availableStudents = m.getAvailableStudents();
        m.manuallyAssign(PAInput.chooseOption("Choose a proposal to assign", availableProposals), PAInput.chooseOption("Choose a student to assign the proposal to", availableStudents), availableProposals, availableStudents);
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
            case 2 -> System.out.println(m.viewProposals());
            //case 3 -> System.out.println(m.importProposalsCSV(PAInput.readString("Introduce the name of the file to read: ", true)));
            case 3 -> System.out.println(m.importProposalsCSV("proposals1"));
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
            case 2 -> System.out.println(m.viewStudents());
            //case 3 -> System.out.println(m.importStudentsCSV(PAInput.readString("Introduce the name of the file to read: ", true)));
            case 3 -> System.out.println(m.importStudentsCSV("students1"));
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
            case 2 -> System.out.println(m.viewTeachers());
            //case 3 -> System.out.println(m.importTeachersCSV(PAInput.readString("Introduce the name of the file to read: ", true)));
            case 3 -> System.out.println(m.importTeachersCSV("teachers1"));
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
            case 1 -> System.out.println(m.viewStudents());
            case 2 -> System.out.println(m.viewTeachers());
            case 3 -> System.out.println(m.viewProposals());
            case 4 -> m.advanceStage();
            // TODO case 5 -> save();
            default -> finish = true;
        }
    }

    private void stageTwoUI() {
        System.out.println("STAGE TWO, " + m.getState());
        switch(PAInput.chooseOption("What do you pretend to do?", "View students list with self-proposals", "View students with registered application", "View students without registered application", "View proposals list", "Import data from CSV file", "Export data to CSV file","Close Stage", "Return to previous stage", "Advance to next stage", "Quit")){
            //TODO: ADD OPTIONS TO VIEW DATA (APPLICATIONS)
            case 1 -> System.out.println(m.viewStudentsSelfProposals());
            case 2 -> System.out.println(m.viewStudentsWithApplication());
            case 3 -> System.out.println(m.viewStudentsWithoutApplication());
            case 4 -> System.out.println(viewProposalsWithFiltersStageTwo());
            case 5 -> System.out.println(m.importApplicationsCSV(PAInput.readString("Introduce the name of the file to read: ", true)));
            case 6 -> System.out.println(m.exportApplicationsCSV(PAInput.readString("Introduce the name of the file to write: ", true)));
            case 7 -> m.closeStage();
            case 8 -> m.returnStage();
            case 9 -> m.advanceStage();
            // TODO case 9 -> save();
            default -> finish = true;
        }
    }

    private void stageThreePrevClosedUI() {
        System.out.println("STAGE THREE PREV CLOSED, " + m.getState());
        switch (PAInput.chooseOption("What do you pretend to do?","Automatic assign self-proposals/proposals with a pre-defined student", "Automatic assign a student to an available proposal", "Manually assign proposal to student", "Remove an assignment", "Remove all assignments", "View students list with self-proposals", "View students with registered application", "View students assigned to proposal", "View students unassigned", "View proposals list", "Close stage", "Advance stage", "Export data to CSV file", "Quit")) {
            case 1 -> m.automaticAssignmentSelfProposals();
            case 2 -> m.automaticAssignment();
            case 3 -> manuallyAssign();
            case 4 -> m.removeAssignment(PAInput.chooseOption("Choose assignment to remove", m.viewAssignments()));
            case 5 -> m.removeAllAssignments();
            case 6 -> System.out.println(m.viewStudentsSelfProposals());
            case 7 -> System.out.println(m.viewStudentsWithApplication());
            case 8 -> System.out.println(m.viewStudentsAssigned());
            case 9 -> System.out.println(m.viewStudentsUnassigned());
            case 10 -> System.out.println(viewProposalsWithFiltersStageThree());
            case 11 -> m.closeStage();
            case 12 -> m.advanceStage();
            case 13 -> m.exportStageThreeCSV(m.exportStageThreeCSV(PAInput.readString("Introduce the name of the file to write: ", true)));
            // TODO save()
            default -> finish = true;
        }
    }

    private void stageThreePrevOpenUI() {
        System.out.println("STAGE THREE PREV OPEN, " + m.getState());
        switch (PAInput.chooseOption("What do you pretend to do?", "Automatic assign self-proposals/proposals with a pre-defined student", "Remove an assignment", "Remove all assignments", "View students list with self-proposals", "View students with registered application", "View students assigned to proposals", "View students unassigned", "View proposals list", "Return to previous stage", "Advance stage", "Export data to CSV file", "Quit")) {
            case 1 -> m.automaticAssignmentSelfProposals();
            case 2 -> m.removeAssignment(PAInput.chooseOption("Choose assignment to remove", m.viewAssignments()));
            case 3 -> m.removeAllAssignments();
            case 4 -> System.out.println(m.viewStudentsSelfProposals());
            case 5 -> System.out.println(m.viewStudentsWithApplication());
            case 6 -> System.out.println(m.viewStudentsAssigned());
            case 7 -> System.out.println(m.viewStudentsUnassigned());
            case 8 -> System.out.println(viewProposalsWithFiltersStageThree());
            case 9 -> m.returnStage();
            case 10 -> m.advanceStage();
            case 11 -> m.exportStageThreeCSV(m.exportStageThreeCSV(PAInput.readString("Introduce the name of the file to write: ", true)));
            // TODO save()
            default -> finish = true;
        }
    }

    private void conflictStateUI() {
        System.out.println("CONFLICT STAGE, " + m.getState());
        switch (PAInput.chooseOption("What do you pretend to do?", "Resolve Conflicts", "Quit")) {
            case 1 -> m.resolveConflictedCases(PAInput.chooseOption("Conflicts:\nProposal: " + m.getConflictedProposal() + "\nStudents:",m.getConflictedCases()));
            default -> finish = true;
        }
    }

    private void stageFourUI() {
        System.out.println("STAGE FOUR, " + m.getState());
        switch (PAInput.chooseOption("What do you pretend to do?", "Automatic assign teachers to their proposals as an advisor", "Close stage", "Return to previous stage", "View students assigned to proposals with advisors", "View students assigned to proposals without advisors", "Quit")) {
            case 1 -> m.automaticAssignmentAdvisors();
            case 2 -> m.closeStage();
            case 3 -> m.returnStage();
            case 4 -> System.out.println(m.viewStudentsAssignedWithAdvisor());
            case 5 -> System.out.println(m.viewStudentsAssignedWithoutAdvisor());
            default -> finish = true;
        }
    }

    private void stageFiveUI() {
        System.out.println("STAGE FIVE, " + m.getState());
        switch(PAInput.chooseOption("What do you pretend to do?", "View students assigned to proposals", "View students unassigned with applications", "View available proposals", "View assigned proposals", "Quit")){
            case 1 -> System.out.println(m.viewStudentsAssigned());
            case 2 -> System.out.println(m.viewStudentsUnassignedWithApplications());
            case 3 -> System.out.println(m.viewProposalsUnassigned());
            case 4 -> System.out.println(m.viewProposalsAssigned());
            default -> finish = true;
        }
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
                case PROPOSAL_ATTRIBUTION_PREV_CLOSED_STAGE_THREE -> stageThreePrevClosedUI();
                case PROPOSAL_ATTRIBUTION_PREV_OPEN_STAGE_THREE -> stageThreePrevOpenUI();
                case CONFLICT_STAGE -> conflictStateUI();
                case ADVISOR_ATTRIBUTION_STAGE_FOUR -> stageFourUI();
                case VIEW_DATA_STAGE_FIVE -> stageFiveUI();
            }
        }
    }
}
