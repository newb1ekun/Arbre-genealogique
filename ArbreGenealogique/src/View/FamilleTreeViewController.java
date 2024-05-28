package View;

import java.time.LocalDate;

import arbre.*;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class FamilleTreeViewController {

    @FXML
    private TreeView<Personne> treeView;

    public void initialize() {
        // Création des instances de Personne
        Personne grandPere = new Personne(1, "Doe", "John", LocalDate.of(1940, 1, 1), null, "Américaine", "M", null, null, null);
        Personne grandMere = new Personne(2, "Doe", "Jane", LocalDate.of(1945, 2, 1), null, "Américaine", "F", null, null, null);
        Personne pere = new Personne(3, "Doe", "Jim", LocalDate.of(1970, 3, 1), null, "Américaine", "M", grandPere, grandMere, null);
        Personne mere = new Personne(4, "Doe", "Jill", LocalDate.of(1975, 4, 1), null, "Américaine", "F", null, null, null);
        Personne enfant1 = new Personne(5, "Doe", "Jack", LocalDate.of(2000, 5, 1), null, "Américaine", "M", pere, mere, null);
        Personne enfant2 = new Personne(6, "Doe", "Bob", LocalDate.of(2000, 5, 1), null, "Américaine", "M",pere, mere, null);

        // Création des TreeItems pour chaque personne
        TreeItem<Personne> rootItem = new TreeItem<>(grandPere);
        TreeItem<Personne> pereItem = new TreeItem<>(pere);
        TreeItem<Personne> mereItem = new TreeItem<>(mere);
        TreeItem<Personne> enfant1Item = new TreeItem<>(enfant1);
        TreeItem<Personne> enfant2Item = new TreeItem<>(enfant2);

        // Construction de l'arbre
        rootItem.getChildren().add(pereItem);
        pereItem.getChildren().addAll(enfant1Item, enfant2Item);
        pereItem.getChildren().add(mereItem);  // Ajoute le parent du côté de la mère

        // Déplier le nœud racine
        rootItem.setExpanded(true);

        // Affecter l'arbre au TreeView
        treeView.setRoot(rootItem);
    }
}