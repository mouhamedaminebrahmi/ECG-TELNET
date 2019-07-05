# ECG-TELNET
FFF


L'objectif de notre projet est de concevoir et impl�menter une solution pour l�authentification biom�trique d�une personne avec un signal �lectrocardiogramme (ECG).
Cette solution est sous forme d�un dispositif portable qui permet � l�utilisateur de s�authentifier � travers une application mobile.
En fait, le principe consiste � acqu�rir d�abord le signal cardiaque ECG mesur� par un capteur d'�lectrocardiographie.
Ensuite appliquer un filtrage sur ce signal afin d�extraire les points caract�ristiques uniques � chaque personne. 
Ses caract�ristiques sont fournies pour un mod�le d�apprentissage automatique. Dans cette partie plusieurs algorithmes ont test� sur notre jeu de donn�es 
pour choisir un algorithme avec un meilleur r�sultat de pr�diction. Une derni�re �tape de d�ploiement du mod�le d�apprentissage automatique pour rendre 
ce dernier en mode production et utiliser par le grand public.


Il faut d'abord installer arduino IDE 
https://www.arduino.cc/en/Main/Software
Ajouter le code �send_data.ino� to Arduino

Installer android Studio 
https://developer.android.com/studio

Ouvrir Android Studio et ajouter le project nomm� �ECG_TELNET_Android�

Compiler et installer l�application sur un smartphone Android.
Ouvrir l�application et choisir le module Bluetooth pour se connecter, dans notre cas nous avons utilis� le module HC-06.

Utiliser Pythonanywhere pour h�berger l�application
https://www.pythonanywhere.com/
