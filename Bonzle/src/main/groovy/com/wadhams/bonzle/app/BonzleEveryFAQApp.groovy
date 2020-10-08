package com.wadhams.bonzle.app

import groovyx.net.http.RESTClient
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType
import com.wadhams.bonzle.service.BonzleEveryFAQService
import groovy.xml.XmlUtil

class BonzleEveryFAQApp {
	static main(args) {
		println 'BonzleEveryFAQApp started...'
		println ''

		BonzleEveryFAQService service = new BonzleEveryFAQService()
		//The first starting place is 2 (p=2)
		//service.execute(2, 99)
		//service.execute(100, 499)
		//service.execute(500, 1499)
		//service.execute(1500, 4999)
		//service.execute(5000, 9999)
		//service.execute(10000, 19999)
		//service.execute(20000, 49999)
		//service.execute(50000, 99999)
		//service.execute(100000, 199999)
		//288876
		//service.execute(288000, 288999)
		//service.execute(289000, 299999)
		//service.execute(300000, 301999)
		service.execute(302000, 319999)
		//313409
		
		println ''
		println 'BonzleEveryFAQApp ended.'
	}
	
}
