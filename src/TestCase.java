import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.ByteArrayOutputStream;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.IOException;  
import java.io.InputStream;
import java.math.BigDecimal;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;  
import org.apache.poi.xssf.usermodel.XSSFRow;  
import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestCase {
  public String format (String s) {
	  if (s.endsWith("/"))
    	  s = s.substring(0, s.length()-1);
      return s;
  }

  private WebDriver driver;

  @Before
  public void setUp() throws Exception {
	  // 设置相应的驱动，路径在本地
	  System.setProperty("webdriver.gecko.driver", "D:/mycodes/java/workspace/selenium/geckodriver.exe");
      driver = new FirefoxDriver();
  }

  @Test
  public void testTestCase() throws Exception {
	  XSSFWorkbook xssfWorkbook = null; 
      try { 
          xssfWorkbook = new XSSFWorkbook("D:/各种资料/学习资料/大三/软件测试课件/input.xlsx"); 
      } catch (IOException e1) { 
          // TODO Auto-generated catch block 
          e1.printStackTrace(); 
      }
      for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
    	  XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
    	  for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
    		  String number, addr;
    		  XSSFRow xssfRow = xssfSheet.getRow(rowNum); 
    		  XSSFCell stNumber = xssfRow.getCell(0); 
              XSSFCell githubAddr = xssfRow.getCell(1);
              if (stNumber.getCellTypeEnum().equals(CellType.NUMERIC)) {
            	  number = new BigDecimal(stNumber.getNumericCellValue()).toString();
            	  System.out.print(number + " ");
              } else {
            	  number = stNumber.toString();
            	  System.out.print(number + " ");
              }
              addr = githubAddr.toString().trim();
              if (addr == null || addr == "")
            	  continue;
              System.out.print(addr + "\n");
              // 进行webDriver测试
              driver.get("https://psych.liebes.top/st");
              driver.findElement(By.id("username")).click();
              driver.findElement(By.id("username")).clear();
              driver.findElement(By.id("username")).sendKeys(number);
              driver.findElement(By.id("password")).clear();
              driver.findElement(By.id("password")).sendKeys(number.substring(4));
              driver.findElement(By.id("submitButton")).click();
              String stURL = driver.findElement(By.xpath("//p")).getText();
              assertEquals(format(stURL), format(addr));
    	  }
      }
  }
}
