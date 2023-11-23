package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.Assert;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.Objects;

public class TestCases {

    public static String USERNAME = "vinishaharinarain@gmail.com";
    public static String PASSWORD = "onlineShoppingPassword1!";

    public static void main(String[] args) {

        //Please go through each test case individually
        //TestCase1();
        //TestCase2();
        //TestCase3();
        //TestCase4();
        //TestCase5();
        //TestCase6();
    }

    public static void TestCase1() {
        FirefoxDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("http://automationpractice.pl/");
        WebElement element = driver.findElement(By.xpath("//input[@name='search_query']"));

        String searchCriteria = "dress";
        element.sendKeys(searchCriteria);

        WebElement button = driver.findElement(By.xpath("//button[@name='submit_search']"));
        button.click();
    }

    public static void TestCase2() {
        //Section a
        FirefoxDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("http://automationpractice.pl/");
        WebElement element = driver.findElement(By.xpath("//input[@name='search_query']"));

        String searchCriteria = "dress,blouse,t shirt";
        element.sendKeys(searchCriteria);

        WebElement button = driver.findElement(By.xpath("//button[@name='submit_search']"));
        button.click();

        //Section b
        String[] searchArray = {"dress", "blouse", "t shirt"};

        for (String s : searchArray) {
            WebElement search = driver.findElement(By.xpath("//input[@name='search_query']"));
            search.clear();
            search.sendKeys(s);

            WebElement searchButton = driver.findElement(By.xpath("//button[@name='submit_search']"));
            searchButton.click();
        }
    }

    public static void TestCase3() {
        FirefoxDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("http://automationpractice.pl/");
        WebElement element = driver.findElement(By.xpath("//input[@name='search_query']"));

        List<String> searchCriteria = null;
        try {
            searchCriteria = Files.readAllLines(Paths.get("C:\\Users\\Vinisha.Harinarain\\OneDrive - Telestream Communications\\Desktop\\SearchCriteria.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        element.sendKeys(Objects.requireNonNull(searchCriteria).get(0));

        WebElement button = driver.findElement(By.xpath("//button[@name='submit_search']"));
        button.click();
    }

    public static void TestCase4() {
        FirefoxDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("http://automationpractice.pl/");

        WebElement signInButton = driver.findElement(By.xpath("//a[@class='login']"));
        signInButton.click();

        WebElement emailField = driver.findElement(By.xpath("//input[@name='email']"));
        emailField.sendKeys(USERNAME);
        WebElement passwordField = driver.findElement(By.xpath("//input[@name='passwd']"));
        passwordField.sendKeys(PASSWORD);

        WebElement button = driver.findElement(By.xpath("//button[@name='SubmitLogin']"));
        button.click();
    }

    public static void TestCase5() {
        //Section a
        FirefoxDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("http://automationpractice.pl/");
        WebElement element = driver.findElement(By.xpath("//input[@name='search_query']"));

        String searchCriteria = "dress";
        element.sendKeys(searchCriteria);

        WebElement button = driver.findElement(By.xpath("//button[@name='submit_search']"));
        button.click();

        WebElement divElement = driver.findElement(By.xpath("//div[@class='product-container']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(divElement).perform();

        WebElement productContainer = driver.findElement(By.xpath("//a[@class='button ajax_add_to_cart_button btn btn-default']"));
        productContainer.click();

        //Section b
        WebElement shoppingCartButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='btn btn-default button button-medium']")));
        shoppingCartButton.click();

        //Section c
        WebElement increaseQuantity = driver.findElement(By.xpath("//a[@class='cart_quantity_up btn btn-default button-plus']"));
        increaseQuantity.click();
        increaseQuantity.click();
        increaseQuantity.click();

        //Section d
        WebElement priceElement = driver.findElement(By.xpath("//li[@class='price']"));
        String priceText = priceElement.getText();
        String priceWithoutDollarSign = priceText.replace("$", "");
        double price = Double.parseDouble(priceWithoutDollarSign);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement quantityElement = driver.findElement(By.xpath("//input[@name='quantity_4_16_0_0_hidden']"));
        String quantityText = quantityElement.getAttribute("value");
        int quantity = Integer.parseInt(quantityText);

        WebElement totalElement = driver.findElement(By.xpath("//span[@id='total_product_price_4_16_0']"));
        String totalText = totalElement.getText();
        String totalWithoutDollarSign = totalText.replace("$", "");
        double total = Double.parseDouble(totalWithoutDollarSign);

        double calculatedTotal = TestCase5d(price, quantity);

        //Section e
        if (calculatedTotal == total) {
            System.out.println("Displayed total matches calculated total.");
        } else {
            System.out.println("Displayed total does not match calculated total.");
        }
    }

    public static Double TestCase5d(double unitPrice, int quantity) {
        return unitPrice * quantity;
    }

    public static void TestCase6() {
        FirefoxDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("http://automationpractice.pl/");

        //Section a
        WebElement womanTab = driver.findElement(By.xpath("//a[@class='sf-with-ul']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(womanTab).perform();

        //Section b & c
        WebElement summerCategory = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='Summer Dresses']")));
        summerCategory.click();

        // Section d
        String expectedURL = "http://www.automationpractice.pl/index.php?id_category=11&controller=category";
        String actualURL = driver.getCurrentUrl();
        Assert.assertEquals("The expected URL is not matching. Page load verification failed.", expectedURL, actualURL);
    }
}