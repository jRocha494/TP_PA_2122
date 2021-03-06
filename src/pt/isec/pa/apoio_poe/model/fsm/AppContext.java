package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.*;
import pt.isec.pa.apoio_poe.model.fsm.states.StageOne;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<Application> getApplications(){return dl.getApplications();}
    public List<Assignment> getAssignments(){return dl.getAssignments();}
    public List<Student> getStudentsSelfProposals(){return dl.getStudentsSelfProposals();}
    public List<Student> getStudentsWithApplication(){return dl.getStudentsWithApplication();}
    public List<Student> getStudentsWithoutApplication(){return dl.getStudentsWithoutApplication();}
    public List<Proposal> getProposalsWithFiltersStageTwo(){return dl.getProposalsWithFiltersStageTwo();}
    public List<Proposal> getProposalsWithFiltersStageThree(){return dl.getProposalsWithFiltersStageThree();}
    public String[] getStudentsAssigned(){return dl.getStudentsAssigned();}
    public String[] getListStudentsUnassigned(){return dl.getListStudentsUnassigned();}
    public List<Student> getStudentsAssignedWithAdvisor(){return dl.getStudentsAssignedWithAdvisor();}
    public List<Student> getStudentsAssignedWithoutAdvisor(){return dl.getStudentsAssignedWithoutAdvisor();}

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

    public boolean boolImportCSV(String filename){ return state.boolImportCSV(filename); }

    public boolean boolExportCSV(String filename){ return state.boolExportCSV(filename); }

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
    public List<Proposal> getAvailableProposalsList() { return dl.getAvailableProposalsList(); }
    public String[] getAvailableProposals() { return dl.getAvailableProposals(); }
    public String[] getAvailableStudents() { return dl.getAvailableStudents(); }
    public List<Student> getAvailableStudentsWithoutApplication() { return dl.getAvailableStudentsWithoutApplication(); }
    public boolean manuallyAssign(String student, String proposal){ return state.manuallyAssign(student, proposal); }
    public boolean manuallyAssign(int proposalChosen, int studentChosen, String[] availableProposals, String[] availableStudents) { return state.manuallyAssign(proposalChosen, studentChosen, availableProposals, availableStudents); }
    public boolean removeAssignment(int assignmentToRemove) { return state.removeAssignment(assignmentToRemove); }
    public boolean removeAllAssignments() { return state.removeAllAssignments(); }
    public String viewStudentsUnassigned() { return state.viewStudentsUnassigned(); }
    public String getAvarageAssigments() { return dl.getAvarageAssigments(); }
    public String getMinimumAssignments() { return dl.getMinimumAssignments(); }
    public String getMaximumAssignments() { return dl.getMaximumAssignments(); }

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

    public boolean add(Object ... parameters) {return state.add(parameters);}

    public void setFiltersStageTwo(boolean[] filters) { dl.setFiltersStageTwo(filters); }

    public void setFiltersStageThree(boolean[] filters) { dl.setFiltersStageThree(filters); }

    public boolean delete(Object selectedItem) {return state.delete(selectedItem);}

    public boolean update(Object ... parameters) {return state.update(parameters);}

    public String[] getBranches() {return dl.getBranches();}

    public List<Student> getStudentsForInternships() {return dl.getStudentsForInternships();}
    public List<Student> getStudentsUnassigned() {return dl.getStudentsUnassigned();}
    public List<Student> getStudentsWithoutProposal() {return dl.getStudentsWithoutProposal();}

    public double getNmrProposalsByBranch(String branch) {return dl.getNmrProposalsByBranch(branch);}

    public double getNmrProposalsAssigned() {return dl.getNmrProposalsAssigned();}
    public double getNmrProposalsUnassigned() {return dl.getNmrProposalsUnassigned();}
}
