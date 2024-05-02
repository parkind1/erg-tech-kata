import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class StringCalculatorTest {
	StringCalculator stringCalculator = new StringCalculator();
	
	@Test
	void testThatAddWithEmptyStringReturnsZero() throws Exception {
		assertEquals(0, stringCalculator.add(""));
	}
	
	@Test
	void testThatAddWithSingle1Returns1() throws Exception {
		assertEquals(1, stringCalculator.add("1"));
	}
	
	@Test
	void testThatAddWithOneAndTwoReturns3() throws Exception {
		assertEquals(3, stringCalculator.add("1,2"));
	}
	
	@Test
	void testThatOneTwoThreeFourReturnsTen() throws Exception {
		assertEquals(10, stringCalculator.add("1,2,3,4"));
	}
	
	@Test
	void testThatANewLineIsNowAllowedToSeperateNumbers() throws Exception {
		assertEquals(6, stringCalculator.add("1\n2,3"));
	}
	
	@Test
	void testThatACustomDelimiterIsAccepted() throws Exception {
		assertEquals(3, stringCalculator.add("//;\n1;2"));
	}
	
	@Test
	void testThatNegativeNumbersThrowAnException() throws Exception {
		try {
			stringCalculator.add("-1,2");
			fail();
		}
		catch (NegativesNotAllowedException nnae) {
			assertTrue(nnae.getMessage().contains("Negatives not allowed:"));
			assertTrue(nnae.getMessage().contains("-1"));
		}
	}
	
	@Test
	void testThatNegativeNumbersAreAllListedInException() throws Exception {
		try {
			stringCalculator.add("2,-4,3,-5");
			fail();
		}
		catch (NegativesNotAllowedException nnae) {
			System.out.println(nnae.getMessage());
			assertTrue(nnae.getMessage().contains("Negatives not allowed:"));
			assertTrue(nnae.getMessage().contains("-4, -5"));
		}
	}
	
	@Test
	void testThatNumbersGreaterThanOneThousandAreExcluded() throws Exception {
		assertEquals(2, stringCalculator.add("1001,2"));
	}
	
	@Test
	void testThatDelimetersCanBeAnyLength() throws Exception {
		assertEquals(6, stringCalculator.add("//[|||]\n1|||2|||3"));
	}
}
