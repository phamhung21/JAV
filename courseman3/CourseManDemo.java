package courseman3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.management.JMException;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import courseman3.controllers.EnrolmentManager;
import courseman3.controllers.Manager;
import courseman3.controllers.ModuleManager;
import courseman3.controllers.StudentManager;
import courseman3.models.Enrolment;

/**
 * @overview Represents the main class of the CourseMan program.
 * 
 * @attributes sman StudentManager mman ModuleManager gui JFrame
 * 
 * @abstract_properties optional(sman) = false /\ optional(mman) = false /\
 *                      optional(gui) = false
 * 
 * @author congnv
 */
public class CourseManDemo extends WindowAdapter implements ActionListener {

	@DomainConstraint(type = DomainConstraint.Type.Object, optional = false)
	private StudentManager sman; // the student manager

	@DomainConstraint(type = DomainConstraint.Type.Object, optional = false)
	private ModuleManager mman; // the module manager

	@DomainConstraint(type = DomainConstraint.Type.Object, optional = false)
	private EnrolmentManager eman; // the enrollment manager

	@DomainConstraint(type = DomainConstraint.Type.Object, optional = false)
	private JFrame gui;

	@DomainConstraint(type = DomainConstraint.Type.Object, optional = false)
	private JMenuItem exit;

	@DomainConstraint(type = DomainConstraint.Type.Object, optional = false)
	private JMenuItem newStudent;

	@DomainConstraint(type = DomainConstraint.Type.Object, optional = false)
	private JMenuItem newModule;

	@DomainConstraint(type = DomainConstraint.Type.Object, optional = false)
	private JMenuItem newEnrolment, iniReport, assReport;

	/**
	 * @effects initialise <tt>sman, mman</tt> <br>
	 *          {@link #createGUI()}: create <tt>gui</tt>
	 */
	public CourseManDemo() {
		sman = new StudentManager();
		mman = new ModuleManager();
		eman = new EnrolmentManager(sman, mman);
		createGUI();
	}

	/**
	 * @modifies this.gui
	 * @effects create <tt>gui</tt> that has a menu bar with:
	 *          <ol>
	 *          <li>File menu has one item: Exit
	 *          <li>Student menu has one item: New Student (to create a new student)
	 *          <li>Module menu has one item: New Module (to create a new module)
	 *          </ol>
	 *          The action listener of the menu items is <tt>this</tt>.
	 */
	public void createGUI() {
		gui = new JFrame();
		gui.setTitle("CourseMan3");
		gui.setSize(500, 400);
		gui.setLocation(200, 200);
		gui.addWindowListener(this);

		JPanel topPan = new JPanel();
		gui.add(topPan, BorderLayout.NORTH);

		JMenuBar menuBar = new JMenuBar();
		topPan.add(menuBar);
		gui.setJMenuBar(menuBar);
		JMenu file = new JMenu("File");
		JMenu student = new JMenu("Student");
		JMenu module = new JMenu("Module");
		JMenu enrolment = new JMenu("Enrolment");
		menuBar.add(file);
		menuBar.add(student);
		menuBar.add(module);
		menuBar.add(enrolment);

		exit = new JMenuItem("Exit");
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_MASK));
		exit.addActionListener(this);
		file.add(exit);

		newStudent = new JMenuItem("New Student");
		newStudent.addActionListener(this);
		student.add(newStudent);
		
		newModule = new JMenuItem("New Module");
		newModule.addActionListener(this);
		module.add(newModule);
		
		newEnrolment = new JMenuItem("New Enrolment");
		newEnrolment.addActionListener(this);
		enrolment.add(newEnrolment);
		
		iniReport = new JMenuItem("Initial Report");
		iniReport.addActionListener(this);
		enrolment.add(iniReport);
		
		assReport = new JMenuItem("Assessment Report");
		assReport.addActionListener(this);
		enrolment.add(assReport);


		// add image to GUI
		JPanel midPanel = new JPanel();
		gui.add(midPanel, BorderLayout.CENTER);

		JLabel label = new JLabel();
		label.setSize(400, 300);

		ImageIcon icon = new ImageIcon("src/courseman3/images/hung.jpg");
		Image image = icon.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon newIcon = new ImageIcon(image);

		label.setIcon(newIcon);
		midPanel.add(label);

	}

	/**
	 * @effects show <tt>gui</tt>
	 */
	public void display() {
		gui.setVisible(true);
	}

	@Override
	public void windowClosing(WindowEvent e) {
		shutDown();
	}

	/**
	 * @effects handles user actions on the menu items
	 * 
	 *          <pre>
	 *          if menu item is Student/New Student
	 *            {@link #sman}.display()}
	 *          else if menu item is Module/New Module
	 *            {@link #mman}.display()
	 *          else if menu item is File/Exit 
	 *            {@link #shutDown()}
	 *          </pre>
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("New Student")) {

			sman.display();
		} else if (cmd.equals("New Module")) {

			mman.display();
		} else if (cmd.equals("New Enrolment")) {
			
			eman.display();
		} else if (cmd.equals("Initial Report")) {
			
			eman.report();
		}else if(cmd.equals("Assessment Report")){
			
			eman.reportAssessment();
		}else if (cmd.equals("Exit")) {
			shutDown();
		}

	}

	/**
	 * @effects start up <tt>sman, mman</tt>
	 */
	public void startUp() {
		sman.startUp();
		mman.startUp();
		eman.startUp();
	}

	/**
	 * @effects shut down <tt>sman, mman</tt> <br>
	 *          dispose <tt>gui</tt> and end the program.
	 */
	public void shutDown() {
		sman.shutDown();
		mman.shutDown();
		eman.shutDown();
		gui.dispose();
		System.exit(1);
	}

	/**
	 * The run method
	 * 
	 * @effects create an instance of <tt>CourseManDemo</tt> {@link #startUp()}:
	 *          start up the <tt>CourseManDemo</tt> instance {@link #display()}:
	 *          display the main gui of <tt>CourseManDemo</tt> instance
	 */
	public static void main(String[] args) {
		CourseManDemo app = new CourseManDemo();
		app.startUp();
		app.display();
	}
}
