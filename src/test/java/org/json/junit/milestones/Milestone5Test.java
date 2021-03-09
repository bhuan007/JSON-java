package org.json.junit.milestones;

import org.json.JSONObject;
import org.json.XML;
import org.junit.Test;
import org.junit.Assert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.concurrent.Semaphore;

public class Milestone5Test {


    private JSONObject actualObject = null;

    @Test
    public void toJSONAsyncTest() throws FileNotFoundException, InterruptedException {
        Semaphore semaphore = new Semaphore(1);
        String path = System.getProperty("user.dir");
        File xmlFile = new File(path + "/src/test/xmlFiles/jani_note.xml");
        Reader reader = new FileReader(xmlFile);
        semaphore.acquire(1);

        String expectedString = "{\"note\":{\"test\":{\"note\":\"hello\"},\"heading\":\"Reminder\",\"from\":{\"b\":\"d\"},\"to\":\"Tove\",\"body\":\"Don't forget me this weekend!\",\"sample\":{\"test\":{\"sample\":\"a\"}}}}";

        XML.toJSONObject(reader, new XML.ToJSONCompletedInterface() {
            @Override
            public void onObjectReturned(JSONObject jsonObject) {
                actualObject = jsonObject;
                semaphore.release();
            }
        }, new XML.ToJSONExceptionInterface() {
            @Override
            public void onException(Exception e) {
                System.out.println("exception thrown");
                System.out.println(e.getMessage());
            }
        });

        while (true) {
            if (semaphore.availablePermits() > 0) break;
        }

        Assert.assertEquals(expectedString, actualObject.toString());

    }

    @Test (expected = FileNotFoundException.class)
    public void toJSONAsyncTestException() throws FileNotFoundException {
        String path = System.getProperty("user.dir");
        File xmlFile = new File(path + "/src/test/xmlFiles/fileDoesNotExist");
        Reader reader = new FileReader(xmlFile);
        XML.toJSONObject(reader, new XML.ToJSONCompletedInterface() {
            @Override
            public void onObjectReturned(JSONObject jsonObject) {
                System.out.println("yes");
                System.out.println(jsonObject.toString());
            }
        }, new XML.ToJSONExceptionInterface() {
            @Override
            public void onException(Exception e) {
                System.out.println("exception thrown");
                System.out.println(e.getMessage());
            }
        });
    }
}
