package linkvindya;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import org.apache.commons.io.FileUtils; // Required for copying screenshot file

public class specialcharacters{

    public static void main(String[] args) {

        // Setup ChromeDriver automatically
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        try {
            // 1. Navigate to LinkedIn login page
            driver.get("https://www.linkedin.com/login");
            System.out.println("Opened LinkedIn login page");

            // 2. Login with your credentials
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")))
                    .sendKeys("madhumaliamarasekara@gmail.com");
            driver.findElement(By.id("password")).sendKeys("Madhu2002@1234");
            
            driver.findElement(By.xpath("//button[@type='submit']")).click();
            
            System.out.println(" Logged in");

            // 3. Wait for feed page to load
            wait.until(ExpectedConditions.urlContains("/feed"));
            Thread.sleep(3000);  // Wait for feed page to fully load
            System.out.println(" Navigated to feed: " + driver.getCurrentUrl());

            // 4. Click 'Start a post' button
          
            WebElement startPostBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[@id=\"ember54\"]"))); // Replace with more stable XPath if needed
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", startPostBtn);
            Thread.sleep(500);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", startPostBtn);
            System.out.println(" Clicked 'Start a post'");

            // 5. Wait for post textbox modal
            WebElement postBox = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("div[role='textbox']")));
            postBox.sendKeys("£££££££££££££££HI***");
            System.out.println(" Entered post text");

           
            
            WebElement postBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[@id=\"ember260\"]"))); // Consider replacing with //button[contains(., 'Post')]
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", postBtn);
            System.out.println(" Post submitted");

            Thread.sleep(3000); // Wait to ensure post submission

            // 7. Take screenshot after post
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                FileUtils.copyFile(screenshot, new File("linkedin_post_success.png"));
                System.out.println(" Screenshot saved as 'linkedin_post_success.png'");
            } catch (IOException e) {
                System.err.println(" Failed to save screenshot");
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.err.println("❌ Error occurred:");
            e.printStackTrace();
        } finally {
            driver.quit();
            System.out.println("🔒 Browser closed");
        }
    }
}