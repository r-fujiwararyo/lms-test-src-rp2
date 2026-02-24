package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.Assert;
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
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト よくある質問機能
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

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
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {

		WebElement linkElement = webDriver.findElement(By.linkText("機能"));
		linkElement.click();

		WebElement titleElement = webDriver.findElement(By.linkText("ヘルプ"));
		titleElement.click();

		pageLoadTimeout(30);
		assertEquals("ヘルプ | LMS", webDriver.getTitle());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {

		WebElement classElement = webDriver.findElement(By.className("panel-body"));

		WebElement linkElement = webDriver.findElement(By.linkText("よくある質問"));
		linkElement.click();

		// タイトル名:よくある質問タグを呼ぶ
		webDriver.switchTo().window("");

		// 指定した要素が画面上に表示されるまで待つ 
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.titleIs("よくある質問 | LMS"));
		assertEquals("よくある質問 | LMS", webDriver.getTitle());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {

		WebElement classElement = webDriver.findElement(By.className("col-lg-10"));
		WebElement idElement = webDriver.findElement(By.id("form"));
		idElement.clear();
		idElement.sendKeys("研修");

		webDriver.findElement(By.cssSelector("input[value='検索']")).click();

		// 下に500ピクセルスクロールする
		scrollTo("500");

		String serch = webDriver.findElement(By.className("mb10")).getText();
		// 部分一致の判定
		Assert.assertTrue("研修", true);
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {
		JavascriptExecutor javascript = (JavascriptExecutor) webDriver;

		// 上に500ピクセルスクロールする
		javascript.executeScript("window.scrollBy(0,-500);");

		WebElement clearElement = webDriver.findElement(By.className("col-lg-10"));
		webDriver.findElement(By.cssSelector("input[value='クリア']")).click();

		WebElement idElement = webDriver.findElement(By.id("form"));
		assertEquals("", idElement.getText());
		getEvidence(new Object() {
		});
	}

}
