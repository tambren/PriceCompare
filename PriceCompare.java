import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.collect.Maps;

public class PriceCompare {


    static ChromeDriver driver;
    static HSSFWorkbook workbook; 
    static HashMap<Integer,Computer> map;
    static HSSFSheet sheet;

    public static void main(String... args) throws FileNotFoundException, IOException {

        init();
        acquireData(); 
        writeToExcel();
        teardown();

    }
    
    // *********************** init() **************************
    // initialize all objects and variables for application, setup Excel

    @SuppressWarnings("resource")
    public static void init() throws FileNotFoundException, IOException {

        System.setProperty("webdriver.chrome.driver","/Users/Brendon/Desktop/chromedriver");
        driver = new ChromeDriver();
        workbook = new HSSFWorkbook();
        map = new HashMap<Integer,Computer>();
        sheet =  workbook.createSheet("Basic");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Name");
        header.createCell(1).setCellValue("CPU");
        header.createCell(2).setCellValue("Monitor");
        header.createCell(3).setCellValue("Resolution");
        header.createCell(4).setCellValue("Price");

    }
    
    // *********************** acquireData() **************************
    // fetch the specs of all in one computers on the ecommerce website
    
    public static void acquireData () {

        driver.get("https://www.bhphotovideo.com");
        WebDriverWait wait = new WebDriverWait(driver, 10);

        driver.findElement(By.xpath("//*[@id=\"bh-app\"]/section/div/div[2]/section[1]/div/div/div[2]/a/span[1]")).click(); 
        
        wait.until(ExpectedConditions.elementToBeClickable(By.id("Desktops   Workstations") ) );
        driver.findElement(By.id("Desktops   Workstations")).click();
        
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"All In One Desktop Computers\"]/div") ) );
        driver.findElement(By.xpath("//*[@id=\"All In One Desktop Computers\"]/div")).click();


        // To increase amount of computers to store, increase conditional by increments of 2
        for (int i=1; i<=9; i+=2) {

            map.put(i, new Computer() );
            map.get(i).setBrand(driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div[2]/div[4]/div[" + i + "]/div[3]/div[1]/h5/a/span[1]")).getText());

            //include brand in name
            map.get(i).setName(map.get(i).getBrand() + driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div[2]/div[4]/div[" + i + "]/div[3]/div[1]/h5/a/span[2]")).getText() );
            map.get(i).setCpu(driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div[2]/div[4]/div[" + i + "]/div[3]/div[2]/div/div/ul/li[1]")).getText() );
            map.get(i).setRam(driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div[2]/div[4]/div[" + i + "]/div[3]/div[2]/div/div/ul/li[2]")).getText() );
            map.get(i).setMonitor(driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div[2]/div[4]/div[" + i + "]/div[3]/div[2]/div/div/ul/li[3]")).getText() );
            map.get(i).setResolution(driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div[2]/div[4]/div[" + i + "]/div[3]/div[2]/div/div/ul/li[4]")).getText() );
            map.get(i).setPrice(driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div[2]/div[4]/div[" + i + "]/div[4]/div/div[1]/div[2]/div/span")).getText() );
            
            System.out.println(map.get(i));      
            
        }

    }
    
    // *********************** writeToExcel() **************************
    // write all stored computers onto Excel spreadsheet

    public static void writeToExcel() {


        for(Integer i: map.keySet()) {
            Row row = sheet.createRow(i);
            row.createCell(0).setCellValue(map.get(i).getName());
            row.createCell(1).setCellValue(map.get(i).getCpu());
            row.createCell(2).setCellValue(map.get(i).getMonitor());
            row.createCell(3).setCellValue(map.get(i).getResolution());
            row.createCell(4).setCellValue(map.get(i).getPrice());    
            
        }

        try {
            FileOutputStream out =  new FileOutputStream(new File("PriceCompare.xls"));
            workbook.write(out);
            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    // *********************** teardown() **************************
    // close driver

    public static void teardown() {
        driver.close();

    }

}
