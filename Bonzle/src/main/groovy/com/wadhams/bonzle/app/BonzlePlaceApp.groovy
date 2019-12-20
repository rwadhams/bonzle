package com.wadhams.bonzle.app

import com.wadhams.bonzle.service.BonzlePlaceService

class BonzlePlaceApp {
	static main(args) {
		println 'BonzlePlaceApp started...'
		println ''

		BonzlePlaceService service = new BonzlePlaceService()
		service.execute()
				
		println ''
		println 'BonzlePlaceApp ended.'
	}
}
