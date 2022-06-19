package pt.isec.pa.apoio_poe.model.data;

import java.io.Serializable;
import java.util.List;

public class Application implements Serializable, Cloneable {
    static final long serialVersionUID = 100L;
    private List<Proposal> chosenProposals;
    private Student student;

    public Application(List<Proposal> chosenProposals, Student student) {
        this.chosenProposals = chosenProposals;
        this.student = student;
    }

    public List<Proposal> getChosenProposals() {
        return chosenProposals;
    }
    public Student getStudent() {
        Student student = null;
        try {
            student = (Student) student.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
        return student;
    }
    public boolean chosenProposalsContains(Proposal p) { return chosenProposals.contains(p); }
    public int getIndexProposal(Proposal p){ return chosenProposals.indexOf(p)+1;}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(student.getStudentNumber());
        for (Proposal p : chosenProposals)
            sb.append("," + p.getId());

        return sb.toString();
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
