#!/usr/bin/python

"""
reportGen.py
Huang Geyang
u5421856

"""
# imports
import datetime
import sys,os
import time
sys.path.append(os.path.realpath('..'))


# files
hgFile = open("build/snapshots/hg_status.txt","r")
countFile = open("build/snapshots/countJava.txt","r")
testFile = open("build/snapshots/TEST-armidale.api.io.FileTest.txt","r")

# some html formats
html_start = """
             <html xmlns="http://wwww.w3.org/1999/xhtml" xml:lang="en">
             <head>
             <title>COMP2100 - Snapshot Report</title>
             <link href="http://styles.anu.edu.au/_anu/3/style/anu-common.css" rel="stylesheet" type="text/css" media="screen"/> 
             </head>
             <body>
             """

html_end = """
           </body>
           </html>
           """

title = ("<h1>Armidale Snapshot Summary Report</h1>"
         "<p>Generated "+time.strftime("%c")+"</p>")

# File Changes
lines = hgFile.readlines()
added = 0
modified = 0
deleted = 0
missing = 0
unknown = 0
for line in lines:
    if line[0] == 'A':
        added += 1
    elif line[0] == 'M':
        modified += 1
    elif line[0] == 'R':
        deleted += 1
    elif line[0] == '!':
        missing += 1
    elif line[0] == '?':
        unknown += 1

hgFile.close()
hg_report = ("<h2>File Changes</h2>"
             "<ul>"
             "<li>"+str(added)+" file(s) added</li>"
             "<li>"+str(modified)+" file(s) modified</li>"
             "<li>"+str(deleted)+" file(s) deleted</li>"
             "<li>"+str(missing)+" file(s) are missing</li>"
             "<li>"+str(unknown)+" file(s) are unknown to Mercurial</li>"             
             "</ul>")

# Line Counts
num_file = 0
result_line = ""
for line in countFile:
    num_file += 1
    line = line.strip()
    if line[-5:] != ".java":
        result_line = line
        num_file -= 1

countFile.close()

lst = []
lst = result_line.split()
lst2 = []

for element in lst:
    temp = ""
    for char in element:
        if char == "(":
            break
        else:
            temp +=char
    lst2.append(temp)

lst2.pop() # remove TOTAL


line_counts = ("<h2>Line Counts</h2>"
               "<p>"+str(num_file)+" Java source files counted</p>"
               "<ul>"
               "<li>"+str(lst2[0])+" total lines</li>"
               "<li>"+str(lst2[2])+" comments</li>"
               "<li>"+str(lst2[3])+" blank lines</li>"
               "<li>"+str(lst2[1])+" source lines of code</li>"
               "</ul>")

# Test Results
lines = testFile.readlines()
valuableLine = lines[1]
testFile.close()
lst3 = valuableLine.split(',')
lst4 = []
for element in lst3:
    temp = ""
    for char in element:
        if char.isdigit():
            temp += char
    lst4.append(temp)
test_results =  ("<h2>Test Results</h2>"
                 "<ul>"
                 "<li>"+lst4[0]+" test run(s)</li>"
                 "<li>"+lst4[1]+" failed</li>"
                 "<li>"+lst4[2]+" passed</li>"
                 "<li>"+lst4[3]+" errors</li>"
                 "</ul>")

# findbugs report link
findBugs = ("<h2>Possible Bugs</h2>"
            "<li><a href=\"findBugsReport.html\">FindBugs report</a></li>")

# Write them all into the report
report = open("build/snapshots/snapshotReport.html","w")
report.write(html_start)
report.write(title)
report.write(hg_report)
report.write(line_counts)
report.write(test_results)
report.write(findBugs)
report.write(html_end)
report.close()