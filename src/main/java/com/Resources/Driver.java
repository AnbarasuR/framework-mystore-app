package com.Resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Driver {

	public WebDriver driver;
	String browserName = null;
	String userDirectory = null;
	String abspath = null;
	public static Logger log = LogManager.getLogger(Driver.class);;
	Properties prop;
	Properties property;

	public String readProperties() throws IOException {
		userDirectory = System.getProperty("user.dir");
		File prepretyPath = new File(userDirectory + "\\SupportingResources\\fileProperties");
		FileInputStream fis = new FileInputStream(prepretyPath);
		property = new Properties();
		property.load(fis);
		browserName = property.getProperty("browser");

//		File logfile = new File(abspath + "\\log.txt");
//		boolean result;
//		result = logfile.createNewFile();
//		if (result == true) {
//			System.out.println("File has been generated");
//		}
//		prop = new Properties();
//		FileReader rd = new FileReader(userDirectory + "\\SupportingResources\\log4j.properties");
//		prop.load(rd);
//		prop.put("log4j.appender.file.File", abspath + "\\log");
//		FileOutputStream fop = new FileOutputStream(userDirectory + "\\SupportingResources\\log4j.properties");
//		prop.store(fop, "");

		return browserName;
	}

	public WebDriver LaunchDriver() throws Exception {

		String browserNames = readProperties();

		try {
			switch (browserNames) {
			case "chromeheadless":
				System.setProperty("webdriver.chrome.driver",
						userDirectory + "\\SupportFiles\\Webdrivers\\chromedriver.exe");
				ChromeOptions options = new ChromeOptions();
				options.addArguments("headless");
				driver = new ChromeDriver(options);
				log.info("headless chrome driver has been initiated");


				break;

			case "chrome":
				System.setProperty("webdriver.chrome.driver",
						userDirectory + "\\SupportFiles\\Webdrivers\\chromedriver.exe");
				driver = new ChromeDriver();
				log.info("Chrome driver has been initiated");
				break;

			case "firefox":
				System.setProperty("webdriver.chrome.driver",
						userDirectory + "\\SupportFiles\\Webdrivers\\geckodriver.exe");
				driver = new ChromeDriver();
				log.info("Gecko driver has been initiated");

				break;

			case "ie":
				System.setProperty("webdriver.chrome.driver",
						userDirectory + "\\SupportFiles\\Webdrivers\\IEDriverServer.exe");
				driver = new ChromeDriver();
				log.info("InterNet Explorer driver has been initiated");

				break;

			case "edge":
				System.setProperty("webdriver.chrome.driver",
						userDirectory + "\\SupportFiles\\Webdrivers\\msedgedriver.exe");
				driver = new ChromeDriver();
				log.info("Edge driver has been initiated");

				break;

			default:

				System.out.println(" Invalid parametter has been provided");
				log.error("Invalid parametter has been provided in proprties files, or add new browser into CASE");

			}
		} catch (Exception e) {

			System.out.println(e);
			log.error(e);

		}
		return driver;

	}

	/******************************************************************************************************
	 * Method Name: createNewTestReultFolder() Method Description: This method will
	 * create new folder in work place when it's called
	 ******************************************************************************************************/
	public String createNewTestReultFolder() {
		String time = getCurrentDate().toUpperCase();
		String uDir = System.getProperty("user.dir");
		String strCreateDir = ("Test Result " + time);
		File f2 = new File(uDir + "\\TestResults");
		File resultfolder = new File(f2, strCreateDir);
		abspath = resultfolder.getAbsolutePath();
		if (!resultfolder.exists()) {
			boolean testFolder = resultfolder.mkdir();
			log.info(testFolder+" folder created");
		}
		log.info(abspath+" folder created");
		return abspath;
	}

	/******************************************************************************************************
	 * / * Method Name: getCurrentDate() / * Method Description: This method will
	 * get the system current date in YYYY-MM-dd_HH-mm-ss AM/PM /
	 ******************************************************************************************************/

	public String getCurrentDate() {
		DateTimeFormatter date = DateTimeFormatter.ofPattern("E YYYY-MM-dd_HH-mm-ss a");
		LocalDateTime cdate = LocalDateTime.now();
		String formatedDate = date.format(cdate);
		log.info(formatedDate+"	Captured");
		return formatedDate;

	}

	/******************************************************************************************************
	 * Method Name: takeScreenPrint() Method Description: This method will take
	 * screenshot and store in result folder
	 ******************************************************************************************************/
	public void takeScreenPrint(String des) throws Exception {
		try {
			File scr = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String dateTime = getCurrentDate();
			String dd = des + dateTime + ".png";
			File dest = new File(des + "\\" + "ScreenShot_" + dateTime + ".png");
			FileUtils.copyFile(scr, dest);
		} catch (Exception e) {
			System.out.println("error" + e);
		}
	}

	/******************************************************************************************************
	 * Method Name: waitForElementToDisplay() Method Description: This method will
	 * wait for element to display
	 ******************************************************************************************************/
	public void waitForElementToDisplay(By ele) throws Exception{
		try {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(ele));
		log.debug(ele+"Element is displayed");
		}catch(Exception e)
		{
			log.error("Element is not displayed", e);
		}
	}

	public ArrayList<Object> testdataManager(String TCID) throws IOException {
		ArrayList<Object> values = new ArrayList<Object>();
		try {

			String uDirect = System.getProperty("user.dir");
			File filePath = new File(uDirect + "\\" + "AppName" + "\\" + "TestData.xlsx");
			FileInputStream fis = new FileInputStream(filePath);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			int sheetCount = workbook.getNumberOfSheets();
			for (int i = 0; i < sheetCount; i++) {
				if (workbook.getSheetName(i).equalsIgnoreCase("UAT")) {
					XSSFSheet desiredSheet = workbook.getSheetAt(i);
					Iterator<Row> Rows = desiredSheet.iterator();

					Row frow = Rows.next();
					Iterator<Cell> rowColum = frow.iterator();
					int temp = 0;
					int testCaseColumn = 0;
					while (rowColum.hasNext()) {
						Cell fRowCells = rowColum.next();
						if (fRowCells.getStringCellValue().equalsIgnoreCase("TEST_CASE")) {
							testCaseColumn = temp;
						}
						temp++;

					}
					while (Rows.hasNext()) {
						Row tcRow = Rows.next();
						if (tcRow.getCell(testCaseColumn).getStringCellValue().equalsIgnoreCase(TCID)) {
							Iterator<Cell> cellVal = tcRow.iterator();
							while (cellVal.hasNext()) {
								Cell val = cellVal.next();
								if (val.getCellType() == CellType.STRING) {
									values.add(val.getStringCellValue());
								} else if (val.getCellType() == CellType.NUMERIC) {
									values.add(NumberToTextConverter.toText(val.getNumericCellValue()));

								}
							}
						}
					}
				}
			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
			log.error("File not founded in speciified path");
		}
		log.info(values+ "is returned");
		return values;
	}

	public void debg() {

	}

}
