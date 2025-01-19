# Architecture du projet 

## Description générale

[Ouvrir la présentation](./Présentation.md)

## Structure modulaire 

### Models
Les modèles représentent les données principales de l'application tels que :
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
- **ThemeViewModel** : Gère le thème utilisé par l'application

### Repository
On utilise un repository pour manipuler et télécharger la liste de musique

### Worker 
Les workers assurent les tâches en arrière-plan, comme :
- Le téléchargement des fichiers audio et des paroles.
- la sérialisation des paroles une fois qu'elles sont téléchargées

## Logique métier 

### Fonctionnement du menu 
Le menu permet de télécharger une première fois ou remettre à jour la liste des musiques présentes ensuite dans la cache de l'application.
Ensuite le menu se décompose en trois parties :
- l'affichage des musiques dans grille avec un défilement verticale 
- options de filtre sur l'état d'une musique et sur le nom de l'artiste ou le titre de la musique qui actualise dynamiquement la grille des musiques
- une barre d'options qui permettent :
    - de quitter l'application 
    - de changer le thème de l'application 
    - de retélécharger la liste des musiques 

### Etat d'une musique 
Une musique peut être dans plusieurs états : vérouillée, dévérouillée ou téléchargée. Dans chacun des cas l'affichage de la musique sera différent :
- vérouillée : la musique est affichée grisée et on ne peut pas intéragir avec
- dévérouillée : des fichiers existent pour la musique mais ne sont pas présentent dans le cache de l'application. Un bouton "Télécharger" est disponible pour récupérer les données de la musique depuis le serveur
- téléchargée : les données de la musique sont présentes dans la cache de l'application. On affiche alors deux boutons. Un premier pour lancer le karaokée sur cette musique et deuxième pour supprimer la musique du cache.

### Logique de téléchargement d'une musique
Les fichiers MP3 et les paroles sont téléchargés via des workers, puis stockés localement dans le cache. Une fois le téléchargement terminé, l'état de la musique passe de "dévérouillée" à "téléchargée", ce qui les rend permet le lancement du karaoke ensuite.

### Fonctionnement du karaoke 
Lorsqu'une musique est sélectionée, on initialise un ExoPlayer avec le fichier mp3 de la musique et on charge les paroles. Les paroles sont affichées à l'écran aven un curseur qui défile selon l'avancement de la musique.
L'écran du karaokée comprend également d'autres fonctionnalités:
- un curseur pour se déplacer dans la musique
- une minuteur pour afficher le temps d'avacement dans la musique
- un bouton pause
- un bouton de rénitialisation pour revenir directement au début de la musique
- un bouton de retour au menu

### Affichage des paroles 
Afin d'afficher correctement les paroles, on récupère depuis l'exoPlayer la position dans la musique, puis on récupère la ligne à mettre à l'écran dans les paroles de la musique à l'aide du temps de début et du temps de fin de la ligne. 
Pour l'avancement du curseur, on calcul un nombre flottant entre 0 et 1 pour avoir la progression dans la phrase actuel de la phrase dans la playlist:
progress = (currentPosition - it.startTime) / (it.endTime - it.startTime)
Puis à l'aide de la position du texte affiché et de la taille du texte on place correctement le curseur sur la phrase en mettant un décalage sur la position intiial du cuseur
x = ( ((textWidth.value * (progress-0.5))).dp)

### Mise en place des thèmes 
L'application permet de basculer entre différents thèmes (clair, sombre, bleu, japonais) pour améliorer l'expérience utilisateur.

## Technologies utilisées 

### ExoPlayer 
Utilisé pour la lecture audio, ExoPlayer offre des performances optimales et une personnalisation avancée pour gérer les fichiers audio MP3.

### WorkManager
Gère les tâches en arrière-plan, comme le téléchargement, de manière fiable, même en cas de redémarrage de l'application.

### JetpackCompose 
Simplifie la création de l'interface utilisateur grâce à une approche déclarative et réactive, parfaitement adaptée aux animations et transitions nécessaires au karaoké.

## Choix techniques 

### Mode paysage 
L'application est conçue uniquement pour le mode paysage afin de maximiser la lisibilité des paroles et l'expérience immersive. 

### PreferenceSharded & Cache
Les préférences partagées sont utilisées pour stocker le dernier thème utilisé tandis que les fichiers mp3 et les paroles sont stockées directement dans le cache car ces fichiers sont beaucoup plus volumineux 

## Améliorations envisageables 

### Améliorations visuelles
Plusieurs amélioration graphique pourrait permettre à l'application d'être plus flexible et de mieux correspondre à chaque appareil :
- Création d'un design pour le mode portait afin de permettre l'usage de l'application dans toutes les circonstances 
- Adaptabilité de la police d'écriture du texte afin de s'aggradir en cas d'utilisation de l'application sur tablette
- Ajout d'animation en fond pendant le karaoke pour un rendu plus dynamique

### Personalisation de l'application avancée 
L'application ne permet pas plus de personnalisation que la selection parmis une selection prédéfinie de thème. Quelques idées à rajouter pour une application plus versatiles :
- Permettre la création de thèmes personnalisés par l'utilisateur stockées ensuite dans le cache de l'application
- Importer des musiques directement depuis l'appareil Android de l'utilisateur 
- Selection de la police d'écriture et de la taille de caractère affiché à l'écran

### Gestion des paroles du karaoke 
Actuellemement lors de la sérialisation des paroles, les lignes des paroles sont associé à uniquement un timecode afin de simplifier la logique d'affichage du curseur et des paroles. Bien que cette simplification ne pose aucun soucis pour la grande majorité des paroles, parfois un mot est divisé par un timecode à cause d'un changement de rythme dans la musique, à ce moment là, avec le systeme actuel celui-ci sera divisé en deux phrases à l'écran : par exemple "again" peut être divisé en "a" puis "gain".
La solution serait pour de ne pas travailler sur le format **LyricsLine** mais avec un format différent qui serait une liste composées de fragment de phrase que l'on afficherait itérativement à l'écran avec un décalage successif pour recomposer la phrase entière. Le calcul de progression serait entièrement à refaire alors pour que le curseur se déplace correctement tout le long de le phrase en respectant le rythme associé à chaque fraguement de musique

### Ajout de test unitaire 
Mettre en place des tests unitaires pour garantir la fiabilité du code, notamment sur les modules critiques comme la synchronisation et les téléchargements.
