package stepDefinations;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class signupPageSteps {
    private WebDriver driver;

    //For generating random characters(test data)
    public static String generateRandomnumber(int a) {
        String candidateChars = "1234567890";
        int length = a;
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(candidateChars.charAt(random.nextInt(candidateChars.length())));
        }
        return sb.toString();
    }

    public static String generateRandomSpecialrChars(int a) {
        String candidateChars = "!@#$%^&*()/";
        int length = a;
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(candidateChars.charAt(random.nextInt(candidateChars
                    .length())));
        }
        return sb.toString();
    }
    public static String generateRandomalphaSpecialrChars(int a) {
        String candidateChars = "ABCDEFGHIJKLMN!@#$%OPQRSTUVWXYZ^&*()/abcdefghijklmnopqrstuvwxyz";
        int length = a;
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(candidateChars.charAt(random.nextInt(candidateChars
                    .length())));
        }
        return sb.toString();
    }

    public static String generateRandomalphaChars(int a) {
        String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        int length = a;
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(candidateChars.charAt(random.nextInt(candidateChars
                    .length())));
        }
        return sb.toString();
    }

    @Given("I am on the signup page")
    public void iAmOnSignupPage() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\RAVI\\Downloads\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://tutorialsninja.com/demo/index.php?route=account/register");
    }

    @When("I enter valid credentials")
    public void enterValidCredentials() throws InterruptedException {
        // Enter valid first name, last name, email, password, and confirm password
        Thread.sleep(4000);
        WebElement firstName=driver.findElement(By.xpath("//input[@placeholder=\"First Name\"]"));
        firstName.sendKeys(generateRandomalphaChars(5));//generateRandomalphaChars method will give random characters
        driver.findElement(By.id("input-lastname")).sendKeys(generateRandomalphaChars(4));
        driver.findElement(By.id("input-email")).sendKeys(generateRandomalphaChars(9)+"@gmail.com");//generateRandomalphaSpecialrChars will give random character with special char
        driver.findElement(By.id("input-telephone")).sendKeys(generateRandomnumber(10));
        driver.findElement(By.id("input-password")).sendKeys("password123");
        driver.findElement(By.id("input-confirm")).sendKeys("password123");
        driver.findElement(By.cssSelector("input[type='checkbox'][name='agree']")).click(); // Check privacy policy checkbox
    }

    @When("I enter an existing email")
    public void enterExistingEmail() {
        // Enter an existing email address
        driver.findElement(By.id("input-email")).sendKeys("existing.email@example.com");
    }

    @When("the password and confirm password fields do not match")
    public void mismatchedPasswords() {
        // Enter mismatched passwords
        driver.findElement(By.id("input-password")).sendKeys("password123");
        driver.findElement(By.id("input-confirm")).sendKeys("password456");
        driver.findElement(By.cssSelector("input[type='checkbox'][name='agree']")).click(); // Check privacy policy checkbox

    }

    @When("I enter an invalid email format")
    public void enterInvalidEmailFormat() {
        // Enter an invalid email address format
        driver.findElement(By.id("input-email")).sendKeys("invalid-email");
    }

    @When("I submit the signup form")
    public void submitSignupForm() {
        // Submit the signup form
        driver.findElement(By.cssSelector("input[type='submit'][value='Continue']")).click();
    }

    @Then("I should see a success message")
    public void verifySuccessMessage() throws InterruptedException {
        Thread.sleep(4000);
        WebElement successMessage = driver.findElement(By.xpath("//h1[text()='Your Account Has Been Created!']"));
        assert successMessage.isDisplayed();
    }

    @Then("I should see an error message")
    public void verifyErrorMessage() throws InterruptedException, IOException {
        Thread.sleep(2000);
        WebElement errorMessage = driver.findElement(By.cssSelector(".alert-danger"));
        assert errorMessage.isDisplayed();

        if (errorMessage.isDisplayed()) {
            // Capture screenshot
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destinationFile = new File("src/test/ErrorScreenShots/error_screenshot.png");
            Files.copy(screenshotFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    @Then("I close the browser")
    public void closeBrowser() {
        driver.quit();
    }
}
