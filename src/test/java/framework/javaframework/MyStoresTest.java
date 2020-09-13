package framework.javaframework;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import resuableMethods.HelperMethods;

public class MyStoresTest extends HelperMethods {

	public WebDriver driver;
	
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
	public void fnLaunchBrowser(String URL) throws Exception {
		driver = LaunchDriver();
		driver.get(URL);
		log.info(URL +" launched");
		driver = impliciWait(driver);
		

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
		takeScreenPrint( folderPath);
		driver.findElement(MyStoresPage.SignIn).click();
		driver.findElement(MyStoresPage.CreateAccountHeader);
		EnterText(MyStoresPage.EmailAddressBox, acc);
		// driver.findElement(MyStoresPage.EmailAddressBox).sendKeys(acc);
		driver.findElement(MyStoresPage.CreateAccountHeader).click();
		driver.findElement(MyStoresPage.ValidEmailIDColourCode);
		takeScreenPrint(folderPath);
		driver.findElement(MyStoresPage.CreateAccButton).click();
		takeScreenPrint( folderPath);
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
		
		waitForElementToDisplay( MyStoresPage.genderMr);
		takeScreenPrint( folderPath);
		driver.findElement(MyStoresPage.genderMr).click();
		EnterText( MyStoresPage.pInfoFirstName, FirstName);
		EnterText( MyStoresPage.pInfoLasttName, LastName);
		String email = driver.findElement(MyStoresPage.pInfoEmail).getText();
		EnterText( MyStoresPage.pInfoPassword, Password);
		SelectDropdwonByIndex( MyStoresPage.pInfoBirthDate, Integer.parseInt(DBODate));
		SelectDropdwonByValue( MyStoresPage.pInfoBirthYear, DOBYear);
		SelectDropdwonByText( MyStoresPage.pInfoBirthMonth, DOBMonth);
		takeScreenPrint(folderPath);
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
