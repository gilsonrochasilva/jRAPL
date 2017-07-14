import sys
import pandas as pd
import gen.graph_creator as g
from gen.graph_creator import path


def dacapo():
    df = pd.read_csv(path()+'xalan.csv', usecols=["Frequency", "MethodName", "DramEnergy0", "CPUEnergy0", "PackageEnergy0", "DramEnergy1", "CPUEnergy1", "PackageEnergy1", "WallClockTime", "UserModeTime", "KernelModeTime"])

    df = df[(df['MethodName'] == "SunflowDoWork") | (df['MethodName'] == "XalanDoWork") | (df['MethodName'] == "H2DoWork")]
    # g.create_energy_graph_system2(df, ['Sunflow', 'Xalan', 'H2'], 'node6e/dacapo')
    g.create_energy_graph_system2(df, ['Xalan'], 'xalan/dacapo')


dacapo()
