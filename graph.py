import pandas as pd
import pygame


def main():
    print('hi')
    parentPoints = {}
    oldPoints = {}
    graph_max = 0
    graph_min = 0
    f = open("C:/Users/Seth Lifland/Downloads/Data-Structures-Final-Project/parentSols.txt", "r")
    for x in f.readlines():
        if len(x) > 0:
            y = float(x.split(',')[1])
            parentPoints[x.split(',')[0]] = y
            if y > graph_max:
                graph_max = y
            if y < graph_min:
                graph_min = y
    f = open("C:/Users/Seth Lifland/Downloads/Data-Structures-Final-Project/targetSols.txt", "r")
    for x in f.readlines():
        if len(x) > 0:
            y = float(x.split(',')[1])
            oldPoints[x.split(',')[0]] = y
            if y > graph_max:
                graph_max = y
            if y < graph_min:
                graph_min = y
    # df1 = pd.DataFrame(parentPoints.items())
    # df1 = df1.rename({0: 'x', 1: 'y'}, axis=1)
    # df2 = pd.DataFrame(oldPoints.items())
    # df1 = pd.concat([df1, df2], axis=1)
    pygame.init()
    X = 800
    Y = 800
    scrn = pygame.display.set_mode((X, Y))
    pygame.display.flip()
    status = True
    pygame.draw.line(scrn, (255, 0, 0), (X / 2, 0), (X / 2, Y))
    pygame.draw.line(scrn, (255, 0, 0), (0, Y / 2), (X, Y / 2))
    scale = detScale(Y, graph_min, graph_max)
    print(scale)
    # pygame.draw.circle(scrn, (0,255,0), (200,200), 2)
    for x, y in parentPoints.items():
        #pygame.draw.circle(scrn, (0, 255, 0), (float(x) * scale[0], float(y) * scale[1]), 2)
        pygame.draw.circle(scrn, (0, 255, 0), ((float(x) + 100) * 4, (float(y) + scale[0]) * scale[1]), 2)
    for x, y in oldPoints.items():
        #pygame.draw.circle(scrn, (0, 0, 255), (float(x) * scale[0], float(y) * scale[1]), 2)
        pygame.draw.circle(scrn, (0, 0, 255), ((float(x) + 100) * 4, (float(y) + scale[0]) * scale[1]), 2)
    pygame.display.update()
    for i in pygame.event.get():
        if i.type == pygame.QUIT:
            status = False
    while(True):
        print('hi')


def detScale(Y, mi, ma):
    d = abs(mi)
    return d, Y / (ma+mi)

main()
