import java.util.List;
import java.util.ArrayList;

public class AutoComplete {

    private static final int ALPHABET_SIZE = 26;
    private static final char FIRST_LETTER = 'a';
    private static final char LAST_LETTER = 'z';

    private static class Node {
        private final Node[] children = new Node[ALPHABET_SIZE];
        private boolean isEndOfWord;

        private boolean hasChildren() {
            for (Node child : children) {
                if (child != null) {
                    return true;
                }
            }
            return false;
        }
    }

    private final Node root;

    public AutoComplete() {
        root = new Node();
    }

    /**
     * Ajoute un mot au dictionnaire.
     *
     * @param word le mot à ajouter
     */
    public void insert(String word) {
        if (!isValidWord(word)) {
            return;
        }
        Node current = root;
        for (int i = 0; i < word.length(); i++) {
            int index = indexOf(word.charAt(i));
            if (current.children[index] == null) {
                current.children[index] = new Node();
            }
            current = current.children[index];
        }
        current.isEndOfWord = true;
    }

    /**
     * Vérifie si un mot existe dans le dictionnaire.
     *
     * @param word le mot recherché
     * @return true si le mot existe, false sinon
     */
    public boolean contains(String word) {
        if (!isValidWord(word)) {
            return false;
        }
        Node node = findNode(word);
        return node != null && node.isEndOfWord;
    }

    /**
     * Supprime un mot du dictionnaire.
     *
     * @param word le mot à supprimer
     * @return true si le mot existait et a été supprimé,
     *         false sinon
     */
    public boolean delete(String word) {
        if (!contains(word)) {
            return false;
        }
        delete(root, word, 0);
        return true;
    }

    /**
     * Retire récursivement le mot à partir du nœud donné.
     *
     * @return true si le nœud courant est devenu inutile et peut être retiré par son parent
     */
    private boolean delete(Node node, String word, int depth) {
        if (depth == word.length()) {
            node.isEndOfWord = false;
        } else {
            int index = indexOf(word.charAt(depth));
            Node child = node.children[index];
            if (delete(child, word, depth + 1)) {
                node.children[index] = null;
            }
        }
        return node != root && !node.isEndOfWord && !node.hasChildren();
    }

    /**
     * Retourne tous les mots qui commencent par le préfixe donné.
     *
     * @param prefix le préfixe recherché
     * @return la liste des mots correspondant au préfixe
     */
    public List<String> startsWith(String prefix) {
        List<String> results = new ArrayList<>();
        if (prefix == null || !hasOnlyValidLetters(prefix)) {
            return results;
        }
        Node prefixNode = findNode(prefix);
        if (prefixNode != null) {
            collectWords(prefixNode, new StringBuilder(prefix), results);
        }
        return results;
    }

    private void collectWords(Node node, StringBuilder currentWord, List<String> results) {
        if (node.isEndOfWord) {
            results.add(currentWord.toString());
        }
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            if (node.children[i] != null) {
                currentWord.append((char) (FIRST_LETTER + i));
                collectWords(node.children[i], currentWord, results);
                currentWord.deleteCharAt(currentWord.length() - 1);
            }
        }
    }

    /**
     * Suit le chemin correspondant à la chaîne donnée.
     *
     * @return le nœud atteint, ou null si le chemin n'existe pas
     */
    private Node findNode(String path) {
        Node current = root;
        for (int i = 0; i < path.length(); i++) {
            current = current.children[indexOf(path.charAt(i))];
            if (current == null) {
                return null;
            }
        }
        return current;
    }

    private static int indexOf(char letter) {
        return letter - FIRST_LETTER;
    }

    private static boolean isValidWord(String word) {
        return word != null && !word.isEmpty() && hasOnlyValidLetters(word);
    }

    private static boolean hasOnlyValidLetters(String text) {
        for (int i = 0; i < text.length(); i++) {
            char letter = text.charAt(i);
            if (letter < FIRST_LETTER || letter > LAST_LETTER) {
                return false;
            }
        }
        return true;
    }

    private static int testsPassed = 0;
    private static int testsFailed = 0;

    private static void check(String description, Object expected, Object actual) {
        if (expected.equals(actual)) {
            testsPassed++;
            System.out.println("[OK]    " + description);
        } else {
            testsFailed++;
            System.out.println("[ECHEC] " + description + " -> attendu " + expected + ", obtenu " + actual);
        }
    }

    private static List<String> sorted(List<String> words) {
        List<String> copy = new ArrayList<>(words);
        copy.sort(null);
        return copy;
    }

    public static void main(String[] args) {
        AutoComplete ac = new AutoComplete();

        ac.insert("chat");
        ac.insert("chateau");
        ac.insert("chapeau");
        ac.insert("chaise");
        ac.insert("chien");
        ac.insert("chocolat");

        // Tests de base
        System.out.println(ac.contains("chat"));
        System.out.println(ac.contains("chien"));

        //Autres tests
        System.out.println("--- contains ---");

        check("contains(\"chat\")", true, ac.contains("chat"));
        check("contains(\"chien\")", true, ac.contains("chien"));
        check("contains(\"cha\") : préfixe mais pas un mot", false, ac.contains("cha"));
        check("contains(\"cheval\") : absent", false, ac.contains("cheval"));
        check("contains(\"chateaux\") : plus long qu'un mot existant", false, ac.contains("chateaux"));
        check("contains(\"\") : mot vide", false, ac.contains(""));
        check("contains(null)", false, ac.contains(null));

        System.out.println("--- startsWith ---");
        check("startsWith(\"cha\")", sorted(List.of("chat", "chateau", "chapeau", "chaise")),
                sorted(ac.startsWith("cha")));
        check("startsWith(\"chi\")", List.of("chien"), ac.startsWith("chi"));
        check("startsWith(\"ch\")",
                sorted(List.of("chat", "chateau", "chapeau", "chaise", "chien", "chocolat")),
                sorted(ac.startsWith("ch")));
        check("startsWith(\"chat\") : le préfixe est lui-même un mot", sorted(List.of("chat", "chateau")),
                sorted(ac.startsWith("chat")));
        check("startsWith(\"chocolat\") : préfixe = mot complet, sans suite", List.of("chocolat"),
                ac.startsWith("chocolat"));
        check("startsWith(\"xyz\") : aucun mot", List.of(), ac.startsWith("xyz"));
        check("startsWith(\"chocolats\") : plus long que tout mot", List.of(), ac.startsWith("chocolats"));
        check("startsWith(\"\") : tous les mots", 6, ac.startsWith("").size());

        System.out.println("--- insert ---");
        ac.insert("chat");
        check("insert d'un doublon : \"chat\" apparaît une seule fois", 1,
                (int) ac.startsWith("chat").stream().filter("chat"::equals).count());
        ac.insert("Chat");
        check("insert(\"Chat\") ignoré (majuscule)", false, ac.contains("Chat"));
        ac.insert("");
        check("insert(\"\") ignoré", 6, ac.startsWith("").size());

        System.out.println("--- delete ---");
        check("delete(\"chat\") : mot préfixe d'un autre", true, ac.delete("chat"));
        check("  \"chat\" n'existe plus", false, ac.contains("chat"));
        check("  \"chateau\" existe toujours", true, ac.contains("chateau"));

        check("delete(\"chateau\") : mot qui prolonge un autre", true, ac.delete("chateau"));
        check("  \"chateau\" n'existe plus", false, ac.contains("chateau"));
        check("  startsWith(\"chat\") vide", List.of(), ac.startsWith("chat"));
        check("  \"chapeau\" existe toujours (branche voisine)", true, ac.contains("chapeau"));

        check("delete(\"cheval\") : mot absent", false, ac.delete("cheval"));
        check("delete(\"cha\") : préfixe qui n'est pas un mot", false, ac.delete("cha"));
        check("  \"chapeau\" et \"chaise\" intacts", sorted(List.of("chapeau", "chaise")),
                sorted(ac.startsWith("cha")));
        check("delete(\"chien\") : 1er appel", true, ac.delete("chien"));
        check("delete(\"chien\") : 2e appel, déjà supprimé", false, ac.delete("chien"));

        ac.insert("chat");
        check("réinsertion de \"chat\" après suppression", true, ac.contains("chat"));

        System.out.println();
        System.out.println(testsPassed + " tests réussis, " + testsFailed + " échoués");
    }
}
