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
  
  public void insert_course(String courseName) {
    int courseCounter = 0;
    PreparedStatement stmtCE = null;
    PreparedStatement stmtC = null;
    
	  try {
      // set auto commit false to have more control
      conn.setAutoCommit(false);
      
      // check if course exists throw error if it does
      stmtCE = conn.prepareStatement("SELECT COUNT(c#) FROM course WHERE name = ?",
                                     ResultSet.TYPE_SCROLL_INSENSITIVE,
                                     ResultSet.CONCUR_UPDATABLE);
      stmtCE.setString(1, courseName);
      ResultSet rsCE = stmtCE.executeQuery();
      if(rsCE.first() && rsCE.getInt(1) > 0)
        throw new Exception("Unable to insert course, course exists!");
     
      // else get the next avaliable course #
      stmtC = conn.prepareStatement("SELECT c#, name, preReqCourses, studentsEnrolled "
                                    +"FROM course ORDER BY c#",
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_UPDATABLE);                                              
      ResultSet rsC = stmtC.executeQuery();
      rsC.beforeFirst();
      while(rsC.next()) {
        if(courseCounter == Integer.parseInt(rsC.getString("c#")))
          courseCounter++;
        else
          break;
      }
      
      // create a blank row in the result set
      rsC.moveToInsertRow();
      // set the value for the new row
      rsC.updateString("c#", String.format("%03d", courseCounter));
      rsC.updateString("name", courseName);
      // empty for now!
      rsC.updateString("preReqCourses", "course_nt()");
      // empty for now!
      rsC.updateString("studentsEnrolled", "student_nt()");
      // insert the new row
      rsC.insertRow();
      
      // commit it to DB
      conn.commit();
      // let user know course was inserted
      System.out.println("Course inserted with c# = " + String.format("%03d", courseCounter));
      
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
      System.out.println("Unable to insert course.\n" + "Error message = " + e.getMessage());
    } catch (Exception e) {
      System.out.println(e.getMessage()); 
      if(debug) {
        e.printStackTrace();
      }
    } finally {
      try{
        // close statement and result sets if not null
        if (stmtCE != null)
          stmtCE.close();
        if (stmtC != null)
          stmtC.close();
        // and set autocommit back on
        conn.setAutoCommit(true);
      } catch (SQLException ei) {
        ei.printStackTrace(); 
      }
    }
  }
  
  public void delete_course(String courseName) {    
    boolean isDeleted = false;
    PreparedStatement stmtSE = null;    
    PreparedStatement stmtC = null;

	  try {
      // set auto commit false to have more control
      conn.setAutoCommit(false);
      
      // check if course has students enrolled throw error if it does
      stmtSE = conn.prepareStatement("SELECT COUNT(VALUE(S)) FROM course C, "
                                     +"TABLE(C.studentsEnrolled) S WHERE C.name = ?",
                                     ResultSet.TYPE_SCROLL_INSENSITIVE,
                                     ResultSet.CONCUR_UPDATABLE);
      stmtSE.setString(1, courseName);
      ResultSet rsSE = stmtSE.executeQuery();
      if(rsSE.first() && rsSE.getInt(1) > 0)
        throw new Exception("Unable to delete course, students enrolled!");
        
      // get the course and delete it!
      stmtC = conn.prepareStatement("SELECT c#, name FROM course WHERE name = ?",
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_UPDATABLE);
      stmtC.setString(1, courseName);
      ResultSet rsC = stmtC.executeQuery();
      
      // delete the course by deleting the row(s)
      rsC.beforeFirst();
      while(rsC.next()) {
        rsC.deleteRow();
        isDeleted = true;
      }
      
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
      System.out.println("Unable to delete course \n" + "Error message = " + e.getMessage());
    } catch (Exception e) {
      System.out.println(e.getMessage()); 
      if(debug) {
        e.printStackTrace();
      }
    } finally {
      try{
        // close statement if not null
        if (stmtSE != null)  
          stmtSE.close();
        if (stmtC != null)  
          stmtC.close();
        // and set autocommit back on
        conn.setAutoCommit(true);
      } catch (SQLException ei) {
        ei.printStackTrace(); 
      }
    }
  }
 
  public void insert_student(String studentName, String gender, String type) {
    int studentCounter = 0;
    PreparedStatement stmtSE = null;
    PreparedStatement stmtS = null;
    
	  try {
      // set auto commit false to have more control
      conn.setAutoCommit(false);
      
      // check if student exists throw error if it does
      stmtSE = conn.prepareStatement("SELECT COUNT(s#) FROM student WHERE name = ?",
                                     ResultSet.TYPE_SCROLL_INSENSITIVE,
                                     ResultSet.CONCUR_UPDATABLE);
      stmtSE.setString(1, studentName);
      ResultSet rsSE = stmtSE.executeQuery();
      if(rsSE.first() && rsSE.getInt(1) > 0)
        throw new Exception("Unable to insert student, student exists!");
     
      // else get the next avaliable student #
      stmtS = conn.prepareStatement("SELECT s#, name, gender, coursesEnrolled "
                                    +"FROM student ORDER BY s#",
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_UPDATABLE);                                              
      ResultSet rsS = stmtS.executeQuery();
      rsS.beforeFirst();
      while(rsS.next()) {
        if(studentCounter == Integer.parseInt(rsS.getString("s#")))
          studentCounter++;
        else
          break;      
      }
      
      // create a blank row in the result set
      rsS.moveToInsertRow();
      // set the value for the new row
      rsS.updateString("s#", String.format("%03d", studentCounter));
      rsS.updateString("name", studentName);
      // empty for now!
      rsS.updateString("gender", gender);
      // empty for now!
      rsS.updateString("coursesEnrolled", "course_nt()");
      // insert the new row
      rsS.insertRow();
      
      // commit it to DB
      conn.commit();
      // let user know student was inserted
      System.out.println("student inserted with s# = " + String.format("%03d", studentCounter));
      
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
      System.out.println("Unable to insert student.\n" + "Error message = " + e.getMessage());
    } catch (Exception e) {
      System.out.println(e.getMessage()); 
      if(debug) {
        e.printStackTrace();
      }
    } finally {
      try{
        // close statement if not null
        if (stmtSE != null)
          stmtSE.close();
        if (stmtS != null)
          stmtS.close();
        // and set autocommit back on
        conn.setAutoCommit(true);
      } catch (SQLException ei) {
        ei.printStackTrace(); 
      }
    }
  }
  
  public void delete_student(String studentName) {    
    boolean isDeleted = false;
    PreparedStatement stmtCE = null;
    PreparedStatement stmtS = null;

	  try {
      // set auto commit false to have more control
      conn.setAutoCommit(false);
      
      // check if student has courses enrolled throw error if it does
      stmtCE = conn.prepareStatement("SELECT COUNT(VALUE(C)) FROM student S, "
                                     +"TABLE(S.coursesEnrolled) C WHERE S.name = ?",
                                     ResultSet.TYPE_SCROLL_INSENSITIVE,
                                     ResultSet.CONCUR_UPDATABLE);
      stmtCE.setString(1, studentName);
      ResultSet rsCE = stmtCE.executeQuery();
      if(rsCE.first() && rsCE.getInt(1) > 0)
        throw new Exception("Unable to delete student, enrolled in courses!");
        
      // get the student and delete it!
      stmtS = conn.prepareStatement("SELECT s#, name FROM student WHERE name = ?",
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_UPDATABLE);
      stmtS.setString(1, studentName);
      ResultSet rsS = stmtS.executeQuery();
      
      // delete the student by deleting the row(s)
      rsS.beforeFirst();
      while(rsS.next()) {
        rsS.deleteRow();
        isDeleted = true;
      }
      
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
      System.out.println("Unable to delete student \n" + "Error message = " + e.getMessage());
    } catch (Exception e) {
      System.out.println(e.getMessage()); 
      if(debug) {
        e.printStackTrace();
      }
    } finally {
      try{
        // close statement if not null
        if (stmtCE != null)  
          stmtCE.close();
        if (stmtS != null)  
          stmtS.close();
        // and set autocommit back on
        conn.setAutoCommit(true);
      } catch (SQLException ei) {
        ei.printStackTrace(); 
      }
    }
  }
  
  public void take_course(String studentName, String courseName) {
    PreparedStatement stmtSE = null;
    PreparedStatement stmtCE = null;
    PreparedStatement stmtSEn = null;
    PreparedStatement stmtCEn = null;
    PreparedStatement stmtS = null;
    PreparedStatement stmtC = null;
    
	  try {
      // set auto commit false to have more control
      conn.setAutoCommit(false);
      
      // check if student exists and throw error if it does not
      stmtSE = conn.prepareStatement("SELECT COUNT(s#) FROM student WHERE name = ?",
                                     ResultSet.TYPE_SCROLL_INSENSITIVE,
                                     ResultSet.CONCUR_UPDATABLE);
      stmtSE.setString(1, studentName);
      ResultSet rsSE = stmtSE.executeQuery();
      if(rsSE.first() && rsSE.getInt(1) <= 0)
        throw new Exception("Unable to take course, student does not exists!");
      
      // check if course exists throw error if it does not
      stmtCE = conn.prepareStatement("SELECT COUNT(c#) FROM course WHERE name = ?",
                                     ResultSet.TYPE_SCROLL_INSENSITIVE,
                                     ResultSet.CONCUR_UPDATABLE);
      stmtCE.setString(1, courseName);
      ResultSet rsCE = stmtCE.executeQuery();
      if(rsCE.first() && rsCE.getInt(1) <= 0)
        throw new Exception("Unable to take course, course does not exists!");

      // check if student is enrolled and throw error if it is
      stmtSEn = conn.prepareStatement(  "SELECT COUNT(S.name) "
                                      + "FROM Student S, TABLE(S.coursesEnrolled) C "
                                      + "WHERE VALUE(C).name = ? AND S.name = ? ",
                                      ResultSet.TYPE_SCROLL_INSENSITIVE,
                                      ResultSet.CONCUR_UPDATABLE);
      stmtSEn.setString(1, courseName);
      stmtSEn.setString(2, studentName);
      ResultSet rsSEn = stmtSEn.executeQuery();
      if(rsSEn.first() && rsSEn.getInt(1) > 0)
        throw new Exception("Unable to take course, student already enrolled!");
      
      // check if course has student enrolled and throw error if it is
      stmtCEn = conn.prepareStatement(  "SELECT COUNT(C.name) "
                                      + "FROM Course C, TABLE(C.studentsEnrolled) S "
                                      + "WHERE VALUE(S).name = ? AND C.name = ? ",
                                      ResultSet.TYPE_SCROLL_INSENSITIVE,
                                      ResultSet.CONCUR_UPDATABLE);
      stmtCEn.setString(1, studentName);
      stmtCEn.setString(2, courseName);
      ResultSet rsCEn = stmtCEn.executeQuery();
      if(rsCEn.first() && rsCEn.getInt(1) > 0)
        throw new Exception("Unable to take course, course already has student enrolled!");
     
      // let student take course
      stmtS = conn.prepareStatement(  "INSERT INTO TABLE ( "
                                    +   "SELECT C.StudentsEnrolled "
                                    +   "FROM course C "
                                    +   "WHERE C.name = ? "
                                    + ") "
                                    + "SELECT REF(S) "
                                    + "FROM student S "
                                    + "WHERE S.name = ? ",
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_UPDATABLE);
      stmtS.setString(1, courseName);
      stmtS.setString(2, studentName);
      stmtS.executeUpdate();

      // let course include student
      stmtC = conn.prepareStatement(  "INSERT INTO TABLE ( "
                                    +   "SELECT S.CoursesEnrolled "
                                    +   "FROM student S "
                                    +   "WHERE S.name = ? "
                                    + ") "
                                    + "SELECT REF(C) "
                                    + "FROM course C "
                                    + "WHERE C.name = ? ",
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_UPDATABLE);
      stmtC.setString(1, studentName);
      stmtC.setString(2, courseName);
      stmtC.executeUpdate();
      
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
      System.out.println("Unable to enrolled student.\n" + "Error message = " + e.getMessage());
    } catch (Exception e) {
      System.out.println(e.getMessage()); 
      if(debug) {
        e.printStackTrace();
      }
    } finally {
      try{
        // close statement if not null
        if (stmtSE != null)
          stmtSE.close();
        if (stmtCE != null)
          stmtCE.close();
        if (stmtSEn != null)
          stmtSEn.close();
        if (stmtCEn != null)
          stmtCEn.close();
        if (stmtS != null)
          stmtS.close();
        if (stmtC != null)
          stmtC.close();
        // and set autocommit back on
        conn.setAutoCommit(true);
      } catch (SQLException ei) {
        ei.printStackTrace(); 
      }
    }
  }
  
  public void drop_course(String studentName, String courseName) {
    PreparedStatement stmtSE = null;
    PreparedStatement stmtCE = null;
    PreparedStatement stmtSEn = null;
    PreparedStatement stmtCEn = null;
    PreparedStatement stmtS = null;
    PreparedStatement stmtC = null;
    
	  try {
      // set auto commit false to have more control
      conn.setAutoCommit(false);
      
      // check if student exists and throw error if it does not
      stmtSE = conn.prepareStatement("SELECT COUNT(s#) FROM student WHERE name = ?",
                                     ResultSet.TYPE_SCROLL_INSENSITIVE,
                                     ResultSet.CONCUR_UPDATABLE);
      stmtSE.setString(1, studentName);
      ResultSet rsSE = stmtSE.executeQuery();
      if(rsSE.first() && rsSE.getInt(1) <= 0)
        throw new Exception("Unable to drop course, student does not exists!");
      
      // check if course exists throw error if it does not
      stmtCE = conn.prepareStatement("SELECT COUNT(c#) FROM course WHERE name = ?",
                                     ResultSet.TYPE_SCROLL_INSENSITIVE,
                                     ResultSet.CONCUR_UPDATABLE);
      stmtCE.setString(1, courseName);
      ResultSet rsCE = stmtCE.executeQuery();
      if(rsCE.first() && rsCE.getInt(1) <= 0)
        throw new Exception("Unable to drop course, course does not exists!");

      // check if student is enrolled and throw error if it is not
      stmtSEn = conn.prepareStatement(  "SELECT COUNT(S.name) "
                                      + "FROM Student S, TABLE(S.coursesEnrolled) C "
                                      + "WHERE VALUE(C).name = ? AND S.name = ? ",
                                      ResultSet.TYPE_SCROLL_INSENSITIVE,
                                      ResultSet.CONCUR_UPDATABLE);
      stmtSEn.setString(1, courseName);
      stmtSEn.setString(2, studentName);
      ResultSet rsSEn = stmtSEn.executeQuery();
      if(rsSEn.first() && rsSEn.getInt(1) <= 0)
        throw new Exception("Unable to drop course, student not enrolled!");
      
      // check if course has student enrolled and throw error if it is not
      stmtCEn = conn.prepareStatement(  "SELECT COUNT(C.name) "
                                      + "FROM Course C, TABLE(C.studentsEnrolled) S "
                                      + "WHERE VALUE(S).name = ? AND C.name = ? ",
                                      ResultSet.TYPE_SCROLL_INSENSITIVE,
                                      ResultSet.CONCUR_UPDATABLE);
      stmtCEn.setString(1, studentName);
      stmtCEn.setString(2, courseName);
      ResultSet rsCEn = stmtCEn.executeQuery();
      if(rsCEn.first() && rsCEn.getInt(1) <= 0)
        throw new Exception("Unable to drop course, course does not have student enrolled!");
     
      // let student drop course
      stmtS = conn.prepareStatement(  "DELETE FROM TABLE ( "
                                    +   "SELECT C.StudentsEnrolled "
                                    +   "FROM course C "
                                    +   "WHERE C.name = ? "
                                    + ") S"
                                    + "WHERE DEREF(S).name = ? ",
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_UPDATABLE);
      stmtS.setString(1, courseName);
      stmtS.setString(2, studentName);
      stmtS.executeUpdate();

      // let course exclude student
      stmtC = conn.prepareStatement(  "DELETE FROM TABLE ( "
                                    +   "SELECT S.CoursesEnrolled "
                                    +   "FROM student S "
                                    +   "WHERE S.name = ? "
                                    + ") C"
                                    + "WHERE DEREF(C).name = ? ",
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_UPDATABLE);
      stmtC.setString(1, studentName);
      stmtC.setString(2, courseName);
      stmtC.executeUpdate();
      
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
      System.out.println("Unable to disenrolled student.\n" + "Error message = " + e.getMessage());
    } catch (Exception e) {
      System.out.println(e.getMessage()); 
      if(debug) {
        e.printStackTrace();
      }
    } finally {
      try{
        // close statement if not null
        if (stmtSE != null)
          stmtSE.close();
        if (stmtCE != null)
          stmtCE.close();
        if (stmtSEn != null)
          stmtSEn.close();
        if (stmtCEn != null)
          stmtCEn.close();
        if (stmtS != null)
          stmtS.close();
        if (stmtC != null)
          stmtC.close();
        // and set autocommit back on
        conn.setAutoCommit(true);
      } catch (SQLException ei) {
        ei.printStackTrace(); 
      }
    }
  }

  public void add_prerequisite(String courseA, String courseB) {
    int courseCounter = 0;
    PreparedStatement stmtCEA = null;
    PreparedStatement stmtCEB = null;
    PreparedStatement stmtCE = null;
    PreparedStatement stmtC = null;
    
	  try {
      // set auto commit false to have more control
      conn.setAutoCommit(false);
      
      // check if course does exists throw error if it does not
      stmtCEA = conn.prepareStatement("SELECT COUNT(c#) FROM course WHERE name = ?",
                                      ResultSet.TYPE_SCROLL_INSENSITIVE,
                                      ResultSet.CONCUR_UPDATABLE);
      stmtCEA.setString(1, courseA);
      ResultSet rsCEA = stmtCEA.executeQuery();
      if(rsCEA.first() && rsCEA.getInt(1) <= 0)
        throw new Exception("Unable to add prerequisite, course: "+ courseA +" doesn't exists!");

      // check if course does exists throw error if it does not
      stmtCEB = conn.prepareStatement("SELECT COUNT(c#) FROM course WHERE name = ?",
                                     ResultSet.TYPE_SCROLL_INSENSITIVE,
                                     ResultSet.CONCUR_UPDATABLE);
      stmtCEB.setString(1, courseB);
      ResultSet rsCEB = stmtCEB.executeQuery();
      if(rsCEB.first() && rsCEB.getInt(1) <= 0)
        throw new Exception("Unable to add prerequisite, course: "+ courseB +" doesn't exists!");
            
      // check if course is prereq
      stmtCE = conn.prepareStatement( "SELECT DISTINCT C.name AS cname, VALUE(P).name AS pcname "
                                     +"FROM Course C, TABLE(C.preReqCourses) P "
                                     +"WHERE C.name = ? AND VALUE(P).name = ?",
                                     ResultSet.TYPE_SCROLL_INSENSITIVE,
                                     ResultSet.CONCUR_UPDATABLE);
      stmtCE.setString(1, courseB);
      stmtCE.setString(2, courseA);
      ResultSet rsCE = stmtCE.executeQuery();
      if(rsCE.first() && rsCE.getInt(1) <= 0)
        throw new Exception("Unable to add prerequisite, as its already a prerequisite!");
      
      // let course include student
      stmtC = conn.prepareStatement(  "INSERT INTO TABLE ( "
                                    +   "SELECT C.prereqcourses "
                                    +   "FROM course C "
                                    +   "WHERE C.name = ? "
                                    + ") "
                                    + "SELECT REF(P) "
                                    + "FROM course P "
                                    + "WHERE P.NAME = ? ",
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_UPDATABLE);
      stmtC.setString(1, courseB);
      stmtC.setString(2, courseA);
      stmtC.executeUpdate();
      
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
      System.out.println("Unable to add prerequisite.\n" + "Error message = " + e.getMessage());
    } catch (Exception e) {
      System.out.println(e.getMessage()); 
      if(debug) {
        e.printStackTrace();
      }
    } finally {
      try{
        // close statement if not null
        if (stmtCEA != null)
          stmtCEA.close();
        if (stmtCEB != null)
          stmtCEB.close();
        if (stmtC != null)
          stmtC.close();
        // and set autocommit back on
        conn.setAutoCommit(true);
      } catch (SQLException ei) {
        ei.printStackTrace(); 
      }
    }
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
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  public static void main(String[] args) {
    as5p4 p = new as5p4();    
    p.connect();
    p.setMap();
    // do tests
    p.disconnect();  
  }
}