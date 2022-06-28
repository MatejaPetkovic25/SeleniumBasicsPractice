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

import static org.openqa.selenium.Keys.ENTER;

public class MatejaDomaci0706 {
    //Zadatak za domaci
    //Otici na sajt https://cms.demo.katalon.com/
    //Pretraziti proizvod Happy Ninja
    //Odabrati poslednji proizvod ako ih ima vise
    //Kolicinu podesiti na 2
    //Otici na cart
    //Kolicinu promeniti na 1 i update-ovati cart
    //Assertujte sta mislite da je potrebno da bi test prosao

    WebDriver driver;
    WebDriverWait wdwait;

    WebElement searchBar;
    WebElement happyNinjaArticle;
    WebElement quantityField;
    WebElement cartQuantityField;
    WebElement addToCartButton;
    WebElement viewCartButton;
    WebElement updateCartButton;
    WebElement message;
    WebElement totalPrice;

    String URL = "https://cms.demo.katalon.com/";
    String articleName = "Happy Ninja";
    String oneArticle = "1";
    String twoArticles = "2";
    String expectedMessage = "Cart updated.";
    String expectedTotal = "$35.00";

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wdwait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @BeforeMethod
    public void pageSetUp() {
        driver.manage().window().maximize();
        driver.navigate().to(URL);
    }

    @Test
    public void cartAmountTest() throws InterruptedException {
        searchBar = driver.findElement(By.className("search-field"));
        searchBar.sendKeys(articleName);
        searchBar.sendKeys(ENTER);
        happyNinjaArticle = driver.findElement(By.xpath("//*[@id=\"post-27\"]/header/h2/a"));
        happyNinjaArticle.click();
        quantityField = driver.findElement(By.cssSelector(".input-text.qty.text"));
        quantityField.clear();
        quantityField.sendKeys(twoArticles);
        addToCartButton = driver.findElement(By.name("add-to-cart"));
        addToCartButton.click();
        viewCartButton = driver.findElement(By.cssSelector(".button.wc-forward"));
        viewCartButton.click();
        cartQuantityField = driver.findElement(By.cssSelector(".input-text.qty.text"));
        cartQuantityField.clear();
        cartQuantityField.sendKeys(oneArticle);
        updateCartButton = driver.findElement(By.name("update_cart"));
        updateCartButton.click();
        Thread.sleep(2000);
        //wdwait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("woocommerce-message"))));
        message = driver.findElement(By.className("woocommerce-message"));
        totalPrice = driver.findElement(By.xpath("//*[@id=\"post-8\"]/div/div/form/table/tbody/tr[1]/td[6]/span"));
        String actualMessage = message.getText();
        String actualTotal = totalPrice.getText();

        Assert.assertTrue(message.isDisplayed());
        Assert.assertEquals(actualMessage, expectedMessage);
        Assert.assertEquals(actualTotal, expectedTotal);

    }

    @AfterMethod
    public void deleteCookies() throws InterruptedException {
        Thread.sleep(5000);
        driver.manage().deleteAllCookies();
    }

    @AfterClass
    public void tearDown() {
        driver.close();
        driver.quit();
    }

}
