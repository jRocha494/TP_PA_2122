package pt.isec.pa.apoio_poe.model.data.tiposProposta;

import pt.isec.pa.apoio_poe.model.data.Proposal;

public class Internship extends Proposal {
    protected String destinedBranch;    // to which branch(es) this internship is destined to. ("RAS", "DA", and/or "SI"
    protected String hostingEntity;   // identification code for the entity hosting said internship

    public Internship(String id, String title, long assignedStudent, String destinedBranch, String hostingEntity) {
        super(id, title, assignedStudent);
        this.destinedBranch = destinedBranch;
        this.hostingEntity = hostingEntity;
    }

    public Internship(String id, String title, String destinedBranch, String hostingEntity) {
        super(id, title);
        this.destinedBranch = destinedBranch;
        this.hostingEntity = hostingEntity;
    }
}
