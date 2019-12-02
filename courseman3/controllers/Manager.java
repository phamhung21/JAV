package courseman3.controllers;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
//import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

//import courseman3.models.Student;
//import courseman2.models.Student;
//import courseman3.models.Module;
import courseman3.DomainConstraint;
import courseman3.NotPossibleException;
import courseman3.models.Student;

/**
 * @overview represents the data manager class responsible for managing the data
 *           objects.
 * 
 * @attributes objects ArrayList <br>
 *             gui JFrame
 * 
 * @abstract_properties optional(objects) = false /\ <br>
 *                      optional(gui) = false
 * 
 * @author congnv
 */
public abstract class Manager extends WindowAdapter implements ActionListener, Serializable {

	@DomainConstraint(type = DomainConstraint.Type.String, mutable = false, optional = false)
	protected String title;

	@DomainConstraint(type = DomainConstraint.Type.String, mutable = false, optional = false, length = 255)
	protected String storageFile;

	@DomainConstraint(type = DomainConstraint.Type.Collection, mutable = true, optional = false)
	protected ArrayList objects;

	// view elements
	@DomainConstraint(type = DomainConstraint.Type.Object, optional = false)
	protected JFrame gui;

	@DomainConstraint(type = DomainConstraint.Type.Object, optional = false)
	protected JPanel middlePanel;

	@DomainConstraint(type = DomainConstraint.Type.Object, optional = false)
	protected JPanel bottomPanel;

	@DomainConstraint(type = DomainConstraint.Type.Object, optional = false)
	protected JButton ok;

	@DomainConstraint(type = DomainConstraint.Type.Object, optional = false)
	protected JButton cancel;
	// you may consider to add other attributes for view elements here

	/**
	 * @effects initialise <tt>this</tt> with <tt>title</tt> and
	 *          <tt>storageFile</tt> and an empty set of objects <br>
	 *          {@link #createGUI()}: create <tt>gui</tt>
	 */
	public Manager(String title, String storageFile) {
		// TODO: complete this code
		this.title = title;
		this.storageFile = storageFile;
		objects = new ArrayList<>();
		createGUI();
	}

	/**
	 * @modifies this.gui
	 * @effects create <tt>gui</tt> as required (mockup image) <br>
	 *          {@link #createMiddlePanel()}: create <tt>this.middlePanel</tt> with
	 *          components The action listener of the buttons is <tt>this</tt>.
	 */
	protected void createGUI() {
		gui = new JFrame(title);
		gui.setSize(300, 300);
		gui.addWindowFocusListener(this);

		createMiddlePanel();

		bottomPanel = new JPanel();
		gui.add(bottomPanel, BorderLayout.SOUTH);
		ok = new JButton("OK");
		ok.addActionListener(this);
		cancel = new JButton("Cancel");
		cancel.addActionListener(this);

		bottomPanel.add(ok);
		bottomPanel.add(cancel);

	}

	/**
	 * @modifies this.middlePanel
	 * @effects create this.middlePanel with suitable components for specific task
	 */
	protected abstract void createMiddlePanel();

	/**
	 * @requires <tt>gui != null</tt>
	 * @effects show <tt>gui</tt>
	 */
	public void display() {
		gui.setVisible(true);
	}

	/**
	 * @effects handles user actions on the buttons
	 * 
	 *          <pre>
	 *          if button is OK
	 *            {@link #doTask()}: do specific task as require
	 *          else if button is Cancel
	 *            {@link #clearInput(JPanel)}: reset form fields in <tt>this.middlePanel</tt>
	 *          </pre>
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO: complete this code
		String cmd = e.getActionCommand();
		if (cmd.equals("OK")) {
			doTask();
		} else if (cmd.equals("Cancel")) {
			clearInput(middlePanel);
		}
	}

	/**
	 * @effects
	 * 
	 *          <pre>
	 *  create a new object from the data in the data panel of gui and add it to <tt>this.objects</tt>
	 *  if success
	 *      {@link #showMessage(String)}: display the success message on the GUI dialog
	 *  else if not success (exception is thrown)
	 *      {@link #showErrorMessage(String)}: display the exception message on the GUI dialog
	 *          </pre>
	 */
	protected void doTask() {
		// TODO: complete this code
		Object object = createObject();
		try {
			if (object != null) {
				objects.add(object);
				showMessage("Add object " + object.toString() + " successfully");
			}
		} catch (Exception e) {
			showErrorMessage("Add object fail");
		}
	}

	/**
	 * @effects create a new object from the data in the data panel of gui and
	 *          return <br>
	 *          throw NotPossibleException if not success
	 */
	protected abstract Object createObject() throws NotPossibleException;

	/**
	 * @effects display the info <tt>message</tt> on the GUI dialog
	 */
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(gui, message, title, JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * @effects display the error <tt>message</tt> on the GUI dialog
	 */
	public void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(gui, message, title, JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * @requires panel neq null
	 * @effects loop through all components in specified <tt>panel</tt> and <br>
	 *          reset input components' text to be empty
	 */
	private void clearInput(JPanel panel) {
		for (Component component : panel.getComponents()) {
			if (component instanceof JTextField) {
				((JTextField) component).setText(null);
			} else if (component instanceof JPanel) { // clear input in the inner panels (if any)
				clearInput((JPanel) component);
			}
		}
	}

	/**
	 * @requires <tt>gui != null</tt>
	 * @effects hide <tt>gui</tt>
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		gui.setVisible(false);
	}

	/**
	 * @requires <tt>this.objects != null</tt>
	 * @modifies this
	 * @effects load into <tt>this.objects</tt> the data objects in the storage file
	 *          <tt>this.storageFile</tt> that was saved before.
	 * 
	 *          <pre>
	 *          if succeeds 
	 *            display a console message 
	 *          else 
	 *            display a console error message
	 *          </pre>
	 */
	public void startUp() {
		// TODO: complete this code

		File f = new File(storageFile);

		System.out.println("Starting up...");

		if (f.length() == 0) {
			System.out.println(title + ".loaded ... 0 objects");
		}
		if (f.exists()) {
			ObjectInputStream input = null;
			try {
				input = new ObjectInputStream(new FileInputStream(f));
				Object newObjects;
				while (true) {
					newObjects = (Object) input.readObject();
					if (newObjects != null) {
						objects.add(newObjects);
//						System.out.println(objects.size());
					}
				}
			} catch (EOFException e) {
				// ignore
			} catch (Exception e) {
				System.err.println(title + ".load: error occured while loading from file " + e.getClass().getName()
						+ ": " + e.getMessage());
			} finally {
				System.out.println(title + ".loaded ..." + objects.size() + " objects");
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {

					}
				}
			}
		}

	}

	/**
	 * @requires <tt>gui != null</tt>
	 * @modifies this
	 * @effects store <code>this.objects</code> to file <br>
	 *          dispose <tt>gui</tt> and clear <tt>objects</tt> and <br>
	 *          shutdown the application
	 * 
	 *          <pre>
	 *          if succeeds 
	 *            display a console message 
	 *          else 
	 *            display a console error message
	 *          </pre>
	 */
	public void shutDown() {
		// TODO: complete this code

		System.out.println("Shutting down...");
		gui.dispose();
		clearInput(middlePanel);

		try {
//			objects = new ArrayList();

			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(this.storageFile));
			for (int i = 0; i < objects.size(); i++) {
				Object os = objects.get(i);
				out.writeObject(os);
			}
			System.out.println(title + ".shutDown ...stored " + objects.size() + " objects");

			out.close();
			objects.clear();
		} catch (IOException e) {
			System.err.println(title + ".shutDown: error occured while storing to file " + e.getMessage());
		}

	}
}