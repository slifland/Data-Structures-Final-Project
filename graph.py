import matplotlib as plt
import pandas as pd


def main():
    print('hi')
    parentPoints = {}
    oldPoints = {}
    f = open("parentSols.txt", "r")
    for x in f.readlines():
        if len(x) > 0:
            parentPoints[x.split(',')[0]] = x.split(',')[1]
    f = open("targetSols.txt", "r")
    for x in f.readlines():
        if len(x) > 0:
            oldPoints[x.split(',')[0]] = x.split(',')[1]
    df1 = pd.DataFrame(parentPoints.items())
    df1 = df1.rename({0: 'x', 1: 'y'}, axis=1)
    df2 = pd.DataFrame(oldPoints.items())
    df1 = pd.concat([df1, df2], axis=1)
    print(df1)
    ax = df1.plot(kind='scatter', x='x', y='y', color='DarkBlue', label='Group 1')
    df1.plot(kind='scatter', x=0, y=1, color='DarkGreen', label='Group 2', ax=ax)
main()
