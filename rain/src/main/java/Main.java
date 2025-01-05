import hu.zsomi.rain.RainConfig;
import picocli.CommandLine;

public class Main {

    public static void main(String[] args) {
        int exitCode = new CommandLine(new RainConfig()).execute(args);
        if (exitCode != 0) {
            throw new RuntimeException("Exited with " + exitCode);
        }
    }
}
