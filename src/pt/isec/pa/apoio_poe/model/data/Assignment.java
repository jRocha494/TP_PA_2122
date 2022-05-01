package pt.isec.pa.apoio_poe.model.data;

import java.io.Serializable;

public class Assignment implements Serializable {
    static final long serialVersionUID = 100L;
    private Student student;
    private Proposal proposal;
    private Teacher advisor;

    public Assignment(Student student, Proposal proposal) {
        this.student = student;
        this.proposal = proposal;
        this.advisor = null;
    }
    public Assignment(Teacher advisor, Proposal proposal) {
        this.student = null;
        this.proposal = proposal;
        this.advisor = advisor;
    }
    public Assignment(Student student, Teacher advisor, Proposal proposal) {
        this.student = student;
        this.proposal = proposal;
        this.advisor = advisor;
    }

    public Student getStudent() {
        return student;
    }
    public Proposal getProposal() {
        return proposal;
    }
    public Teacher getAdvisor() {
        return advisor;
    }

    public boolean hasAdvisor() { return advisor!=null; }
    public boolean hasStudent() { return advisor!=null; }

    public void setAdvisor(Teacher advisor) {
        this.advisor = advisor;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append("-> ");
        if(hasStudent())
                sb.append("Student: ").append(student.getStudentNumber()).append(" | ").append(student.getName()).append(" <--> ");
        if(hasAdvisor())
                sb.append("Advisor: ").append(advisor.getEmail()).append(" | ").append(advisor.getName()).append(" <--> ");
        sb.append("Proposal: ").append(proposal.getId()).append(" | ").append(proposal.getTitle()).append("\n");

        return sb.toString();
    }
}
