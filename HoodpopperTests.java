import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class HoodpopperTests {

	static WebDriver driver = new FirefoxDriver();
	
	@Before
	public void setUp() throws Exception {
		driver.get("http://lit-bayou-7912.herokuapp.com/");
	}
	
/*
 * As a Ruby Hoodpopper user
 *I want the tokenize to separate the code into tokens properly
 *So that my variables, operators, and spaces get identified correctly.
 * 
 */
	
	// Given the code is “a + b = 2”, 
	//when tokenizing the variables “a”, 
	//then it is identified as “:on_ident”.
	@Test
	public void tokenOnIndent () {
		driver.findElement(By.id("code_code")).sendKeys("a + b = 2");
		driver.findElement(By.xpath("(//input[@name='commit'])[1]")).click();
		String bodyText = driver.findElement(By.tagName("body")).getText();
		assertTrue(bodyText.contains(":on_ident, \"a\"]"));
	}
	
	//Given the code is “a + b = 2”, 
	//when tokenizing the operators “+”, 
	//then it is identified as “:on_op”.
	@Test
	public void tokenOnOp () {
		driver.findElement(By.id("code_code")).sendKeys("a + b = 2");
		driver.findElement(By.xpath("(//input[@name='commit'])[1]")).click();
		String bodyText = driver.findElement(By.tagName("body")).getText();
		assertTrue(bodyText.contains(":on_op, \"+\"]"));
	}
	
	// Given the code is “a + b = 2’, 
	//when tokenizing all spaces, 
	//then they are identified as “:on_sp”
	@Test
	public void tokenOnSp () {
		driver.findElement(By.id("code_code")).sendKeys("a + b = 2");
		driver.findElement(By.xpath("(//input[@name='commit'])[1]")).click();
		String bodyText = driver.findElement(By.tagName("body")).getText();
		assertTrue(bodyText.contains(":on_sp, \" \"]"));
	}
	
	//Given the code is “a&&b=true”, 
	//when tokenizing no spaces, 
	//then nothing gets identified as :on_sp
	@Test
	public void tokenNoSpaces () {
		driver.findElement(By.id("code_code")).sendKeys("a&&b=true");
		driver.findElement(By.xpath("(//input[@name='commit'])[1]")).click();
		String bodyText = driver.findElement(By.tagName("body")).getText();
		assertFalse(bodyText.contains(":on_sp"));
	}
	
	// When clicking the tokenize button, 
	// then it properly shows the tokenize operation and no other page (like compile or parse page)
	@Test
	public void tokenCorrectPage () {
		driver.findElement(By.xpath("(//input[@name='commit'])[1]")).click();
		String bodyText = driver.findElement(By.tagName("body")).getText();
		assertTrue(bodyText.contains("Hood Popped - Tokenize Operation"));
		
	}
	
/* 
 * As a Ruby Hoodpopper user
 * I want the parse button to parse the tokens properly
 * So that any non-whitespace tokens are shown in the AST and any whitespace tokens do not appear in the AST.
 * 
 */
	
	//Given the code is “a + b = 2”, 
	//when parsing the variable “b”, 
	//then it has been tokenized and should appear in the AST
	@Test
	public void parseVariableB () {
		driver.findElement(By.id("code_code")).sendKeys("a + b = 2");
		driver.findElement(By.xpath("(//input[@name='commit'])[2]")).click();
		String bodyText = driver.findElement(By.tagName("body")).getText();
		assertTrue(bodyText.contains("b"));
	}
	
	
	// When clicking the parse button, 
	// then it properly shows the parse operation and no other pages (like compile or tokenize page)
	@Test
	public void parseCorrectPage () {
		driver.findElement(By.xpath("(//input[@name='commit'])[2]")).click();
		String bodyText = driver.findElement(By.tagName("body")).getText();
		assertTrue(bodyText.contains("Hood Popped - Parse Operation"));
	}
	
	
/* 
 * As a Ruby Hoodpopper user
 *I want the compile button to go through the AST and write machine-level instructions to an executable
 *So that the written code is compiled correctly in Ruby. 
 *
 */
	
	//Given the code is ‘puts = “I love writing Ruby” ‘, 
	//when the code compiles, 
	//then the program has “putstring” YARV operation
	@Test
	public void putStringShown () {
		driver.findElement(By.id("code_code")).sendKeys("puts = \"I love writing Ruby\"");
		driver.findElement(By.xpath("(//input[@name='commit'])[3]")).click();
		String bodyText = driver.findElement(By.tagName("body")).getText();
		assertTrue(bodyText.contains("putstring"));
	}
	
	//Given the code that is “a + b = 2”, 
	//when the code compiles, 
	//then it should call the “opt_plus” operation
	@Test
	public void optPlusShown () {
		driver.findElement(By.id("code_code")).sendKeys("a + b = 2");
		driver.findElement(By.xpath("(//input[@name='commit'])[3]")).click();
		String bodyText = driver.findElement(By.tagName("body")).getText();
		assertTrue(bodyText.contains("opt_plus"));
	}
	
	//Given the code that is “a – b = 2”, 
	//when the code compiles, 
	//then it should call the “opt_minus” operation
	@Test
	public void optMinusShown () {
		driver.findElement(By.id("code_code")).sendKeys("a - b = 2");
		driver.findElement(By.xpath("(//input[@name='commit'])[3]")).click();
		String bodyText = driver.findElement(By.tagName("body")).getText();
		assertTrue(bodyText.contains("opt_minus"));
	}
	
	// When clicking the compile button, 
	// then it properly shows the compile operation and no other pages (like parse or tokenize page)
	@Test
	public void compileCorrectPage () {
		driver.findElement(By.xpath("(//input[@name='commit'])[3]")).click();
		String bodyText = driver.findElement(By.tagName("body")).getText();
		assertTrue(bodyText.contains("Hood Popped - Compile Operation"));
	}
	
	
}
