package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.Application;
import pt.isec.pa.apoio_poe.model.data.DataLogic;
import pt.isec.pa.apoio_poe.model.data.Proposal;
import pt.isec.pa.apoio_poe.model.data.Student;
import pt.isec.pa.apoio_poe.model.fsm.AppContext;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StageTwo extends StateAdapter{
    protected StageTwo(AppContext ac, DataLogic dl) {
        super(ac, dl);
        ac.setCloseStatus("Stage2", false);
    }

    @Override
    public AppState getState() { return AppState.APPLICATION_OPTIONS_STAGE_TWO; }

    @Override
    public String getStage() { return "Second Stage - Application Options"; }

    @Override
    public boolean closeStage(){
        // in case previous stage (stage 1) is closed... closes this stage
        if(ac.isStageClosed("Stage1")){
            ac.setCloseStatus("Stage2", true);
            changeState(AppState.PROPOSAL_ATTRIBUTION_STAGE_THREE);
            return true;
        }
        return false;
    }

    @Override
    public boolean returnStage(){
        // in case previous stage (stage 1) is NOT closed, returns to it
        if(!ac.isStageClosed("Stage1")){
            changeState(AppState.CONFIGURATIONS_STATE_STAGE_ONE);
            return true;
        }
        return false;
    }

    @Override
    public boolean advanceStage(){
        changeState(AppState.PROPOSAL_ATTRIBUTION_STAGE_THREE);
        return true;
    }

    @Override
    public String importApplicationsCSV(String filename) {
        StringBuilder sb = new StringBuilder();
        boolean flag = false;
        long studentNumber;
        String line, id;
        List<Proposal> chosenProposals = new ArrayList<>();
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

                    if (!dl.studentExists(studentNumber)) {
                        sb.append("Student with number " + studentNumber + " does not exist\n");
                        break;
                    }

                    if (dl.getStudent(studentNumber).hasApplication()) {
                        sb.append("Student with number " + studentNumber + " already has made an application\n");
                        break;
                    }

                    if (dl.getStudent(studentNumber).hasProposed()) {
                        sb.append("Student with number " + studentNumber + " already has self-proposed\n");
                        break;
                    }

                    if(dl.proposalWithStudentExists(studentNumber)){
                        sb.append("Student with number " + studentNumber + " already is assigned to a Proposal\n");
                        break;
                    }
                } else {
                    sb.append("Student Number not found");
                    break;
                }

                //Chosen Proposals
                for(int i=0; i<6 && sc.hasNext(); i++) {
                    id = sc.next();
                    if (!ac.proposalIdIsValid(id)) {
                        sb.append("Proposal id is not valid");
                        flag = true;
                        break;
                    }

                    if (!dl.proposalExists(id)) {
                        sb.append("Proposal with id " + id + " does not exist\n");
                        flag = true;
                        break;
                    }

                    if(chosenProposals.contains(id)){
                        sb.append("Proposal with id " + id + " is already selected\n");
                        flag = true;
                        break;
                    }

                    if(dl.hasAssignedStudent(id)){
                        sb.append("Proposal with id " + id + " already has a Student assigned to it\n");
                        flag = true;
                        break;
                    }

                    if(dl.isInternship(id) && !dl.hasInternshipAccess(studentNumber)){
                        sb.append("Internship with id " + id + " cant be assigned to student." + studentNumber + " not allowed to have access\n");
                        flag = true;
                        break;
                    }
                    chosenProposals.add(dl.getProposal(id));
                }

                if(flag)
                    break;

                if(chosenProposals.isEmpty()){
                    sb.append("Proposal id not found\n");
                    break;
                }

                //Add Application
                if(!sc.hasNext())
                    dl.addApplication(dl.getStudent(studentNumber), chosenProposals);
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
    public String exportApplicationsCSV(String filename) {
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

            for(Application a : dl.getApplicationsValues()){
                pw.println(a.toString());
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
}
