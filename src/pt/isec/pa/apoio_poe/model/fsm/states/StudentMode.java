package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.DataLogic;
import pt.isec.pa.apoio_poe.model.data.Student;
import pt.isec.pa.apoio_poe.model.fsm.AppContext;
import pt.isec.pa.apoio_poe.model.fsm.AppState;
import pt.isec.pa.apoio_poe.model.fsm.StateAdapter;

import java.io.*;
import java.util.Scanner;

public class StudentMode extends StateAdapter {
    public StudentMode(AppContext ac, DataLogic dl) { super(ac, dl); }

    @Override
    public boolean changeConfigurationMode(int option){
        switch(option){ // following the same order set on TextUI (teacher, proposal)
            case 1 -> changeState(AppState.CONFIGURATIONS_STATE_STUDENT_MANAGER);
            case 2 -> changeState(AppState.CONFIGURATIONS_STATE_TEACHER_MANAGER);
            case 3 -> changeState(AppState.CONFIGURATIONS_STATE_PROPOSAL_MANAGER);
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
    public String viewStudents() {
        StringBuilder sb = new StringBuilder();
        for (Student s : dl.getStudentsValues()) {
            sb.append(s.studentToString());
        }
        return sb.toString();
    }

    @Override
//    public boolean add(String number, String name, String email, String course, String branch, String classification, String internshipAccess){
    public boolean add(String ... parameters){
        if (parameters.length != 7)
            return false;

        String number = parameters[0];
        String name = parameters[1];
        String email = parameters[2];
        String course = parameters[3];
        String branch = parameters[4];
        String classification = parameters[5];
        String internshipAccess = parameters[6];

        //validates student number
        try {
            if (number.length() != 10)
                return false;
            long studentNumber = Long.parseLong(number);
            if (dl.studentExists(studentNumber))
                return false;

            //Email
            if(!ac.emailIsValid(email))
                return false;
            if (dl.studentExists(email))
                return false;

            //Classification
            double classif = Double.parseDouble(classification);
            if(classif>1 || classif<0)
                return false;

            //InternshipAccess
            boolean iAccess = Boolean.parseBoolean(internshipAccess);

            dl.addStudent(studentNumber, name, email, course.toUpperCase(), branch.toUpperCase(), classif, iAccess);

        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public String importCSV(String filename) {
        StringBuilder sb = new StringBuilder();
        long studentNumber;
        double classification;
        boolean internshipAccess;
        String name, email, line, course, branch;
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

                //Student Number
                if (sc.hasNext()) {
                    String snString = sc.next();
                    if(snString.length()!=10){
                        sb.append("Student Number is not valid");
                        break;
                    }

                    studentNumber = Long.parseLong(snString);

                    if (dl.studentExists(studentNumber)) {
                        sb.append("Student with number " + studentNumber + " already exists\n");
                        break;
                    }
                } else {
                    sb.append("Student Number not found");
                    break;
                }

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

                    if (dl.studentExists(email)) {
                        sb.append("Student with email " + email + " already exists\n");
                        break;
                    }
                } else {
                    sb.append("Email not found\n");
                    break;
                }

                //Course
                if (sc.hasNext()) {
                    course = sc.next();
                    if(!(course.equalsIgnoreCase("LEI") || course.equalsIgnoreCase("LEI-PL"))){
                        sb.append("Course is not valid");
                        break;
                    }
                } else {
                    sb.append("Course not found");
                    break;
                }

                //Branch
                if (sc.hasNext()) {
                    branch = sc.next();
                    if(!(branch.equalsIgnoreCase("DA") || branch.equalsIgnoreCase("RAS") || branch.equalsIgnoreCase("SI"))){
                        sb.append("Branch is not valid");
                        break;
                    }
                } else {
                    sb.append("Branch not found");
                    break;
                }

                //Classification
                if (sc.hasNext()) {
                    String cString = sc.next();
                    classification = Double.parseDouble(cString);
                    if(classification>1 || classification<0){
                        sb.append("Classification is not valid");
                        break;
                    }
                } else {
                    sb.append("Student Number not found");
                    break;
                }

                //Internship Access
                if (sc.hasNext()) {
                    String iaString = sc.next();
                    if(!(iaString.equalsIgnoreCase("TRUE") || iaString.equalsIgnoreCase("FALSE"))){
                        sb.append("Internship Access Value is not valid");
                        break;
                    }
                    internshipAccess = Boolean.parseBoolean(iaString);
                } else {
                    sb.append("Student Number not found");
                    break;
                }

                //Add Student
                if(!sc.hasNext())
                    dl.addStudent(studentNumber, name, email, course.toUpperCase(), branch.toUpperCase(), classification, internshipAccess);
                else
                    sb.append("More fields than expected\n");

            }

            if(sc!=null) sc.close();
            br.close();
            fr.close();
        }catch (FileNotFoundException e){
            sb.append("The specified file was not found\n");
        }catch (NumberFormatException e){
            sb.append("Argument should be a number but it was not\n");
        }catch (IOException e){
            sb.append("There was an error (IOException)\n");
        }

        return sb.toString();
    }

    @Override
    public String exportCSV(String filename) {
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

            for(Student s : dl.getStudentsValues()){
                pw.println(s.toStringExport());
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
        return AppState.CONFIGURATIONS_STATE_STUDENT_MANAGER;
    }

    @Override
    public String getStage() {
        return "First Stage - Configurations";
    }
}
