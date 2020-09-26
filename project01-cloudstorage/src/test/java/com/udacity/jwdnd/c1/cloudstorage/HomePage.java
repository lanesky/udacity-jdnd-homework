package com.udacity.jwdnd.c1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HomePage {

    @FindBy(css="#logout-button")
    private WebElement logoutButton;

    private final WebDriver webDriver;

    /*
    NOTE ELEMENTS
    */
    @FindBy(css="#nav-notes-tab")
    private WebElement navNotesTab;

    @FindBy(css="#show-add-note-model")
    private WebElement showAddNoteModelButton;

    @FindBy(css="#note-title")
    private WebElement noteTitleFieldOnModal;

    @FindBy(css="#note-description")
    private WebElement noteDescriptionFieldOnModal;

    @FindBy(css="#note-submit-button")
    private WebElement noteSubmitButtonOnModal;

    @FindBy(css="#nav-notes")
    private WebElement navNotesDiv;

    /*
    CREDENTIAL ELEMENTS
    */
    @FindBy(css="#nav-credentials-tab")
    private WebElement navCredentialsTab;

    @FindBy(css="#show-add-credential-model")
    private WebElement showAddCredentialModelButton;

    @FindBy(css="#credential-url")
    private WebElement credentialUrlFieldOnModal;

    @FindBy(css="#credential-username")
    private WebElement credentialusernameFieldOnModal;

    @FindBy(css="#credential-password")
    private WebElement credentialpasswordFieldOnModal;

    @FindBy(css="#credential-submit-button")
    private WebElement credentialSubmitButtonOnModal;

    @FindBy(css="#nav-credentials")
    private WebElement navCredentialsDiv;

    public HomePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public boolean logoutButtonExisted() {
        return (!webDriver.findElements(By.id("logout-button")).isEmpty());
    }

    public void logout() {
        this.logoutButton.click();
    }

    /*
    * Browser operations fro notes
    * */

    public void addNote(String noteTitle, String noteDescription) throws InterruptedException {

        this.navNotesTab.click();
        Thread.sleep(2000);

        this.showAddNoteModelButton.click();
        Thread.sleep(2000);

        this.noteTitleFieldOnModal.sendKeys(noteTitle);
        this.noteDescriptionFieldOnModal.sendKeys(noteDescription);
        Thread.sleep(2000);

        this.noteSubmitButtonOnModal.click();
        Thread.sleep(2000);
    }

    public boolean existsNote(String noteTitle, String noteDescription) throws InterruptedException {
        this.navNotesTab.click();
        Thread.sleep(2000);

        List<WebElement> rows = this.navNotesDiv.findElements(By.className("data-row"));

        for ( WebElement c: rows) {
            String titleOnForm = c.findElement(By.className("data-note-title")).getText();
            String descrpitonOnForm = c.findElement(By.className("data-note-description")).getText();

            if (noteTitle.equals(titleOnForm) && noteDescription.equals(descrpitonOnForm)) return true;
        }

        return false;
    }

    public void editNote(String noteTitle, String noteDescription,
                              String noteTitleNew, String noteDescriptionNew) throws InterruptedException {
        this.navNotesTab.click();
        Thread.sleep(2000);

        List<WebElement> rows = this.navNotesDiv.findElements(By.className("data-row"));

        for ( WebElement c: rows) {
            WebElement titleOnForm = c.findElement(By.className("data-note-title"));
            WebElement descrpitonOnForm = c.findElement(By.className("data-note-description"));

            if (noteTitle.equals(titleOnForm.getText()) && noteDescription.equals(descrpitonOnForm.getText())) {

                WebElement editButton = c.findElement(By.className("note-show-modal"));
                editButton.click();
                Thread.sleep(2000);

                this.noteTitleFieldOnModal.clear();
                this.noteDescriptionFieldOnModal.clear();
                Thread.sleep(2000);

                this.noteTitleFieldOnModal.sendKeys(noteTitleNew);
                this.noteDescriptionFieldOnModal.sendKeys(noteDescriptionNew);
                Thread.sleep(2000);

                this.noteSubmitButtonOnModal.click();
                Thread.sleep(2000);

                break;

            }
        }

    }

    public void deleteNote(String noteTitle, String noteDescription) throws InterruptedException {

        this.navNotesTab.click();
        Thread.sleep(2000);

        List<WebElement> rows = webDriver.findElement(By.id("nav-notes")).findElements(By.className("data-row"));

        for ( WebElement c: rows) {


            String titleOnForm = c.findElement(By.className("data-note-title")).getText();
            String descrpitonOnForm = c.findElement(By.className("data-note-description")).getText();
            WebElement deleteButton = c.findElement(By.className("delete-note-row"));

            if (noteTitle.equals(titleOnForm) && noteDescription.equals(descrpitonOnForm)) {
                deleteButton.click();

                /* break is necessary here, otherwise you may encounter an exception like,
                org.openqa.selenium.StaleElementReferenceException: stale element reference: element is not attached to the page document
                * */
                break;
            }
        }

    }

    /*
     * Browser operations fro notes
     * */

    public void addCredential(String url, String username, String password) throws InterruptedException {

        this.navCredentialsTab.click();
        Thread.sleep(2000);

        this.showAddCredentialModelButton.click();
        Thread.sleep(2000);

        this.credentialUrlFieldOnModal.sendKeys(url);
        this.credentialusernameFieldOnModal.sendKeys(username);
        this.credentialpasswordFieldOnModal.sendKeys(password);

        Thread.sleep(2000);

        this.credentialSubmitButtonOnModal.click();
        Thread.sleep(2000);
    }

    public boolean existsCredential(String url, String username) throws InterruptedException {
        this.navCredentialsTab.click();
        Thread.sleep(2000);

        List<WebElement> rows = this.navCredentialsDiv.findElements(By.className("data-row"));

        for ( WebElement c: rows) {
            String urlOnForm = c.findElement(By.className("data-credential-url")).getText();
            String usernameOnForm = c.findElement(By.className("data-credential-userName")).getText();

            if (url.equals(urlOnForm) && username.equals(usernameOnForm)) return true;
        }

        return false;
    }

    public void editCredential(String url, String username,
                               String urlNew, String usernameNew) throws InterruptedException {
        this.navCredentialsTab.click();
        Thread.sleep(2000);

        List<WebElement> rows = this.navCredentialsDiv.findElements(By.className("data-row"));

        for ( WebElement c: rows) {
            WebElement urlOnForm = c.findElement(By.className("data-credential-url"));
            WebElement usernameOnForm = c.findElement(By.className("data-credential-userName"));

            if (url.equals(urlOnForm.getText()) && username.equals(usernameOnForm.getText())) {

                WebElement editButton = c.findElement(By.className("credential-show-modal"));
                editButton.click();
                Thread.sleep(2000);

                this.credentialUrlFieldOnModal.clear();
                this.credentialusernameFieldOnModal.clear();
                Thread.sleep(2000);

                this.credentialUrlFieldOnModal.sendKeys(urlNew);
                this.credentialusernameFieldOnModal.sendKeys(usernameNew);
                Thread.sleep(2000);

                this.credentialSubmitButtonOnModal.click();
                Thread.sleep(2000);

                break;

            }
        }

    }

    public void deleteCredential(String url, String username) throws InterruptedException {
        this.navCredentialsTab.click();
        Thread.sleep(2000);

        List<WebElement> rows = this.navCredentialsDiv.findElements(By.className("data-row"));

        for ( WebElement c: rows) {
            WebElement urlOnForm = c.findElement(By.className("data-credential-url"));
            WebElement usernameOnForm = c.findElement(By.className("data-credential-userName"));

            if (url.equals(urlOnForm.getText()) && username.equals(usernameOnForm.getText())) {

                WebElement deleteButton = c.findElement(By.className("delete-credential-row"));
                deleteButton.click();
//                Thread.sleep(2000);

                break;

            }
        }

    }
}
