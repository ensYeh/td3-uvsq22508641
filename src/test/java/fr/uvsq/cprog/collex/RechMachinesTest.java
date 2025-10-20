package fr.uvsq.cprog.collex.commands;

import fr.uvsq.cprog.collex.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class RechMachinesTest {

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
            dns.addItem(new AdresseIP("193.51.31.154"), new NomMachine("poste.uvsq.fr"));
            dns.addItem(new AdresseIP("193.51.25.12"), new NomMachine("ecampus.uvsq.fr"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testRechMachinesNom() throws Exception {
        RechMachines cmd = new RechMachines(dns, "uvsq.fr", false);
        String result = cmd.execute();

        String[] lignes = result.split(System.lineSeparator());
        assertEquals(3, lignes.length);
        assertTrue(lignes[0].contains("ecampus.uvsq.fr"));
        assertTrue(lignes[1].contains("poste.uvsq.fr"));
        assertTrue(lignes[2].contains("www.uvsq.fr"));
    }

    @Test
    public void testRechMachinesIp() throws Exception {
        RechMachines cmd = new RechMachines(dns, "uvsq.fr", true);
        String result = cmd.execute();

        String[] lignes = result.split(System.lineSeparator());
        assertEquals(3, lignes.length);
        assertEquals("193.51.25.12 ecampus.uvsq.fr", lignes[0]);
        assertEquals("193.51.31.90 www.uvsq.fr", lignes[1]);
        assertEquals("193.51.31.154 poste.uvsq.fr", lignes[2]);
    }

    @Test
    public void testRechMachinesDomaineVide() throws Exception {
        RechMachines cmd = new RechMachines(dns, "inconnu.fr", false);
        String result = cmd.execute();
        assertEquals("", result);
    }
}
