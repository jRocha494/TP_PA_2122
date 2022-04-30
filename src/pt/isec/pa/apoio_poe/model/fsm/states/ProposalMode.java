package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.DataLogic;
import pt.isec.pa.apoio_poe.model.data.Proposal;
import pt.isec.pa.apoio_poe.model.data.Student;
import pt.isec.pa.apoio_poe.model.data.tiposProposta.Internship;
import pt.isec.pa.apoio_poe.model.fsm.AppContext;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProposalMode extends StateAdapter{
    public ProposalMode(AppContext ac, DataLogic dl) { super(ac, dl); }

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
    public String importProposalsCSV(String filename) {
        StringBuilder sb = new StringBuilder();
        long assignedStudent = -1;
        double classification;
        boolean internshipAccess;
        String pType, id, title, hostingEntity=null, proposingTeacher=null, line;
        List<String> destinedBranch = new ArrayList<>();;
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

                //Proposal type
                if (sc.hasNext()) {
                    pType = sc.next();
                    if(!(pType.equalsIgnoreCase("T1") || pType.equalsIgnoreCase("T2") || pType.equalsIgnoreCase("T3"))){
                        sb.append("Proposal type is not valid");
                        break;
                    }
                } else {
                    sb.append("Proposal type not found");
                    break;
                }

                //Proposal id
                if (sc.hasNext()) {
                    id = sc.next();
                    if(!ac.proposalIdIsValid(id)) {
                        sb.append("Proposal id is not valid");
                        break;
                    }

                    if (dl.proposalExists(id)) {
                        sb.append("Proposal with id " + id + " already exists\n");
                        break;
                    }
                } else {
                    sb.append("Proposal id not found\n");
                    break;
                }

                //Branch - only for Internships and Projects
                if(!pType.equalsIgnoreCase("T3")) {
                    if (sc.hasNext()) {
                        String[] branches = sc.next().trim().split("\\|"); // Removes whitespaces and splits when a "|" is found
                        destinedBranch = new ArrayList<>();
                        for (String branch : branches) {
                            if (!(branch.equalsIgnoreCase("DA") || branch.equalsIgnoreCase("RAS") || branch.equalsIgnoreCase("SI"))) {
                                sb.append("Branch is not valid");
                                break;
                            }
                            destinedBranch.add(branch);
                        }
                    } else {
                        sb.append("Branch not found");
                        break;
                    }
                }

                //Title
                if (sc.hasNext()) {
                    title = sc.next();
                } else {
                    sb.append("Title not found");
                    break;
                }

                if(pType.equalsIgnoreCase("T1")){
                    //Internship - Hosting Entity and possibly Student Number

                    //Hosting Entity
                    if (sc.hasNext()) {
                        hostingEntity = sc.next();
                    } else {
                        sb.append("Hosting Entity not found");
                        break;
                    }

                }else if(pType.equalsIgnoreCase("T2")){
                    //Proposals - Teacher Email and possibly Student Number
                    //Teacher Email
                    if (sc.hasNext()) {
                        proposingTeacher = sc.next();
                        if(!dl.teacherExists(proposingTeacher)) {
                            sb.append("Teacher with email " + proposingTeacher + " does not exist");
                            break;
                        }
                    } else {
                        sb.append("Email not found\n");
                        break;
                    }
                }

                //Student Number if exists
                if (sc.hasNext()) {
                    String snString = sc.next();
                    if(snString.length()!=10){
                        sb.append("Student Number is not valid");
                        break;
                    }

                    assignedStudent = Long.parseLong(snString);

                    if (!dl.studentExists(assignedStudent)) {
                        sb.append("Student with number " + assignedStudent + " does not exist");
                        break;
                    }

                    if(dl.proposalWithStudentExists(assignedStudent)){
                        sb.append("Proposal assigned to student " + assignedStudent + " already exists");
                        break;
                    }
                } else if(pType.equalsIgnoreCase("T3")){
                    sb.append("Student Number not found");
                    break;
                }

                //Add Student
                if(!sc.hasNext()) {
                    if(pType.equalsIgnoreCase("T1"))
                        dl.addInternship(id, title, assignedStudent, destinedBranch, hostingEntity);
                    if(pType.equalsIgnoreCase("T2"))
                        dl.addProject(id, title, assignedStudent, destinedBranch, proposingTeacher);
                    if(pType.equalsIgnoreCase("T3"))
                        dl.addSelfProposal(id, title, assignedStudent);
                }else
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
    public String exportProposalsCSV(String filename) {
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

            for(var p : dl.getProposalsValues())
                pw.println(p.toString());

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
        return AppState.CONFIGURATIONS_STATE_PROPOSAL_MANAGER;
    }

    @Override
    public String getStage() {
        return "First Stage - Configurations";
    }
}
