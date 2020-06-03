import com.udojava.evalex.Expression;

import java.util.List;
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

    public static Double evaluate(final String function, final List<String> argsNames, final List<Double> args) {
        Expression expression=new Expression(function);
        for (int i=0; i < argsNames.size(); i++) {
            expression.setVariable(argsNames.get(i), args.get(i).toString());
        }
        return expression.eval().doubleValue();
    }
}
