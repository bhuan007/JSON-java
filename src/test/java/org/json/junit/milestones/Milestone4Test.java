package org.json.junit.milestones;

import org.json.JSONObject;
import org.json.XML;
import org.junit.Test;
import org.junit.Assert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Milestone4Test {

    @Test
    public void streamFilterTest() throws FileNotFoundException {
        System.out.println("\n--- Stream Filter Test ---");
        String path = System.getProperty("user.dir");
        File xmlFile = new File(path + "/src/test/xmlFiles/jani_note.xml");
        Reader reader = new FileReader(xmlFile);
        JSONObject obj = XML.toJSONObject(reader);

        List<Object> list = obj.toStream().filter(node -> {
            return node.has("test");
        }).collect(Collectors.toList());

        List<Object> expectedList = new ArrayList<>();

        expectedList.add(new JSONObject("{\"test\":{\"sample\":\"a\"}}"));
        expectedList.add(new JSONObject("{\"test\":{\"note\":\"hello\"},\"heading\":\"Reminder\",\"from\":{\"b\":\"d\"},\"to\":\"Tove\",\"body\":\"Don't forget me this weekend!\",\"sample\":{\"test\":{\"sample\":\"a\"}}}"));

        for (Object item: list) {
            System.out.println(item);
        }

        Assert.assertEquals(list.get(0).toString(), expectedList.get(0).toString());
        Assert.assertEquals(list.get(1).toString(), expectedList.get(1).toString());
    }

    @Test
    public void streamForEachTest() throws FileNotFoundException {
        System.out.println("\n--- Stream For Each Test ---");
        String path = System.getProperty("user.dir");
        File xmlFile = new File(path + "/src/test/xmlFiles/jani_note.xml");
        Reader reader = new FileReader(xmlFile);
        JSONObject obj = XML.toJSONObject(reader);

        StringBuilder actualSb = new StringBuilder();
        String expected = "{\"test\":{\"sample\":\"a\"}}{\"test\":{\"note\":\"hello\"},\"heading\":\"Reminder\",\"from\":{\"b\":\"d\"},\"to\":\"Tove\",\"body\":\"Don't forget me this weekend!\",\"sample\":{\"test\":{\"sample\":\"a\"}}}";

        obj.toStream().forEach(node -> {
            if (node.has("test")) {
                actualSb.append(node.toString());
                System.out.println(node.toString());
            }
        });

        Assert.assertEquals(expected, actualSb.toString());
    }

    @Test
    public void streamFilterForEachTest() throws FileNotFoundException {
        System.out.println("\n--- Stream Filter For Each Test ---");
        String path = System.getProperty("user.dir");
        File xmlFile = new File(path + "/src/test/xmlFiles/jani_note.xml");
        Reader reader = new FileReader(xmlFile);
        JSONObject obj = XML.toJSONObject(reader);

        String expected = "{\"note\":{\"aKey\":\"aValue\",\"test\":{\"note\":\"hello\"},\"heading\":\"Reminder\",\"from\":{\"b\":\"d\"},\"to\":\"Tove\",\"body\":\"Don't forget me this weekend!\",\"sample\":{\"test\":{\"aKey\":\"aValue\",\"sample\":\"a\"}}}}";

        String actual = obj.toStream().filter(node -> {
            return node.has("sample");
        }).forEach(node -> {
            node.put("aKey", "aValue");
        }).stringify();

        System.out.println(obj.toString());

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void streamMapFilterTest() throws FileNotFoundException {
        System.out.println("\n--- Stream Map Filter Test ---");
        String path = System.getProperty("user.dir");
        File xmlFile = new File(path + "/src/test/xmlFiles/jani_note.xml");
        Reader reader = new FileReader(xmlFile);
        JSONObject obj = XML.toJSONObject(reader);

        List<Object> expectedList = new ArrayList<>();
        expectedList.add(new JSONObject("{\"sample\":\"a\"}"));
        expectedList.add(new JSONObject("{\"note\":\"hello\"}"));

        List<Object> list2 = obj.toStream().map(node -> {
            if (node.has("test")) return (JSONObject) node.getJSONObject("test");
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());

        for (Object item: list2) {
            System.out.println(item);
        }

        Assert.assertEquals(list2.get(0).toString(), expectedList.get(0).toString());
        Assert.assertEquals(list2.get(1).toString(), expectedList.get(1).toString());
    }
}
