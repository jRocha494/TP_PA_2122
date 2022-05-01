package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.*;
import pt.isec.pa.apoio_poe.model.data.tiposProposta.Project;
import pt.isec.pa.apoio_poe.model.fsm.AppContext;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class PrevClosedStageThree extends StateAdapter{
    protected PrevClosedStageThree(AppContext ac, DataLogic dl) {
        super(ac, dl);
    }

    @Override
    public AppState getState() { return AppState.PROPOSAL_ATTRIBUTION_PREV_CLOSED_STAGE_THREE; }

    @Override
    public String getStage() { return "Third Stage - Previous Stage Closed"; }

    @Override
    public boolean automaticAttributionSelfProposals() {
        //TODO: Adicionar flag que já foi atribuída às propostas? Senão no stage3 pode ser atribuida a um estudante,
        // e depois se for feita a atribuição das que têm aluno associado, volta a adicionar novamente...
        for(Proposal p : dl.getProposalsValues()){
            if(p.hasAssignedStudent() && !p.hasBeenAssigned() && !p.getAssignedStudent().hasBeenAssigned())
                dl.addAttribution(new Attribution(p.getAssignedStudent(), p));
        }
        return true;
    }

    @Override
    public boolean automaticAttributionsNotAssigned(){
        for(Student s : dl.getStudentWithBestClassification()){
            if(s.hasApplication()){
                Proposal p = dl.getFirstFreeProposal(s);
                if(p != null) {
                    List<Student> studentsWithSameProposal = dl.getStudentsWithSameProposal(dl.getStudentWithBestClassification(),p);
                    if(studentsWithSameProposal.size()==1){
                        dl.addAttribution(new Attribution(s,p));
                        s.setHasBeenAssigned(true);
                        p.setHasBeenAssigned(p.getAssignedStudent(), true);
                    }else {
                        changeState(AppState.CONFLICT_STAGE,studentsWithSameProposal,p);
                        return true;
                    }
                }
            }
        }
        return true;
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
