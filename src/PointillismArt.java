import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PointillismArt {

	BufferedImage photo;
	BufferedImage art;
	
	int w, h, bound;
	
	public static final Color[] colors = {Color.WHITE, Color.BLACK, Color.RED,
			Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.DARK_GRAY,
			Color.GRAY, Color.LIGHT_GRAY, Color.CYAN};
	
	public PointillismArt(String imageName, int boundary) {
		bound = boundary;
		
		try {
			photo = ImageIO.read(new File(imageName));
			w = photo.getWidth();
			h = photo.getHeight();
		} catch (Exception Exc) {
			System.out.println("Specified image not found");
		}
		
		/*colors[0] = Color.BLACK;
		colors[1] = Color.WHITE;
		for (int i = 2; i < colors.length; i++)
			colors[i] = new Color((int) (1 + 255 * Math.random()), (int) (1 + 255 * Math.random()), (int) (1 + 255 * Math.random()));*/
		//System.out.println(saturation(new Color(120, 178, 230).getRGB()));
		
	}

	public void imageToPoints(int diam, int lowerBound) {
		art = new BufferedImage(w + 2 * bound, h + 2 * bound, BufferedImage.TYPE_INT_RGB);
		Graphics2D gr = art.createGraphics();
		gr.setColor(Color.WHITE);
		gr.fillRect(0, 0, w + 2 * bound, h + 2 * bound);

		int x;
		int y = 0;
		int rgb;
		int r, g, b;
		int rgbDiff;
		int total;
		double rand;
		
		int[] cs = new int[colors.length];

		int areaSquare = diam * diam;
		while (y + diam < h) {
			x = 0;
			while (x + diam < w) {
				total = 0;
				r = 0;
				g = 0;
				b = 0;
				for (int i = x; i < x + diam; i++) {
					for (int j = y; j < y + diam; j++) {
						rgb = photo.getRGB(i, j);
						r += ((rgb >> 16) & 0xFF);
						g += ((rgb >> 8) & 0xFF);
						b += (rgb & 0xFF);
					}
				}
				r /= areaSquare;
				g /= areaSquare;
				b /= areaSquare;
				
				rgb = (r << 16) | (g << 8) | (b);
				
				for (int k = 0; k < colors.length; k++) {
					rgbDiff = 195 - colorDiff(rgb, colors[k].getRGB()) / 1000;
					
					if (rgbDiff < lowerBound) {
						cs[k] = 0;
						continue;
					}
					
					rgbDiff -= lowerBound * 4 / 5;
					
					cs[k] = rgbDiff * rgbDiff;
					
					/* && brightness(colors[k].getRGB()) != 180 && brightness(colors[k].getRGB()) != 0*/
					
					
					/*hue = Math.abs(hue(rgb) - hue(colors[k].getRGB()));//hue
					if (hue > 180)
						hue = 360 - hue;
					hue = 180 - hue;*/
					//cs[k] *= 2;
					
					/* else if (cs[k] < lowerBound) {
						System.out.println(brightness(colors[k].getRGB()) + ", " + saturation(colors[k].getRGB()) + ", " + k);
					}*/
						
					//bright = (180 - Math.abs(brightness(rgb) - brightness(colors[k].getRGB())));//brightness
					
					/*if (k == 0)
						bright *= 1.2;
					
					if (k == 1)
						bright /= 1.2;*/
					
					/*if ((bright > 150 && brightness(colors[k].getRGB()) < 10) || (bright < 30 && brightness(colors[k].getRGB()) > 170)) {
						//System.out.println(brightness(colors[k].getRGB()) + ", " + saturation(colors[k].getRGB()) + ", " + k);
						cs[k] = 0;
						continue;
					}*/
						
					//System.out.println(brightness(rgb) + " : " +  brightness(colors[k].getRGB()) + " : " + k);
					//cs[k] += (180 - Math.abs(saturation(rgb) - saturation(colors[k].getRGB())));//saturation
					
					//if (bright < hue)
					
					/*if (rgbDiff > lowerBound + 15)
						cs[k] = rgbDiff;
					else
						cs[k] = hue;*/
					
					//cs[k] = hue + rgbDiff;// + bright;
					
					
					total += cs[k];
				}
				/*r /= areaSquare;
				g /= areaSquare;
				b /= areaSquare;

				black = 765 - r - g - b;
				total = black + r + g + b;*/
				
				for (int i = 0; i < 7; i++) {
					rand = Math.random() * total;
					int hold = 0;
					for (int k = 0; k < cs.length; k++) {
						hold += cs[k];
						if (rand < hold) {
							gr.setColor(new Color(colors[k].getRed(), colors[k].getGreen(), colors[k].getBlue(), 100));
							break;
						}
					}
					
					
					
					gr.fillOval((int) (x + Math.random() * diam) + bound, (int) (y + Math.random() * diam) + bound, diam, diam);
	
					//gr.fillRect(x, y, diam, diam);
				}

				x += diam;
			}

			y += diam;
		}
	}
	
	public void makePhoto(String name) {
		try {
			File f = new File(name);
			ImageIO.write(art, "jpg", f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int hue(int rgb) {
		int r = ((rgb >> 16) & 0xFF);
		int g = ((rgb >> 8) & 0xFF);
		int b = ((rgb) & 0xFF);
		
		int min = Math.min(Math.min(r, g), b);
		int max = Math.max(Math.max(r, g), b);
		
		if (min == max)
			return 0;
		
		float hue;
		if (max == r)
			hue = (float) (g - b) / (max - min);
		else if (max == g)
			hue = 2 + (float) (b - r) / (max - min);
		else
			hue = 4 + (float) (r - g) / (max - min);
		
		hue = hue * 60;
		
		if (hue < 0)
			hue = hue + 360;
		
		return (int) hue;
	}
	
	public int brightness(int rgb) {
		int r = ((rgb >> 16) & 0xFF);
		int g = ((rgb >> 8) & 0xFF);
		int b = ((rgb) & 0xFF);
		
		float min = Math.min(Math.min(r, g), b);
		float max = Math.max(Math.max(r, g), b);
		
		return (int) ((max + min) * 90f / 255f);
	}
	
	public int saturation(int rgb) {
		if (brightness(rgb) == 180 || brightness(rgb) == 0)
			return 0;
		
		int r = ((rgb >> 16) & 0xFF);
		int g = ((rgb >> 8) & 0xFF);
		int b = ((rgb) & 0xFF);
		
		float min = Math.min(Math.min(r, g), b);
		float max = Math.max(Math.max(r, g), b);
		
		return (int) ((max - min) * 180f / (1 - Math.abs(brightness(rgb) / 90f - 1)) / 255f);
	}
	
	public int colorDiff(int rgb1, int rgb2) {
		int r1 = ((rgb1 >> 16) & 0xFF);
		int g1 = ((rgb1 >> 8) & 0xFF);
		int b1 = ((rgb1) & 0xFF);
		
		int r2 = ((rgb2 >> 16) & 0xFF);
		int g2 = ((rgb2 >> 8) & 0xFF);
		int b2 = ((rgb2) & 0xFF);
		
		return (r1 - r2) * (r1 - r2) + (g1 - g2) * (g1 - g2) + (b1 - b2) * (b1 - b2);
		
	}

}
