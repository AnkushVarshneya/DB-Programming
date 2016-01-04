package java_sqlite_3005;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;

public class PhoneDetailsDialog extends JDialog {

	// adding a phone?	
	boolean adding;
	
	// This is a pointer to the email buddy that is being edited
	private Phone thePhone;
	
	DialogClient theDialogClient;

	// These are the components of the dialog box
	private JLabel					aLabel; //reuseable label variable
	
	private JTextField				imieField; //imie of the phone
	private JComboBox 				statusFiled; //artist of the phone
	private JComboBox 				modelIDField; //modelID of the phone
	
	private JButton					updateButton;
	private JButton					deleteButton;
	private JButton					cancelButton;
	
	Font UIFont = new Font("Courier New", Font.BOLD, 16);

	public PhoneDetailsDialog(Frame owner, DialogClient aClient, String title, boolean modal, Phone aPhone, boolean isAdding){
		super(owner,title,modal);

		//Store the client and model variables
		theDialogClient = aClient;
		thePhone =aPhone;
		adding = isAdding;

		ArrayList<String> statusType =  new ArrayList<String>();
		// load all status's
		statusType.add("lost");
		statusType.add("stolen");
		statusType.add("active");
		
		statusFiled = new JComboBox(statusType.toArray());
	
		ArrayList<String> modelType = new ArrayList<String>();
		// load all models
		for (Model m : ((GUI)owner).getModelByTerm("", "model_name", true)) {
			modelType.add(m.getModelID());
		}

		modelIDField = new JComboBox(modelType.toArray());

		// Put all the components onto the window and given them initial values
		buildDialogWindow(thePhone);

		// Add listeners for the Ok and Cancel buttons as well as window closing
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				updateButtonClicked();
			}});
   		if(!adding) {
			deleteButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event){
					deleteButtonClicked();
				}});
   		}

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				cancelButtonClicked();
			}});

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				cancelButtonClicked();
			}});

		setSize(400, 250);
		
	}

	// This code adds the necessary components to the interface
	private void buildDialogWindow(Phone aPhone) {
		
   		GridBagLayout layout = new GridBagLayout();
        GridBagConstraints lc = new GridBagConstraints();
        getContentPane().setLayout(layout);

 
        lc.anchor = GridBagConstraints.EAST;
        lc.insets = new Insets(5, 5, 5, 5);

        aLabel = new JLabel("IMIE");
        lc.gridx = 0; lc.gridy = 0;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
        layout.setConstraints(aLabel, lc);
        getContentPane().add(aLabel);

        aLabel = new JLabel("Status");
        lc.gridx = 0; lc.gridy = 1;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
        layout.setConstraints(aLabel, lc);
        getContentPane().add(aLabel);

        aLabel = new JLabel("ModelID");
        lc.gridx = 0; lc.gridy = 2;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
        layout.setConstraints(aLabel, lc);
        getContentPane().add(aLabel);

        aLabel = new JLabel("  "); //blank label
        lc.gridx = 0; lc.gridy = 5;
        lc.gridwidth = 3; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
        layout.setConstraints(aLabel, lc);
        getContentPane().add(aLabel);

        aLabel = new JLabel("  "); //blank label
        lc.gridx = 1; lc.gridy = 6;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 1.0; lc.weighty = 0.0;
        layout.setConstraints(aLabel, lc);
        getContentPane().add(aLabel);
   		
   		// Add the IMIE field make it read only
		imieField = new JTextField(aPhone.getImie()+"");
		imieField.setFont(UIFont);
        lc.gridx = 1; lc.gridy = 0;
        lc.gridwidth = 3; lc.gridheight = 1;
        lc.fill = GridBagConstraints.BOTH;
        lc.weightx = 1.0; lc.weighty = 0.0;
        layout.setConstraints(imieField, lc);
   		getContentPane().add(imieField);
		imieField.setEditable(false);

		// Add status field
		statusFiled.setSelectedItem(aPhone.getStatus());
		statusFiled.setFont(UIFont);
        lc.gridx = 1; lc.gridy = 1;
        lc.gridwidth = 3; lc.gridheight = 1;
        lc.fill = GridBagConstraints.BOTH;
        lc.weightx = 1.0; lc.weighty = 0.0;
        layout.setConstraints(statusFiled, lc);
   		getContentPane().add(statusFiled);
         
 		// Add the modelID field
		modelIDField.setSelectedItem(aPhone.getModelID());
		modelIDField.setFont(UIFont);
        lc.gridx = 1; lc.gridy = 2;
        lc.gridwidth = 3; lc.gridheight = 1;
        lc.fill = GridBagConstraints.BOTH;
        lc.weightx = 1.0; lc.weighty = 0.0;
        layout.setConstraints(modelIDField, lc);
   		getContentPane().add(modelIDField);
        
		// Add the Update button
		updateButton = new JButton((adding)? "ADD":"UPDATE");

        lc.gridx = 1; lc.gridy = 6;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
        layout.setConstraints(updateButton, lc);
   		getContentPane().add(updateButton);
        
   		if(!adding) {
			// Add the Delete button
			deleteButton = new JButton("DELETE");
	
	        lc.gridx = 2; lc.gridy = 6;
	        lc.gridwidth = 1; lc.gridheight = 1;
	        lc.weightx = 0.0; lc.weighty = 0.0;
	        layout.setConstraints(deleteButton, lc);
	   		getContentPane().add(deleteButton);
   		}

   		// Add the Cancel button
		cancelButton = new JButton("CANCEL");
        
        lc.gridx = 3; lc.gridy = 6;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
        layout.setConstraints(cancelButton, lc);
   		getContentPane().add(cancelButton);
		
		
	}


	private void updateButtonClicked(){
		
		thePhone.setImie(Integer.parseInt(imieField.getText()));
		thePhone.setStatus((String)statusFiled.getSelectedItem());
		thePhone.setModelID((String)modelIDField.getSelectedItem());
		
		//Inform the dialog client that the dialog finished
		if (theDialogClient != null) {
			if(adding) {
				theDialogClient.dialogFinished(DialogClient.operation.ADD);
			}
			else {
				theDialogClient.dialogFinished(DialogClient.operation.UPDATE);
			}
		}
		
		//Make the dialog go away
		dispose();
	}
	
	private void deleteButtonClicked(){
		
		thePhone.setImie(Integer.parseInt(imieField.getText()));
		thePhone.setStatus((String)statusFiled.getSelectedItem());
		thePhone.setModelID((String)modelIDField.getSelectedItem());
		
		//Inform the dialog client that the dialog finished		
		if (theDialogClient != null) theDialogClient.dialogFinished(DialogClient.operation.DELETE);
		
		//Make the dialog go away		
		dispose();
	}

	private void cancelButtonClicked(){
		
		//Inform the dialog client that the dialog finished
		if (theDialogClient != null) theDialogClient.dialogCancelled();

		//Make the dialog go away
		dispose();
	}
}