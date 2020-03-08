README
======

BonzleApp
---------
This App is more of a test HTTP GET against Bonzle. Specifically, it retrieves the FAQ page for Tenterfield, NSW.


Bonzle Data Mining
------------------
Sequence:
1.	BonzlePlaceApp		(provide list of states)
2.	BonzleFAQExtractApp	(provide list of states)


BonzlePlaceApp
	Input: list of states to process
	Delegates to BonzlePlaceService

BonzlePlaceService
	Outputs:	<state>-places.txt		e.g. SA-places.txt
		Format:	numeric-place-value | place-name | <state>
			1463|Adelaide|SA
			1551|Aldinga%20Beach|SA



BonzleFAQExtractApp
	Input: list of states to process
	Delegates to BonzleFAQExtractService

BonzleFAQExtractService
	Process:	Based on state, reads each place from the <state>-places.txt file.
				Queries Bonzle for this place's FAQ page, extracts the <body>.
	Outputs:	<state>-faq.txt		e.g. SA-faq.txt
		Format:	one line per place, <body onload="iniPanel()"><table...
	
	After Completion:	Edit each <state>-faq.txt file. Replace "million" with numeric value.


Bonzle Data Reporting
---------------------
BonzleFAQReportApp
	Input: list of states to process
	Delegates to BonzleFAQReportService

BonzleFAQReportService
	Process:	Based on state, reads each faq line from the <state>-faq.txt file.
				Extracts data values for reporting each place.
	Outputs:	<state>-report.txt		e.g. SA-report.txt
		Format:	lat lng elevation population place-name
			-34.92866  138.59863  0061  1000000  Adelaide, SA
			-35.27826  138.45802  0024  0005500  Aldinga Beach, SA


Manual Report Editing
---------------------
Open <state>-report.txt in TextPad, edit and save with new report name.

1.	Above 500m, sorted North to South
	Filename: SA-above500m-north-to-south.txt
	Process:	sort by elevation, desc
				delete all under 500
				sort by lat, asc
				save

