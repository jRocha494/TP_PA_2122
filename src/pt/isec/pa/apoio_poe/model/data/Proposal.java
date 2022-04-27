package pt.isec.pa.apoio_poe.model.data;

public class Proposal {
    protected String id;  // identification code
    protected String title;
    protected long assignedStudent;   // identification (studentNumber) of the student assigned to said internship (null if no student was assigned yet)

    protected Proposal(String id, String title, long assignedStudent) {
        this.id = id;
        this.title = title;
        this.assignedStudent = assignedStudent;
    }

    protected Proposal(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public long getAssignedStudent() {
        return assignedStudent;
    }
}
