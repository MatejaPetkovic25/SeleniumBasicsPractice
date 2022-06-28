import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class MatejaDomaci0606 {
    //Zadatak za domaci
    //Zadatak za Wordpress koji smo radili treba uraditi ponovo
    // sa anotacijama i napraviti test kejseve za neuspesan login

    WebDriver driver;
    WebDriverWait wdwait;
    WebElement loginPageButton;
    WebElement usernameBox;
    WebElement passwordBox;
    WebElement continueButton;


    String URL = "https://wordpress.com";
    String validUsername = "nekijuzernejm1";
    String validPassword = "nekasifra1";
    String invalidUsername = "invalidUsername10";
    String invalidPassword = "invalidPassword";
    String invalidPasswordMessage = "Oops, that's not the right password. Please try again!";
    String blankUsernameBoxMessage = "Please enter a username or email address.";
    String blankPasswordBoxMessage = "Don't forget to enter your password.";


    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wdwait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }

    @BeforeMethod
    public void pageSetUp() throws InterruptedException {
        driver.manage().window().maximize();
        driver.navigate().to(URL);
        loginPageButton = driver.findElement(By.linkText("Log In"));
        loginPageButton.click();
        Thread.sleep(2000);

    }

    @Test (priority = 10)
    public void succesfullLoginTest() throws InterruptedException {
        usernameBox = driver.findElement(By.id("usernameOrEmail"));
        continueButton = driver.findElement(By.cssSelector(".button.form-button.is-primary"));
        usernameBox.sendKeys(validUsername);
        continueButton.click();
        wdwait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("password"))));
        passwordBox = driver.findElement(By.id("password"));
        passwordBox.sendKeys(validPassword);
        continueButton.click();
        Thread.sleep(3000);

        WebElement profileButton = driver.findElement(By.cssSelector(".gravatar.masterbar__item-me-gravatar"));
        profileButton.click();
        Thread.sleep(3000);
        String actualUsername = driver.findElement(By.className("profile-gravatar__user-display-name")).getText();
        Assert.assertEquals(actualUsername, validUsername);
        WebElement image = driver.findElement(By.className("gravatar"));
        Assert.assertTrue(image.isDisplayed());
    }

    @Test (priority = 20)
    public void loginWithInvalidUsername() throws InterruptedException {
        usernameBox = driver.findElement(By.id("usernameOrEmail"));
        continueButton = driver.findElement(By.cssSelector(".button.form-button.is-primary"));
        usernameBox.sendKeys(invalidUsername);
        continueButton.click();
        Thread.sleep(2000);

        WebElement errorMessage = driver.findElement(By.cssSelector(".form-input-validation.is-error"));

        boolean check = false;
        try {
            check = driver.findElement(By.id("password")).isDisplayed();
        } catch (Exception e) {

        }

        Assert.assertFalse(check);
        Assert.assertTrue(errorMessage.isDisplayed());
    }

    @Test (priority = 30)
    public void loginWithInvalidPassword() throws InterruptedException {
        usernameBox = driver.findElement(By.id("usernameOrEmail"));
        continueButton = driver.findElement(By.cssSelector(".button.form-button.is-primary"));
        usernameBox.sendKeys(validUsername);
        continueButton.click();
        wdwait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("password"))));
        passwordBox = driver.findElement(By.id("password"));
        passwordBox.sendKeys(invalidPassword);
        continueButton.click();
        Thread.sleep(2000);

        WebElement errorMessage = driver.findElement(By.cssSelector(".form-input-validation.is-error"));

        Assert.assertTrue(continueButton.isDisplayed());
        Assert.assertTrue(errorMessage.isDisplayed());
        Assert.assertEquals(errorMessage.getText(), invalidPasswordMessage);

    }

    @Test (priority = 40)
    public void loginWithoutEnteringUsername() {
        continueButton = driver.findElement(By.cssSelector(".button.form-button.is-primary"));
        continueButton.click();
        WebElement errorMessage = driver.findElement(By.cssSelector(".form-input-validation.is-error"));

        boolean check = false;
        try {
            check = driver.findElement(By.id("password")).isDisplayed();
        } catch (Exception e) {

        }

        Assert.assertFalse(check);
        Assert.assertTrue(errorMessage.isDisplayed());
        Assert.assertEquals(errorMessage.getText(), blankUsernameBoxMessage);
    }

    @Test (priority = 50)
    public void loginWithoutEnteringPassword() throws InterruptedException {
        usernameBox = driver.findElement(By.id("usernameOrEmail"));
        continueButton = driver.findElement(By.cssSelector(".button.form-button.is-primary"));
        usernameBox.sendKeys(validUsername);
        continueButton.click();
        wdwait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("password"))));
        continueButton.click();
        Thread.sleep(2000);

        WebElement errorMessage = driver.findElement(By.cssSelector(".form-input-validation.is-error"));

        Assert.assertTrue(continueButton.isDisplayed());
        Assert.assertTrue(errorMessage.isDisplayed());
        Assert.assertEquals(errorMessage.getText(), blankPasswordBoxMessage);
    }

    @AfterMethod
    public void removeCookies() throws InterruptedException {
        Thread.sleep(3000);
        driver.manage().deleteAllCookies();
    }

    @AfterClass
    public void tearDown() {
        driver.close();
        driver.quit();
    }

}
