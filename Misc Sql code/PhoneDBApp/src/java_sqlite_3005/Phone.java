package java_sqlite_3005;

public class Phone {
	/*
	 * This class represents a phone
	 */
	
	private int imie; // imie number
	private String status; //status
	private String modelID; // model
	
	public Phone(int a_imie, String aStatus, String aModelID){
		imie = a_imie;
		status = aStatus;
		modelID = aModelID;
	}

	/**
	 * @return the imie
	 */
	public int getImie() {
		return imie;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the modelID
	 */
	public String getModelID() {
		return modelID;
	}

	/**
	 * @param imie the imie to set
	 */
	public void setImie(int imie) {
		this.imie = imie;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param modelID the modelID to set
	 */
	public void setModelID(String modelID) {
		this.modelID = modelID;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Phone [imie=" + imie + ", status=" + status + ", modelID="
				+ modelID + "]";
	}

}