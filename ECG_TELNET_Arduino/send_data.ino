#include <SoftwareSerial.h>

SoftwareSerial BTserial(0, 1); // RX | TX
// Broches utilisées pour les entrées et les sorties **
int sensorPin = A0;// broches d'entrée analogiques
// Caractère utilisé pour la lecture en caractères série
int sensorValue = 0;
void setup() {
// initialise les communications série à 9600 bps:
BTserial.begin(9600); }

void loop() {
// lit la valeur du capteur:
sensorValue = analogRead(sensorPin);





// envoie les valeurs du capteur via un module série au module BT

BTserial.print('#');// met # avant les valeurs pour que notre application sache quoi faire avec les données
  
  
    BTserial.print(sensorValue);
   
 // utilisé comme caractère de fin de transmission - utilisé dans l'application pour la longueur de chaîne
 BTserial.print('~');
 
 BTserial.println();
 // ajout d'un délai pour éliminer les transmissions manquées
 delay(25); 

}
