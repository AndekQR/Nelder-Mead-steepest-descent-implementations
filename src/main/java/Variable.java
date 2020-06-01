import java.util.ArrayList;
import java.util.List;

public class Variable {

    private List<Double> arguments;
    private Double value;

    public Variable() {
        this.arguments = new ArrayList<>();
    }

    public Variable(Variable variable){
        this.setArguments(variable.getArguments());
        this.setValue(variable.getValue());
    }

    public Variable(List<Double> arguments, Double value) {
        this.arguments=arguments;
        this.value=value;
    }

    public List<Double> getArguments() {
        return arguments;
    }

    public void setArguments(List<Double> arguments) {
        this.arguments=arguments;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value=value;
    }

    @Override
    public String toString() {
        return "Variable{" +
                "arguments=" + arguments +
                ", value=" + value +
                '}';
    }

}
