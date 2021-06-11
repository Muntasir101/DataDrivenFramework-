package com.DataProvider;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataProviderDemo {
    WebDriver driver;

    @BeforeClass
    public void setup()
    {
        System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"//src//main//resources//geckodriver.exe");
        driver=new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test(dataProvider="LoginData")
    public void loginTest(String user,String pwd,String exp)
    {
        driver.get("https://admin-demo.nopcommerce.com/login");

        WebElement txtEmail=driver.findElement(By.id("Email"));
        txtEmail.clear();
        txtEmail.sendKeys(user);

        WebElement txtPassword=driver.findElement(By.id("Password"));
        txtPassword.clear();
        txtPassword.sendKeys(pwd);

        driver.findElement(By.xpath("/html/body/div[6]/div/div/div/div/div[2]/div[1]/div/form/div[3]/button")).click(); //Login  button

        String exp_title="Dashboard / nopCommerce administration";
        String act_title=driver.getTitle();

        if(exp.equals("Valid"))
        {
            if(exp_title.equals(act_title))
            {
                driver.findElement(By.linkText("Logout")).click();
                Assert.assertTrue(true);
            }
            else
            {
                Assert.assertTrue(false);
            }
        }
        else if(exp.equals("Invalid"))
        {
            if(exp_title.equals(act_title))
            {
                driver.findElement(By.linkText("Logout")).click();
                Assert.assertTrue(false);
            }
            else
            {
                Assert.assertTrue(true);
            }
        }
    }

    @DataProvider(name="LoginData")
    public Object [][] getData() throws IOException
    {
		String loginData[][]= {
								{"admin@yourstore.com","admin12","InValid"},
								{"admin@yourstore.com","admin","valid"},
								{"adm@yourstore.com","admin","Invalid"},
								{"adm123456@yourstore.com","adm","Invalid"}
							};
        return loginData;
    }

    @AfterClass
    void tearDown()
    {
        driver.close();
    }
}