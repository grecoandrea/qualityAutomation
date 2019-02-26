package utilities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

public class Randomizer {

	Random number = new Random();

	public Randomizer() {
		
	}

	protected String randomString() {
		String generatedString = RandomStringUtils.randomAlphabetic(6);
		return generatedString;
	}

	protected String randomNumber(int Low, int High) {
		int Result = number.nextInt(High-Low) + Low;
		String str=Integer.toString(Result);
		return str;
	}

	protected String randomValues() {
		String addresses[];
		addresses = new String[] {
				"panettone milanese",
				"torta paradiso",
				"focaccia genovese",
				"pastiera napoletana"
		};


		int random = number.nextInt(addresses.length);;
		return addresses[random];

	}

	protected String randomBirthdate() {
		int RandomDateOffset = number.nextInt(10000-1) + 1;
		LocalDate localDate = LocalDate.ofEpochDay(RandomDateOffset);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		return dtf.format(localDate);

	}
	
	public String getRandomSearch() {
		return randomValues();
	}
	
	public String getRandomBirthdate() {
		return randomBirthdate();
	}
	
	public String getRandomString() {
		return randomString();
	}
	
	
}
