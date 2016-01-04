package java_sqlite_3005;

public class Camera {
	/*
	 * This class represents a phone
	 */

	private int megapixel;
	private String resolution;
	private String zoom;
	private String video;
	private String features;
	private String camera_type;

	/**
	 * @param megapixel
	 * @param resolution
	 * @param zoom
	 * @param video
	 * @param features
	 * @param camera_type
	 */
	public Camera(int megapixel, String resolution, String zoom, String video,
			String features, String camera_type) {
		this.megapixel = megapixel;
		this.resolution = resolution;
		this.zoom = zoom;
		this.video = video;
		this.features = features;
		this.camera_type = camera_type;
	}

	/**
	 * @return the megapixel
	 */
	public int getMegapixel() {
		return megapixel;
	}

	/**
	 * @return the resolution
	 */
	public String getResolution() {
		return resolution;
	}

	/**
	 * @return the zoom
	 */
	public String getZoom() {
		return zoom;
	}

	/**
	 * @return the video
	 */
	public String getVideo() {
		return video;
	}

	/**
	 * @return the features
	 */
	public String getFeatures() {
		return features;
	}

	/**
	 * @return the camera_type
	 */
	public String getCamera_type() {
		return camera_type;
	}

	/**
	 * @param megapixel the megapixel to set
	 */
	public void setMegapixel(int megapixel) {
		this.megapixel = megapixel;
	}

	/**
	 * @param resolution the resolution to set
	 */
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	/**
	 * @param zoom the zoom to set
	 */
	public void setZoom(String zoom) {
		this.zoom = zoom;
	}

	/**
	 * @param video the video to set
	 */
	public void setVideo(String video) {
		this.video = video;
	}

	/**
	 * @param features the features to set
	 */
	public void setFeatures(String features) {
		this.features = features;
	}

	/**
	 * @param camera_type the camera_type to set
	 */
	public void setCamera_type(String camera_type) {
		this.camera_type = camera_type;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Camera [megapixel=" + megapixel + ", resolution=" + resolution
				+ ", zoom=" + zoom + ", video=" + video + ", features="
				+ features + ", camera_type=" + camera_type + "]";
	}

}