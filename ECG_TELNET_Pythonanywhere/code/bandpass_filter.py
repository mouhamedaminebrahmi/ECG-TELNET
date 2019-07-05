from scipy.signal import butter, lfilter, freqs
import matplotlib.pyplot as plt
import numpy as np
from scipy import  signal
def bandpass_filter(data, lowcut, highcut, signal_freq, filter_order): # filtre numérique passe-bande
        #lowcut, highcut : fréquences de coupures du filtre
        #signal_freq : fréquence d'échantillonage du signal
        #filter_order : ordre du filtre
     
        nyquist_freq = 0.5 * signal_freq    
        low = lowcut / nyquist_freq
        high = highcut / nyquist_freq
        b, a = butter(filter_order, [low, high], btype="band")
        y = lfilter(b, a, data)
        return y

        