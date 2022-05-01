package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.DataLogic;
import pt.isec.pa.apoio_poe.model.data.Proposal;
import pt.isec.pa.apoio_poe.model.data.Student;
import pt.isec.pa.apoio_poe.model.fsm.AppContext;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public abstract class StateAdapter implements IState {
    protected AppContext ac;
    protected DataLogic dl;

    protected StateAdapter(AppContext ac, DataLogic dl) {
        this.ac = ac;
        this.dl = dl;
    }

    protected void changeState(AppState newState) {
        ac.changeState(newState.createState(ac, dl));
    }

    protected void changeState(AppState newState, List<Student> students, Proposal proposal){
        ac.changeState(newState.createState(ac,dl,students,proposal));
    }

    @Override
    public String importProposalsCSV(String filename) {
        return "";
    }

    @Override
    public String exportProposalsCSV(String filename) {
        return "";
    }

    @Override
    public String importStudentsCSV(String filename) {
        return "";
    }

    @Override
    public String exportStudentsCSV(String filename) {
        return "";
    }

    @Override
    public String importTeachersCSV(String filename) {
        return "";
    }

    @Override
    public String exportTeachersCSV(String filename) {
        return "";
    }

    @Override
    public String importApplicationsCSV(String filename) {
        return "";
    }

    @Override
    public String exportApplicationsCSV(String filename) {
        return "";
    }

    @Override
    public boolean changeConfigurationMode(int option) {
        return false;
    }

    @Override
    public boolean closeStage() {
        return false;
    }

    @Override
    public boolean advanceStage() {
        return false;
    }

    @Override
    public boolean returnStage() {
        return false;
    }

    @Override
    public boolean automaticAssignment() { return false; }

    @Override
    public String[] getConflictedCases(){ return new String[0]; }

    @Override
    public String getConflictedProposal() { return ""; }

    @Override
    public boolean resolveConflictedCases(int option) { return false; }

    @Override
    public String exportStageThreeCSV(String filename){ return ""; }
    public String viewStudents() {
        return "";
    }

    @Override
    public String viewTeachers() {
        return "";
    }

    @Override
    public String viewProposals() {
        return "";
    }

    @Override
    public String viewStudentsSelfProposals() {
        StringBuilder sb = new StringBuilder();
        for (Student s : dl.getStudentsValues()) {
            if(s.hasProposed())
                sb.append(s.studentToString());
        }
        return sb.toString();
    }

    @Override
    public String viewStudentsWithApplication() {
        StringBuilder sb = new StringBuilder();
        for (Student s : dl.getStudentsValues()) {
            if(s.hasApplication())
                sb.append(s.studentToString());
        }
        return sb.toString();
    }

    @Override
    public String viewStudentsWithoutApplication() {
        return "";
    }

    @Override
    public String filterProposals(Integer[] filters) {
        return "";
    }

    @Override
    public boolean automaticAssignmentSelfProposals() {
        return false;
    }

    @Override
    public boolean manuallyAssign(int proposalChosen, int studentChosen, String[] availableProposals, String[] availableStudents) {
        return false;
    }

    @Override
    public boolean removeAssignment(int assignmentToRemove) {
        return false;
    }

    @Override
    public boolean removeAllAssignments() {
        return false;
    }

    @Override
    public String viewStudentsAssigned(){ return ""; }
    @Override
    public String viewStudentsUnassigned(){ return ""; }

    @Override
    public boolean automaticAssignmentAdvisors(){ return false; }

    @Override
    public String viewStudentsAssignedWithAdvisor(){return "";}

    @Override
    public String viewStudentsAssignedWithoutAdvisor(){return "";}

    @Override
    public String viewStudentsUnassignedWithApplications(){return "";}

    @Override
    public String viewProposalsUnassigned(){return "";}
    @Override
    public String viewProposalsAssigned(){return "";}
}