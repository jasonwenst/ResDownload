package entity;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import constances.Constance;
import utils.NumberFormatUtils;

public class FileStatusBean {
	
	private Log log = LogFactory.getLog(FileStatusBean.class);

	private String fileName;
	private double totalLength;
	private double compoletedLt;
	private boolean isCompoleted;
	
	
	public FileStatusBean(String fileName, double totalLength, double compoletedLt, boolean isCompoleted) {
		super();
		this.fileName = fileName;
		this.totalLength = totalLength;
		this.compoletedLt = compoletedLt;
		this.isCompoleted = isCompoleted;
	}
	
	
	public String getSizeAsMB() {
		log.info("totalLength = " + totalLength + "compoletedLt = " + compoletedLt);
		if(isCompoleted) {
			return NumberFormatUtils.formatByPattern(totalLength/1024/1024, Constance.PATTERN) + "MB";
		} else {
			return NumberFormatUtils.formatByPattern(compoletedLt/1024/1024, Constance.PATTERN) + "/" + NumberFormatUtils.formatByPattern(totalLength/1024/1024, Constance.PATTERN) + "MB";
		}
	}
	
	public String getStatus() {
		return isCompoleted ? "compoleted" : "downloading";
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public double getTotalLength() {
		return totalLength;
	}
	public void setTotalLength(double totalLength) {
		this.totalLength = totalLength;
	}
	public double getCompoletedLt() {
		return compoletedLt;
	}
	public void setCompoletedLt(double compoletedLt) {
		this.compoletedLt = compoletedLt;
	}
	public boolean getIsCompoleted() {
		return isCompoleted;
	}
	public void setIsCompoleted(boolean isCompoleted) {
		this.isCompoleted = isCompoleted;
	}

	@Override
	public String toString() {
		return "FileStatusBean [fileName=" + fileName + ", totalLength=" + totalLength + ", compoletedLt="
				+ compoletedLt + ", isCompoleted=" + isCompoleted + "]";
	}
	
	
	
}
