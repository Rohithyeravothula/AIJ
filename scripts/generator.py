import random

#max board size
n = 10

#max num of trees
t = 3

methods = ["DFS", "BFS", "SA"]


class Node(object):
	def __init__(self, board, size, lizCount, methodName):
		self.board = board
		self.size = size
		self.lizCount = lizCount
		self.methodName = methodName

def write_board_to_file(node):
	l = len(node.board)
	w = ""
	for i in range(0, l):
		s = "".join(str(x) for x in node.board[i])
		w += s+"\n"
	w = w[:-1]
	f=open("input.txt", 'w')
	f.write(node.methodName+"\n")
	f.write(node.size+"\n")
	f.write(node.lizCount+"\n")
	f.write(w)
	f.close()


def get_lizard(n, l):
	r = random.randint(1, n+l)
	t = random.randint(0, 1)
	return [r, n][t]


def generate():
	cur_n = random.randint(1, n)
	cur_t = random.randint(1, cur_n)
	cur_liz = get_lizard(cur_n, cur_t)
	methodName = methods[random.randint(0, 2)]
	board = []
	for i in range(0, cur_n):
		d=[0]*cur_n
		board.append(d)

	i=0
	while i<t:
		x,y=(random.randint(0, cur_n-1), random.randint(0, cur_n-1))
		board[x][y] = 2
		i+=1
	cur_node = Node(board, str(cur_n), str(cur_liz), methodName)
	write_board_to_file(cur_node)

