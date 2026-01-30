import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv("log.csv")

df['joint_CD'] = ((df['a1'] == 0) & (df['a2'] == 1)).astype(int)
df['joint_CC'] = ((df['a1'] == 0) & (df['a2'] == 0)).astype(int)
df['joint_DC'] = ((df['a1'] == 1) & (df['a2'] == 0)).astype(int)
df['joint_DD'] = ((df['a1'] == 1) & (df['a2'] == 1)).astype(int)

df['CD_cumsum'] = df['joint_CD'].cumsum()
df['CC_cumsum'] = df['joint_CC'].cumsum()
df['DC_cumsum'] = df['joint_DC'].cumsum()
df['DD_cumsum'] = df['joint_DD'].cumsum()

#plot cumulative sums 
plt.figure(figsize=(10,6))
first_CD_iter = df[df['joint_CD']==1]['iter'].iloc[0]
plt.axvline(x=first_CD_iter, color='black', linestyle=':', label='First (C,D)')
first_DC_iter = df[df['joint_DC']==1]['iter'].iloc[0]
plt.axvline(x=first_DC_iter, color='black', linestyle=':', label='First (D,C)')
plt.plot(df['iter'], df['CD_cumsum'], label='Joint Action (C, D)', color='blue')
plt.plot(df['iter'], df['CC_cumsum'], label='Joint Action (C, C)', color='green')
plt.plot(df['iter'], df['DC_cumsum'], label='Joint Action (D, C)', color='yellow')
plt.plot(df['iter'], df['DD_cumsum'], label='Joint Action (D, D)', color='red')
plt.xlabel('Iteration')
plt.ylabel('Cumulative occurrences')
plt.title('Cumulative Joint Actions over Time')
plt.legend()
plt.grid(True)
plt.show()

#plot q values over iterations
plt.plot(df['iter'], df['q1C'], label='Agent 1\'s Q(C)', color='blue')
plt.plot(df['iter'], df['q1D'], label='Agent 1\'s Q(D)', color='green')
plt.plot(df['iter'], df['q2C'], label='Agent 2\'s Q(C)', color='yellow')
plt.plot(df['iter'], df['q2D'], label='Agent 2\'s Q(D)', color='red')
plt.xlabel('Iteration')
plt.ylabel('Q values')
plt.title('Q values over Time')
plt.legend()
plt.grid(True)
plt.show()