package fr.uvsq.cprog.collex;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NomMachineTest {
    @Test
    public void parseReussi() {
        NomMachine n = new NomMachine("www.uvsq.fr");
        assertEquals("www", n.getMachine());
        assertEquals("uvsq.fr", n.getDomaine());
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseNonReussi() {
        new NomMachine("localhost");
    }
}