package fr.uvsq.cprog.collex;
package fr.uvsq.cprog.collex.AjoutItem;
package fr.uvsq.cprog.collex.RechIP;
package fr.uvsq.cprog.collex.RechNom;
package fr.uvsq.cprog.collex.RechMachines;
package fr.uvsq.cprog.collex.QuittApp;

import java.util.Scanner;

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
            return new QuittApp();
        }
        line = line.trim();
        if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit")) {
            return new QuittApp();
        }
        if (line.startsWith("add ")) {
            String[] parts = line.substring(4).trim().split("\\s+");
            if (parts.length != 2) {
                return () -> "ERREUR : syntaxe add ip nom.qualifie";
            }
            return new AjoutItem(dns, parts[0], parts[1]);
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
            return new RechMachines(dns, rest, byIp);
        }
        if (line.matches("[0-9.]+")) {
            return new RechIP(dns, line);
        }
        return new RechNom(dns, line);
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