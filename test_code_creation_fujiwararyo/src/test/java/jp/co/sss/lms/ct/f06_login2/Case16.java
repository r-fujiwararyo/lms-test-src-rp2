package jp.co.sss.lms.ct.f06_login2;

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
 * 結合テスト ログイン機能②
 * ケース16
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース16 受講生 初回ログイン 変更パスワード未入力")
public class Case16 {

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
	@DisplayName("テスト02 DBに初期登録された未ログインの受講生ユーザーでログイン")
	void test02() {

		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		WebElement loginId = webDriver.findElement(By.id("loginId"));
		loginId.clear();
		loginId.sendKeys("StudentAA02");

		WebElement passElement = webDriver.findElement(By.id("password"));
		passElement.clear();
		passElement.sendKeys("StudentAA02");

		WebElement clickElement = webDriver.findElement(By.className("btn"));
		clickElement.click();
		pageLoadTimeout(10);

				assertEquals("セキュリティ規約 | LMS", webDriver.getTitle());
				scrollTo("300");
				getEvidence(new Object() {
				});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 「同意します」チェックボックスにチェックを入れ「次へ」ボタン押下")
	void test03() {

				webDriver.findElement(By.name("securityFlg")).click();
				webDriver.findElement(By.className("btn-primary")).click();
				pageLoadTimeout(10);
				assertEquals("パスワード変更 | LMS", webDriver.getTitle());
				getEvidence(new Object() {
				});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 パスワードを未入力で「変更」ボタン押下")
	void test04() {

		WebElement pass = webDriver.findElement(By.id("password"));
		pass.clear();
		pass.sendKeys("StudentAA02A");

		WebElement newpass = webDriver.findElement(By.id("passwordConfirm"));
		newpass.clear();
		newpass.sendKeys("StudentAA02A");
		scrollTo("500");
		webDriver.findElement(By.xpath("//div/button[text()='変更']")).click();
		visibilityTimeout(By.id("modal-label"), 10);

		webDriver.findElement(By.id("upd-btn")).click();
		WebElement error = webDriver
				.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[1]/div/ul/li/span"));
		assertEquals("現在のパスワードは必須です。", error.getText());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 20文字以上の変更パスワードを入力し「変更」ボタン押下")
	void test05() {

		WebElement current = webDriver.findElement(By.id("currentPassword"));
		current.clear();
		current.sendKeys("StudentAA02");

		WebElement pass = webDriver.findElement(By.id("password"));
		pass.clear();
		pass.sendKeys("StudentAA02AStudentAA02AStudentAA02A");

		WebElement newpass = webDriver.findElement(By.id("passwordConfirm"));
		newpass.clear();
		newpass.sendKeys("StudentAA02AStudentAA02AStudentAA02A");

		scrollTo("500");
		webDriver.findElement(By.xpath("//div/button[text()='変更']")).click();
		visibilityTimeout(By.id("modal-label"), 10);

		webDriver.findElement(By.id("upd-btn")).click();

		WebElement error = webDriver.findElement(
				By.xpath("//span[contains(@class,'error') and contains(text(),'パスワードの長さが最大値(20)を超えています。')]"));
		assertEquals("パスワードの長さが最大値(20)を超えています。", error.getText());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 ポリシーに合わない変更パスワードを入力し「変更」ボタン押下")
	void test06() {

		WebElement current = webDriver.findElement(By.id("currentPassword"));
		current.clear();
		current.sendKeys("StudentAA02");

		WebElement pass = webDriver.findElement(By.id("password"));
		pass.clear();
		pass.sendKeys("aaaaa");

		WebElement newpass = webDriver.findElement(By.id("passwordConfirm"));
		newpass.clear();
		newpass.sendKeys("aaaaa");
		
		scrollTo("500");
		webDriver.findElement(By.xpath("//div/button[text()='変更']")).click();

		visibilityTimeout(By.id("modal-label"), 10);

		webDriver.findElement(By.id("upd-btn")).click();

		WebElement error = webDriver.findElement(
				By.xpath(
						"//span[contains(@class,'help-inline') and contains(text(),'パスワードは英大文字、英小文字、数字の3文字種を混合させた8文字以上を入力してください。')]"));
		assertEquals("パスワードは英大文字、英小文字、数字の3文字種を混合させた8文字以上を入力してください。", error.getText());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 一致しない確認パスワードを入力し「変更」ボタン押下")
	void test07() {
		
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		WebElement current = webDriver.findElement(By.id("currentPassword"));
		current.clear();
		current.sendKeys("StudentAA02");

		WebElement pass = webDriver.findElement(By.id("password"));
		pass.clear();
		pass.sendKeys("StudentAA02A");

		WebElement newpass = webDriver.findElement(By.id("passwordConfirm"));
		newpass.clear();
		newpass.sendKeys("StudentAA02");
		
		scrollTo("500");
		webDriver.findElement(By.xpath("//div/button[text()='変更']")).click();

		visibilityTimeout(By.id("modal-label"), 10);

		webDriver.findElement(By.id("upd-btn")).click();
		
		WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(
			    By.xpath("//span[contains(@class,'error') and contains(text(),'パスワードと確認パスワードが一致しません。')]")));
		assertEquals("パスワードと確認パスワードが一致しません。", error.getText().trim());
		getEvidence(new Object() {
		});
	}
}
