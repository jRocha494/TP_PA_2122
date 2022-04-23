package pt.isec.pa.apoio_poe.model.data.tiposProposta;

import pt.isec.pa.apoio_poe.model.data.Proposal;

public class Project extends Proposal {
    protected String destinedBranch;    // to which branch(es) this internship is destined to. ("RAS", "DA", and/or "SI"
    protected String proposingTeacher;    // identification for the teacher proposing this project

    public Project(String id, String title, long assignedStudent, String destinedBranch, String proposingTeacher) {
        super(id, title, assignedStudent);
        this.destinedBranch = destinedBranch;
        this.proposingTeacher = proposingTeacher;
    }

    public Project(String id, String title, String destinedBranch, String proposingTeacher) {
        super(id, title);
        this.destinedBranch = destinedBranch;
        this.proposingTeacher = proposingTeacher;
    }
}
