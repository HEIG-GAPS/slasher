# Slasher

## But

_Slasher_ est un IDE pour bases de données. Il permet de se connecter à n'importe quel SGBD disponible sur le marché. Il peut être agrémenté de fonctionnalités supplémentaires à travers des modules externes. Il est fourni de base avec un éditeur SQL avancé.

## Structure technique de l'IDE

L'IDE est composé des trois briques suivantes :

1. *l'application* qui permet gère l'interface graphique et met à disposition les outils nécessaires pour ouvrir et enregistrer un projet. Elle met également à disposition les outils nécessaires à certaines tâches, tel que _Apache POI_ pour la gestion des fichiers Excel, les mises à jour, etc.
2. *le gestionnaire de _Drivers_* qui permet de gérer les _Drivers_ pour les connexions aux bases de données.
3. *le gestionnaire de modules externes* qui permet de gérer l'addition de nouvelles fonctionnalités.

En outre, l'application gère également les connexions aux bases de données, en stockant les différents paramètres et en sécurisant le mot de passe si ce dernier doit être stocké.

A l'exception des fichiers SQL, tous les fichiers sont gérés avec une notion de projet. Un projet est composé d'un module externe et d'un Driver. Lors que le fichier est ouvert, une vérification préalable est faite par l'IDE de sorte à s'assurer que le module externe ainsi que le Driver sont disponibles (ou offre la possibilité de les télécharger, le cas échéant).

## Editeur SQL

L'éditeur SQL est un module qui est fourni directement dans l'IDE. Ce module doit pouvoir être utilisé par des modules externes. La liste ci-dessous des fonctionnalités qu'il met à disposition n'est pas exhaustive mais ce sont les points principaux :

- la coloration syntaxique;
- l'auto-completion;
- l'analyse grammaticale :
  - afin d'obtenir les champs exportés par une vue;
  - afin d'obtenir les champs nécessaires à l'appel d'une procédure stockée (y compris la description de la procédure stockée, des paramètres et du type de retour);
  - afin d'obtenir les champs exportés par une procédure stockée;
  - afin d'obtenir les champs exportés de fonctionnalités SQL avancées (p. ex. WITH de PostgreSQL);
- l'ouverture et la sauvegarde de fichiers SQL;
- la gestion des caractères selon la base de données;
- le formattage d'une requête;
- la gestion des transactions.

Les résultats de l'exécution d'une requête devront être affichés sous la forme d'un tableau. Ils pourront être exportés dans de divers formats tel que Excel, CSV, TSV, XML, JSON.

## Les Drivers

Un _Driver_ est un module permettant de se connecter à la base de données qu'il gère. Cela peut être une version spécifique ou de manière globale à un SGBD. Il met à disposition :

- de quoi récupérer les schémas, tables, vues, procédures stockées, fonctions, triggers, domaines;
- les mots-clés SQL (y compris ceux propres au SGBD); 
- la grammaire;
- le mode de formatage d'une requête.

Il gère également la sauvegarde des paramètres nécessaires à la connexion.

## Les modules externes

Un module externe est une fonctionnalité supplémentaire qui est ajouté à l'IDE. Voici quelques exemples :

- un comparateur de schéma et de données depuis une autre base de données;
- un comparateur de schéma et de données depuis une sauvegarde;
- un outil de récupération de données depuis une autre base de données;
- un outil de récupération de données depuis une sauvegarde;
- un outil de test unitaire pour les procédures stockées et triggers;
- un outil de test et d'optimisation pour les procédures stockées.

Il peut se reposer sur tout ce que met à disposition l'IDE (éditeur SQL, Apache POI, ...) et sera associé à un Driver lors de la sauvegarde d'un _projet_.

## Les projets

Les projets devront être stockés sous la forme XML (compressée ou non) et disposant d'une extension propre qui pourra être chargée par l'IDE. L'élément de base que doit contenir ce fichier et l'association entre le module externe et le Driver. Pour le reste, cela est propre à chaque module.

Un cas particulier peut être envisagé pour les requêtes SQL où il faudrait utiliser une entête spécifique propre à l'IDE (en commentaire dans le fichier) afin d'utiliser le Driver et la connexion. Ceci doit être fait uniquement si on ouvre le fichier depuis le disque. Si ce dernier est ouvert depuis l'IDE, afin d'être exécuté sur une base de données où la connexion a déjà été établie, cette entête est ignorée.

## Contraintes d'implémentation

L'application est développée en Java 8. Son interface graphique est développée en utilisant JavaFX. Il doit s'exécuter sous Windows, Linux et Mac OS X en prenant en considération les particularités de chaque système d'exploitation.

Il s'agit d'un projet OpenSource sous licence MIT. Le code source sera disponible sur GitHub (https://github.com/HEIG-GAPS/slasher).

La libraire ANTLR4 devra être utilisée pour l'analyse lexicale et la grammaire.

## Implémentation de l'éditeur SQL

La première étape consiste à trouver un composant JavaFX permettant l'édition de texte avec application automatique de style. Une piste est RichTextFX (https://github.com/FXMisc/RichTextFX/wiki).

La seconde étape consiste en l'ajout de l'auto-completion. Il doit être possible de compléter des mots clés, des tables, vues, procédures stockées et également les champs des tables, vues et procédures stockes (dans le cas où ces dernières donnent un résultat de type _Record_ ou _Table_). Attention à gérer également correctement les alias.

La troisième étape est la mise en place d'un analyseur grammaticale. Le but étant de détecter les erreurs avant que la requête soit exécutée. Une erreur grammaticale ne devrait pas interdire l'exécution d'une requête (l'analyse peut être mauvaise). Les erreurs doivent être clairement identifiées pour que l'utilisateur puisse les corriger, avec un message claire de l'erreur.

La quatrième étape consiste en l'identification des champs retournées par les sous-requêtes (quelles soient dans un _From_ ou à travers du _CTE_ - https://en.wikipedia.org/wiki/Hierarchical_and_recursive_queries_in_SQL#Common_table_expression). Ainsi, il serait possible d'identifier que des champs utilisés sont incorrects.

La cinquième étape est d'utiliser la détection des champs des sous-requêtes du point ci-dessus afin de les proposer à l'auto-complétion.

Sixièmement, et pour finir, il faudrait permettre de lancer un refactoring dès le moment où un champ d'une sous-requête est modifié. Il devrait également être possible d'effectuer une recherche d'utilisation à travers la requête.
