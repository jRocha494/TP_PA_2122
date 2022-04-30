package pt.isec.pa.apoio_poe.model.data;

import java.util.List;

public class Application {
    List<String> chosenProposals;
    long studentNumber;

    public Application(List<String> choosenProposals, long studentNumber) {
        this.chosenProposals = choosenProposals;
        this.studentNumber = studentNumber;
    }

    public List<String> getChosenProposals() {
        return chosenProposals;
    }

    public long getStudentNumber() {
        return studentNumber;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(studentNumber);
        for (String p : chosenProposals)
            sb.append("," + p);

        return sb.toString();
    }
}
