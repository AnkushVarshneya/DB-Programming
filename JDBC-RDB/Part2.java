/**
 * Author: Ankush Varshneya (100853074)
 * Assignment#4 Part#2
 * This program contains functions for UI.
 * In order to run it, You need to do the following:
 * 1. javac Part2.java
 * 2. java Part2
 */

import java.sql.*;
import java.io.*;
import oracle.jdbc.*;
import oracle.sql.*;
import java.util.*;

public class Part2
{
  // use the same connection for all functions
  private static Connection conn = null;
  private static boolean debug = false;
  
  public void open_branch(String address) {    
    int branchCounter = 0;
    PreparedStatement stmt = null;
    
	  try {
      // set auto commit false to have more control
      conn.setAutoCommit(false);

      // get the branch number to use
      stmt = conn.prepareStatement("SELECT B#, Address FROM Branch ORDER BY B#",
                                   ResultSet.TYPE_SCROLL_INSENSITIVE,
                                   ResultSet.CONCUR_UPDATABLE);                                              
      ResultSet rs = stmt.executeQuery();
      rs.beforeFirst();
      while(rs.next()) {
        if(branchCounter == Integer.parseInt(rs.getString("B#")))
          branchCounter++;
        else
          break;      
      }
      
      // create a blank row in the result set
      rs.moveToInsertRow();
      // set the value for the new row
      rs.updateString("B#", String.format("%03d", branchCounter));
      rs.updateString("Address", address);
      // insert the new row
      rs.insertRow();
      
      // commit it to DB
      conn.commit();
      // let user know branch was opened
      System.out.println("Branch Opened with B# = " + String.format("%03d", branchCounter));
      
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
      // let user know branch was not opened
      System.out.println("Unable to Open Branch \n" + "Error message = " + e.getMessage());
    } catch (Exception e) {
      System.out.println(e.getMessage()); 
      if(debug) {
        e.printStackTrace();
      }
    } finally {
      try{
        // close statement if not null
        if (stmt != null)          
          stmt.close();
        // and set autocommit back on
        conn.setAutoCommit(true);
      } catch (SQLException ei) {
        ei.printStackTrace(); 
      }
    }
  }
  
  public void close_branch(String branch) {    
    boolean isDeleted = false;
    boolean isAddress = false;
    PreparedStatement stmt = null;

    // if it cant be parsed assume its an address
    try {
      Integer.parseInt(branch);
    } catch (NumberFormatException e) {
      isAddress = true;
    }
    
	  try {
      // set auto commit false to have more control
      conn.setAutoCommit(false);
    
      // get the proper query based on isAddress
      stmt = conn.prepareStatement("SELECT B#, Address FROM Branch WHERE "
                                   + (isAddress? "Address":"B#") + " = ?",
                                   ResultSet.TYPE_SCROLL_INSENSITIVE,
                                   ResultSet.CONCUR_UPDATABLE);
      stmt.setString(1, branch);
      
      ResultSet rs = stmt.executeQuery();
      
      // close the branch(s) by deteting the row(s)
      rs.beforeFirst();
      while(rs.next()) {
        rs.deleteRow();
        isDeleted = true;
      }
      
      // commit it to DB
      conn.commit();
      
      // if nothing was deleted throw an error telling the user
      // Otherwise let the user know branch was closed
      if(!isDeleted)
        throw new Exception("Unable to Close Branch as its not open.");
      else
        System.out.println("Branch closed!");        
      
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
      // let the user know branch was not closed
      System.out.println("Unable to Close Branch \n" + "Error message = " + e.getMessage());
    } catch (Exception e) {
      System.out.println(e.getMessage()); 
      if(debug) {
        e.printStackTrace();
      }
    } finally {
      try{
        // close statement if not null
        if (stmt != null)            
          stmt.close();
        // and set autocommit back on
        conn.setAutoCommit(true);
      } catch (SQLException ei) {
        ei.printStackTrace(); 
      }
    }
  }
  
  public void setup_account(String name, String branch, float amount) {    
    int accountCounter = 0;
    String customerNumber = null;
    boolean isAddress = false;
    PreparedStatement stmtB  = null;
    PreparedStatement stmtBE = null;
    PreparedStatement stmtCE = null;
    PreparedStatement stmtC  = null;
    PreparedStatement stmtA  = null;
    
    // if branch cant be parsed assume its an address
    try {
      Integer.parseInt(branch);
    } catch (NumberFormatException e) {
      isAddress = true;
    }
    
    try {
      // set auto commit false to have more control
      conn.setAutoCommit(false);

      // if address then get B#
      if (isAddress) {
        // get the branch number to use
        stmtB = conn.prepareStatement("SELECT B# FROM Branch WHERE Address = ?",
                                      ResultSet.TYPE_SCROLL_INSENSITIVE,
                                      ResultSet.CONCUR_UPDATABLE);
        stmtB.setString(1, branch);
        ResultSet rsB = stmtB.executeQuery();
        
        // change branch to have B#
        if(rsB.first())
          branch = rsB.getString("B#");
      }

      // get the customer number to use
      stmtC = conn.prepareStatement("SELECT C# FROM Customer WHERE Name = ?",
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_UPDATABLE);
      stmtC.setString(1, name);
      ResultSet rsC = stmtC.executeQuery();
      
      // save the customer number
      if(rsC.first())
        customerNumber = rsC.getString("C#");
      
      // check if the branch exits and throw error if it does not
      stmtBE = conn.prepareStatement("SELECT B# FROM Branch WHERE B# = ?",
                                     ResultSet.TYPE_SCROLL_INSENSITIVE,
                                     ResultSet.CONCUR_UPDATABLE);
      stmtBE.setString(1, branch);
      ResultSet rsBE = stmtBE.executeQuery();
      if(!rsBE.first())
        throw new Exception("Unable to set account, branch not found.");
      
      // check if the customer exits and throw error if it does not
      stmtCE = conn.prepareStatement("SELECT C# FROM Customer WHERE C# = ?",
                                     ResultSet.TYPE_SCROLL_INSENSITIVE,
                                     ResultSet.CONCUR_UPDATABLE);
      stmtCE.setString(1, customerNumber);
      ResultSet rsCE = stmtCE.executeQuery();
      if(!rsCE.first())
        throw new Exception("Unable to set account, customer not found.");
    
      // get the account number to use
      stmtA = conn.prepareStatement("SELECT B#, DLA#, C#, Balance FROM Account WHERE B# = ? ORDER BY DLA#",
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_UPDATABLE);
      stmtA.setString(1, branch);
      ResultSet rsA = stmtA.executeQuery();
      rsA.beforeFirst();
      while(rsA.next()) {
        if(accountCounter == Integer.parseInt(rsA.getString("DLA#")))
          accountCounter++;
        else
          break;
      }
      
      // create a blank row in the result set
      rsA.moveToInsertRow();
      // set the value for the new row
      rsA.updateString("B#", branch);
      rsA.updateString("DLA#", String.format("%04d", accountCounter));
      rsA.updateString("C#", customerNumber);
      rsA.updateFloat("Balance", amount);
      // insert the new row
      rsA.insertRow();
      
      // Set Customers Status
      try {
        set_status(customerNumber);
      } catch (Exception e){
        throw e;
      }
      
      // commit it to DB
      conn.commit();
      
      // let user know account was setup
      System.out.println("Set Account with Account# = " + branch
                         + String.format("%04d", accountCounter));
      
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
      // let user know account was not setup
      System.out.println("Unable to Set Account.\n" + "Error message = " + e.getMessage());
    } catch (Exception e) {
      System.out.println(e.getMessage()); 
      if(debug) {
        e.printStackTrace();
      }
    } finally {
      try{
        // close statement if not null
        if (stmtB != null)
          stmtB  = null;
        if (stmtBE != null)
          stmtBE = null;
        if (stmtCE != null)
          stmtCE = null;
        if (stmtC != null)
          stmtC  = null;
        if (stmtA != null)
          stmtA  = null;
    
        // and set autocommit back on
        conn.setAutoCommit(true);
      } catch (SQLException ei) {
        ei.printStackTrace(); 
      }
    }
  }
  
  public void setup_customer(String name, String branch) {
    setup_customer(name, branch, 0.0f);
  }

  public void setup_customer(String name, String branch, float amount) {
    int customerCounter = 0;
    boolean isAddress = false;
    PreparedStatement stmtB  = null;
    PreparedStatement stmtBE = null;
    PreparedStatement stmtC  = null;
    
    // if branch cant be parsed assume its an address
    try {
      Integer.parseInt(branch);
    } catch (NumberFormatException e) {
      isAddress = true;
    }
    
    try {
      // set auto commit false to have more control
      conn.setAutoCommit(false);

      // if address then get B#
      if (isAddress) {
        // get the branch number to use
        stmtB = conn.prepareStatement("SELECT B# FROM Branch WHERE Address = ?",
                                      ResultSet.TYPE_SCROLL_INSENSITIVE,
                                      ResultSet.CONCUR_UPDATABLE);
        stmtB.setString(1, branch);
        ResultSet rsB = stmtB.executeQuery();
        
        // change branch to have B#
        if(rsB.first())
          branch = rsB.getString("B#");
      }
      
      // check if the branch exits and throw error if it does not
      stmtBE = conn.prepareStatement("SELECT B# FROM Branch WHERE B# = ?",
                                     ResultSet.TYPE_SCROLL_INSENSITIVE,
                                     ResultSet.CONCUR_UPDATABLE);
      stmtBE.setString(1, branch);
      ResultSet rsBE = stmtBE.executeQuery();
      if(!rsBE.first())
        throw new Exception("Unable to set up customer, branch not found.");
     
      // get the customer number to use
      stmtC = conn.prepareStatement("SELECT C#, Name FROM Customer ORDER BY C#",
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_UPDATABLE);
      ResultSet rsC = stmtC.executeQuery();
      rsC.beforeFirst();
      while(rsC.next()) {
        if(customerCounter == Integer.parseInt(rsC.getString("C#")))
          customerCounter++;
        else
          break;
      }
      
      // create a blank row in the result set
      rsC.moveToInsertRow();
      // set the value for the new row
      rsC.updateString("C#", String.format("%05d", customerCounter));
      rsC.updateString("Name", name);
      // try to add the customer and give error if they exist
      try {
        // insert the new row
        rsC.insertRow();
      } catch (Exception e) {
        throw new Exception("Unable to setup customer, as it already exists.");        
      }
        
      // commit it to DB
      conn.commit();
      
      // set their account
      try {
        setup_account(name, branch, amount);
      } catch (Exception e) {
        throw e;
      }

      // let user know the customer was setup
      System.out.println("Set customer with customer# = "
                         + String.format("%05d", customerCounter));
      
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
      // let user know the customer was not setup
      System.out.println("Unable to setup customer.\n" + "Error message = " + e.getMessage());
    } catch (Exception e) {
      System.out.println(e.getMessage()); 
      if(debug) {
        e.printStackTrace();
      }
    } finally {
      try{
        // close statement if not null
        if (stmtB != null)
          stmtB  = null;
        if (stmtBE != null)
          stmtBE = null;
        if (stmtC != null)
          stmtC  = null;
    
        // and set autocommit back on
        conn.setAutoCommit(true);
      } catch (SQLException ei) {
        ei.printStackTrace(); 
      }
    }
  }

  public void close_account(String name, String branch) {    
    boolean isDeleted = false;
    boolean isAddress = false;
    PreparedStatement stmtB = null;
    PreparedStatement stmtA = null;
    PreparedStatement stmtAD = null;
    PreparedStatement stmtLAC = null;
    PreparedStatement stmtLAB = null;
    PreparedStatement stmtC = null;
    
    // if it cant be parsed assume its an address
    try {
      Integer.parseInt(branch);
    } catch (NumberFormatException e) {
      isAddress = true;
    }

	  try {
      // set auto commit false to have more control
      conn.setAutoCommit(false);

      // if address then get B#
      if (isAddress) {
        // get the branch number to use
        stmtB = conn.prepareStatement("SELECT B# FROM Branch WHERE Address = ?",
                                      ResultSet.TYPE_SCROLL_INSENSITIVE,
                                      ResultSet.CONCUR_UPDATABLE);
        stmtB.setString(1, branch);
        ResultSet rsB = stmtB.executeQuery();
        
        // change branch to have B#
        if(rsB.first())
          branch = rsB.getString("B#");
      }
      
      // get all accounts with 0 balance for the customer on a given branch
      stmtA = conn.prepareStatement("SELECT Account.B#||Account.DLA# AS Account# "
                                    + "FROM Account, Customer WHERE Account.C# = Customer.C# AND "
                                    + "Account.Balance = 0 AND Account.B# = ? AND Customer.Name = ?",
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_UPDATABLE);
      stmtA.setString(1, branch);
      stmtA.setString(2, name);

      ResultSet rsA = stmtA.executeQuery();
      
      stmtAD = conn.prepareStatement("DELETE FROM ACCOUNT WHERE B#||DLA# = ?",
                                     ResultSet.TYPE_SCROLL_INSENSITIVE,
                                     ResultSet.CONCUR_UPDATABLE);
      // delete all the account
      rsA.beforeFirst();
      while(rsA.next()) {
        stmtAD.setString(1, rsA.getString("Account#"));
        stmtAD.executeUpdate();
        isDeleted = true;
      }   
      
      // commit it to DB
      conn.commit();

      // if an account was deleted then check if customer and branch are needed
      if(isDeleted) {
        // check account count for the customer for the branch
        stmtLAC = conn.prepareStatement("SELECT Count(Account.C#) FROM Account, Customer WHERE "
                                        + "Account.C# = Customer.C# AND Customer.Name = ?",
                                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_READ_ONLY);
        stmtLAC.setString(1, name);

        ResultSet rsLAC = stmtLAC.executeQuery();
        rsLAC.beforeFirst();
        while(rsLAC.next()) {
          // delete customer if he is not used and commit it to DB
          if(rsLAC.getInt(1) <= 0) {
            stmtC = conn.prepareStatement("DELETE FROM CUSTOMER WHERE Name = ?",
                                          ResultSet.TYPE_SCROLL_INSENSITIVE,
                                          ResultSet.CONCUR_UPDATABLE);
            stmtC.setString(1, name);
            stmtC.executeUpdate();
            conn.commit();
            
            // let user know customer deleted
            System.out.println("Customer deleted!");
          }
        }
        
        // check accounts for a branch
        stmtLAB = conn.prepareStatement("SELECT Count(B#) FROM Account WHERE B# = ?",
                                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_READ_ONLY);
        stmtLAB.setString(1, branch);
        ResultSet rsLAB = stmtLAB.executeQuery();
        rsLAB.beforeFirst();
        while(rsLAB.next()) {
          // delete branch if it is not used
          if(rsLAB.getInt(1) <= 0) {
            try {
              close_branch(branch);
            } catch (Exception e){
              throw e;
            }
          }
        }
        
      // let user know the account was closed
      System.out.println("Account closed!");
      }
      // if nothing was deleted throw an error telling the user
      else
        throw new Exception("Unable to Close Account(s) as all have money or no account exist.");
      
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
      // let user know the account was not closed
      System.out.println("Unable to Close Account(s) \n" + "Error message = " + e.getMessage());
    } catch (Exception e) {
      System.out.println(e.getMessage()); 
      if(debug) {
        e.printStackTrace();
      }
    } finally {
      try{
        // close statement if not null
        if (stmtB != null)            
          stmtB.close();
        if (stmtA != null)
          stmtA.close();
        if (stmtLAC != null)
          stmtLAC.close();
        if (stmtLAB != null)
          stmtLAB.close();
        if (stmtC != null)
          stmtC.close();
    
        // and set autocommit back on
        conn.setAutoCommit(true);
      } catch (SQLException ei) {
        ei.printStackTrace(); 
      }
    }
  }

  public void withdraw(String name, String account, float amount) {    
    boolean isUpdated;
    PreparedStatement stmt = null;
    
	  try {
      // set auto commit false to have more control
      conn.setAutoCommit(false);
      
      // get the account to use
      stmt = conn.prepareStatement("SELECT Balance, C# FROM Account WHERE B#||DLA# = ?",
                                   ResultSet.TYPE_SCROLL_INSENSITIVE,
                                   ResultSet.CONCUR_UPDATABLE);
      stmt.setString(1, account);
      ResultSet rs = stmt.executeQuery();
      if(rs.first()) {
        // check if you can withdraw
        float balance = rs.getFloat("Balance");
        if(balance >= amount) {
          if(amount >= 0) {
            rs.updateFloat("Balance", balance - amount);
            // update the row
            rs.updateRow();
            
            // Set Customers Status
            try {
              set_status(rs.getString("C#"));
            } catch (Exception e) {
              throw e;
            }
            
            // commit it to DB
            conn.commit();
          }
          else
            throw new Exception("Negative amount can not be withdrawn.");
        }
        else
          throw new Exception("Insufficient funds");
      }
      else
        throw new Exception("Invalid account number");
      
      // let user know withdraw was done
      System.out.println("Withdraw complete!");
      
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
      // let user know withdraw was not done
      System.out.println("Unable to Withdraw \n" + "Error message = " + e.getMessage());
    } catch (Exception e) {
      System.out.println(e.getMessage()); 
      if(debug) {
        e.printStackTrace();
      }
    } finally {
      try{
        // close statement if not null
        if (stmt != null)          
          stmt.close();
        // and set autocommit back on
        conn.setAutoCommit(true);
      } catch (SQLException ei) {
        ei.printStackTrace(); 
      }
    }
  }

  public void deposit(String name, String account, float amount) {    
    boolean isUpdated;
    PreparedStatement stmt = null;
    
	  try {
      // set auto commit false to have more control
      conn.setAutoCommit(false);
      
      // get the account to use
      stmt = conn.prepareStatement("SELECT Balance, C# FROM Account WHERE B#||DLA# = ?",
                                   ResultSet.TYPE_SCROLL_INSENSITIVE,
                                   ResultSet.CONCUR_UPDATABLE);
      stmt.setString(1, account);
      ResultSet rs = stmt.executeQuery();
      if(rs.first()) {
        // check if amount is non negative
        float balance = rs.getFloat("Balance");
        if(amount >= 0) {
          rs.updateFloat("Balance", balance + amount);
          // update the row
          rs.updateRow();
          
          // Set Customers Status
          try {
            set_status(rs.getString("C#"));
          } catch (Exception e) {
            throw e;
          }
          
          // commit it to DB
          conn.commit();
        }
        else
          throw new Exception("Negative amount can not be deposited.");
      }
      else
        throw new Exception("Invalid account number");
      
      // let user know deposit was done
      System.out.println("Deposit complete!");
      
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
      // let user know deposit was not done
     System.out.println("Unable to Deposit \n" + "Error message = " + e.getMessage());
    } catch (Exception e) {
      System.out.println(e.getMessage()); 
      if(debug) {
        e.printStackTrace();
      }
    } finally {
      try{
        // close statement if not null
        if (stmt != null)          
          stmt.close();
        // and set autocommit back on
        conn.setAutoCommit(true);
      } catch (SQLException ei) {
        ei.printStackTrace(); 
      }
    }
  }

  public void transfer(String name, String account1, String account2, float amount) {    
    boolean isUpdated;
    PreparedStatement stmt1 = null;
    PreparedStatement stmt2 = null;
        
	  try {
      // set auto commit false to have more control
      conn.setAutoCommit(false);
      
      // get the account to use for withdraw
      stmt1 = conn.prepareStatement("SELECT Balance, C# FROM Account WHERE B#||DLA# = ?",
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_UPDATABLE);
      stmt1.setString(1, account1);
      ResultSet rs1 = stmt1.executeQuery();
                                    
      // get the account to use for deposit
      stmt2 = conn.prepareStatement("SELECT Balance, C# FROM Account WHERE B#||DLA# = ?",
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_UPDATABLE);
      stmt2.setString(1, account2);
      ResultSet rs2 = stmt2.executeQuery();
      
      if(rs1.first() && rs2.first()) {
        // check if you can withdraw and if amount is non negative
        float balance1 = rs1.getFloat("Balance");
        float balance2 = rs2.getFloat("Balance");
        if(balance1 >= amount) {
          if(amount >= 0) {
            rs1.updateFloat("Balance", balance1 - amount);
            rs2.updateFloat("Balance", balance2 + amount);
            // update the row
            rs1.updateRow();
            rs2.updateRow();
            
            // Set Customers Status
            try {
              set_status(rs1.getString("C#"));
              set_status(rs2.getString("C#"));
            } catch (Exception e) {
              throw e;
            }
            
            // commit it to DB
            conn.commit();
          }
          else
            throw new Exception("Negative amount can not be transfered.");
        }
        else
          throw new Exception("Insufficient funds");
      }
      else
        throw new Exception("Invalid account number");
        
      // let user know transfer was done
      System.out.println("Transfer complete!");
      
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
      // let user know transfer was not done
      System.out.println("Unable to Withdraw \n" + "Error message = " + e.getMessage());
    } catch (Exception e) {
      System.out.println(e.getMessage()); 
      if(debug) {
        e.printStackTrace();
      }
    } finally {
      try{
        // close statement if not null
        if (stmt1 != null)
          stmt1.close();
        if (stmt2 != null)
          stmt2.close();
        // and set autocommit back on
        conn.setAutoCommit(true);
      } catch (SQLException ei) {
        ei.printStackTrace(); 
      }
    }
  }

  public void show_branch(String branch) {    
    float total = 0;
    boolean isAddress = false;
    PreparedStatement stmt = null;
    
    // if it cant be parsed assume its an address
    try {
      Integer.parseInt(branch);
    } catch (NumberFormatException e) {
      isAddress = true;
    }

	  try {
      // get the proper query based on isAddress
      stmt = conn.prepareStatement("SELECT Account.B#||Account.DLA# AS Account#, Account.C#, Account.Balance "
                                   + "FROM Branch, Account WHERE Branch.B# = Account.B# AND Branch."
                                   + (isAddress? "Address":"B#") + " = ? ORDER BY Account.C#",
                                   ResultSet.TYPE_SCROLL_INSENSITIVE,
                                   ResultSet.CONCUR_READ_ONLY);
      stmt.setString(1, branch);
      ResultSet rs = stmt.executeQuery();
      // print them all!
            
      System.out.println("+----------+-----------+---------+");
      System.out.println("| Account# | Customer# | Balance |");
      System.out.println("+==========+===========+=========+");
      
      // print accounts and add to total
      rs.beforeFirst();
      while(rs.next()) {
        System.out.format("|%10s|%11s|%9.2f|\n",
                          rs.getString("Account#"),
                          rs.getString("C#"),
                          rs.getFloat("Balance"));
        total += rs.getFloat("Balance");
        System.out.println("+----------+-----------+---------+");
      }
      System.out.format("|        Total Balance = %8.2f|\n", total);
      System.out.println("+----------+-----------+---------+");
      
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
      // throw a error if unable to show
      System.out.println("Unable to show branch \n" + "Error message = " + e.getMessage());
    } catch (Exception e) {
      System.out.println(e.getMessage()); 
      if(debug) {
        e.printStackTrace();
      }
    } finally {
      try{
        // close statement if not null
        if (stmt != null)
          stmt.close();
      } catch (SQLException ei) {
        ei.printStackTrace(); 
      }
    }
  }
  
  public void show_all_branches() {    
    PreparedStatement stmt = null;
    
	  try {
      // get all banks
      stmt = conn.prepareStatement("SELECT B#, Address FROM Branch ORDER BY B#",
                                   ResultSet.TYPE_SCROLL_INSENSITIVE,
                                   ResultSet.CONCUR_READ_ONLY);
      ResultSet rs = stmt.executeQuery();
      // print them all!
      
      System.out.println("     Branch#    |     Address     ");
      System.out.println("================+=================");

      rs.beforeFirst();
      while(rs.next()) {
        System.out.format("%16s|%17s\n",
                          rs.getString("B#"),
                          rs.getString("Address"));
        // print branch
        show_branch(rs.getString("B#"));
      }   
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
      // throw a error if unable to show
      System.out.println("Unable to show all branch(es) \n" + "Error message = " + e.getMessage());
    } catch (Exception e) {
      System.out.println(e.getMessage()); 
      if(debug) {
        e.printStackTrace();
      }
    } finally {
      try{
        // close statement if not null
        if (stmt != null)
          stmt.close();
      } catch (SQLException ei) {
        ei.printStackTrace(); 
      }
    }
  }

  public void show_customer(String name) {    
    float total = 0;
    boolean isAddress = false;
    PreparedStatement stmt = null;
   
	  try {
      // get the customer
      stmt = conn.prepareStatement("SELECT Customer.C#, Customer.Name, Customer.Status, "
                                   + "Account.B#||Account.DLA# AS Account#, Account.Balance "
                                   + "FROM Customer, Account WHERE Customer.C# = Account.C# AND Customer.Name = ?",
                                   ResultSet.TYPE_SCROLL_INSENSITIVE,
                                   ResultSet.CONCUR_READ_ONLY);  
      stmt.setString(1, name);
      ResultSet rs = stmt.executeQuery();
      // print them all!
            
      System.out.println("+-----------+----------+--------+----------+---------+");
      System.out.println("| Customer# |   Name   | Status | Account# | Balance |");
      System.out.println("+===========+==========+========+==========+=========+");
      
      // print accounts and add to total
      rs.beforeFirst();
      while(rs.next()) {
        System.out.format("|%11s|%10s|%8d|%10s|%9.2f|\n",
                          rs.getString("C#"),
                          rs.getString("Name"),
                          rs.getInt("Status"),
                          rs.getString("Account#"),
                          rs.getFloat("Balance"));
        total += rs.getFloat("Balance");
        System.out.println("+-----------+----------+--------+----------+---------+");
      }
      System.out.format("|                            Total Balance = %8.2f|\n", total);
      System.out.println("+-----------+----------+--------+----------+---------+");
      
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
      // throw a error if unable to show
      System.out.println("Unable to show customer \n" + "Error message = " + e.getMessage());
    } catch (Exception e) {
      System.out.println(e.getMessage()); 
      if(debug) {
        e.printStackTrace();
      }
    } finally {
      try{
        // close statement if not null
        if (stmt != null)
          stmt.close();
      } catch (SQLException ei) {
        ei.printStackTrace(); 
      }
    }
  } 
  
  private void set_status(String cNum) {    
    boolean isUpdated;
    PreparedStatement stmt = null;
    PreparedStatement stmt2 = null;
    
	  try {
      // set auto commit false to have more control
      conn.setAutoCommit(false);
      
      // get the balance
      stmt = conn.prepareStatement("SELECT Account.C#, SUM(Account.balance) AS TotalBalance "
                                   + "FROM Account WHERE Account.C# = ? GROUP BY(Account.C#)",
                                   ResultSet.TYPE_SCROLL_INSENSITIVE,
                                   ResultSet.CONCUR_READ_ONLY);
      stmt.setString(1, cNum);
      ResultSet rs = stmt.executeQuery();
      rs.beforeFirst();
      while(rs.next()) {
        int status = 3;
        if(rs.getFloat("TotalBalance") == 0)
          status = 0;
        else if (rs.getFloat("TotalBalance") < 1000)
          status = 1;
        else if (rs.getFloat("TotalBalance") < 2000)
          status = 2;
        else
          status = 3;
        
        // Update Status
        stmt2 = conn.prepareStatement("UPDATE Customer SET Status = ? WHERE C# = ?",
                                   ResultSet.TYPE_SCROLL_INSENSITIVE,
                                   ResultSet.CONCUR_UPDATABLE);
        stmt2.setInt(1, status);
        stmt2.setString(2, cNum);
        stmt2.executeUpdate();
        
        conn.commit();
          
      // let user know if status changed
        System.out.println("Status changed!");
      }      
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
      // let user know if status not changed
      System.out.println("Unable to change status \n" + "Error message = " + e.getMessage());
    } catch (Exception e) {
      System.out.println(e.getMessage()); 
      if(debug) {
        e.printStackTrace();
      }
    } finally {
      try{
        // close statement if not null
        if (stmt != null)          
          stmt.close();
        if (stmt2 != null)          
          stmt2.close();
        // and set autocommit back on
        conn.setAutoCommit(true);
      } catch (SQLException ei) {
        ei.printStackTrace(); 
      }
    }
  }

  public void clean_data_base() {  
    PreparedStatement stmt1 = null;
    PreparedStatement stmt2 = null;
    PreparedStatement stmt3 = null;
    
	  try {
      // set auto commit false to have more control
      conn.setAutoCommit(false);
  
      // delete the tables
  
      stmt1 = conn.prepareStatement("DELETE FROM ACCOUNT",
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_UPDATABLE);
      stmt1.executeUpdate();
      
      stmt2 = conn.prepareStatement("DELETE FROM Customer",
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_UPDATABLE);
      stmt2.executeUpdate();
      
      
      stmt3 = conn.prepareStatement("DELETE FROM Branch",
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_UPDATABLE);
      stmt3.executeUpdate();
      
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
      System.out.println("Unable to clean data base \n" + "Error message = " + e.getMessage());
    } catch (Exception e) {
      System.out.println(e.getMessage()); 
      if(debug) {
        e.printStackTrace();
      }
    } finally {
      try{
        // close statement if not null
        if (stmt1 != null)            
          stmt1.close();
        if (stmt2 != null)
          stmt2.close();
        if (stmt3 != null)
          stmt3.close();
          
        // and set autocommit back on
        conn.setAutoCommit(true);
      } catch (SQLException ei) {
        ei.printStackTrace(); 
      }
    }
  }
  
  private void prompt() {
    int choice = 0;    
    int prevChoice = 0;
    do {
      prevChoice = choice;
      // clear the screen
      clearScreen();
      String branch = null;
      String name = null;
      Float amount = 0.0f;
      String account = null;
      String account1 = null;
      String account2 = null;
      
      // decide what to show
      try {
        switch(choice) {
          case 1:
            System.out.println("1. open_branch - Bank Application");
            System.out.println("-------------------------------------");
            String address = System.console().readLine("Address: ");
            open_branch(address);
            break;
          case 2:
            System.out.println("2. close_branch - Bank Application");
            System.out.println("--------------------------------------");
            branch = System.console().readLine("Address or Branch Number: ");
            close_branch(branch);
            break;
          case 3:
            System.out.println("3. setup_account - Bank Application");
            System.out.println("--------------------------------------");
            name = System.console().readLine("Name: ");
            branch = System.console().readLine("Address or Branch Number: ");
            amount = Float.parseFloat(System.console().readLine("Amount: "));
            setup_account(name, branch, amount);
            break;
          case 4:
            System.out.println("4. setup_customer - Bank Application");
            System.out.println("--------------------------------------");
            name = System.console().readLine("Name: ");
            branch = System.console().readLine("Address or Branch Number: ");
            setup_customer(name, branch);
            break;
          case 5:
            System.out.println("5. close_account - Bank Application");
            System.out.println("--------------------------------------");
            name = System.console().readLine("Name: ");
            branch = System.console().readLine("Address or Branch Number: ");
            close_account(name, branch);
            break;
          case 6:
            System.out.println("6. withdraw - Bank Application");
            System.out.println("--------------------------------------");
            name = System.console().readLine("Name: ");
            account = System.console().readLine("Account Number: ");
            amount = Float.parseFloat(System.console().readLine("Amount: "));
            withdraw(name, account, amount);
            break;
          case 7:
            System.out.println("7. deposit - Bank Application");
            System.out.println("--------------------------------------");
            name = System.console().readLine("Name: ");
            account = System.console().readLine("Account Number: ");
            amount = Float.parseFloat(System.console().readLine("Amount: "));
            deposit(name, account, amount);
            break;
          case 8:
            System.out.println("8. transfer - Bank Application");
            System.out.println("--------------------------------------");
            name = System.console().readLine("Name: ");
            account1 = System.console().readLine("Account Number from: ");
            account2 = System.console().readLine("Account Number to: ");
            amount = Float.parseFloat(System.console().readLine("Amount: "));
            transfer(name, account1, account2, amount);
            break;
          case 9:
            System.out.println("9. show_branch - Bank Application");
            System.out.println("--------------------------------------");
            branch = System.console().readLine("Address or Branch Number: ");
            show_branch(branch);
            break;
          case 10:
            System.out.println("10. show_all_branches - Bank Application");
            System.out.println("--------------------------------------");
            show_all_branches();
            break;
          case 11:
            System.out.println("11. show_customer - Bank Application");
            System.out.println("--------------------------------------");
            name = System.console().readLine("Name: ");
            show_customer(name);
            break;
          case 0:
            System.out.println("Main Menu - Welcome - Bank Application");
            System.out.println("--------------------------------------");
            System.out.println("1. open_branch");
            System.out.println("2. close_branch");
            System.out.println("3. setup_account");
            System.out.println("4. setup_customer");
            System.out.println("5. close_account");
            System.out.println("6. withdraw");
            System.out.println("7. deposit");
            System.out.println("8. transfer");
            System.out.println("9. show_branch");
            System.out.println("10. show_all_branches");
            System.out.println("11. show_customer");
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
    } while(choice >= 0 && choice <= 11);
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
  
  public static void main(String[] args) {
    Part2 p2 = new Part2();    
    p2.connect();
    p2.prompt();
    p2.disconnect();  
  }
}