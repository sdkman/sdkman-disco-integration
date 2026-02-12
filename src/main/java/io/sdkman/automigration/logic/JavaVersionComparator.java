package io.sdkman.automigration.logic;

import java.util.Comparator;

public class JavaVersionComparator implements Comparator<String> {

	private static final JavaVersionComparator INSTANCE = new JavaVersionComparator();

	private JavaVersionComparator() {
	}

	public static JavaVersionComparator getInstance() {
		return INSTANCE;
	}

	@Override
	public int compare(String v1, String v2) {
		if (v1 == null && v2 == null) {
			return 0;
		}
		if (v1 == null) {
			return -1;
		}
		if (v2 == null) {
			return 1;
		}

		String[] segments1 = splitVersion(v1);
		String[] segments2 = splitVersion(v2);

		int maxLength = Math.max(segments1.length, segments2.length);
		for (int i = 0; i < maxLength; i++) {
			String seg1 = (i < segments1.length) ? segments1[i] : "0";
			String seg2 = (i < segments2.length) ? segments2[i] : "0";

			int comparison = compareSegments(seg1, seg2);
			if (comparison != 0) {
				return comparison;
			}
		}
		return 0;
	}

	private String[] splitVersion(String version) {
		return version.split("[.\\-+_]");
	}

	private int compareSegments(String seg1, String seg2) {
		boolean isNumeric1 = isNumeric(seg1);
		boolean isNumeric2 = isNumeric(seg2);

		if (isNumeric1 && isNumeric2) {
			return Long.compare(Long.parseLong(seg1), Long.parseLong(seg2));
		}

		// "ea" (early access) is less than any numeric version
		if (seg1.equalsIgnoreCase("ea") && isNumeric2) {
			return -1;
		}
		if (isNumeric1 && seg2.equalsIgnoreCase("ea")) {
			return 1;
		}

		// Fall back to string comparison for non-numeric segments
		return seg1.compareToIgnoreCase(seg2);
	}

	private boolean isNumeric(String s) {
		if (s == null || s.isEmpty()) {
			return false;
		}
		for (char c : s.toCharArray()) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}

}
