package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.DataLogic;
import pt.isec.pa.apoio_poe.model.fsm.AppContext;
import pt.isec.pa.apoio_poe.model.fsm.states.IState;
import pt.isec.pa.apoio_poe.model.fsm.states.StageOne;

public enum AppState {
    CONFIGURATIONS_STATE_STAGE_ONE, CONFIGURATIONS_STATE_PROPOSAL_MANAGER, CONFIGURATIONS_STATE_STUDENT_MANAGER,
    CONFIGURATIONS_STATE_TEACHER_MANAGER, CLOSED_STAGE, CANDIDATURE_OPTIONS_STAGE_TWO,
    PROPOSAL_ATTRIBUTION_STAGE_THREE, CONFLICT_STATE, ADVISOR_ATTRIBUTION_STAGE_FOUR, VIEW_DATA_STAGE_FIVE;

    IState createState(AppContext ac, DataLogic dl) {
        return switch (this){
            case CONFIGURATIONS_STATE_STAGE_ONE -> new StageOne(ac, dl);
            case CONFIGURATIONS_STATE_PROPOSAL_MANAGER -> new ProposalManager(ac, dl);
            case CONFIGURATIONS_STATE_STUDENT_MANAGER -> new StudentManager(ac, dl);
            case CONFIGURATIONS_STATE_TEACHER_MANAGER -> new TeacherManager(ac, dl);
            case CLOSED_STAGE -> null;
            case CANDIDATURE_OPTIONS_STAGE_TWO -> null;
            case PROPOSAL_ATTRIBUTION_STAGE_THREE -> null;
            case CONFLICT_STATE -> null;
            case ADVISOR_ATTRIBUTION_STAGE_FOUR -> null;
            case VIEW_DATA_STAGE_FIVE -> null;
        };
    }
}
