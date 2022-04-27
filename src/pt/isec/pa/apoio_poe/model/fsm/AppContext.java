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
        this.state = new StageOne(this, dl);
        this.closeStatusStages = new HashMap<>();
        setup();
    }

    private void setup(){
        closeStatusStages.put("Stage1", false);
        closeStatusStages.put("Stage2", false);
        closeStatusStages.put("Stage3", false);
        closeStatusStages.put("Stage4", false);
    }

    public void setCloseStatus(String stage, boolean status){   // Receives the name of the stage, and sets its close status if it finds the stage
        closeStatusStages.computeIfPresent(stage, (k, v) -> v = status);
    }

    public boolean getCloseStatus(String stage){    // Receives the name of the stage, and returns its close status
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

    public void changeState(IState newState) { this.state = newState; }

    public boolean changeConfigurationMode(int option){ return state.changeConfigurationMode(option); }
    public boolean closeStage() { return state.closeStage(); }
    public boolean advanceStage() { return state.advanceStage(); }

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
}
