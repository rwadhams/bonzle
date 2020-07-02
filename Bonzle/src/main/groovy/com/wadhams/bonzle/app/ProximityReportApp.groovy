package com.wadhams.bonzle.app

import com.wadhams.bonzle.service.BonzleFAQReportService
import com.wadhams.bonzle.service.ProximityReportService

class ProximityReportApp {
	static main(args) {
		println 'ProximityReportApp started...'
		println ''

		ProximityReportService service = new ProximityReportService()
		
		def states = ['NSW', 'VIC']	//'NSW', 'VIC', 'QLD', 'SA', 'WA', 'TAS'
		String startingPlace = 'Tenterfield, NSW'
		//String startingPlace = 'Deepwater, NSW'
		double proximityDistance = 250d
		int minimumElevation = 500
		
		service.execute(states, startingPlace, proximityDistance, minimumElevation)
				
		println ''
		println 'ProximityReportApp ended.'
	}
}
