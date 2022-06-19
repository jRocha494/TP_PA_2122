package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.*;
import pt.isec.pa.apoio_poe.model.fsm.AppContext;
import pt.isec.pa.apoio_poe.model.fsm.AppState;
import pt.isec.pa.apoio_poe.model.fsm.StateAdapter;

import java.util.ArrayList;
import java.util.List;

public class ConflictStage extends StateAdapter {

    public ConflictStage(AppContext ac, DataLogic dl, List<Student> students, Proposal proposal) {
        super(ac, dl);
        dl.setConflictStudents(students);
        dl.setConflictProposal(proposal);
    }

    @Override
    public AppState getState() {
        return AppState.CONFLICT_STAGE;
    }

    @Override
    public String getStage() {
        return "Third Stage - Previous Stage Closed";
    }

    @Override
    public String[] getConflictedCases(){
        List<String> studentsToString = new ArrayList<>();

        for(Student s : dl.getConflictStudents())
            studentsToString.add(s.studentToString());

        return studentsToString.toArray(new String[studentsToString.size()]);
    }

    @Override
    public String getConflictedProposal() { return dl.getConflictProposal().proposalToString(); }

    @Override
    public boolean resolveConflictedCases(int option) {
        dl.addAssignment(new Assignment(dl.getConflictStudents().get(option-1),dl.getConflictProposal()));
        dl.getConflictStudents().get(option-1).setHasBeenAssigned(true);
        dl.getConflictProposal().setHasBeenAssigned(dl.getConflictStudents().get(option-1), true);
        //changeState(AppState.PROPOSAL_ATTRIBUTION_PREV_CLOSED_STAGE_THREE);
        changeState(AppState.PROPOSAL_ATTRIBUTION_STAGE_THREE);
        return true;
    }


}
