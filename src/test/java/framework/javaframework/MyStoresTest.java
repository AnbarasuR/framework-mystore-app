package framework.javaframework;

import java.io.File;
import java.io.IOException;
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
	 * Description: Fill registration form
	 * 
	 *****************************************************************/

	@Test(dependsOnMethods = { "fnCreateNewAccoount" }, dataProvider = "getregisterUserValues")

	public void fnRegisterNewUser(String fn, String ln, String pwd, int index, String month, String year)
			throws Exception {
		// Thread.sleep(3000);
		waitForElementToDisplay(driver, MyStoresPage.genderMr);
		takeScreenPrint(driver, folderPath);
		driver.findElement(MyStoresPage.genderMr).click();
		EnterText(driver, MyStoresPage.pInfoFirstName, fn);
		EnterText(driver, MyStoresPage.pInfoLasttName, ln);
		String email = driver.findElement(MyStoresPage.pInfoEmail).getText();
		EnterText(driver, MyStoresPage.pInfoPassword, pwd);
		SelectDropdwonByIndex(driver, MyStoresPage.pInfoBirthDate, index);
		SelectDropdwonByValue(driver, MyStoresPage.pInfoBirthYear, year);
		SelectDropdwonByText(driver, MyStoresPage.pInfoBirthMonth, month);
		takeScreenPrint(driver, folderPath);
	}

	@DataProvider
	public Object[][] validUserID() {
		Object[][] data = new Object[1][1];
		data[0][0] = "aramacha@test.com";
		return data;

	}

	@DataProvider
	public Object[][] getregisterUserValues() {
		Object[][] regData = new Object[1][6];
		regData[0][0] = "FIRSTTES";
		regData[0][1] = "LASTTEST";
		regData[0][2] = "Tester12!";
		regData[0][3] = 20;
		regData[0][4] = "May";
		regData[0][5] = "1993";
		return regData;
	}

	@AfterClass
	public void closeBrowsers() {
		driver.quit();
	}

}
