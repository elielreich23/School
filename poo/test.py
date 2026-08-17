name = input("what  is your name ")

print ("Hello " + name + "!")

age = int(input( "how old are you ?"))
if age>= 18:
    print ("Hello " + name + " horray you are an adult")
else : 
    print("too young ")

quant = int(input ("enter a number"))

value = []
for i in range (0, quant, 1 ):
    value = input('enter the value {} : ' . format(i+1))