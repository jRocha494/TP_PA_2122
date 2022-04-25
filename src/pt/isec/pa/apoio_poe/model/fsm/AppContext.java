package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.DataLogic;
import pt.isec.pa.apoio_poe.model.fsm.states.AppState;
import pt.isec.pa.apoio_poe.model.fsm.states.IState;
import pt.isec.pa.apoio_poe.model.fsm.states.StageOne;

public class AppContext {
    private IState state;
    private DataLogic dl;

    public AppContext() {
        dl = new DataLogic();
        state = new StageOne(this, dl);
    }

    public AppState getState() {
        return state.getState();
    }

    public String getStage(){return state.getStage();}

    public void changeState(IState newState) {
        this.state = newState;
    }

    public String importTeachersCSV(String filename){
        return state.importTeachersCSV(filename);
    }
}
