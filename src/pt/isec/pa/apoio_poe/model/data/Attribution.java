package pt.isec.pa.apoio_poe.model.data;

public class Attribution {
    Student student;
    Proposal proposal;
    Teacher advisor;

    public Attribution(Student student, Proposal proposal) {
        this.student = student;
        this.proposal = proposal;
        this.advisor = null;
    }
    public Attribution(Teacher advisor, Proposal proposal) {
        this.student = null;
        this.proposal = proposal;
        this.advisor = advisor;
    }
    public Attribution(Student student, Teacher advisor, Proposal proposal) {
        this.student = student;
        this.proposal = proposal;
        this.advisor = advisor;
    }


}
