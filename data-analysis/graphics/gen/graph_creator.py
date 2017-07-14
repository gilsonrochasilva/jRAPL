import os
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from pandas import DataFrame

def path():
    return "/home/gilson/Documents/git/github/gilsonrochasilva/jRAPL/graphics/data/"


def remove(file):
    pdf = path() + '-eps-converted-to.pdf'
    if os.path.isfile(pdf):
        os.remove(pdf)


def create_time_graph(df, name, labels):
    ax = df[['WallClockTime']].plot(kind='bar', color=['#1F1F1F'], figsize=(8, 4), legend=False)
    ax.set_xticklabels(labels, rotation=0, fontsize='medium')
    ax.grid(False)

    ax.set_xlabel("")
    ax.set_ylabel("Time (S)")

    file = path() + 'figs/strategies/' + name
    plt.savefig(file + ".eps", format='eps')
    remove(file)


def create_energy_graph_system2(df, l, name):
    #if "Frequency" in df:
    #    df = df[df['Frequency'] == "2.6"]

    df['DramEnergy'] = df[['DramEnergy0']].values + df[['DramEnergy1']].values
    df['CPUEnergy'] = df[['CPUEnergy0']].values + df[['CPUEnergy1']].values
    packageEnergy = df[['PackageEnergy0']].values + df[['PackageEnergy1']].values

    df['Uncore'] = packageEnergy - df[['CPUEnergy']].values

    df['PackagePower'] =  packageEnergy / df[['WallClockTime']].values

    df['UncorePower'] = df[['Uncore']] / df[['WallClockTime']].values
    df['CPUPower'] = df[['CPUEnergy']] / df[['WallClockTime']].values
    df['DramPower'] = df[['DramEnergy']].values / df[['WallClockTime']].values

    ax = df[['CPUEnergy', 'Uncore','DramEnergy']].plot(kind='bar', stacked=True, color=['#AFAFAF', '#797979','#1F1F1F'], legend=False, figsize=(8, 4))

    box = ax.get_position()
    ax.set_position([box.x0 * 0.7, box.y0 + box.height * 0.1, box.width * 0.75, box.height * 0.9])

    patches, labels = ax.get_legend_handles_labels()
    ax.legend(patches[::-1], ['CPU', 'Uncore','DRAM'][::-1], loc='center left', bbox_to_anchor=(1.1, 0.5))
    ax.grid(False) # remove the dotted lines

    xticks = ax.get_xticks()
    ax2 = ax.twinx()
    ax.set_xticks(xticks)
    plt.xticks(rotation=0)

    ax2.plot(ax.get_xticks(), df[['UncorePower', 'CPUPower', 'DramPower']], linewidth=4, linestyle=':', marker='o')
    ax2.legend(['Uncore', 'CPU', 'DRAM'], loc='upper center', bbox_to_anchor=(0.5, -0.05), ncol=3)

    ax2.set_ylim((0,  max(df[('PackagePower')].values)))

    ax.set_xticklabels(l, rotation=0, fontsize='medium')

    ax.set_ylabel("Energy (J)")
    ax2.set_ylabel("Power (W)")

    file = path() + 'figs/strategies/' + name + "-energy"
    plt.savefig(file + ".eps", format='eps')
    remove(file)

    create_time_graph(df, name + "-time", l)

    print "Done!"




def create_frequency_graph_system2(option, name, f='output_node6e_without_turboboost.csv'):
    df = pd.read_csv(path()+ f, usecols=["Frequency", "OptionNum", "DramEnergy0", "CPUEnergy0", "PackageEnergy0", "DramEnergy1", "CPUEnergy1", "PackageEnergy1", "WallClockTime", "UserModeTime", "KernelModeTime"])

    df = df[(df['OptionNum'] == "option17")]

    # energy

    df['DramEnergy'] = df[['DramEnergy0']].values + df[['DramEnergy1']].values
    df['CPUEnergy'] = df[['CPUEnergy0']].values + df[['CPUEnergy1']].values
    packageEnergy = df[['PackageEnergy0']].values + df[['PackageEnergy1']].values

    df['UncoreEnergy'] = packageEnergy - df[['CPUEnergy']].values
    df['PackagePower'] =  packageEnergy / df[['WallClockTime']].values

    # power

    df['UncorePower'] = df[['UncoreEnergy']] / df[['WallClockTime']].values
    df['CPUPower'] = df[['CPUEnergy']] / df[['WallClockTime']].values
    df['DramPower'] = df[['DramEnergy']] / df[['WallClockTime']].values

    ax = df[['CPUEnergy', 'UncoreEnergy','DramEnergy']][::-1].plot(kind='bar', stacked=True, color=['#AFAFAF', '#797979','#1F1F1F'], legend=False, figsize=(8, 4))

    box = ax.get_position()
    ax.set_position([box.x0 * 0.7, box.y0 + box.height * 0.1, box.width * 0.75, box.height * 0.9])

    patches, labels = ax.get_legend_handles_labels()
    ax.legend(patches[::-1], ['CPU', 'Uncore','DRAM'][::-1], loc='center left', bbox_to_anchor=(1.1, 0.5))
    ax.grid(False) # remove the dotted lines

    xticks = ax.get_xticks()
    ax2 = ax.twinx()
    ax.set_xticks(xticks)
    plt.xticks(rotation=0)

    ax2.plot(ax.get_xticks(), df[['UncorePower', 'CPUPower', 'DramPower']][::-1], linewidth=4, linestyle=':', marker='o')
    ax2.legend(['UncorePower', 'CPUPower', 'DramPower'], loc='upper center', bbox_to_anchor=(0.5, -0.05), ncol=3)

    ax2.set_ylim((0,  max(df[('PackagePower')].values) + 5))
    ax.set_xticklabels(["1.2","1.4","1.6","1.8","2.0","2.2","2.4","2.6"], rotation=0, fontsize='medium')

    ax.set_ylabel("Energy (J)")
    ax2.set_ylabel("Power (W)")

    file = path() + 'figs/dvfs/node6e/' + name + "-energy"
    plt.savefig(file + ".eps", format='eps')
    remove(file)

    print "Done!"


"""
    this function creates figures for the DVFS experiments in System#1
"""

def create_frequency_graph_energy_time_system2(option, name, f='output_node6e_without_turboboost.csv'):
    df = pd.read_csv(path()+ f, usecols=["Frequency", "OptionNum", "DramEnergy0", "CPUEnergy0", "PackageEnergy0", "DramEnergy1", "CPUEnergy1", "PackageEnergy1", "WallClockTime", "UserModeTime", "KernelModeTime"])

    df = df[(df['OptionNum'] == option)]

    # energy
    df['DramEnergy'] = df[['DramEnergy0']].values + df[['DramEnergy1']].values
    df['CPUEnergy'] = df[['CPUEnergy0']].values + df[['CPUEnergy1']].values
    packageEnergy = df[['PackageEnergy0']].values + df[['PackageEnergy1']].values

    df['UncoreEnergy'] = packageEnergy - df[['CPUEnergy']].values
    df['PackagePower'] =  packageEnergy / df[['WallClockTime']].values

    # power
    df['UncorePower'] = df[['UncoreEnergy']] / df[['WallClockTime']].values
    df['CPUPower'] = df[['CPUEnergy']] / df[['WallClockTime']].values
    df['DramPower'] = df[['DramEnergy']] / df[['WallClockTime']].values

    ax = df[['CPUEnergy', 'UncoreEnergy','DramEnergy']][::-1].plot(kind='bar', stacked=True, color=['#AFAFAF', '#797979','#1F1F1F'], legend=False, figsize=(8, 4))

    box = ax.get_position()
    ax.set_position([box.x0 * 0.7, box.y0 + box.height * 0.1, box.width * 0.75, box.height * 0.9])

    patches, labels = ax.get_legend_handles_labels()
    ax.legend(patches[::-1], ['CPU', 'Uncore','DRAM'][::-1], loc='center left', bbox_to_anchor=(1.1, 0.5))
    ax.grid(False) # remove the dotted lines

    xticks = ax.get_xticks()
    ax2 = ax.twinx()
    ax.set_xticks(xticks)
    plt.xticks(rotation=0)

    ax2.plot(ax.get_xticks(), df[['WallClockTime', 'UserModeTime', 'KernelModeTime']][::-1], linewidth=4, linestyle=':', marker='o')
    ax2.legend(['WallClock', 'User', 'Kernel'], loc='upper center', bbox_to_anchor=(0.5, -0.05), ncol=3)

    ax2.set_ylim((0,  max(df[('WallClockTime')].values)))
    ax.set_xticklabels(["1.2","1.4","1.6","1.8","2.0","2.2","2.4","2.6"], rotation=0, fontsize='medium')

    ax.set_ylabel("Energy (J)")
    ax2.set_ylabel("Time (S)")

    file = path() + 'figs/dvfs/node6e/' + name + "-energy-time"
    plt.savefig(file + ".eps", format='eps')
    remove(file)

    print "Done!"
