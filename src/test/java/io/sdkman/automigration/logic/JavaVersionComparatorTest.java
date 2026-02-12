package io.sdkman.automigration.logic;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JavaVersionComparatorTest {

	private final JavaVersionComparator comparator = JavaVersionComparator.getInstance();

	@Test
	void testStandardVersionComparison() {
		// 21.0.10 should be greater than 21.0.9 (the bug case)
		assertThat(comparator.compare("21.0.10+7", "21.0.9+6")).isGreaterThan(0);
		assertThat(comparator.compare("21.0.9+6", "21.0.10+7")).isLessThan(0);
	}

	@Test
	void testLegacyJava8Version() {
		assertThat(comparator.compare("8.0.322+6", "8.0.312+7")).isGreaterThan(0);
		assertThat(comparator.compare("8.0.312+7", "8.0.322+6")).isLessThan(0);
	}

	@Test
	void testFourPartVersion() {
		// Four-part security releases
		assertThat(comparator.compare("11.0.14.1+1", "11.0.14.0+9")).isGreaterThan(0);
		assertThat(comparator.compare("11.0.14.0+9", "11.0.14.1+1")).isLessThan(0);
	}

	@Test
	void testEarlyAccessVersion() {
		// EA versions should be less than GA versions
		assertThat(comparator.compare("18-ea+3", "18+36")).isLessThan(0);
		assertThat(comparator.compare("18+36", "18-ea+3")).isGreaterThan(0);
	}

	@Test
	void testShortVersion() {
		// GraalVM-style short versions
		assertThat(comparator.compare("22.2", "22.1")).isGreaterThan(0);
		assertThat(comparator.compare("22.1", "22.2")).isLessThan(0);
	}

	@Test
	void testSimpleIntegerVersion() {
		assertThat(comparator.compare("17", "8")).isGreaterThan(0);
		assertThat(comparator.compare("8", "17")).isLessThan(0);
	}

	@Test
	void testEqualVersions() {
		assertThat(comparator.compare("21.0.10+7", "21.0.10+7")).isEqualTo(0);
		assertThat(comparator.compare("11.0.14.1", "11.0.14.1")).isEqualTo(0);
	}

	@Test
	void testNullHandling() {
		assertThat(comparator.compare(null, null)).isEqualTo(0);
		assertThat(comparator.compare(null, "21.0.10")).isLessThan(0);
		assertThat(comparator.compare("21.0.10", null)).isGreaterThan(0);
	}

	@Test
	void testMissingSegmentsTreatedAsZero() {
		// 22.1 vs 22.1.0 should be equal
		assertThat(comparator.compare("22.1", "22.1.0")).isEqualTo(0);
		assertThat(comparator.compare("22.1.0", "22.1")).isEqualTo(0);
	}

	@Test
	void testFourPartGraalVmVersion() {
		// Mandrel/Liberica Native style versions
		assertThat(comparator.compare("22.0.0.2", "22.0.0.1")).isGreaterThan(0);
		assertThat(comparator.compare("22.0.0.1", "22.0.0.2")).isLessThan(0);
	}

	@Test
	void testBuildNumberComparison() {
		// Same version, different build numbers
		assertThat(comparator.compare("21.0.10+8", "21.0.10+7")).isGreaterThan(0);
		assertThat(comparator.compare("21.0.10+7", "21.0.10+8")).isLessThan(0);
	}

	@Test
	void testUnderscoreSeparator() {
		// Some versions use underscore as separator
		assertThat(comparator.compare("8u322", "8u312")).isGreaterThan(0);
	}

}
