package user;

import camp.Camp;

public class StudentCommittee extends Student {
    protected Camp campIC;
    public StudentCommittee(Student student, Camp camp) {
        this(student.getName(), student.getUserID(), student.getPassword(), student.getFaculty(), camp);
    }
    public StudentCommittee(String name, String userID, String password, String faculty, Camp camp) {
        super(name, userID, password, faculty);
        this.campIC = camp;
    }
}
