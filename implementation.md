Package database
================
DbObject
--------
Super classe de tous les objects d'une base de données

Serveur
-------
Modélisation d'un serveur de base de données. Contient une instance du driver pour récupérer les bases de données disponibles.
Contient une liste avec les bases de données enregistrées(connectées). Le port et le hostname y sont stocké.


Database
--------
Modélisation d'une base de données. Est liée à un driver avec un nom d'utilisateur. Elle permet à l'interface graphique de récupérer les informations sur la base de données au travers du driver quel qu'il soit.


DbComposent
-----------
Super classe de tous les objects contenus dans une base de données (Schema, table,..) (Contrairement à DbObject). Elle permet de stocker dans une base de données quel que soit le type (MySQL ou Postgres) les objects qui la composents.

Tous les composants de base de données
--------------------------------------
Pour chaque élément de la base de données, il y a une classe qui la représente.


Package treeItem
================
Modélisation de tous les objects de la base de données (package Database) dans des "TreeItems" de vue en arbre à droite.
Utilisation d'un enum pour les types d'éléments de base de données, afin de déclancher des actions dans le contrôleur principal uniquement sur certains types.

Tous les éléments inferieurs dans l'arborescence d'une base de données, contiennent une référence vers celle-ci afin de pouvoir lier les requêtes et les actions sur la bonne base de données.
