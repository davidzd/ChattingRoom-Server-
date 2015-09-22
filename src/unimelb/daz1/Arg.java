package unimelb.daz1;
import org.kohsuke.args4j.Option;
/**
 * Created by davidzd on 15/9/20.
 */
public class Arg {
    @Option(name = "-p", usage = "Determine the port")
    public Integer port = 4444;
}
