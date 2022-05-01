package pt.isec.pa.apoio_poe.model;

import pt.isec.pa.apoio_poe.model.data.DataLogic;
import pt.isec.pa.apoio_poe.model.fsm.AppContext;
import pt.isec.pa.apoio_poe.model.fsm.states.AppState;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Manager {
    AppContext ac;

    public Manager(){
        ac = new AppContext();
    }

    public AppState getState() {
        return ac.getState();
    }

    public String getStage(){return ac.getStage();}

    public String importProposalsCSV(String filename) { return ac.importProposalsCSV(filename); }

    public String exportProposalsCSV(String filename) { return ac.exportProposalsCSV(filename); }

    public String importStudentsCSV(String filename){
        return ac.importStudentsCSV(filename);
    }

    public String exportStudentsCSV(String filename) {
        return ac.exportStudentsCSV(filename);
    }

    public String importTeachersCSV(String filename){
        return ac.importTeachersCSV(filename);
    }

    public String exportTeachersCSV(String filename){
        return ac.exportTeachersCSV(filename);
    }

    public String importApplicationsCSV(String filename){
        return ac.importApplicationsCSV(filename);
    }

    public String exportApplicationsCSV(String filename){
        return ac.exportApplicationsCSV(filename);
    }

    public boolean changeConfigurationMode(int option){ return ac.changeConfigurationMode(option); }
    public boolean closeStage() { return ac.closeStage(); }
    public boolean advanceStage() { return ac.advanceStage(); }
    public boolean returnStage() { return ac.returnStage(); }
    public String viewStudents() { return ac.viewStudents(); }
    public String viewTeachers() { return ac.viewTeachers(); }
    public String viewProposals() { return ac.viewProposals(); }
    public String viewStudentsSelfProposals() { return ac.viewStudentsSelfProposals(); }
    public String viewStudentsWithApplication() { return ac.viewStudentsWithApplication(); }
    public String viewStudentsWithoutApplication() { return ac.viewStudentsWithoutApplication(); }
    public String[] viewAssignments() { return ac.viewAssignments(); }

    public String filterProposals(Integer... filters) { return ac.filterProposals(filters); }

    public boolean automaticAssignmentSelfProposals() { return ac.automaticAssignmentSelfProposals(); }
    public String[] getAvailableProposals() { return ac.getAvailableProposals(); }
    public String[] getAvailableStudents() { return ac.getAvailableStudents(); }
    public boolean manuallyAssign(int proposalChosen, int studentChosen, String[] availableProposals, String[] availableStudents) { return ac.manuallyAssign(proposalChosen, studentChosen, availableProposals, availableStudents); }

    public boolean removeAssignment(int assignmentToRemove) { return ac.removeAssignment(assignmentToRemove);}
    public boolean removeAllAssignments() { return ac.removeAllAssignments(); }

    public String viewStudentsAssigned() { return ac.viewStudentsAssigned(); }
    public String viewStudentsUnassigned() { return ac.viewStudentsUnassigned(); }
    public boolean automaticAssignment() { return ac.automaticAssignment();}
    public String[] getConflictedCases(){ return ac.getConflictedCases(); }
    public String getConflictedProposal() { return ac.getConflictedProposal(); }
    public boolean resolveConflictedCases(int option) { return ac.resolveConflictedCases(option); }
    public String exportStageThreeCSV(String filename) { return ac.exportStageThreeCSV(filename); }

    public boolean automaticAssignmentAdvisors() { return ac.automaticAssignmentAdvisors(); }

    public void save(String filename){
        try(ObjectOutputStream oos =
                    new ObjectOutputStream(
                            new FileOutputStream(filename)))
        {
            oos.writeObject(ac);
        } catch (Exception e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }

    public void loadAppContext(String filename){
        AppContext appContext = load(filename);
        if(appContext!=null) {
            ac = load(filename);
            ac.changeState(AppState.values()[ac.getCurrentState()].createState(ac, ac.getDl()));
        }
    }

    private AppContext load(String filename) {
        try(ObjectInputStream ois =
                    new ObjectInputStream(
                            new FileInputStream(filename)))
        {
            return (AppContext) ois.readObject();
        } catch (Exception e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
        return null;
    }
}
