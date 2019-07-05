from bandpass_filter import bandpass_filter 
import numpy as np
from findpeaks import findpeaks

def pan_tompkin_index_real(data,filtered_signal):#algortithme d'extraction Pan-Tompkins : extraction des positions temporelles des pics R
       integration_window = 5
       filtered = bandpass_filter(data,2,15,150,2) #filtrage passe bande
       derivative = np.ediff1d(filtered) # calcul de la derivé du signal
       square = derivative ** 2 #calcul du carré du signal dérivé
       integrated_ecg_measurements = np.convolve(square, np.ones(integration_window))
       integrated_ecg_measurements = integrated_ecg_measurements[40:-20] # Elimination d'un nombre d'échantillons au début et à la fin du signal
       threshold = 1.5*sum(integrated_ecg_measurements)/len(integrated_ecg_measurements) #fixation du seuil de détéction(dépand de la nature du signal)
       detected_peaks_indices = findpeaks(integrated_ecg_measurements,limit=threshold,spacing=15) #detection des positions des pics
       detected_peaks_indices = detected_peaks_indices+40     
       for s in range(detected_peaks_indices.size):
           v = filtered_signal[detected_peaks_indices[s]-15:detected_peaks_indices[s]]
           detected_peaks_indices[s] = np.argmax(v)+detected_peaks_indices[s]-15   
       return detected_peaks_indices
   
def pan_tompkin_amplitude_real(data,filtered_data):#Extraction des amplitudes des pics R
       detected_peaks_indices= pan_tompkin_index_real(data,filtered_data) 
       detected_peaks_values = filtered_data[detected_peaks_indices]
       return detected_peaks_values