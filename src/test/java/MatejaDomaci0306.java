import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class MatejaDomaci0306 {
    public static void main(String[] args) throws InterruptedException {
   //Zadatak za domaci
        //Otici na sajt https://s1.demo.opensourcecms.com/wordpress/
        //Naci comment sekciju. Ostaviti komentar. Assertovati da li je komentar prikazan na stranici.

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.navigate().to("https://s1.demo.opensourcecms.com/wordpress/");

        WebElement findingCommentSection = driver.findElement(By.xpath("/html/body/div/main/ul/li/div/div[1]/div[1]/div[2]/time/a"));
        findingCommentSection.click();

        WebElement commentSection = driver.findElement(By.id("comment"));
        WebElement nameSection = driver.findElement(By.id("author"));
        WebElement emailSection = driver.findElement(By.id("email"));
        WebElement postComment = driver.findElement(By.id("submit"));

        String comment = "This is a new comment, published by myself";
        String name = "Saul Goodman";
        String email = "saul@mail.com";

        commentSection.sendKeys(comment);
        nameSection.sendKeys(name);
        emailSection.sendKeys(email);
        postComment.click();

        Thread.sleep(1000);

        String actualName = driver.findElement(By.xpath("/html/body/div/main/div[5]/div[3]/ol/li[2]/article/footer/div[1]/b")).getText();
        WebElement commentAwaitingModeration = driver.findElement(By.className("comment-awaiting-moderation"));

        Assert.assertEquals(actualName, name);
        Assert.assertTrue(commentAwaitingModeration.isDisplayed());


    }
}
