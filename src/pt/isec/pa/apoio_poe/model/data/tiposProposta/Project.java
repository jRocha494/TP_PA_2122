package pt.isec.pa.apoio_poe.model.data.tiposProposta;

import pt.isec.pa.apoio_poe.model.data.Proposal;
import pt.isec.pa.apoio_poe.model.data.Student;

import java.util.List;

public class Project extends Proposal {
    protected List<String> destinedBranch;    // to which branch(es) this internship is destined to. ("RAS", "DA", and/or "SI"
    protected String proposingTeacher;    // identification for the teacher proposing this project

    public Project(String id, String title, Student assignedStudent, List<String> destinedBranch, String proposingTeacher) {
        super(id, title, assignedStudent);
        this.destinedBranch = destinedBranch;
        this.proposingTeacher = proposingTeacher;
    }

    public Project(String id, String title, List<String> destinedBranch, String proposingTeacher) {
        super(id, title);
        this.destinedBranch = destinedBranch;
        this.proposingTeacher = proposingTeacher;
    }

    public List<String> getDestinedBranch() {
        return destinedBranch;
    }

    public String getProposingTeacher() {
        return proposingTeacher;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("T2," + id + ",");
        for(String b : destinedBranch) {
            if (destinedBranch.indexOf(b) != 0)
                sb.append("|");
            sb.append(b);
        }
        sb.append("," + title + "," + proposingTeacher );
        if(assignedStudent != null)
            sb.append("," + assignedStudent);

        return sb.toString();
    }
}
