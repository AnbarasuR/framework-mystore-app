package framework.javaframework;

import org.openqa.selenium.By;

public class MyStoresPage {

	public static By logo = By.cssSelector("div[id='header_logo']");
	public static By SignIn = By.xpath("//a[@class='login']");

	// Create Account
	public static By CreateAccountHeader = By.xpath("//form[@id='create-account_form']/h3[text()='Create an account']");
	public static By EmailAddressBox = By.id("email_create");
	public static By ValidEmailIDColourCode = By.cssSelector("div[class='form-group form-ok']");
	public static By CreateAccButton = By.xpath("//button[@id='SubmitCreate']");

	public static By genderMr = By.cssSelector("input[id='id_gender1']");
	public static By genderMrs = By.cssSelector("input[id='id_gender2']");
	public static By pInfoFirstName = By.cssSelector("input[id='customer_firstname']");
	public static By pInfoLasttName = By.cssSelector("input[id='customer_lastname']");
	public static By pInfoEmail = By.cssSelector("input[id='email']");
	public static By pInfoPassword = By.cssSelector("input[name='passwd']");
	public static By pInfoBirthDate = By.cssSelector("select[id='days']");
	public static By pInfoBirthMonth = By.cssSelector("select[id='months']");
	public static By pInfoBirthYear = By.cssSelector("select[id='years']");

	public static By addressInfoFirstName = By.cssSelector("input[id='firstname']");
	public static By addressInfoLasttName = By.cssSelector("input[id='lastname']");
	public static By addressInfoMail = By.cssSelector("input[id='address1']");
	public static By addressInfoCompanyName = By.cssSelector("input[id='company']");
	public static By addressInfoMailInline = By
			.xpath("//span[@class='inline-infos' and text()='Street address, P.O. Box, Company name, etc.']");
	public static By addressInfoCity = By.cssSelector("input[id='city']");
	public static By addressInfoState = By.cssSelector("input[id='id_state']");
	public static By addressInfoPostCode = By.cssSelector("input[id='postcode']");
	public static By addressInfoCountry = By.cssSelector("input[id='id_country']");
	public static By addressInfoAdditionTxtBox = By.cssSelector("textarea[id='other']");
	public static By addressInfoAdditionInfoInline = By.xpath(
			"//p[@class='textarea form-group']/following-sibling::p[text()='You must register at least one phone number.']");
	public static By addressInfoHomePh = By.cssSelector("inpuy[id='phone']");
	public static By addressInfoMobPh = By.cssSelector("input[id='phone_mobile']");
	public static By addressInfoAlias = By.cssSelector("input[id='alias']");
	public static By registerButton = By.cssSelector("button[id='submitAccount']");
}
