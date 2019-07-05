import numpy as np 

def euclidean_distance(x1,y1,x2,y2): #fonction de calcul de la distance euclidienne entre deux points 
    a = np.array((x1,y1))
    b = np.array((x2,y2))
    dist = np.linalg.norm(a-b)
    return dist