package br.com.caelum.vraptor.filegen;

import java.util.HashMap;
import java.util.Map;


public class CodeFormatter {

	Map<String, String> converters = new HashMap<String, String>();
	
	public void addConverter(String key, String value) {
		converters.put(key, value);
	}

	public String generateSourceFromTemplate(String content) {

		StringBuffer code = new StringBuffer(content);
		
		for (Map.Entry<String, String> converter : converters.entrySet()) {
			replace(code, converter.getKey(), converter.getValue());
		}
		
		return code.toString();
	}
	
    public static void replace(StringBuffer code, String marker, String newCode) {
        int posMarker = code.indexOf(marker);
        while (posMarker >= 0) {
        	code.replace(posMarker, posMarker + marker.length(), newCode);
        	posMarker = code.indexOf(marker);
        }
    }

	public String updateSourceBefore(String text, String content, String new_content) {
		int pos = content.lastIndexOf(text);
		return content.substring(0, pos) + new_content + content.substring(pos, content.length());
	}
	
	
}
