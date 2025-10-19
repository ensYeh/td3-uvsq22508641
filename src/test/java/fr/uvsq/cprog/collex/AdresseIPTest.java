package fr.uvsq.cprog.collex;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AdresseIPTest {
    @Test
    public void parseReussi() {
        AdresseIP a = new AdresseIP("192.168.0.1");
        assertEquals("192.168.0.1", a.asString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseNonReussi() {
        new AdresseIP("999.0.0.1");
    }
}
