package java_sqlite_3005;

public class Display {
	/*
	 * This class represents a phone
	 */
	
	private float screensize;
	private String resolution;
	private String touchtype;
	private String pixel_density;
	private String material_used;

	/**
	 * @param screensize
	 * @param resolution
	 * @param touchtype
	 * @param pixel_density
	 * @param material_used
	 */
	public Display(float screensize, String resolution, String touchtype,
			String pixel_density, String material_used) {
		this.screensize = screensize;
		this.resolution = resolution;
		this.touchtype = touchtype;
		this.pixel_density = pixel_density;
		this.material_used = material_used;
	}

	/**
	 * @return the screensize
	 */
	public float getScreensize() {
		return screensize;
	}

	/**
	 * @return the resolution
	 */
	public String getResolution() {
		return resolution;
	}

	/**
	 * @return the touchtype
	 */
	public String getTouchtype() {
		return touchtype;
	}

	/**
	 * @return the pixel_density
	 */
	public String getPixel_density() {
		return pixel_density;
	}

	/**
	 * @return the material_used
	 */
	public String getMaterial_used() {
		return material_used;
	}

	/**
	 * @param screensize the screensize to set
	 */
	public void setScreensize(float screensize) {
		this.screensize = screensize;
	}

	/**
	 * @param resolution the resolution to set
	 */
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	/**
	 * @param touchtype the touchtype to set
	 */
	public void setTouchtype(String touchtype) {
		this.touchtype = touchtype;
	}

	/**
	 * @param pixel_density the pixel_density to set
	 */
	public void setPixel_density(String pixel_density) {
		this.pixel_density = pixel_density;
	}

	/**
	 * @param material_used the material_used to set
	 */
	public void setMaterial_used(String material_used) {
		this.material_used = material_used;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Display [screensize=" + screensize + ", resolution="
				+ resolution + ", touchtype=" + touchtype + ", pixel_density="
				+ pixel_density + ", material_used=" + material_used + "]";
	}

}