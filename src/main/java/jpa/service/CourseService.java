package jpa.service;

import jpa.dao.CourseDAO;
import jpa.dao.DatabaseDAO;
import jpa.entitymodels.Course;

import javax.persistence.Query;
import java.util.List;

public class CourseService implements CourseDAO {

    //sometimes I see classes like this one written as CourseDAOImpl or something similar
    @Override
    public List<Course> getAllCourses() {
        String query = "SELECT c from Course c";
        Query q = DatabaseDAO.entityManager.createQuery(query);
        return q.getResultList();
    }
}
