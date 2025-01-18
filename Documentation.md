# Architecture du projet 

## Description général 

./presentation.md

## Structure modulaire 

### Models
Les modèles représentent les données principales de l'application, comme :
- **Song** : Contient **ID** ainsi que le lien vers les fichiers de la musique s'ils existent, .
- **ID** : Contient le titre de la musique et l'artiste d'une musique.
- **LyricsLine** : Gère une ligne de parole avec son timer de départ et son timer de fin.
- **SongData** : Contient les informations d'une musique tel qu'une liste de **LyricsLine**
### Objects 
Les objets regroupent les instances réutilisables ou partagées à travers l'application, comme des singletons pour gérer les ressources ou les configurations globales.

### UI
La partie UI est développée avec **Jetpack Compose** et inclut :
- Des écrans dédiés (lecture, sélection de musique, paramètres).
- Des composants réutilisables (boutons, carte d'affichage des musiques).

### ViewModel
Les ViewModels gèrent les états de l'application et permettent la communication entre l'interface utilisateur et la logique métier. Chaque logique métier disposent d'un viewModel associé. Les viewmodels de l'application sont :
- **DownloadViewModel** : Gère le téléchargement des fichiers associés à une musique
- **ErrorViewModel** : Récupère les erreurs dans des actions asynchrone ou en fond pour les afficher à l'écran
- **FilterViewModel** : Fitlre les musiques affichées dans le menu
- **KaraokeViewModel** : Contient une instance **ExoPlayer** pour la lecture de la musique
- **ThemeViewModel** : Gère le thème 

### Repository
Les repositories centralisent l'accès aux données, qu'elles proviennent du réseau, du stockage local ou des ressources embarquées. Par exemple :
- Gestion des téléchargements.
- Accès aux paroles et aux fichiers audio.

### Worker 
Les workers assurent les tâches en arrière-plan, comme :
- Le téléchargement des fichiers audio et des paroles.
- La gestion des notifications pour informer l'utilisateur de la progression des tâches.

## Logique métier 

### Fonctionnement du menu 
Le menu permet de sélectionner une musique parmi les options disponibles. Il est mis à jour dynamiquement en fonction des fichiers téléchargés ou disponibles sur le réseau.

### Fonctionnement du karaoke 
Lors de la lecture d'une musique, les paroles s'affichent et défilent sur l'écran en synchronisation avec l'audio. Cette synchronisation repose sur les timers définis dans les données des paroles.

### Logique de téléchargement 
Les fichiers MP3 et les paroles sont téléchargés via des workers, puis stockés localement. Une fois le téléchargement terminé, l'état de chaque élément passe à "téléchargé", ce qui les rend accessibles hors ligne.

### Mise en place des thèmes 
L'application permet de basculer entre différents thèmes (clair, sombre) pour améliorer l'expérience utilisateur dans divers environnements.

## Technologies utilisées 

### ExoPlayer 
Utilisé pour la lecture audio, ExoPlayer offre des performances optimales et une personnalisation avancée pour gérer les fichiers MP3 et leurs états.

### WorkManager
Gère les tâches en arrière-plan, comme le téléchargement, de manière fiable, même en cas de redémarrage de l'application.

### JetpackCompose 
Simplifie la création de l'interface utilisateur grâce à une approche déclarative et réactive, parfaitement adaptée aux animations et transitions nécessaires au karaoké.

## Choix techniques 

### Mode paysage 
L'application est conçue uniquement pour le mode paysage afin de maximiser la lisibilité des paroles et l'expérience immersive.

### PreferenceSharded 
Les préférences partagées sont utilisées pour stocker des configurations simples, comme le thème sélectionné ou les dernières chansons jouées.

### KaraokeViewModel
Un ViewModel central gère les états globaux de l'application, comme la synchronisation des paroles, les contrôles de lecture, et les paramètres utilisateur.

## Amélioration technique 

### Mode portrait & taille de texte
Ajouter un mode portrait et permettre la personnalisation de la taille des textes pour une meilleure accessibilité.

### Personalisation de l'application avancée 
Intégrer des options permettant de personnaliser les couleurs, les polices et les animations de l'interface utilisateur.

### Gestion des paroles du karaoke 
Améliorer le système de synchronisation des paroles, notamment pour gérer des écarts possibles lors de la lecture.

### Ajout de test unitaire 
Mettre en place des tests unitaires pour garantir la fiabilité du code, notamment sur les modules critiques comme la synchronisation et les téléchargements.
