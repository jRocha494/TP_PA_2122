package pt.isec.pa.apoio_poe.model.data;

public class Teacher {
    private String email;
    private String name;
    private boolean isAdvisor;  // True if this teacher is an advisor, False if this teacher is a project proposer

    public Teacher(String email, String name, boolean isAdvisor) {
        this.email = email;
        this.name = name;
        this.isAdvisor = isAdvisor;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
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
