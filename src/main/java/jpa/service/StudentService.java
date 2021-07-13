package jpa.service;

import jpa.dao.DatabaseDAO;
import jpa.dao.StudentDAO;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.xml.crypto.Data;
import java.util.List;
import java.util.stream.Collectors;

public class StudentService implements StudentDAO {

    @Override
    public List<Student> getAllStudents() {
        String query = "SELECT s from Student s ORDER BY s.sEmail";
        Query q = DatabaseDAO.entityManager.createQuery(query);
        return (List<Student>) q.getResultList();
    }

    @Override
    public Student getStudentByEmail(String sEmail) throws NoResultException {
        Query q = DatabaseDAO.entityManager.createQuery("SELECT s from Student s WHERE s.sEmail=:email");
        q.setParameter("email", sEmail);
        return (Student) q.getSingleResult();


    }
    /* Afterthoughts:
    I realized I probably don't even need to do a SQL query for this one even though it's probably a better idea
    I could just getAllStudents() then see if that List contains a Student with the email matches sEmail
    I'm not sure which would be most efficient or the "right" way to do it though
    */

    @Override
    public boolean validateStudent(String sEmail, String sPassword) throws NoResultException{
        Student student;
        student = getStudentByEmail(sEmail);
        if (student.getsPass().equals(sPassword))
            return true;
        return false;
    }
    /*  Afterthoughts:
    I feel like I could also have done something like this but this is a little more involved
    EDIT: this did work but the above is less lines so I used that instead

    Query q = DatabaseDAO.entityManager.createQuery("SELECT s from Student s WHERE s.sEmail=:email AND s.sPass=:password");
    q.setParameter("email", sEmail);
    q.setParameter("password", sPassword);
    try{
        Student student = (Student) q.getSingleResult();
        return true;
    }catch(NoResultException e){
        return false;
    }
    return false; */


    @Override
    public void registerStudentToCourse(String sEmail, int cId) throws NoResultException {

        Query q = DatabaseDAO.entityManager.createQuery("SELECT c from Course c WHERE c.cId=:id");
        q.setParameter("id", cId);
        Course course = (Course) q.getSingleResult();
        Student student = getStudentByEmail(sEmail);

        /* I want to say here that even though I'm not using JPQL to access the join table the getsCourses method is
         accessing a List<Courses> that is a many to many relationship which IS accessing that join table */
        if (!student.getsCourses().contains(course)) {
            List<Course> studentCourses = student.getsCourses();
            studentCourses.add(course);
            //saves the changes to the student's courses
            System.out.println("Registered for the course!");
        }
        else{
            System.out.println("Student is already registered in that course!");
        }
    }
    /* Afterthoughts:
    Ok so after some looking around there doesn't seem to be a way to use JPQL to query a join table unless I created
    a new Entity..something like StudentCourse but I feel the above way is better

    EDIT: I think I found another way as well but haven't implemented it all the way yet.
    Native Queries should allow you access to the join table. Once I have the class ids it should be relatively easy to
    1. check if cId is in that list
    2. If it's not in that list insert that class id into the join table with that student's email

    Query q = DatabaseDAO.entityManager.createNativeQuery("SELECT class_id from student_course WHERE student_email = " + sEmail);
    List<Integer> classIds = q.getResultList();
    */



    @Override
    public List<Course> getStudentCourses(String sEmail) {
        //THIS WORKS!

        // EDIT: 2 AM and I finally got this last part to work CORRECTLY (before it added the the course to
        String query = "SELECT c from Student s INNER JOIN s.sCourses c WHERE s.sEmail= :email ORDER BY c.cId";
        Query q = DatabaseDAO.entityManager.createQuery(query);
        q.setParameter("email", sEmail);
        return (List<Course>) q.getResultList();


    }
    /* Afterthoughts:
    I think I could have done this another way as well, something like this

    Query q = DatabaseDAO.entityManager.createQuery("SELECT c from Course c join Student s WHERE s.sEmail=:email ");
    q.setParameter("email", sEmail);
    return (List<Course>) q.getResultList();

    EDIT: NO this wouldn't have worked because it would create a join table with every student and every course aka 10*10 results
    and I wrote this before the I figured out the JPQL join table problem.
    EDIT 2:
    Although this works it didn't order the results because it just added the course to the list and didn't retrieve elemtns from the database
    Student student = getStudentByEmail(sEmail);
    return student.getsCourses();


    Ultimately a lot of the methods in this class rely on the first method getStudentByEmail


    */
}
