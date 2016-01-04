/**
 * Author: Ankush Varshneya (100853074)
 * Assignment#5 Part#4
 * This program contains functions and test case
 * In order to run it, You need to do the following:
 * 1. javac as5p4.java
 * 2. java as5p4
 * TODO: prereq check on delete
 */

import java.sql.*;
import java.io.*;
import oracle.jdbc.*;
import oracle.sql.*;
import java.util.*;

public class as5p4
{
  // use the same connection for all functions
  private static Connection conn = null;
  private static boolean debug = false;
  private static ArrayDescriptor courseDescriptor = null;
  private static ArrayDescriptor studentDescriptor = null;
  
  public static void main(String[] args) {
    as5p4 p = new as5p4();    
    p.connect();
    p.setMap();
    p.prompt();
    p.disconnect();
  }
  
  public void connect() {
    try {
      // make a new driver manager and connection with thin driver
      DriverManager.registerDriver
        (new oracle.jdbc.driver.OracleDriver());
      System.out.println("Connecting to JDBC...");
      conn = DriverManager.getConnection
        ("jdbc:oracle:thin:@localhost:1521:xe", "oracle", "oracle11g");
      System.out.println("JDBC connected.\n");
    } catch (SQLException e) {
      e.printStackTrace(); 
    }
  }
  
  public void disconnect() {
    try {
      //close connection if not null
      if (conn != null) {
        conn.close();
      }
    } catch (SQLException e) {
      e.printStackTrace(); 
    }
  }
  
  public void setMap() {
    try {
      if(conn == null)
        connect();
      
      // set the map with custom type
      java.util.Map<String, Class<?>> map = conn.getTypeMap();
      
      map.put(CourseT._SQL_NAME, Class.forName("CourseT"));
      map.put(StudentT._SQL_NAME, Class.forName("StudentT"));
      map.put(GradT._SQL_NAME, Class.forName("GradT"));
      map.put(UGradT._SQL_NAME, Class.forName("UGradT"));
    
      conn.setTypeMap(map);
 
      courseDescriptor = ArrayDescriptor.createDescriptor("COURSE_NT", conn);
      studentDescriptor = ArrayDescriptor.createDescriptor("STUDENT_NT", conn);

    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

 public void prompt() {
    int choice = 0;    
    int prevChoice = 0;
    String courseName;
    String studentName;
    String courseNameA;
    String courseNameB;
    String gender;
    String phone;
    String type;
    
    do {
      prevChoice = choice;
      // clear the screen
      clearScreen();
      
      // decide what to show
      try {
        switch(choice) {
          case 1:
            System.out.println("1. insert_course - Register System");
            System.out.println("------------------------------------");
            courseName = System.console().readLine("Course Name: ");
            insert_course(courseName);
            break;
          case 2:
            System.out.println("2. delete_course - Register System");
            System.out.println("-------------------------------------");
            courseName = System.console().readLine("Course Name: ");
            delete_course(courseName);
            break;
          case 3:
            System.out.println("3. insert_student - Register System");
            System.out.println("-------------------------------------");
            studentName = System.console().readLine("Student Name: ");
            gender = System.console().readLine("Gender (M or F): ");
            phone = System.console().readLine("Phone: ");
            type = System.console().readLine("Student (G or U): ");
            insert_student(studentName, gender, type, phone);
            break;
          case 4:
            System.out.println("4. delete_student - Register System");
            System.out.println("-------------------------------------");
            studentName = System.console().readLine("Student Name: ");
            delete_student(studentName);
            break;
          case 5:
            System.out.println("5. take_course - Register System");
            System.out.println("-------------------------------------");
            studentName = System.console().readLine("Student Name: ");
            courseName = System.console().readLine("Course Name: ");
            take_course(studentName, courseName);
            break;
          case 6:
            System.out.println("6. drop_course - Register System");
            System.out.println("-------------------------------------");
            studentName = System.console().readLine("Student Name: ");
            courseName = System.console().readLine("Course Name: ");
            drop_course(studentName, courseName);
            break;
          case 7:
            System.out.println("7. add_prerequisite - Register System");
            System.out.println("-------------------------------------");
            courseNameA = System.console().readLine("Course Name(prereq): ");
            courseNameB = System.console().readLine("Course Name: ");
            add_prerequisite(courseNameA, courseNameB);
            break;
          case 8:
            System.out.println("8. run_tests - Register System");
            System.out.println("-------------------------------------");
            run_tests();
            break;
          case 0:
            System.out.println("Main Menu - Welcome - Register System");
            System.out.println("-------------------------------------");
            System.out.println("1. insert_course");
            System.out.println("2. delete_course");
            System.out.println("3. insert_student");
            System.out.println("4. delete_student");
            System.out.println("5. take_course");
            System.out.println("6. drop_course");
            System.out.println("7. add_prerequisite");
            System.out.println("8. run_tests");
            System.out.println("Any other Integer to Quit");
            choice = Integer.parseInt(System.console().readLine("Choose what to action to preform: "));
            break;
        }
      }
      catch(Exception e){}
      // set choice to 0 to go back to main page if not there already
      if (prevChoice != 0) {
        choice = 0;
        pressAnyKeyToContinue();
      }
    } while(choice >= 0 && choice <= 8);
  }
  
  private void run_tests() {
  
    // clean db before tests
    clean_data_base();
    
    // ok
    insert_course("Math");
    // Error    
    insert_course("Math");
    // ok
    delete_course("Math");
    // error
    delete_course("Math");
    
    // ok
    insert_student("U1", "M", "U", "123");
    // error
    insert_student("U1", "M", "U", "123");
    // ok
    delete_student("U1");
    // error
    delete_student("U1");

    // ok
    insert_student("G1", "F", "G", "123");
    // error
    insert_student("G1", "F", "G", "123");
    // ok
    delete_student("G1");
    // error
    delete_student("G1");
    
    // ok
    insert_course("Math");    
    // ok
    insert_course("Chem");    
    // ok
    insert_student("U1", "M", "U", "123");
    // ok
    insert_student("G1", "F", "G", "123");
        
    // Error
    take_course("Invalid", "Invalid");
    // Error
    take_course("Invalid", "Math");
    // Error
    take_course("G1", "Invalid");
    // ok
    take_course("U1", "Math");
    // ok
    take_course("G1", "Chem");
    // error
    take_course("U1", "Math");
    // error
    take_course("G1", "Chem");
    // error
    delete_student("U1");
    // error
    delete_student("G1");
    // error
    delete_course("Math");
    // error
    delete_course("Chem");
    
    // error
    add_prerequisite("Envalid", "Envalid");
    // error
    add_prerequisite("Envalid", "Math");
    // error
    add_prerequisite("Math", "Envalid");
    // error
    add_prerequisite("Math", "Math");
    // error
    add_prerequisite("Chem", "Chem");
    // ok
    add_prerequisite("Math", "Chem");

    // error
    drop_course("Invalid", "Invalid");
    // error
    drop_course("Invalid", "Math");
    // error
    drop_course("G1", "Invalid");    
    // ok
    drop_course("U1", "Math");
    // ok
    drop_course("G1", "Chem");
    // error
    drop_course("U1", "Math");
    // error
    drop_course("G1", "Chem");
    
    // Error
    delete_course("Math");
    
    // Ok
    delete_course("Chem");
    // Ok
    delete_course("Math");
    // ok
    delete_student("U1");
    // ok
    delete_student("G1");    
  }
  
  private void clearScreen() {
    System.out.print("\f");
  }
  
  private void pressAnyKeyToContinue() { 
    System.out.println("Press any key to continue...");
    try {
      System.in.read();
    }  
    catch(Exception e){}
  }

  public void insert_course(String courseName) {
    int courseCounter = 0;
    
	  try {
      // set auto commit false to have more control
      conn.setAutoCommit(false);
      
      // check if course exists
      PreparedStatement stmtCE =
        conn.prepareStatement("SELECT COUNT(c#) FROM course WHERE name = ?",
                              ResultSet.TYPE_SCROLL_INSENSITIVE,
                              ResultSet.CONCUR_UPDATABLE);
      stmtCE.setString(1, courseName);
      ResultSet rsCE = stmtCE.executeQuery();
      
      // throw error if it does
      if(rsCE.first() && rsCE.getInt(1) > 0)
        throw new Exception("Unable to insert course, course exists!");

      // close the statement and resultset
      stmtCE.close();
      rsCE.close();
      
      // now get the next avaliable course #
      PreparedStatement stmtC =
        conn.prepareStatement("SELECT VALUE(C) FROM course C ORDER BY VALUE(C).c#",
                              ResultSet.TYPE_SCROLL_INSENSITIVE,
                              ResultSet.CONCUR_UPDATABLE); 
      ResultSet rsC = stmtC.executeQuery();
      while(rsC.next()) {
        if(courseCounter == Integer.parseInt(((CourseT)rsC.getObject(1)).getCnum()))
          courseCounter++;
        else
          break;
      }
      
      // close the statement and resultset
      stmtC.close();
      rsC.close();

      // make the course object
      CourseT course = new CourseT( new Integer(courseCounter).toString(),
                                    courseName,
                                    new oracle.sql.ARRAY(courseDescriptor, conn, null),
                                    new oracle.sql.ARRAY(studentDescriptor, conn, null)
                                  );

      // insert course
      PreparedStatement stmtCI =
        conn.prepareStatement("INSERT INTO course VALUES(?)");
      stmtCI.setObject(1, course);
      stmtCI.executeUpdate();
      
      // close the statement and resultset
      stmtCI.close();
      
      // commit it to DB
      conn.commit();
      
      // let user know course was inserted
      System.out.println("Course inserted with c# = "
                        + new Integer(courseCounter).toString());
      
    } catch (SQLException e) {
      // roll back
      if (conn != null) {
        try {
          conn.rollback();
          System.out.println("Rolled Back!");
        } catch (SQLException ei) {
          ei.printStackTrace(); 
        }
      }
      // let user know course was not inserted
      System.out.println("Unable to insert course.\n"
                        + "Error message = " + e.getMessage()); 
      if(debug) {
        e.printStackTrace();
      }
    } catch (Exception e) {
      System.out.println(e.getMessage()); 
      if(debug) {
        e.printStackTrace();
      }
    } finally {
      try{
        // set autocommit back on
        conn.setAutoCommit(true);
      } catch (SQLException ei) {
        ei.printStackTrace(); 
      }
    }
  }
  
  public void delete_course(String courseName) {
    boolean isDeleted = false;

	  try {
      // set auto commit false to have more control
      conn.setAutoCommit(false);
      
      // check if course is prereq to any other courses
      PreparedStatement stmtP =
        conn.prepareStatement("SELECT DISTINCT "
                              +  "C.name AS cname, "
                              +  "VALUE(P).name AS pcname "
                              +"FROM Course C, TABLE(C.preReqCourses) P "
                              +"WHERE VALUE(P).name = ? ",
                              ResultSet.TYPE_SCROLL_INSENSITIVE,
                              ResultSet.CONCUR_UPDATABLE);
      stmtP.setString(1, courseName);
      ResultSet rsP = stmtP.executeQuery();
      
      // throw error if it is
      if(rsP.first())
        throw new Exception("Unable to delete course, its a prerequisite!");
      
      // close the statement and resultset
      stmtP.close();
      rsP.close();

      // get the course
      PreparedStatement stmtC =
        conn.prepareStatement("SELECT VALUE(C) FROM course C WHERE VALUE(C).name = ?",
                              ResultSet.TYPE_SCROLL_INSENSITIVE,
                              ResultSet.CONCUR_UPDATABLE);
      stmtC.setString(1, courseName);
      ResultSet rsC = stmtC.executeQuery();
      
      // check if course exists and throw error if it does not
      if(!rsC.first())
        throw new Exception("Unable to delete course, course does not exist!");
      
      rsC.beforeFirst();
      while(rsC.next()) {
        // get the course object
        CourseT course = (CourseT) rsC.getObject(1);
        
        // check if course has students enrolled throw error if it does
        ResultSet rsSE = course.getStudentsEnrolled().getResultSet();
        if(rsSE.next())
          throw new Exception("Unable to delete course, students enrolled!");
        
        // delete the course by deleting the row
        rsC.deleteRow();
        isDeleted = true;
      }
      
      // close the statement and resultset
      stmtC.close();
      rsC.close();
      
      // commit it to DB
      conn.commit();
      
      // if nothing was deleted throw an error telling the user
      // Otherwise let the user know course was deleted
      if(!isDeleted)
        throw new Exception("Unable to delete course as its may not exist.");
      else
        System.out.println("Course Deleted!");        
      
    } catch (SQLException e) {
      // roll back
      if (conn != null) {
        try {
          conn.rollback();
          System.out.println("Rolled Back!");
        } catch (SQLException ei) {
          ei.printStackTrace(); 
        }
      }
      // let user know course was not deleted
      System.out.println("Unable to delete course \n"
                        + "Error message = " + e.getMessage()); 
      if(debug) {
        e.printStackTrace();
      }
    } catch (Exception e) {
      System.out.println(e.getMessage()); 
      if(debug) {
        e.printStackTrace();
      }
    } finally {
      try{
        // set autocommit back on
        conn.setAutoCommit(true);
      } catch (SQLException ei) {
        ei.printStackTrace(); 
      }
    }
  }
 
  public void insert_student(String studentName, String gender, String type, String phone) {
    int studentCounter = 0;
    
	  try {
      // set auto commit false to have more control
      conn.setAutoCommit(false);
      
      // check if student exists 
      PreparedStatement stmtSE =
        conn.prepareStatement("SELECT COUNT(s#) FROM student WHERE name = ?",
                              ResultSet.TYPE_SCROLL_INSENSITIVE,
                              ResultSet.CONCUR_UPDATABLE);
      stmtSE.setString(1, studentName);
      ResultSet rsSE = stmtSE.executeQuery();
      
      // throw error if it does
      if(rsSE.first() && rsSE.getInt(1) > 0)
        throw new Exception("Unable to insert student, student exists!");
     
      // close the statement and resultset
      stmtSE.close();
      rsSE.close();
      
      // now get the next avaliable student #
      PreparedStatement stmtS =
        conn.prepareStatement("SELECT VALUE(S) FROM student S ORDER BY VALUE(S).s#",
                              ResultSet.TYPE_SCROLL_INSENSITIVE,
                              ResultSet.CONCUR_UPDATABLE);
      ResultSet rsS = stmtS.executeQuery();
      while(rsS.next()) {
        if(studentCounter == Integer.parseInt(((StudentT)rsS.getObject(1)).getSnum()))
          studentCounter++;
        else
          break;
      }
      
      // close the statement and resultset
      stmtS.close();
      rsS.close();
      
      // make the student object based on type
      StudentT student;
      if((type.equals("G"))||(type.equals("g"))){ // for grads
        student = new GradT(  phone,
                              new Integer(studentCounter).toString(),
                              studentName,
                              gender,
                              new oracle.sql.ARRAY(courseDescriptor, conn, null)                              
        );
      } else { // assume all others undergrads
        student = new UGradT( phone,
                              new Integer(studentCounter).toString(),
                              studentName,
                              gender,
                              new oracle.sql.ARRAY(courseDescriptor, conn, null)
        );
      }
      
      // insert student
      PreparedStatement stmtSI =
        conn.prepareStatement("INSERT INTO student VALUES(?)");
      stmtSI.setObject(1, student);
      stmtSI.executeUpdate();
      
      // close the statement and resultset
      stmtSI.close();

      // commit it to DB
      conn.commit();
      
      // let user know student was inserted
      System.out.println("student inserted with s# = "
                        + new Integer(studentCounter).toString());
      
    } catch (SQLException e) {
      // roll back
      if (conn != null) {
        try {
          conn.rollback();
          System.out.println("Rolled Back!");
        } catch (SQLException ei) {
          ei.printStackTrace(); 
        }
      }
      // let user know student was not inserted
      System.out.println("Unable to insert student.\n"
                        +"Error message = " + e.getMessage()); 
      if(debug) {
        e.printStackTrace();
      }
    } catch (Exception e) {
      System.out.println(e.getMessage()); 
      if(debug) {
        e.printStackTrace();
      }
    } finally {
      try{
        // set autocommit back on
        conn.setAutoCommit(true);
      } catch (SQLException ei) {
        ei.printStackTrace(); 
      }
    }
  }
  
  public void delete_student(String studentName) {
    boolean isDeleted = false;

	  try {
      // set auto commit false to have more control
      conn.setAutoCommit(false);
      
      // get the student
      PreparedStatement stmtS =
        conn.prepareStatement("SELECT VALUE(S) FROM student S WHERE VALUE(S).name = ?",
                              ResultSet.TYPE_SCROLL_INSENSITIVE,
                              ResultSet.CONCUR_UPDATABLE);
      stmtS.setString(1, studentName);
      ResultSet rsS = stmtS.executeQuery();

      // check if student exists and throw error if it does not
      if(!rsS.first())
        throw new Exception("Unable to delete student, student does not exist!");
      
      rsS.beforeFirst();
      while(rsS.next()) {
        // get the student object
        StudentT student = (StudentT) rsS.getObject(1);
        
        // check if student has courses enrolled throw error if it does
        ResultSet rsCE = student.getCoursesEnrolled().getResultSet();
        if(rsCE.next())
          throw new Exception("Unable to delete student, enrolled in courses!");
        
        // delete the student by deleting the row
        rsS.deleteRow();
        isDeleted = true;
      }
      
      // close the statement and resultset
      stmtS.close();
      rsS.close();
      
      // commit it to DB
      conn.commit();
      
      // if nothing was deleted throw an error telling the user
      // Otherwise let the user know student was deleted
      if(!isDeleted)
        throw new Exception("Unable to delete student as its may not exist.");
      else
        System.out.println("Student Deleted!");        
      
    } catch (SQLException e) {
      // roll back
      if (conn != null) {
        try {
          conn.rollback();
          System.out.println("Rolled Back!");
        } catch (SQLException ei) {
          ei.printStackTrace(); 
        }
      }
      // let user know student was not deleted
      System.out.println("Unable to delete student \n"
                        + "Error message = " + e.getMessage()); 
      if(debug) {
        e.printStackTrace();
      }
    } catch (Exception e) {
      System.out.println(e.getMessage()); 
      if(debug) {
        e.printStackTrace();
      }
    } finally {
      try{
        // set autocommit back on
        conn.setAutoCommit(true);
      } catch (SQLException ei) {
        ei.printStackTrace(); 
      }
    }
  }
  
  public void take_course(String studentName, String courseName) {
	  try {
      // set auto commit false to have more control
      conn.setAutoCommit(false);
      
      // get the student
      PreparedStatement stmtS =
        conn.prepareStatement("SELECT REF(S) FROM student S WHERE VALUE(S).name = ?",
                              ResultSet.TYPE_SCROLL_INSENSITIVE,
                              ResultSet.CONCUR_UPDATABLE);
      stmtS.setString(1, studentName);
      ResultSet rsS = stmtS.executeQuery();
     
      // get the course
      PreparedStatement stmtC =
        conn.prepareStatement("SELECT REF(C) FROM course C WHERE VALUE(C).name = ?",
                              ResultSet.TYPE_SCROLL_INSENSITIVE,
                              ResultSet.CONCUR_UPDATABLE);
      stmtC.setString(1, courseName);
      ResultSet rsC = stmtC.executeQuery();
 
      // check if student exists and throw error if it does not
      if(!rsS.first())
        throw new Exception("Unable to take course, student does not exists!");
      
      // check if course exists and throw error if it does not
      if(!rsC.first())
        throw new Exception("Unable to take course, course does not exists!");
 
      // get the student ref
      rsS.first();
      REF studentRef = (REF) rsS.getRef(1);
       
      // get the course ref
      rsC.first();
      REF courseRef = (REF) rsC.getRef(1);
      
      // check if student is enrolled and throw error if it is
      ResultSet rsCE = ((StudentT)studentRef.getObject())
                        .getCoursesEnrolled().getResultSet();
      while(rsCE.next()) {
        // check the refs
        if(((CourseT)(rsCE.getRef(2).getObject())).getName()              
          .equals(((CourseT)(courseRef.getObject())).getName()))
          throw new Exception("Unable to take course, student already enrolled!");
      }

      // check if course has student enrolled and throw error if it is
      ResultSet rsSE = ((CourseT)courseRef.getObject())
                        .getStudentsEnrolled().getResultSet();
      while(rsSE.next()) {
        // check the refs
        if(((StudentT)(rsSE.getRef(2).getObject())).getName()
          .equals(((StudentT)(studentRef.getObject())).getName()))
          throw new Exception("Unable to take course, course already has student enrolled!");
      }
      
      // let student enroll in course by inserting in courses nested table
      PreparedStatement stmtSI =
        conn.prepareStatement(  "INSERT INTO TABLE ( "
                              +   "SELECT C.StudentsEnrolled "
                              +   "FROM course C "
                              +   "WHERE REF(C) = ? "
                              + ") VALUES(?) ",
                              ResultSet.TYPE_SCROLL_INSENSITIVE,
                              ResultSet.CONCUR_UPDATABLE);
      stmtSI.setRef(1, courseRef);
      stmtSI.setRef(2, studentRef);
      stmtSI.executeUpdate();

      // let course enroll student by inserting in students nested table 
      PreparedStatement stmtCI =
        conn.prepareStatement(  "INSERT INTO TABLE ( "
                              +   "SELECT S.CoursesEnrolled "
                              +   "FROM student S "
                              +   "WHERE REF(S) = ? "
                              + ") VALUES(?) ",
                              ResultSet.TYPE_SCROLL_INSENSITIVE,
                              ResultSet.CONCUR_UPDATABLE);
      stmtCI.setRef(1, studentRef);
      stmtCI.setRef(2, courseRef);
      stmtCI.executeUpdate();

      // close the statement and resultset
      stmtC.close();
      rsC.close();
      stmtS.close();
      rsS.close();
      stmtCI.close();
      stmtSI.close();

      // commit it to DB
      conn.commit();
      
      // let user know student was enrolled
      System.out.println("Student enrolled!");
      
    } catch (SQLException e) {
      // roll back
      if (conn != null) {
        try {
          conn.rollback();
          System.out.println("Rolled Back!");
        } catch (SQLException ei) {
          ei.printStackTrace(); 
        }
      }
      // let user know student was not enrolled
      System.out.println("Unable to enrolled student.\n"
                        + "Error message = " + e.getMessage()); 
      if(debug) {
        e.printStackTrace();
      }
    } catch (Exception e) {
      System.out.println(e.getMessage()); 
      if(debug) {
        e.printStackTrace();
      }
    } finally {
      try{
        // set autocommit back on
        conn.setAutoCommit(true);
      } catch (SQLException ei) {
        ei.printStackTrace(); 
      }
    }
  }
  
  public void drop_course(String studentName, String courseName) {
    boolean studentEnrolled = false;
    boolean courseEnrolled = false;
    boolean isDeleted = false;

	  try {
      // set auto commit false to have more control
      conn.setAutoCommit(false);
      
      // get the student
      PreparedStatement stmtS =
        conn.prepareStatement("SELECT REF(S) FROM student S WHERE VALUE(S).name = ?",
                              ResultSet.TYPE_SCROLL_INSENSITIVE,
                              ResultSet.CONCUR_UPDATABLE);
      stmtS.setString(1, studentName);
      ResultSet rsS = stmtS.executeQuery();
     
      // get the course
      PreparedStatement stmtC =
        conn.prepareStatement("SELECT REF(C) FROM course C WHERE VALUE(C).name = ?",
                              ResultSet.TYPE_SCROLL_INSENSITIVE,
                              ResultSet.CONCUR_UPDATABLE);
      stmtC.setString(1, courseName);
      ResultSet rsC = stmtC.executeQuery();
 
      // check if student exists and throw error if it does not
      if(!rsS.first())
        throw new Exception("Unable to drop course, student does not exists!");
      
      // check if course exists and throw error if it does not
      if(!rsC.first())
        throw new Exception("Unable to drop course, course does not exists!");
 
      // get the student ref
      rsS.first();
      REF studentRef = (REF) rsS.getRef(1);
       
      // get the course ref
      rsC.first();
      REF courseRef = (REF) rsC.getRef(1);
      
      // check if student is enrolled and throw error if it is not
      ResultSet rsCE = ((StudentT)studentRef.getObject())
                        .getCoursesEnrolled().getResultSet();
      while(rsCE.next()) {
        // check the refs
        if(((CourseT)(rsCE.getRef(2).getObject())).getName()              
          .equals(((CourseT)(courseRef.getObject())).getName()))
          studentEnrolled = true;
      }
      if (!studentEnrolled)
        throw new Exception("Unable to drop course, student not enrolled!");
      
      // check if course has student enrolled and throw error if it is not
      ResultSet rsSE = ((CourseT)courseRef.getObject())
                        .getStudentsEnrolled().getResultSet();
      while(rsSE.next()) {
        // check the refs
        if(((StudentT)(rsSE.getRef(2).getObject())).getName()
          .equals(((StudentT)(studentRef.getObject())).getName()))
          courseEnrolled = true;
      }
      if(!courseEnrolled)
        throw new Exception("Unable to drop course, course does not have student enrolled!");

      // let course disenroll student by deleting in students nested table 
      PreparedStatement stmtSD =
        conn.prepareStatement( "DELETE FROM TABLE ( "
                              +   "SELECT C.StudentsEnrolled "
                              +   "FROM course C "
                              +   "WHERE REF(C) = ? "
                              + ") S "
                              + "WHERE VALUE(S) = ? ",
                              ResultSet.TYPE_SCROLL_INSENSITIVE,
                              ResultSet.CONCUR_UPDATABLE);
      stmtSD.setRef(1, courseRef);
      stmtSD.setRef(2, studentRef);
      stmtSD.executeUpdate();

      // let student disenroll in course by deleting in courses nested table
      PreparedStatement stmtCD =
        conn.prepareStatement( "DELETE FROM TABLE ( "
                              +   "SELECT S.CoursesEnrolled "
                              +   "FROM student S "
                              +   "WHERE REF(S) = ? "
                              + ") C "
                              + "WHERE VALUE(C) = ? ",
                              ResultSet.TYPE_SCROLL_INSENSITIVE,
                              ResultSet.CONCUR_UPDATABLE);
      stmtCD.setRef(1, studentRef);
      stmtCD.setRef(2, courseRef);
      stmtCD.executeUpdate();
      
      // close the statement and resultset
      stmtC.close();
      rsC.close();
      stmtS.close();
      rsS.close();
      stmtCD.close();
      stmtSD.close();
      
      // commit it to DB
      conn.commit();
      
      // let user know student was disenrolled
      System.out.println("Student disenrolled!");
      
    } catch (SQLException e) {
      // roll back
      if (conn != null) {
        try {
          conn.rollback();
          System.out.println("Rolled Back!");
        } catch (SQLException ei) {
          ei.printStackTrace(); 
        }
      }
      // let user know student was not disenrolled
      System.out.println("Unable to disenrolled student.\n"
                        + "Error message = " + e.getMessage()); 
      if(debug) {
        e.printStackTrace();
      }
    } catch (Exception e) {
      System.out.println(e.getMessage()); 
      if(debug) {
        e.printStackTrace();
      }
    } finally {
      try{
        // set autocommit back on
        conn.setAutoCommit(true);
      } catch (SQLException ei) {
        ei.printStackTrace(); 
      }
    }
  }

  public void add_prerequisite(String courseNameA, String courseNameB) {    
	  try {
      // set auto commit false to have more control
      conn.setAutoCommit(false);

      // check if course are the ssame and throw error
      if(courseNameA.equals(courseNameB))
        throw new Exception("Unable to add prerequisite, and both are the same course!");
 
      // get the course A
      PreparedStatement stmtA =
        conn.prepareStatement("SELECT REF(C) FROM course C WHERE VALUE(C).name = ?",
                              ResultSet.TYPE_SCROLL_INSENSITIVE,
                              ResultSet.CONCUR_UPDATABLE);
      stmtA.setString(1, courseNameA);
      ResultSet rsA = stmtA.executeQuery();
 
      // get the course B
      PreparedStatement stmtB =
        conn.prepareStatement("SELECT REF(C) FROM course C WHERE VALUE(C).name = ?",
                              ResultSet.TYPE_SCROLL_INSENSITIVE,
                              ResultSet.CONCUR_UPDATABLE);
      stmtB.setString(1, courseNameB);
      ResultSet rsB = stmtB.executeQuery();

      // check if course A exists and throw error if it does not
      if(!rsA.first())
        throw new Exception("Unable to add prerequisite, course: "+ courseNameA +" doesn't exists!");
 
      // check if course B exists and throw error if it does not
      if(!rsB.first())
        throw new Exception("Unable to add prerequisite, course: "+ courseNameB +" doesn't exists!");
 
      // get the course A ref
      rsA.first();
      REF courseARef = (REF) rsA.getRef(1);

      // get the course B ref
      rsB.first();
      REF courseBRef = (REF) rsB.getRef(1);

      // check if course A is prerequisite of course B and throw error if it is
      ResultSet rsCP = ((CourseT)courseBRef.getObject())
                        .getPreReqCourses().getResultSet();
      while(rsCP.next()) {
        // check the refs
        if(((CourseT)(rsCP.getRef(2).getObject())).getName()
          .equals(((CourseT)(courseARef.getObject())).getName()))
          throw new Exception("Unable to add prerequisite, as its already a prerequisite!");
      }

      // let course A be a prerequisite of course B by inserting in prereq courses nested table
      PreparedStatement stmtPI =
        conn.prepareStatement(  "INSERT INTO TABLE ( "
                              +   "SELECT C.prereqcourses "
                              +   "FROM course C "
                              +   "WHERE REF(C) = ? "
                              + ") VALUES(?)",
                              ResultSet.TYPE_SCROLL_INSENSITIVE,
                              ResultSet.CONCUR_UPDATABLE);
      stmtPI.setRef(1, courseBRef);
      stmtPI.setRef(2, courseARef);
      stmtPI.executeUpdate();
    
      // close the statement and resultset
      stmtA.close();
      rsA.close();
      stmtB.close();
      rsB.close();
      stmtPI.close();
        
      // commit it to DB
      conn.commit();
      
      // let user know prereq was added
      System.out.println("Added prerequisite!");
      
    } catch (SQLException e) {
      // roll back
      if (conn != null) {
        try {
          conn.rollback();
          System.out.println("Rolled Back!");
        } catch (SQLException ei) {
          ei.printStackTrace(); 
        }
      }
      // let user know prereq was not added
      System.out.println("Unable to add prerequisite.\n"
                        + "Error message = " + e.getMessage()); 
      if(debug) {
        e.printStackTrace();
      }
    } catch (Exception e) {
      System.out.println(e.getMessage()); 
      if(debug) {
        e.printStackTrace();
      }
    } finally {
      try{
        // set autocommit back on
        conn.setAutoCommit(true);
      } catch (SQLException ei) {
        ei.printStackTrace(); 
      }
    }
  } 
  
  public void clean_data_base() {
	  try {
      // set auto commit false to have more control
      conn.setAutoCommit(false);
  
      // delete the tables
  
      PreparedStatement stmt1 =
        conn.prepareStatement("DELETE FROM Student",
                              ResultSet.TYPE_SCROLL_INSENSITIVE,
                              ResultSet.CONCUR_UPDATABLE);
      stmt1.executeUpdate();
      
      PreparedStatement stmt2 =
        conn.prepareStatement("DELETE FROM Course",
                              ResultSet.TYPE_SCROLL_INSENSITIVE,
                              ResultSet.CONCUR_UPDATABLE);
      stmt2.executeUpdate();
      
      stmt1.close();
      stmt2.close();
      
      // commit to db
      conn.commit();
      
      // let user know if able to clean
      System.out.println("cleaned data base.");
      
    } catch (SQLException e) {
      // roll back
      if (conn != null) {
        try {
          
          e.printStackTrace(); 
          conn.rollback();
          System.out.println("Rolled Back!");
        } catch (SQLException ei) {
          ei.printStackTrace(); 
        }
      }
      // let user know if unable to clean
      System.out.println("Unable to clean data base \n" 
                        + "Error message = " + e.getMessage());
    } catch (Exception e) {
      System.out.println(e.getMessage()); 
      if(debug) {
        e.printStackTrace();
      }
    } finally {
      try{
        // set autocommit back on
        conn.setAutoCommit(true);
      } catch (SQLException ei) {
        ei.printStackTrace(); 
      }
    }
  }
}