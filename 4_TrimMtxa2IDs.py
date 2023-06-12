import sys

TAX = open(sys.argv[1], 'r')
OUT = open(sys.argv[2], 'w')

# make dictionary of GIs and predicted Taxonomies
PRDCT_TAX = {}
for line in TAX:
    line = line.strip().split('\t')
    GI = line[0]
    LINEAGE = line[1].split(';')
    for i in range(0,8):
        if len(LINEAGE) < 8:
            LINEAGE.append('')
           
    SCORE = line[-1]                   
    PI = float(line[-2])
    if PI >= 98:                                ### this number means anything with >= 98 percent ID match to reference will be annotated to species
        X = str(';'.join(LINEAGE[0:7]))
        PRDCT_TAX[GI] = X
    elif 98 > PI >= 96:                         ### greater than 96 percent ID annotated to genus
        X = str(';'.join(LINEAGE[0:6]))
        PRDCT_TAX[GI] = X
    elif 96 > PI >= 85:                         ### annotated to family, ...
        X = str(';'.join(LINEAGE[0:5]))
        PRDCT_TAX[GI] = X
    elif 85 > PI >= 80:
        X = str(';'.join(LINEAGE[0:4]))
        PRDCT_TAX[GI] = X
#   else:
#                X = str(';'.join(LINEAGE[0:3]))
#                PRDCT_TAX[GI] = X

for key in PRDCT_TAX:
    OUT.write(str(key) + '\t' + PRDCT_TAX[key] + '\n')

OUT.close()
TAX.close()

