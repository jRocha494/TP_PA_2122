package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.Assignment;
import pt.isec.pa.apoio_poe.model.data.Assignment;
import pt.isec.pa.apoio_poe.model.data.DataLogic;
import pt.isec.pa.apoio_poe.model.data.Proposal;
import pt.isec.pa.apoio_poe.model.data.Student;
import pt.isec.pa.apoio_poe.model.data.tiposProposta.Project;
import pt.isec.pa.apoio_poe.model.data.tiposProposta.SelfProposal;
import pt.isec.pa.apoio_poe.model.fsm.AppContext;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PrevOpenStageThree extends StateAdapter{
    protected PrevOpenStageThree(AppContext ac, DataLogic dl) {
        super(ac, dl);
    }

    @Override
    public AppState getState() { return AppState.PROPOSAL_ATTRIBUTION_PREV_OPEN_STAGE_THREE; }

    @Override
    public String getStage() { return "Third Stage - Previous Stage Closed"; }

    @Override
    public boolean returnStage(){
        if(!ac.isStageClosed("Stage2")){
            changeState(AppState.APPLICATION_OPTIONS_STAGE_TWO);
            return true;
        }
        return false;
    }

    @Override
    public boolean advanceStage(){
        changeState(AppState.ADVISOR_ATTRIBUTION_STAGE_FOUR);
        return true;
    }

    @Override
    public boolean automaticAssignmentSelfProposals() {
        //TODO: Adicionar flag 'que já foi atribuída' às propostas? Senão no stage3 pode ser atribuida a um estudante,
        // e depois se for feita a atribuição das que têm aluno associado, volta a adicionar novamente...
        //TODO: as flags 'hasBeenAssigned' nas proposals, students e teachers devem ser usadas ao importar info?
        for (Proposal p : dl.getProposalsValues()) {
            if (p.hasAssignedStudent() && !p.hasBeenAssigned() && !p.getAssignedStudent().hasBeenAssigned()) { // if the proposal has a pre-assigned student, and checks if the proposal and the student haven't been officially assigned (as to add more protection)
                dl.addAssignment(new Assignment(p.getAssignedStudent(), p));
                p.setHasBeenAssigned(true); // sets the proposal's flag on whether it has been officially assigned to true
                p.getAssignedStudent().setHasBeenAssigned(true);    // sets the students' flag on whether it has been officially assigned to true
            }
        }
        return true;
    }

    @Override
    public boolean removeAssignment(int assignmentToRemove) {
        // doesn't remove assignments that are self proposals, or proposed by teachers and already have a student pre-assigned
        Assignment a = dl.getAssignment(assignmentToRemove-1);
        if(a.getProposal() instanceof SelfProposal || (a.getProposal() instanceof Project && a.getProposal().hasAssignedStudent())) {}
        else {
            if (a.hasAdvisor())
                a.getAdvisor().setHasBeenAssigned(false);
            if (a.hasStudent())
                a.getStudent().setHasBeenAssigned(false);
            a.getProposal().setHasBeenAssigned(false);
            a.getProposal().setAssignedStudent(null);
            dl.removeAssignment(a);
        }
        return true;
    }

    @Override
    public boolean removeAllAssignments(){
        for(Assignment a : dl.getAssignmentList()){
            if(a.getProposal() instanceof SelfProposal || (a.getProposal() instanceof Project && a.getProposal().hasAssignedStudent()))
                continue;
            else {
                if (a.hasAdvisor())
                    a.getAdvisor().setHasBeenAssigned(false);
                if (a.hasStudent())
                    a.getStudent().setHasBeenAssigned(false);
                a.getProposal().setHasBeenAssigned(false);
                a.getProposal().setAssignedStudent(null);
                dl.removeAssignment(a);
            }
        }
        return true;
    }

    @Override
    public String viewStudentsAssigned(){
        StringBuilder sb = new StringBuilder();
        for (Student s : dl.getStudentsValues()) {
            if(s.hasBeenAssigned()) {
                sb.append(s.studentToString());
                Proposal p = dl.getProposalByStudent(s.getStudentNumber()); // gets the proposal assigned to the student
                if(p instanceof SelfProposal)
                    sb.append("-> Preference order: 1");
                else
                    sb.append("-> Preference order: ").append(dl.getApplicationByStudent(s).getIndexProposal(p));

            }
        }
        return sb.toString();
    }

    @Override
    public String viewStudentsUnassigned(){
        StringBuilder sb = new StringBuilder();
        for (Student s : dl.getStudentsValues()) {
            if(!s.hasBeenAssigned()) {
                sb.append(s.studentToString());
            }
        }
        return sb.toString();
    }

    Predicate<Proposal> bySelfProposals = proposal -> proposal instanceof SelfProposal;
    Predicate<Proposal> byTeacherProposals = proposal -> proposal instanceof Project;
    Predicate<Proposal> byProposalUnassigned = proposal -> proposal.hasBeenAssigned() == false;
    Predicate<Proposal> byProposalAssigned = proposal -> proposal.hasBeenAssigned();
    @Override
    public String filterProposals(Integer... filters){
        StringBuilder sb = new StringBuilder();

        List<Proposal> results = new ArrayList();
        results.addAll(dl.getProposalsValues());
        for(int element : filters){
            switch (element){
                case 1 -> results = results.stream().filter(bySelfProposals).collect(Collectors.toList());
                case 2 -> results = results.stream().filter(byTeacherProposals).collect(Collectors.toList());
                case 3 -> results = results.stream().filter(byProposalUnassigned).collect(Collectors.toList());
                case 4 -> results = results.stream().filter(byProposalAssigned).collect(Collectors.toList());
                default -> { return ""; }
            }
        }
        sb.append("\n[FILTERED PROPOSALS]");
        for(var proposal : results){
            sb.append(proposal.proposalToString());
        }
        return sb.toString();
    }

    @Override
    public String exportStageThreeCSV(String filename){
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
                pw.println(s.toString());
                Proposal assignedProposal = dl.getProposalByStudent(s.getStudentNumber());
                if(assignedProposal !=  null){
                    pw.println("," + assignedProposal.toString());
                    int indexProposal = dl.getIndexofProposalInApplication(assignedProposal,s);
                    if(indexProposal != -1)
                        pw.println("," + indexProposal);
                }

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
