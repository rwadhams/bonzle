package com.wadhams.bonzle.service

import groovyx.net.http.RESTClient
import java.text.NumberFormat
import java.util.regex.Pattern
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType
import groovy.xml.XmlUtil

class BonzleEveryFAQService {
	BonzleHelperService helper = new BonzleHelperService()
	
	Pattern elevationPattern = ~/(\d?,?[\d\.]{1,3}) m above sea level/
	Pattern placePattern = ~/<h1.*>Frequently asked questions about (.*)<\/h1>/
	Pattern latLongPattern = ~/latitude of (-\d{1,2}\.\d{1,5}) decimal degrees and a longitude of (\d{3}\.\d{1,5}) decimal degrees/
	Pattern populationPattern = ~/population of about [\w]{5,6} ([\d]{1,3},?[\d]{1,3},?[\d]{1,3}) \(based on the 2001 census\)/

	NumberFormat nf4 = NumberFormat.integerInstance
	NumberFormat nf7 = NumberFormat.integerInstance
	
	def BonzleEveryFAQService() {
		nf4.setMinimumIntegerDigits(4)
		nf4.setGroupingUsed(false)

		nf7.setMinimumIntegerDigits(7)
		nf7.setGroupingUsed(false)
	}
	
	def execute(Number start, Number end) {
		println 'BonzleEveryFAQService execute()...'
		println ''
		
		def http = new RESTClient("http://www.bonzle.com/")

		File faqFile = new File("out/every-faq.txt")
		faqFile.withWriterAppend {bw ->
			(start..end).each {n ->
				print "n: $n\t"
				
				//http://www.bonzle.com/c/a?a=p&p=709&d=faq&cmd=sp&c=1&w=40000&mpsec=0
				Map qm = [:]
				qm['a'] = 'p'
				qm['p'] = n
				qm['d'] = 'faq'
				qm['cmd'] = 'sp'
				qm['c'] = '1'
				qm['w'] = '40000'
				qm['mpsec'] = '0'
	
				try {
					String body
					http.get(path:'c/a', query:qm, contentType:ContentType.TEXT) { resp, reader ->
						print "response status: ${resp.statusLine}\t"
						body = helper.extractBodyWithOnLoad(reader)
					}
					//println body
					bw.println extractValuesFromBody(n, body)
					sleep(100)		//DOS lock-out protection
				}
				catch (Exception e) {
					e.printStackTrace()
				}
						
				println ''
			}
		}
		
		
	}
	
	String extractValuesFromBody(Number n, String line) {
		StringBuilder sb = new StringBuilder()
		
		String n1 = nf7.format(n)
		
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
			return n1
		}
		
		def elevationMatcher = line =~ elevationPattern
		if (elevationMatcher) {
		//	println elevationMatcher[0]
			String e1 = elevationMatcher[0][1]
			BigDecimal e2 = new BigDecimal(e1)
			int e3 = e2.intValue()
			String e4 = nf4.format(e3)
			//println "Elevation....: $e1\t$e2\t$e3\t$e4"
			sb.append(e4)
			sb.append('  ')
		}
		else {
			println 'Elevation not found!'
			return n1
		}
	
		def populationMatcher = line =~ populationPattern
		if (populationMatcher) {
		//	println populationMatcher[0]
			String p1 = populationMatcher[0][1]
			String p2 = p1.replace(',', '')
			BigDecimal p3 = new BigDecimal(p2)
			int p4 = p3.intValue()
			String p5 = nf7.format(p4)
			//println "Population...: $p1\t$p2\t$p3\t$p4\t$p5"
			sb.append(p5)
			sb.append('  ')
		}
		else {
			println 'Population not found!'
			sb.append('0000000  ')
		}

		sb.append(n1)
		sb.append('  ')

		def placeMatcher = line =~ placePattern
		if (placeMatcher) {
		//	println placeMatcher[0]
			String place = placeMatcher[0][1]
			println "Place name...: $place"
			sb.append(place)
		}
		else {
			println 'Place not found!'
			return n1
		}
		
		return sb.toString()
	}
}
