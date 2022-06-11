package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.Assignment;
import pt.isec.pa.apoio_poe.model.data.DataLogic;
import pt.isec.pa.apoio_poe.model.data.Proposal;
import pt.isec.pa.apoio_poe.model.data.tiposProposta.Project;
import pt.isec.pa.apoio_poe.model.fsm.AppContext;
import pt.isec.pa.apoio_poe.model.fsm.AppState;
import pt.isec.pa.apoio_poe.model.fsm.StateAdapter;

public class StageFour extends StateAdapter {
    public StageFour(AppContext ac, DataLogic dl) {
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
    public boolean closeStage(){
        ac.setCloseStatus("Stage4", true);
        changeState(AppState.VIEW_DATA_STAGE_FIVE);
        return true;
    }

    @Override
    public boolean returnStage(){
        // in case previous stage (stage 3) is NOT closed, returns to it
        if(!ac.isStageClosed("Stage3")){
            if(!ac.isStageClosed("Stage2"))
                changeState(AppState.PROPOSAL_ATTRIBUTION_PREV_OPEN_STAGE_THREE);
            else
                changeState(AppState.PROPOSAL_ATTRIBUTION_PREV_CLOSED_STAGE_THREE);
            return true;
        }
        return false;
    }

    @Override
    public boolean automaticAssignmentAdvisors(){
        for(Proposal p : dl.getProposalsValues()){
            if(p instanceof Project){
                if(p.hasBeenAssigned()){
                    for(Assignment a : dl.getAssignmentList()){
                        if (p.equals(a.getProposal())) {
                            a.setAdvisor(((Project) p).getProposingTeacher());
                            ((Project) p).getProposingTeacher().setHasBeenAssigned(true);
                            break;
                        }
                    }
                }
                else{
                    if(p.hasAssignedStudent())
                        dl.addAssignment(new Assignment(p.getAssignedStudent(), ((Project) p).getProposingTeacher(), p));
                    else
                        dl.addAssignment(new Assignment(((Project) p).getProposingTeacher(), p));
                    p.setHasBeenAssigned(true);
                    ((Project) p).getProposingTeacher().setHasBeenAssigned(true);
                }
            }
        }
        return true;
    }

    @Override
    public String viewStudentsAssignedWithAdvisor(){
        StringBuilder sb = new StringBuilder();
        for (Assignment a : dl.getAssignmentList()){
            if(a.hasStudent() && a.hasAdvisor())
                sb.append(a.getStudent().studentToString());
        }

        return sb.toString();
    }

    @Override
    public String viewStudentsAssignedWithoutAdvisor(){
        StringBuilder sb = new StringBuilder();
        for (Assignment a : dl.getAssignmentList()){
            if(a.hasStudent() && !a.hasAdvisor())
                sb.append(a.getStudent().studentToString());
        }

        return sb.toString();
    }
}
