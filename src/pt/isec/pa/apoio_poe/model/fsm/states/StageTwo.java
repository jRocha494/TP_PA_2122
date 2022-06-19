package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.Application;
import pt.isec.pa.apoio_poe.model.data.DataLogic;
import pt.isec.pa.apoio_poe.model.data.Proposal;
import pt.isec.pa.apoio_poe.model.data.Student;
import pt.isec.pa.apoio_poe.model.data.tiposProposta.Project;
import pt.isec.pa.apoio_poe.model.data.tiposProposta.SelfProposal;
import pt.isec.pa.apoio_poe.model.fsm.AppContext;
import pt.isec.pa.apoio_poe.model.fsm.AppState;
import pt.isec.pa.apoio_poe.model.fsm.StateAdapter;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StageTwo extends StateAdapter {
    public StageTwo(AppContext ac, DataLogic dl) {
        super(ac, dl);
        ac.setCloseStatus("Stage2", false);
    }

    @Override
    public AppState getState() {
        return AppState.APPLICATION_OPTIONS_STAGE_TWO;
    }

    @Override
    public String getStage() {
        return "Second Stage - Application Options";
    }

    @Override
    public boolean closeStage() {
        // in case previous stage (stage 1) is closed... closes this stage
        if (ac.isStageClosed("Stage1")) {
            ac.setCloseStatus("Stage2", true);
            //changeState(AppState.PROPOSAL_ATTRIBUTION_PREV_CLOSED_STAGE_THREE);
            changeState(AppState.PROPOSAL_ATTRIBUTION_STAGE_THREE);
            return true;
        }
        return false;
    }

    @Override
    public boolean returnStage() {
        // in case previous stage (stage 1) is NOT closed, returns to it
        if (!ac.isStageClosed("Stage1"))
            changeState(AppState.CONFIGURATIONS_STATE_STAGE_ONE);
        else
            changeState(AppState.CLOSED_STAGE);
        return true;
    }

    @Override
    public boolean advanceStage() {
        //changeState(AppState.PROPOSAL_ATTRIBUTION_PREV_OPEN_STAGE_THREE);
        changeState(AppState.PROPOSAL_ATTRIBUTION_STAGE_THREE);
        return true;
    }


    @Override
    public boolean boolImportCSV(String filename) {
        boolean flag = false;
        long studentNumber;
        String line, id;
        List<Proposal> chosenProposals = new ArrayList<>();
        FileReader fr = null;
        BufferedReader br = null;
        Scanner sc = null;

        if (!ac.filenameIsValid(filename)) {
            return false;
        } else if (!filename.endsWith(".csv"))
            filename += ".csv";

        try {
            fr = new FileReader(filename);
            br = new BufferedReader(fr);

            while ((line = br.readLine()) != null) {
                sc = new Scanner(line);
                sc.useDelimiter(",");

                //Student Number
                if (sc.hasNext()) {
                    String snString = sc.next();
                    if (snString.length() != 10) {
                        break;
                    }

                    studentNumber = Long.parseLong(snString);

                    if (!dl.studentExists(studentNumber)) {
                        break;
                    }

                    if (dl.getStudent(studentNumber).hasApplication()) {
                        break;
                    }

                    if (dl.getStudent(studentNumber).hasProposed()) {
                        break;
                    }

                    if (dl.proposalWithStudentExists(studentNumber)) {
                        break;
                    }
                } else {
                    break;
                }

                //Chosen Proposals
                for (int i = 0; i < 6 && sc.hasNext(); i++) {
                    id = sc.next();
                    if (!ac.proposalIdIsValid(id)) {
                        flag = true;
                        break;
                    }

                    if (!dl.proposalExists(id)) {
                        flag = true;
                        break;
                    }

                    if (chosenProposals.contains(dl.getProposal(id))) {
                        flag = true;
                        break;
                    }

                    if (dl.hasAssignedStudent(id)) {
                        flag = true;
                        break;
                    }

                    if (dl.isInternship(id) && !dl.hasInternshipAccess(studentNumber)) {
                        flag = true;
                        break;
                    }
                    chosenProposals.add(dl.getProposal(id));
                }

                if (flag)
                    break;

                if (chosenProposals.isEmpty()) {
                    break;
                }

                //Add Application
                if (!sc.hasNext())
                    dl.addApplication(dl.getStudent(studentNumber), chosenProposals);

            }

            if (sc != null) sc.close();
            br.close();
            fr.close();
        } catch (NumberFormatException | IOException e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean boolExportCSV(String filename) {
        FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter pw = null;

        if (!ac.filenameIsValid(filename)) {
            return false;
        } else if (!filename.endsWith(".csv"))
            filename += ".csv";

        try {
            fw = new FileWriter(filename);
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);

            for (Application a : dl.getApplicationsValues()) {
                pw.println(a.toString());
            }

            pw.close();
            bw.close();
            fw.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    @Override
    public String importCSV(String filename) {
        StringBuilder sb = new StringBuilder();
        boolean flag = false;
        long studentNumber;
        String line, id;
        List<Proposal> chosenProposals = new ArrayList<>();
        FileReader fr = null;
        BufferedReader br = null;
        Scanner sc = null;

        if (!ac.filenameIsValid(filename)) {
            sb.append("File name is not valid");
            return sb.toString();
        } else if (!filename.endsWith(".csv"))
            filename += ".csv";

        try {
            fr = new FileReader(filename);
            br = new BufferedReader(fr);

            while ((line = br.readLine()) != null) {
                sc = new Scanner(line);
                sc.useDelimiter(",");

                //Student Number
                if (sc.hasNext()) {
                    String snString = sc.next();
                    if (snString.length() != 10) {
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

                    if (dl.proposalWithStudentExists(studentNumber)) {
                        sb.append("Student with number " + studentNumber + " already is assigned to a Proposal\n");
                        break;
                    }
                } else {
                    sb.append("Student Number not found");
                    break;
                }

                //Chosen Proposals
                for (int i = 0; i < 6 && sc.hasNext(); i++) {
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

                    if (chosenProposals.contains(dl.getProposal(id))) {
                        sb.append("Proposal with id " + id + " is already selected\n");
                        flag = true;
                        break;
                    }

                    if (dl.hasAssignedStudent(id)) {
                        sb.append("Proposal with id " + id + " already has a Student assigned to it\n");
                        flag = true;
                        break;
                    }

                    if (dl.isInternship(id) && !dl.hasInternshipAccess(studentNumber)) {
                        sb.append("Internship with id " + id + " cant be assigned to student." + studentNumber + " not allowed to have access\n");
                        flag = true;
                        break;
                    }
                    chosenProposals.add(dl.getProposal(id));
                }

                if (flag)
                    break;

                if (chosenProposals.isEmpty()) {
                    sb.append("Proposal id not found\n");
                    break;
                }

                //Add Application
                if (!sc.hasNext())
                    dl.addApplication(dl.getStudent(studentNumber), chosenProposals);
                else
                    sb.append("More fields than expected\n");

            }

            if (sc != null) sc.close();
            br.close();
            fr.close();
        } catch (FileNotFoundException e) {
            sb.append("The specified file was not found\n");
        } catch (NumberFormatException e) {
            sb.append("Argument should be a number but it was not\n");
        } catch (IOException e) {
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

        if (!ac.filenameIsValid(filename)) {
            sb.append("File name is not valid");
            return sb.toString();
        } else if (!filename.endsWith(".csv"))
            filename += ".csv";

        try {
            fw = new FileWriter(filename);
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);

            for (Application a : dl.getApplicationsValues()) {
                pw.println(a.toString());
            }

            pw.close();
            bw.close();
            fw.close();
        } catch (FileNotFoundException e) {
            sb.append("The specified file was not found");
        } catch (IOException e) {
            sb.append("There was an error (IOException)");
        }

        return sb.toString();
    }

    public String viewStudentsWithoutApplication() {
        StringBuilder sb = new StringBuilder();
        for (Student s : dl.getStudentsValues()) {
            if (!s.hasApplication())
                sb.append(s.studentToString());
        }
        return sb.toString();
    }

    Predicate<Proposal> bySelfProposals = proposal -> proposal instanceof SelfProposal;
    Predicate<Proposal> byTeacherProposals = proposal -> proposal instanceof Project;
    Predicate<Proposal> byProposalInApplication = proposal -> dl.applicationHasProposal(proposal);
    Predicate<Proposal> byProposalNotInApplication = proposal -> dl.applicationHasProposal(proposal) == false;

    @Override
    public String filterProposals(Integer... filters) {
        StringBuilder sb = new StringBuilder();

        List<Proposal> results = new ArrayList();
        results.addAll(dl.getProposalsValues());
        for (int element : filters) {
            switch (element) {
                case 1 -> results = results.stream().filter(bySelfProposals).collect(Collectors.toList());
                case 2 -> results = results.stream().filter(byTeacherProposals).collect(Collectors.toList());
                case 3 -> results = results.stream().filter(byProposalInApplication).collect(Collectors.toList());
                case 4 -> results = results.stream().filter(byProposalNotInApplication).collect(Collectors.toList());
                default -> {
                    return "";
                }
            }
        }
        sb.append("\n[FILTERED PROPOSALS]");
        for (var proposal : results) {
            sb.append(proposal.proposalToString());
        }
        return sb.toString();
    }

    @Override
    public boolean delete(Object selectedObject){
        Student s = ((Application) selectedObject).getStudent();
        return dl.deleteApplication(s.getStudentNumber());
    }
//
//    @Override
//    public boolean update(Object ... parameters){
//        List<Proposal> chosenProposals = new ArrayList<>();
//        long studentNumber;
//
//        if (parameters.length < 2 || parameters.length > 7)
//            return false;
//
//        Student student = (Student) parameters[0];
//        Proposal[] proposals = Arrays.copyOfRange((Proposal[]) parameters,1,parameters.length-1);
//
//        try{
//            studentNumber = student.getStudentNumber();
//            if (!dl.studentExists(studentNumber) ||
//                    dl.getStudent(studentNumber).hasApplication() ||
//                    dl.getStudent(studentNumber).hasProposed() ||
//                    dl.proposalWithStudentExists(studentNumber))
//                return false;
//
//            //Chosen Proposals
//            for (int i = 0; i < proposals.length-1 && proposals[i] != null ; i++) {
//                if (!ac.proposalIdIsValid(proposals[i].getId())) {
//                    return false;
//                }
//
//                if (!dl.proposalExists(proposals[i].getId()) ||
//                        chosenProposals.contains(proposals[i]) ||
//                        dl.hasAssignedStudent(proposals[i].getId()) ||
//                        (dl.isInternship(proposals[i].getId()) && !dl.hasInternshipAccess(studentNumber))) {
//                    return false;
//                }
//                chosenProposals.add(proposals[i]);
//            }
//            dl.updateApplication(dl.getStudent(studentNumber), chosenProposals);
//        }catch (NumberFormatException e){
//            return false;
//        }
//        return true;
//    }

    @Override
    public boolean add(Object ... parameters) {
        List<Proposal> chosenProposals = new ArrayList<>();
        long studentNumber;

        if (parameters.length < 2 || parameters.length > 7)
            return false;

        Student student = (Student) parameters[0];
        Proposal[] proposals = new Proposal[parameters.length-1];
        for (int i = 0; i < parameters.length-1; i++) {
            proposals[i] = (Proposal) parameters[i+1];
        }

        try {
            studentNumber = student.getStudentNumber();
            if (!dl.studentExists(studentNumber) ||
                    dl.getStudent(studentNumber).hasApplication() ||
                    dl.getStudent(studentNumber).hasProposed() ||
                    dl.proposalWithStudentExists(studentNumber))
                return false;

            //Chosen Proposals
            for (int i = 0; i < proposals.length-1 && proposals[i] != null ; i++) {
                if (!ac.proposalIdIsValid(proposals[i].getId())) {
                    return false;
                }

                if (!dl.proposalExists(proposals[i].getId()) ||
                    chosenProposals.contains(proposals[i]) ||
                    dl.hasAssignedStudent(proposals[i].getId()) ||
                    (dl.isInternship(proposals[i].getId()) && !dl.hasInternshipAccess(studentNumber))) {
                    return false;
                }
                chosenProposals.add(proposals[i]);
            }
            dl.addApplication(dl.getStudent(studentNumber), chosenProposals);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
