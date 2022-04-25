package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.DataLogic;
import pt.isec.pa.apoio_poe.model.data.Teacher;
import pt.isec.pa.apoio_poe.model.fsm.AppContext;

import java.io.*;
import java.util.Scanner;

public class TeacherManager extends StateAdapter{
    public TeacherManager(AppContext ac, DataLogic dl) { super(ac, dl); }

    @Override
    public String importTeachersCSV(String filename) {
        StringBuilder sb = new StringBuilder();
        String name, email, line;
        FileReader fr = null;
        BufferedReader br = null;
        Scanner sc = null;

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
                pw.println(t.getName() + "," + t.getEmail());
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
