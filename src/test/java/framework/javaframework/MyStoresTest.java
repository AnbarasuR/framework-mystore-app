package framework.javaframework;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import resuableMethods.HelperMethods;

public class MyStoresTest extends HelperMethods {

	WebDriver driver;
	String folderPath;

	/*
	 * *****************************************************************************
	 * 
	 * Method Name: fncreateTestReultFolder
	 * Description: Create New folder and
	 * return folder path to Global variable passing path parameter to
	 * takeScreenPrint() method 
	 * Time Of Execution: Execute once
	 * 
	 **********************************************************************************/
	@BeforeSuite
	public void fncreateTestReultFolder() {

		folderPath = createNewTestReultFolder();
	}

	/*
	 * ****************************************************************
	 * 
	 * Method Name: fnLaunchBrowser 
	 * Description: Invoke Browser driver 
	 * Time Of Execution: Execute each time before Test
	 * 
	 ******************************************************************/
	@BeforeClass
	@Parameters({ "URL" })
	public void fnLaunchBrowser(String URL) {
		System.setProperty("webdriver.chrome.driver", "D:\\Softwares\\Drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(URL);

	}

	/*
	 * ****************************************************************
	 * 
	 * Method Name: fnCreateNewAccoount 
	 * Description: Create an user account
	 * 
	 *****************************************************************/
	@Test(enabled = true, dataProvider = "validUserID")
	public void fnCreateNewAccoount(String acc) throws Exception {
		driver.findElement(MyStoresPage.SignIn);
		takeScreenPrint(driver, folderPath);
		driver.findElement(MyStoresPage.SignIn).click();
		driver.findElement(MyStoresPage.CreateAccountHeader);
		EnterText(driver, MyStoresPage.EmailAddressBox, acc);
		// driver.findElement(MyStoresPage.EmailAddressBox).sendKeys(acc);
		driver.findElement(MyStoresPage.CreateAccountHeader).click();
		driver.findElement(MyStoresPage.ValidEmailIDColourCode);
		takeScreenPrint(driver, folderPath);
		driver.findElement(MyStoresPage.CreateAccButton).click();
		takeScreenPrint(driver, folderPath);
	}

	/*
	 * ****************************************************************
	 * 
	 * Method Name: fnRegisterNewUser 
	 * Description: Fill registration form [data hard coded from 
	 * 
	 *****************************************************************/
	@Test(dependsOnMethods = {"fnCreateNewAccoount"},dataProvider="getRegistrationdata")
	public void printdata( String UserID,String FirstName,String LastName,String Email,String Gender,String DBODate,String DOBMonth,String DOBYear,String Password) throws Exception
	{
		
		waitForElementToDisplay(driver, MyStoresPage.genderMr);
		takeScreenPrint(driver, folderPath);
		driver.findElement(MyStoresPage.genderMr).click();
		EnterText(driver, MyStoresPage.pInfoFirstName, FirstName);
		EnterText(driver, MyStoresPage.pInfoLasttName, LastName);
		String email = driver.findElement(MyStoresPage.pInfoEmail).getText();
		EnterText(driver, MyStoresPage.pInfoPassword, Password);
		SelectDropdwonByIndex(driver, MyStoresPage.pInfoBirthDate, Integer.parseInt(DBODate));
		SelectDropdwonByValue(driver, MyStoresPage.pInfoBirthYear, DOBYear);
		SelectDropdwonByText(driver, MyStoresPage.pInfoBirthMonth, DOBMonth);
		takeScreenPrint(driver, folderPath);
	}

	@DataProvider
	public Object[][] validUserID() {
		Object[][] data = new Object[1][1];
		data[0][0] = "aramacha@test.com";
		return data;

	}

	@DataProvider
	public Object[][] getRegistrationdata() throws Exception
	{
		String testCase ="TC01";
		ArrayList<Object> getTestData=testdataManager(testCase);
		int size = getTestData.size();
		System.out.println(size);
		Object[][] data = new Object[1][size-1];
		int k=0;
		for(int i=0; i<size; i++)
		{
			if(getTestData.get(i).equals(testCase))
			{
				System.out.println("data identified as Test case name");
			}
			else
			{
				
				data[0][k] = getTestData.get(i);
				k++;
			}
		}
		return data;
	}

	@AfterClass
	public void closeBrowsers() {
		driver.quit();
	}

}
