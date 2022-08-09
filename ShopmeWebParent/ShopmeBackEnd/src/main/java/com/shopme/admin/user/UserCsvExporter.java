package com.shopme.admin.user;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.shopme.common.entities.User;

public class UserCsvExporter extends AbstractExporter{

	public void export(List<User> listUsers, HttpServletResponse response) throws IOException {
		super.setResponseHeader(response, "text/csv",  ".csv");
		
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		
		String[] csvHeader = {"User ID", "E-mail", "First Name", "Last Name", "Enabled"};
		String[] fieldMapping = {"id", "email", "firstName", "lastName", "enabled"};
		
		csvWriter.writeHeader(csvHeader);
		for(User user : listUsers) {
			csvWriter.write(user, fieldMapping);
		}
		csvWriter.close();
	
	}
}
