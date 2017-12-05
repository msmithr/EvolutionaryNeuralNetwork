# normalize inputs for a data set
# usage: ./normalize_data <input filename> <output filename>

from sys import argv
import sys

if len(argv) != 3:
    print("./normalize_data <input filename> <output filename>")
    exit(1)

f = open(argv[1], "r")

iteration = 0;
inputs = list()
outputs = list()

for line in f:
    temp = list()
    line = line.rstrip()
    if (iteration % 2) == 0:
        for i in line.split(" "):
            temp.append(float(i))
        inputs.append(temp)
    else:
        for i in line.split(" "):
            temp.append(float(i))
        outputs.append(temp)

    iteration += 1

f.close()

maximums = list()
minimums = list()
out_maximums = list()
out_minimums = list()

for i in range(len(inputs[0])):
    tempmax = sys.float_info.min
    tempmin = sys.float_info.max

    for j in range(len(inputs)):
        if inputs[j][i] > tempmax:
            tempmax = inputs[j][i]
        if inputs[j][i] < tempmin:
            tempmin = inputs[j][i]

    maximums.append(tempmax)
    minimums.append(tempmin)

for i in range(len(inputs)):
    for j in range(len(inputs[0])):
        inputs[i][j] = (inputs[i][j] - minimums[j]) / (maximums[j] - minimums[j])

f = open(sys.argv[2], "w")
for i in range(len(inputs)):
    for j in range(len(inputs[i])):
        f.write(str(inputs[i][j]))
        f.write(" ")
    f.write("\n")
    for j in range(len(outputs[i])):
        f.write(str(outputs[i][j]))
        f.write(" ")
    f.write("\n")
