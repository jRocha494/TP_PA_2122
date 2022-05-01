package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.*;
import pt.isec.pa.apoio_poe.model.fsm.AppContext;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ConflictStage extends StateAdapter {
    List<Student> students;
    Proposal proposal;

    public ConflictStage(AppContext ac, DataLogic dl, List<Student> students, Proposal proposal) {
        super(ac, dl);
        this.students = students;
        this.proposal = proposal;
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

        for(Student s : students)
            studentsToString.add(s.studentToString());

        return studentsToString.toArray(new String[studentsToString.size()]);
    }

    @Override
    public String getConflictedProposal() { return proposal.proposalToString(); }

    @Override
    public boolean resolveConflictedCases(int option) {
        dl.addAttribution(new Attribution(students.get(option-1),proposal));
        students.get(option-1).setHasBeenAssigned(true);
        proposal.setHasBeenAssigned(students.get(option-1), true);
        changeState(AppState.PROPOSAL_ATTRIBUTION_PREV_CLOSED_STAGE_THREE);
        return true;
    }


}
