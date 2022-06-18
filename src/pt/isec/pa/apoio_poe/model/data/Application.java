package pt.isec.pa.apoio_poe.model.data;

import java.io.Serializable;
import java.util.List;

public class Application implements Serializable, Cloneable {
    static final long serialVersionUID = 100L;
    private List<Proposal> chosenProposals;
    private Student studentNumber;

    public Application(List<Proposal> chosenProposals, Student studentNumber) {
        this.chosenProposals = chosenProposals;
        this.studentNumber = studentNumber;
    }

    public List<Proposal> getChoosenProposals() {
        return chosenProposals;
    }
    public Student getStudentNumber() {
        return studentNumber;
    }
    public boolean chosenProposalsContains(Proposal p) { return chosenProposals.contains(p); }
    public int getIndexProposal(Proposal p){ return chosenProposals.indexOf(p)+1;}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(studentNumber.getStudentNumber());
        for (Proposal p : chosenProposals)
            sb.append("," + p.getId());

        return sb.toString();
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
