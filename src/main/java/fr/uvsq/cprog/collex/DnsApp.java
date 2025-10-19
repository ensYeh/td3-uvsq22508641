package fr.uvsq.cprog.collex;

public final class DnsApp {
    public static void main(final String[] args) {
        try {
            Dns dns = new Dns();
            DnsTUI tui = new DnsTUI(dns);
            boolean running = true;
            while (running) {
                Commande cmd = tui.nextCommande();
                String out = cmd.execute();
                if (out != null && out.equals("QUIT")) {
                    running = false;
                    break;
                }
                tui.affiche(out);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}