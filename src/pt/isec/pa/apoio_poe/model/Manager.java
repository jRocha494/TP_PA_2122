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

    public String importStudentsCSV(String filename){
        return ac.importStudentsCSV(filename);
    }

    public String importTeachersCSV(String filename){
        return ac.importTeachersCSV(filename);
    }

    public String exportTeachersCSV(String filename){
        return ac.exportTeachersCSV(filename);
    }
}
