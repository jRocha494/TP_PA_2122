package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.DataLogic;
import pt.isec.pa.apoio_poe.model.data.Proposal;
import pt.isec.pa.apoio_poe.model.data.Student;
import pt.isec.pa.apoio_poe.model.data.Teacher;
import pt.isec.pa.apoio_poe.model.fsm.AppContext;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Pattern;

public class StageOne extends StateAdapter{
    public StageOne(AppContext ac, DataLogic dl) {
        super(ac, dl);
        ac.setCloseStatus("Stage1", false);
    }

    @Override
    public AppState getState() {
        return AppState.CONFIGURATIONS_STATE_STAGE_ONE;
    }

    @Override
    public boolean changeConfigurationMode(int option){
        switch(option){ // following the same order set on TextUI (student, teacher, proposal)
            case 1 -> changeState(AppState.CONFIGURATIONS_STATE_STUDENT_MANAGER);
            case 2 -> changeState(AppState.CONFIGURATIONS_STATE_TEACHER_MANAGER);
            case 3 -> changeState(AppState.CONFIGURATIONS_STATE_PROPOSAL_MANAGER);
            default -> { return false; }
        }
        return true;
    }

    @Override
    public boolean closeStage(){
        //TODO In UI it should validate whether these methods return true or false (Would it make sense to throw exceptions?)
        if(dl.areProposalsMoreThanStudents()) { // if every branch has more proposals than students...
            ac.setCloseStatus("Stage1", true);  // sets the close status flag for this stage to true
            changeState(AppState.CLOSED_STAGE);
            return true;
        }
        return false;
    }

    @Override
    public boolean advanceStage(){
        changeState(AppState.APPLICATION_OPTIONS_STAGE_TWO);
        return true;
    }

    @Override
    public String getStage() {
        return "First Stage - Configurations";
    }

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
