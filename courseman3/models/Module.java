package courseman3.models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import courseman3.DomainConstraint;
import courseman3.NotPossibleException;

/**
 * @overview a module is a subject that undergraduates have to enroll
 * 
 * @attributes code String name String semester Integer int
 * 
 * @object A typical module is <c,n,s> where code<c>, name<n> and semester<s>
 * 
 * @abstract_properties
 * 
 *                      <pre>
 *                      mutable(code)=false/\optional(code)=false/\
 *                      mutable(name)=true/\optional(name)=false/\
 *                      mutable(semester)=true/\optional(semester)=false/\min(semester)=1/\max(semester)=9
 * 
 *                      <pre>
 *
 */
public class Module implements Serializable {

	private static int [] codeTemp = new int[10];

	@DomainConstraint(type = DomainConstraint.Type.Integer, optional = false)
	private int semester;

	@DomainConstraint(type = DomainConstraint.Type.String, optional = false)
	private String code;

	@DomainConstraint(type = DomainConstraint.Type.String, optional = false)
	private String name;

	@DomainConstraint(type = DomainConstraint.Type.Integer, optional = false)
	private int credits;

	/**
	 * @effects
	 * 
	 *          <pre>
	 *  if valid
	 * 			create Module
	 * 		else
	 * 			error
	 *          </pre>
	 * 
	 * @param name
	 * @param semester
	 * @param credits
	 * @throws Exception 
	 */
	public Module(String name, int semester, int credits) throws Exception {
		this.code= nextString(semester);
		this.name = name;
		this.semester = semester;
		this.credits = credits;

	}
	
	private String nextString (int semester ) throws Exception{
		if (semester<1|| semester>10) {
			
		}
		int now = 100*semester;
		now++;
		now = now+codeTemp[semester];
		codeTemp[semester]+=1;
		return "M"+now;
	}

	/**
	 * @effects return code
	 * @return
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @effects return name
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @effects
	 * 
	 *          <pre>
	 *          if valid set this.name = name
	 *          </pre>
	 * 
	 * @return
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @effects return semester
	 * @return
	 */
	public int getSemester() {
		return semester;
	}

	/**
	 * @effects
	 * 
	 *          <pre>
	 *          if valid set this.semester = semester
	 *          </pre>
	 * 
	 * @return
	 */
	public void setSemester(int semester) {
		this.semester = semester;
	}

	/**
	 * @effects return credits
	 * @return
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * @effects
	 * 
	 *          <pre>
	 *          if valid set this.credits = credits
	 *          </pre>
	 * 
	 * @return
	 */
	public void setCredits(int credits) {
		this.credits = credits;
	}

	/**
	 * @effects
	 * 
	 *          <pre>
	 *  
	 *          if valid return true else return false
	 *          </pre>
	 * 
	 * @return
	 */
	private boolean validateName(String name) {

		if (name.length() < 1 || name == null) {
			return false;
		}
		return true;
	}

	/**
	 * @effects
	 * 
	 *          <pre>
	 *          if valid return true else return false
	 *          </pre>
	 * 
	 * @return
	 */
	private boolean validateSemester(int semester) throws NotPossibleException{
		if (semester < 1) {
			return false;
		}
		return true;
	}

	/**
	 * @effects
	 * 
	 *          <pre>
	 *          if valid return true else return false
	 *          </pre>
	 * 
	 * @return
	 */
	private boolean validateCredits(int credits) throws NotPossibleException{
		if (credits < 1) {
			return false;
		}
		return true;
	}

	/**
	 * @effects
	 * 
	 *          <pre>
	 *          if valid return true else return false
	 *          </pre>
	 * 
	 * @return
	 */
	public boolean repOK() {
		if (validateName(name) && validateSemester(semester) && validateCredits(credits)) {
			return true;
		}
		return false;
	}

	/**
	 * @effects
	 * 
	 *          <pre>
	 * 
	 *          return Module
	 *          </pre>
	 * 
	 * @return
	 */
	public String toString() {

		if (repOK() == false) {
			return "Error in create module, please check again";
		}
		return "Module<" + code + ", " + name + ", semester " + semester + ", credits " + credits + ">";
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException, ClassNotFoundException {
		out.defaultWriteObject();
		out.writeObject(codeTemp);
		
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		// invoke default read first
		in.defaultReadObject();

		// read static variables		
		codeTemp = (int[]) in.readObject();
	}
}
