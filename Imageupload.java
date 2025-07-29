package linkvindya;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;

public class Imageupload{

    public static void main(String[] args) {
        // Setup ChromeDriver
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

       

        try {
            // Step 1: Login
            driver.get("https://www.linkedin.com/login");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")))
                .sendKeys("madhumaliamarasekara@gmail.com");  // 🔁 REPLACE
            driver.findElement(By.id("password")).sendKeys("Madhu2002@1234");  // 🔁 REPLACE
            driver.findElement(By.xpath("//button[@type='submit']")).click();
            wait.until(ExpectedConditions.urlContains("/feed"));
           

            
         // Step 3: Click "Add media" (photo icon)
           
            WebElement photoButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@aria-label='Add a photo']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", photoButton);
            

            // Step 4: Upload image from local file system
            
         // Step 4: Upload image from local file system
            WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//input[@type='file']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].style.display = 'block';", fileInput);
            fileInput.sendKeys("C:\\Users\\PN Tech\\Pictures\\cyber.jpg");
            System.out.println("📷 Image uploaded");
            Thread.sleep(4000);  // Allow time for the image to finish uploading
            


            // Step 5: Click "Next"
            
            WebElement nextBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"ember238\"]")));
            nextBtn.click();
            System.out.println("➡️ Clicked Next");
            
            // Step 6: Click "Post"
        
            WebElement postBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"ember290\"]")));
            postBtn.click();
            System.out.println("🚀 Post submitted!");
            Thread.sleep(6000); 
            

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
