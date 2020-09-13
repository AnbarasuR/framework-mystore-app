package resuableMethods;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.Resources.Driver;




public class HelperMethods extends Driver{
	
	public WebDriver driver;
	
	
	public WebDriver impliciWait(WebDriver driver)
	{
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		this.driver = driver;
		return driver;
		
	}
	/******************************************************************************************************
	 * Method Name: EnterText() Method Description: This method will allow you to
	 * enter text into text box
	 ******************************************************************************************************/
	public String EnterText( By by, String acc) {
		
		driver.findElement(by).sendKeys(acc);
		return acc;
	}



	/******************************************************************************************************
	 * Method Name: SelectDropdwonByValue() Method Description: This method will
	 * allow you to select any values from static drop-down
	 ******************************************************************************************************/
	public void SelectDropdwonByValue( By ele, String value) {
		Select sd = new Select(driver.findElement(ele));
		sd.selectByValue(value);
	}

	/******************************************************************************************************
	 * Method Name: SelectDropdwonByIndex() Method Description: This method will
	 * allow you to select any values from static drop-down by index
	 ******************************************************************************************************/
	public void SelectDropdwonByIndex( By ele, int num) {

		Select sd = new Select(driver.findElement(ele));
		sd.selectByIndex(num);
	}

	/******************************************************************************************************
	 * Method Name: SelectDropdwonByText() 
	 * Method Description: This method will
	 * allow you to select any values from static drop-down by Text
	 ******************************************************************************************************/

	public void SelectDropdwonByText( By ele, String Text) {

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

	

}
