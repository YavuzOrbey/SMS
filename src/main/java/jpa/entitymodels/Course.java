package jpa.entitymodels;

import javax.persistence.*;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name="course")
public class Course {
    @Id
    @Column(name="id", nullable = false)
    private int cId;
    @Column(name="name", nullable = false, length = 50)
    private String cName;
    @Column(name="instructor", nullable = false, length = 50)
    private String cInstructorName;

    @ManyToMany(mappedBy = "sCourses")
    List<Student> cStudents;
    public Course() {
            cId = 1;
            cName = null;
            cInstructorName = null;
    }

    public Course(int cId, String cName, String cInstructorName) {
        this.cId = cId;
        this.cName = cName;
        this.cInstructorName = cInstructorName;
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcInstructorName() {
        return cInstructorName;
    }

    public void setcInstructorName(String cInstructorName) {
        this.cInstructorName = cInstructorName;
    }

    @Override
    public String toString() {
        return String.format("%1$-10d | %2$-30s | %3$-30s\n", cId, cName, cInstructorName);
    }


}
