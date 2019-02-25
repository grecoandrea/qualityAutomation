package utilities;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateCalculator {

	public DateCalculator()	{
	}

	protected String dateSixMonthsEarly() {
		LocalDateTime SixMonths = LocalDateTime.now().minus(6, ChronoUnit.MONTHS);
		String retrievedDate = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(SixMonths);
		return retrievedDate;
	}

	protected String dateOlderMonthsEarly(String date) {
		int day = Integer.parseInt(date.substring(0,2));
		int month = Integer.parseInt(date.substring(3,5));
		int year = Integer.parseInt(date.substring(6,10));
		LocalDateTime previousMonth = LocalDateTime.of(year, month, day, 12, 00).minus(6, ChronoUnit.MONTHS);
		String retrievedDate = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(previousMonth);
		return retrievedDate;
	}
	
	public String dateActual() {
		Date date = new Date() ;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd") ;
		return dateFormat.format(date);
	}

	public String retDateSixMonthsEarly() {
		return dateSixMonthsEarly();
	}
	
	public String retDateActual() {
		return dateActual();
	}
	
	public String retDateOlderMonthsEarly(String date) {
		return dateOlderMonthsEarly(date);
	}
}
