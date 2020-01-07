package bv_ws1920;

public class MinimumFilter implements Filter {
	int width;
	int height;
	int [] src;
	
	int kernelW = 0;
	int kernelH = 0;
	
	RasterImage destination;
	
	@Override
	public void setSourceImage(RasterImage sourceImage) {
		width = sourceImage.width;
		height = sourceImage.height;
		src = sourceImage.argb.clone(); 
	}

	@Override
	public void setDestinationImage(RasterImage destinationImage) {
		destination = destinationImage;
		destination.argb = src.clone();
	}

	@Override
	public void setKernelWidth(int kernelWidth) {
		kernelW = kernelWidth;
	}

	@Override
	public void setKernelHeight(int kernelHeight) {
		kernelH = kernelHeight;
	}

	@Override
	public void apply() {
		FilterWorkspace workspace = new FilterWorkspace(src, width, height);
		workspace.HashTagNaturalna(kernelW, kernelH, this);
		destination.argb = workspace.dst.clone();
	}

}
