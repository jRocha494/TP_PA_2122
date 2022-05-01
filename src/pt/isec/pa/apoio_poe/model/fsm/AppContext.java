package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.DataLogic;
import pt.isec.pa.apoio_poe.model.fsm.states.AppState;
import pt.isec.pa.apoio_poe.model.fsm.states.IState;
import pt.isec.pa.apoio_poe.model.fsm.states.StageOne;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class AppContext {
    private IState state;
    private DataLogic dl;
    private Map<String, Boolean> closeStatusStages; // Used to manage whether a certain stage has already been closed or not

    public AppContext() {
        this.dl = new DataLogic();
        this.closeStatusStages = new HashMap<>();
        setup();
        this.state = new StageOne(this, dl);
    }

    private void setup(){
        this.closeStatusStages.put("Stage1", false);
        this.closeStatusStages.put("Stage2", false);
        this.closeStatusStages.put("Stage3", false);
        this.closeStatusStages.put("Stage4", false);
    }

    public void setCloseStatus(String stage, boolean status){   // Receives the name of the stage, and sets its close status if it finds the stage
        closeStatusStages.computeIfPresent(stage, (k, v) -> v = status);
    }

    public boolean isStageClosed(String stage){    // Receives the name of the stage, and returns its close status
        return closeStatusStages.get(stage);
    }

    public AppState getState() { return state.getState(); }

    public String getStage(){return state.getStage();}

    public String importProposalsCSV(String filename){ return state.importProposalsCSV(filename); }

    public String exportProposalsCSV(String filename){ return state.exportProposalsCSV(filename); }

    public String importStudentsCSV(String filename){ return state.importStudentsCSV(filename); }

    public String exportStudentsCSV(String filename){ return state.exportStudentsCSV(filename); }

    public String importTeachersCSV(String filename){ return state.importTeachersCSV(filename); }

    public String exportTeachersCSV(String filename){ return state.exportTeachersCSV(filename); }

    public String importApplicationsCSV(String filename){ return state.importApplicationsCSV(filename); }

    public String exportApplicationsCSV(String filename){ return state.exportApplicationsCSV(filename); }

    public void changeState(IState newState) { this.state = newState; }

    public boolean changeConfigurationMode(int option){ return state.changeConfigurationMode(option); }
    public boolean closeStage() { return state.closeStage(); }
    public boolean advanceStage() { return state.advanceStage(); }
    public boolean returnStage() { return state.returnStage(); }
    public String viewStudents() { return dl.viewStudents(); }
    public String viewTeachers() { return dl.viewTeachers(); }
    public String viewProposals() { return dl.viewProposals(); }
    public String viewStudentsSelfProposals() { return dl.viewStudentsSelfProposals(); }
    public String viewStudentsWithApplication() { return dl.viewStudentsWithApplication(); }
    public String viewStudentsWithoutApplication() { return dl.viewStudentsWithoutApplication(); }

    public String filterProposals(Integer... filters) { return dl.filterProposals(filters);}

    public boolean automaticAttributionSelfProposals() { return state.automaticAttributionSelfProposals(); }

    public boolean automaticAttributionsNotAssigned() { return state.automaticAttributionsNotAssigned();}

    public String[] getConflictedCases(){ return state.getConflictedCases(); }

    public String getConflictedProposal() { return state.getConflictedProposal(); }

    public boolean resolveConflictedCases(int option) { return state.resolveConflictedCases(option); }

    public boolean filenameIsValid(String filename) {
        String[] fn = filename.split("\\.");

        if(fn.length > 1)
            return false;

        return true;
    }

    public boolean emailIsValid(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(regex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public boolean proposalIdIsValid(String id) {
        String regex = "[a-zA-Z]\\d\\d\\d";

        Pattern pat = Pattern.compile(regex);
        if (regex == null)
            return false;
        return pat.matcher(id).matches();
    }

    public String exportStageThreeCSV(String filename) { return state.exportStageThreeCSV(filename); }
}
