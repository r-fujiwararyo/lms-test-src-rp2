package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト レポート機能
 * ケース09
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース09 受講生 レポート登録 入力チェック")
public class Case09 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {

		// 指定のURLの画面を開く
		goTo("http://localhost:8080/lms");

		// titleの取得とアサーション
		assertEquals("ログイン | LMS", webDriver.getTitle());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {

		WebElement loginId = webDriver.findElement(By.id("loginId"));
		loginId.clear();
		loginId.sendKeys("StudentAA01");

		WebElement passElement = webDriver.findElement(By.id("password"));
		passElement.clear();
		passElement.sendKeys("StudentAA01A");

		WebElement clickElement = webDriver.findElement(By.className("btn"));
		clickElement.click();

		pageLoadTimeout(30);

		assertEquals("コース詳細 | LMS", webDriver.getTitle());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test03() {

		webDriver.findElement(By.partialLinkText("ようこそ受講生ＡＡ１さん")).click();
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.titleIs("ユーザー詳細"));

		scrollTo("300");
		assertEquals("ユーザー詳細", webDriver.getTitle());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 該当レポートの「修正する」ボタンを押下しレポート登録画面に遷移")
	void test04() {

		webDriver.findElement(By.xpath("//tr[td[text()='週報【デモ】']]/td[5]/form[2]")).click();
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.titleIs("レポート登録 | LMS"));
		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しエラー表示：学習項目が未入力")
	void test05() {

		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		webDriver.findElement(By.id("intFieldName_0")).clear();

		scrollTo("400");
		webDriver.findElement(By.className("btn-primary")).click();
		visibilityTimeout(By.className("errorInput"), 10);
		WebElement inputField = webDriver.findElement(By.id("intFieldName_0"));

		String classNames = inputField.getAttribute("class");
		assertTrue(classNames.contains("errorInput"));
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：理解度が未入力")
	void test06() {

		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		WebElement inputElement = webDriver.findElement(By.id("intFieldName_0"));
		inputElement.sendKeys("ITリテラシー①");

		WebElement xpathElement = webDriver.findElement(By.id("intFieldValue_0"));

		Select selectReason = new Select(xpathElement);
		selectReason.selectByIndex(0);
		scrollTo("400");

		webDriver.findElement(By.className("btn-primary")).click();
		visibilityTimeout(By.className("errorInput"), 10);
		WebElement inputField = webDriver.findElement(By.id("intFieldValue_0"));
		String classNames = inputField.getAttribute("class");
		assertTrue(classNames.contains("errorInput"));
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が数値以外")
	void test07() {

		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		WebElement inputElement = webDriver.findElement(By.id("content_0"));
		inputElement.clear();
		inputElement.sendKeys("$");
		scrollTo("400");
		webDriver.findElement(By.className("btn-primary")).click();
		visibilityTimeout(By.className("errorInput"), 10);

		WebElement inputField = webDriver.findElement(By.id("content_0"));
		String classNames = inputField.getAttribute("class");
		assertTrue(classNames.contains("errorInput"));
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が範囲外")
	void test08() {

		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		WebElement inputElement = webDriver.findElement(By.id("content_0"));
		inputElement.clear();
		inputElement.sendKeys("11");
		scrollTo("400");
		webDriver.findElement(By.className("btn-primary")).click();

		visibilityTimeout(By.className("errorInput"), 10);
		WebElement inputField = webDriver.findElement(By.id("content_0"));
		String classNames = inputField.getAttribute("class");
		assertTrue(classNames.contains("errorInput"));
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度・所感が未入力")
	void test09() {

		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

		WebElement idElement = webDriver.findElement(By.id("content_0"));
		idElement.clear();
		scrollTo("400");
		WebElement inputElement = webDriver.findElement(By.id("content_1"));
		inputElement.clear();
		webDriver.findElement(By.className("btn-primary")).click();
		visibilityTimeout(By.className("errorInput"), 10);
		WebElement idField = webDriver.findElement(By.id("content_0"));
		WebElement inputField = webDriver.findElement(By.id("content_1"));
		String className = inputField.getAttribute("class");
		String classNames = idField.getAttribute("class");
		assertTrue(classNames.contains("errorInput"));
		assertTrue(className.contains("errorInput"));
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(10)
	@DisplayName("テスト10 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：所感・一週間の振り返りが2000文字超")
	void test10() {

		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		String longText = "A".repeat(2001);
		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		scrollTo("400");
		WebElement impressions = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("content_1")));
		impressions.clear();
		js.executeScript("arguments[0].value = arguments[1];", impressions, longText);

		WebElement week = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("content_2")));
		week.clear();
		js.executeScript("arguments[0].value = arguments[1];", week, longText);

		webDriver.findElement(By.className("btn-primary")).click();

		visibilityTimeout(By.className("errorInput"), 10);
		WebElement idField = webDriver.findElement(By.id("content_1"));
		WebElement inputField = webDriver.findElement(By.id("content_2"));
		String className = idField.getAttribute("class");
		String classNames = inputField.getAttribute("class");
		assertTrue(classNames.contains("errorInput"));
		assertTrue(className.contains("errorInput"));
		scrollTo("400");
		getEvidence(new Object() {
		});
	}
}
