package linkvindya;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;

import java.time.Duration;
import org.apache.commons.io.FileUtils;

public class Longpost{

    public static void main(String[] args) {

        // Setup ChromeDriver
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        try {
            // 1. Navigate to LinkedIn login page
            driver.get("https://www.linkedin.com/login");
            System.out.println("✅ Opened LinkedIn login page");

            // 2. Log in
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))).sendKeys("madhumaliamarasekara@gmail.com");
            driver.findElement(By.id("password")).sendKeys("Madhu2002@1234");
            driver.findElement(By.xpath("//button[@type='submit']")).click();
            System.out.println("✅ Logged in");

            // 3. Wait for the feed to load
            wait.until(ExpectedConditions.urlContains("/feed"));
            System.out.println("✅ Navigated to feed: " + driver.getCurrentUrl());

            // 4. Click 'Start a post'
        
            WebElement startPostBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"ember53\"]")));
            startPostBtn.click();
            System.out.println("✅ Clicked 'Start a post'");

            // 5. Wait for post box and enter content using JS
            WebElement postBox = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("div[role='textbox'][contenteditable='true']")));

            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Inject text via JavaScript
            String postText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec ut semper sapien. Praesent consequat, turpis at dapibus efficitur, magna arcu mollis odio, vitae hendrerit augue lorem at nisl. Suspendisse";
            js.executeScript("arguments[0].innerText = arguments[1];", postBox, postText);

            // Dispatch input event to trigger LinkedIn's change detection
            js.executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", postBox);

            System.out.println("✅ Entered post text");

            // 6. Click Post button
           
            WebElement postBtn = wait.until(
            	    ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"ember218\"]"))
            	);
            	postBtn.click();
            	System.out.println("✅ Post submitted");

            // 7. Wait a bit and take screenshot
            wait.until(ExpectedConditions.invisibilityOf(postBtn));  // Post modal closes
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File("linkedin_post_success.png"));
            System.out.println("✅ Screenshot saved as 'linkedin_post_success.png'");

        } catch (Exception e) {
            System.err.println("❌ Error occurred:");
            e.printStackTrace();
        } finally {
            driver.quit();
            System.out.println("🔒 Browser closed");
        }
    }
}

