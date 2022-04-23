package pt.isec.pa.apoio_poe.model.data;

import java.util.HashMap;
import java.util.Map;

public class DataLogic {
    // TODO how to use this map: numberStudents.get('<branchName>').getNmrStudents()/.getNmrProposals()
    Map<String, Wrapper> numberStudents; // Number of students and proposals by branch

    public DataLogic() {
        this.numberStudents = new HashMap<>();
        setup();
    }

    private void setup(){
        // TODO populate numberStudents for other branches
        this.numberStudents.put("DA", new Wrapper(0, 0));   // each branch starts with 0 students and 0 proposals -> to be incremented as they're created
    }

    private class Wrapper{    // to be used on 'numberStudents' hashmap
        int nmrStudents;    // number of students in that branch
        int nmrProposals;   // number of proposals destined for that branch

        public Wrapper(int nmrStudents, int nmrProposals) {
            this.nmrStudents = nmrStudents;
            this.nmrProposals = nmrProposals;
        }

        public int getNmrStudents() { return nmrStudents; }
        public int getNmrProposals() { return nmrProposals; }
        public void setNmrProposals(int nmrProposals) { this.nmrProposals = nmrProposals; }
        public void setNmrStudents(int nmrStudents) { this.nmrStudents = nmrStudents; }
    }
}
