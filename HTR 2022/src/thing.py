from py4j.java_gateway import JavaGateway
gateway = JavaGateway()
lexicon_driver = gateway.entry_point
fN = "scraped.txt"
lst = lexicon_driver.main2(fN)

emotions = ["anger", "anticipation", "disgust", "fear", "joy", "sadness", "surprise", "trust"]
stringOutput = []

j = -1
for i in lst:
    j += 1
    if i >= 8:
        stringOutput.append("Very high tones of " + emotions[j])
    elif i > 6 and i < 8:
        stringOutput.append("High tones of " + emotions[j])
    elif i > 4 and i < 6:
        stringOutput.append("Medium tones of " + emotions[j])
    elif i > 2 and i < 4:
        stringOutput.append("Low tones of " + emotions[j])
    else:
        stringOutput.append("Very low tones of " + emotions[j])

print(lst[0])

gateway.shutdown()





