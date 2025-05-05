package jp.kouto.fuyuki.akiba.todo_application.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * StringUtilsを継承
 */
public class StringUtil extends StringUtils {
	
	public static Timestamp stringToTimestamp(String str, String pattern) {
		Timestamp ts = null;
		try {
			if(StringUtils.isBlank(str)) throw new IllegalArgumentException("str is null.");
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			Date date = sdf.parse(str);
			ts = new Timestamp(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ts;
	}
}
