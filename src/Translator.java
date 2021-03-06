import com.sun.xml.internal.ws.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Translator
{
    protected String separator = "       ";
    protected HashMap<String, String> morseMap;

    public Translator()
    {
        this.mapCharactersToMorse();
    }

    public String toMorse(String sentence)
    {
        Stream words = Stream.of(sentence.split("[ \n]"));

        String morse = words
                .map(word -> this.wordToMorse(word.toString()))
                .collect(Collectors.joining(this.separator))
                .toString();

        return morse.trim();
    }

    public String fromMorse(String sentence)
    {
        sentence = sentence.replaceAll(this.separator, " / ");

        Stream letters = Stream.of(sentence.split("[\\s\n]"));

        String regular = letters
                .map(letter -> this.morseToLetter(letter.toString()))
                .filter(s -> s != null)
                .collect(Collectors.joining(""))
                .toString();

        return StringUtils.capitalize(regular.toLowerCase().trim());
    }

    protected String wordToMorse(String word)
    {
        return word.chars()
                .mapToObj(character -> this.getMorseEquivalent((char) character))
                .collect(Collectors.joining(" "));
    }

    protected String morseToLetter(String letter)
    {
        if (letter.isEmpty() || letter == null) {
            return null;
        }

        if (letter.equalsIgnoreCase("/") || letter.equalsIgnoreCase("|")) {
            return " ";
        }

        return this.getRegularEquivalent(letter);
    }

    protected String getMorseEquivalent(char character)
    {
        String value = String.valueOf(character).toUpperCase();

        return this.morseMap.containsKey(value) ? this.morseMap.get(value) : "??";
    }

    protected String getRegularEquivalent(String letter)
    {
        return this.morseMap.entrySet()
                .stream()
                .filter(value -> value.getValue().equals(letter))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    protected void mapCharactersToMorse()
    {
        this.morseMap = new HashMap<>();

        this.morseMap.put("A", ".-");
        this.morseMap.put("B", "-...");
        this.morseMap.put("C", "-.-.");
        this.morseMap.put("D", "-..");
        this.morseMap.put("E", ".");
        this.morseMap.put("F", "..-.");
        this.morseMap.put("G", "--.");
        this.morseMap.put("H", "....");
        this.morseMap.put("I", "..");
        this.morseMap.put("J", ".---");
        this.morseMap.put("K", "-.-");
        this.morseMap.put("L", ".-..");
        this.morseMap.put("M", "--");
        this.morseMap.put("N", "-.");
        this.morseMap.put("O", "---");
        this.morseMap.put("P", ".--.");
        this.morseMap.put("Q", "--.-");
        this.morseMap.put("R", ".-.");
        this.morseMap.put("S", "...");
        this.morseMap.put("T", "-");
        this.morseMap.put("U", "..-");
        this.morseMap.put("V", "...-");
        this.morseMap.put("W", ".--");
        this.morseMap.put("X", "-..-");
        this.morseMap.put("Y", "-.--");
        this.morseMap.put("Z", "--..");

        this.morseMap.put("0", "-----");
        this.morseMap.put("1", ".----");
        this.morseMap.put("2", "..---");
        this.morseMap.put("3", "...--");
        this.morseMap.put("4", "....-");
        this.morseMap.put("5", ".....");
        this.morseMap.put("6", "-....");
        this.morseMap.put("7", "--...");
        this.morseMap.put("8", "---..");
        this.morseMap.put("9", "----.");

        this.morseMap.put(".", ".-.-.-");
        this.morseMap.put(",", "--..--");
        this.morseMap.put("!", "-.-.--");
        this.morseMap.put("?", "..--..");
        this.morseMap.put(":", "---...");
        this.morseMap.put("-", "-....-");
        this.morseMap.put("@", ".--.-.");
        this.morseMap.put("error", "........");
    }
}
