package ru.shade.ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.cucumber.java.en.Given;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.*;
import static ru.shade.ui.PropertiesProvider.*;

public class RegistrationPage implements ArgumentsProvider {
    private final SelenideElement fieldName = $x("//input[@placeholder='Name']");
    private final SelenideElement fieldSurname = $x("//input[@placeholder='Surname']");
    private final SelenideElement fieldComment = $x("//input[@placeholder='Comment']");
    private final SelenideElement buttonSend = $x("//button[@type='submit']");
    private final SelenideElement successRegistationText = $x("//div[text()='Success Registration']");

    @Given("Fill fields for registration")
    public void signUp(String name, String surname, String comment) {
        fieldName.sendKeys(name);
        fieldSurname.sendKeys(surname);
        fieldComment.sendKeys(comment);
        buttonSend.click();
    }

    @Given("Check success registration")
    public void checkSuccessfulRegistration() {
        successRegistationText.should(exist);
        closeWindow();
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        return Stream.of(
                Arguments.of(REGISTRATION_NAME, REGISTRATION_SURNAME, REGISTRATION_COMMENT),
                Arguments.of(REGISTRATION_NAME_TEST, REGISTRATION_SURNAME_TEST, REGISTRATION_COMMENT));
    }
}