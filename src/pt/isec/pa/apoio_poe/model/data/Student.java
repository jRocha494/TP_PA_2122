package pt.isec.pa.apoio_poe.model.data;

public class Student {
    private long studentNumber;
    private String name;
    private String email;
    private String course;  // "LEI" or "LEI-PL"
    private String branch;  // "DA", "RAS" or "SI"
    private double classification;
    private boolean hasInternshipAccess;   // boolean representing whether this Student is eligible to apply for internships
    private boolean hasProposed;    // True if this student has proposed a project/internship, False if not (Students can only propose once)
    private boolean hasApplication; // True if this student has an associated application

    public Student(long studentNumber, String name, String email, String course, String branch, double classification, boolean hasInternshipAccess) {
        this.studentNumber = studentNumber;
        this.name = name;
        this.email = email;
        this.course = course;
        this.branch = branch;
        this.classification = classification;
        this.hasInternshipAccess = hasInternshipAccess;
        this.hasProposed = false;
        this.hasApplication = false;
    }

    public long getStudentNumber() {
        return studentNumber;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCourse() {
        return course;
    }

    public String getBranch() {
        return branch;
    }

    public double getClassification() {
        return classification;
    }

    public boolean hasInternshipAccess() {
        return hasInternshipAccess;
    }

    public boolean hasProposed() {
        return hasProposed;
    }

    public boolean hasApplication() {
        return hasApplication;
    }

    public void setHasProposed(boolean hasProposed) {
        this.hasProposed = hasProposed;
    }

    public void setHasApplication(boolean hasApplication) {
        this.hasApplication = hasApplication;
    }

    @Override
    public String toString() { 
        StringBuilder sb = new StringBuilder();

        sb.append(studentNumber + "," + name + "," + email + "," + course +
                "," + branch + "," + classification + "," + hasInternshipAccess);

        return sb.toString();
    }

    public String studentToString(){
        StringBuilder sb = new StringBuilder();
        sb.append("|> " + name + " | " + studentNumber + " | " + email);
        sb.append(course + " | " + branch);
        sb.append("Classification: " + classification +
                (hasInternshipAccess ? " can" : "can't") + " apply for internships, " +
                (hasProposed ? "has" : "hasn't") + " self-proposed, " +
                (hasApplication ? "has already" : "hasn't") + " applied for a internship/project");

        return sb.toString();
    }
}
