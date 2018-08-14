package xertxa.tubemaster.bot.module;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import xertxa.tubemaster.bot.Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public abstract class Module implements Runnable {
    protected static int account;
    public String target;
    public String action;
    protected int amount;
    protected ArrayList<String> usernames = new ArrayList<>();
    protected Controller controller;
    protected ArrayList<String> passwords = new ArrayList<>();
    protected PhantomJSDriver driver;
    private WebDriverWait wait;
    private ArrayList<String> proxies = new ArrayList<>();

    public Module(String target, String action) {
        this.target = target;
        this.action = action;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setAmount(int amount) {
        int size = usernames.size();
        if (amount > size) {
            this.amount = size;
            controller.log("[INFO] Amount exceeds number of accounts. Clamped and set to " + size + " instead.");
        } else {
            this.amount = amount;
            controller.log("[INFO] Amount of " + amount + " set.");
        }
    }

    public void setAccounts(File list) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(list));
            String line = reader.readLine();
            while (line != null) {
                usernames.add(line.substring(0, line.indexOf(":")));
                passwords.add(line.substring(line.indexOf(":") + 1, line.length()));
                line = reader.readLine();
            }
            reader.close();
            int size = usernames.size();
            if (size > 1) {
                controller.log("[INFO] " + size + " accounts set from \"" + list.getName() + "\".");
            } else {
                controller.log("[INFO] " + size + " account set from \"" + list.getName() + "\".");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setProxies(File list) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(list));
            String line = reader.readLine();
            while (line != null) {
                proxies.add(line);
                line = reader.readLine();
            }
            reader.close();
            int size = proxies.size();
            if (size > 1) {
                controller.log("[INFO] " + size + " proxies set from \"" + list.getName() + "\".");
            } else {
                controller.log("[INFO] " + size + " proxy set from \"" + list.getName() + "\".");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void login(String username, String password) {
        driver.get("https://accounts.google.com/Login");
        wait.until(ExpectedConditions.titleIs("Sign in - Google Accounts"));
        WebElement nextButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("identifierNext")));
        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("identifierId")));
        usernameInput.sendKeys(username);
        nextButton.click();
        WebElement passwordNextButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("passwordNext")));
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
        passwordInput.sendKeys(password);
        passwordNextButton.click();
        wait.until(ExpectedConditions.titleIs("Google Account"));
        controller.log("[INFO] Signed into account \"" + username + "\".");
    }

    protected void click(String xpath) {
        try {
            WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
            Thread.sleep(500L);
            button.click();
            Thread.sleep(500L);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void setDriver() {
        System.setProperty("phantomjs.binary.path", "phantomjs.exe");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setJavascriptEnabled(true);
        driver = new PhantomJSDriver();
        wait = new WebDriverWait(driver, 10);
    }

    protected synchronized void increment() {
        account++;
    }
}
