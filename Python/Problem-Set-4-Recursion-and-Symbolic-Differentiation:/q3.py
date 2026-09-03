import math
# You are not allowed to use python's eval() or exec() functions

def calculate(F, x):
    
    if len(F)==6:
        if F[0]=="s":
            return math.sin(x)
        elif F[0]=="c":
            return math.cos(x)
        elif F[0]=="e":
            return math.exp(x)
    
    if "+" in F:
        index=F.index("+")
        return calculate(F[:index], x)+calculate(F[index+1:],x)
    if "*" in F:
        index=F.index("*")
        return calculate(F[:index], x)*calculate(F[index+1:], x)
    
    if F[0]=="s":
        return math.sin(calculate(F[4:-1], x))
    if F[0]=="c":
        return math.cos(calculate(F[4:-1], x))
    if F[0]=="e":
        return math.exp(calculate(F[4:-1], x))


def derivative(F, x):
    
    
    if len(F)==6:
        if F[0]=="s":
            return math.cos(x)
        elif F[0]=="c":
            return -math.sin(x)
        elif F[0]=="e":
            return math.exp(x)
    
    if "+" in F:
        index=F.index("+")
        return derivative(F[:index], x)+derivative(F[index+1:],x)
    if "*" in F:
        index=F.index("*")
        return derivative(F[:index], x)*calculate(F[index+1:], x)+calculate(F[:index], x)*derivative(F[index+1:], x)
    
    if F[0]=="s":
        return math.cos(calculate(F[4:-1], x))*derivative(F[4:-1], x)
    if F[0]=="c":
        return -math.sin(calculate(F[4:-1], x))*derivative(F[4:-1],x)
    if F[0]=="e":
        return math.exp(calculate(F[4:-1], x))*derivative(F[4:-1], x)  
    


if __name__ == '__main__':
    print(calculate('sin(exp(x))', 0.7))                                      # approximately  0.903488472004459
    print(calculate('sin(cos(x))*exp(sin(cos(sin(x))))*cos(exp(x))', 1.2))    # approximately -0.6116323306863213
    print(calculate('sin(x)+cos(x)', 0.5))                                    # approximately  1.3570081004945758
    print(derivative('sin(exp(x))', 0.7))                                     # approximately -0.8631193610376219
    print(derivative('sin(cos(x))*exp(sin(cos(sin(x))))*cos(exp(x))', 1.2))   # approximately  2.017408588854982
    print(derivative('sin(x)+cos(x)', 0.5))                                   # approximately  0.39815702328616975
