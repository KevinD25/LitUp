#!/usr/bin/evn python
from samplebase import SampleBase


class LitupLogo(SampleBase):
	def __init__(self, *args, **kwargs):
		super(LitupLogo, self).__init__(*args, **kwargs)

	def setLetter(self, canvas, x, y):
		offset_canvas = canvas
		offset_canvas.SetPixel(x, y, 255, 255, 255)

	def run(self):
		c = self.matrix.CreateFrameCanvas()
		while True:
			self.setLetter(c, 1, 14)
			self.setLetter(c, 1, 15)
			self.setLetter(c, 1, 16)
			self.setLetter(c, 1, 17)
			self.setLetter(c, 2, 17)
			self.setLetter(c, 3, 17)
			self.setLetter(c, 5, 13)
			self.setLetter(c, 5, 15)
			self.setLetter(c, 5, 16)
			self.setLetter(c, 5, 17)
			self.setLetter(c, 7, 14)
			self.setLetter(c, 8, 14)
			self.setLetter(c, 9, 14)
			self.setLetter(c, 8, 15)
			self.setLetter(c, 8, 16)
			self.setLetter(c, 8, 17)
#U
			self.setLetter(c, 18, 14)
			self.setLetter(c, 18, 15)
			self.setLetter(c, 18, 16)
			self.setLetter(c, 18, 17)
			self.setLetter(c, 19, 17)
			self.setLetter(c, 20, 17)
			self.setLetter(c, 21, 14)
			self.setLetter(c, 21, 15)
			self.setLetter(c, 21, 16)
			self.setLetter(c, 21, 17)
#P
			self.setLetter(c, 23, 14)
			self.setLetter(c, 23, 15)
			self.setLetter(c, 23, 16)
			self.setLetter(c, 23, 17)
			self.setLetter(c, 24, 14)
			self.setLetter(c, 24, 16)
			self.setLetter(c, 25, 15)

			c = self.matrix.SwapOnVSync(c)

if __name__ == "__main__":
	lituplogo = LitupLogo()
	if (not lituplogo.process()):
		lituplogo.print_help()
