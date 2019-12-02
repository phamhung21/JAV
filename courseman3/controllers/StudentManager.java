package courseman3.controllers;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import courseman3.DomainConstraint;
import courseman3.NotPossibleException;
import courseman3.models.Module;
import courseman3.models.Student;

/**
 * @overview represents the data manager class responsible for managing the
 *           student objects.
 * 
 * @author congnv
 */

public class StudentManager extends Manager implements Serializable {
	// view elements
	// you may add more view elements here

//	@DomainConstraint(type = DomainConstraint.Type.Object, optional = false)
//	private ArrayList students;

	@DomainConstraint(type = DomainConstraint.Type.Object, optional = false)
	private JTextField tfName;

	@DomainConstraint(type = DomainConstraint.Type.Object, optional = false)
	private JTextField tfDob;

	@DomainConstraint(type = DomainConstraint.Type.Object, optional = false)
	private JTextField tfAddress;

	@DomainConstraint(type = DomainConstraint.Type.Object, optional = false)
	private JTextField tfEmail;

	@DomainConstraint(type = DomainConstraint.Type.Object, optional = false)
	private Student newStudent;

//	private ArrayList<Student> st = new ArrayList<>();

	/**
	 * {@link super()}: initialize this with suitable title & storageFile
	 */
	public StudentManager() {
		super("StudentManger", "students.dat");
//		createGUI();
	}

	@Override
	public void createMiddlePanel() {
		gui.setTitle("Create new student");
		gui.setSize(300, 300);
		gui.setLocation(200, 200);
//		gui.setVisible(true);

		JPanel topPan = new JPanel();
		gui.add(topPan, BorderLayout.NORTH);
		gui.addWindowListener(this);

		JLabel lb1 = new JLabel();
		lb1.setText("Enter new student details");
		topPan.add(lb1);

		middlePanel = new JPanel();
		middlePanel.setBorder(BorderFactory.createEtchedBorder());
		middlePanel.setLayout(new GridLayout(4, 4));
		gui.add(middlePanel, BorderLayout.CENTER);

		JLabel name = new JLabel("Name(*): ");
		tfName = new JTextField();

		JLabel dob = new JLabel("Date of birth(*): ");
		tfDob = new JTextField();

		JLabel address = new JLabel("Address: ");
		tfAddress = new JTextField();

		JLabel email = new JLabel("Email: ");
		tfEmail = new JTextField();

		middlePanel.add(name);
		middlePanel.add(tfName);
		middlePanel.add(dob);
		middlePanel.add(tfDob);
		middlePanel.add(email);
		middlePanel.add(tfEmail);
		middlePanel.add(address);
		middlePanel.add(tfAddress);

	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		gui.setVisible(true);
		;
	}

	/**
	 * @effects create a new student from the data in the data panel of gui and
	 *          return <br>
	 *          throws NotPossibleException if not success
	 */
	@Override
	public Object createObject() throws NotPossibleException {
		newStudent = null;
//    	objects = new ArrayList<Manager>();

		if (tfName.getText().isEmpty() || tfDob.getText().isEmpty()) {
			showErrorMessage("Please fill all field (*)");
		} else {
			newStudent = new Student(tfName.getText(), tfDob.getText(), tfAddress.getText(), tfEmail.getText());
			return newStudent;
		}
//		objects.add(newStudent);

		return null;

	}

	/**
	 * @effects return student object with specified <tt>id</tt> in
	 *          <tt>this.objects</tt> OR null if not found
	 */
	public Student getStudentByID(String id) {

		for (Object s: objects) {
			Student st = (Student) s;
			if(st.getId().equals(id)){
				return st;
			}
		}
		showErrorMessage("Not Found Student ID = " + id);
		return null;
	}
}
