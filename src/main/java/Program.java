import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Program {


    public static void main(String[] args) {
        Program program=new Program();
        System.out.println("Calculate [0] - both [1] - Steepest descent method [2] - Nelder Mead method");
        int choose = new Scanner(System.in).nextInt();
        switch (choose){
            case 0: {
                NelderMeadParams nelderMeadParams = program.getNelderMeadParams();
                System.out.println(nelderMeadParams);
                new NelderMeadMethod().start(nelderMeadParams);
                System.out.println("----------------------------------------\n" +
                        "------------------------------------------");
                SteepestDescentParams steepestDescentParams=program.getsteepestDescentParams();
                System.out.println(steepestDescentParams);
                System.out.println("steepest descent method result: " + new SteepestDescentMethod().start(steepestDescentParams));
            }
            case 1: {
                NelderMeadParams nelderMeadParams = program.getNelderMeadParams();

                System.out.println(nelderMeadParams);
                new NelderMeadMethod().start(nelderMeadParams);
            }
            case 2: {
                SteepestDescentParams steepestDescentParams=program.getsteepestDescentParams();
                System.out.println(steepestDescentParams);
                System.out.println("steepest descent method result: " + new SteepestDescentMethod().start(steepestDescentParams));
            }
        }



    }

    private NelderMeadParams getNelderMeadParams() {
        Pattern functionPattern=Pattern.compile("nelderMeadFunction:\\s?");
        Pattern x1Pattern=Pattern.compile("x1:\\s?");
        Pattern x2Pattern=Pattern.compile("x2:\\s?");
        Pattern x3Pattern=Pattern.compile("x3:\\s?");
        Pattern alfaPattern=Pattern.compile("alfa:\\s?");
        Pattern betaPattern=Pattern.compile("beta:\\s?");
        Pattern gammaPattern=Pattern.compile("gamma:\\s?");
        Pattern epsilonPattern=Pattern.compile("epsilon:\\s?");
        Pattern doubleNumberPattern=Pattern.compile("\\d+.?\\d*");

        NelderMeadParams nelderMeadParams=new NelderMeadParams();

        try {
            List<String> allLines=Files.readAllLines(Paths.get("file.txt"));
            allLines.forEach(line -> {
                Matcher functionMatcher=functionPattern.matcher(line);
                Matcher x1Matcher=x1Pattern.matcher(line);
                Matcher x2Matcher=x2Pattern.matcher(line);
                Matcher x3Matcher=x3Pattern.matcher(line);
                Matcher alfaMatcher=alfaPattern.matcher(line);
                Matcher betaMatcher=betaPattern.matcher(line);
                Matcher gammaMatcher=gammaPattern.matcher(line);
                Matcher epsilonMatcher=epsilonPattern.matcher(line);

                if (functionMatcher.find()) {
                    nelderMeadParams.setFunction(line.substring(functionMatcher.end()));
                } else if (x1Matcher.find()) {
                    Matcher doubleNumberMatche=doubleNumberPattern.matcher(line.substring(x1Matcher.end()));
                    Variable tmp = new Variable();
                    while (doubleNumberMatche.find()) {
                        tmp.getArguments().add(Double.valueOf(doubleNumberMatche.group()));
                    }
                    nelderMeadParams.getX().add(0, tmp);
                } else if (x2Matcher.find()) {
                    Matcher doubleNumberMatche=doubleNumberPattern.matcher(line.substring(x2Matcher.end()));
                    Variable tmp = new Variable();
                    while (doubleNumberMatche.find()) {
                        tmp.getArguments().add(Double.valueOf(doubleNumberMatche.group()));
                    }
                    nelderMeadParams.getX().add(1, tmp);
                } else if (x3Matcher.find()) {
                    Matcher doubleNumberMatche=doubleNumberPattern.matcher(line.substring(x3Matcher.end()));
                    Variable tmp = new Variable();
                    while (doubleNumberMatche.find()) {
                        tmp.getArguments().add(Double.valueOf(doubleNumberMatche.group()));
                    }
                    nelderMeadParams.getX().add(2, tmp);
                } else if (alfaMatcher.find()) {
                    nelderMeadParams.setAlfa(Double.valueOf(line.substring(alfaMatcher.end())));
                } else if (betaMatcher.find()) {
                    nelderMeadParams.setBeta(Double.valueOf(line.substring(betaMatcher.end())));
                } else if (gammaMatcher.find()) {
                    nelderMeadParams.setGamma(Double.valueOf(line.substring(gammaMatcher.end())));
                } else if (epsilonMatcher.find()) {
                    nelderMeadParams.setEpsilon(Double.valueOf(line.substring(epsilonMatcher.end())));
                }
            });
        } catch (IOException e) {
            System.out.println("Read file error: " + e.getMessage());
        }

        return nelderMeadParams;
    }

    private SteepestDescentParams getsteepestDescentParams() {
        Pattern funtionPattern=Pattern.compile("newtonRaphsonFunction:\\s?");
        Pattern startingPointPattern=Pattern.compile("starting point:\\s?");
        Pattern precisionPattern=Pattern.compile("precision:\\s?");

        SteepestDescentParams steepestDescentParams=new SteepestDescentParams();
        try {
            List<String> allLines=Files.readAllLines(Paths.get("file.txt"));
            allLines.forEach(line -> {
                Matcher functionMatcher=funtionPattern.matcher(line);
                Matcher startingPointMatcher=startingPointPattern.matcher(line);
                Matcher precisionMatcher=precisionPattern.matcher(line);
                if (functionMatcher.find()) {
                    steepestDescentParams.setFunction(line.substring(functionMatcher.end()));
                } else if (startingPointMatcher.find()) {
                    Pattern pointsPattern=Pattern.compile("\\d+.?\\d*");
                    Matcher pointsMatcher=pointsPattern.matcher(line.substring(startingPointMatcher.end())); //[0.0, 0.0]
                    while (pointsMatcher.find()) {
                        steepestDescentParams.getStartingPoint().add(Double.parseDouble(pointsMatcher.group()));
                    }
                } else if (precisionMatcher.find()) {
                    steepestDescentParams.setPrecision(Double.valueOf(line.substring(precisionMatcher.end())));
                }
            });
        } catch (IOException e) {
            System.out.println("Read file error: " + e.getMessage());
        }
        return steepestDescentParams;
    }
}
