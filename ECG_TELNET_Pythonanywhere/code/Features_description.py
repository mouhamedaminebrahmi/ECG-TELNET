import numpy as np
from euclidean_distance import euclidean_distance

def Features_description(xp,xq,xr,xs,xt,yp,yq,yr,ys,yt,fs): #fonction de calcul de l'ensemble de dsecripteurs pour chaque battement du coeur(51 descripteurs)
    """Time features"""
    features_vector = []
    features_vector = np.append(features_vector,abs(xp-xr)/fs)
    features_vector = np.append(features_vector,abs(xq-xr)/fs)
    features_vector = np.append(features_vector,abs(xs-xr)/fs)
    features_vector = np.append(features_vector,abs(xt-xr)/fs)
    features_vector = np.append(features_vector,abs(xq-xp)/fs)
    features_vector = np.append(features_vector,abs(xt-xp)/fs)
    features_vector = np.append(features_vector,abs(xs-xq)/fs)
    features_vector = np.append(features_vector,abs(xt-xq)/fs)
    features_vector = np.append(features_vector,abs(xt-xs)/fs)
    features_vector = np.append(features_vector,abs(xs-xp)/fs)
    features_vector = np.append(features_vector,abs((xt-xp)/(xs-xq)))
    features_vector = np.append(features_vector,abs((xt-xq)/(xs-xq)))
    
    """amplitude features"""
    features_vector = np.append(features_vector,yp)
    features_vector = np.append(features_vector,yq)
    features_vector = np.append(features_vector,ys)
    features_vector = np.append(features_vector,yt)
    features_vector = np.append(features_vector,abs(yq-yp))
    features_vector = np.append(features_vector,abs(yr-yq))
    features_vector = np.append(features_vector,abs(ys-yr))
    features_vector = np.append(features_vector,abs(yt-ys))
    features_vector = np.append(features_vector,abs(ys-yq))
    features_vector = np.append(features_vector,abs(ys-yp))
    features_vector = np.append(features_vector,abs(yt-yp))
    features_vector = np.append(features_vector,abs(yt-yq))
    features_vector = np.append(features_vector,abs((yt-ys)/(ys-yq)))
    features_vector = np.append(features_vector,abs((yq-yp)/(ys-yq)))
    features_vector = np.append(features_vector,abs((yq-yp)/(yt-yq)))
    features_vector = np.append(features_vector,abs((yq-yp)/(ys-yp)))
    features_vector = np.append(features_vector,abs((yq-yp)/(yr-yq)))
    features_vector = np.append(features_vector,abs((yq-yp)/(ys-yr)))
    features_vector = np.append(features_vector,abs((ys-yr)/(ys-yq)))
    features_vector = np.append(features_vector,abs((ys-yr)/(yt-yq)))
    features_vector = np.append(features_vector,abs((yt-ys)/(yq-yp)))
    features_vector = np.append(features_vector,abs((yt-ys)/(yt-yq)))
    
    """distance features"""
    features_vector = np.append(features_vector,euclidean_distance(xp,yp,xq,yq))
    features_vector = np.append(features_vector,euclidean_distance(xq,yq,xr,yr))
    features_vector = np.append(features_vector,euclidean_distance(xr,yr,xs,ys))
    features_vector = np.append(features_vector,euclidean_distance(xs,ys,xt,yt))
    features_vector = np.append(features_vector,euclidean_distance(xq,yq,xs,ys))
    features_vector = np.append(features_vector,euclidean_distance(xp,yp,xr,yr))
    features_vector = np.append(features_vector,euclidean_distance(xt,yt,xs,ys)/euclidean_distance(xq,yq,xs,ys))
    features_vector = np.append(features_vector,euclidean_distance(xr,yr,xs,ys)/euclidean_distance(xq,yq,xr,yr))
     
    """slope features"""
    features_vector = np.append(features_vector,(yq-yp)/(xq-xp))
    features_vector = np.append(features_vector,(yq-yr)/(xq-xr))
    features_vector = np.append(features_vector,(ys-yr)/(xs-xr))
    features_vector = np.append(features_vector,(yt-ys)/(xt-xs))
    features_vector = np.append(features_vector,(ys-yq)/(xs-xq))
    features_vector = np.append(features_vector,(yt-yp)/(xt-xp))
    features_vector = np.append(features_vector,(ys-yp)/(xs-xp))
    features_vector = np.append(features_vector,(yt-yq)/(xt-xq))
    features_vector = np.append(features_vector,(yr-yp)/(xr-xp))
    
    
    return features_vector