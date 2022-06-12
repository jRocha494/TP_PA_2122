package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.DataLogic;
import pt.isec.pa.apoio_poe.model.data.Proposal;
import pt.isec.pa.apoio_poe.model.data.Student;
import pt.isec.pa.apoio_poe.model.data.Teacher;
import pt.isec.pa.apoio_poe.model.fsm.states.StageOne;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class AppContext implements Serializable {
    static final long serialVersionUID = 100L;
    private int currentState;
    private transient IState state;
    private DataLogic dl;
    private Map<String, Boolean> closeStatusStages; // Used to manage whether a certain stage has already been closed or not

    public AppContext(DataLogic dl) {
        this.dl = dl;
        this.closeStatusStages = new HashMap<>();
        setup();
        this.state = new StageOne(this, dl);
    }

    private void setup(){
        this.closeStatusStages.put("Stage1", false);
        this.closeStatusStages.put("Stage2", false);
        this.closeStatusStages.put("Stage3", false);
        this.closeStatusStages.put("Stage4", false);
    }

    public void setCloseStatus(String stage, boolean status){   // Receives the name of the stage, and sets its close status if it finds the stage
        closeStatusStages.computeIfPresent(stage, (k, v) -> v = status);
    }

    public List<Student> getStudents(){return dl.getStudents();}
    public List<Teacher> getTeachers(){return dl.getTeachers();}
    public List<Proposal> getProposals(){return dl.getProposals();}

    public void setCurrentState(int ordinal) { this.currentState = ordinal; }

    public int getCurrentState() { return currentState; }

    public boolean isStageClosed(String stage){    // Receives the name of the stage, and returns its close status
        return closeStatusStages.get(stage);
    }

    public AppState getState() { return state.getState(); }

    public String getStage(){return state.getStage();}

    public DataLogic getDl() {
        return dl;
    }

    public String importProposalsCSV(String filename){ return state.importProposalsCSV(filename); }

    public String exportProposalsCSV(String filename){ return state.exportProposalsCSV(filename); }

    public String importStudentsCSV(String filename){ return state.importStudentsCSV(filename); }

    public String exportStudentsCSV(String filename){ return state.exportStudentsCSV(filename); }

    public String importTeachersCSV(String filename){ return state.importTeachersCSV(filename); }

    public String exportTeachersCSV(String filename){ return state.exportTeachersCSV(filename); }

    public String importApplicationsCSV(String filename){ return state.importApplicationsCSV(filename); }

    public String exportApplicationsCSV(String filename){ return state.exportApplicationsCSV(filename); }

    public void changeState(IState newState) { this.state = newState; }

    public boolean changeConfigurationMode(int option){ return state.changeConfigurationMode(option); }
    public boolean closeStage() { return state.closeStage(); }
    public boolean advanceStage() { return state.advanceStage(); }
    public boolean returnStage() { return state.returnStage(); }
    public String viewStudents() { return state.viewStudents(); }
    public String viewTeachers() { return state.viewTeachers(); }
    public String viewProposals() { return state.viewProposals(); }
    public String viewStudentsSelfProposals() { return state.viewStudentsSelfProposals(); }
    public String viewStudentsWithApplication() { return state.viewStudentsWithApplication(); }
    public String viewStudentsWithoutApplication() { return state.viewStudentsWithoutApplication(); }
    public String[] viewAssignments() { return dl.viewAssignments(); }

    public String filterProposals(Integer... filters) { return state.filterProposals(filters);}

    public boolean automaticAssignmentSelfProposals() { return state.automaticAssignmentSelfProposals(); }
    public String[] getAvailableProposals() { return dl.getAvailableProposals(); }
    public String[] getAvailableStudents() { return dl.getAvailableStudents(); }
    public boolean manuallyAssign(int proposalChosen, int studentChosen, String[] availableProposals, String[] availableStudents) { return state.manuallyAssign(proposalChosen, studentChosen, availableProposals, availableStudents); }
    public boolean removeAssignment(int assignmentToRemove) { return state.removeAssignment(assignmentToRemove); }
    public boolean removeAllAssignments() { return state.removeAllAssignments(); }
    public String viewStudentsUnassigned() { return state.viewStudentsUnassigned(); }

    public String viewStudentsAssigned() { return state.viewStudentsAssigned(); }

    public boolean automaticAssignment() { return state.automaticAssignment();}

    public String[] getConflictedCases(){ return state.getConflictedCases(); }

    public String getConflictedProposal() { return state.getConflictedProposal(); }

    public boolean resolveConflictedCases(int option) { return state.resolveConflictedCases(option); }

    public boolean filenameIsValid(String filename) {
        String[] fn = filename.split("\\.");

        if(fn.length > 1)
            return false;

        return true;
    }

    public boolean emailIsValid(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(regex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public boolean proposalIdIsValid(String id) {
        String regex = "[a-zA-Z]\\d\\d\\d";

        Pattern pat = Pattern.compile(regex);
        if (regex == null)
            return false;
        return pat.matcher(id).matches();
    }

    public String exportStageThreeCSV(String filename) { return state.exportStageThreeCSV(filename); }

    public boolean automaticAssignmentAdvisors() { return state.automaticAssignmentAdvisors(); }

    public String viewStudentsAssignedWithAdvisor() { return state.viewStudentsAssignedWithAdvisor(); }
    public String viewStudentsAssignedWithoutAdvisor() { return state.viewStudentsAssignedWithoutAdvisor();}
    public String viewStudentsUnassignedWithApplications() { return state.viewStudentsUnassignedWithApplications(); }
    public String viewProposalsUnassigned() { return state.viewProposalsUnassigned(); }
    public String viewProposalsAssigned() { return state.viewProposalsAssigned(); }
}
