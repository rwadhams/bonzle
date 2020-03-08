package com.wadhams.bonzle.service

import groovyx.net.http.RESTClient
import java.text.NumberFormat
import java.util.regex.Pattern
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType
import groovy.xml.XmlUtil
import java.math.RoundingMode

class BonzleFAQReportService {
//	BonzleHelperService helper = new BonzleHelperService()
	
	Pattern elevationPattern = ~/(\d?,?[\d\.]{1,3}) m above sea level/
	Pattern placePattern = ~/<h1.*>Frequently asked questions about (.*)<\/h1>/
	Pattern latLongPattern = ~/latitude of (-\d{1,2}\.\d{1,5}) decimal degrees and a longitude of (\d{3}\.\d{1,5}) decimal degrees/
	Pattern populationPattern = ~/population of about [\w]{5,6} ([\d]{1,3},?[\d]{1,3},?[\d]{1,3}) \(based on the 2001 census\)/
	
	def execute(states) {
		println 'BonzleFAQReportService execute()...'
		println ''
		
		states.each {state ->
			//File dataFile = new File("out/${state}-data-small.txt")
			File dataFile = new File("out/${state}-data.txt")
			dataFile.withPrintWriter {pw ->
				//File faqFile = new File("out/${state}-faq-small.txt")
				File faqFile = new File("out/${state}-faq.txt")
				faqFile.each {line ->
					//println line
					StringBuilder sb = new StringBuilder()
					
					def latLongMatcher = line =~ latLongPattern
					if (latLongMatcher) {
					//	println latLongMatcher[0]
						String lat = latLongMatcher[0][1]
						String lng = latLongMatcher[0][2]
						//println "Lat..........: $lat"
						//println "Long.........: $lng"
						sb.append(lat.padRight(9, '0'))
						sb.append('  ')
						sb.append(lng.padRight(9, '0'))
						sb.append('  ')
					}
					else {
						println 'Lat/Long not found!'
					}
					
					def elevationMatcher = line =~ elevationPattern
					if (elevationMatcher) {
					//	println elevationMatcher[0]
						String e1 = elevationMatcher[0][1]
						BigDecimal e2 = new BigDecimal(e1)
						int e3 = e2.intValue()
						NumberFormat nf = NumberFormat.integerInstance
						nf.setMinimumIntegerDigits(4)
						nf.setGroupingUsed(false)
						String e4 = nf.format(e3)
						//println "Elevation....: $e1\t$e2\t$e3\t$e4"
						sb.append(e4)
						sb.append('  ')
					}
					else {
						println 'Elevation not found!'
					}
				
					def populationMatcher = line =~ populationPattern
					if (populationMatcher) {
					//	println populationMatcher[0]
						String p1 = populationMatcher[0][1]
						String p2 = p1.replace(',', '')
						BigDecimal p3 = new BigDecimal(p2)
						int p4 = p3.intValue()
						NumberFormat nf = NumberFormat.integerInstance
						nf.setMinimumIntegerDigits(7)
						nf.setGroupingUsed(false)
						String p5 = nf.format(p4)
						//println "Population...: $p1\t$p2\t$p3\t$p4\t$p5"
						sb.append(p5)
						sb.append('  ')
					}
					else {
						println 'Population not found!'
					}
	
					def placeMatcher = line =~ placePattern
					if (placeMatcher) {
					//	println placeMatcher[0]
						String place = placeMatcher[0][1]
						println "Place name...: $place"
						sb.append(place)
					}
					else {
						println 'Place not found!'
					}
					pw.println sb.toString()
				}
			}
		}
	}
	
}
