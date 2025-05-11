# NotBooking – Projet Java de Réservation d’Hébergements

_NotBooking_ est une application Java développée dans le cadre d’un **projet universitaire** de l'ECE Lyon. Il s'agit d'une plateforme de réservation d’hébergements (hôtels, maisons, etc.) qui permet aux utilisateurs de rechercher un logement, consulter les détails, réserver et payer en ligne, tout en bénéficiant de réductions. Une interface d’administration permet également de visualiser les statistiques de réservation.

## 🎯 Objectifs du projet

- Offrir une expérience utilisateur fluide pour la recherche et la réservation d’hébergements.
- Implémenter une architecture MVC propre et bien structurée (Model – View – Controller).
- Gérer les accès utilisateurs selon leur rôle (client ou administrateur).
- Proposer des statistiques interactives avec des graphiques via JFreeChart.
- Réaliser un découpage fonctionnel clair pour un travail en équipe efficace.

---

## 👥 Équipe de développement

Nous avons divisé le projet en **4 modules fonctionnels**, chacun pris en charge par un membre de l’équipe :

### 🧑‍💻 Utilisateurs et Connexion – **Antoine Coatmeur**
- Création de la classe `Utilisateur`
- Gestion de l'inscription / connexion
- Sécurisation des accès selon le type d'utilisateur (client ou admin)
- DAO pour la communication avec la base de données
- Interfaces d’inscription et de connexion

---

### 🏠 Hébergements et Recherche – **Thibault Rabbé**
- Création de la classe `Hebergement`
- Moteur de recherche multi-critères (ville, date, capacité…)
- Interface de consultation des résultats et fiches de détails
- DAO des hébergements
- Contrôleur de recherche

---

### 🧾 Réservation et Paiement – **Arnaud Pechier**
- Création de la classe `Reservation`
- Interface de réservation (sélection des dates, nombre de personnes…)
- Calcul du tarif avec ou sans réduction
- Gestion des paiements et carte bancaire
- Génération d’une confirmation de réservation
- DAO pour les réservations

---

### 📊 Statistiques et Historique – **Thomas Prigent**
- Récupération des données de réservation
- Création de statistiques visuelles (par mois, par utilisateur…)
- Intégration de **JFreeChart** pour les graphiques
- Interface graphique pour l’administration
- Contrôleur des statistiques

---

## 📁 Structure du projet

- `JAVAmodel/` : contient toutes les classes métiers (`Utilisateur`, `Hebergement`, `Reservation`, etc.)
- `JAVAdao/` : Data Access Object pour les opérations SQL
- `JAVAcontroller/` : contrôleurs liés aux vues JavaFX
- `projet_java/` : fichiers FXML, feuilles de style CSS, et autres ressources
- `images/` : images utilisées dans l’interface
- `scriptbdd.txt` : **script SQL** pour générer la base de données utilisée dans le projet

---

## 🧠 Technologies utilisées

- Java 17+
- JavaFX (UI)
- JFreeChart (statistiques)
- JDBC + MySQL (base de données)
- Maven (gestion de dépendances)
- CSS personnalisé (thème visuel)

---

## 📦 Base de données

Le script SQL de la base de données est fourni dans le fichier scriptbdd.txt

---

## 🚀 Lancement de l'application

Assurez-vous d’avoir :

1. Importé le projet dans IntelliJ IDEA ou un autre IDE Java.
2. Configuré le chemin vers JavaFX dans les options de lancement.
3. Créé une configuration d'exécution (Run Configuration) liée à la classe `BookingApp` :
    - **Main class** : `com.example.projet_java.BookingApp`
    - **VM Options** :
      ```
      --module-path chemin/vers/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml
      ```
      Remplacez `chemin/vers/javafx-sdk/lib` par le chemin réel vers votre SDK JavaFX.

4. Lancez simplement l’application via cette configuration.


---
## 📈 Améliorations futures

Voici quelques pistes d'évolution possibles du projet NotBooking :

- ✅ **Ajout automatique de codes de réduction** : par rapport à l'ancienneté d'un client ou par une demande.
- 🔍 **Filtres de recherche avancés** : intégrer des filtres comme le prix, les équipements, les avis, la distance, etc.
- 🌟 **Système d’avis** : autoriser les utilisateurs à laisser une note et un commentaire après leur séjour.
- 🧾 **Gestion de la validation des réservations** : permettre au propriétaire de valider ou refuser une réservation.
- 💰 **Portefeuille dynamique** : suivre les paiements, soldes, remboursements ou bonus de fidélité des utilisateurs.
---
## 🙌 Remerciements

Un grand merci à tous les membres de l’équipe pour leur implication, leur rigueur et leur entraide tout au long du développement.

Nous remercions également **M. Baret** pour sa disponibilité et son accompagnement, toujours présent pour nous guider lorsque nous rencontrions des difficultés techniques ou méthodologiques.

---
### 📬 Contact

En cas de problème avec le lancement de l'application ou pour toute question concernant le projet, vous pouvez contacter l'équipe :

- **Thibault Rabbé** – [thibault.rabbe@edu.ece.fr](mailto:thibault.rabbe@edu.ece.fr)


> Projet réalisé dans le cadre de notre cursus d'ingénieur.

---
