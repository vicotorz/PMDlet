function pmd_draw(path)
y=load(path);
y=y';
s=size(y,2);
X=zeros(1,3*s);
Y=zeros(1,3*s);
for i=1:3:size(y,2)
   for j=i:1:i+3
    X(1,j)=i;
   end
end
start=1;
for i=1:3:size(y,2)
    Y(1,i+1)=y(1,start);
    start=start+1;
end
xlabel('SAR');
ylabell('Frequency');
plot(X,Y)