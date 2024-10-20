package uob.oop;

import java.text.DecimalFormat;

public class NewsClassifier {
    public String[] myHTMLs;
    public String[] myStopWords = new String[127];
    public String[] newsTitles;
    public String[] newsContents;
    public String[] newsCleanedContent;
    public double[][] newsTFIDF;
    private final String TITLE_GROUP1 = "Osiris-Rex's sample from asteroid Bennu will reveal secrets of our solar system";
    private final String TITLE_GROUP2 = "Bitcoin slides to five-month low amid wider sell-off";
    public Toolkit myTK;

    public NewsClassifier() {
        myTK = new Toolkit();
        myHTMLs = myTK.loadHTML(); // populates myHTMls
        myStopWords = myTK.loadStopWords(); //populates myStopWords varibale
        // no modifying above ^
        loadData(); //task to be done
    }

    public static void main(String[] args) {
        NewsClassifier myNewsClassifier = new NewsClassifier();

        myNewsClassifier.newsCleanedContent = myNewsClassifier.preProcessing();

        myNewsClassifier.newsTFIDF = myNewsClassifier.calculateTFIDF(myNewsClassifier.newsCleanedContent);

        //Change the _index value to calculate similar based on a different news article.
        double[][] doubSimilarity = myNewsClassifier.newsSimilarity(0);

        System.out.println(myNewsClassifier.resultString(doubSimilarity, 10));

        String strGroupingResults = myNewsClassifier.groupingResults(myNewsClassifier.TITLE_GROUP1, myNewsClassifier.TITLE_GROUP2);
        System.out.println(strGroupingResults);
    }

    public void loadData() {
        //TODO 4.1 - 2 marks
        // populate the newsTitles and newsContents by return values
        // from HTMLParser.getNewsTITle and getNewsContent
        int htmlSize = myHTMLs.length;
        newsTitles = new String[htmlSize];
        newsContents = new String[htmlSize];

        for (int i = 0; i < htmlSize; i++) {
            newsTitles[i] = HtmlParser.getNewsTitle(myHTMLs[i]);
            newsContents[i] = HtmlParser.getNewsContent(myHTMLs[i]);
        }
    }

    public String[] preProcessing() {

        String[] myCleanedContent = null; // do not modify part of skeleton code
        if (newsContents == null || newsContents.length == 0) {
            return null;
        }
        //TODO 4.2 - 5 marks
        myCleanedContent = new String[newsContents.length];
        for (int i = 0; i < newsContents.length; i++) {
            String cleanedContent = NLP.textCleaning(newsContents[i]);
            String lemmatizedContent = NLP.textLemmatization(cleanedContent);
            String processedContent = NLP.removeStopWords(lemmatizedContent, myStopWords);

            myCleanedContent[i] = processedContent;

        }
        return myCleanedContent;
    }

    public double[][] calculateTFIDF(String[] _cleanedContents) {
        String[] vocabularyList = buildVocabulary(_cleanedContents);
        double[][] myTFIDF = null; // do not modify
        //TODO 4.3 - 10 marks
        int numDocs = _cleanedContents.length;
        myTFIDF = new double[numDocs][vocabularyList.length];
        if (_cleanedContents == null || _cleanedContents.length == 0) {
            return null;
        }
        for (int i = 0; i < numDocs; i++) {
            String[] docWords = _cleanedContents[i].split(" ");
            int totalWordsInDoc = docWords.length;
            for (int j = 0; j < vocabularyList.length; j++) {
                String term = vocabularyList[j];
                int termFreq = 0;
                for (String word : docWords) {
                    if (word.equals(term)) {
                        termFreq++;
                    }
                }
                double tf = (double) termFreq / totalWordsInDoc;

                int docWithTerm = 0;
                for (String doc : _cleanedContents) {
                    String[] words = doc.split(" ");
                    boolean termFound = false;
                    for (String word : words) {
                        if (word.equals(term)) {
                            termFound = true;
                            break;
                        }
                    }
                    if (termFound) {
                        docWithTerm++;
                    }
                }
                if (docWithTerm == 0) {
                    myTFIDF[i][j] = 0.0; // Handle zero IDF
                } else
                {
                    double idf = Math.log((double) numDocs / docWithTerm) + 1;
                    myTFIDF[i][j] = tf * idf;

                }
            }
        }

        return myTFIDF;
    }


    public String[] buildVocabulary(String[] _cleanedContents) {
        String[] arrayVocabulary = null; // do not modify
        //TODO 4.4 - 10 marks
        if (_cleanedContents == null || _cleanedContents.length == 0) {
            return null;
        }
        int totalWords = 0;
        // count words
        for (String article : _cleanedContents) {
            totalWords += article.split(" ").length;
        }
        arrayVocabulary = new String[totalWords];
        int currentIndex = 0;
        for (String article : _cleanedContents) {
            String[] articleWords = article.split(" ");
            for (String word : articleWords) {
                if (currentIndex < arrayVocabulary.length) {
                    if (!arrayContains(arrayVocabulary, currentIndex, word)) {
                        arrayVocabulary[currentIndex] = word;
                        currentIndex++;
                    }
                }
            }
        }
        arrayVocabulary = trimArray(arrayVocabulary, currentIndex);
        return arrayVocabulary;
    }

    // Helper method for buildVocab emethod
    private boolean arrayContains(String[] arr, int index, String word) {
        for (int i = 0; i < index; i++) {
            if (arr[i] != null && arr[i].equals(word)) {
                return true;
            }
        }
        return false;
    }

    // helper method
    private String[] trimArray(String[] arr, int length) {
        String[] trimmedArray = new String[length];
        for (int i = 0; i < length; i++) {
            trimmedArray[i] = arr[i];
        }
        return trimmedArray;
    }
    // Helper method to trim arrays to the specified length


    public double[][] newsSimilarity(int _newsIndex) {
        double[][] mySimilarity = null; // do not change
        //TODO 4.5 - 15 marks

        int numArticles = newsTFIDF.length;
        mySimilarity = new double[numArticles][2];
        Vector vector1 = new Vector(newsTFIDF[_newsIndex]);
        for (int j = 0; j < numArticles; j++) {
            if (j != _newsIndex) {
                Vector vector2 = new Vector(newsTFIDF[j]);
                double similarity = vector1.cosineSimilarity(vector2);
                mySimilarity[j][0] = j; // Index of the second news article
                mySimilarity[j][1] = similarity; // CS value

            } else {
                mySimilarity[j][0] = j; // Index of the same news article
                mySimilarity[j][1] = 1.00000; // CS with itself is 1.0
            }
        }
        // bubble sort
        for (int i = 0; i < numArticles - 1; i++) {
            for (int j = 0; j < numArticles - i - 1; j++) {
                if (mySimilarity[j][1] < mySimilarity[j + 1][1]) {

                    double[] temp = mySimilarity[j];
                    mySimilarity[j] = mySimilarity[j + 1];
                    mySimilarity[j + 1] = temp;
                }
            }
        }
        return mySimilarity;
    }


    public String groupingResults(String _firstTitle, String _secondTitle) {
        int[] arrayGroup1 = null, arrayGroup2 = null;

        //TODO 4.6 - 15 marks
        arrayGroup1 = new int[newsTitles.length];
        arrayGroup2 = new int[newsTitles.length];
        int group1Count = 0;
        int group2Count = 0;
        int firstIndex = 0;
        int secondIndex = 0;

        for (int i = 0; i < newsTitles.length; i++) {
            if (newsTitles[i].equals(_firstTitle)) {
                 firstIndex =i;
            }
            else if( newsTitles[i].equals(_secondTitle)){
                secondIndex = i;
            }
        }

        Vector [] vectorArray = new Vector[newsTFIDF.length];

        for (int i = 0; i < newsTFIDF.length; i++) {
            vectorArray[i] = new Vector(newsTFIDF[i]);
        }

        for (int i = 0; i < newsTitles.length; i++) {

            //  if (!newsTitles[i].equals(_firstTitle) && !newsTitles[i].equals(_secondTitle)) {
            // Calculate CS values between the news article and the two provided titles
            double csWithFirstTitle = vectorArray[i].cosineSimilarity(vectorArray[firstIndex]);
            double csWithSecondTitle = vectorArray[i].cosineSimilarity(vectorArray[secondIndex]);

            if (csWithFirstTitle > csWithSecondTitle) {
                arrayGroup1[group1Count] = i;
                group1Count++;
            } else {
                arrayGroup2[group2Count] = i;
                group2Count++;
            }
            //}
        }
        arrayGroup1 = trimArray(arrayGroup1, group1Count);
        arrayGroup2 = trimArray(arrayGroup2, group2Count);

        return resultString(arrayGroup1, arrayGroup2);

    }


    private int[] trimArray(int[] arr, int length) {
        int[] trimmedArray = new int[length];
        for (int i = 0; i < length; i++) {
            trimmedArray[i] = arr[i];
        }
        return trimmedArray;
    }

    public String resultString(double[][] _similarityArray, int _groupNumber) {
        if (_similarityArray == null || _similarityArray.length == 0) {
            return null;
        }
        StringBuilder mySB = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat("#.#####");
        for (int j = 0; j < _groupNumber; j++) {
            for (int k = 0; k < _similarityArray[j].length; k++) {
                if (k == 0) {
                    mySB.append((int) _similarityArray[j][k]).append(" ");
                } else {
                    String formattedCS = decimalFormat.format(_similarityArray[j][k]);
                    mySB.append(formattedCS).append(" ");
                }
            }
            mySB.append(newsTitles[(int) _similarityArray[j][0]]).append("\r\n");
        }
        mySB.delete(mySB.length() - 2, mySB.length());
        return mySB.toString();
    }

    public String resultString(int[] _firstGroup, int[] _secondGroup) {
        StringBuilder mySB = new StringBuilder();
        mySB.append("There are ").append(_firstGroup.length).append(" news in Group 1, and ").append(_secondGroup.length).append(" in Group 2.\r\n").append("=====Group 1=====\r\n");

        for (int i : _firstGroup) {
            mySB.append("[").append(i + 1).append("] - ").append(newsTitles[i]).append("\r\n");
        }
        mySB.append("=====Group 2=====\r\n");
        for (int i : _secondGroup) {
            mySB.append("[").append(i + 1).append("] - ").append(newsTitles[i]).append("\r\n");
        }

        mySB.delete(mySB.length() - 2, mySB.length());
        return mySB.toString();
    }

}
