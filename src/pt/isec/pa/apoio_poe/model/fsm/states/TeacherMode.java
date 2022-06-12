package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.DataLogic;
import pt.isec.pa.apoio_poe.model.data.Teacher;
import pt.isec.pa.apoio_poe.model.fsm.AppContext;
import pt.isec.pa.apoio_poe.model.fsm.AppState;
import pt.isec.pa.apoio_poe.model.fsm.StateAdapter;

import java.io.*;
import java.util.Scanner;

public class TeacherMode extends StateAdapter {
    public TeacherMode(AppContext ac, DataLogic dl) { super(ac, dl); }

    @Override
    public boolean changeConfigurationMode(int option){
        switch(option){ // following the same order set on TextUI (student, proposal)
            case 1 -> changeState(AppState.CONFIGURATIONS_STATE_STUDENT_MANAGER);
            case 2 -> changeState(AppState.CONFIGURATIONS_STATE_PROPOSAL_MANAGER);
            default -> { return false; }
        }
        return true;
    }

    @Override
    public boolean closeStage(){
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
    public String viewTeachers() {
        StringBuilder sb = new StringBuilder();
        for (Teacher t : dl.getTeachersValues()) {
            sb.append(t.teacherToString());
        }
        return sb.toString();
    }

    @Override
    public String importTeachersCSV(String filename) {
        StringBuilder sb = new StringBuilder();
        String name, email, line;
        FileReader fr = null;
        BufferedReader br = null;
        Scanner sc = null;

        if(!ac.filenameIsValid(filename)){
            sb.append("File name is not valid");
            return sb.toString();
        }else if(!filename.endsWith(".csv"))
            filename += ".csv";

        try{
            fr = new FileReader(filename);
            br = new BufferedReader(fr);

            while ((line = br.readLine()) != null) {
                sc = new Scanner(line);
                sc.useDelimiter(",");

                //Name
                if (sc.hasNext()) {
                    name = sc.next();
                } else {
                    sb.append("Name not found");
                    break;
                }

                //Email
                if (sc.hasNext()) {
                    email = sc.next();
                    if(!ac.emailIsValid(email)) {
                        sb.append("Email is not valid");
                        break;
                    }

                    if (dl.teacherExists(email)) {
                        sb.append("Teacher with email " + email + " already exists\n");
                        break;
                    }
                } else {
                    sb.append("Email not found\n");
                    break;
                }

                //Adds Teacher
                if(!sc.hasNext())
                    dl.addTeacher(email, name, false);
                else
                    sb.append("More fields than expected\n");

            }

            if(sc!=null) sc.close();
            br.close();
            fr.close();
        }catch (FileNotFoundException e){
            sb.append("The specified file was not found\n");
        }catch (IOException e){
            sb.append("There was an error (IOException)\n");
        }

        return sb.toString();
    }

    @Override
    public String exportTeachersCSV(String filename) {
        StringBuilder sb = new StringBuilder();
        FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter pw = null;

        if(!ac.filenameIsValid(filename)){
            sb.append("File name is not valid");
            return sb.toString();
        }else if(!filename.endsWith(".csv"))
            filename += ".csv";

        try{
            fw = new FileWriter(filename);
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);

            for(Teacher t : dl.getTeachersValues()){
                pw.println(t.toStringExport());
            }

            pw.close();
            bw.close();
            fw.close();
        }catch (FileNotFoundException e){
            sb.append("The specified file was not found");
        }catch (IOException e){
            sb.append("There was an error (IOException)");
        }

        return sb.toString();
    }

    @Override
    public AppState getState() {
        return AppState.CONFIGURATIONS_STATE_TEACHER_MANAGER;
    }

    @Override
    public String getStage() {
        return "First Stage - Configurations";
    }
}
