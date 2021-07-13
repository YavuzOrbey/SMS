package jpa.entitymodels;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="student")
public class Student {
    @Id
    @Column(name="email", nullable = false, length = 50)
    private String sEmail;
    @Column(name="name", nullable = false, length = 50)
    private String sName;
    @Column(name="password", nullable = false, length = 50)
    private String sPass;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name="student_course",
            joinColumns = @JoinColumn(name="student_email"),
            inverseJoinColumns = @JoinColumn(name="class_id")
    )
    private List<Course> sCourses;

    public Student() {
    }

    public Student(String sEmail, String sName, String sPass, List<Course> sCourses) {
        this.sEmail = sEmail;
        this.sName = sName;
        this.sPass = sPass;
        this.sCourses = sCourses;
    }

    public String getsEmail() {
        return sEmail;
    }

    public void setsEmail(String sEmail) {
        this.sEmail = sEmail;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsPass() {
        return sPass;
    }

    public void setsPass(String sPass) {
        this.sPass = sPass;
    }
    public List<Course> getsCourses() {
        return sCourses;
    }

    public void setsCourses(List<Course> sCourses) {
        this.sCourses = sCourses;
    }

    @Override
    public String toString() {
        return "StudentEntity{" +
                "sEmail='" + sEmail + '\'' +
                ", sName='" + sName + '\'';
    }


}
