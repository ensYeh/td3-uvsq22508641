package fr.uvsq.cprog.collex;

import fr.uvsq.cprog.collex.AjoutItem.AjoutItem;
import fr.uvsq.cprog.collex.QuittApp.QuittApp;
import fr.uvsq.cprog.collex.RechIP.RechIP;
import fr.uvsq.cprog.collex.RechNom.RechNom;
import fr.uvsq.cprog.collex.RechMachines.RechMachines;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;


public class DnsTUITest {

    private Dns dns;

    @Before
    public void setUp() throws Exception {
        dns = new Dns() {
        };
    }

    @Test
    public void testQuit() {
        String input = "quit\n";
        DnsTUI tui = new DnsTUI(dns) {
            { super.scanner = new java.util.Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))); }
        };
        Commande cmd = tui.nextCommande();
        assertTrue(cmd instanceof QuittApp);
    }

    @Test
    public void testAdd() {
        String input = "add 193.51.25.24 pikachu.uvsq.fr\n";
        DnsTUI tui = new DnsTUI(dns) {
            { super.scanner = new java.util.Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))); }
        };
        Commande cmd = tui.nextCommande();
        assertTrue(cmd instanceof AjoutItem);
    }

    @Test
    public void testLsNom() {
        String input = "ls uvsq.fr\n";
        DnsTUI tui = new DnsTUI(dns) {
            { super.scanner = new java.util.Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))); }
        };
        Commande cmd = tui.nextCommande();
        assertTrue(cmd instanceof RechMachines);
    }

    @Test
    public void testLsAvecOptionA() {
        String input = "ls -a uvsq.fr\n";
        DnsTUI tui = new DnsTUI(dns) {
            { super.scanner = new java.util.Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))); }
        };
        Commande cmd = tui.nextCommande();
        assertTrue(cmd instanceof RechMachines);
        assertTrue(((RechMachines) cmd).isByIp());
    }

    @Test
    public void testAdresseIP() {
        String input = "193.51.31.90\n";
        DnsTUI tui = new DnsTUI(dns) {
            { super.scanner = new java.util.Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))); }
        };
        Commande cmd = tui.nextCommande();
        assertTrue(cmd instanceof RechIP);
    }

    @Test
    public void testNomMachine() {
        String input = "www.uvsq.fr\n";
        DnsTUI tui = new DnsTUI(dns) {
            { super.scanner = new java.util.Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))); }
        };
        Commande cmd = tui.nextCommande();
        assertTrue(cmd instanceof RechNom);
    }

    @Test
    public void testAffiche() {
        DnsTUI tui = new DnsTUI(dns);
        tui.affiche("Hello");
    }

    @Test
    public void testAfficheVide() {
        DnsTUI tui = new DnsTUI(dns);
        tui.affiche("");
        tui.affiche(null);
        tui.affiche("QUIT"); 
    }
}
