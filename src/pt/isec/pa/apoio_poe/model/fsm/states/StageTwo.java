package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.DataLogic;
import pt.isec.pa.apoio_poe.model.fsm.AppContext;

public class StageTwo extends StateAdapter{
    protected StageTwo(AppContext ac, DataLogic dl) {
        super(ac, dl);
        ac.setCloseStatus("Stage2", false);
    }

    @Override
    public AppState getState() { return AppState.APPLICATION_OPTIONS_STAGE_TWO; }

    @Override
    public String getStage() { return "Second Stage - Application Options"; }

    @Override
    public boolean closeStage(){
        // in case previous stage (stage 1) is closed... closes this stage
        if(ac.isStageClosed("Stage1")){
            ac.setCloseStatus("Stage2", true);
            changeState(AppState.PROPOSAL_ATTRIBUTION_STAGE_THREE);
            return true;
        }
        return false;
    }

    @Override
    public boolean returnStage(){
        // in case previous stage (stage 1) is NOT closed, returns to it
        if(!ac.isStageClosed("Stage1")){
            changeState(AppState.CONFIGURATIONS_STATE_STAGE_ONE);
            return true;
        }
        return false;
    }

    @Override
    public boolean advanceStage(){
        changeState(AppState.PROPOSAL_ATTRIBUTION_STAGE_THREE);
        return true;
    }
}
