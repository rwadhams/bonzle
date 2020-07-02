package com.wadhams.bonzle.app

import com.wadhams.bonzle.service.BonzleFAQReportService

class BonzleFAQReportApp {
	static main(args) {
		println 'BonzleFAQReportApp started...'
		println ''

		BonzleFAQReportService service = new BonzleFAQReportService()
		
		def states = ['NSW']	//'NSW', 'VIC', 'QLD', 'SA', 'WA', 'TAS'
		service.execute(states)
				
		println ''
		println 'BonzleFAQReportApp ended.'
	}
}
