package courseman3.controllers;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import courseman3.DomainConstraint;
import courseman3.NotPossibleException;
import courseman3.models.CompulsoryModule;
import courseman3.models.ElectiveModule;
import courseman3.models.Module;
import courseman3.models.Student;

/**
 * @overview represents the data manager class responsible for managing the
 *           module objects.
 * 
 * @attributes students ArrayList gui JFrame
 * 
 * @abstract_properties
 * 
 *                      <pre>
 *  optional(module) = false /\
 *  optional(gui) = false
 *                      </pre>
 * 
 * @author hungpv
 */
public class ModuleManager extends Manager implements Serializable {

	@DomainConstraint(type = DomainConstraint.Type.String, mutable = false, optional = false, length = 255)
	private static String storageFile = "modules.dat";

	@DomainConstraint(type = DomainConstraint.Type.String, mutable = false, optional = false)
	private static String title = "ModuleManager";

//	@DomainConstraint(type = DomainConstraint.Type.Object, optional = false)
//	private ArrayList<Module> modules;

	@DomainConstraint(type = DomainConstraint.Type.Object, optional = false)
	private JTextField tfName;

	@DomainConstraint(type = DomainConstraint.Type.Object, optional = false)
	private JTextField tfSemester;

	@DomainConstraint(type = DomainConstraint.Type.Object, optional = false)
	private JTextField tfCredits;

	@DomainConstraint(type = DomainConstraint.Type.Object, optional = false)
	private JTextField tfDepart;

	@DomainConstraint(type = DomainConstraint.Type.Object, optional = false)
	private JComboBox box;

	@DomainConstraint(type = DomainConstraint.Type.Object, optional = false)
	private JLabel department;

	@DomainConstraint(type = DomainConstraint.Type.Object, optional = false)
	private String choice;

	@DomainConstraint(type = DomainConstraint.Type.Object, optional = false)
	private Module newModule;

	/**
	 * @effects initialise <tt>this</tt> with an empty set of module <br>
	 *          {@link #createGUI()}: create <tt>gui</tt>
	 */
	public ModuleManager() {
		super(title, storageFile);
//		createGUI();
	}

	@Override
	public void createMiddlePanel() {
		// TODO Auto-generated method stub
		gui.setTitle("Create new module");
		gui.setSize(300, 300);
		gui.setLocation(200, 200);

		JPanel topPan = new JPanel();
		gui.add(topPan, BorderLayout.NORTH);
		gui.addWindowListener(this);

		JLabel lb1 = new JLabel("Enter module details");
		topPan.add(lb1);

		middlePanel = new JPanel();
		middlePanel.setBorder(BorderFactory.createEtchedBorder());
		middlePanel.setLayout(new GridLayout(5, 5));
		gui.add(middlePanel, BorderLayout.CENTER);

		JLabel type = new JLabel("Module Type");
		String[] typeName = { "Compulsory Module", "Elective Module" };
		box = new JComboBox(typeName);

		middlePanel.add(type);
		middlePanel.add(box);

		JLabel name = new JLabel("Name(*)");
		tfName = new JTextField(20);
		middlePanel.add(name);
		middlePanel.add(tfName);

		JLabel semester = new JLabel("Semester (Integer) (*)");
		tfSemester = new JTextField(20);
		middlePanel.add(semester);
		middlePanel.add(tfSemester);

		JLabel credits = new JLabel("Credits (Integer) (*)");
		tfCredits = new JTextField(20);
		middlePanel.add(credits);
		middlePanel.add(tfCredits);

		department = new JLabel("Department(*)");
		department.setVisible(false);
		tfDepart = new JTextField(20);
		tfDepart.setVisible(false);
		middlePanel.add(department);
		middlePanel.add(tfDepart);

		box.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				choice = box.getSelectedItem().toString();
				if (choice.equals("Compulsory Module")) {
					tfDepart.setVisible(false);
					department.setVisible(false);
				} else if (choice.equals("Elective Module")) {
					tfDepart.setVisible(true);
					department.setVisible(true);
				}
			}
		});

	}

	/**
	 * @requires <tt>gui != null</tt>
	 * @effects show <tt>gui</tt>
	 */
	public void display() {
		gui.setVisible(true);
	}

	@Override
	public Object createObject() throws NotPossibleException {
		// TODO Auto-generated method stub
		String name = tfName.getText();
		String credits = tfCredits.getText();
		String semester = tfSemester.getText();
		String depart = tfDepart.getText();
		newModule = null;
		try {
			choice = box.getSelectedItem().toString();
			if (choice.equals("Compulsory Module")) {
				if (name.isEmpty() || credits.isEmpty() || semester.isEmpty()) {
					showErrorMessage("Please fill all field (*)");
				} else {
					newModule = new CompulsoryModule(name, Integer.parseInt(semester), Integer.parseInt(credits));
					return newModule;
				}
			} else if (choice.equals("Elective Module")) {

				if (name.isEmpty() || credits.isEmpty() || semester.isEmpty() || depart.isEmpty()) {
					showErrorMessage("Please fill all field (*)");
				} else {
					newModule = new ElectiveModule(name, Integer.parseInt(semester), Integer.parseInt(credits), depart);
					return newModule;
				}
			}
		} catch (NotPossibleException e) {
			showErrorMessage("Create Module is unsuccess");
		} catch (NumberFormatException e) {
			showErrorMessage("Semester and credits are number, not string");
		} catch (Exception e) {
			showErrorMessage("Semester and credits are number, not string");
		}
		return null;
	}

	/**
	 * @effects return student object with specified <tt>id</tt> in
	 *          <tt>this.objects</tt> OR null if not found
	 */
	public Module getModuleByCode(String id) {
		for (Object ob : objects) {
			Module st = (Module) ob;
			if (st.getCode().equals(id)) {
				return st;
			}
		}
		showErrorMessage("Not Found Module Code = " + id);
		return null;
	}

}
