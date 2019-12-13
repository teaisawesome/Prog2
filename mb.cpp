// mandelbrot set
int inter(const std::complex<double> & c, int maxinter
{
	auto z = c;
	
	for(int i = 0; i < maxinter; i++)
	{
		if(abs(z) >= 2.0)
		{return i;}
		z = z*z + c;
	}

	return maxinter;
}

void compute_set()
{
	point bl(-2, -2);
	point tr(2, 2);

	int maxiter = 1000;
	unsigned long resolution = 1000000;

	double dx = tr.x() - bl.x();
	double dy = tr.y() - bl.y();

	int W = sqrt(static_cast<double>(resolution)*dx/dy);
	int H = sqrt(static_cast<double>(resolution)*dy/dx);

	canvas image(W, H, maxiter);

	double step_x = dx/W; // size of one pixel in x direction
	double step_y = dy/H; // size of one pixel in y direction

	double y = tr.y();
	for(int py = 0; py < H; py++)
	{
		double x = bl.x();
		for(int px = 0; px < W; px++)
		{
			image.put_pixel(
					px,
					py,
					gfx::rgb::gray(iter(std::complex<double>(x,y), maxiter))
				);
			x += step_x;
		}
		y -= step_y;
	}

}

/* ./rajzolo < ../x.ppm
 ___________________________x (tr(x), tr(y))
| ^<- dx --->				|
| |							|
| dy						|
| |							|
| ˇ							|
_____________________________


*/
