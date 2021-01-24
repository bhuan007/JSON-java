package org.json.junit;

import org.json.JSONObject;
import org.json.JSONPointer;
import org.json.XML;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public class Milestone2Tests {
    // Extracts sub object from xml to json file
    @Test
    public void testtoJSONObjectOne() throws FileNotFoundException {
        // Test 1
        String path = System.getProperty("user.dir");
        File xmlFile = new File(path + "/src/test/xmlFiles/jani_note.xml");
        Reader reader = new FileReader(xmlFile);
        JSONPointer pointer = new JSONPointer("/childNodes/0");
        JSONObject jsonObject = XML.toJSONObject(reader, pointer);

        String expectedJson = "{\"childNodes\":[\"Tove\"],\"tagName\":\"to\"}";
        assertEquals(expectedJson, jsonObject.toString());

        //Test 2
        File xmlFile2 = new File(path + "/src/test/xmlFiles/food.xml");
        Reader reader2 = new FileReader(xmlFile2);
        JSONPointer pointer2 = new JSONPointer("/childNodes/2/childNodes/0");
        JSONObject jsonObject2 = XML.toJSONObject(reader2, pointer2);

        String expectedJson2 = "{\"childNodes\":[\"Berry-Berry Belgian Waffles\"],\"tagName\":\"name\"}";
        assertEquals(expectedJson2, jsonObject2.toString());

    }

    // Replace sub object on xml file with another json file
    @Test
    public void testtoJSONObjectTwo() throws FileNotFoundException {
        // Test 1
        String path = System.getProperty("user.dir");
        File xmlFile = new File(path + "/src/test/xmlFiles/jani_note.xml");
        Reader reader = new FileReader(xmlFile);
        JSONPointer pointer = new JSONPointer("/childNodes/3");

        JSONObject replacement = new JSONObject("{\"childNodes\":[\"Tove\"],\"tagName\":\"to\"}");

        JSONObject jsonObject = XML.toJSONObject(reader, pointer, replacement);
        JSONObject expectedJSON = new JSONObject("{\"childNodes\":[{\"childNodes\":[\"Tove\"],\"tagName\":\"to\"},{\"childNodes\":[\"Jani\"],\"tagName\":\"from\"},{\"childNodes\":[\"Reminder\"],\"tagName\":\"heading\"},{\"childNodes\":[\"Tove\"],\"tagName\":\"to\"}],\"tagName\":\"note\"}");

        assertEquals(expectedJSON.toString(), jsonObject.toString());


        // Test 2
        File xmlFile2 = new File(path + "/src/test/xmlFiles/food.xml");
        Reader reader2 = new FileReader(xmlFile2);
        JSONPointer pointer2 = new JSONPointer("/childNodes");

        JSONObject replacement2 = new JSONObject("{\"childNodes\":[\"Tove\"],\"tagName\":\"to\"}");

        JSONObject jsonObject2 = XML.toJSONObject(reader2, pointer2, replacement2);
        JSONObject expectedJSON2 = new JSONObject("{\"childNodes\":{\"childNodes\":[\"Tove\"],\"tagName\":\"to\"},\"tagName\":\"breakfast_menu\"}");

        assertEquals(expectedJSON2.toString(), jsonObject2.toString());


    }
}
