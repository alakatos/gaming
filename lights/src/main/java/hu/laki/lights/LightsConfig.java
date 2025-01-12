package hu.laki.lights;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

import lombok.Getter;
import picocli.CommandLine.Option;

@Getter
public class LightsConfig implements Callable<Integer> {

    private final Consumer<LightsConfig> configurableWorker;
    @Option(names = { "-g",
            "--gravity" }, description = "Gravity  (${DEFAULT-VALUE})", defaultValue = "10", required = false)
    private double g;

    @Option(names = { "-fps",
            "--frame-per-second" }, description = "Frame rate  (${DEFAULT-VALUE})", defaultValue = "30", required = false)
    private int fps;

    @Option(names = { "-n",
            "--max-number-of-stars" }, description = "Max number of stars (${DEFAULT-VALUE})", defaultValue = "2000", required = false)
    private int maxNumberOfStars;

    @Option(names = { "-zgr",
            "--zero-gravity-radius" }, description = "Zero gravity radius (${DEFAULT-VALUE})", defaultValue = "20", required = false)
    private int zeroGravityRadius;

    public LightsConfig(Consumer<LightsConfig> worker) {
        this.configurableWorker = worker;
    }

    @Override
    public Integer call() throws Exception {
        configurableWorker.accept(this);
        return 0;
    }

}
