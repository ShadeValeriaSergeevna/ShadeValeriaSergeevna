import io.qameta.allure.Flaky;
import io.qameta.allure.Owner;
import lombok.SneakyThrows;
import ru.shade.ui.BaseClass;
import ru.shade.ui.pages.RegistrationPage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static ru.shade.ui.constants.ContactsPageConstants.CONTACTS_PAGE_NAME;
import static ru.shade.ui.constants.ContactsPageConstants.MAP_OF_CONTACTS;
import static ru.shade.ui.constants.FilesPageConstants.FILES_PAGE_NAME;
import static ru.shade.ui.constants.MainPageConstants.MAIN_PAGE_HEADNAME;
import static ru.shade.ui.constants.MainPageConstants.MAIN_PAGE_NAME;
import static ru.shade.ui.constants.RegistrationPageConstants.REGISTRATION_PAGE_NAME;
import static ru.shade.ui.constants.TextPageConstants.TEXT_PAGE_NAME;
import static ru.shade.ui.constants.TextPageConstants.TYPES_OF_TESTING_ARRAY;
import static ru.shade.ui.PageManager.*;

@Owner("Shade Valeria")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Execution(ExecutionMode.CONCURRENT)
@Tag("Shade.QA")
public class UITest extends BaseClass {
    @Test
    @Order(0)
    @DisplayName("Check MainPage")
    public void checkMainPage() {
        mainPage.open()
                .checkHeadName(MAIN_PAGE_HEADNAME)
                .checkQuantityPages(5);

        mainPage.checkPageNames(MAIN_PAGE_NAME);
        mainPage.checkPageNames(REGISTRATION_PAGE_NAME);
        mainPage.checkPageNames(FILES_PAGE_NAME);
        mainPage.checkPageNames(TEXT_PAGE_NAME);
        mainPage.checkPageNames(CONTACTS_PAGE_NAME);
    }

    @ParameterizedTest
    @ArgumentsSource(RegistrationPage.class)
    @Order(1)
    @DisplayName("Check Registration with different users")
    public void checkRegistrationPage(String name, String surname, String comment) {
        mainPage.open().clickPage(REGISTRATION_PAGE_NAME);

        registrationPage.signUp(name, surname, comment);
        registrationPage.checkSuccessfulRegistration();
    }

    @Test
    @Order(2)
    @SneakyThrows
    @DisplayName("Check FilesPage")
    public void checkFilesPage() {
        mainPage.open().clickPage(FILES_PAGE_NAME);

        filesPage.deleteAllFiles();
        filesPage.downloadFiles();
        filesPage.checkFileTXT();
        filesPage.checkFilePNG();
        filesPage.checkFileEXCEL();
    }

    @Test
    @Order(3)
    @DisplayName("Check TextPage")
    public void checkTextPage() {
        mainPage.open().clickPage(TEXT_PAGE_NAME);

        textPage.checkTypesOfTesting(TYPES_OF_TESTING_ARRAY);
    }

    @Test
    @Order(4)
    @DisplayName("Check ContactsPage")
    @Flaky
    public void checkContactsPage() {
        mainPage.open().clickPage(CONTACTS_PAGE_NAME);

        contactsPage.checkContacts(MAP_OF_CONTACTS);
    }
}