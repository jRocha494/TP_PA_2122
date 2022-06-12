package pt.isec.pa.apoio_poe.model.data.tiposProposta;

import pt.isec.pa.apoio_poe.model.data.Proposal;
import pt.isec.pa.apoio_poe.model.data.Student;

public class SelfProposal extends Proposal {
    public SelfProposal(String id, String title, Student assignedStudent) {
        super(id, title, assignedStudent);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(getId() + " | " + getTitle());
        return sb.toString();
    }

    @Override
    public String toStringExport() {
        StringBuilder sb = new StringBuilder();

        sb.append("T3," + getId() + "," + getTitle() + "," + getAssignedStudent().getStudentNumber());

        return sb.toString();
    }

    @Override
    public String proposalToString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[SELF PROPOSAL]\n" + super.proposalToString());

        return sb.toString();
    }
}