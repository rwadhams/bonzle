package com.wadhams.bonzle.service

class BonzleHelperService {

	String extractBody(reader) {
		StringBuffer sb = new StringBuffer()
		
		BufferedReader br = new BufferedReader(reader)
		String str
		boolean keep = false
		while((str = br.readLine())!= null) {
			if (str.contains('<body>')) {
				keep = true
			}
			if (keep) {
				sb.append(str)
			}
			if (str.contains('</body>')) {
				break	//exit loop
			}
		}
				
		return sb.toString()
	}
	
	String extractBodyWithOnLoad(reader) {
		StringBuffer sb = new StringBuffer()
		
		BufferedReader br = new BufferedReader(reader)
		String str
		boolean keep = false
		while((str = br.readLine())!= null) {
			if (str.contains('<body onload="iniPanel()">')) {
				keep = true
			}
			if (keep) {
				sb.append(str)
			}
			if (str.contains('</body>')) {
				break	//exit loop
			}
		}
				
		return sb.toString()
	}
}
