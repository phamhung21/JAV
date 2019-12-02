package courseman3.models;

import java.io.Serializable;

/**
 * @overview An elective module is a module that belongs to specific department.
 * 
 * @attributes
 * 
 * department		String
 * 
 * @object
 * A typical elective module is <c,n,s,d> where code<c>, name<n>, semester<s> and department<d>
 * 
 * @abstract_properties
 * mutable(department)=false/\optional(department)=false
 *
 */
public class ElectiveModule extends Module {

	
	private String department;

	/**
	 * @effects
	 * if valid
	 * 	create ElectiveModule
	 * else
	 * 	error
	 * @param name
	 * @param semester
	 * @param credits
	 * @param department
	 * @throws Exception 
	 */
	public ElectiveModule(String name, int semester, int credits, String department) throws Exception {

		super(name, semester, credits);
		if (validateDepartment(department)) {
			this.department = department;

		}
	}

	/**
	 * @effects
	 * return department
	 * @return
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @effects
	 * 	this.department = department
	 * @param department
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * @effects
	 * if valid
	 * 		return true
	 * else
	 * 		return false
	 * @param department
	 * @return
	 */
	private boolean validateDepartment(String department) {
		if (department.length() < 1 || department == null) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		
		return "Module<" + super.getCode() + ", " + super.getName() + ", semester " + super.getSemester()
				+ ", credits " + super.getCredits() + ", department: " + department + ">";
	}

}
