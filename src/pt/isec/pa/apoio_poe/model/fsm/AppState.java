package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.DataLogic;
import pt.isec.pa.apoio_poe.model.data.Proposal;
import pt.isec.pa.apoio_poe.model.data.Student;
import pt.isec.pa.apoio_poe.model.fsm.AppContext;
import pt.isec.pa.apoio_poe.model.fsm.IState;
import pt.isec.pa.apoio_poe.model.fsm.states.ClosedStage;
import pt.isec.pa.apoio_poe.model.fsm.states.*;

import java.util.List;

public enum AppState {
    CONFIGURATIONS_STATE_STAGE_ONE, CONFIGURATIONS_STATE_PROPOSAL_MANAGER, CONFIGURATIONS_STATE_STUDENT_MANAGER,
    CONFIGURATIONS_STATE_TEACHER_MANAGER, CLOSED_STAGE, APPLICATION_OPTIONS_STAGE_TWO,
    PROPOSAL_ATTRIBUTION_PREV_OPEN_STAGE_THREE, PROPOSAL_ATTRIBUTION_PREV_CLOSED_STAGE_THREE, CONFLICT_STAGE, ADVISOR_ATTRIBUTION_STAGE_FOUR, VIEW_DATA_STAGE_FIVE;

    public IState createState(AppContext ac, DataLogic dl) {
        return switch (this){
            case CONFIGURATIONS_STATE_STAGE_ONE -> new StageOne(ac, dl);
            case CONFIGURATIONS_STATE_PROPOSAL_MANAGER -> new ProposalMode(ac, dl);
            case CONFIGURATIONS_STATE_STUDENT_MANAGER -> new StudentMode(ac, dl);
            case CONFIGURATIONS_STATE_TEACHER_MANAGER -> new TeacherMode(ac, dl);
            case CLOSED_STAGE -> new ClosedStage(ac, dl);
            case APPLICATION_OPTIONS_STAGE_TWO -> new StageTwo(ac, dl);
            case PROPOSAL_ATTRIBUTION_PREV_OPEN_STAGE_THREE -> new PrevOpenStageThree(ac,dl);
            case PROPOSAL_ATTRIBUTION_PREV_CLOSED_STAGE_THREE -> new PrevClosedStageThree(ac,dl);
            case CONFLICT_STAGE -> null;
            case ADVISOR_ATTRIBUTION_STAGE_FOUR -> new StageFour(ac, dl);
            case VIEW_DATA_STAGE_FIVE -> new StageFive(ac, dl);
        };
    }

    public IState createState(AppContext ac, DataLogic dl, List<Student> students, Proposal proposal) {
        return switch (this){
            case CONFIGURATIONS_STATE_STAGE_ONE -> new StageOne(ac, dl);
            case CONFIGURATIONS_STATE_PROPOSAL_MANAGER -> new ProposalMode(ac, dl);
            case CONFIGURATIONS_STATE_STUDENT_MANAGER -> new StudentMode(ac, dl);
            case CONFIGURATIONS_STATE_TEACHER_MANAGER -> new TeacherMode(ac, dl);
            case CLOSED_STAGE -> new ClosedStage(ac, dl);
            case APPLICATION_OPTIONS_STAGE_TWO -> new StageTwo(ac, dl);
            case PROPOSAL_ATTRIBUTION_PREV_OPEN_STAGE_THREE -> new PrevOpenStageThree(ac,dl);
            case PROPOSAL_ATTRIBUTION_PREV_CLOSED_STAGE_THREE -> new PrevClosedStageThree(ac,dl);
            case CONFLICT_STAGE -> new ConflictStage(ac,dl,students,proposal);
            case ADVISOR_ATTRIBUTION_STAGE_FOUR -> new StageFour(ac, dl);
            case VIEW_DATA_STAGE_FIVE -> new StageFive(ac, dl);
        };
    }
}
