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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト レポート機能
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

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
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {

		webDriver
				.findElement(By.xpath(
						"//tr[td[contains(normalize-space(), '2022年10月2日(日)')]][td[3][contains(., '提出済み')]]/td[5]"))
				.click();
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.titleIs("セクション詳細 | LMS"));
		scrollTo("200");
		
		WebElement button = webDriver.findElement(By.xpath("//input[@value='提出済み週報【デモ】を確認する']"));
		assertEquals("提出済み週報【デモ】を確認する", button.getAttribute("value"));
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {

		webDriver.findElement(By.xpath("//input[@value='提出済み週報【デモ】を確認する']")).click();
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.titleIs("レポート登録 | LMS"));
		
		scrollTo("500");
		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {
		
		//		所感のテキストタグをクリアにし、記入する
		WebElement xpathElement = webDriver.findElement(By.xpath("//*[@id='content_1']"));
		xpathElement.clear();
		xpathElement.sendKeys("修正しました。");

		webDriver.findElement(By.xpath("//button[@type='submit']")).click();
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.titleIs("セクション詳細 | LMS"));
		
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {

		webDriver.findElement(By.partialLinkText("ようこそ受講生ＡＡ１さん")).click();
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.titleIs("ユーザー詳細"));
		
		scrollTo("500");
		assertEquals("ユーザー詳細", webDriver.getTitle());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {

		webDriver.findElement(By.xpath("//tr[td[text()='週報【デモ】']]/td[5]/form[1]")).click();
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.titleIs("レポート詳細 | LMS"));

		//		修正しました。という文字列を取得する
		WebElement textElement = webDriver.findElement(By.xpath("//tr[th[text()='所感']]/td"));
		assertEquals("修正しました。", textElement.getText());
		getEvidence(new Object() {
		});
	}
}
