package courseman3.models;
//import utils.DomainConstraint;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import courseman3.DomainConstraint;

/**
 * @overview a student is a person who is undergraduate
 * 
 * @attributes id String name String dob String address String email String
 * 
 * @object A typical student is <i,n,d,a,e> where id<i>, name<n>, dob<d>,
 *         address<a> and email<e>
 * 
 * @abstract_properties
 * 
 *                      <pre>
 * 						mutable(id)=false/\optional(id)=false/\
 *                      mutable(name)=false/\optional(name)=false/\min(name)=1/\
 *                      mutable(dob)=true/\optional(dob)=false/\min(dob)=1/\
 *                      mutable(address)=true/\optional(address)=false/\min(address)=1
 *                      mutable(email)=true/\optional(email)=false/\min(email)=1
 *                      </pre>
 *
 */

public class Student implements Serializable {

	
	private static int idTemp = 2019;
	
	@DomainConstraint(type = DomainConstraint.Type.String, optional = false)
	private String id;
	
	@DomainConstraint(type = DomainConstraint.Type.String, optional = false)
	private String name;

	@DomainConstraint(type = DomainConstraint.Type.String, optional = false)
	private String dob;

	@DomainConstraint(type = DomainConstraint.Type.String, optional = false)
	private String address;

	@DomainConstraint(type = DomainConstraint.Type.String, optional = false)
	private String email;

	/**
	 * @effects
	 * 
	 *          <pre>
	 * 		if validate
	 * 			initialise this as Student<id,name,dob,address,email>
	 * 		else
	 * 			print error
	 *          </pre>
	 * 
	 * @param name
	 * @param dob
	 * @param address
	 * @param email
	 */
	public Student(String name, String dob, String address, String email) {
		this.id = "S" + idTemp++;
		this.name = name;
		this.dob = dob;
		this.address = address;
		this.email = email;
	}

	/**
	 * @effects return id
	 * 
	 */
	public String getId() {
		return id;

	}

	/**
	 * @effects return name
	 * 
	 */
	public String getName() {
		return name;
	}

	/**
	 * @effects this.name = name
	 * @param address
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @effects return dob
	 * 
	 */
	public String getDob() {
		return dob;
	}

	/**
	 * @effects this.dob = dob
	 * @param address
	 */
	public void setDob(String dob) {
		this.dob = dob;
	}

	/**
	 * @effects return address
	 * 
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @effects this.address = address
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @effects return email
	 * 
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @effects this.email = email
	 * @param address
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @effects
	 * 
	 *          <pre>
	 * 		if name.length < 1 || name == null
	 * 			print error
	 * 			return false
	 * 		else
	 * 			return true
	 *          </pre>
	 * 
	 */

	private boolean validateName(String name) {
		if (name.length() < 1 || name == null) {
			System.out.println("invalid name");
			return false;
		}
		return true;
	}

	/**
	 * @effects
	 * 
	 *          <pre>
	 * if address.length < 1 || address == null
	 * 			print error
	 * 			return false
	 * 		else
	 * 			return true
	 *          </pre>
	 * 
	 */
	private boolean validateAddress(String address) {
		if (address.length() < 1 || address == null) {
			return false;
		}
		return true;
	}

	/**
	 * @effects
	 * 
	 *          <pre>
	 * 	if email.length < 1 || email == null
	 * 			print error
	 * 			return false
	 * 		else
	 * 			return true
	 *          </pre>
	 * 
	 */
	private boolean validateEmail(String email) {
		if (email.length() < 1 || email == null) {
			return false;
		}
		return true;
	}
	
	private void getStudentByID(String id) {
		// TODO Auto-generated method stub

	}

	/**
	 * @effects
	 * 
	 *          <pre>
	 *  	if dob.length < 1 || dob == null
	 * 			print error
	 * 			return false
	 * 		else
	 * 			return true
	 *          </pre>
	 */
	private boolean validateDob(String dob) {
		if (dob.length() < 1 || dob == null) {
			return false;
		}
		return true;
	}

	/**
	 * @effects
	 * 
	 *          <pre>
	 * 		if this valid with regrards to abstract_properties
	 * 			return true
	 * 		else
	 * 			return false
	 *          </pre>
	 * 
	 */
	public boolean repOK() {
		if (validateName(name) && validateAddress(address) && validateEmail(email) && validateDob(dob)) {
			return true;
		}
		return false;
	}

	/**
	 * return Student
	 */
	public String toString() {
		if (repOK() == false) {
			return "Error in create student, please check again";
		}
		return "Student <" + id + ", " + name + ", " + dob + ", " + address + ", " + email + ">";
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException, ClassNotFoundException {
		out.defaultWriteObject();
		out.writeObject(idTemp);
		
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		// invoke default read first
		in.defaultReadObject();

		// read static variables		
		idTemp = (int) in.readObject();
	}

}
