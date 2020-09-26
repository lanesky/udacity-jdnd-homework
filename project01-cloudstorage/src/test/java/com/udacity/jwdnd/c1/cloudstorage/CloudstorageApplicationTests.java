package com.udacity.jwdnd.c1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;

//import com.udacity.jwdnd.c1.review.model.ChatMessage;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudstorageApplicationTests {

	@LocalServerPort
	public int port;

	public static WebDriver driver;

	public String baseURL;

	@BeforeAll
	public static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();

	}

	@AfterAll
	public static void afterAll() {
		driver.quit();
		driver = null;
	}

	@BeforeEach
	public void beforeEach() {
		baseURL = baseURL = "http://localhost:" + port;
	}


	// verifies that the home page is not accessible without logging in.
	@Test
	public void testHomeNeedLogin() {
		driver.get(baseURL + "/home");
		HomePage homePage = new HomePage(driver);
		assertEquals(false, homePage.logoutButtonExisted());

	}

	// signs up a new user, logs that user in, verifies that they can access the home page,
	// then logs out and verifies that the home page is no longer accessible.
	@Test
	public void testLogout() {
		signup();
		login();
		logout();
	}

	// logs in an existing user, creates a note and verifies that the note details are visible in the note list.
	@Test
	public void testCreateNote() throws InterruptedException {
		signup();
		login();

		String nodeTitle = "testnodetitle";
		String nodeDescription = "testnodedesription";

		driver.get(baseURL + "/home");
		HomePage homePage = new HomePage(driver);

		homePage.addNote(nodeTitle, nodeDescription);

		driver.get(baseURL + "/home");
		homePage = new HomePage(driver);
		assertEquals(true, homePage.existsNote(nodeTitle, nodeDescription));
		logout();

	}

	// logs in an existing user with existing notes, clicks the edit note button on an existing note,
	// changes the note data, saves the changes, and verifies that the changes appear in the note list.
	@Test
	public void testEditNote() throws InterruptedException {
		signup();
		login();

		String nodeTitle = "aaaaaaaaaa";
		String nodeDescription = "vvvvvvvvvvvvvvvv";

		String nodeTitleNew = "bbbbbbb";
		String nodeDescriptionNew = "ccccccccc";

		driver.get(baseURL + "/home");
		HomePage homePage = new HomePage(driver);

		homePage.addNote(nodeTitle, nodeDescription);

		driver.get(baseURL + "/home");
		homePage = new HomePage(driver);
		assertEquals(true, homePage.existsNote(nodeTitle, nodeDescription));

		driver.get(baseURL + "/home");
		homePage = new HomePage(driver);
		homePage.editNote(nodeTitle, nodeDescription, nodeTitleNew, nodeDescriptionNew);

		driver.get(baseURL + "/home");
		homePage = new HomePage(driver);
		assertEquals(true, homePage.existsNote(nodeTitleNew, nodeDescriptionNew));

		logout();

	}

	// logs in an existing user with existing notes, clicks the delete note button on an existing note,
	// and verifies that the note no longer appears in the note list.
	@Test
	public void testDeleteNote() throws InterruptedException {
		signup();
		login();

		String nodeTitle = "testDeleteNote";
		String nodeDescription = "testDeleteNotenodeDescription";

		driver.get(baseURL + "/home");
		HomePage homePage = new HomePage(driver);
		homePage.addNote(nodeTitle, nodeDescription);

		driver.get(baseURL + "/home");
		driver.navigate().refresh();
//		homePage = new HomePage(driver);
		homePage.deleteNote(nodeTitle, nodeDescription);


		driver.get(baseURL + "/home");
		homePage = new HomePage(driver);
		assertEquals(false, homePage.existsNote(nodeTitle, nodeDescription));

		logout();

	}

	// logs in an existing user, creates a credential and verifies that
	// the credential details are visible in the credential list.
	@Test
	public void testCreateCreadential() throws InterruptedException {
		signup();
		login();

		String url = "google.com";
		String username = "a@google.com";
		String password = "13424234234234";

		driver.get(baseURL + "/home");
		HomePage homePage = new HomePage(driver);

		homePage.addCredential(url, username,password);

		driver.get(baseURL + "/home");
		homePage = new HomePage(driver);
		assertEquals(true, homePage.existsCredential(url, username));
		logout();

	}


	// logs in an existing user with existing credentials, clicks the edit credential button
	// on an existing credential, changes the credential data, saves the changes, and verifies that the changes appear in the credential list.
	@Test
	public void testEditCreadential() throws InterruptedException {
		signup();
		login();

		String url = "facebook.com";
		String username = "a@facebook.com";
		String password = "aaaaaaaaaa";

		String urlNew = "facebooknew.com";
		String usernameNew = "abbbbbbbbbb@facebook.com";

		driver.get(baseURL + "/home");
		HomePage homePage = new HomePage(driver);

		homePage.addCredential(url, username,password);

		driver.get(baseURL + "/home");
		homePage = new HomePage(driver);
		assertEquals(true, homePage.existsCredential(url, username));

		driver.get(baseURL + "/home");
		homePage = new HomePage(driver);
		homePage.editCredential(url, username, urlNew, usernameNew);

		driver.get(baseURL + "/home");
		homePage = new HomePage(driver);
		assertEquals(true, homePage.existsCredential(urlNew, usernameNew));

		logout();

	}

	// logs in an existing user with existing credentials, clicks the delete credential button
	// on an existing credential, and verifies that the credential no longer appears in the credential list.
	@Test
	public void testDeleteCreadential() throws InterruptedException {
		signup();
		login();

		String url = "test.com";
		String username = "a@test.com";
		String password = "eeeeeee";

		driver.get(baseURL + "/home");
		HomePage homePage = new HomePage(driver);

		homePage.addCredential(url, username,password);

		driver.get(baseURL + "/home");
		homePage = new HomePage(driver);
		assertEquals(true, homePage.existsCredential(url, username));

		driver.get(baseURL + "/home");
		homePage = new HomePage(driver);
		homePage.deleteCredential(url, username);

		driver.get(baseURL + "/home");
		homePage = new HomePage(driver);
		assertEquals(false, homePage.existsCredential(url, username));

		logout();

	}

	private void signup() {
		String username = "pzastoup";
		String password = "whatabadpassword";

		driver.get(baseURL + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Peter", "Zastoupil", username, password);
}

	private void login() {
		String username = "pzastoup";
		String password = "whatabadpassword";

		driver.get(baseURL + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
	}

	private void logout() {

		driver.get(baseURL + "/home");
		HomePage homePage = new HomePage(driver);
		assertEquals(true, homePage.logoutButtonExisted());

		homePage.logout();
		homePage = new HomePage(driver);
		assertEquals(false, homePage.logoutButtonExisted());
	}

}
