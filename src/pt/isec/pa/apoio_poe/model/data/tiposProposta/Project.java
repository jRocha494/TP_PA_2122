package pt.isec.pa.apoio_poe.model.data.tiposProposta;

import pt.isec.pa.apoio_poe.model.data.Proposal;
import pt.isec.pa.apoio_poe.model.data.Student;
import pt.isec.pa.apoio_poe.model.data.Teacher;

import java.util.List;

public class Project extends Proposal {
    protected List<String> destinedBranch;    // to which branch(es) this internship is destined to. ("RAS", "DA", and/or "SI"
    protected Teacher proposingTeacher;    // identification for the teacher proposing this project

    public Project(String id, String title, Student assignedStudent, List<String> destinedBranch, Teacher proposingTeacher) {
        super(id, title, assignedStudent);
        this.destinedBranch = destinedBranch;
        this.proposingTeacher = proposingTeacher;
    }

    public List<String> getDestinedBranch() {
        return destinedBranch;
    }

    public Teacher getProposingTeacher() {
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
        sb.append("," + title + "," + proposingTeacher.getEmail() );
        if(assignedStudent != null)
            sb.append("," + assignedStudent.getStudentNumber());

        return sb.toString();
    }

    @Override
    public String proposalToString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[PROJECT]\n" + super.proposalToString());
        sb.append("Proposed by " + proposingTeacher.getName() + " | " + proposingTeacher.getEmail());
        sb.append("Branches: ");
        for(String b : destinedBranch){
            sb.append(" -> " + b);
        }

        return sb.toString();
    }
}
