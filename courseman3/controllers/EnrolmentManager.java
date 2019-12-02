package courseman3.controllers;

import courseman3.DomainConstraint;
import courseman3.NotPossibleException;
import courseman3.models.Enrolment;
//import courseman1.Arrays;
import courseman3.models.Student;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class EnrolmentManager extends Manager implements Serializable {

//	@DomainConstraint(type = DomainConstraint.Type.Object, optional = false)
//	private ArrayList enrolment;

	@DomainConstraint(type = DomainConstraint.Type.Object, optional = false)
	private JTextField tfStudentID;

	@DomainConstraint(type = DomainConstraint.Type.Object, optional = false)
	private JTextField tfModuleID;

	@DomainConstraint(type = DomainConstraint.Type.Object, optional = false)
	private JTextField tfInternalMark;

	@DomainConstraint(type = DomainConstraint.Type.Object, optional = false)
	private JTextField tfExamMark;

//	@DomainConstraint(type = DomainConstraint.Type.Object, optional = false)
//	private Enrolment newEnrolment;

	private StudentManager stMan;

	private ModuleManager modMan;

	public EnrolmentManager(StudentManager sman, ModuleManager mman) {
		super("EnrolmentManger", "enrolments.dat");
		this.stMan = sman;
		this.modMan = mman;
//		createGUI();

	}

	@Override
	public void createMiddlePanel() {
		gui.setTitle("Create new enrolment");
		gui.setSize(300, 300);
		gui.setLocation(200, 200);

		JPanel topPan = new JPanel();
		gui.add(topPan, BorderLayout.NORTH);
		gui.addWindowListener(this);

		JLabel lb1 = new JLabel("Enter enrolment detail: ");
		topPan.add(lb1);

		middlePanel = new JPanel();
		middlePanel.setBorder(BorderFactory.createEtchedBorder());
		middlePanel.setLayout(new GridLayout(4, 4));
		gui.add(middlePanel, BorderLayout.CENTER);

		JLabel stID = new JLabel("StudentID(*): ");
		tfStudentID = new JTextField();

		JLabel modID = new JLabel("MoudelID(*): ");
		tfModuleID = new JTextField();

		JLabel interMark = new JLabel("Internal Mark: ");
		tfInternalMark = new JTextField();

		JLabel exMark = new JLabel("Exam Mark: ");
		tfExamMark = new JTextField();

		middlePanel.add(stID);
		middlePanel.add(tfStudentID);

		middlePanel.add(modID);
		middlePanel.add(tfModuleID);

		middlePanel.add(interMark);
		middlePanel.add(tfInternalMark);

		middlePanel.add(exMark);
		middlePanel.add(tfExamMark);

	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		gui.setVisible(true);
	}

	@Override
	public Object createObject() throws NotPossibleException {
		try {
			if (tfStudentID.getText().isEmpty() || tfModuleID.getText().isEmpty() || tfInternalMark.getText().isEmpty()
					|| tfExamMark.getText().isEmpty()) {
				showErrorMessage("All field must not be empty");
			} else {
				Enrolment newEnrolment = new Enrolment(stMan.getStudentByID(tfStudentID.getText()),
						modMan.getModuleByCode(tfModuleID.getText()), Double.parseDouble(tfInternalMark.getText()),
						Double.parseDouble(tfExamMark.getText()));
				return newEnrolment;
			}
		} catch (NotPossibleException e) {
			showErrorMessage("Invalid input!!! Check again");
		} catch (NumberFormatException e) {
			showErrorMessage("Invalid mark!! Please check again");
		} catch (NullPointerException e) {
			showErrorMessage("Invalid input!!! Check again");
		}

		return null;
	}

	public void report() {
		JFrame gui = new JFrame("List of initial enrolments");
		gui.setSize(600, 250);
		gui.setLocation(500, 300);
		gui.addWindowListener(this);
		gui.setVisible(true);

		middlePanel = new JPanel();
		middlePanel.setLayout(new BorderLayout());
		gui.add(middlePanel, BorderLayout.CENTER);

		String header[] =  { "#", "Student ID", "Student name", "Module code", "Module name" };
		Object[][] data = {};

		DefaultTableModel model = new DefaultTableModel(data, header);

		for (int i = 0; i < objects.size(); i++) {
			Enrolment enrol = (Enrolment) objects.get(i);
//			System.out.println(enrol);


			model.addRow(new Object[] { String.valueOf(i + 1), enrol.getStudent().getId(), enrol.getStudent().getName(),
					enrol.getModule().getCode(), enrol.getModule().getName() });
		}
		JTable table = new JTable(model);
		JScrollPane srcTable = new JScrollPane(table);
		middlePanel.add(srcTable);
		table.setVisible(true);

	}

	public void reportAssessment() {
		JFrame gui = new JFrame("List of assessment enrolments");
		gui.setSize(600, 250);
		gui.setLocation(500, 300);
		gui.addWindowListener(this);
		gui.setVisible(true);

		middlePanel = new JPanel();
		middlePanel.setLayout(new BorderLayout());
		gui.add(middlePanel, BorderLayout.CENTER);

		String header[] = { "#", "Student ID", "Module code", "Internal Mark", "Exam Mark", "Final Grade" };
		Object[][] data = {};

		DefaultTableModel model = new DefaultTableModel(data, header);

		for (int i = 0; i < objects.size(); i++) {
			Enrolment enrol = (Enrolment) objects.get(i);

			model.addRow(new Object[] { String.valueOf(i + 1), enrol.getStudent().getId(), enrol.getModule().getCode(),
					enrol.getInternalMark(), enrol.getExamMark(), enrol.getFinalGrade() });
		}
		JTable table = new JTable(model);
		JScrollPane srcTable = new JScrollPane(table);
		middlePanel.add(srcTable);
		table.setVisible(true);

	}
}