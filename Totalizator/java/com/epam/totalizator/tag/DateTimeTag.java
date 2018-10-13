package com.epam.totalizator.tag;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Class of custom tag, that show date and time with pattern.
 */
public class DateTimeTag extends TagSupport {
	
	private static final long serialVersionUID = 1L;
	
	private String timePattern;
	private String datePattern;
	private Timestamp value;
	
	public void setTimePattern(String timePattern) {
		this.timePattern = timePattern;
	}

	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}

	public void setValue(Timestamp value) {
		this.value = value;
	}

	@Override
	public int doStartTag() throws JspException{
		try {
			String to = "";
			long sec = value.getTime();
			Date date = new Date(sec);
			Time time = new Time(sec);
			SimpleDateFormat format = new SimpleDateFormat(datePattern);
			to += format.format(date);
			format.applyPattern(timePattern);
			to += "<br>";
			to += format.format(time);
			pageContext.getOut().write(to);
		}catch(IOException e) {
			throw new JspException(e.getMessage());
		}
		return SKIP_BODY;
	}
}
