package ua_parser.pig.platform;

import java.io.IOException;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;

import ua_parser.Platform;
import ua_parser.pig.PigParser;

public class Family extends EvalFunc<String> {

    PigParser parser;

    public Family() throws IOException {
        parser = PigParser.getParser();
    }

    public String exec(Tuple input) throws IOException {
        if (input == null || input.size() == 0)
            return null;
        try {
            String agentString = (String) input.get(0);
            Platform platform = parser.parsePlatform(agentString);
            if (platform == null) {
                return null;
            }
            return platform.family;
        } catch (Exception e) {
            throw new IOException("Caught exception processing input row ", e);
        }
    }

}
