package com.wadhams.bonzle.app

import com.wadhams.bonzle.service.BonzleFAQExtractService
import com.wadhams.bonzle.service.BonzlePlaceService

class BonzleFAQExtractApp {
	static main(args) {
		println 'BonzleFAQExtractApp started...'
		println ''

		BonzleFAQExtractService service = new BonzleFAQExtractService()
		service.execute()
				
		println ''
		println 'BonzleFAQExtractApp ended.'
	}
}
