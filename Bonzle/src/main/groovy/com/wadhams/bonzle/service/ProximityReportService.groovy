package com.wadhams.bonzle.service

import java.text.NumberFormat
import java.util.regex.Matcher
import java.util.regex.Pattern

import com.wadhams.bonzle.dto.LineDTO
import com.wadhams.geolatlng.geo.GeoPosition
import com.wadhams.geolatlng.geo.Proximity

class ProximityReportService {
	Pattern linePattern = ~/(-\d\d\.[\d]{5})  (\d\d\d\.[\d]{5})  ([\d]{4})  ([\d]{7})  (.*)/
	
	List<LineDTO> lineList
	
	def execute(states, startingPlace, proximityDistance, minimumElevation) {
		println 'ProximityReportService execute()...'
		println ''
		println "State List........: $states"
		println "Starting place....: $startingPlace"
		println "Proximity distance: $proximityDistance"
		println "Minumum elevation.: $minimumElevation"
		println ''
		
		lineList = []
		states.each {state ->
			File dataFile = new File("out/${state}-data.txt")
			dataFile.each {line ->
				Matcher matcher = line =~ linePattern
				if (matcher) {
					LineDTO dto = new LineDTO(latDegrees: matcher[0][1], lngDegrees: matcher[0][2], elevation: matcher[0][3], population: matcher[0][4], placeName: matcher[0][5])
					lineList << dto
				}
				else {
					println "Unable to parse line: $line"
				}
			}
		}
		
		//debug
//		lineList.each {dto ->
//			println dto
//		}
		
		LineDTO startingDTO = lineList.find {dto ->
			dto.placeName == startingPlace
		}
		println "Starting DTO: $startingDTO"
		println ''
		GeoPosition startingGeoPosition = new GeoPosition(startingDTO.getDoubleLatDegrees(), startingDTO.getDoubleLngDegrees())
		println "Starting GeoPosition: ${startingGeoPosition.getLatDecimalDegrees()}, ${startingGeoPosition.getLngDecimalDegrees()}"
		println ''
		
		Proximity startingProximity = new Proximity(startingGeoPosition, proximityDistance)
		
		Set<LineDTO> highLocationSet = []
		highLocationSet << startingDTO
		
		boolean findMore = true
		while (findMore) {
			LineDTO highestDTO = highestProximity(startingProximity, highLocationSet, minimumElevation)
			if (highestDTO) {
				highLocationSet << highestDTO
				GeoPosition gp = new GeoPosition(highestDTO.getDoubleLatDegrees(), highestDTO.getDoubleLngDegrees())
				Proximity proximity = new Proximity(gp, proximityDistance)
				startingProximity = proximity
			}
			else {
				findMore = false
			}
		}

//		println 'highLocationSet...'		
//		highLocationSet.each {dto ->
//			println dto
//		}
//		println ''

		//sort by latitude
		List highLocationList = highLocationSet.sort {dto ->
			dto.doubleLatDegrees * -1
		}
		println 'highLocationList...'
		highLocationList.each {dto ->
			println dto
		}
		println ''

		
		File reportFile = new File("out/proximity-report.txt")
		PrintWriter pw = reportFile.newPrintWriter()
		pw.println 'Proximity Report'
		pw.println '----------------'
		pw.println "State List....: $states"
		pw.println "Starting place: $startingPlace"
		pw.println ''
		
		//TODO more stuff here....
		
		pw.close()
	}
	
	LineDTO highestProximity(Proximity startingProximity, Set<LineDTO> highLocationSet, int minimumElevation) {
		List<LineDTO> resultList = lineList.findAll {dto ->
			GeoPosition gp = new GeoPosition(dto.getDoubleLatDegrees(), dto.getDoubleLngDegrees())
			if (startingProximity.nearBy(gp) && !highLocationSet.contains(dto) && dto.integerElevation >= minimumElevation ) {
				return dto
			}
		}
		
		//short-circuit based on no results
		if (resultList.size() == 0) {
			return null
		}
		
		resultList.each {dto ->
			println dto
		}
		println ''
		
		LineDTO highestDTO = resultList.max {dto ->
			dto.integerElevation
		}
		println "Highest: $highestDTO"
		
		return highestDTO
	}

}
