import pandas as pd
import numpy as np
from scipy.signal import butter, lfilter, freqs
import matplotlib.pyplot as plt
from time import gmtime, strftime
import math
from firebase import firebase
firebase = firebase.FirebaseApplication('https://ecg-telnet.firebaseio.com/') #connexion à la base de données Firebase
from Features_description import Features_description #fonction d'extraction des descripteurs 
from findpeaks import findpeaks #fonction de detection des pics dans un signal 
from pan_tompkin_index_real import pan_tompkin_index_real #detection des position des pics R
from pan_tompkin_index_real import pan_tompkin_amplitude_real # detection des amplitudes des pics R
from pqrs_extraction import P_detection #detection des positions des ondes P
from pqrs_extraction import Q_detection #detection des positions des ondes Q
from pqrs_extraction import S_detection #detection des positions des ondes S
from pqrs_extraction import T_detection #detection des positions des ondes T
from pqrs_extraction import P_amplitude #detection des amplitudes des ondes P
from pqrs_extraction import Q_amplitude #detection des amplitudes des ondes Q
from pqrs_extraction import S_amplitude #detection des amplitudes des ondes S
from pqrs_extraction import T_amplitude #detection des amplitudes des ondes T
from euclidean_distance import euclidean_distance #calcul de distance euclidienne entre 2 points
from scipy.signal import find_peaks
from bandpass_filter import bandpass_filter #filtre numérique passe-bande 

ecg_retrieved = firebase.get('/forme',None) #reception du signal ECG brut à partir de la base de données 

ecg_retrieved = list(ecg_retrieved.values())

ecg_retrieved1 = np.asarray(ecg_retrieved)
ecg_retrieved1 = ecg_retrieved1.reshape(ecg_retrieved1.size)

filtered_signal = bandpass_filter(x,6,40,150,2) #filtrage du signal 
#plt.plot(filtered_signal)         
     
r_wave = pan_tompkin_index_real(x,filtered_signal) #detection des positions des pics R
r_amplitude = pan_tompkin_amplitude_real(x,filtered_signal) #detection des amplitudes des pics R

fs=150 #fréquence d'échantillonage du signal
p_wave = P_detection(filtered_signal,r_wave,fs) #detection des positions des ondes P
q_wave = Q_detection(filtered_signal,r_wave,fs) #detection des positions des ondes Q		
s_wave = S_detection(filtered_signal,r_wave,fs) #detection des positions des ondes S
t_wave = T_detection(filtered_signal,r_wave,fs) #detection des positions des ondes T

p_amplitude = P_amplitude(filtered_signal,r_wave,fs) #detection des amplitudes des ondes P
q_amplitude = Q_amplitude(filtered_signal,r_wave,fs) #detection des amplitudes des ondes q
s_amplitude = S_amplitude(filtered_signal,r_wave,fs) #detection des amplitudes des ondes S
t_amplitude = T_amplitude(filtered_signal,r_wave,fs) #detection des amplitudes des ondes T

#Extraction de l'ensemble de descripteurs à partir de chaque battement du coeur (8 complexes QRS sont utilisés)
output_features0 = Features_description(p_wave[0],q_wave[0],r_wave[0],s_wave[0],t_wave[0],p_amplitude[0],q_amplitude[0],r_amplitude[0],s_amplitude[0],t_amplitude[0],fs)
output_features1 = Features_description(p_wave[1],q_wave[1],r_wave[1],s_wave[1],t_wave[1],p_amplitude[1],q_amplitude[1],r_amplitude[1],s_amplitude[1],t_amplitude[1],fs)
output_features2 = Features_description(p_wave[2],q_wave[2],r_wave[2],s_wave[2],t_wave[2],p_amplitude[2],q_amplitude[2],r_amplitude[2],s_amplitude[2],t_amplitude[2],fs)
output_features3 = Features_description(p_wave[3],q_wave[3],r_wave[3],s_wave[3],t_wave[3],p_amplitude[3],q_amplitude[3],r_amplitude[3],s_amplitude[3],t_amplitude[3],fs)
output_features4 = Features_description(p_wave[4],q_wave[4],r_wave[4],s_wave[4],t_wave[4],p_amplitude[4],q_amplitude[4],r_amplitude[4],s_amplitude[4],t_amplitude[4],fs)
output_features5 = Features_description(p_wave[5],q_wave[5],r_wave[5],s_wave[5],t_wave[5],p_amplitude[5],q_amplitude[5],r_amplitude[5],s_amplitude[5],t_amplitude[5],fs)
output_features6 = Features_description(p_wave[6],q_wave[6],r_wave[6],s_wave[6],t_wave[6],p_amplitude[6],q_amplitude[6],r_amplitude[6],s_amplitude[6],t_amplitude[6],fs)
output_features7 = Features_description(p_wave[7],q_wave[7],r_wave[7],s_wave[7],t_wave[7],p_amplitude[7],q_amplitude[7],r_amplitude[7],s_amplitude[7],t_amplitude[7],fs)

numero_personne = 1 # 1 correspand à la classe attribuée à la personne
output_features0 = np.append(output_features0,numero_personne)
output_features1 = np.append(output_features1,numero_personne)
output_features2 = np.append(output_features2,numero_personne)
output_features3 = np.append(output_features3,numero_personne)
output_features4 = np.append(output_features4,numero_personne)
output_features5 = np.append(output_features5,numero_personne)
output_features6 = np.append(output_features6,numero_personne)
output_features7 = np.append(output_features7,numero_personne)

output_features0_ = np.reshape(output_features0, (52,1)).T
output_features1_ = np.reshape(output_features1, (52,1)).T
output_features2_ = np.reshape(output_features2, (52,1)).T
output_features3_ = np.reshape(output_features3, (52,1)).T
output_features4_ = np.reshape(output_features4, (52,1)).T
output_features5_ = np.reshape(output_features5, (52,1)).T
output_features6_ = np.reshape(output_features6, (52,1)).T
output_features7_ = np.reshape(output_features7, (52,1)).T


#les descripteurs extraits sont mis dans une matrice  
features = np.concatenate((output_features0_,output_features1_,output_features2_,output_features3_,output_features4_,output_features5_,output_features6_,output_features7_))    
df = pd.DataFrame(features)

test = pd.read_csv('extern_test_file_python_anywhere_1.csv')
test1 = test.iloc[:,1:].values
variable = np.concatenate((test1,features))

#importer le modèle de classification déja construit
import pickle 
with open('modele.pkl','rb') as f:
    model=pickle.load(f)
    
entree_model = variable[:,:-1]
y_entree = variable[:,51]


#Normalisation des données
from sklearn.preprocessing import StandardScaler
sc = StandardScaler()
input_features_scaled = sc.fit_transform(entree_model) 

#prédiction de la classe correspondante aux descripteurs introduits
p = model.predict(input_features_scaled)

from collections import Counter

personne = p[12:19].copy()
estimatedclass = Counter(personne).most_common(1)
name =   int(estimatedclass[0][0]) #la classe détéctée
name1 =   int(estimatedclass[0][1]) #le nombre d'occurence de la classe détéctée
