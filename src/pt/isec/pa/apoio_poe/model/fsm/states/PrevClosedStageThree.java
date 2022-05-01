package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.Attribution;
import pt.isec.pa.apoio_poe.model.data.DataLogic;
import pt.isec.pa.apoio_poe.model.data.Proposal;
import pt.isec.pa.apoio_poe.model.data.tiposProposta.Project;
import pt.isec.pa.apoio_poe.model.fsm.AppContext;

public class PrevClosedStageThree extends StateAdapter{
    protected PrevClosedStageThree(AppContext ac, DataLogic dl) {
        super(ac, dl);
    }

    @Override
    public AppState getState() { return AppState.PROPOSAL_ATTRIBUTION_PREV_CLOSED_STAGE_THREE; }

    @Override
    public String getStage() { return "Third Stage - Previous Stage Closed"; }

    @Override
    public boolean automaticAttributionSelfProposals() {
        //TODO: Adicionar flag que já foi atribuída às propostas? Senão no stage3 pode ser atribuida a um estudante,
        // e depois se for feita a atribuição das que têm aluno associado, volta a adicionar novamente...
        for(Proposal p : dl.getProposalsValues()){
            if(p.hasAssignedStudent())
                dl.addAttribution(new Attribution(p.getAssignedStudent(), p));
        }
        return true;
    }
}
