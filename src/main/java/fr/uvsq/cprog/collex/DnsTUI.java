package fr.uvsq.cprog.collex;

public class DnsTUI {
    private final Dns dns;
    private final Scanner scanner;

    public DnsTUI(final Dns dns) {
        this.dns = dns;
        this.scanner = new Scanner(System.in);
    }

    public Commande nextCommande() {
        System.out.print("> ");
        String line = scanner.nextLine();
        if (line == null) {
            return new QuitCommande();
        }
        line = line.trim();
        if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit")) {
            return new QuitCommande();
        }
        if (line.startsWith("add ")) {
            String[] parts = line.substring(4).trim().split("\\s+");
            if (parts.length != 2) {
                return () -> "ERREUR : syntaxe add ip nom.qualifie";
            }
            return new AddCommande(dns, parts[0], parts[1]);
        }
        if (line.startsWith("ls")) {
            String rest = line.substring(2).trim();
            boolean byIp = false;
            if (rest.startsWith("-a")) {
                byIp = true;
                rest = rest.substring(2).trim();
            }
            if (rest.isEmpty()) {
                return () -> "";
            }
            return new LsCommande(dns, rest, byIp);
        }
        if (line.matches("[0-9.]+")) {
            return new FindByIpCommande(dns, line);
        }
        return new FindByNameCommande(dns, line);
    }

    public void affiche(final String s) {
        if (s == null || s.isEmpty()) {
            return;
        }
        if (s.equals("QUIT")) {
            return;
        }
        System.out.println(s);
    }
}