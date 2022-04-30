package pt.isec.pa.apoio_poe.model.data;

import java.util.List;

public class Application {
    List<String> choosenProposals;
    long studentNumber;

    public Application(List<String> choosenProposals, long studentNumber) {
        this.choosenProposals = choosenProposals;
        this.studentNumber = studentNumber;
    }

    public List<String> getChoosenProposals() {
        return choosenProposals;
    }

    public long getStudentNumber() {
        return studentNumber;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(studentNumber);
        for (String p : choosenProposals)
            sb.append("," + p);

        return sb.toString();
    }
}
