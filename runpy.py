from textblob import TextBlob
import math
filename="C:\\Users\\bhaskar\\Documents\\NetBeansProjects\\TwitterAnalyzer\\textcollection.txt"
lines = open(filename,encoding='utf8').read().split('$$$$$')
count_positive=0
count_negative=0
count_neutral=0
count=0
y=0
count_sent=0
for x in lines:
    y+=1
    blob = TextBlob(x)
    sum=0
    count=0
    #print("tweet #:"+str(y))
    for sentence in blob.sentences:
        sum=sum+sentence.sentiment.polarity
        count=count+1
        count_sent = count_sent+1
        #print(str(count)+"::::"+sentence.string+":::::"+str(sentence.sentiment.polarity))
    if(count!=0):
        sum=sum/count
        if(sum<=-0.1):
            count_negative+=1
            #print("tweet#"+str(y)+" is negative")
        elif(0.000005>sum>-0.1):
            count_neutral+=1
            #print("tweet#"+str(y)+" is neutral")
        else:
            count_positive+=1
            #print("tweet#"+str(y)+" is positive")
print ("negative->"+str(count_negative))
print("neutral->"+str(count_neutral))
print("positive->"+str(count_positive))
print("no of tweets->"+str(y))
print("no of sentences->"+str(count_sent))
f = open('C:\\Users\\bhaskar\\Documents\\NetBeansProjects\\TwitterAnalyzer\\sentimentoutput.txt','w')
f.write(str(math.ceil(count_negative*100/y))+'\n'+str(math.ceil(count_neutral*100/y))+'\n'+str(100.0-math.ceil(count_negative*100/y)-math.ceil(count_neutral*100/y)))
f.close()
