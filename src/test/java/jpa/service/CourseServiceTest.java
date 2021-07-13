package jpa.service;

import jpa.dao.DatabaseDAO;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import org.junit.jupiter.api.Test;

import javax.persistence.Query;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseServiceTest {
    CourseService courseService = new CourseService();
    @Test
    void testGetAllCourses() {
        // These aren't REALLY valid tests because I'm not setting the courses under the 'given' label of testing I'm
        // just getting the stuff that's already in the database however I'm setting the List<Course> to that info so maybe?
        Query query = DatabaseDAO.entityManager.createQuery("SELECT c FROM Course c");
        List<Course> expected = query.getResultList(); //given part
        List<Course> actual = courseService.getAllCourses(); // courseService.getAllCourses() is the when part
        assertEquals(expected, actual );
    }
}