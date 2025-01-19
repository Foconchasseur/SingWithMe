# Le projet

## Présentation :

Ce projet fait pour le module Android de l'ENSSAT. Il a pour but de faire une application de karaoké. Le sujet complet peut être trouvé [ici](https://gcpa-enssat-24-25.s3.eu-west-3.amazonaws.com/index.html)

Le projet ce compose de 2 parties principale. Le première est un menu permettant de parcourir, rechercher et sélection les musiques. La deuxième est le karaoké en lui même qui doit lire une musique au format MP3, et afficher les paroles de manière synchroniser grâce à un fichier *.md contenant les paroles et leurs times codes.


## Spécificité

### Le menu :

La liste des musiques à parcourir peut être récuperé à l'adresse : https://gcpa-enssat-24-25.s3.eu-west-3.amazonaws.com/playlist.json

Ce fichier JSON contient plusieurs information :
- le nom de la chanson
- l'artiste qui interprète la chanson

et soit :
- un booléen qui indique que la musique n'est pas disponible 
- le path vers le fichier .md de la musique.

Dans le fichier .md d'une musique il y a une entête qui donne, le titre, l'auteur, et le nom du fichier .mp3 pour pouvoir le récupérer

Du coup l'application doit pouvoir télécharger le fichier JSON de playlist, l'interpréter pour pouvoir afficher toutes les musiques et afficher différemment les musique téléchargeable des autres. 

Après cela l'utilisateur doit pouvoir choisir une musique à télécharger et l'application se chargera de télécharger le fichier .md et .mp3 en cache pour que la musique puisse être jouer pour l'utilisateur dès qu'il le souhaite.


### Le Karaoké :

Une fois la musique séléctionné, l'application doit lire le fichier .mp3 et afficher les paroles stocker dans le fichier .md. 

Pour afficher les paroles à dire, l'application affiche un texte noir pour les future parole et un texte rouge pour les paroles précédente. De plus un petit indiquateur gris indique le moment de la ligne sur lequel on est.

Les silences des musique sont représenté par ". . ."

De plus nous avons rajouter un slider permettant d'avancer dans la musique.