{Sample program
inTJRAC language
computerfactiorial
asd}
read x;{input an integer}
if 0<x then {don't compute if x<=0}
fact:=1
repeat
fact:=fact*x;
x:=x-1
until x=0
write fact
end
x:=5
write end
13;fact
/* exegesis */
// exegesis2
