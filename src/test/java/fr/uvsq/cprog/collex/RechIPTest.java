package fr.uvsq.cprog.collex.commands;

import fr.uvsq.cprog.collex.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class RechIPTest {

    private Dns dns;

    @Before
    public void setUp() throws IOException {
        dns = new Dns() {
            @Override
            public void addItem(AdresseIP ip, NomMachine nom) throws IOException {
                super.addItem(ip, nom);
            }
        };

        try {
            dns.addItem(new AdresseIP("193.51.31.90"), new NomMachine("www.uvsq.fr"));
            dns.addItem(new AdresseIP("193.51.25.12"), new NomMachine("ecampus.uvsq.fr"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testRechIPExist() {
        RechIP cmd = new RechIP(dns, "193.51.31.90");
        String result = null;
        try {
            result = cmd.execute();
        } catch (Exception e) {
            fail("Aucune exception attendue");
        }
        assertEquals("www.uvsq.fr", result);
    }

    @Test
    public void testRechIPInexist() {
        RechIP cmd = new RechIP(dns, "1.2.3.4");
        String result = null;
        try {
            result = cmd.execute();
        } catch (Exception e) {
            fail("Aucune exception attendue");
        }
        assertEquals("ERREUR : introuvable", result);
    }
}
