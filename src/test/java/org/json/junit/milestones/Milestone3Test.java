package org.json.junit.milestones;

import org.json.JSONObject;
import org.json.XML;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public class Milestone3Test {
    @Test
    public void test1() throws FileNotFoundException {
        class KeyTransformer implements Function<String, String> {
            @Override
            public String apply(String s) {
                return "swe262_" + s;
            }
        }

        KeyTransformer function = new KeyTransformer();
        String path = System.getProperty("user.dir");
        File xmlFile = new File(path + "/src/test/xmlFiles/jani_note.xml");
        Reader reader = new FileReader(xmlFile);

        JSONObject expectedJson = new JSONObject("{\"swe262_note\":{\"swe262_heading\":\"Reminder\",\"swe262_from\":\"Jani\",\"swe262_to\":\"Tove\",\"swe262_body\":\"Don't forget me this weekend!\"}}");
        JSONObject actualJson = XML.toJSONObject(reader, function);
        assertEquals(expectedJson.toString(), actualJson.toString());
    }
}
