package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.DataLogic;
import pt.isec.pa.apoio_poe.model.data.Proposal;
import pt.isec.pa.apoio_poe.model.data.Student;
import pt.isec.pa.apoio_poe.model.data.tiposProposta.SelfProposal;
import pt.isec.pa.apoio_poe.model.fsm.AppContext;

public class StageFive extends StateAdapter{
    protected StageFive(AppContext ac, DataLogic dl) {
        super(ac, dl);
    }

    @Override
    public AppState getState() {
        return AppState.VIEW_DATA_STAGE_FIVE;
    }

    @Override
    public String getStage() {
        return "Fifth Stage - View Data";
    }

    @Override
    public String viewStudentsAssigned(){
        StringBuilder sb = new StringBuilder();
        for (Student s : dl.getStudentsValues()) {
            if(s.hasBeenAssigned()) {
                sb.append(s.studentToString());
            }
        }
        return sb.toString();
    }

    @Override
    public String viewStudentsUnassignedWithApplications(){
        StringBuilder sb = new StringBuilder();
        for (Student s : dl.getStudentsValues()) {
            if(!s.hasBeenAssigned() && s.hasApplication()) {
                sb.append(s.studentToString());
            }
        }
        return sb.toString();
    }

    @Override
    public String viewProposalsUnassigned(){
        StringBuilder sb = new StringBuilder();
        for (Proposal p : dl.getProposalsValues()) {
            if(!p.hasBeenAssigned())
                sb.append(p.proposalToString());
        }
        return sb.toString();
    }
    @Override
    public String viewProposalsAssigned(){
        StringBuilder sb = new StringBuilder();
        for (Proposal p : dl.getProposalsValues()) {
            if(p.hasBeenAssigned())
                sb.append(p.proposalToString());
        }
        return sb.toString();
    }
}
