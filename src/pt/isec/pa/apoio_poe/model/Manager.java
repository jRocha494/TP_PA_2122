package pt.isec.pa.apoio_poe.model;

import pt.isec.pa.apoio_poe.model.fsm.AppContext;
import pt.isec.pa.apoio_poe.model.fsm.states.AppState;

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

    public String filterProposals(Integer... filters) { return ac.filterProposals(filters); }

    public boolean automaticAttributionSelfProposals() { return ac.automaticAttributionSelfProposals(); }
}
