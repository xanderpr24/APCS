import java.util.ArrayList;
import java.util.Arrays;

public class Duplicates {
    public static void main(String[] foo) {
        Character[] c = new Character[1];
        Arrays.stream(foo)
        .map(s -> Tree.seedTree(getSplit(s).toArray(c)))
        .forEach(System.out::println);
    }

    private static ArrayList<Character> getSplit(String sentence) {
        return sentence.chars()
        .mapToObj(c -> Character.toUpperCase((char)c))
        .filter(Character::isAlphabetic)
        .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
}
