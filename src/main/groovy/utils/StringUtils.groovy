package utils

class StringUtils {

    static String regexFind(final String string, final String regex) {
        return string.find(regex)
    }

    static String regexReplace(final String string, final String regex, final String replacement) {
        return string.replaceAll(regex, replacement)
    }
}
