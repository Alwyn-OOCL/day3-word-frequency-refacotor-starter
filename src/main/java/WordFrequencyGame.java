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

    public String getWordFrequency(String sentence) {
        if (sentence.split(REGEX).length == 1) {
            return sentence + " 1";
        } else {
            try {
                //split the input string with 1 to n pieces of spaces
                String[] words = sentence.split(REGEX);

                List<WordFrequency> frequencies = Arrays.stream(words)
                        .map(word -> new WordFrequency(word, 1))
                        .toList();

                //get the map for the next step of sizing the same word
                Map<String, List<WordFrequency>> wordFrequencyMap = getListMap(frequencies);

                frequencies = wordFrequencyMap.entrySet().stream()
                        .map(entry -> new WordFrequency(entry.getKey(), entry.getValue().size()))
                        .sorted((word, nextWord) -> nextWord.getWordCount() - word.getWordCount())
                        .toList();

                return frequencies.stream()
                        .map(wordFrequency -> wordFrequency.getWord() + " " + wordFrequency.getWordCount())
                        .collect(Collectors.joining(LINE_BREAK));
            } catch (Exception e) {
                return CALCULATE_ERROR;
            }
        }
    }

    private Map<String, List<WordFrequency>> getListMap(List<WordFrequency> wordFrequencies) {
        Map<String, List<WordFrequency>> wordFrequencyMap = new HashMap<>();
        for (WordFrequency wordFrequency : wordFrequencies) {
            if (!wordFrequencyMap.containsKey(wordFrequency.getWord())) {
                ArrayList wordFrequencyCount = new ArrayList<>();
                wordFrequencyCount.add(wordFrequency);
                wordFrequencyMap.put(wordFrequency.getWord(), wordFrequencyCount);
            } else {
                wordFrequencyMap.get(wordFrequency.getWord()).add(wordFrequency);
            }
        }
        return wordFrequencyMap;
    }


}
