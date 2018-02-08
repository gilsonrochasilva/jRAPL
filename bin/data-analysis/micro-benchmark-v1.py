import datetime as dt
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import sys

def path():
    return "/home/gilson/Documents/git/github/gilsonrochasilva/jRAPL/data-analysis/"

def barGraph(prefix, xTicks, energyUncore, energyDRAM, energyCPU, powerDataUncore, powerDataDRAM, powerDataCPU):

    barSpace = 0.25
    barWidth = 0.5

    # barSpace = 2.0
    # barWidth = 0.5
    barStep = barWidth + barSpace

    energyLocations = np.arange(0, energyUncore.__len__() * barStep, barStep)
    powerLocations = np.arange(0 + barSpace, powerDataUncore.__len__() * barStep, barStep)

    figure, energyAxis = plt.subplots()

    energyAxis.set_ylabel('Energy (J)')
    e1 = energyAxis.bar(energyLocations, energyUncore + energyDRAM + energyCPU, barWidth, color='#A0A0A0')
    # e1 = energyAxis.bar(energyLocations, energyUncore, barWidth, color='#E0E0E0')
    # e2 = energyAxis.bar(energyLocations, energyDRAM, barWidth, bottom=energyUncore, color='#A0A0A0')
    # e3 = energyAxis.bar(energyLocations, energyCPU, barWidth, bottom=energyDRAM, color='#606060')

    # energyAxis.legend(['Energy Consumption'], loc='lower center')
    energyAxis.legend(['Energy Consumption'], loc='lower center', bbox_to_anchor=(0.5, -0.14))
    # energyAxis.legend((e1[0], e2[0], e3[0]), ('UNCORE', 'DRAM', 'CPU'), loc='center right', bbox_to_anchor=(1.47, 0.5))

    powerAxis = energyAxis.twinx()
    powerAxis.set_ylabel('Power (W)')

    p1 = powerAxis.plot(powerLocations, powerDataUncore, 'bo--')
    p2 = powerAxis.plot(powerLocations, powerDataDRAM, 'go--')
    p3 = powerAxis.plot(powerLocations, powerDataCPU, 'ro--')

    powerAxis.legend((p1[0], p2[0], p3[0]), ('UNCORE', 'DRAM', 'CPU'), loc='center top')
    # powerAxis.legend((p1[0], p2[0], p3[0]), ('UNCORE', 'DRAM', 'CPU'), loc=0)

    figure.tight_layout()
    figure.subplots_adjust(bottom=0.14)

    plt.xticks(powerLocations, xTicks)
    plt.savefig(path() + 'results/' + prefix + '-' + dt.datetime.now().strftime("%Y-%m-%d %H:%M:%S")+ ".png", format='png')
    # plt.show()

def boxplotGraph(prefix, xTicks, energyData):
    figure, energyAxis = plt.subplots()

    energyAxis.set_ylabel('Energy (J)')
    energyAxis.boxplot(energyData, labels=xTicks)

    plt.savefig(path() + 'results/' + prefix + '-' + dt.datetime.now().strftime("%Y-%m-%d %H:%M:%S") + ".png", format='png')
    # plt.show()

def main():

    testNumber = "8";

    readerData = pd.read_csv(path() + 'inputs/test-' + testNumber + '-reader-read-20mb.csv', usecols=['CLASS', 'UNCORE-ENERGY', 'DRAM-ENERGY', 'CPU-ENERGY', 'UNCORE-POWER', 'DRAM-POWER', 'CPU-POWER'])
    readerDataBoxplot = pd.read_csv(path() + 'inputs/test-' + testNumber + '-reader-read-boxplot-20mb.csv', usecols=['CLASS', 'MAX', 'Q3', 'Q2', 'Q1', 'MIN'])

    writerData = pd.read_csv(path() + 'inputs/test-' + testNumber + '-writer-write-20mb.csv', usecols=['CLASS', 'UNCORE-ENERGY', 'DRAM-ENERGY', 'CPU-ENERGY', 'UNCORE-POWER', 'DRAM-POWER', 'CPU-POWER'])
    writerDataBoxplot = pd.read_csv(path() + 'inputs/test-' + testNumber + '-writer-write-boxplot-20mb.csv', usecols=['CLASS', 'MAX', 'Q3', 'Q2', 'Q1', 'MIN'])

    inputStreamData = pd.read_csv(path() + 'inputs/test-' + testNumber + '-input-stream-read-20mb.csv', usecols=['CLASS', 'UNCORE-ENERGY', 'DRAM-ENERGY', 'CPU-ENERGY', 'UNCORE-POWER', 'DRAM-POWER', 'CPU-POWER'])
    inputStreamDataBoxplot = pd.read_csv(path() + 'inputs/test-' + testNumber + '-input-stream-read-boxplot-20mb.csv', usecols=['CLASS', 'MAX', 'Q3', 'Q2', 'Q1', 'MIN'])

    outputStreamData = pd.read_csv(path() + 'inputs/test-' + testNumber + '-output-stream-write-20mb.csv', usecols=['CLASS', 'UNCORE-ENERGY', 'DRAM-ENERGY', 'CPU-ENERGY', 'UNCORE-POWER', 'DRAM-POWER', 'CPU-POWER'])
    outputStreamDataBoxplot = pd.read_csv(path() + 'inputs/test-' + testNumber + '-output-stream-write-boxplot-20mb.csv', usecols=['CLASS', 'MAX', 'Q3', 'Q2', 'Q1', 'MIN'])

    barGraph('test-' + testNumber + '-reader-20mb', readerData['CLASS'].values, readerData['UNCORE-ENERGY'].values, readerData['DRAM-ENERGY'].values, readerData['CPU-ENERGY'].values, readerData['UNCORE-POWER'].values, readerData['DRAM-POWER'].values, readerData['CPU-POWER'].values)
    boxplotGraph('test-' + testNumber + '-reader-read-boxplot-20mb', readerDataBoxplot['CLASS'].values, readerDataBoxplot[['MAX', 'Q3', 'Q2', 'Q1', 'MIN']].values.tolist())

    barGraph('test-' + testNumber + '-writer-write-20mb', writerData['CLASS'].values, writerData['UNCORE-ENERGY'].values, writerData['DRAM-ENERGY'].values, writerData['CPU-ENERGY'].values, writerData['UNCORE-POWER'].values, writerData['DRAM-POWER'].values, writerData['CPU-POWER'].values)
    boxplotGraph('test-' + testNumber + '-writer-write-boxplot-20mb', writerDataBoxplot['CLASS'].values, writerDataBoxplot[['MAX', 'Q3', 'Q2', 'Q1', 'MIN']].values.tolist())

    barGraph('test-' + testNumber + '-input-stream-read-20mb', inputStreamData['CLASS'].values, inputStreamData['UNCORE-ENERGY'].values, inputStreamData['DRAM-ENERGY'].values, inputStreamData['CPU-ENERGY'].values, inputStreamData['UNCORE-POWER'].values, inputStreamData['DRAM-POWER'].values, inputStreamData['CPU-POWER'].values)
    boxplotGraph('test-' + testNumber + '-input-stream-read-boxplot-20mb', inputStreamDataBoxplot['CLASS'].values, inputStreamDataBoxplot[['MAX', 'Q3', 'Q2', 'Q1', 'MIN']].values.tolist())

    barGraph('test-' + testNumber + '-output-stream-write-20mb', outputStreamData['CLASS'].values, outputStreamData['UNCORE-ENERGY'].values, outputStreamData['DRAM-ENERGY'].values, outputStreamData['CPU-ENERGY'].values, outputStreamData['UNCORE-POWER'].values, outputStreamData['DRAM-POWER'].values, outputStreamData['CPU-POWER'].values)
    boxplotGraph('test-' + testNumber + '-output-stream-write-boxplot-20mb', outputStreamDataBoxplot['CLASS'].values, outputStreamDataBoxplot[['MAX', 'Q3', 'Q2', 'Q1', 'MIN']].values.tolist())

def scanner():

    testNumber = "9";

    readerData = pd.read_csv(path() + 'inputs/test-' + testNumber + '-scanner-next-20mb.csv', usecols=['CLASS', 'UNCORE-ENERGY', 'DRAM-ENERGY', 'CPU-ENERGY', 'UNCORE-POWER', 'DRAM-POWER', 'CPU-POWER'])
    readerDataBoxplot = pd.read_csv(path() + 'inputs/test-' + testNumber + '-scanner-next-boxplot-20mb.csv', usecols=['CLASS', 'MAX', 'Q3', 'Q2', 'Q1', 'MIN'])

    barGraph('test-' + testNumber + '-scanner-next-20mb', readerData['CLASS'].values, readerData['UNCORE-ENERGY'].values, readerData['DRAM-ENERGY'].values, readerData['CPU-ENERGY'].values, readerData['UNCORE-POWER'].values, readerData['DRAM-POWER'].values, readerData['CPU-POWER'].values)
    boxplotGraph('test-' + testNumber + '-scanner-next-boxplot-20mb', readerDataBoxplot['CLASS'].values, readerDataBoxplot[['MAX', 'Q3', 'Q2', 'Q1', 'MIN']].values.tolist())

def generate(barChartData, distributionChartData):
    readerData = pd.read_csv(path() + 'inputs/' + barChartData, usecols=['CLASS', 'UNCORE-ENERGY', 'DRAM-ENERGY', 'CPU-ENERGY', 'UNCORE-POWER', 'DRAM-POWER', 'CPU-POWER'])
    readerDataBoxplot = pd.read_csv(path() + 'inputs/' + distributionChartData, usecols=['CLASS', 'MAX', 'Q3', 'Q2', 'Q1', 'MIN'])

    barGraph(barChartData.replace('.csv', ''), readerData['CLASS'].values, readerData['UNCORE-ENERGY'].values, readerData['DRAM-ENERGY'].values, readerData['CPU-ENERGY'].values, readerData['UNCORE-POWER'].values, readerData['DRAM-POWER'].values, readerData['CPU-POWER'].values)
    boxplotGraph(distributionChartData.replace('.csv', ''), readerDataBoxplot['CLASS'].values, readerDataBoxplot[['MAX', 'Q3', 'Q2', 'Q1', 'MIN']].values.tolist())

generate(sys.argv[1], sys.argv[2])