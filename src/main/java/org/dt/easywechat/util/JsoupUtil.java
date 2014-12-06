package org.dt.easywechat.util;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class JsoupUtil {
	
	public static String getTagContent(Document doc, String tag) {
		if(doc != null && tag != null) {
			Elements elems = doc.getElementsByTag(tag);
			if(elems != null && !elems.isEmpty()) 
				return elems.first().html();
		}
		return null;
	}

}
