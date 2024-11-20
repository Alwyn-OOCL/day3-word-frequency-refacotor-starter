import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WordFrequencyGame {

    public static final String REGEX = "\\s+";
    public static final String CALCULATE_ERROR = "Calculate Error";
    public static final String LINE_BREAK = "\n";
    public static final String SPACE = " ";

    public String getWordFrequency(String sentence) {
        try {
            //split the input string with 1 to n pieces of spaces
            List<WordFrequency> frequencies = getInitialWordFrequencies(sentence);

            //get the map for the next step of sizing the same word
            frequencies = getWordFrequencies(frequencies);

            return joinWordFrequencyResult(frequencies);
        } catch (Exception e) {
            return CALCULATE_ERROR;
        }
    }

    private static String joinWordFrequencyResult(List<WordFrequency> frequencies) {
        return frequencies.stream()
                .map(wordFrequency -> wordFrequency.getWord() + SPACE + wordFrequency.getWordCount())
                .collect(Collectors.joining(LINE_BREAK));
    }

    private List<WordFrequency> getWordFrequencies(List<WordFrequency> frequencies) {
        Map<String, List<WordFrequency>> wordFrequencyMap = getWordToFrequencyMap(frequencies);

        return wordFrequencyMap.entrySet().stream()
                .map(entry -> new WordFrequency(entry.getKey(), entry.getValue().size()))
                .sorted((word, nextWord) -> nextWord.getWordCount() - word.getWordCount())
                .toList();
    }

    private static List<WordFrequency> getInitialWordFrequencies(String sentence) {
        String[] words = sentence.split(REGEX);
        return Arrays.stream(words)
                .map(word -> new WordFrequency(word, 1))
                .toList();
    }

    private Map<String, List<WordFrequency>> getWordToFrequencyMap(List<WordFrequency> wordFrequencies) {
        Map<String, List<WordFrequency>> wordFrequencyMap = new HashMap<>();
        for (WordFrequency wordFrequency : wordFrequencies) {
            wordFrequencyMap.computeIfAbsent(wordFrequency.getWord(), word -> new ArrayList<>()).add(wordFrequency);
        }
        return wordFrequencyMap;
    }


}
