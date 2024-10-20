package uob.oop;

public class NLP {
    /***
     * Clean the given (_content) text by removing all the characters that are not 'a'-'z', '0'-'9' and white space.
     * @param _content Text that need to be cleaned.
     * @return The cleaned text.
     */
    public static String textCleaning(String _content) {
        StringBuilder sbContent = new StringBuilder();
        //TODO Task 2.1 - 3 marks
        char[] charContent = _content.toLowerCase().toCharArray();
        for (char c : charContent) {
            if (Character.isLetterOrDigit(c) || Character.isWhitespace(c)) {
                sbContent.append(c);
            }
        }
        return sbContent.toString().trim();
    }


    /***
     * Text lemmatization. Delete 'ing', 'ed', 'es' and 's' from the end of the word.
     * @param _content Text that need to be lemmatized.
     * @return Lemmatized text.
     */
    public static String textLemmatization(String _content) {
        StringBuilder sbContent = new StringBuilder();
        //TODO Task 2.2 - 3 marks
        String[] words = _content.split(" ");
        for (String word : words) {
            if (word.endsWith("ing")) {
                word = word.substring(0, word.length() - 3);
            } else if (word.endsWith("ed")) {
                word = word.substring(0, word.length() - 2);
            } else if (word.endsWith("es")) {
                word = word.substring(0, word.length() - 2);
            } else if (word.endsWith("s")) {
                word = word.substring(0, word.length() - 1);
            }
            sbContent.append(word).append(" ");
        }
        // features length = 8 feature length = 7  featured
        // apples length = 6 apple
        return sbContent.toString().trim();
    }

    /***
     * Remove stop-words from the text.
     * @param _content The original text.
     * @param _stopWords An array that contains stop-words.
     * @return Modified text.
     */

    public static String removeStopWords(String _content, String[] _stopWords) {
        StringBuilder sbConent = new StringBuilder();
        _content = _content.toLowerCase();
        String[] words = _content.split(" ");

        for (String word : words) {
            word = word.trim();
            boolean isStopWord = false;
            for (String stopWord : _stopWords) {
                if (word.equalsIgnoreCase(stopWord)) {
                    isStopWord = true;
                    break;
                }
            }
            if (!isStopWord) {
                sbConent.append(word).append(" ");
            }
        }
        return sbConent.toString().trim();
    }
}
