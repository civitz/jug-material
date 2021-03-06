package jug.streams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.IntSummaryStatistics;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.assertj.core.data.Offset;
import org.junit.Test;

public class IntStreamTest {
	
	@Test
	public void shouldGetRightStatistics() throws Exception {
		/*
		 * For primitive types you can get statistics for free
		 */
		IntSummaryStatistics statistics = IntStream.range(1, 101)
			.summaryStatistics();
		
		assertThat(statistics.getSum()).isEqualTo(5050L);
		assertThat(statistics.getMax()).isEqualTo(100);
		assertThat(statistics.getMin()).isEqualTo(1);
		assertThat(statistics.getAverage()).isEqualTo(50.50, Offset.offset(0.05d));
	}
	
	@Test
	public void shouldGenerateWithIterator() throws Exception {
		// iteratively build infinite stream
		String result = IntStream.iterate(5, x -> x + 5)
			.limit(5) // limit size
			.boxed()  // turn to Integer (primitive => objects)
			.map(Object::toString) // convert to string
			.collect(Collectors.joining(",")); // join
		
		assertThat(result).isEqualTo("5,10,15,20,25");
	}
	
	@Test
	public void sum_shouldGetCorrectSum() throws Exception {
		int sum = IntStream.range(1, 101)
			.sum();
		
		assertThat(sum).isEqualByComparingTo(5050);
	}
	
	@Test
	public void flatMap_shouldGenerateCorrectNumberOfElements() throws Exception {
		long count = Stream.of(1, 5, 7, 8, 10)
			.flatMap(x -> IntStream.range(1, x).boxed())
			.count();
		
		assertThat(count).isEqualTo(26);
	}
}

