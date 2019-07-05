L'objectif de notre projet est de concevoir et implémenter une solution pour l’authentification biométrique d’une personne avec un signal électrocardiogramme (ECG).

Cette solution est sous forme d’un dispositif mobile qui permet à l’utilisateur de s’authentifier à travers une application android.

En fait, le principe consiste à acquérir d’abord le signal cardiaque ECG mesuré par un capteur d'électrocardiographie.

Ensuite on applique un filtrage au signal pour enlever toutes sortes de bruit.

Afin d’extraire les points caractéristiques ayant pour rôle de spécifier la signature unique de chaque signal ECG de chaque personne, on utilise un algorithme d’extraction des complexes QRS intitulé algorithme de Pan-Tompkins. Puis, on détecte un ensemble de descripteurs pour chaque battement du coeur pour chaque signal ECG : descripteurs temporelles, en terme d’amplitudes, de distances euclidiennes et de pentes entre plusieurs points présents dans le signal. 

Ses caractéristiques sont fournies pour un modèle de classification Machine Learning. Dans cette partie plusieurs algorithmes ont été testé afin de choisir le modèle de classification le plus performant, en tenant compte des données disponibles.

 La dernière étape est le déploiement de la totalité du système pour rendre ce dernier en mode production .


Il faut d'abord installer arduino IDE 
https://www.arduino.cc/en/Main/Software
Ajouter le code “send_data.ino” to Arduino
Compiler et téléverser le code sur Arduino.Dans notre cas nous avons utilisé (Arduino Mega)

Installer android Studio 
https://developer.android.com/studio
Ouvrir Android Studio et ajouter le projet nommé “ECG_TELNET_Android”

Compiler et installer l’application sur un smartphone Android.
Ouvrir l’application et choisir le module Bluetooth pour se connecter, dans notre cas nous avons utilisé le module HC-06.

Utiliser Pythonanywhere pour héberger l’application
https://www.pythonanywhere.com/
créer un nouveau compte
créer un environnement virtuel : connectez-vous à votre compte PythonAnywhere, ouvrez une console Bash , clonez votre référentiel et créez un virtualenv:

git clone <my-repo-url>  
cd my-project-name
mkvirtualenv --python=/usr/bin/python3.6 my-project-name
pip install Pandas Numpy scikit-learn




Héberger les fichiers de l’application de classification sur un répertoire dans le serveur en ligne PythonAnywhere : “bandpass_filter.py”, “euclidean_distance.py”, “Features_description.py”, “findpeaks.py” ,”pan_tompkin_index_real.py” ,”pqrs_extraction.py”

La construction du modèle doit être exécutée sur le PythonAnywhere
Former un fichier contenant les descripteurs et l'héberger sur le répertoire de l'exécution du serveur 
Construire le modèle de classification et l’enregistrer comme un fichier pkl : Executer le fichier “constrcution_modèle_classification.py” (avec les modifications nécessaires)




Base de données utilisée pour la validation théorique : https://physionet.org/cgi-bin/atm/ATM
