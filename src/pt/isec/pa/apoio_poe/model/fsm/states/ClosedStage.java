package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.DataLogic;
import pt.isec.pa.apoio_poe.model.data.Proposal;
import pt.isec.pa.apoio_poe.model.data.Student;
import pt.isec.pa.apoio_poe.model.data.Teacher;
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


    @Override
    public String viewStudents() {
        StringBuilder sb = new StringBuilder();
        for (Student s : dl.getStudentsValues()) {
            sb.append(s.studentToString());
        }
        return sb.toString();
    }
    @Override
    public String viewTeachers() {
        StringBuilder sb = new StringBuilder();
        for (Teacher t : dl.getTeachersValues()) {
            sb.append(t.teacherToString());
        }
        return sb.toString();
    }
    @Override
    public String viewProposals() {
        StringBuilder sb = new StringBuilder();
        for (Proposal p : dl.getProposalsValues()) {
            sb.append(p.proposalToString());
        }
        return sb.toString();
    }
}
