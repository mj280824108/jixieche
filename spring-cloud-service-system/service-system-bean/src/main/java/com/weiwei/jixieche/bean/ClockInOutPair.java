package com.weiwei.jixieche.bean;


import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 */
public class ClockInOutPair implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 上班卡记录ID
	 */
	private Long clockInId;
	/**
	 * 下班卡记录ID
	 */
	private Long clockOutId;
	/**
	 * 司机ID
	 */
	private Integer driverId;

	/**
	 * 机械Id
	 */
	private Integer machineId;
	/**
	 * 子账号ID
	 */
	private Integer childAccountId;
	/**
	 * 上班时间
	 */
	private Date clockInTime;
	/**
	 * 下班时间
	 */
	private Date clockOutTime;

	public Long getClockInId() {
		return clockInId;
	}

	public void setClockInId(Long clockInId) {
		this.clockInId = clockInId;
	}

	public Long getClockOutId() {
		return clockOutId;
	}

	public void setClockOutId(Long clockOutId) {
		this.clockOutId = clockOutId;
	}

	public Integer getDriverId() {
		return driverId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}

	public Integer getMachineId() {
		return machineId;
	}

	public void setMachineId(Integer machineId) {
		this.machineId = machineId;
	}

	public Integer getChildAccountId() {
		return childAccountId;
	}

	public void setChildAccountId(Integer childAccountId) {
		this.childAccountId = childAccountId;
	}

	public Date getClockInTime() {
		return clockInTime;
	}

	public void setClockInTime(Date clockInTime) {
		this.clockInTime = clockInTime;
	}

	public Date getClockOutTime() {
		return clockOutTime;
	}

	public void setClockOutTime(Date clockOutTime) {
		this.clockOutTime = clockOutTime;
	}
}
