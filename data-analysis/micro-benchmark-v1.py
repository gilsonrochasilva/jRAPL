import datetime as dt
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

def path():
    return "/home/gilson/Documents/git/github/gilsonrochasilva/jRAPL/data-analysis/"

def generateGraph(prefix, energyUncore, energyDRAM, energyCPU, powerDataUncore, powerDataDRAM, powerDataCPU):

    barSpace = 0.25
    barWidth = 0.5
    barStep = barWidth + barSpace

    xTicks = ('BR', 'LNR', 'CAR', 'PBR', 'FR', 'SR')

    energyLocations = np.arange(0, energyUncore.__len__() * barStep, barStep)
    powerLocations = np.arange(0 + barSpace, powerDataUncore.__len__() * barStep, barStep)

    figure, energyAxis = plt.subplots()

    energyAxis.set_ylabel('Energy (J)')
    e1 = energyAxis.bar(energyLocations, energyUncore, barWidth, color='#E0E0E0')
    e2 = energyAxis.bar(energyLocations, energyDRAM, barWidth, bottom=energyUncore, color='#A0A0A0')
    e3 = energyAxis.bar(energyLocations, energyCPU, barWidth, bottom=energyDRAM, color='#606060')

    energyAxis.legend((e1[0], e2[0], e3[0]), ('UNCORE', 'DRAM', 'CPU'), loc='center right', bbox_to_anchor=(1.47, 0.5))

    powerAxis = energyAxis.twinx()
    powerAxis.set_ylabel('Power (W)')

    p1 = powerAxis.plot(powerLocations, powerDataUncore, 'bo--')
    p2 = powerAxis.plot(powerLocations, powerDataDRAM, 'go--')
    p3 = powerAxis.plot(powerLocations, powerDataCPU, 'ro--')

    powerAxis.legend((p1[0], p2[0], p3[0]), ('UNCORE', 'DRAM', 'CPU'), loc='lower center', ncol=3, bbox_to_anchor=(0.5, -0.14))

    figure.tight_layout()
    figure.subplots_adjust(right=0.7, bottom=0.14)

    plt.xticks(powerLocations, xTicks)
    plt.savefig(path() + 'results/' + prefix + '-' + dt.datetime.now().strftime("%Y-%m-%d %H:%M:%S")+ ".eps", format='eps')
    # plt.show()

def main():
    dataFrame = pd.read_csv(path() + '/inputs/test-3-reader-read-20m.csv', usecols=['CLASS', 'UNCORE-ENERGY', 'DRAM-ENERGY', 'CPU-ENERGY', 'UNCORE-POWER', 'DRAM-POWER', 'CPU-POWER'])
    generateGraph(
        'test-2-reader-read-20mb',
        dataFrame['UNCORE-ENERGY'].values,
        dataFrame['DRAM-ENERGY'].values,
        dataFrame['CPU-ENERGY'].values,
        dataFrame['UNCORE-POWER'].values,
        dataFrame['DRAM-POWER'].values,
        dataFrame['CPU-POWER'].values
    )

main()