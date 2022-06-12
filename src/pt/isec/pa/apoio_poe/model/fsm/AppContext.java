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

    public String importCSV(String filename){ return state.importCSV(filename); }

    public String exportCSV(String filename){ return state.exportCSV(filename); }

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

    public boolean filenameIsValid(String filename) { return dl.filenameIsValid(filename); }

    public boolean emailIsValid(String email) { return dl.emailIsValid(email); }

    public boolean proposalIdIsValid(String id) { return dl.proposalIdIsValid(id); }

    public boolean automaticAssignmentAdvisors() { return state.automaticAssignmentAdvisors(); }

    public String viewStudentsAssignedWithAdvisor() { return state.viewStudentsAssignedWithAdvisor(); }
    public String viewStudentsAssignedWithoutAdvisor() { return state.viewStudentsAssignedWithoutAdvisor();}
    public String viewStudentsUnassignedWithApplications() { return state.viewStudentsUnassignedWithApplications(); }
    public String viewProposalsUnassigned() { return state.viewProposalsUnassigned(); }
    public String viewProposalsAssigned() { return state.viewProposalsAssigned(); }
}
