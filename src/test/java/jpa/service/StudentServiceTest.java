package jpa.service;

import jpa.dao.DatabaseDAO;
import jpa.entitymodels.Student;
import org.junit.jupiter.api.Test;

import javax.persistence.Query;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {
    StudentService studentService = new StudentService();
    @Test
    void testGetAllStudents() {
        // I feel like I'm just repeating myself with this unit test its really just doing the same thing as the method
        // and making sure they are both equal. Whats the point?
        Query query = DatabaseDAO.entityManager.createQuery("SELECT s FROM Student s");
        List<Student> expected = query.getResultList();
        List<Student> actual =  studentService.getAllStudents();
        assertEquals(expected, actual);
    }

    @Test
    void testGetStudentByEmail() {
        Query query = DatabaseDAO.entityManager.createQuery("SELECT s FROM Student s WHERE s.sName='Hazel Luckham'");
        Student actual = (Student) query.getSingleResult();
        assertEquals(actual, studentService.getStudentByEmail("hluckham0@google.ru"));
    }

    @Test
    void testValidateStudent() {
        assertTrue(studentService.validateStudent("hluckham0@google.ru", "X1uZcoIh0dj"));
    }

    /* I don't think I should test this because I don't want to just put something into the database and then delete it
    and all the problems associated with that

    @Test
    void registerStudentToCourse() {

    }*/

    @Test
    void testGetStudentCourses() {
        Query query = DatabaseDAO.entityManager.createQuery("SELECT s FROM Student s WHERE s.sEmail='hluckham0@google.ru'");
        Student hluckham0 = (Student) query.getSingleResult();
        assertEquals(hluckham0.getsCourses(), studentService.getStudentCourses("hluckham0@google.ru"));
    }
}