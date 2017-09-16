import os
import generator
import time
import multiprocessing


filename = "homework.java"
name = filename[:-5]
# print(name)

checkFileName = "Validator.java"
checkName = checkFileName[:-5]

def run_algo():
	os.system("java " + name)

def run_check():
	os.system("java " + checkName + " > result.txt")
	f = open("result.txt", 'r')
	if f.read() == "FAILED":
		os.system("cp input.txt fails")
	f.close()

def wrap_algo():
	p = multiprocessing.Process(target=run_algo, name="run_algo")
	p.start()
	t = time.time()
	while True:
		cur = time.time()
		if cur - t >= 300:
			if p.is_alive():
				print("terminating the algorithm ")
				os.system("echo $(pidof -s java " + name + ")")
				os.system("kill -9 $(pidof -s java " + name + ")")
				os.system("cp input.txt fails")
		break
		if p.is_alive() is False:
			break
		time.sleep(10)

def wrap_check():
	p = multiprocessing.Process(target=run_check, name="run_check")
	p.start()
	t = time.time()
	while True:
		cur = time.time()
		if cur - t >= 300:
			if p.is_alive():
				print("terminating the algorithm ")
				os.system("kill -9 $(pidof -s java " + checkName + ")")
			break
		if p.is_alive() is False:
			break
		time.sleep(2)

def run_suit():
	os.system("javac " + filename)
	os.system("javac " + checkFileName)
	count = 0
	while count < iterations:
		if count % 10 == 0:
			print("running for iteration " + str(count))
		generator.generate()	
		wrap_algo()
		wrap_check()
		count += 1

iterations = 10000
run_suit()