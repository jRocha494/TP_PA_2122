package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.DataLogic;
import pt.isec.pa.apoio_poe.model.fsm.AppContext;

public class ProposalManager extends StateAdapter{
    public ProposalManager(AppContext ac, DataLogic dl) { super(ac, dl); }

    @Override
    public String importProposalsCSV(String filename) {
        StringBuilder sb = new StringBuilder();

        return sb.toString();
    }

    @Override
    public String exportProposalsCSV(String filename) {
        StringBuilder sb = new StringBuilder();

        return sb.toString();
    }

    @Override
    public AppState getState() {
        return AppState.CONFIGURATIONS_STATE_PROPOSAL_MANAGER;
    }

    @Override
    public String getStage() {
        return "First Stage - Configurations";
    }
}
