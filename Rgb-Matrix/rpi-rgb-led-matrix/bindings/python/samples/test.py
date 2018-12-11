#!/usr/bin/env python
from samplebase import SampleBase

class SimpleDots(SampleBase):
	def __init__(self, *args, **kwargs):
		super(SimpleDots, self).__init__(*args, **kwargs)

	def run(self):
		offset_canvas = self.matrix.CreateFrameCanvas()
		x = 0
		while True:
			if x > 32:
				x = 0
			else:
				x += 1
			for o in range(0, int(x)):
				offset_canvas.SetPixel(o, 30, 0, 0, 0)
			offset_canvas.SetPixel(x, 30, 200, 100, 100)
			offset_canvas = self.matrix.SwapOnVSync(offset_canvas)
			self.usleep(20*5000)

if __name__ == "__main__":
	simple_dots = SimpleDots()
	if (not simple_dots.process()):
		simple_dots.print_help()
