# ECG-TELNET
FFF


L'objectif de notre projet est de concevoir et implémenter une solution pour l’authentification biométrique d’une personne avec un signal électrocardiogramme (ECG).
Cette solution est sous forme d’un dispositif portable qui permet à l’utilisateur de s’authentifier à travers une application mobile.
En fait, le principe consiste à acquérir d’abord le signal cardiaque ECG mesuré par un capteur d'électrocardiographie.
Ensuite appliquer un filtrage sur ce signal afin d’extraire les points caractéristiques uniques à chaque personne. 
Ses caractéristiques sont fournies pour un modèle d’apprentissage automatique. Dans cette partie plusieurs algorithmes ont testé sur notre jeu de données 
pour choisir un algorithme avec un meilleur résultat de prédiction. Une dernière étape de déploiement du modèle d’apprentissage automatique pour rendre 
ce dernier en mode production et utiliser par le grand public.


Il faut d'abord installer arduino IDE 
https://www.arduino.cc/en/Main/Software
Ajouter le code “send_data.ino” to Arduino

Installer android Studio 
https://developer.android.com/studio

Ouvrir Android Studio et ajouter le project nommé “ECG_TELNET_Android”

Compiler et installer l’application sur un smartphone Android.
Ouvrir l’application et choisir le module Bluetooth pour se connecter, dans notre cas nous avons utilisé le module HC-06.

Utiliser Pythonanywhere pour héberger l’application
https://www.pythonanywhere.com/
