public class MysteryTest {
    public static void main(String[] args) {


        //------TEST 1A: Diff entre arbre de recherche et AVL
        MysteryTree tree = new MysteryTree();
        int n = 31;
        for(int i = 1; i <= n; i++) {
            tree.insert(i);
        }
        System.out.println("EXPÉRIENCE 1A");
        System.out.println("Hauteur après " + n + " insertions croissantes : " + tree.height());



        //------TEST 1B: Diff entre arbre de recherche et AVL
        MysteryTree tree2 = new MysteryTree();
        int n2 = 63;
        for(int i = 1; i <= n2; i++) {
            tree2.insert(i);

        }
        System.out.println("EXPÉRIENCE 1B");
        System.out.println("Hauteur après " + n2 + " insertions croissantes : " + tree2.height());


    // Experience 2: Diff entre AVL et Splay

        MysteryTree tree3 = new MysteryTree();
        for (int i = 1; i <= n; i++) {
            tree3.insert(i);
        }

        System.out.println("\n--- EXPÉRIENCE 2 : Test de recherche ---");
        System.out.println("Hauteur initiale avant recherche de l'élément 1 : " + tree3.height());

        // On cherche plusieurs fois l'élément 1 (qui est tout au fond à gauche)
        for (int k = 0; k < 10; k++) {
            tree3.search(1);
        }

        System.out.println("Hauteur après 10 recherches consécutives de l'élément 1 : " + tree3.height());
    }
}