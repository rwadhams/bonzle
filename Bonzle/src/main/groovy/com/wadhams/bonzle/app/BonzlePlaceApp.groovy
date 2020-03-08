package com.wadhams.bonzle.app

import com.wadhams.bonzle.service.BonzlePlaceService

class BonzlePlaceApp {
	static main(args) {
		println 'BonzlePlaceApp started...'
		println ''

		BonzlePlaceService service = new BonzlePlaceService()
		
		def states = ['SA']	//'NSW', 'VIC', 'QLD', 'SA', 'WA', 'TAS'
		service.execute(states)
				
		println ''
		println 'BonzlePlaceApp ended.'
	}
}
