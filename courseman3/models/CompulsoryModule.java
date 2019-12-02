package courseman3.models;


public class CompulsoryModule extends Module {
	
	/**
	 * @throws Exception 
	 * @overview A Compulsory Module is a module that students have to enroll
	 */
	public CompulsoryModule(String name, int semester, int credits) throws Exception {
		super(name, semester, credits);
	}

}
