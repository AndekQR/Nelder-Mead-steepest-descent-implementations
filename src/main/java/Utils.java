import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static SortedSet<String> parseNames(String function) {
        Pattern varaibleMatcher=Pattern.compile("(([a-z]{2})|([a-z]\\d+))|([a-z])");
        SortedSet<String> result = new TreeSet<>();
        Matcher matcher=varaibleMatcher.matcher(function);
        while (matcher.find()) {
            result.add(matcher.group());
        }
        return result;
    }
}
