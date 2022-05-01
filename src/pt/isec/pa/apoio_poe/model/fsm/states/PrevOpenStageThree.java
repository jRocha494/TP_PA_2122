package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.Assignment;
import pt.isec.pa.apoio_poe.model.data.Assignment;
import pt.isec.pa.apoio_poe.model.data.DataLogic;
import pt.isec.pa.apoio_poe.model.data.Proposal;
import pt.isec.pa.apoio_poe.model.data.Student;
import pt.isec.pa.apoio_poe.model.fsm.AppContext;

import java.io.*;

public class PrevOpenStageThree extends StateAdapter{
    protected PrevOpenStageThree(AppContext ac, DataLogic dl) {
        super(ac, dl);
    }

    @Override
    public AppState getState() { return AppState.PROPOSAL_ATTRIBUTION_PREV_OPEN_STAGE_THREE; }

    @Override
    public String getStage() { return "Third Stage - Previous Stage Closed"; }

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
