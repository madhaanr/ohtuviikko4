
package ohtu;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class Tester {
    public static void main(String[] args) {
        WebDriver driver = new HtmlUnitDriver();

        driver.get("http://localhost:8080");
        
        System.out.println( driver.getPageSource() );
        
        WebElement element = driver.findElement(By.linkText("register new user"));       
        element.click(); 
        
        System.out.println("==");       
        System.out.println( driver.getPageSource() );
        
        element = driver.findElement(By.name("username"));
        element.sendKeys("ralliauto3");
        element = driver.findElement(By.name("password"));
        element.sendKeys("ralliauto3");
        element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys("ralliauto3");
        element = driver.findElement(By.name("add"));
        element.submit();
        
        System.out.println("==");
        System.out.println( driver.getPageSource() );
        
        driver.get("http://localhost:8080");
        element = driver.findElement(By.linkText("login"));
        element.click();
        element = driver.findElement(By.name("username"));
        element.sendKeys("ralliauto");
        element = driver.findElement(By.name("password"));
        element.sendKeys("ralliauto1");
        element = driver.findElement(By.name("login"));
        element.submit();
        
              
//        element = driver.findElement(By.name("username"));
//        element.sendKeys("ralliauto");
//        element = driver.findElement(By.name("password"));
//        element.sendKeys("ralliauto1");
//        element = driver.findElement(By.name("passwordConfirmation"));
//        element.sendKeys("ralliauto1");
//        element = driver.findElement(By.name("add"));
//        element.submit();
        
//        element = driver.findElement(By.name("username"));
//        element.sendKeys("pek");
//        element = driver.findElement(By.name("password"));
//        element.sendKeys("akkep");
//        element = driver.findElement(By.name("login"));
//        element.submit();
        
//        element = driver.findElement(By.name("username"));
//        element.sendKeys("pekka");
//        element = driver.findElement(By.name("password"));
//        element.sendKeys("a");
//        element = driver.findElement(By.name("login"));
//        element.submit();
                
        System.out.println("==");
        System.out.println( driver.getPageSource() );
        
        //epäonnistunut kirjautuminen: oikea käyttäjätunnus, väärä salasana
    }
}
