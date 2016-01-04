package java_sqlite_3005;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import java.util.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class GUI extends JFrame implements DialogClient{
		
	public int GUI_DISPLAY_LIMIT = 100;

	// Store the model as a vector of email buddies

	Connection databaseConnection;
	Statement stat;

	ArrayList<Phone> phoneList = new ArrayList<Phone>();
	ArrayList<Model> modelList = new ArrayList<Model>();


	private Model	selectedModel; //Model currently selected in the GUI list
	private Phone	selectedPhone; //Phone currently selected in the GUI list
	
	private Phone phoneBeingEdited; //phone being edited in a dialog

	// Store the view that contains the components
	ListPanel	view; //panel of GUI components for the main window
	GUI			thisFrame;

	// Here are the component listeners
	ActionListener			theModelSearchButtonListener;
	ActionListener			thePhoneSearchButtonListener;
	ActionListener			thePhoneAddButtonListener;
	ListSelectionListener	modelListSelectionListener;
	ListSelectionListener	phoneListSelectionListener;
	MouseListener			doubleClickPhoneListListener;
	KeyListener             keyListener;

	// Here is the default constructor
	public GUI(String title, Connection aDB, Statement aStatement, ArrayList<Phone> initialPhones, ArrayList<Model> initialModels) {
		super(title);
		databaseConnection = aDB;
		stat = aStatement;

        phoneList = initialPhones;
        modelList = initialModels;
		selectedPhone = null;
		selectedModel = null;
		thisFrame = this;
		
		addWindowListener(
				new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
	 					try {
	 						System.out.println("Closing Database Connection");
							databaseConnection.close();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						System.exit(0);
					}
				}
			);

		// Make the main window view panel
		add(view = new ListPanel());

		// Add a listener for the model search button
		theModelSearchButtonListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				modelSearch();
			}};

		// Add a listener for the phone search button
		thePhoneSearchButtonListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				phoneSearch(false);
			}};

		thePhoneAddButtonListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// make a new phone with a auto generated imie
				phoneBeingEdited = new Phone(generateNewImie(), "", "");
					System.out.println("Added a phone: " + phoneBeingEdited);
										
					PhoneDetailsDialog dialog = new PhoneDetailsDialog(thisFrame, thisFrame, "Phone Details Dialog", true, phoneBeingEdited, true);         
					dialog.setVisible(true);
				} 
			};
				
		// Add a listener to allow selection of buddies from the list
		modelListSelectionListener = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				selectModel();
			}};

		// Add a listener to allow selection of buddies from the list
		phoneListSelectionListener = new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent event) {
					selectPhone();
				}};

		// Add a listener to allow double click selections from the list for editing
		doubleClickPhoneListListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
					JList theList = (JList) event.getSource();
					int index = theList.locationToIndex(event.getPoint());
					phoneBeingEdited = (Phone) theList.getModel().getElementAt(index);
					System.out.println("Double Click on: " + phoneBeingEdited);
										
					PhoneDetailsDialog dialog = new PhoneDetailsDialog(thisFrame, thisFrame, "Phone Details Dialog", true, phoneBeingEdited, false);         
					dialog.setVisible(true);
				} 
			}};
			
		keyListener = new KeyListener() {

				@Override
				public void keyPressed(KeyEvent arg0) {
						
				}

				@Override
				public void keyReleased(KeyEvent arg0) {
					
				}

				@Override
				public void keyTyped(KeyEvent arg0) {

					int keyChar = arg0.getKeyChar();

			        if (keyChar == KeyEvent.VK_ENTER)  modelSearch();
				
				}};


        setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600,300);

		// Start off with everything updated properly to reflect the model state
		update();
	}

	// Enable all listeners
	private void enableListeners() {
		view.getModelSearchButton().addActionListener(theModelSearchButtonListener);
		view.getPhoneSearchButton().addActionListener(thePhoneSearchButtonListener);
		view.getPhoneAddButton().addActionListener(thePhoneAddButtonListener);
		view.getPhoneList().addListSelectionListener(phoneListSelectionListener);
		view.getModelList().addListSelectionListener(modelListSelectionListener);
		view.getPhoneList().addMouseListener(doubleClickPhoneListListener);
		view.getModelSearchText().addKeyListener(keyListener);
	}

	// Disable all listeners
	private void disableListeners() {
		view.getModelSearchButton().removeActionListener(theModelSearchButtonListener);
		view.getPhoneSearchButton().removeActionListener(thePhoneSearchButtonListener);
		view.getPhoneAddButton().removeActionListener(thePhoneAddButtonListener);
		view.getPhoneList().removeListSelectionListener(phoneListSelectionListener);
		view.getModelList().removeListSelectionListener(modelListSelectionListener);
		view.getPhoneList().removeMouseListener(doubleClickPhoneListListener);
		view.getModelSearchText().removeKeyListener(keyListener);
	}

	public ArrayList<Model> getModelByTerm (String searchPrototype, String searchColumn, Boolean like){

		//start of query
		String sqlQueryString = "SELECT * FROM model"
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
		    		+ " ON model.modelID = sid";
		
		//check some special cases then search
        if(!(searchPrototype.equals("*") || searchPrototype.equals("%") || searchPrototype.equals(""))) {
        	sqlQueryString += " WHERE " + searchColumn;
        	if(like) {
        		sqlQueryString += " like '%" + searchPrototype + "%'";
        	} else {
        		sqlQueryString += " = '" + searchPrototype + "'";
        	}
        }

        //ad order by clause to the query
        sqlQueryString += " ORDER BY model_name ASC";
        // end query
        sqlQueryString += ";";

        ArrayList<Model> models = null;

	    try {
			ResultSet rs = stat.executeQuery(sqlQueryString);
	    	System.out.println("Executed Query: " + sqlQueryString );
	    	
	    	models = new ArrayList<Model>();
	    	int count = 0;
	        while (rs.next() && count < GUI_DISPLAY_LIMIT){
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
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
	    	System.out.println("Error Executing Query: " + sqlQueryString );
			e.printStackTrace();
		}
	    
	    return models;
	}

	// This is called when the user clicks the model search button
	private void modelSearch() {

		ArrayList<Model> models = getModelByTerm(view.getModelSearchText().getText().trim(), "model_name", true);
		if(models!=null){
			Model[] modelArray = new Model[1]; //just to establish array type
			modelArray =  models.toArray(modelArray);
			
			modelList = models;
			
			//remove while unselecting
			view.getPhoneList().removeListSelectionListener(phoneListSelectionListener);
			
			//deselect selected phone as new search happened 
			view.getPhoneList().clearSelection();
			selectedPhone = null;
			//re-add after unselecting
			view.getPhoneList().addListSelectionListener(phoneListSelectionListener);
		}
		
		System.out.println("Model Search clicked");
		update();
	}

	//generates new imie... ie max(imie) + 1
	private int generateNewImie() {
		
		//start of query
		String sqlQueryString = "SELECT MAX(imie) + 1 as max FROM phone;";
		int maxImie;

	    try {
			ResultSet rs = stat.executeQuery(sqlQueryString);
	    	System.out.println("Executed Query: " + sqlQueryString );
	    	
	    	if (rs.next()){
	        	maxImie = rs.getInt("max");
	        } else {
	        	maxImie = -1; // indicates an error
	        }
	        rs.close(); //close the query result table
	        		        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
	    	System.out.println("Error Executing Query: " + sqlQueryString );
			e.printStackTrace();
			maxImie = -1; // indicates an error
		}

	    return maxImie;
	}

	// This is called when the user clicks the model search button
	private void phoneSearch(boolean refresh) {

			String searchPrototype = view.getPhoneSearchText().getText().trim();

			// if you want to refresh then just search with blank string
			if(refresh)
				searchPrototype= "";
			//start of query
			String sqlQueryString = "SELECT * FROM phone";
										
			
			//check some special cases then search
	        if(!(searchPrototype.equals("*") || searchPrototype.equals("%") || searchPrototype.equals(""))) {
	        	sqlQueryString += " WHERE imie ='" + searchPrototype + "'";
	        }

	        //ad order by clause to the query
	        sqlQueryString += " ORDER BY imie ASC";
	        // end query
	        sqlQueryString += ";";
	        		
		    try {
				ResultSet rs = stat.executeQuery(sqlQueryString);
		    	System.out.println("Executed Query: " + sqlQueryString );
		    	
		        ArrayList<Phone> phones = new ArrayList<Phone>();

		        int count = 0;
		        while (rs.next() && count < GUI_DISPLAY_LIMIT){
		            Phone phone = new Phone(
		            		rs.getInt("imie"),
		            		rs.getString("status"),
		            		rs.getString("modelID"));
		            phones.add(phone);
		            count++;
		        }
		        rs.close(); //close the query result table

		        phoneList = phones;		        
			} catch (SQLException e) {
				// TODO Auto-generated catch block
		    	System.out.println("Error Executing Query: " + sqlQueryString );
				e.printStackTrace();
			}
			
			System.out.println("Phone Search clicked");
			update();
		}

	// This is called when the user selects a phone from the list
	// selected phone show details of it self in the model list
	private void selectPhone() {
		selectedPhone = (Phone)(view.getPhoneList().getSelectedValue());
		System.out.println("Phone Selected: " + selectedPhone);
		
		// if null don't bother
		if (selectedPhone == null)
				return;
		
		ArrayList<Model> models = getModelByTerm(selectedPhone.getModelID(), "model.modelID", false);
		if(models!=null){
			Model[] modelArray = new Model[1]; //just to establish array type
			modelArray =  models.toArray(modelArray);
			
			modelList = models;
			
			 //clear searched text
	        view.getModelSearchText().setText("");
		}
    	
	    System.out.println("Phone details shown");
		update();
	}

	// This is called when the user selects a model from the list
	private void selectModel() {
		selectedModel = (Model)(view.getModelList().getSelectedValue());
		System.out.println("Model Selected: " + selectedModel);
	
		update();
	}

	// Update the search button
	private void updateSearchButton() {
		view.getModelSearchButton().setEnabled(true);
	}

	// Update the list
	private void updateList() {
		boolean		foundSelected = false;

        Phone phoneArray[] = new Phone[1]; //just to establish array type
	    view.getPhoneList().setListData(((Phone []) phoneList.toArray(phoneArray)));

	    Model modelArray[] = new Model[1]; //just to establish array type
	    view.getModelList().setListData(((Model []) modelList.toArray(modelArray)));

		if (selectedPhone != null)
			view.getPhoneList().setSelectedValue(selectedPhone, true);
		if (selectedModel != null)
			view.getModelList().setSelectedValue(selectedModel, true);
	}

	// Update the components
	private void update() {
		disableListeners();
		updateList();
		updateSearchButton();
		enableListeners();
	}

	@Override
	public void dialogFinished(DialogClient.operation requestedOperation) {
		if(requestedOperation == DialogClient.operation.UPDATE){
			//update song data in database

	        String sqlUpdateQueryString = "UPDATE phone SET "
	        		+ "status = '" + phoneBeingEdited.getStatus() + "', "
	        		+ "modelID = '" + phoneBeingEdited.getModelID() + "' "
	        		+ "where imie = '" + phoneBeingEdited.getImie() + "';";
		    try {
				stat.executeUpdate(sqlUpdateQueryString);
				System.out.println("Executed Query: " + sqlUpdateQueryString );
				
		    } catch (SQLException e) {
				// TODO Auto-generated catch block
		    	System.out.println("Error Executing Query: " + sqlUpdateQueryString );
				e.printStackTrace();
			}
		
			System.out.println("UPDATE: " + phoneBeingEdited );
		}
		else if(requestedOperation == DialogClient.operation.ADD){
			//add song from database
			String sqlAddQueryString = "INSERT INTO phone VALUES ("
	        								+ phoneBeingEdited.getImie() + ", '"
	        								+ phoneBeingEdited.getStatus() + "', '"
	        								+ phoneBeingEdited.getModelID()	+ "' );";
		    try {
				stat.executeUpdate(sqlAddQueryString);
				System.out.println("Executed Query: " + sqlAddQueryString );
				
		    } catch (SQLException e) {
				// TODO Auto-generated catch block
		    	System.out.println("Error Executing Query: " + sqlAddQueryString );
				e.printStackTrace();
			}
		
			System.out.println("ADDED: " + phoneBeingEdited );
			
		}
		else if(requestedOperation == DialogClient.operation.DELETE){
			//delete song from database

	        String sqlDeleteQueryString = "DELETE FROM phone "
	        		+ "where imie = '" + phoneBeingEdited.getImie() + "';";
		    try {
				stat.executeUpdate(sqlDeleteQueryString);
				System.out.println("Executed Query: " + sqlDeleteQueryString );
				
		    } catch (SQLException e) {
				// TODO Auto-generated catch block
		    	System.out.println("Error Executing Query: " + sqlDeleteQueryString );
				e.printStackTrace();
			}
		
			System.out.println("DELETE: " + phoneBeingEdited );
			//view.getPhoneSearchButton()
			
		}
		// refresh phone list
		phoneSearch(true);
		phoneBeingEdited = null;
		update();
	}

	@Override
	public void dialogCancelled() {

		phoneBeingEdited = null;
		update();
		
	}

}