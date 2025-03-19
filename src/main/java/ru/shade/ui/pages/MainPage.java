package ru.shade.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.cucumber.java.en.Given;
import ru.shade.ui.PropertiesProvider;

import static com.codeborne.selenide.Condition.exactOwnText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static org.junit.Assert.assertEquals;

public class MainPage {
    private final ElementsCollection pageNames = $$x("//a[contains(@class, 't453__item_link t-menu__link-item')]");

    private final SelenideElement headName = $x("//*[text()='Shade QA/Test project of Shade Valeria']");

    private final ElementsCollection headNames = $$x("//li[@class='t453__item']/*");

    @Given("Open site")
    public MainPage open() {
        Selenide.open(PropertiesProvider.BASE_URL);
        return this;
    }

    @Given("Check head")
    public MainPage checkHeadName(String headNameResult) {
        assertEquals(headNameResult, headName.getOwnText());
        return this;
    }

    @Given("Check page names")
    public void checkPageNames(String headNameResult) {
        assertEquals(headNameResult, headNames.findBy(text(headNameResult)).getText());
    }

    @Given("Check q-ty pages {int}")
    public MainPage checkQuantityPages(int quantityPages) {
        assertEquals(quantityPages, pageNames.size());
        return this;
    }

    @Given("Click page {string}")
    public MainPage clickPage(String pageName) {
        pageNames.findBy(exactOwnText(pageName)).click();
        return this;
    }
}