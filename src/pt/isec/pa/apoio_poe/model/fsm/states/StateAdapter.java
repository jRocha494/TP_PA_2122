package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.DataLogic;
import pt.isec.pa.apoio_poe.model.fsm.AppContext;

import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class StateAdapter implements IState{
    protected AppContext ac;
    protected DataLogic dl;

    protected StateAdapter(AppContext ac, DataLogic dl) {
        this.ac = ac;
        this.dl = dl;
    }

    protected void changeState(AppState newState){
        ac.changeState(newState.createState(ac,dl));
    }

    @Override
    public String importProposalsCSV(String filename) { return ""; }

    @Override
    public String exportProposalsCSV(String filename) { return ""; }

    @Override
    public String importStudentsCSV(String filename) {
        return "";
    }

    @Override
    public String exportStudentsCSV(String filename) {
        return "";
    }

    @Override
    public String importTeachersCSV(String filename) { return ""; }

    @Override
    public String exportTeachersCSV(String filename) { return ""; }
}
