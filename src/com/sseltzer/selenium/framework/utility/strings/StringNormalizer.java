package com.sseltzer.selenium.framework.utility.strings;

import org.apache.commons.lang3.StringUtils;

import com.sseltzer.selenium.framework.verification.validation.ArgumentValidator;

public abstract class StringNormalizer {
	public abstract String normalize(String term);

	private static class TitleCaseNormalizer extends StringNormalizer {
		public String normalize(String term) {
			if ("".equals(term)) return term;
			StringBuilder newTerm = new StringBuilder();
			for (String word : term.split(" ")) newTerm.append(Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase() + " ");
			return newTerm.toString().trim();
		}
	}

	private static class CSSNormalizer extends StringNormalizer {
		public String normalize(String term) {
			ArgumentValidator.create().validate(term, "cssStr");
			return term.replaceAll("[.]", StringUtils.EMPTY);
		}
	}

	public static String titleCase(String term) {
		return NormalizeType.TITLE.getNormalizer().normalize(term);
	}

	public static String css(String term) {
		return NormalizeType.CSS.getNormalizer().normalize(term);
	}

	private enum NormalizeType {
		TITLE(new TitleCaseNormalizer()), CSS(new CSSNormalizer());
		private StringNormalizer normalizer;

		private NormalizeType(StringNormalizer normalizer) {
			this.normalizer = normalizer;
		}

		public StringNormalizer getNormalizer() {
			return normalizer;
		}
	}
}
