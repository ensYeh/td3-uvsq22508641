package fr.uvsq.cprog.collex.commands;
import fr.uvsq.cprog.collex.Commande;

public class QuittApp implements Commande {
    @Override
    public String execute() {
        return "QUIT";
    }
}
