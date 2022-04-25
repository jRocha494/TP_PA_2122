package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.DataLogic;
import pt.isec.pa.apoio_poe.model.fsm.AppContext;

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
    public boolean changeConfigurationMode(int option){ return false; }
    @Override
    public boolean closeStage(){ return false; }
    @Override
    public boolean advanceStage(){ return false; }
}
