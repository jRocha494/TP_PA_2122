package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.DataLogic;
import pt.isec.pa.apoio_poe.model.fsm.AppContext;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class StudentManager extends StateAdapter{
    public StudentManager(AppContext ac, DataLogic dl) { super(ac, dl); }

    @Override
    public String importStudentsCSV(String filename) {
        StringBuilder sb = new StringBuilder();
        long studentNumber;
        double classification;
        boolean internshipAccess;
        String name, email, line, course, branch;
        FileReader fr = null;
        BufferedReader br = null;
        Scanner sc = null;

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
    public String exportStudentsCSV(String filename) {
        StringBuilder sb = new StringBuilder();

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
