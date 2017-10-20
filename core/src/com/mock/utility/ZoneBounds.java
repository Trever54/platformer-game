package com.mock.utility;

public class ZoneBounds {
	
	private Integer minX;
	private Integer maxX;
	private Integer minY;
	private Integer maxY;

	public ZoneBounds(Integer minX, Integer maxX, Integer minY, Integer maxY) {
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
	}
	
	public Integer getMaxX() {
		return maxX;
	}
	
	public Integer getMinX() {
		return minX;
	}
	
	public Integer getMaxY() {
		return maxY;
	}
	
	public Integer getMinY() {
		return minY;
	}
}
