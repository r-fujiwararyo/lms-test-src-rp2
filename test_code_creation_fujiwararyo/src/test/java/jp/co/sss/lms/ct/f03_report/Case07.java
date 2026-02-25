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
 * ケース07
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

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
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		scrollTo("200");

		webDriver.findElement(By.xpath("//tr[td[contains(normalize-space(), '未提出')]]/td[5]")).click();
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.titleIs("セクション詳細 | LMS"));
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		
		webDriver.findElement(By.xpath("//input[@value='日報【デモ】を提出する']")).click();
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.titleIs("レポート登録 | LMS"));
		
		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() {
		
		WebElement idElement = webDriver.findElement(By.id("content_0"));
		idElement.clear();
		idElement.sendKeys("今日も頑張りました。");
	
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//button[contains(normalize-space(), '提出')]")));
		submitBtn.click();

		// ボタン要素を取得
		WebElement button = webDriver.findElement(By.xpath("//input[@value='提出済み日報【デモ】を確認する']"));

		// ボタンの「value属性」の文字列と、期待する文字列を比較する
		assertEquals("提出済み日報【デモ】を確認する", button.getAttribute("value"));
		getEvidence(new Object() {
		});
	}
}
