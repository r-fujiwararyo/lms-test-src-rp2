package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト 勤怠管理機能
 * ケース10
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース10 受講生 勤怠登録 正常系")
public class Case10 {

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
		loginId.sendKeys("StudentAA02");

		WebElement passElement = webDriver.findElement(By.id("password"));
		passElement.clear();
		passElement.sendKeys("StudentAA02A");

		WebElement clickElement = webDriver.findElement(By.className("btn"));
		clickElement.click();

		pageLoadTimeout(30);

		assertEquals("コース詳細 | LMS", webDriver.getTitle());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「勤怠」リンクから勤怠管理画面に遷移")
	void test03() {

		webDriver.findElement(By.linkText("勤怠")).click();
		pageLoadTimeout(30);
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「出勤」ボタンを押下し出勤時間を登録")
	void test04() {

		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		WebElement attendance = webDriver.findElement(
				By.xpath("//input[@value='出勤']"));
		attendance.click();
		wait.until(ExpectedConditions.alertIsPresent());

		// アラートに制御を切り替える
		Alert alert = webDriver.switchTo().alert();

		// OKボタン（確定）をクリック
		alert.accept();

		// テーブルの特定のセルが表示され、かつ「テキストが空ではない」状態になるまで待つ
		WebElement attendanceTime = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[@id='main']//table/tbody/tr[1]/td[3]")));

		// 打刻された瞬間の時刻を取得
		String expectedTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));

		assertEquals(expectedTime, attendanceTime.getText().trim());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「退勤」ボタンを押下し退勤時間を登録")
	void test05() {
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		WebElement attendance = webDriver.findElement(
				By.xpath("//input[@value='退勤']"));
		attendance.click();
		// アラートに制御を切り替える
		Alert alert = webDriver.switchTo().alert();

		// OKボタン（確定）をクリック
		alert.accept();

		// テーブルの特定のセルが表示され、かつ「テキストが空ではない」状態になるまで待つ
		WebElement attendanceTime = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[@id='main']//table/tbody/tr[1]/td[4]")));

		// 打刻された瞬間の時刻を取得
		String expectedTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));

		assertEquals(expectedTime, attendanceTime.getText().trim());
		getEvidence(new Object() {
		});
	}
}
