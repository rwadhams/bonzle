package com.wadhams.bonzle.app

import groovyx.net.http.RESTClient
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType
import groovy.xml.XmlUtil

class BonzleApp {
	static main(args) {
		println 'BonzleApp started...'
		println ''

		def http = new RESTClient("http://www.bonzle.com/")
		
		//a=p&p=761&cmd=sp&st=NSW&place=Tenterfield
		Map qm = [:]
		qm['a'] = 'p'
		qm['p'] = '761'
		qm['d'] = 'faq'
		qm['cmd'] = 'sp'
		qm['st'] = 'NSW'
		qm['place'] = 'Tenterfield'

		http.get(path:'c/a', query:qm, contentType:ContentType.TEXT) { resp, reader ->
			println '' + resp.class.getName()
			println '' + reader.class.getName()
			println "response status: ${resp.statusLine}"
			println 'Response data: ----------------'
//			System.out << XmlUtil.serialize(reader.text)
//			System.out << reader
			String body = extractBody(reader)
			println body
			
			println '--------------------'
		}
				
//		def resp1 = api.get(path:'c/a', query:qm)
//		assert resp1.status == 200
//		assert resp1.contentType == 'text/html'
//		println resp1.data
		
		
		
		println ''
		println 'BonzleApp ended.'
	}
	
	static String extractBody(reader) {
		StringBuffer sb = new StringBuffer()
		
		BufferedReader br = new BufferedReader(reader)
		String str
		boolean keep = false
		while((str = br.readLine())!= null) {
			if (str.contains('<body onload="iniPanel()">')) {
				keep = true
			}
			if (keep) {
				sb.append(str)
			}
			if (str.contains('</body>')) {
				break	//exit loop
			}
		}
				
		return sb.toString()
	}
}
