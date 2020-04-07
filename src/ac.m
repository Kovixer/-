clc;
clear;
close all;

comp1 = 'task1.csv';
comp2 = 'task2.csv';
comp3 = 'task3.csv';
nfig = 1;

figure(nfig);nfig = nfig + 1;
c1 = readtable(comp1);c2 = readtable(comp2);c3 = readtable(comp3);
a1 = table2array(c1);
a2 = table2array(c2);
a3 = table2array(c3);
a1 = a1(:, 2);
a2 = a2(:, 2);
a3 = a3(:, 2); 
plot(a1);
hold on;
plot(a2);
hold on;
plot(a3);
legend("l = k", "l < k", "l > k");
title('Pe');
grid on;
