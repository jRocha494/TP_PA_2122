package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.DataLogic;
import pt.isec.pa.apoio_poe.model.data.Proposal;
import pt.isec.pa.apoio_poe.model.data.Student;
import pt.isec.pa.apoio_poe.model.fsm.AppContext;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public abstract class StateAdapter implements IState{
    protected AppContext ac;
    protected DataLogic dl;

    protected StateAdapter(AppContext ac, DataLogic dl) {
        this.ac = ac;
        this.dl = dl;
    }

    protected void changeState(AppState newState){
        ac.changeState(newState.createState(ac,dl));
    }

    protected void changeState(AppState newState, List<Student> students, Proposal proposal){
        ac.changeState(newState.createState(ac,dl,students,proposal));
    }

    @Override
    public String importProposalsCSV(String filename) { return ""; }

    @Override
    public String exportProposalsCSV(String filename) { return ""; }

    @Override
    public String importStudentsCSV(String filename) {
        return "";
    }

    @Override
    public String exportStudentsCSV(String filename) {
        return "";
    }

    @Override
    public String importTeachersCSV(String filename) { return ""; }

    @Override
    public String exportTeachersCSV(String filename) { return ""; }

    @Override
    public String importApplicationsCSV(String filename) { return ""; }

    @Override
    public String exportApplicationsCSV(String filename) { return ""; }

    @Override
    public boolean changeConfigurationMode(int option){ return false; }
    @Override
    public boolean closeStage(){ return false; }
    @Override
    public boolean advanceStage(){ return false; }
    @Override
    public boolean returnStage(){ return false; }

    @Override
    public boolean automaticAttributionSelfProposals() { return false; }

    @Override
    public boolean automaticAttributionsNotAssigned() { return false; }

    @Override
    public String[] getConflictedCases(){ return new String[0]; }

    @Override
    public String getConflictedProposal() { return ""; }

    @Override
    public boolean resolveConflictedCases(int option) { return false; }

    @Override
    public String exportStageThreeCSV(String filename){ return ""; }
}
