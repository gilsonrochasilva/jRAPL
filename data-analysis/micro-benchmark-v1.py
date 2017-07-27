import datetime as dt
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

def path():
    return "/home/gilson/Documents/git/github/gilsonrochasilva/jRAPL/data-analysis/"

def barGraph(prefix, xTicks, energyUncore, energyDRAM, energyCPU, powerDataUncore, powerDataDRAM, powerDataCPU):

    barSpace = 0.25
    barWidth = 0.5
    barStep = barWidth + barSpace

    energyLocations = np.arange(0, energyUncore.__len__() * barStep, barStep)
    powerLocations = np.arange(0 + barSpace, powerDataUncore.__len__() * barStep, barStep)

    figure, energyAxis = plt.subplots()

    energyAxis.set_ylabel('Energy (J)')
    e1 = energyAxis.bar(energyLocations, energyUncore + energyDRAM + energyCPU, barWidth, color='#A0A0A0')
    # e1 = energyAxis.bar(energyLocations, energyUncore, barWidth, color='#E0E0E0')
    # e2 = energyAxis.bar(energyLocations, energyDRAM, barWidth, bottom=energyUncore, color='#A0A0A0')
    # e3 = energyAxis.bar(energyLocations, energyCPU, barWidth, bottom=energyDRAM, color='#606060')

    energyAxis.legend(['Energy Consumption'], loc='lower center', bbox_to_anchor=(0.5, -0.14))
    # energyAxis.legend((e1[0], e2[0], e3[0]), ('UNCORE', 'DRAM', 'CPU'), loc='center right', bbox_to_anchor=(1.47, 0.5))

    powerAxis = energyAxis.twinx()
    powerAxis.set_ylabel('Power (W)')

    p1 = powerAxis.plot(powerLocations, powerDataUncore, 'bo--')
    p2 = powerAxis.plot(powerLocations, powerDataDRAM, 'go--')
    p3 = powerAxis.plot(powerLocations, powerDataCPU, 'ro--')

    powerAxis.legend((p1[0], p2[0], p3[0]), ('UNCORE', 'DRAM', 'CPU'), loc='center right', bbox_to_anchor=(1.47, 0.5))

    figure.tight_layout()
    figure.subplots_adjust(right=0.7, bottom=0.14)

    plt.xticks(powerLocations, xTicks)
    plt.savefig(path() + 'results/' + prefix + '-' + dt.datetime.now().strftime("%Y-%m-%d %H:%M:%S")+ ".eps", format='eps')
    # plt.show()

def boxplotGraph(prefix, xTicks, energyData):
    figure, energyAxis = plt.subplots()

    energyAxis.set_ylabel('Energy (J)')
    energyAxis.boxplot(energyData, labels=xTicks)

    plt.savefig(path() + 'results/' + prefix + '-' + dt.datetime.now().strftime("%Y-%m-%d %H:%M:%S") + ".eps", format='eps')
    # plt.show()

def main():

    testNumber = "6";

    readData = pd.read_csv(path() + 'inputs/test-' + testNumber + '-reader-read-20mb.csv', usecols=['CLASS', 'UNCORE-ENERGY', 'DRAM-ENERGY', 'CPU-ENERGY', 'UNCORE-POWER', 'DRAM-POWER', 'CPU-POWER'])
    barGraph('test-' + testNumber + '-reader-read-20mb', readData['CLASS'].values, readData['UNCORE-ENERGY'].values, readData['DRAM-ENERGY'].values, readData['CPU-ENERGY'].values, readData['UNCORE-POWER'].values, readData['DRAM-POWER'].values, readData['CPU-POWER'].values)

    readDataBoxplot = pd.read_csv(path() + 'inputs/test-' + testNumber + '-reader-read-boxplot-20mb.csv', usecols=['CLASS', 'MAX', 'Q3', 'Q2', 'Q1', 'MIN'])
    boxplotGraph('test-' + testNumber + '-reader-read-boxplot-20mb', readDataBoxplot['CLASS'].values, readDataBoxplot[['MAX', 'Q3', 'Q2', 'Q1', 'MIN']].values.tolist())

    writeData = pd.read_csv(path() + 'inputs/test-' + testNumber + '-writer-write-20mb.csv', usecols=['CLASS', 'UNCORE-ENERGY', 'DRAM-ENERGY', 'CPU-ENERGY', 'UNCORE-POWER', 'DRAM-POWER', 'CPU-POWER'])
    barGraph('test-' + testNumber + '-writer-write-20mb', writeData['CLASS'].values, writeData['UNCORE-ENERGY'].values, writeData['DRAM-ENERGY'].values, writeData['CPU-ENERGY'].values, writeData['UNCORE-POWER'].values, writeData['DRAM-POWER'].values, writeData['CPU-POWER'].values)

    writeDataBoxplot = pd.read_csv(path() + 'inputs/test-' + testNumber + '-writer-write-boxplot-20mb.csv', usecols=['CLASS', 'MAX', 'Q3', 'Q2', 'Q1', 'MIN'])
    boxplotGraph('test-' + testNumber + '-writer-write-boxplot-20mb', writeDataBoxplot['CLASS'].values, writeDataBoxplot[['MAX', 'Q3', 'Q2', 'Q1', 'MIN']].values.tolist())

main()