package com.wadhams.bonzle.service

import groovyx.net.http.RESTClient
import java.util.regex.Pattern
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType
import groovy.xml.XmlUtil

class BonzlePlaceService {
	BonzleHelperService helper = new BonzleHelperService()
	
	Pattern letterPattern = ~/<a href="\/c\/a\?a=br&amp;o=91772837&amp;l=([A-Z])&amp;.*?<\/a>/
	
	def execute() {
		println 'BonzlePlaceService execute()...'
		println ''

		def http = new RESTClient("http://www.bonzle.com/")
		
		def states = ['NSW']	//NSW, VIC, QLD, SA, WA, TAS
		
		states.each {state ->
			//a=br&o=91772837&l=&st=NSW
			Map qm1 = [:]
			qm1['a'] = 'br'
			qm1['o'] = '91772837'
			qm1['l'] = ''
			qm1['st'] = state
	
			String body1
			http.get(path:'c/a', query:qm1, contentType:ContentType.TEXT) { resp, reader ->
				println "response status: ${resp.statusLine}"
				body1 = helper.extractBody(reader)
			}
			println body1
			
			def m1 = body1 =~ letterPattern
			def letters = m1.collect {m ->
				m[1]
			}
			println letters
			
			Pattern placePattern = ~/<a href="\/c\/a\?a=p&amp;p=([\d]{1,5}).*?place=(.*?)&amp;.*?<\/a>, ${state}<br>/

			File f = new File("out/${state}-places.txt")
			f.withPrintWriter {pw ->
				letters.each {letter ->
					println letter
					//http://www.bonzle.com/c/a?a=br&o=91772837&l=A&st=NSW
					Map qm2 = [:]
					qm2['a'] = 'br'
					qm2['o'] = '91772837'
					qm2['l'] = letter
					qm2['st'] = state
			
					String body2
					http.get(path:'c/a', query:qm2, contentType:ContentType.TEXT) { resp, reader ->
						println "response status: ${resp.statusLine}"
						body2 = helper.extractBody(reader)
					}
					println body2
					def m2 = body2 =~ placePattern
					m2.each {m ->
						println m
						pw.println "${m[1]}|${m[2]}|${state}"
					}
					sleep(500)		//DOS lock-out protection
				}
			}
			
		}
	}
	
}
