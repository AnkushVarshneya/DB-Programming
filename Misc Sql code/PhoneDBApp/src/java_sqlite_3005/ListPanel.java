package java_sqlite_3005;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// This is the Panel that contains represents the view of the
// Music Store

public class ListPanel extends JPanel {

	// These are the components
	private JButton		modelSearchButton;
	private JButton		phoneSearchButton;
	private JButton		phoneAddButton;
	private JTextField  modelSearchText;
	private JTextField  phoneSearchText;
	private JList		phoneList;
	private JList		modelList;
	
	private Font UIFont = new Font("Courier New", Font.BOLD, 16);


	// These are the get methods that are used to access the components
	public JButton getModelSearchButton() { return modelSearchButton; }
	public JButton getPhoneSearchButton() { return phoneSearchButton; }
	public JButton getPhoneAddButton() { return phoneAddButton; }
	public JList getPhoneList() { return phoneList; }
	public JList getModelList() { return modelList; }
	public JTextField getModelSearchText() { return modelSearchText; }
	public JTextField getPhoneSearchText() { return phoneSearchText; }
	
	
	// This is the default constructor
	public ListPanel(){
		super();

		// Use a GridBagLayout (lotsa fun)
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints layoutConstraints = new GridBagConstraints();
		setLayout(layout);

		// Add the phone Search button
		phoneSearchText = new JTextField("");
		phoneSearchText.setFont(UIFont);

		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 0;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 1.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(phoneSearchText, layoutConstraints);
		add(phoneSearchText);

		// Add the phone Search button
		phoneSearchButton = new JButton("Search");
		layoutConstraints.gridx = 1;
		layoutConstraints.gridy = 0;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 0.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(phoneSearchButton, layoutConstraints);
		add(phoneSearchButton);
		
		// Add the phone add button
		phoneAddButton = new JButton("Add Phone");
		layoutConstraints.gridx = 2;
		layoutConstraints.gridy = 0;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 0.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(phoneAddButton, layoutConstraints);
		add(phoneAddButton);

		// Add the bookList list
		phoneList = new JList();
		phoneList.setFont(UIFont);
		//bookList.setPrototypeCellValue("xxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		JScrollPane scrollPane = new JScrollPane( phoneList,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 1;
		layoutConstraints.gridwidth = 3;
		layoutConstraints.gridheight = 4;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.NORTHWEST;
		layoutConstraints.weightx = 1.0;
		layoutConstraints.weighty = 1.0;
		layout.setConstraints(scrollPane, layoutConstraints);
		add(scrollPane);

		// Add the model Search button
		modelSearchText = new JTextField("");
		modelSearchText.setFont(UIFont);

		layoutConstraints.gridx = 3;
		layoutConstraints.gridy = 0;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 1.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(modelSearchText, layoutConstraints);
		add(modelSearchText);

		// Add the model Search button
		modelSearchButton = new JButton("Search");
		layoutConstraints.gridx = 4;
		layoutConstraints.gridy = 0;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 0.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(modelSearchButton, layoutConstraints);
		add(modelSearchButton);

		// Add the songList list
		modelList = new JList();
		modelList.setFont(UIFont);
		//songList.setPrototypeCellValue("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		scrollPane = new JScrollPane( modelList,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		layoutConstraints.gridx = 3;
		layoutConstraints.gridy = 1;
		layoutConstraints.gridwidth = 2;
		layoutConstraints.gridheight = 4;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.NORTHWEST;
		layoutConstraints.weightx = 2.0;
		layoutConstraints.weighty = 1.0;
		layout.setConstraints(scrollPane, layoutConstraints);
		add(scrollPane);


	}
}