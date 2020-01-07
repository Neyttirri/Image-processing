package bv_ws1920;

import java.util.Arrays;

public class FilterWorkspace {
	
	private int[] src;
	private int width;
	private int height;
	
//	private static final int maxKernel = 9;
//	
//	private int newW = width + maxKernel -1;;
//	private int newH = height + maxKernel -1;;
//	public int[] copyExtended = new int[newW * newH];
	
	public int [] dst;
	
	public FilterWorkspace(int[] src, int width, int height) {
		this.src = src;
		this.width = width;
		this.height = height;
		dst = src.clone();
	}
	
	
	
	public void HashTagNaturalna(int kW, int kH, Filter filter) {
		int[] neighbours = new int[kW * kH];
		int posK;
		int posSrc;
		int value = 0;
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if(kW == 1) {
					break;
				}
//Randbehanglung
//TODO: optimize
				
				//top without diagonals
				if(y < kH/2) {
					for (int h = 0; h <kH/2 - y; h++) {
						//left corner
						if( x < kW/2) {
							for (int w = kW/2 -x; w < kW; w++) {
								posK = h * kW + w;
								posSrc = w - kW/2 + x;
								neighbours[posK] = src[posSrc];
							}
						}
						//right corner
						else if (x > width - kW/2 -1) {
							for (int w = 0; w < width - x + kW/2; w++) {
								posK = h * kW + w;
								posSrc = x - kW/2 + w;
								neighbours[posK] = src[posSrc];
							}
						}
						//no corner
						else {
							for (int w = 0; w < kW; w++) {
								posK = h * kW + w;
								posSrc = x - kW/2 + w;
								neighbours[posK] = src[posSrc];
							}
						}
						
					}
				}
				
				//bottom without diagonals
				// TODO check
				if(y > height - kH/2) {
					for (int h = 0; h <kH/2 - y; h++) {
						//left corner
						if( x < kW/2) {
							for (int w = kW/2; w < kW; w++) {
								posK = h * kW + w;
								posSrc = y*width + w - kW/2;
								neighbours[posK] = src[posSrc];
							}
						}
						//right corner
						else if (x > width - kW/2 -1) {
							for (int w = 0; w < width - x + kW/2; w++) {
								posK = h * kW + w;
								posSrc = y*width + x - kW/2 + w;
								neighbours[posK] = src[posSrc];
							}
						}
						//no corner
						else {
							for (int w = 0; w < kW; w++) {
								posK = h * kW + w;
								posSrc = y*width + x - kW/2 + w;
								neighbours[posK] = src[posSrc];
							}
						}
					}
				}
				
				// left without diagonals
				if(x < kW/2) {
					//top
					if(y < kH/2) {
						for (int h = kH/2 - y; h < kH; h++) {
							for (int w = 0; w < kW/2 - x; w++) {
								posK = h*kW + w;
								posSrc = ( y + h - kH/2) * width;
								neighbours[posK] = src[posSrc];									
							}
						}
					}
					//bottom
					else if (y > height - kH/2 -1) {
						for (int h = 0; h < height -y +kH/2 ; h++) {
							for (int w = 0; w < kW/2 - x; w++) {
								posK = h*kW + w;
								posSrc = (y - kH/2 + h) * width;
								neighbours[posK] = src[posSrc];
							}
						}
					}
					//in between
					else {
						for (int h = 0; h < kH; h++) {
							for (int w = 0; w < kW/2 - x; w++) {
								posK = h*kW + w;
								posSrc = (y - kH/2 + h) * width;
								neighbours[posK] = src[posSrc];
							}
						}
					}
					
				}
				
				// right without diagonals
				if(x > width - kW/2 - 1) {
					//top
					if(y < kH/2) {
						for (int h = kH/2 - y; h < kH; h++) {
							for (int w = width - x + 1; w < kW - width + x -1; w++) { 
								posK = h*kW + w;
								posSrc = (h - kH/2 + y) * width;
								neighbours[posK] = src[posSrc];
							}
						}
					}
					//bottom
					else if (y > height - kH/2 -1) {
						for (int h = 0; h < height -y + 1; h++) {
							for (int w = width - x + 1; w < kW - width + x -1; w++) {
								posK = h*kW + w;
								posSrc = (h - kH/2 + y) * width;
								neighbours[posK] = src[posSrc];
							}
						}
					}
					//in between
					else {
						for (int h = 0; h < kH; h++) {
							for (int w = width - x + 1; w < kW - width + x -1; w++) {
								posK = h*kW + w;
								posSrc = (y -kH/2 + h) * width;
								neighbours[posK] = src[posSrc];
							}
						}
					}
				}
				
				// diagonals
				//above
				if(y < kH/2 && x < kW/2) { 					//left
					for (int h = 0; h < kH/2 - y; h++) {
						for (int w = 0; w < kW/2 - x; w++) { 
							posK = h*kW + w;
							neighbours[posK] = src[0];
						}
					}
				}
				//right
				else if (y < kH/2 && x > width - kW/2 - 1) {
					for (int h = 0; h < kH/2 - y; h++) {
						for (int w = width - x + kW/2; w < kW; w++) {
							posK = h*kW + w;
							neighbours[posK] = src[width - 1];
						}
					}
				}
				//under
				
				//left
				else if(y > height - kH/2 -1 && x < kW/2){
					for (int h = height - y + kH/2; h < kH; h++) {
						for (int w = 0; w < kW/2 - x; w++) {
							posK = h*kW + w;
							posSrc = (height - 1) * width;
							neighbours[posK] = src[posSrc];
						}
					}
				}
				//right
				else if(y > height - kH/2 -1 && x > width - kW/2 - 1){
					for (int h = height - y + kH/2; h < kH; h++) {
						for (int w = width - x +kW/2; w < kW; w++) {
							posK = h*kW + w;
							posSrc = height * width - 1;
							neighbours[posK] = src[posSrc];
						}
					}
				}
				
				
				
				
				// body 
				if(x > kW/2 && x < width - kW/2 && y > kH/2 && y < height - kH/2) {
					for (int h = 0; h < kH; h++) {
						for (int w = 0; w < kW; w++) {
							posK = h*kW + w;
							posSrc = (y - kH/2 + h)* width + x -kW/2 + w;
							neighbours[posK] = src[posSrc];
						}
					}
				}
				
				Arrays.sort(neighbours);
				if(filter instanceof MedianFilter)
					value= neighbours[neighbours.length/ 2 + 1];
				else if(filter instanceof MaximumFilter)
					value= neighbours[kW * kH - 1];
				else if(filter instanceof MinimumFilter)
					value= neighbours[0];
				dst[y * width + x] = value;
			}
		}
	}
	
	
	
	
	
	//old code + makeExtendedCopy method
//	public void filterImage(Filter filter, int kernelW, int kernelH) {
//		int[] neighbours = new int[kernelW * kernelH];
//		int pos;
//		int posOriginal;
//		int value = 0;
//		for (int i = kernelH/2; i < newH - kernelH; i++) {
//			for (int j = kernelW/2; i < newW - kernelW/2; j++) { 			//iterating only over the original pixels
//				//pos = (maxKernel/2 + i) * newW + maxKernel/2 + j;		//position in the extended copy 
//				//getting the neighbours
//				for (int h = 0; h < kernelH; h++) {
//					for (int w = 0; w < kernelW; w++) {
//						neighbours[h * kernelW + w] = copyExtended[(i - kernelH/2 +h) * newW + j + w]; 
//					}
//				}
//				Arrays.sort(neighbours);
//				if(filter instanceof MedianFilter)
//					value= neighbours[neighbours.length/ 2 + 1];
//				else if(filter instanceof MaximumFilter)
//					value= neighbours[kernelW * kernelH - 1];
//				else if(filter instanceof MinimumFilter)
//					value= neighbours[0];
//				posOriginal = (i - kernelH/2) * (newW - maxKernel +1) + j - kernelW/2;
//				dst[posOriginal] = value;
//			}
//		}
//	}
	
	
	
//	
//	
//	public void makeExtendedCopy() {		//maximum size 
//		
//		int kernelW = maxKernel;
//		int kernelH = maxKernel;
//		
//	// filling the array parallel 
//			int pos1;
//			int pos2;
//			int posExt1;
//			int posExt2;
//			
////					top and bottom without diagonals
//			for (int h= 0; h < kernelH/2; h++) {
//				for (int w = kernelW/2; w < newW - kernelW/2 ; w++) {
//					//top
//					posExt1 = h * newW + w;
//					pos1 = w - kernelW/2;
//					copyExtended[posExt1] = src[pos1];
//					
//					//bottom
//					posExt2 = (newH - kernelH + h) * newW + w;
//					pos2 = (height - 1) * width + w - kernelW/2;
//					copyExtended[posExt2] = src[pos2];
//					}
//				}
////			left and right without diagonals		
//			for (int h= kernelH/2; h < newH - kernelH/2; h++) {
//				for (int w = 0; w < kernelW/2 ; w++) {
//					//left
//					posExt1 = h * newW + w;
//					pos1 = (h - kernelH/2) * width;
//					copyExtended[posExt1] = src[pos1];
//					
//					//right
//					posExt2 = h * newW + newW - kernelW + w;	
//					pos2 = (h - kernelH/2) * width + width - 1; 
//					copyExtended[posExt2] = src[pos2];
//					}
//				}
//			
////			diagonals above		
//			for (int h= 0; h < kernelH/2; h++) {
//				for (int w = 0; w < kernelW/2 ; w++) {
//					// diagonal left
//					posExt1 = h * newW + w;
//					pos1 = 0;
//					copyExtended[posExt1] = src[pos1];
//					
//					//diagonal right
//					posExt2 = h * newW + newW - kernelW/2 + w;
//					pos2 = width - 1;
//					copyExtended[posExt2] = src[pos2];
//					}
//				}
//			
////			diagonals under		
//			for (int h= newH - kernelH/2; h < newH; h++) {
//				for (int w = 0; w < kernelW/2 ; w++) {
//					// diagonal left
//					posExt1 = h * newW + w;
//					pos1 = 0;
//					copyExtended[posExt1] = src[pos1];
//					
//					//diagonal right
//					posExt2 = h * newW + newW - kernelW/2 + w;
//					pos2 = width - 1;
//					copyExtended[posExt2] = src[pos2];
//					}
//				}
//			
////			body
//			for (int h = kernelH/2; h < newH - kernelH; h++) {
//				for (int w = kernelW/2; w < newW - kernelW/2; w++) {
//					posExt1 = h * newW + w;
//					pos1 = (h - kernelH/2) * width + w - kernelW/2;
//					copyExtended[posExt1] = src[pos1] = dst[pos1];
//					
//				}
//			}

	}

