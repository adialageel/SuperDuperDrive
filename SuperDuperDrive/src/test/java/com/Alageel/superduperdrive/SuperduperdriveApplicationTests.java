package com.Alageel.superduperdrive;

import com.Alageel.superduperdrive.mapper.CredentialsMapper;
import com.Alageel.superduperdrive.model.Credentials;
import com.Alageel.superduperdrive.page.*;
import com.Alageel.superduperdrive.services.EncryptionService;
import com.Alageel.superduperdrive.page.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SuperduperdriveApplicationTests {
    @LocalServerPort
    private int port;
    private WebDriver driver;
    private WebDriverWait wait;
    public String BASE_URL;
    @Autowired
    CredentialsMapper credentialsMapper;
    @Autowired
    EncryptionService encryptionService;
    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }
    @BeforeEach
    public void beforeEach() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 15);
        BASE_URL = "http://localhost:" + port;
    }
    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }
    @Test
    public void getLoginPage() {
        driver.get(BASE_URL + "/login");
        assertEquals("Login", driver.getTitle());
    }
    @Test
    public void testSignUpAndLogin() {
        signupAndLoginProcess();
        assertEquals(BASE_URL + "/home", driver.getCurrentUrl());
        HomePage homePage = new HomePage(driver, wait);
        homePage.logout();
        wait.until(ExpectedConditions.urlContains(BASE_URL + "/login"));
        assertNotEquals(BASE_URL + "/home", driver.getCurrentUrl());
    }
    @Test
    public void NoteCreation() {
        // Sign up and login user
        signupAndLoginProcess();
        // Create notes for testing
        String nTitle = "New Note";
        String nDescription = "Testing selenium";
        //check loading
        wait.until(ExpectedConditions.titleContains("Home"));
        Assertions.assertEquals("Home", driver.getTitle());
        NotePage notePage = new NotePage(driver, wait);
        notePage.createNote(nTitle, nDescription);
        //Now check the notes
        assertEquals(nTitle, notePage.getDisplayedNoteTitle());
        assertEquals(nDescription, notePage.getDisplayedNoteDescription());
        // Create the description and title
        String editedNoteTitle = "New Note2";
        String editedNoteDescription = "Testing selenium overall";

        // Verify that edited note is displayed correctly
        notePage.editNote(editedNoteTitle, editedNoteDescription);
        assertEquals(editedNoteTitle, notePage.getDisplayedNoteTitle());
        assertEquals(editedNoteDescription, notePage.getDisplayedNoteDescription());

        // Delete note
        notePage.deleteNote();
        assertNotEquals(true, notePage.isNoteAvailable());

    }




    @Test
    public void credentials() {
        //Sign up and login user
        signupAndLoginProcess();

        // credential details
        String url = "www.Adi.com";
        String credentialUsername = "Adi";
        String credentialPassword = "eng.adi";


        //verify that home page is loaded
        wait.until(ExpectedConditions.titleContains("Home"));
        Assertions.assertEquals("Home", driver.getTitle());

        // create credential with credential details
        CredentialPage credentialPage = new CredentialPage(driver, wait);
        credentialPage.createCredentials(url, credentialUsername, credentialPassword);

        // Get saved credential details
        Credentials credentials = credentialsMapper.getCredentialsByCredId(credentialPage.getCredentialId());
        assertEquals(credentials.getPassword(), credentialPage.getDisplayedCredentialPassword());
        // check the encryption
        String decryptedPassword = encryptionService.decryptValue(credentialPage.getDisplayedCredentialPassword(), credentials.getKey());
        assertEquals(credentialPassword, decryptedPassword);
        // Now use .viewCredentials to see the credentials
        credentialPage.viewCredentials();
        String viewedModalPassword = credentialPage.getModalCredentialPassword();
        // making sure the credentials aren't encrypted
        assertEquals(credentialPassword, viewedModalPassword);
        // Now we will create the cred values
        String editedUrl = "www.Adi.edu.ng";
        String editedCredUsername = "Adi";
        String editedCredPassword = "eng.adi2";

        credentialPage.editCredentials(editedUrl, editedCredUsername, editedCredPassword);

        // Store the credentials data
        Credentials editedCredentials = credentialsMapper.getCredentialsByCredId(credentialPage.getCredentialId());

        //Checking that the showed data similar to the stored
        assertEquals(editedCredentials.getPassword(), credentialPage.getDisplayedCredentialPassword());
        String editedDecryptedPassword = encryptionService.decryptValue(credentialPage.getDisplayedCredentialPassword(), editedCredentials.getKey());
        assertEquals(editedCredPassword, editedDecryptedPassword);
        assertEquals(editedCredUsername, credentialPage.getDisplayedCredentialUsername());
        assertEquals(editedUrl, credentialPage.getDisplayedCredentialUrl());
        credentialPage.deleteCredential();
        assertNotEquals(true, credentialPage.isCredentialAvailable());

    }

    /**
     * Verify that unauthorized users cant access other page except the login and signup page.
     */

    @Test
    public void unAuthorizedUsers() {
        driver.get(BASE_URL + "/home");
        assertNotEquals(BASE_URL + "/home", driver.getCurrentUrl());
        assertEquals(BASE_URL + "/login", driver.getCurrentUrl());
        driver.get(BASE_URL + "/signup");
        assertEquals(BASE_URL + "/signup", driver.getCurrentUrl());
        driver.get(BASE_URL + "/login");
        assertEquals(BASE_URL + "/login", driver.getCurrentUrl());
    }

    /**
     * Sign up and login user
     */
    private void signupAndLoginProcess() {
        String username = "alageel";
        String password = "eng$alageel";
        String lastName = "alhamad";
        String firstName = "adi";
        driver.get(BASE_URL + "/signup");
        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.signUp(firstName, lastName, username, password);
        driver.get(BASE_URL + "/loginup");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);
    }

}
