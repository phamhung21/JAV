package courseman3.models;

import java.io.Serializable;
import java.util.Set;

import courseman3.DomainConstraint;

/**
 * @overview an enrolment is that one typical student registers to learn one
 *           specific module
 * 
 * @attributes student Student module Module internalMark Double double
 *             examinationMark double double finalGrade Character char
 * 
 * @object A typical enrolment is <s,m,i,e,f> where student<s>, module<m>,
 *         internalMark<i>, examinationMark<e> and finalGrade<f>
 *
 * @abstract_properties
 * 
 *                      <pre>
 * mutable(student)=true/\optional(student)=false/\
 * mutable(module)=true/\optional(module)=false/\
 * mutable(internalMark)=true/\optional(internalMark)=false/\
 * mutable(examinationMark)=true/\optional(examinationMark)=false/\
 * mutable(finalGrade)=true/\optional(finalGrade)=false
 *                      </pre>
 * 
 */
public class Enrolment implements Comparable,Serializable {

	@DomainConstraint(type = DomainConstraint.Type.Object, mutable = false, optional = false)
	private Student student;
	@DomainConstraint(type = DomainConstraint.Type.Object, mutable = false, optional = false)
	private Module module;
	@DomainConstraint(type = DomainConstraint.Type.Integer, mutable = true, optional = false, min = 0.0)
	private double internalMark;
	@DomainConstraint(type = DomainConstraint.Type.Integer, mutable = true, optional = false, min = 0.0)
	private double examMark;
	@DomainConstraint(type = DomainConstraint.Type.Char, mutable = true, optional = false)
	private char finalGrade;
	@DomainConstraint(type = DomainConstraint.Type.Char, mutable = true, optional = false, min = 0.0)
	private double mark;

	/**
	 * @effects if validate create enrolment else error
	 * @param student
	 * @param module
	 */
	public Enrolment(Student student, Module module, double internalMark, double examMark) {
		if (isValidateModule(module) && isValidateStudent(student)) {
			this.student = student;
			this.module = module;
			this.internalMark = internalMark;
			this.examMark = examMark;
		} else {

			System.out.println("error in create, please check again erolment");
		}

	}

	/**
	 * @effects if valid return true else return false
	 * @param student
	 * @return
	 */

	private boolean isValidateStudent(Student student) {
		if (student.repOK()) {
			return true;
		}
		return false;
	}

	/**
	 * @effects if valid return true else return false
	 * @param student
	 * @return
	 */
	private boolean isValidateModule(Module module) {
		if (module.repOK()) {
			return true;
		}
		return false;
	}

	/**
	 * @effects return student
	 * @return
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * @effects this.student = student
	 * @param student
	 */
	public void setStudent(Student student) {
		this.student = student;
	}

	/**
	 * @effects return module
	 * @return
	 */
	public Module getModule() {
		return module;
	}

	/**
	 * @effects return this.module = module
	 * @param module
	 */
	public void setModule(Module module) {
		this.module = module;
	}

	/**
	 * @effects return internal mark
	 * @return
	 */
	public double getInternalMark() {
		return internalMark;
	}

	/**
	 * @effects return this.internalMark = internalMark
	 * 
	 */
	public void setInternalMark(double internalMark) {
		this.internalMark = internalMark;
	}

	/**
	 * @effects return examMark
	 * @return
	 */
	public double getExamMark() {
		return examMark;
	}

	/**
	 * @effects return this.examMark = examMark
	 * 
	 */
	public void setExamMark(double examMark) {
		this.examMark = examMark;
	}

	/**
	 * @effects return this.mark = mark
	 * 
	 */
	public void setMark(double internalMark, double examMark) {
		this.internalMark = internalMark;
		this.examMark = examMark;
	}

	/**
	 * @effects return mark
	 * 
	 */
	public double getMark() {
		return mark;
	}

	/**
	 * @effects return this.finalGrade = finalGrade
	 * @param module
	 */
	public void setFinalGrade(char finalGrade) {
		this.finalGrade = finalGrade;
	}

	/**
	 * @effects return this.internalMark = internalMark
	 * @param module
	 */

	private boolean validateInternalMark(double internalMark) {
		if (internalMark < 0.0 || internalMark > 10.0) {
			return false;
		}
		return true;
	}

	/**
	 * @effects if valid return true else return false
	 * @param examMark
	 * @return
	 */
	private boolean validateExamMark(double examMark) {
		if (examMark < 0.0 || examMark > 10.0) {
			return false;
		}
		return true;
	}

	/**
	 * @effects if valid return true else return false
	 * @param finalGrade
	 * @return
	 */
	private boolean validateFinalGrade(char finalGrade) {
		if (finalGrade == 'P' || finalGrade == 'F' || finalGrade == 'E' || finalGrade == 'G') {
			return true;
		}
		return false;
	}

	/**
	 * @effects return finalGrade
	 * @return
	 */
	public char getFinalGrade() {
		double aggMark = 0.4 * internalMark + 0.6 * examMark;
		char finalGrade = 0;

		if (aggMark < 5.0) {
			finalGrade = 'F';
		}

		if (aggMark >= 5.0 && aggMark < 6) {
			finalGrade = 'P';
		}
		if (6 < aggMark && aggMark < 8.0) {
			finalGrade = 'G';
		}
		if (aggMark >= 8.0) {
			finalGrade = 'E';
		}
		return finalGrade;
	}

	/**
	 * @effects if valid return true else return false
	 * @return
	 */
	public boolean repOK() {
		if (isValidateModule(module) && isValidateStudent(student)) {
			return true;
		}
		return false;
	}

	/**
	 * @effects return enrolment
	 */
	public String toString() {

		return "Enrolment " + student.toString() + "\n" + "	  " + module.toString() + " ";
	}

	@Override
	public int compareTo(Object o) {
		if (o == null || (!(o instanceof Enrolment))) {
			System.err.println("Object is not Enrolment");
		}
		Enrolment thatE = (Enrolment) o;

		return this.student.getId().compareTo(thatE.student.getId());
	}
}
