import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class MatejaDomaci0206 {
    public static void main(String[] args) throws InterruptedException {

        //Zadatak za domaci
        //Napraviti program koji ce dodati ovaj proizvod u korpu
        // https://www.amazon.com/Selenium-Framework-Design-Data-Driven-Testing/dp/1788473574/ref=sr_1_2?dchild=1&keywords=selenium+test&qid=1631829742&sr=8-2
        //zatim obrisati proizvod brisanjem cookie-a i proveriti da li je korpa prazna

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.navigate().to("https://www.amazon.com/Selenium-Framework-Design-Data-Driven-Testing/dp/1788473574/ref=sr_1_2?dchild=1&keywords=selenium+test&qid=1631829742&sr=8-2");

        WebElement addToCart = driver.findElement(By.id("add-to-cart-button"));
        addToCart.click();

        //Thread.sleep(1000);

        WebElement goToCart = driver.findElement(By.id("sw-gtc"));
        goToCart.click();

        Cookie kolacic = new Cookie("session-id", "137-2776890-1434158");

        //Thread.sleep(1000);

        driver.manage().deleteCookie(kolacic);
        driver.navigate().refresh();

        WebElement cartIsEmpty = driver.findElement(By.cssSelector(".a-row.sc-your-amazon-cart-is-empty"));
        String actualResult = driver.findElement(By.id("nav-cart-count")).getText();
        String expectedResult = "0";

        Assert.assertTrue(cartIsEmpty.isDisplayed());
        Assert.assertEquals(actualResult, expectedResult);

    }
}
