package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.DataLogic;
import pt.isec.pa.apoio_poe.model.fsm.AppContext;

public enum AppState {
    CONFIGURATIONS_STATE_STAGE_ONE, CONFIGURATIONS_STATE_PROPOSAL_MANAGER, CONFIGURATIONS_STATE_STUDENT_MANAGER,
    CONFIGURATIONS_STATE_TEACHER_MANAGER, CLOSED_STAGE, APPLICATION_OPTIONS_STAGE_TWO,
    PROPOSAL_ATTRIBUTION_PREV_OPEN_STAGE_THREE, PROPOSAL_ATTRIBUTION_PREV_CLOSED_STAGE_THREE, CONFLICT_STATE, ADVISOR_ATTRIBUTION_STAGE_FOUR, VIEW_DATA_STAGE_FIVE;

    IState createState(AppContext ac, DataLogic dl) {
        return switch (this){
            case CONFIGURATIONS_STATE_STAGE_ONE -> new StageOne(ac, dl);
            case CONFIGURATIONS_STATE_PROPOSAL_MANAGER -> new ProposalMode(ac, dl);
            case CONFIGURATIONS_STATE_STUDENT_MANAGER -> new StudentMode(ac, dl);
            case CONFIGURATIONS_STATE_TEACHER_MANAGER -> new TeacherMode(ac, dl);
            case CLOSED_STAGE -> new ClosedStage(ac, dl);
            case APPLICATION_OPTIONS_STAGE_TWO -> new StageTwo(ac, dl);
            case PROPOSAL_ATTRIBUTION_PREV_OPEN_STAGE_THREE -> null;
            case PROPOSAL_ATTRIBUTION_PREV_CLOSED_STAGE_THREE -> null;
            case CONFLICT_STATE -> null;
            case ADVISOR_ATTRIBUTION_STAGE_FOUR -> null;
            case VIEW_DATA_STAGE_FIVE -> null;
        };
    }
}
