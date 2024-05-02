import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringCalculator {
	final String NORMAL_DELIMETER = ",|\\n";

	public int add(String integers) throws Exception {
		
		if (integers.isEmpty()) {
			return 0;
		}
		
		if (isCustomDelimiter(integers)) {
			List<Integer> numbers = getCustomDelimetedNumbers(integers);
			checkForNegativeNumbers(numbers);
			numbers = filterNumbersGreaterThanOneThousand(numbers);
			return sumOfNumbers(numbers);			
		} else {
			List<Integer> numbers = getNumbersToSum(integers);
			checkForNegativeNumbers(numbers);
			numbers = filterNumbersGreaterThanOneThousand(numbers);
			return sumOfNumbers(numbers);				
		}
	}

	private List<Integer> filterNumbersGreaterThanOneThousand(List<Integer> numbers) {
		return numbers.stream().filter(n -> n<1000).collect(Collectors.toList());
	}
	
	private void checkForNegativeNumbers(List<Integer> numbers) throws NegativesNotAllowedException {
		List<Integer> negatives = numbers.stream().filter(e -> e < 0).toList();
		if (!negatives.isEmpty()) {
			throw new NegativesNotAllowedException("Negatives not allowed: " + negatives.stream().map(String::valueOf).collect(Collectors.toSet()));
		}
	}
	
	private boolean isCustomDelimiter(String integers) {
		return integers.startsWith("//");
	}
	
	private List<Integer> getCustomDelimetedNumbers(String integers) {
		Matcher matcher = Pattern.compile("//(.)\n*(.*)").matcher(integers);
		matcher.matches();
		String customDelimeter = matcher.group(1);
		String numbers = matcher.group(2);
		return Arrays.asList(numbers.split(customDelimeter)).stream().map(Integer::valueOf).collect(Collectors.toList());
	}
	
	private List<Integer> getNumbersToSum(String integers) {
		return Arrays.asList(integers.split(NORMAL_DELIMETER)).stream().map(Integer::valueOf).collect(Collectors.toList());
	}

	private Integer sumOfNumbers(List<Integer> integers) {
		return integers.stream()
				  .collect(Collectors.summingInt(Integer::intValue));			
		
	}
}
