package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.DataLogic;
import pt.isec.pa.apoio_poe.model.fsm.AppContext;

public class ClosedStage extends StateAdapter{
    protected ClosedStage(AppContext ac, DataLogic dl) {
        super(ac, dl);
    }

    @Override
    public boolean advanceStage() {
        changeState(AppState.APPLICATION_OPTIONS_STAGE_TWO);
        return true;
    }

    @Override
    public AppState getState() { return AppState.CLOSED_STAGE; }

    @Override
    public String getStage() { return "Closed Stage"; }
}
