package pt.isec.pa.apoio_poe.model.data.tiposProposta;

import pt.isec.pa.apoio_poe.model.data.Proposal;
import pt.isec.pa.apoio_poe.model.data.Student;

import java.util.List;

public class Internship extends Proposal implements Cloneable{
    protected List<String> destinedBranch;    // to which branch(es) this internship is destined to. ("RAS", "DA", and/or "SI"
    protected String hostingEntity;   // identification code for the entity hosting said internship

    public Internship(String id, String title, Student assignedStudent, List<String> destinedBranch, String hostingEntity) {
        super(id, title, assignedStudent);
        this.destinedBranch = destinedBranch;
        this.hostingEntity = hostingEntity;
    }

    public List<String> getDestinedBranch() {
        return destinedBranch;
    }

    public String getHostingEntity() {
        return hostingEntity;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(getId() + " | " + getTitle());
        return sb.toString();
    }

    @Override
    public String toStringExport() {
        StringBuilder sb = new StringBuilder();

        sb.append("T1," + getId() + ",");
        for(String b : destinedBranch) {
            if (destinedBranch.indexOf(b) != 0)
                sb.append("|");
            sb.append(b);
        }
        sb.append("," + getTitle() + "," + hostingEntity);
        if(hasAssignedStudent())
            sb.append("," + getAssignedStudent().getStudentNumber());

        return sb.toString();
    }

    @Override
    public String proposalToString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[PROJECT]\n" + super.proposalToString());
        sb.append("Hosted by " + hostingEntity);
        sb.append("Branches: ");
        for(String b : destinedBranch){
            sb.append(" -> " + b);
        }

        return sb.toString();
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
