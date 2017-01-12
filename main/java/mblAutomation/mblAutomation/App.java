package mblAutomation.mblAutomation;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import io.appium.java_client.android.AndroidDriver;

public class App {
	private static AndroidDriver driver;
	@BeforeSuite
	public void appiumSetUp() {
		try {
			File classpathRoot = new File(System.getProperty("user.dir"));
			File appDir = new File(classpathRoot, "/Apps/");
			File app = new File(appDir, "HDFC_Bank_5.3.apk");

			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
			capabilities.setCapability("deviceName", "Redmi 2 Prime");
			capabilities.setCapability("deviceId", "8bbac5c67d52");
			capabilities.setCapability("platformVersion", "5.5.1");
			capabilities.setCapability("platformName", "Android");
			capabilities.setCapability("app", app.getAbsolutePath());
			capabilities.setCapability("appPackage", "com.snapwork.hdfc");
			capabilities.setCapability("appActivity", "com.snapwork.hdfc.HDFCBank");

			driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
			// driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	@Test
	public void Scenario_1() {
		WebDriverWait waitObj = new WebDriverWait(driver, 2 * 60);
		WebElement btnRegister = waitObj.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//android.view.View[@content-desc='Customer Id']")));
		btnRegister.click();
		driver.findElement(By.xpath("//android.view.View[contains(@resource-id,'fldLoginUserId')]")).sendKeys("xxxxx");
		driver.findElement(By.xpath("//android.view.View[@content-desc='Continue']")).click();
		driver.findElement(By.xpath("//android.view.View[contains(@resource-id,'chkLogin')]")).click();
		driver.findElement(By.xpath("//android.view.View[contains(@resource-id,'frmLoginPass')]")).sendKeys("xxxxx");
		WebElement login = waitObj
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.view.View[@content-desc='Login']")));
		login.click();
		driver.findElement(By.xpath("//android.view.View[@content-desc='Account Summary']")).click();
		
		//Scenario ---1
//		Launch the application and login with valid credentials and assert if the account details with correct balance are correctly displayed in the Main Dashboard screen
		Assert.assertTrue(driver.findElement(By.xpath("//android.view.View/[@index='0']")).getText().contains("*****"));
		driver.findElement(By.xpath("//android.view.View[contains(@resource-id,'bv')]")).click();
		driver.findElement(By.xpath("//android.view.View[@content-desc='Funds Transfer']")).click();
		WebElement bkButton = waitObj.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//android.view.View[contains(@resource-id,'fldToAcctNo')]")));
		bkButton.click();

//		Scenario 2
//		 
//		2.View a list of added beneficiaries and assert their given names
		
		List<WebElement> benName = driver.findElements(By.className("android.widget.CheckedTextView"));
		for (WebElement e : benName) {
			Assert.assertTrue(e.getAttribute("text").equals("Sivakumar-5*************"));
		}
	}
	
	@AfterSuite
	public void driverClose() {

		driver.quit();
	}
}
