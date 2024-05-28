package arbre;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import java.time.LocalDate;

public class GenealogieController {

    @FXML
    private TreeView<Personne> treeView;

    public void initialize() {
        // Créer des exemples de données
        Personne grandPere = new Personne(1, "Doe", "John", LocalDate.of(1940, 1, 1), null, "Américaine", "M", null, null, null, false, null);
        Personne grandMere = new Personne(2, "Doe", "Jane", LocalDate.of(1945, 2, 1), null, "Américaine", "F", null, null, null, false, null);
        Personne pere = new Personne(3, "Doe", "Jim", LocalDate.of(1970, 3, 1), null, "Américaine", "M", grandPere, grandMere, null, false, null);
        Personne mere = new Personne(4, "Doe", "Jill", LocalDate.of(1975, 4, 1), null, "Américaine", "F", null, null, null, false, null);
        Personne enfant = new Personne(5, "Doe", "Jack", LocalDate.of(2000, 5, 1), null, "Américaine", "M", pere, mere, null, false, null);

        pere.ajoutEnfants(enfant);
        grandPere.ajoutEnfants(pere);

        // Créer la vue en arbre
        TreeItem<Personne> rootItem = new TreeItem<>(grandPere);
        createTree(rootItem, grandPere);

        treeView.setRoot(rootItem);
        treeView.setShowRoot(true);
    }

    private void createTree(TreeItem<Personne> parentItem, Personne parent) {
        for (Personne enfant : parent.getEnfants()) {
            TreeItem<Personne> childItem = new TreeItem<>(enfant);
            parentItem.getChildren().add(childItem);
            createTree(childItem, enfant);
        }
    }
}
