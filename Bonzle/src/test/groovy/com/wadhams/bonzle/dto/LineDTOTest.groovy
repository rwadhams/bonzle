package com.wadhams.bonzle.dto

import spock.lang.Ignore
import spock.lang.Title

@Title("Unit tests for LineDTO")
class LineDTOTest extends spock.lang.Specification {
	
	//@Ignore
	def "test constructor"() {
		given:
			LineDTO dto = new LineDTO(latDegrees: '-33.74457', lngDegrees: '148.84893', elevation: '0476', population: '0000250', placeName: 'Woodstock, NSW')
		
		expect:
			dto.latDegrees == '-33.74457'
			dto.lngDegrees == '148.84893'
			dto.elevation == '0476'
			dto.population == '0000250'
			dto.placeName == 'Woodstock, NSW'
	}
	
	def "test integer elevation method"() {
		given:
			LineDTO dto = new LineDTO(latDegrees: '-33.74457', lngDegrees: '148.84893', elevation: '0476', population: '0000250', placeName: 'Woodstock, NSW')
		
		expect:
			dto.getIntegerElevation() == 476
	}

	def "test double lat.lng methods"() {
		given:
			LineDTO dto = new LineDTO(latDegrees: '-33.74457', lngDegrees: '148.84893', elevation: '0476', population: '0000250', placeName: 'Woodstock, NSW')
		
		expect:
			dto.getDoubleLatDegrees() == -33.74457d
			dto.getDoubleLngDegrees() == 148.84893d
	}

	def "test equals method"() {
		given:
			LineDTO same1 = new LineDTO(latDegrees: '-33.74457', lngDegrees: '148.84893', elevation: '0476', population: '0000250', placeName: 'Woodstock, NSW')
			LineDTO same2 = new LineDTO(latDegrees: '-33.74457', lngDegrees: '148.84893', elevation: '0476', population: '0000250', placeName: 'Woodstock, NSW')
			LineDTO diff1 = new LineDTO(latDegrees: '-33.74457', lngDegrees: '148.84893', elevation: '0476', population: '0000250', placeName: 'Different, NSW')
			
		expect:
			same1 == same2
			same1 != diff1
	}

}
