package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.DataLogic;
import pt.isec.pa.apoio_poe.model.fsm.AppContext;

public class StageFour extends StateAdapter{
    protected StageFour(AppContext ac, DataLogic dl) {
        super(ac, dl);
    }

    @Override
    public AppState getState() {
        return AppState.ADVISOR_ATTRIBUTION_STAGE_FOUR;
    }

    @Override
    public String getStage() {
        return "Fourth Stage - Advisor assignment";
    }

    @Override
    public boolean automaticAssignmentAdvisors(){
        return false;
    }
}
