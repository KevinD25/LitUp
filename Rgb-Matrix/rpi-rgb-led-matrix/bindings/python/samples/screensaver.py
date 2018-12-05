#!/usr/bin/env python
import argparse
from samplebase import SampleBase

class ScreenSaver(SampleBase):
	def __init__(self, *args, **kwargs):
		self.parser = super.parser()

		self.parser.add_argument("-i", action="store")
		super(ScreenSaver, self).__init__(*args, **kwargs)
	def run(self):
	#	offset_canvas = self.matrix.CreateFrameCanvas()


if __name__=="__main__":
	screen_saver = ScreenSaver()
	if(not screen_saver.process()):
		screen_saver.print_help()
