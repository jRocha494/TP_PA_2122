package pt.isec.pa.apoio_poe.model.data;

import java.util.List;

public class Application {
    List<Proposal> chosenProposals;
    Student studentNumber;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(studentNumber);
        for (Proposal p : chosenProposals)
            sb.append("," + p.getId());

        return sb.toString();
    }
}
