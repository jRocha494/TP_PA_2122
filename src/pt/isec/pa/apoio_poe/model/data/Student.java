package pt.isec.pa.apoio_poe.model.data;

import java.io.Serializable;

public class Student implements Comparable<Student>, Serializable, Cloneable {
    static final long serialVersionUID = 100L;
    private long studentNumber;
    private String name;
    private String email;
    private String course;  // "LEI" or "LEI-PL"
    private String branch;  // "DA", "RAS" or "SI"
    private double classification;
    private boolean hasInternshipAccess;   // boolean representing whether this Student is eligible to apply for internships
    private boolean hasProposed;    // True if this student has proposed a project/internship, False if not (Students can only propose once)
    private boolean hasApplication; // True if this student has an associated application
    private boolean hasBeenAssigned;    // Flag on whether this student has been officially associated with an assignment or not

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
        this.hasBeenAssigned = false;
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
    public boolean hasBeenAssigned() {
        return hasBeenAssigned;
    }
    public void setHasBeenAssigned(boolean hasBeenAssigned) {
        this.hasBeenAssigned = hasBeenAssigned;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(studentNumber + " | " + name);
        return sb.toString();
    }

    public String toStringExport() {
        StringBuilder sb = new StringBuilder();

        sb.append(studentNumber + "," + name + "," + email + "," + course +
                "," + branch + "," + classification + "," + hasInternshipAccess);

        return sb.toString();
    }

    public String studentToString(){
        StringBuilder sb = new StringBuilder();
        sb.append("|> ").append(name).append(" | ").append(studentNumber).append(" | ").append(email);
        sb.append(course + " | " + branch);
        sb.append("Classification: " + classification +
                (hasInternshipAccess ? " can" : "can't") + " apply for internships, " +
                (hasProposed ? "has" : "hasn't") + " self-proposed, " +
                (hasApplication ? "has already" : "hasn't") + " applied for a internship/project");

        return sb.toString();
    }

    @Override
    public int compareTo(Student o) {
        return Double.compare(getClassification(),o.getClassification());
    }

    @Override
    public boolean equals(Object o){
        if(o == this)
            return true;

        if(!(o instanceof Student))
            return false;

        Student s = (Student) o;
        return studentNumber == s.studentNumber;
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
