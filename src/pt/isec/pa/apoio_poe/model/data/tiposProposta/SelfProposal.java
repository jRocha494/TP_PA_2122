package pt.isec.pa.apoio_poe.model.data.tiposProposta;

import pt.isec.pa.apoio_poe.model.data.Proposal;

public class SelfProposal extends Proposal {
    public SelfProposal(String id, String title, long assignedStudent) {
        super(id, title, assignedStudent);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("T3," + id + "," + title + "," + assignedStudent);

        return sb.toString();
    }
}
