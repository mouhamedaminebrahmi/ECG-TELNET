import numpy as np 
import matplotlib.pyplot as plt 
import pandas as pd

dataset = pd.read_csv('training_real1.csv');
                             
x = dataset.iloc[:52,1:-1].values
y = dataset.iloc[:52, 52].values

#Splitting data into training and testing

from sklearn.model_selection import train_test_split
x_train, x_test, y_train, y_test = train_test_split(x, y, test_size = 0.25, random_state =0)

#Feature scaling 
from sklearn.preprocessing import StandardScaler
sc_x = StandardScaler()
x_train_scaled = sc_x.fit_transform(x_train)
x_test_scaled = sc_x.transform(x_test) 
x_scaled = sc_x.transform(x)

#SVM model 
from sklearn.svm import SVC
classifier = SVC(kernel ='rbf',C = 4,random_state=0)
classifier.fit(x_train_scaled, y_train)

#predicting the test results

y_pred = classifier.predict(x_test_scaled)

#accuracy 

from sklearn.metrics import accuracy_score
accuracy = accuracy_score(y_test, y_pred)

#classification report
from sklearn.metrics import classification_report
classification_r = classification_report(y_test, y_pred)

#confusion matrix 

from sklearn.metrics import confusion_matrix 
cm = confusion_matrix (y_test, y_pred)


from sklearn.model_selection import cross_val_score
accuracies = cross_val_score(estimator = classifier, X = x_scaled, y = y, cv=10)
# précision moyenne 
moyenne_acc = accuracies.mean()

#save the model as a pkl file 
import pickle
with open('modele.pkl','wb') as f:
    pickle.dump(classifier,f)








