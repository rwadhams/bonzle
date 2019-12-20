package com.wadhams.bonzle.service

import groovyx.net.http.RESTClient
import java.util.regex.Pattern
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType
import groovy.xml.XmlUtil

class BonzleFAQService {
	BonzleHelperService helper = new BonzleHelperService()
	
//	Pattern letterPattern = ~/<a href="\/c\/a\?a=br&amp;o=91772837&amp;l=([A-Z])&amp;.*?<\/a>/
	
	def execute() {
		println 'BonzleFAQService execute()...'
		println ''
		
		def http = new RESTClient("http://www.bonzle.com/")
		
		def states = ['NSW']	//NSW, VIC, QLD, SA, WA, TAS
		
		states.each {state ->
			File faqFile = new File("out/${state}-faq.txt")
			faqFile.withPrintWriter {pw ->
				File placeFile = new File("out/${state}-places.txt")
				placeFile.each {line ->
					println line
					def sa = line.split(/\|/)
					assert sa.size() == 3
					//TODO FAQ query and write each body to file
					//http://www.bonzle.com/c/a?a=p&p=618&d=faq&cmd=sp&c=1
					Map qm = [:]
					qm['a'] = 'p'
					qm['p'] = sa[0]
					qm['d'] = 'faq'
					qm['cmd'] = 'sp'
			
					String body
					http.get(path:'c/a', query:qm, contentType:ContentType.TEXT) { resp, reader ->
						println "response status: ${resp.statusLine}"
						body = helper.extractBodyWithOnLoad(reader)
					}
					pw.println body
					sleep(500)		//DOS lock-out protection
				}
			}
		}
	}
	
}
