package java_sqlite_3005;

public class Model {
	/*
	 * This class represents a model
	 */
	
	private String modelID;
	private String model_name;
	private String dimention;
	private String weight;
	private String connectivity;
	private String ports;
	private String sensor;
	private String battery;
	private float storage;
	private float ram;
	private String maufacture;
	private SoC soc;
	private Software software;
	private Camera primaryCamrea;
	private Camera secondaryCamera;
	private Display display;

	/**
	 * @param modelID
	 * @param model_name
	 * @param dimention
	 * @param weight
	 * @param connectivity
	 * @param ports
	 * @param sensor
	 * @param battery
	 * @param storage
	 * @param ram
	 * @param maufacture
	 * @param soc
	 * @param software
	 * @param primaryCamrea
	 * @param secondaryCamera
	 * @param display
	 */
	public Model(String modelID, String model_name, String dimention,
			String weight, String connectivity, String ports, String sensor,
			String battery, float storage, float ram, String maufacture,
			SoC soc, Software software, Camera primaryCamrea,
			Camera secondaryCamera, Display display) {
		this.modelID = modelID;
		this.model_name = model_name;
		this.dimention = dimention;
		this.weight = weight;
		this.connectivity = connectivity;
		this.ports = ports;
		this.sensor = sensor;
		this.battery = battery;
		this.storage = storage;
		this.ram = ram;
		this.maufacture = maufacture;
		this.soc = soc;
		this.software = software;
		if(primaryCamrea.getMegapixel()>0)
			this.primaryCamrea = primaryCamrea;
		if(secondaryCamera.getMegapixel()>0)
			this.secondaryCamera = secondaryCamera;
		this.display = display;
	}

	/**
	 * @return the modelID
	 */
	public String getModelID() {
		return modelID;
	}

	/**
	 * @return the model_name
	 */
	public String getModel_name() {
		return model_name;
	}

	/**
	 * @return the dimention
	 */
	public String getDimention() {
		return dimention;
	}

	/**
	 * @return the weight
	 */
	public String getWeight() {
		return weight;
	}

	/**
	 * @return the connectivity
	 */
	public String getConnectivity() {
		return connectivity;
	}

	/**
	 * @return the ports
	 */
	public String getPorts() {
		return ports;
	}

	/**
	 * @return the sensor
	 */
	public String getSensor() {
		return sensor;
	}

	/**
	 * @return the battery
	 */
	public String getBattery() {
		return battery;
	}

	/**
	 * @return the storage
	 */
	public float getStorage() {
		return storage;
	}

	/**
	 * @return the ram
	 */
	public float getRam() {
		return ram;
	}

	/**
	 * @return the maufacture
	 */
	public String getMaufacture() {
		return maufacture;
	}

	/**
	 * @return the soc
	 */
	public SoC getSoc() {
		return soc;
	}

	/**
	 * @return the software
	 */
	public Software getSoftware() {
		return software;
	}

	/**
	 * @return the primaryCamrea
	 */
	public Camera getPrimaryCamrea() {
		return primaryCamrea;
	}

	/**
	 * @return the secondaryCamera
	 */
	public Camera getSecondaryCamera() {
		return secondaryCamera;
	}

	/**
	 * @return the display
	 */
	public Display getDisplay() {
		return display;
	}

	/**
	 * @param modelID the modelID to set
	 */
	public void setModelID(String modelID) {
		this.modelID = modelID;
	}

	/**
	 * @param model_name the model_name to set
	 */
	public void setModel_name(String model_name) {
		this.model_name = model_name;
	}

	/**
	 * @param dimention the dimention to set
	 */
	public void setDimention(String dimention) {
		this.dimention = dimention;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(String weight) {
		this.weight = weight;
	}

	/**
	 * @param connectivity the connectivity to set
	 */
	public void setConnectivity(String connectivity) {
		this.connectivity = connectivity;
	}

	/**
	 * @param ports the ports to set
	 */
	public void setPorts(String ports) {
		this.ports = ports;
	}

	/**
	 * @param sensor the sensor to set
	 */
	public void setSensor(String sensor) {
		this.sensor = sensor;
	}

	/**
	 * @param battery the battery to set
	 */
	public void setBattery(String battery) {
		this.battery = battery;
	}

	/**
	 * @param storage the storage to set
	 */
	public void setStorage(float storage) {
		this.storage = storage;
	}

	/**
	 * @param ram the ram to set
	 */
	public void setRam(float ram) {
		this.ram = ram;
	}

	/**
	 * @param maufacture the maufacture to set
	 */
	public void setMaufacture(String maufacture) {
		this.maufacture = maufacture;
	}

	/**
	 * @param soc the soc to set
	 */
	public void setSoc(SoC soc) {
		this.soc = soc;
	}

	/**
	 * @param software the software to set
	 */
	public void setSoftware(Software software) {
		this.software = software;
	}

	/**
	 * @param primaryCamrea the primaryCamrea to set
	 */
	public void setPrimaryCamrea(Camera primaryCamrea) {
		this.primaryCamrea = primaryCamrea;
	}

	/**
	 * @param secondaryCamera the secondaryCamera to set
	 */
	public void setSecondaryCamera(Camera secondaryCamera) {
		this.secondaryCamera = secondaryCamera;
	}

	/**
	 * @param display the display to set
	 */
	public void setDisplay(Display display) {
		this.display = display;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Model [modelID=" + modelID + ", model_name=" + model_name
				+ ", dimention=" + dimention + ", weight=" + weight
				+ ", connectivity=" + connectivity + ", ports=" + ports
				+ ", sensor=" + sensor + ", battery=" + battery + ", storage="
				+ storage + ", ram=" + ram + ", maufacture=" + maufacture
				+ ", soc=" + soc + ", software=" + software
				+ ", primaryCamrea=" + primaryCamrea + ", secondaryCamera="
				+ secondaryCamera + ", display=" + display + "]";
	}

}