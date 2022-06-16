package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.DataLogic;
import pt.isec.pa.apoio_poe.model.data.Proposal;
import pt.isec.pa.apoio_poe.model.data.Student;
import pt.isec.pa.apoio_poe.model.data.Teacher;
import pt.isec.pa.apoio_poe.model.fsm.AppContext;
import pt.isec.pa.apoio_poe.model.fsm.AppState;
import pt.isec.pa.apoio_poe.model.fsm.StateAdapter;

import java.io.*;

public class StageFive extends StateAdapter {
    public StageFive(AppContext ac, DataLogic dl) {
        super(ac, dl);
    }

    @Override
    public AppState getState() {
        return AppState.VIEW_DATA_STAGE_FIVE;
    }

    @Override
    public String getStage() {
        return "Fifth Stage - View Data";
    }

    @Override
    public String viewStudentsAssigned(){
        StringBuilder sb = new StringBuilder();
        for (Student s : dl.getStudentsValues()) {
            if(s.hasBeenAssigned()) {
                sb.append(s.studentToString());
            }
        }
        return sb.toString();
    }

    @Override
    public String viewStudentsUnassignedWithApplications(){
        StringBuilder sb = new StringBuilder();
        for (Student s : dl.getStudentsValues()) {
            if(!s.hasBeenAssigned() && s.hasApplication()) {
                sb.append(s.studentToString());
            }
        }
        return sb.toString();
    }

    @Override
    public String viewProposalsUnassigned(){
        StringBuilder sb = new StringBuilder();
        for (Proposal p : dl.getProposalsValues()) {
            if(!p.hasBeenAssigned())
                sb.append(p.proposalToString());
        }
        return sb.toString();
    }
    @Override
    public String viewProposalsAssigned(){
        StringBuilder sb = new StringBuilder();
        for (Proposal p : dl.getProposalsValues()) {
            if(p.hasBeenAssigned())
                sb.append(p.proposalToString());
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
                Proposal assignedProposal = dl.getProposalByStudent(s.getStudentNumber());
                if(assignedProposal !=  null){
                    pw.println("," + assignedProposal.toString());
                    int indexProposal = dl.getIndexofProposalInApplication(assignedProposal,s);
                    if(indexProposal != -1)
                        pw.println("," + indexProposal);
                    Teacher assignedTeacher = dl.getAssignedTeacherByStudent(s.getStudentNumber());
                    if(assignedTeacher != null)
                        pw.println("," + assignedTeacher.getEmail());
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
