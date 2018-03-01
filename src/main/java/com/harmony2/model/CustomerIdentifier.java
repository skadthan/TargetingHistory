package com.harmony2.model;
/**
 * Disposition entity class.
 * @version 1.0
 * @since Feb 02, 2018
 */
public class CustomerIdentifier {
    
    public String getSsoid() {
		return ssoid;
	}

	public void setSsoid(String ssoid) {
		this.ssoid = ssoid;
	}

	private String ssoid;    
    
   
    
    /**
     * Default Constructor
     */
    public CustomerIdentifier() {
        super();        
    }
    
    @Override
    public String toString() {
        return "CustomerIdentifier [ssoid=" + ssoid + "]";
    }  

}
