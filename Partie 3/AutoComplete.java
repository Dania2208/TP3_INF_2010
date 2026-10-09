import java.util.List;

public class AutoComplete {

    private static class Node {
        // TODO
    }

    private final Node root;

    public AutoComplete() {
        // TODO
    }

    /**
     * Ajoute un mot au dictionnaire.
     *
     * @param word le mot à ajouter
     */
    public void insert(String word) {
        // TODO
    }

    /**
     * Vérifie si un mot existe dans le dictionnaire.
     *
     * @param word le mot recherché
     * @return true si le mot existe, false sinon
     */
    public boolean contains(String word) {
        // TODO
        return false;
    }

    /**
     * Supprime un mot du dictionnaire.
     *
     * @param word le mot à supprimer
     * @return true si le mot existait et a été supprimé,
     *         false sinon
     */
    public boolean delete(String word) {
        // TODO
        return false;
    }

    /**
     * Retourne tous les mots qui commencent par le préfixe donné.
     *
     * @param prefix le préfixe recherché
     * @return la liste des mots correspondant au préfixe
     */
    public List<String> startsWith(String prefix) {
        // TODO
        return null;
    }

    public static void main(String[] args) {
        AutoComplete ac = new AutoComplete();

        ac.insert("chat");
        ac.insert("chateau");
        ac.insert("chapeau");
        ac.insert("chaise");
        ac.insert("chien");

        // Tests de base
        System.out.println(ac.contains("chat"));
        System.out.println(ac.contains("chien"));

        // TODO: ajouter vos propres tests
    }
}
