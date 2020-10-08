package com.wadhams.bonzle.app

import groovyx.net.http.RESTClient
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType
import com.wadhams.bonzle.service.BonzleEveryFAQService
import groovy.xml.XmlUtil

class BonzleEveryFAQSplitterApp {
	static main(args) {
		println 'BonzleEveryFAQSplitterApp started...'
		println ''

		File qldFile = new File("out/every-faq-qld.txt")
		PrintWriter pwQLD = qldFile.newPrintWriter()
		
		File nswFile = new File("out/every-faq-nsw.txt")
		PrintWriter pwNSW = nswFile.newPrintWriter()
		
		File actFile = new File("out/every-faq-act.txt")
		PrintWriter pwACT = actFile.newPrintWriter()
		
		File vicFile = new File("out/every-faq-vic.txt")
		PrintWriter pwVIC = vicFile.newPrintWriter()
		
		File tasFile = new File("out/every-faq-tas.txt")
		PrintWriter pwTAS = tasFile.newPrintWriter()
		
		File saFile = new File("out/every-faq-sa.txt")
		PrintWriter pwSA = saFile.newPrintWriter()
		
		File ntFile = new File("out/every-faq-nt.txt")
		PrintWriter pwNT = ntFile.newPrintWriter()
		
		File waFile = new File("out/every-faq-wa.txt")
		PrintWriter pwWA = waFile.newPrintWriter()
		
		//input file loop
		File faqFile = new File("out/every-faq-01.txt")
		faqFile.eachLine {line ->
			if (line.endsWith('QLD')) {
				pwQLD.println line
			}
			else if (line.endsWith('NSW')) {
				pwNSW.println line
			}
			else if (line.endsWith('ACT')) {
				pwACT.println line
			}
			else if (line.endsWith('VIC')) {
				pwVIC.println line
			}
			else if (line.endsWith('TAS')) {
				pwTAS.println line
			}
			else if (line.endsWith('SA')) {
				pwSA.println line
			}
			else if (line.endsWith('NT')) {
				pwNT.println line
			}
			else if (line.endsWith('WA')) {
				pwWA.println line
			}
			else {
				println line
			}
		}
		
		pwQLD.close()
		pwNSW.close()
		pwACT.close()
		pwVIC.close()
		pwTAS.close()
		pwSA.close()
		pwNT.close()
		pwWA.close()
		
		println ''
		println 'BonzleEveryFAQSplitterApp ended.'
	}
	
}
