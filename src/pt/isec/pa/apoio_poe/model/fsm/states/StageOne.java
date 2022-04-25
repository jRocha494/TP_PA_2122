package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.DataLogic;
import pt.isec.pa.apoio_poe.model.fsm.AppContext;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class StageOne extends StateAdapter{
    public StageOne(AppContext ac, DataLogic dl) {
        super(ac, dl);
    }

    @Override
    public String importProposalsCSV(String filename) {
        StringBuilder sb = new StringBuilder();

        return sb.toString();
    }

    @Override
    public String exportProposalsCSV(String filename) {
        StringBuilder sb = new StringBuilder();

        return sb.toString();
    }

    @Override
    public String importStudentsCSV(String filename) {
        StringBuilder sb = new StringBuilder();

        return sb.toString();
    }

    @Override
    public String exportStudentsCSV(String filename) {
        StringBuilder sb = new StringBuilder();

        return sb.toString();
    }
    @Override
    public String importTeachersCSV(String filename) {
        StringBuilder sb = new StringBuilder();
        String name=null, email=null, line=null;
        Scanner sc = null;

        try{
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);

            while ((line = br.readLine()) != null) {
                sc = new Scanner(line);
                sc.useDelimiter(",");

                if (sc.hasNext()) {
                    name = sc.next();
                    sb.append("Name: ").append(name).append("\n");
                } else {
                    sb.append("Name not found");
                    break;
                }

                if (sc.hasNext()) {
                    email = sc.next();
                    if (dl.teacherExists(email)) {
                        sb.append("Teacher with email " + email + " already exists\n");
                        break;
                    }
                    sb.append("Email: ").append(email).append("\n");
                } else {
                    sb.append("Email not found\n");
                    break;
                }

                dl.addTeacher(email, name, false);
            }

            br.close();
            fr.close();
        }catch (FileNotFoundException e){
            sb.append("The specified file was not found");
        }catch (IOException e){
            sb.append("There was an error (IOException)");
        }

        return sb.toString();
    }

    @Override
    public String exportTeachersCSV(String filename) {
        StringBuilder sb = new StringBuilder();

        return sb.toString();
    }

    @Override
    public AppState getState() {
        return AppState.CONFIGURATIONS_STATE_STAGE_ONE;
    }

    @Override
    public String getStage() {
        return "First Stage - Configurations";
    }
}
