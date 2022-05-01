package pt.isec.pa.apoio_poe.model.data;

import java.io.Serializable;

public class Teacher implements Serializable {
    static final long serialVersionUID = 100L;
    private String email;
    private String name;
    private boolean isAdvisor;  // True if this teacher is an advisor, False if this teacher is a project proposer
    private boolean hasBeenAssigned;    // Flag on whether this teacher has been officially associated with an assignment or not

    public Teacher(String email, String name, boolean isAdvisor) {
        this.email = email;
        this.name = name;
        this.isAdvisor = isAdvisor;
        this.hasBeenAssigned = false;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public boolean isAdvisor() {
        return isAdvisor;
    }

    public boolean isHasBeenAssigned() {
        return hasBeenAssigned;
    }

    public void setHasBeenAssigned(boolean hasBeenAssigned) {
        this.hasBeenAssigned = hasBeenAssigned;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(name + "," + email);

        return sb.toString();
    }

    public String teacherToString() {
        StringBuilder sb = new StringBuilder();
        sb.append("|> " + name + " | " + email +
                (isAdvisor ? "is" : "isn't") + " an advisor");

        return sb.toString();
    }
}
