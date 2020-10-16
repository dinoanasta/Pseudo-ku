# python plotter.py 2 file1.csv file2.csv OutputFile

import matplotlib.pyplot as plt
import numpy as np
import sys

file = sys.argv[1]
#title = sys.argv[2]
n = range(int(sys.argv[1]))
title = sys.argv[int(sys.argv[1])+2]

first = False

datapath = sys.argv[2]
data = np.loadtxt(datapath, dtype = str, delimiter=',', max_rows=1)


xLabel = data[0]
yLabel = data[1]


for i in n:
    datapath = sys.argv[i+2]
    data = np.loadtxt(datapath, delimiter=',', skiprows=1)
    X = data[:,0]
    Y = data[:,1]
    if first:
        plt.plot(X,Y,label = sys.argv[i+2][:-4])      
    else:
        first = True
        plt.plot(X,Y,label = sys.argv[i+2][:-4])

plt.xlabel(xLabel)
plt.ylabel(yLabel)
plt.title(title)
plt.ylim(ymin=0)
plt.xlim(xmin=0)
plt.legend(shadow = False, fancybox = True)
plt.savefig(title +'.pdf')
plt.close()
