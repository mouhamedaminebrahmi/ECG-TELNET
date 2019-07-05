L'objectif de notre projet est de concevoir et impl�menter une solution pour l�authentification biom�trique d�une personne avec un signal �lectrocardiogramme (ECG).

Cette solution est sous forme d�un dispositif mobile qui permet � l�utilisateur de s�authentifier � travers une application android.

En fait, le principe consiste � acqu�rir d�abord le signal cardiaque ECG mesur� par un capteur d'�lectrocardiographie.

Ensuite on applique un filtrage au signal pour enlever toutes sortes de bruit.

Afin d�extraire les points caract�ristiques ayant pour r�le de sp�cifier la signature unique de chaque signal ECG de chaque personne, on utilise un algorithme d�extraction des complexes QRS intitul� algorithme de Pan-Tompkins. Puis, on d�tecte un ensemble de descripteurs pour chaque battement du coeur pour chaque signal ECG : descripteurs temporelles, en terme d�amplitudes, de distances euclidiennes et de pentes entre plusieurs points pr�sents dans le signal. 

Ses caract�ristiques sont fournies pour un mod�le de classification Machine Learning. Dans cette partie plusieurs algorithmes ont �t� test� afin de choisir le mod�le de classification le plus performant, en tenant compte des donn�es disponibles.

 La derni�re �tape est le d�ploiement de la totalit� du syst�me pour rendre ce dernier en mode production .


Il faut d'abord installer arduino IDE 
https://www.arduino.cc/en/Main/Software
Ajouter le code �send_data.ino� to Arduino
Compiler et t�l�verser le code sur Arduino.Dans notre cas nous avons utilis� (Arduino Mega)

Installer android Studio 
https://developer.android.com/studio
Ouvrir Android Studio et ajouter le projet nomm� �ECG_TELNET_Android�

Compiler et installer l�application sur un smartphone Android.
Ouvrir l�application et choisir le module Bluetooth pour se connecter, dans notre cas nous avons utilis� le module HC-06.

Utiliser Pythonanywhere pour h�berger l�application
https://www.pythonanywhere.com/
cr�er un nouveau compte
cr�er un environnement virtuel : connectez-vous � votre compte PythonAnywhere, ouvrez une console Bash , clonez votre r�f�rentiel et cr�ez un virtualenv:

git clone <my-repo-url>  
cd my-project-name
mkvirtualenv --python=/usr/bin/python3.6 my-project-name
pip install Pandas Numpy scikit-learn




H�berger les fichiers de l�application de classification sur un r�pertoire dans le serveur en ligne PythonAnywhere : �bandpass_filter.py�, �euclidean_distance.py�, �Features_description.py�, �findpeaks.py� ,�pan_tompkin_index_real.py� ,�pqrs_extraction.py�

La construction du mod�le doit �tre ex�cut�e sur le PythonAnywhere
Former un fichier contenant les descripteurs et l'h�berger sur le r�pertoire de l'ex�cution du serveur 
Construire le mod�le de classification et l�enregistrer comme un fichier pkl : Executer le fichier �constrcution_mod�le_classification.py� (avec les modifications n�cessaires)




Base de donn�es utilis�e pour la validation th�orique : https://physionet.org/cgi-bin/atm/ATM
