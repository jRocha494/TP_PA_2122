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

    public boolean exportProposalsCSV(String readString) {
        return false;
    }

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

    public boolean changeConfigurationMode(int option){ return ac.changeConfigurationMode(option); }
    public boolean closeStage() { return ac.closeStage(); }
    public boolean advanceStage() { return ac.advanceStage(); }

}
