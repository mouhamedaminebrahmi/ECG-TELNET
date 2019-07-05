import numpy as np 

#fs : fréquence d'echantillonage du signal 

def P_detection(data,R_index,fs): #detection des positions des ondes P
    PINDICE=[]
    for s in range(1,R_index.size-1):
        infp = R_index[s]-int((0.2*fs)//1)
        supp = R_index[s]-int((0.07*fs)//1)
        vectp=data[infp:supp]
        PINDICE = np.append(PINDICE,np.argmax(vectp)+infp)
    return PINDICE
        
        
def Q_detection(data,R_index,fs):#detection des positions des ondes Q
    QINDICE=[]
    for s in range(1,R_index.size-1):
        infp = R_index[s]-int((0.069*fs)//1)
        supp = R_index[s];
        vectp=data[infp:supp]
        QINDICE = np.append(QINDICE,np.argmin(vectp)+infp)
    return QINDICE       
        
    
def S_detection(data,R_index,fs):#detection des positions des ondes S
    SINDICE=[]
    for s in range(1,R_index.size-1):
        supp = R_index[s]+int((0.07*fs)//1)
        infp = R_index[s]+1;
        vectp=data[infp:supp]
        SINDICE = np.append(SINDICE,np.argmin(vectp)+infp)
    return SINDICE  

def T_detection(data,R_index,fs):#detection des positions des ondes T
    TINDICE=[]
    for s in range(1,R_index.size-1):
        supp = R_index[s]+int((0.4*fs)//1)
        infp = R_index[s]+int((0.08*fs)//1)
        vectp=data[infp:supp]
        TINDICE = np.append(TINDICE,np.argmax(vectp)+infp)
    return TINDICE
    
def P_amplitude(data,R_index,fs):#detection des amplitudes des ondes P
    PAMPLITUDE=[]
    for s in range(1,R_index.size-1):
        infp = R_index[s]-int((0.2*fs)//1)
        supp = R_index[s]-int((0.07*fs)//1)
        vectp=data[infp:supp]
        PAMPLITUDE = np.append(PAMPLITUDE,np.max(vectp))
    return PAMPLITUDE
 
def Q_amplitude(data,R_index,fs):#detection des amplitudes des ondes Q
    QAMPLITUDE=[]
    for s in range(1,R_index.size-1):
        infp = R_index[s]-int((0.069*fs)//1)
        supp = R_index[s];
        vectp=data[infp:supp]
        QAMPLITUDE= np.append(QAMPLITUDE,np.min(vectp))
    return QAMPLITUDE

   
def S_amplitude(data,R_index,fs):#detection des amplitudes des ondes S
    SAMPLITUDE=[]
    for s in range(1,R_index.size-1):
        supp = R_index[s]+int((0.07*fs)//1)
        infp = R_index[s]+1;
        vectp=data[infp:supp]
        SAMPLITUDE = np.append(SAMPLITUDE,np.min(vectp))
    return SAMPLITUDE      

       
def T_amplitude(data,R_index,fs):#detection des amplitudes des ondes T
    TAMPLITUDE=[]
    for s in range(1,R_index.size-1):
        supp = R_index[s]+int((0.4*fs)//1)
        infp = R_index[s]+int((0.08*fs)//1)
        vectp=data[infp:supp]
        TAMPLITUDE = np.append(TAMPLITUDE,np.max(vectp))
    return TAMPLITUDE