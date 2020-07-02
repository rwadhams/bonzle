package com.wadhams.bonzle.dto

import groovy.transform.ToString

@ToString(includeNames=true)
class LineDTO {
	String latDegrees
	String lngDegrees
	String elevation
	String population
	String placeName
	
	double getDoubleLatDegrees() {
		return Double.parseDouble(latDegrees)
	}
	
	double getDoubleLngDegrees() {
		return Double.parseDouble(lngDegrees)
	}
	
	int getIntegerElevation() {
		return Integer.parseInt(elevation)
	}

	@Override
	public int hashCode() {
		return getIntegerElevation()
	}

	@Override
	public boolean equals(Object obj) {
		return this.placeName.equals(obj.getPlaceName())
	}
}
