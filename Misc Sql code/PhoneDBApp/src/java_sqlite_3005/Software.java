package java_sqlite_3005;

public class Software {
	/*
	 * This class represents a software
	 */

	private String os;
	private String version;

	/**
	 * @param os
	 * @param version
	 */
	public Software(String os, String version) {
		this.os = os;
		this.version = version;
	}

	/**
	 * @return the os
	 */
	public String getOs() {
		return os;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param os the os to set
	 */
	public void setOs(String os) {
		this.os = os;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Software [os=" + os + ", version=" + version + "]";
	}

}