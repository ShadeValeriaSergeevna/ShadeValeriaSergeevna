package ru.shade.ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.cucumber.java.en.Given;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Selenide.$x;
import static org.junit.Assert.*;
import static ru.shade.ui.constants.FilesPageConstants.*;
import static ru.shade.ui.PropertiesProvider.PATH_FILES;
import static org.apache.commons.io.FileUtils.readFileToString;
import static org.apache.commons.io.FileUtils.sizeOf;

public class FilesPage {
    private final String downloadFiles = "//a[@class='t1075__link' ]//*[contains(text(), '";
    private final SelenideElement downloadFilePNG = $x(downloadFiles + FILES_PAGE_PNG + "')]//ancestor::a");
    private final SelenideElement downloadFileTXT = $x(downloadFiles + FILES_PAGE_TXT + "')]//ancestor::a");
    private final SelenideElement downloadFileExcel = $x(downloadFiles + FILES_PAGE_EXCEL + "')]//ancestor::a");

    private String downloadFilePNGPath;
    private String downloadFileTXTPath;
    private String downloadFileExcelPath;

    @Given("Download file {string}")
    public void downloadFiles() {
        downloadFilePNGPath = downloadFilePNG.download().getAbsolutePath();
        downloadFileTXTPath = downloadFileTXT.download().getAbsolutePath();
        downloadFileExcelPath = downloadFileExcel.download().getAbsolutePath();

        System.out.println(downloadFilePNGPath);
    }

    @Given("Delete all files")
    public void deleteAllFiles() throws IOException {
        FileUtils.deleteDirectory(new File(PATH_FILES));
    }

    @Given("Check txt file")
    public void checkFileTXT() throws IOException {
        assertEquals(readFileToString(new File(FILE_TXT_EXPECTED_PATH), StandardCharsets.UTF_8),
                readFileToString(new File(downloadFileTXTPath), StandardCharsets.UTF_8));
    }

    @Given("Check png file")
    public void checkFilePNG() {
        assertTrue(sizeOf(new File(downloadFilePNGPath)) > 0);
    }

    @Given("Check excel file")
    public void checkFileEXCEL() throws Exception {
        XSSFWorkbook xlsxBook = new XSSFWorkbook(downloadFileExcelPath);
        XSSFSheet sheet = xlsxBook.getSheetAt(0);

        assertEquals(FILE_EXCEL_RESULT,
                getCellData(sheet, 0, 0));
    }

    public static String getCellData(XSSFSheet sheet, int rowNum, int colNum) throws Exception {
        try {
            XSSFCell cell = sheet.getRow(rowNum).getCell(colNum);
            return cell.getStringCellValue();
        } catch (Exception e) {
            return "Error";
        }
    }
}