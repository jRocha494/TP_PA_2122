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

    public boolean importProposalsCSV(String readString) {
        return false;
    }

    public boolean exportProposalsCSV(String readString) {
        return false;
    }

    public String importStudentsCSV(String filename){
        return ac.importStudentsCSV(filename);
    }

    public boolean exportStudentsCSV(String readString) {
        return false;
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
