package java_sqlite_3005;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class javaWithSQliteMain {

    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Java With SQLite example");
		GUI frame = null;
		
        int DISPLAY_LIMIT = 100;
        int count;		

		//Connect to database
        try {
        	
        	//direct java to the sqlite-jdbc driver jar code
        	// load the sqlite-JDBC driver using the current class loader
			Class.forName("org.sqlite.JDBC");
			
			//create connection to a database in the project home directory.
			//if the database does not exist one will be created in the home directory
		    
			//Connection conn = DriverManager.getConnection("jdbc:sqlite:mytest.db");
			Connection database = DriverManager.getConnection("jdbc:sqlite:..//Question3.db");
		       //create a statement object which will be used to relay a
		       //sql query to the database
		     Statement stat = database.createStatement();

		    /*
		     * SQLite supports in-memory databases, which does not create any database files
		     * To use in memory database in your Java code, get the database connection as follows:
		     *
		     * Connection connection = DriverManager.getConnection("jdbc:sqlite::memory:");
		     * 
		    */
			
		   
                //Query database for initial contents for GUI

	            String sqlQueryString = "SELECT * FROM phone"
	            						+ " ORDER BY imie ASC;";
	            System.out.println("");
	            System.out.println(sqlQueryString);

	            ArrayList<Phone> phones = new ArrayList<Phone>();

		        ResultSet rs = stat.executeQuery(sqlQueryString);
		        count = 0;
		        while (rs.next() && count < DISPLAY_LIMIT){
		            Phone phone = new Phone(
		            		rs.getInt("imie"),
		            		rs.getString("status"),
		            		rs.getString("modelID"));
		            phones.add(phone);
		            count++;
		        }
		        rs.close(); //close the query result table

		        sqlQueryString = "SELECT * FROM model"
							    	+ " NATURAL JOIN software"
							    	+ " NATURAL JOIN SoC"
							    	+ " NATURAL JOIN display"
							    	+ " LEFT JOIN (SELECT"
							    			+ " modelID as pid,"
							    			+ " megapixel AS pc_megapixel,"
							    			+ " resolution AS pc_resolution,"
							    			+ " zoom AS pc_zoom,"
							    			+ " video AS pc_video,"
							    			+ " features AS pc_features"
							    			+ " FROM camera"
							    				+ " WHERE camera_type = 'Primary')"
							    		+ " ON model.modelID = pid"
							    	+ " LEFT JOIN (SELECT"
							    			+ " modelID as sid,"
							    			+ " megapixel AS sc_megapixel,"
							    			+ " resolution AS sc_resolution,"
							    			+ " zoom AS sc_zoom,"
							    			+ " video AS sc_video,"
							    			+ " features AS sc_features"
							    			+ " FROM camera"
							    				+ " WHERE camera_type = 'Secondary')"
							    		+ " ON model.modelID = sid"
		            			+ " ORDER BY model_name ASC;";

	            System.out.println("");
	            System.out.println(sqlQueryString);

	            ArrayList<Model> models = new ArrayList<Model>();

		        rs = stat.executeQuery(sqlQueryString);
		        count = 0;
		        while (rs.next() && count < DISPLAY_LIMIT){
		        	 Model model =  new Model(
		            		rs.getString("modelID"),
		            		rs.getString("model_name"),
		            		rs.getString("dimention"),
		            		rs.getString("weight"),
		            		rs.getString("connectivity"),
		            		rs.getString("ports"),
		            		rs.getString("sensor"),
		            		rs.getString("battery"),
		            		rs.getFloat("storage"),
		            		rs.getFloat("ram"),
		            		rs.getString("manufacture"),
		            		new SoC(
		            				rs.getString("chipset"),
				            		rs.getString("gpu"),
				            		rs.getInt("cpu_core"),
				            		rs.getInt("cpu_speed")
		            				),
		            		new Software(
		            				rs.getString("os"),
				            		rs.getString("version")
		            				),
		            		new Camera(
		            				rs.getInt("pc_megapixel"),
		            				rs.getString("pc_resolution"),
		            				rs.getString("pc_zoom"),
		            				rs.getString("pc_video"),
		            				rs.getString("pc_features"),
		            				"Primary"
		            				),
				            new Camera(
				            		rs.getInt("sc_megapixel"),
				            		rs.getString("sc_resolution"),
				            		rs.getString("sc_zoom"),
				            		rs.getString("sc_video"),
				            		rs.getString("sc_features"),
				            		"Secondary"
				            		),
		            		new Display(
		            				rs.getFloat("screensize"),
		            				rs.getString("resolution"),
		            				rs.getString("touchtype"),
		            				rs.getString("pixel_density"),
		            				rs.getString("material_used")
		            				)
		        		   );
		           models.add(model);
		           count++;
		        }
		        rs.close(); //close the query result table
		        
		        Model[] modelArray = new Model[1]; //just to establish array type
		        modelArray =  models.toArray(modelArray);
				
		        //Create GUI with knowledge of database and initial query content
		        frame =  new GUI("Question3", database, stat, phones, models); //create GUI frame with knowledge of the database
		        
		        //Leave it to GUI to close database
		        //conn.close(); //close connection to database			
												
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

        //make GUI visible
		frame.setVisible(true);




	}

}
