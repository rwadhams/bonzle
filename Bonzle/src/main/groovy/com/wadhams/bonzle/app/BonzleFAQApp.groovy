package com.wadhams.bonzle.app

import com.wadhams.bonzle.service.BonzleFAQService
import com.wadhams.bonzle.service.BonzlePlaceService

class BonzleFAQApp {
	static main(args) {
		println 'BonzleFAQApp started...'
		println ''

		BonzleFAQService service = new BonzleFAQService()
		service.execute()
				
		println ''
		println 'BonzleFAQApp ended.'
	}
}
