package java_sqlite_3005;

public class SoC {
	/*
	 * This class represents a SoC
	 */
	
	private String chipset;
	private String gpu;
	private int cpuCore;
	private int cpuSpeed;

	/**
	 * @param chipset
	 * @param gpu
	 * @param cpuCore
	 * @param cpuSpeed
	 */
	public SoC(String chipset, String gpu, int cpuCore, int cpuSpeed) {
		this.chipset = chipset;
		this.gpu = gpu;
		this.cpuCore = cpuCore;
		this.cpuSpeed = cpuSpeed;
	}

	/**
	 * @return the chipset
	 */
	public String getChipset() {
		return chipset;
	}

	/**
	 * @return the gpu
	 */
	public String getGpu() {
		return gpu;
	}

	/**
	 * @return the cpuCore
	 */
	public int getCpuCore() {
		return cpuCore;
	}

	/**
	 * @return the cpuSpeed
	 */
	public int getCpuSpeed() {
		return cpuSpeed;
	}

	/**
	 * @param chipset the chipset to set
	 */
	public void setChipset(String chipset) {
		this.chipset = chipset;
	}

	/**
	 * @param gpu the gpu to set
	 */
	public void setGpu(String gpu) {
		this.gpu = gpu;
	}

	/**
	 * @param cpuCore the cpuCore to set
	 */
	public void setCpuCore(int cpuCore) {
		this.cpuCore = cpuCore;
	}

	/**
	 * @param cpuSpeed the cpuSpeed to set
	 */
	public void setCpuSpeed(int cpuSpeed) {
		this.cpuSpeed = cpuSpeed;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SoC [chipset=" + chipset + ", gpu=" + gpu + ", cpuCore="
				+ cpuCore + ", cpuSpeed=" + cpuSpeed + "]";
	}

}