package resuableMethods;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByClassName;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import framework.javaframework.MyStoresPage;


public class HelperMethods {

	/******************************************************************************************************
	 * Method Name: EnterText() Method Description: This method will allow you to
	 * enter text into text box
	 ******************************************************************************************************/
	public String EnterText(WebDriver driver, By by, String acc) {
		driver.findElement(by).sendKeys(acc);
		return acc;
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
		String abspath = resultfolder.getAbsolutePath();
		if (!resultfolder.exists()) {
			boolean testFolder = resultfolder.mkdir();
		}
		return abspath;
	}

	/******************************************************************************************************
	 * Method Name: takeScreenPrint() Method Description: This method will take
	 * screenshot and store in result folder
	 ******************************************************************************************************/
	public void takeScreenPrint(WebDriver driver, String des) throws Exception {
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
	 * Method Name: SelectDropdwonByValue() Method Description: This method will
	 * allow you to select any values from static drop-down
	 ******************************************************************************************************/
	public void SelectDropdwonByValue(WebDriver driver, By ele, String value) {
		Select sd = new Select(driver.findElement(ele));
		sd.selectByValue(value);
	}

	/******************************************************************************************************
	 * Method Name: SelectDropdwonByIndex() Method Description: This method will
	 * allow you to select any values from static drop-down by index
	 ******************************************************************************************************/
	public void SelectDropdwonByIndex(WebDriver driver, By ele, int num) {

		Select sd = new Select(driver.findElement(ele));
		sd.selectByIndex(num);
	}

	/******************************************************************************************************
	 * Method Name: SelectDropdwonByText() 
	 * Method Description: This method will
	 * allow you to select any values from static drop-down by Text
	 ******************************************************************************************************/

	public void SelectDropdwonByText(WebDriver driver, By ele, String Text) {

		Select sd = new Select(driver.findElement(ele));
		String str = null;
		/*
		 * if non breaking space or special character is starts with ends with Value of
		 * Text get the each options from the drop down by index if index of values
		 * contain text then loop will break
		 */
		for (int i = 1; i <= sd.getOptions().size(); i++) {
			str = sd.getOptions().get(i).getText();
			if (str.contains(Text)) {
				break;
			}
		}
		sd.selectByVisibleText(str);
	}

	/******************************************************************************************************
	/ * Method Name: getCurrentDate() 
	/ * Method Description: This method will get the system current date in YYYY-MM-dd_HH-mm-ss AM/PM
	/ ******************************************************************************************************/

	public String getCurrentDate() {
		DateTimeFormatter date = DateTimeFormatter.ofPattern("E YYYY-MM-dd_HH-mm-ss a");
		LocalDateTime cdate = LocalDateTime.now();
		String formatedDate = date.format(cdate);
		return formatedDate;

	}
	
	/******************************************************************************************************
	 * Method Name: waitForElementToDisplay() 
	 * Method Description: This method will wait for element to display
	 ******************************************************************************************************/
	public void waitForElementToDisplay(WebDriver driver, By ele)
	{
		WebDriverWait wait = new WebDriverWait (driver,20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(ele));
	}
	
	public   ArrayList<Object> testdataManager(String TCID) throws IOException {
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
					int temp=0;
					int testCaseColumn=0;
					while (rowColum.hasNext()) {
						Cell fRowCells = rowColum.next();
						if(fRowCells.getStringCellValue().equalsIgnoreCase("TEST_CASE"))
						{
							testCaseColumn=temp;	
						}
						temp++;
						
					}
					while(Rows.hasNext())
					{
						Row tcRow = Rows.next();
						if(tcRow.getCell(testCaseColumn).getStringCellValue().equalsIgnoreCase(TCID))
						{
							Iterator<Cell> cellVal = tcRow.iterator();
							while(cellVal.hasNext())
							{
								Cell val =cellVal.next();
								if(val.getCellType()==CellType.STRING)
								{
									values.add(val.getStringCellValue());
								}
								else if(val.getCellType()==CellType.NUMERIC)
								{
									values.add(NumberToTextConverter.toText(val.getNumericCellValue()));
									
								}
							}
						}
					}
				}
			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		return values;

	}
	
//	public String[] getTestData(String TestCaseName) throws IOException
//	{
//		int _testDataCount=0;
//		ArrayList<String> obj = testdataManager(TestCaseName);
//		
//		_testDataCount = obj.size();
//		String[] data = new String[_testDataCount] ;
//		try {			
//			for(int i=1; i<_testDataCount; i++)
//			{
//			data[i] = obj.get(i);
//			}
//			//return data;		
//		}
//		catch(Exception e)
//		{
//			System.out.println(e);
//		}
//		return data;
//		
//	}
//	

}
