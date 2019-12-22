package com.wadhams.bonzle.app

import com.wadhams.bonzle.service.BonzleFAQReportService

class BonzleFAQReportApp {
	static main(args) {
		println 'BonzleFAQReportApp started...'
		println ''

		BonzleFAQReportService service = new BonzleFAQReportService()
		service.execute()
				
		println ''
		println 'BonzleFAQReportApp ended.'
	}
}
