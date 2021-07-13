package jpa.mainrunner;

import jdk.internal.util.xml.impl.Input;
import jpa.dao.DatabaseDAO;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.service.CourseService;
import jpa.service.StudentService;

import javax.persistence.NoResultException;
import java.sql.SQLOutput;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SMSRunner {
    public static void main(String[] args) {
        boolean isRunning = true;
        DatabaseDAO.startDatabase();
        StudentService studentService = new StudentService();
        CourseService courseService = new CourseService();
        Student currentStudent;
        Scanner scan = new Scanner(System.in);
        while(isRunning){
            displayChoices("Welcome! Please choose one of the following options: ", "1. Student Login", "2. Quit");
            try {
                int choice = scan.nextInt();
                scan.nextLine();
                if(choice==1){
                    System.out.print("Enter email: ");
                    String email = scan.nextLine();
                    try{
                        currentStudent = studentService.getStudentByEmail(email);
                        System.out.print("Enter password: ");
                        String password = scan.nextLine();
                        //find student with that email/validate email...exit if invalid
                        /*
                        I originally just used studentService.validateStudent(email, password) but
                        I figured if I already have the student in currentStudent reference variable I might as well just access
                        their email and check if against the input password
                        */
                        if(studentService.validateStudent(currentStudent.getsEmail(), password)){
                            System.out.println("Logging in....");
                        /* As I'm writing this I realize you can retrieve a student's courses in two ways
                            1. By using the Service you can use the getStudentCourses(email) method  OR
                            2. If you have the student object you should just be able to use the getCourses method on the object
                            and in fact my implementation of the StudentService getStudentCourses method does exactly this
                            */
                            boolean loggedIn = true;
                            while(loggedIn){
                                System.out.println("My Classes: ");
                                System.out.printf("%1$-10s | %2$-30s | %3$-30s\n", "Course ID", "Course Name", "Instructor Name");
                                for(Course c: studentService.getStudentCourses(currentStudent.getsEmail())){
                                    System.out.print(c);
                                }

                                displayChoices("Welcome " + currentStudent.getsName() + "! Choose one of the following options: ", "1. Register for a Class", "2. Logout");

                                try {
                                    choice = scan.nextInt();
                                    scan.nextLine();
                                    if (choice == 1) {
                                        System.out.printf("%1$-10s | %2$-30s | %3$-30s\n", "Course ID", "Course Name", "Instructor Name");
                                        for (Course c : courseService.getAllCourses()) {
                                            System.out.print(c);
                                        }
                                        System.out.println("Which course would you like to register to?");
                                        choice = scan.nextInt();
                                        scan.nextLine();
                                        try {
                                            studentService.registerStudentToCourse(currentStudent.getsEmail(), choice);
                                        } catch (NoResultException e) {
                                            System.out.println("Course does not exist!");
                                        }
                                    } else if (choice == 2) {
                                        System.out.println("Logging out....Goodbye!");
                                        loggedIn = false;
                                    }
                                }catch (InputMismatchException e){
                                    scan.nextLine();
                                    System.out.println("Invalid! Try again.");
                                }
                            }
                        }else{
                            System.out.println("Wrong Credentials");
                        }
                    }
                    catch(NoResultException e){
                        System.out.println("Wrong Credentials");
                    }
                }else if(choice==2){
                    isRunning = false;
                }
            }catch (InputMismatchException e){
                scan.nextLine();
                System.out.println("Invalid! Try again.");
            }
        }

        DatabaseDAO.closeDatabase();
        System.out.println("Application shutting down...Goodbye!");
    }

    public static void displayChoices(String intro, String ...choices){
        System.out.println(intro);
        for(String choice: choices){
            System.out.println(choice);
        }
    }
}
