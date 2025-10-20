package fr.uvsq.cprog.collex;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;

import static org.junit.Assert.*;

public class DnsTest {

    private Path tempDir;
    private Path dbFile;
    private Path propsFile;
    private Dns dns;

    @Before
    public void setUp() throws IOException {
        tempDir = Files.createTempDirectory("dns_test_");

        dbFile = tempDir.resolve("dns_db.txt");
        Files.write(dbFile, List.of(
                "www.uvsq.fr 193.51.31.90",
                "ecampus.uvsq.fr 193.51.25.12",
                "poste.uvsq.fr 193.51.31.154"
        ), StandardCharsets.UTF_8);

        propsFile = tempDir.resolve("dns.properties");
        Files.write(propsFile, List.of("dns.file=" + dbFile.toAbsolutePath()), StandardCharsets.UTF_8);

        System.setProperty("java.class.path", tempDir.toString());

        Thread.currentThread().setContextClassLoader(new ClassLoader() {
            @Override
            public java.io.InputStream getResourceAsStream(String name) {
                if (name.equals("dns.properties")) {
                    try {
                        return Files.newInputStream(propsFile);
                    } catch (IOException e) {
                        return null;
                    }
                }
                return super.getResourceAsStream(name);
            }
        });

        dns = new Dns();
    }

    @After
    public void tearDown() throws IOException {
        Files.walk(tempDir)
                .sorted((a, b) -> b.compareTo(a))
                .forEach(p -> {
                    try {
                        Files.deleteIfExists(p);
                    } catch (IOException ignored) { }
                });
    }

    @Test
    public void testGetItemByName() {
        DnsItem item = dns.getItem(new NomMachine("www.uvsq.fr"));
        assertNotNull(item);
        assertEquals("193.51.31.90", item.getIp().asString());
    }

    @Test
    public void testGetItemByIp() {
        DnsItem item = dns.getItem(new AdresseIP("193.51.25.12"));
        assertNotNull(item);
        assertEquals("ecampus.uvsq.fr", item.getNom().asString());
    }

    @Test
    public void testGetItemsByDomain() {
        List<DnsItem> items = dns.getItems("uvsq.fr");
        assertEquals(3, items.size());
    }

    @Test
    public void testAddItemSuccess() throws IOException {
        AdresseIP ip = new AdresseIP("193.51.25.24");
        NomMachine nom = new NomMachine("pikachu.uvsq.fr");
        dns.addItem(ip, nom);

        DnsItem item = dns.getItem(nom);
        assertNotNull(item);
        assertEquals("193.51.25.24", item.getIp().asString());

        List<String> lines = Files.readAllLines(dbFile, StandardCharsets.UTF_8);
        assertTrue(lines.stream().anyMatch(l -> l.contains("pikachu.uvsq.fr")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddItemDuplicateName() throws IOException {
        dns.addItem(new AdresseIP("193.51.99.99"), new NomMachine("www.uvsq.fr"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddItemDuplicateIp() throws IOException {
        dns.addItem(new AdresseIP("193.51.31.90"), new NomMachine("autre.uvsq.fr"));
    }
}
