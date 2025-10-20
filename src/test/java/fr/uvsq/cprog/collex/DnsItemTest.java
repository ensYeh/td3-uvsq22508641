package fr.uvsq.cprog.collex;

import org.junit.Test;

import static org.junit.Assert.*;

public class DnsItemTest {

    @Test
    public void testCreationEtGetters() {
        NomMachine nom = new NomMachine("www.uvsq.fr");
        AdresseIP ip = new AdresseIP("193.51.31.90");

        DnsItem item = new DnsItem(nom, ip);

        assertEquals(nom, item.getNom());
        assertEquals(ip, item.getIp());
    }

    @Test
    public void testToString() {
        NomMachine nom = new NomMachine("ecampus.uvsq.fr");
        AdresseIP ip = new AdresseIP("193.51.25.12");

        DnsItem item = new DnsItem(nom, ip);

        String expected = "ecampus.uvsq.fr 193.51.25.12";
        assertEquals(expected, item.toString());
    }

    @Test(expected = NullPointerException.class)
    public void testNomNull() {
        new DnsItem(null, new AdresseIP("193.51.31.90"));
    }

    @Test(expected = NullPointerException.class)
    public void testIpNull() {
        new DnsItem(new NomMachine("www.uvsq.fr"), null);
    }
}
