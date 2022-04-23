package pt.isec.pa.apoio_poe.model.data;

public class Student {
    private long studentNumber;
    private String name;
    private String email;
    private String course;  // "LEI" or "LEI-PL"
    private String branch;  // "DA", "RAS" or "SI"
    private double classification;
    private boolean internshipAccess;   // boolean representing whether this Student is eligible to apply for internships
    private boolean hasProposed;    // True if this student has proposed a project/internship, False if not (Students can only propose once)
    
    public Student(long studentNumber, String name, String email, String course, String branch, double classification, boolean internshipAccess) {
        this.studentNumber = studentNumber;
        this.name = name;
        this.email = email;
        this.course = course;
        this.branch = branch;
        this.classification = classification;
        this.internshipAccess = internshipAccess;
        this.hasProposed = false;
    }
}
