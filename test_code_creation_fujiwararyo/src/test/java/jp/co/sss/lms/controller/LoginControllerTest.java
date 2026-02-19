package jp.co.sss.lms.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginControllerTest {

	@LocalServerPort
	private int port;
	private WebDriver driver;
	private WebDriverWait wait;
	private static final String SCREENSHOT_DIR = "./screenshots";

	@BeforeEach
	void setUp() {
		ChromeOptions options = new ChromeOptions();
		driver = new ChromeDriver(options);
		LoginPage(driver);
		Path path = Paths.get(SCREENSHOT_DIR);
	}

	@AfterEach
	void taerDwun() {
		if (driver != null) {
			driver.quit();
		}
	}

	private void takeScreenshot(String fileNameBase) throws IOException {
		String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
		File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		Files.copy(file.toPath(), Paths.get(SCREENSHOT_DIR + "/" + fileNameBase + timestamp + ".png"));
	}
	
	public void LoginPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	@Test
	@DisplayName("指定のURLの画面を開くテスト")
	void loginTest() throws Exception {

		//		指定のURLの画面を開く
		driver.get("http://localhost:" + port + "/lms");

		//		titleの取得とアサーション
		assertEquals("ログイン | LMS", driver.getTitle());
		
		takeScreenshot("login_test");
	}
}
