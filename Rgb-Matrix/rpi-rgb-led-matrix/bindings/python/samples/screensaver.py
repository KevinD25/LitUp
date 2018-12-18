#!/usr/bin/env python
import argparse
from samplebase import SampleBase
class ScreenSaver(SampleBase):
	def __init__(self, *args, **kwargs):
		super(ScreenSaver, self).__init__(*args, **kwargs)
		self.parser.add_argument("-i", "--input", help="Add a file with the screensaver")

	def Pixel(self, canvas, x, y, R, G, B):
		offset_canvas = canvas
		offset_canvas.SetPixel(x, y, R, G, B)
		offset_canvas.SetPixel(x+1, y, R, G, B)
		offset_canvas.SetPixel(x, y+1, R, G, B)
		offset_canvas.SetPixel(x+1,y+1, R, G, B)

	def run(self):
		print "start"
		rawfile = open(self.args.input, "rt")
		rawscreensaver = rawfile.readline().strip('\n')
		screensaver = rawscreensaver.split(";")
		del screensaver[0]
		del screensaver[-1]
		c = self.matrix.CreateFrameCanvas()
		x = 0
		y = 0
		print screensaver
		while True:
			for index, val in enumerate(screensaver, start=1):
				colors = val.split("/")
				self.Pixel(c, x, y, int(colors[0]), int(colors[1]), int(colors[2]))
				x += 2
				if index > 1 and index%16 == 0:
					y+=2
					x = 0
			c = self.matrix.SwapOnVSync(c)

if __name__=="__main__":
	screen_saver = ScreenSaver()
	if(not screen_saver.process()):
		screen_saver.print_help()
