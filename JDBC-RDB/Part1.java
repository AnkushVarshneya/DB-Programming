/**
 * Author: Ankush Varshneya (100853074)
 * Assignment#4 Part#1
 * This program creates 3 tables.
 * In order to run it, You need to do the following:
 * 1. javac Part1.java
 * 2. java Part1
 */

import java.sql.*;
import java.io.*;
import oracle.jdbc.*;
import oracle.sql.*;
import java.util.*;

public class Part1
{
  // use the same connection for all functions
  public static Connection conn = null;
  
  public static void main(String[] args) {
    Statement stmt = null;
    try {
      // make a new driver manager and connection with thin driver
      DriverManager.registerDriver
        (new oracle.jdbc.driver.OracleDriver());
      System.out.println("Connecting to JDBC...");
      conn = DriverManager.getConnection
        ("jdbc:oracle:thin:@localhost:1521:xe", "oracle", "oracle11g");
      System.out.println("JDBC connected.\n");
     
      // set auto commit false to have more control
      conn.setAutoCommit(false);

      // Create a statement
      stmt = conn.createStatement();
      
      // Create Branch Table and commit
      stmt.executeQuery("CREATE TABLE Branch ( " +
                          "B#       VARCHAR(3), " +
                          "Address  VARCHAR(10), " +
                          "PRIMARY KEY(B#), " +
                          "UNIQUE(Address) " +
                         ")");
      System.out.println("Branch table created.");
      conn.commit();
      
      // Create Customer Table and commit
      stmt.executeQuery("CREATE TABLE Customer ( " +
                          "C#       VARCHAR(5), " +
                          "Name     VARCHAR(10), " +
                          "Status   INTEGER, " +
                          "PRIMARY KEY(C#), " +
                          "UNIQUE(Name), " +
                          "CHECK(Status IN (0, 1, 2, 3)) " +
                         ")");
      System.out.println("Customer table created.");
      conn.commit();
      
      // Create Account Table and commit
      stmt.executeQuery("CREATE TABLE Account ( " +
                          "B#       VARCHAR(3), " +
                          "DLA#     VARCHAR(4), " +
                          "C#       VARCHAR(5), " +
                          "Balance  FLOAT DEFAULT 0 CHECK(Balance>=0), " +
                          "PRIMARY KEY (B#, DLA#), " +
                          "FOREIGN KEY (B#) REFERENCES Branch(B#) ON DELETE CASCADE, " +
                          "FOREIGN KEY (C#) REFERENCES Customer(C#) ON DELETE CASCADE " +
                         ")");
      System.out.println("Account table created.");
      conn.commit();
      
    } catch(SQLException e) {
      // roll back
      if (conn != null) {
        try {
          conn.rollback();
          System.out.println("Rolled Back!");
        } catch (SQLException ei) {
          ei.printStackTrace(); 
        }
      }
      System.out.println("SQL exception: ");
      System.out.println("Error code = " + e.getErrorCode());
      System.out.println("Error message = " +e.getMessage());
      System.out.println("SQL state = " + e.getSQLState());
      e.printStackTrace();
    } finally {
      try {
        // close statement and connection if not null
        if (stmt != null) {            
          stmt.close();
        }
        if (conn != null) { 
          // and set autocommit back on
          conn.setAutoCommit(true);
          conn.close();
        }           
      } catch (SQLException ei) {
        ei.printStackTrace(); 
      }
    }
  }
}