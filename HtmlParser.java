package uob.oop;

public class HtmlParser {
    /***
     * Extract the title of the news from the _htmlCode.
     * @param _htmlCode Contains the full HTML string from a specific news. E.g. 01.htm.
     * @return Return the title if it's been found. Otherwise, return "Title not found!".
     */
    public static String getNewsTitle(String _htmlCode) {
        //TODO Task 1.1 - 5 marks
        int indexStart = _htmlCode.indexOf("<title>");
        int indexEnd = _htmlCode.indexOf("</title>");

        if (indexStart != -1 && indexEnd != -1) {
            String title = _htmlCode.substring(indexStart + "<title>".length(), indexEnd).trim(); // plus 7 to skip the <title>
            // get rid of | ...
            int indexBar = title.indexOf("|");
            if (indexBar != -1) {
                title = title.substring(0, indexBar).trim(); // extracts from index 0 to the |
            }
            return title;
        } else {
            return "Title not found!";
        }
    }

    /***
     * Extract the content of the news from the _htmlCode.
     * @param _htmlCode Contains the full HTML string from a specific news. E.g. 01.htm.
     * @return Return the content if it's been found. Otherwise, return "Content not found!".
     */
    public static String getNewsContent(String _htmlCode) {
        //TODO Task 1.2 - 5 marks
        // Plan - find index of articleBody + " after tp find end
        // use .toLowerCase()
        // RETURN..*
        int indexStart = _htmlCode.indexOf("\"articleBody\": \"");
        if (indexStart != -1) {
            indexStart += "\"articleBody\": \"".length(); // move index to char after ""
            int indexEnd = _htmlCode.indexOf("\",\"mainEntityOfPage\"", indexStart);
            if (indexEnd != -1) {
                String content = _htmlCode.substring(indexStart, indexEnd);
                return content.toLowerCase().trim();
            }
        }
        return "Content not found!";
    }


}
