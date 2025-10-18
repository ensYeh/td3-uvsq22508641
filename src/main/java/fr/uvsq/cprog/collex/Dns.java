package fr.uvsq.cprog.collex;

public class Dns {
    private final Map<String, DnsItem> byName = new HashMap<>();
    private final Map<String, DnsItem> byIp = new HashMap<>();
    private final Path dbPath;

    public Dns() throws IOException {
        Properties props = new Properties();
        try (var is = Dns.class.getClassLoader().getResourceAsStream("dns.properties")) {
            if (is == null) {
                throw new IOException("Fichier de properties introuvable");
            }
            props.load(is);
        }
        String filename = props.getProperty("dns.file");
        if (filename == null) {
            throw new IOException("Propriete dns.file absente");
        }
        dbPath = Paths.get(filename);
        if (Files.exists(dbPath)) {
            List<String> lines = Files.readAllLines(dbPath, StandardCharsets.UTF_8);
            for (String l : lines) {
                String s = l.trim();
                if (s.isEmpty()) {
                    continue;
                }
                String[] parts = s.split("\\s+");
                if (parts.length != 2) {
                    continue; 
                }
                NomMachine nm = new NomMachine(parts[0]);
                AdresseIP aip = new AdresseIP(parts[1]);
                DnsItem item = new DnsItem(nm, aip);
                byName.put(nm.asString().toLowerCase(), item);
                byIp.put(aip.asString(), item);
            }
        } else {
            Path parent = dbPath.getParent();
            if (parent != null && !Files.exists(parent)) {
                Files.createDirectories(parent);
            }
            Files.createFile(dbPath);
        }
    }

    public DnsItem getItem(final AdresseIP ip) {
        Objects.requireNonNull(ip);
        return byIp.get(ip.asString());
    }

    public DnsItem getItem(final NomMachine nom) {
        Objects.requireNonNull(nom);
        return byName.get(nom.asString().toLowerCase());
    }

    public List<DnsItem> getItems(final String domaine) {
        if (domaine == null) {
            return Collections.emptyList();
        }
        List<DnsItem> result = byName.values().stream()
                .filter(i -> i.getNom().getDomaine().equalsIgnoreCase(domaine))
                .collect(Collectors.toList());
        return result;
    }



}