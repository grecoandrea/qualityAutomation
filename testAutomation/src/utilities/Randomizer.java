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

	protected String randomAddress() {
		String addresses[];
		addresses = new String[] {
				"Via Aurelia 1",
				"Via Cassia 100",
				"Via Flaminia 1",
				"Via Salaria 100",
				"Via Tiburtina 1",
				"Via Casilina 100",
				"Via Appia 1",
				"Via Nomentana 100",
				"Via Prenestina 1",
				"Via Anagnina 100",
				"Via Ardeatina 1",
				"Via Laurentina 100",
				"Via Tuscolana 1",
				"Via Portuense 100",
				"Via Trionfale 1",
				"Via Cornelia 100"
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
	
	protected String randomPhoneNumber() {
		int Low = 1000000000;
		int High = 1999999999;
		return randomNumber(Low, High);
	}
	
	protected String randomResultInTable() {
		int Low = 1;
		int High = 19;
		return randomNumber(Low, High);
	}
	
	protected String randomMonth() {
		int Low = 1;
		int High = 9;
		return randomNumber(Low, High);
	}
	
	protected String randomDocument() {
		int Low = 100;
		int High = 10000;
		return "DOC-"+randomNumber(Low, High);
	}

	protected String randomTax() {
		int Low = 100;
		int High = 10000;
		return "TAX-"+randomNumber(Low, High);
	}
	
	public String getRandomName() {
		return "SEL-"+randomString();
	}

	
	public String getRandomSurname() {
		return "SEL-"+randomString();
	}

	public String getRandomDocument() {
		return randomDocument();
	}
	
	public String getRandomPhoneNumber() {
		return randomPhoneNumber();
	}

	public String getRandomTax() {
		return randomTax();
	}
	
	public String getRandomAddress() {
		return randomAddress();
	}

	
	public String getRandomBirthdate() {
		return randomBirthdate();
	}
	
	public String getRandomString() {
		return randomString();
	}

	public String getRandomResultInTable() {
		return randomResultInTable();
	}

	public String getRandomMonth() {
		return randomMonth();
	}
	
}
