package com.cami.view.resolver;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import com.cami.view.Excelview;

public class ExcelViewResolver implements ViewResolver {
	
	@Override
	public View resolveViewName(final String view, final Locale locale)
			throws Exception
	{
		return new Excelview();
	}

}
