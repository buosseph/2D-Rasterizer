/* CS 4810 - HW1
	Brian Uosseph
	byu9pv
*/

import java.io.*;
import java.awt.image.*;
import java.awt.image.Raster;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.util.ArrayList;

class HW1 {

	public static int width;
	public static int height;
	public static BufferedImage image;
	public static WritableRaster raster;
	public static String filename;
	public static ArrayList<Vertex> vertex_list;

	public static Vertex[] bezPoints;

	public static int convertToListIndex(int signed_int) {
		if (signed_int < 0) {
			return vertex_list.size()+signed_int;
		} else {
			return signed_int-1;
		}
	}

	public static void main(String[] args) {
		String input = args[0];

		try {
			FileInputStream fstream = new FileInputStream(input);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			vertex_list = new ArrayList<Vertex>();

			String line;

			try {
				while ((line = br.readLine()) != null) {

					line = line.trim();
					if (line.isEmpty()) {
						continue;
					}

					String[] tokens= line.split("\\s+");


					if (tokens[0].equals("png")) {
						width = Integer.parseInt(tokens[1]);
						height = Integer.parseInt(tokens[2]);
						filename = tokens[3];
						System.out.println(filename+" will be created.");

						image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
						raster = image.getRaster();
					}
					else if (tokens[0].equals("xy")){
						float x = Float.parseFloat(tokens[1]);
						float y = Float.parseFloat(tokens[2]);


						Vertex v = new Vertex(x, y);
						vertex_list.add(v);
					}
					else if (tokens[0].equals("xyrgb")){
						float x = Float.parseFloat(tokens[1]);
						float y = Float.parseFloat(tokens[2]);
						int r = Integer.parseInt(tokens[3]);
						int g = Integer.parseInt(tokens[4]);
						int b = Integer.parseInt(tokens[5]);

						Vertex v = new Vertex(x, y, r, g, b);
						vertex_list.add(v);
					}
					else if (tokens[0].equals("xyc")){
						float x = Float.parseFloat(tokens[1]);
						float y = Float.parseFloat(tokens[2]);
						String hex = tokens[3];

						Color fromHex = new Color(
							Integer.valueOf(hex.substring(1,3),16), 
							Integer.valueOf(hex.substring(3,5),16), 
							Integer.valueOf(hex.substring(5,7),16));
						int r = fromHex.getRed();
						int g = fromHex.getGreen();
						int b = fromHex.getBlue();

						Vertex v = new Vertex(x, y, r, g, b);
						vertex_list.add(v);
					}
					else if (tokens[0].equals("xyrgba")){
						float x = Float.parseFloat(tokens[1]);
						float y = Float.parseFloat(tokens[2]);
						int r = Integer.parseInt(tokens[3]);
						int g = Integer.parseInt(tokens[4]);
						int b = Integer.parseInt(tokens[5]);
						int a = Integer.parseInt(tokens[6]);

						Vertex v = new Vertex(x, y, r, g, b, a);
						vertex_list.add(v);
					}
					else if (tokens[0].equals("xyca")){
						float x = Float.parseFloat(tokens[1]);
						float y = Float.parseFloat(tokens[2]);
						String hex = tokens[3];

						int r = Integer.valueOf(hex.substring(1,3),16);
						int g = Integer.valueOf(hex.substring(3,5),16);
						int b = Integer.valueOf(hex.substring(5,7),16);
						int a = Integer.valueOf(hex.substring(7,9),16);

						Vertex v = new Vertex(x, y, r, g, b, a);
						vertex_list.add(v);
					}
					else if (tokens[0].equals("linec")){
						int index1 = convertToListIndex(Integer.parseInt(tokens[1]));
						int index2 = convertToListIndex(Integer.parseInt(tokens[2]));
						String hex = tokens[3];

						linec(vertex_list.get(index1), vertex_list.get(index2), hex);

					}
					else if (tokens[0].equals("tric")){
						int index1 = convertToListIndex(Integer.parseInt(tokens[1]));
						int index2 = convertToListIndex(Integer.parseInt(tokens[2]));
						int index3 = convertToListIndex(Integer.parseInt(tokens[3]));
						String hex = tokens[4];

						tric(vertex_list.get(index1), vertex_list.get(index2), vertex_list.get(index3), hex);

					}
					else if (tokens[0].equals("lineg")){
						int index1 = convertToListIndex(Integer.parseInt(tokens[1]));
						int index2 = convertToListIndex(Integer.parseInt(tokens[2]));

						lineg(vertex_list.get(index1), vertex_list.get(index2));
					}
					else if (tokens[0].equals("trig")){
						int index1 = convertToListIndex(Integer.parseInt(tokens[1]));
						int index2 = convertToListIndex(Integer.parseInt(tokens[2]));
						int index3 = convertToListIndex(Integer.parseInt(tokens[3]));

						trig(vertex_list.get(index1), vertex_list.get(index2), vertex_list.get(index3));
					}
					else if (tokens[0].equals("trica")){
						int index1 = convertToListIndex(Integer.parseInt(tokens[1]));
						int index2 = convertToListIndex(Integer.parseInt(tokens[2]));
						int index3 = convertToListIndex(Integer.parseInt(tokens[3]));
						String hex = tokens[4];

						trica(vertex_list.get(index1), vertex_list.get(index2), vertex_list.get(index3), hex);
					}
					else if (tokens[0].equals("aalinec")){
						int index1 = convertToListIndex(Integer.parseInt(tokens[1]));
						int index2 = convertToListIndex(Integer.parseInt(tokens[2]));
						String hex = tokens[3];

						aalinec(vertex_list.get(index1), vertex_list.get(index2), hex);
					}

					else if (tokens[0].equals("circle")){
						int index1 = convertToListIndex(Integer.parseInt(tokens[1]));
						int radius = Integer.parseInt(tokens[2]);

						circle(vertex_list.get(index1), radius);

					}
					else if (tokens[0].equals("cubicc")){
						int index1 = convertToListIndex(Integer.parseInt(tokens[1]));
						int index2 = convertToListIndex(Integer.parseInt(tokens[2]));
						int index3 = convertToListIndex(Integer.parseInt(tokens[3]));
						int index4 = convertToListIndex(Integer.parseInt(tokens[4]));
						String hex = tokens[5];

						cubicc(vertex_list.get(index1), vertex_list.get(index2), vertex_list.get(index3), vertex_list.get(index4), hex);
					}
					else if (tokens[0].equals("cubicg")){
						int index1 = convertToListIndex(Integer.parseInt(tokens[1]));
						int index2 = convertToListIndex(Integer.parseInt(tokens[2]));
						int index3 = convertToListIndex(Integer.parseInt(tokens[3]));
						int index4 = convertToListIndex(Integer.parseInt(tokens[4]));

						cubicg(vertex_list.get(index1), vertex_list.get(index2), vertex_list.get(index3), vertex_list.get(index4));
					}
					else if (tokens[0].equals("beznc")){
						int n = Integer.parseInt(tokens[1]);
						bezPoints = new Vertex[n];
						for(int i=0; i < n; i++){
							int index = convertToListIndex(Integer.parseInt(tokens[i+2]));
							bezPoints[i] = vertex_list.get(index);
						}
						String hex = tokens[n+2];

						beznc(n, bezPoints, hex);
					}			
					else if (tokens[0].equals("aadot")){
						int index = convertToListIndex(Integer.parseInt(tokens[1]));

						aadot(vertex_list.get(index));
					}
					else {
						continue;
					}


				}
				ImageIO.write(image, "png", new File(filename));
				System.exit(0);
			}
			catch (Exception e){
				System.err.println("Error: "+e);
			}

		} 
		catch (FileNotFoundException e ) {
			System.err.println("FileNotFoundException: "+ e.getMessage());
		}
	}

	// Complete, based on http://en.wikipedia.org/wiki/Bresenham%27s_line_algorithm
	public static void linec( Vertex i1, Vertex i2, String hex){
		// Bresenham algorithm
		Color fromHex = new Color(
			Integer.valueOf(hex.substring(1,3),16), 
			Integer.valueOf(hex.substring(3,5),16), 
			Integer.valueOf(hex.substring(5,7),16));
		int[] color = {
			fromHex.getRed(), 
			fromHex.getGreen(), 
			fromHex.getBlue(), 
			255	};

		int x1 = (int)i1.getX();
		int y1 = (int)i1.getY();
		int x2 = (int)i2.getX();
		int y2 = (int)i2.getY();
		int dx = Math.abs(x2 - x1);
		int dy = Math.abs(y2 - y1);
		int sx, sy;

		if (x1 < x2)
			sx = 1;
		else 
			sx = -1;

		if (y1 < y2)
			sy = 1;
		else
			sy = -1;

		int err = dx - dy;

		while (true) {
			raster.setPixel(x1, y1, color);
			if (x1 == x2 && y1 == y2) { break; }
			int e2 = 2*err;
			if (e2 > -dy) { err -= dy; x1 += sx; }
			if (x1 == x2 && y1 == y2) {
				raster.setPixel(x1,y1,color);
				break;
			}
			if (e2 < dx) { err += dx; y1 += sy; }

		}
	}

	// Complete
	public static void tric(Vertex i1, Vertex i2, Vertex i3, String hex) {
		Color fromHex = new Color(
			Integer.valueOf(hex.substring(1,3),16), 
			Integer.valueOf(hex.substring(3,5),16), 
			Integer.valueOf(hex.substring(5,7),16));
		int r = fromHex.getRed();
		int g = fromHex.getGreen();
		int b = fromHex.getBlue();
		int[] color = {r, g, b, 255};

		Vertex[] vertices = new Vertex[3];
		vertices[0] = i1;
		vertices[1] = i2;
		vertices[2] = i3;

		int[] order = new int[3];

		// Find lowest y
		if (i1.getY() < i2.getY()){
			if (i1.getY() < i3.getY()){
				order[0] = 0;
			} else {
				order[0] = 2;
			}
		} else {
			if (i2.getY() < i3.getY()){
				order[0] = 1;
			} else {
				order[0] = 2;
			}
		}
		// Find highest y
		if (i1.getY() > i2.getY()){
			if (i1.getY() > i3.getY()){
				order[2] = 0;
			} else {
				order[2] = 2;
			}
		} else {
			if (i2.getY() > i3.getY()){
				order[2] = 1;
			} else {
				order[2] = 2;
			}
		}

		order[1] = 3 - (order[0] + order[2]);

		if (vertices[order[0]].getY() - vertices[order[2]].getY() != 0.0f){
			if (vertices[order[1]].getY() - vertices[order[2]].getY() == 0.0f){
				
				// Triangle has flat bottom. Only need top loop, and don't change d0

				float slope1 = (vertices[order[1]].getX() - vertices[order[0]].getX()) / (vertices[order[1]].getY() - vertices[order[0]].getY());
				float slope2 = (vertices[order[2]].getX() - vertices[order[0]].getX()) / (vertices[order[2]].getY() - vertices[order[0]].getY());

				float x1 = vertices[order[0]].getX();
				float x2 = vertices[order[0]].getX();

				for (int y = (int) vertices[order[0]].getY(); y <= (int) vertices[order[1]].getY(); y++){
					Vertex v1 = new Vertex((float)Math.floor(x1+0.5f), (float)y);
					Vertex v2 = new Vertex((float)Math.floor(x2+0.5f), (float)y);
					linec(v1, v2, hex);
					x1 += slope1;
					x2 += slope2;
				}
			} else if(vertices[order[0]].getY() - vertices[order[1]].getY() == 0.0f){
				
				// Triangle has flat top. Only need bottom loop.

				float slope1 = (vertices[order[2]].getX() - vertices[order[0]].getX()) / (vertices[order[2]].getY() - vertices[order[0]].getY());
				float slope2 = (vertices[order[2]].getX() - vertices[order[1]].getX()) / (vertices[order[2]].getY() - vertices[order[1]].getY());

				float x1 = vertices[order[2]].getX();
				float x2 = vertices[order[2]].getX();

				for (int y = (int) vertices[order[2]].getY(); y >= (int) vertices[order[0]].getY(); y--){
					x1 -= slope1;
					x2 -= slope2;

					Vertex v1 = new Vertex((float)Math.floor(x1+0.5f), (float)y);
					Vertex v2 = new Vertex((float)Math.floor(x2+0.5f), (float)y);
					linec(v1, v2, hex);
				}
			} else {

				// Arbitrary triangle
				/*
				float leftx = vertices[order[0]].getX();
				float rightx = vertices[order[0]].getX();

				float dxldy;
				float dxrdy;

				float dxdy1 = (vertices[order[1]].getX() - vertices[order[0]].getX()) / (vertices[order[1]].getY() - vertices[order[0]].getY());
				float dxdy2 = (vertices[order[2]].getX() - vertices[order[0]].getX()) / (vertices[order[2]].getY() - vertices[order[0]].getY());

				if (dxdy1 < dxdy2){
					dxldy = dxdy1;
					dxrdy = dxdy2;
				} else {
					dxldy = dxdy2;
					dxrdy = dxdy1;
				}
				// Fill top of triangle
				for (int y = (int) vertices[order[0]].getY(); y < (int) vertices[order[1]].getY(); y++){
					for(int x = (int)(Math.floor(leftx+0.5f)-0.5f); x < (int)(Math.floor(rightx+0.5f)-0.5f); x++){
						raster.setPixel(x, y, color);
					}
					leftx += dxldy;
					rightx += dxrdy;
				}

				if(dxdy1 < dxdy2){
					dxldy = (vertices[order[1]].getX() - vertices[order[2]].getX()) / (vertices[order[1]].getY() - vertices[order[2]].getY());
				}
				else {
					dxrdy = (vertices[order[1]].getX() - vertices[order[2]].getX()) / (vertices[order[1]].getY() - vertices[order[2]].getY());
				}
				// Fill bottom half
				for(int y = (int) vertices[order[1]].getY(); y < vertices[order[2]].getY(); y++) {
					for(int x = (int)(Math.floor(leftx+0.5f)-0.5f); x < (int)(Math.floor(rightx+0.5f)-0.5f); x++){
						raster.setPixel(x, y, color);
					}
					leftx += dxldy;
					rightx += dxrdy;
				}
				*/


				Vertex mid =new Vertex(
					vertices[order[0]].getX() + ( vertices[order[1]].getY() - vertices[order[0]].getY() ) / ( vertices[order[2]].getY() - vertices[order[0]].getY() ) * ( vertices[order[2]].getX() - vertices[order[0]].getX() ),
					vertices[order[1]].getY());

				// Fill top half
				float slope1 = (vertices[order[1]].getX() - vertices[order[0]].getX()) / (vertices[order[1]].getY() - vertices[order[0]].getY());
				float slope2 = (vertices[order[2]].getX() - vertices[order[0]].getX()) / (vertices[order[2]].getY() - vertices[order[0]].getY());

				float x1 = vertices[order[0]].getX();
				float x2 = vertices[order[0]].getX();

				for (int y = (int) vertices[order[0]].getY(); y <= (int) vertices[order[1]].getY(); y++){
					Vertex v1 = new Vertex((float)Math.floor(x1+0.5f)-0.5f, (float)y);
					Vertex v2 = new Vertex((float)Math.floor(x2+0.5f)-0.5f, (float)y);
					linec(v1, v2, hex);
					x1 += slope1;
					x2 += slope2;
				}

				// Fill bottom half
				slope1 = (vertices[order[2]].getX() - vertices[order[0]].getX()) / (vertices[order[2]].getY() - vertices[order[0]].getY());
				slope2 = (vertices[order[2]].getX() - vertices[order[1]].getX()) / (vertices[order[2]].getY() - vertices[order[1]].getY());

				x1 = vertices[order[2]].getX();
				x2 = vertices[order[2]].getX();

				for (int y = (int) vertices[order[2]].getY(); y > (int) vertices[order[1]].getY(); y--){
					x1 -= slope1;
					x2 -= slope2;
					Vertex v1 = new Vertex(x1, (float)y);
					Vertex v2 = new Vertex(x2, (float)y);
					linec(v1, v2, hex);
				}
			}
		} else {
			System.out.println("Trianlge has no height. Not drawing it.");
		}
	}

	// Completed with minor differences, based on https://en.wikipedia.org/wiki/Midpoint_circle_algorithm and http://www.asksatyam.com/2011/01/midpoint-circle-algorithm.html
	// +15%
	public static void circle(Vertex i1, int radius){
		int xC = (int)Math.floor(i1.getX()+0.5f);
		int yC = (int)Math.floor(i1.getY()+0.5f);
		int x = radius;
		int y = 0;
		int radiusError = 1-x;
		int[] color = {i1.getR(), i1.getG(), i1.getB(), 255};

		while ( x >= y ){
			raster.setPixel(xC+x, yC+y, color);
			raster.setPixel(xC+x, yC-y, color);
			raster.setPixel(xC-x, yC+y, color);
			raster.setPixel(xC-x, yC-y, color);
			raster.setPixel(xC+y, yC+x, color);
			raster.setPixel(xC+y, yC-x, color);
			raster.setPixel(xC-y, yC+x, color);
			raster.setPixel(xC-y, yC-x, color);
			y++;
			if (radiusError < 0){
				radiusError += 2*y+1;
			} else {
				x--;
				radiusError +=2*(y-x+1);
			}
		}
	}

	// For cubicc, based on http://en.wikipedia.org/wiki/Linear_interpolation
	public static Vertex lerp(Vertex a, Vertex b, float t){
		float x = a.getX() + (b.getX() - a.getX())*t;
		float y = a.getY() + (b.getY() - a.getY())*t;
		Vertex v = new Vertex(x,y);
		return v;
	}

	// Completed with minor differences, based on http://www.cubic.org/docs/bezier.htm
	// +15%
	public static void cubicc(Vertex i1, Vertex i2, Vertex i3, Vertex i4, String hex){
		Color fromHex = new Color(
			Integer.valueOf(hex.substring(1,3),16), 
			Integer.valueOf(hex.substring(3,5),16), 
			Integer.valueOf(hex.substring(5,7),16));
		int[] color = {
			fromHex.getRed(), 
			fromHex.getGreen(), 
			fromHex.getBlue(), 
			255	};

		for (int i=0; i<1000; i++){
			float t = (float)i/999.0f;

			Vertex v1 = lerp(i1, i2, t);
			Vertex v2 = lerp(i2, i3, t);
			Vertex v3 = lerp(i3, i4, t);
			Vertex v4 = lerp(v1, v2, t);
			Vertex v5 = lerp(v2, v3, t);
			Vertex v6 = lerp(v4, v5, t);

			raster.setPixel((int)v6.getX(), (int)v6.getY(), color);
		}
	}

	// For lineg, based on http://en.wikipedia.org/wiki/Linear_interpolation
 	public static int[] lerpColor(int[] color1, int[] color2, float t) {
		//the parameter 't' is the 'percentage value'
		// t is from 0 to 1 (0 = color1, 1 = color2)
 		int r = (int) (color1[0] + (color2[0] - color1[0]) * t);
		int g = (int) (color1[1] + (color2[1] - color1[1]) * t);
		int b = (int) (color1[2] + (color2[2] - color1[2]) * t);
		int a = (int) (color1[3] + (color2[3] - color1[3]) * t);
		int[] color = {r, g, b, a};
		//System.out.println("Rf: "+((color2[0] - color1[0]) * t)+", Gf: "+((color2[1] - color1[1]) * t)+", Bf: "+( (color2[2] - color1[2]) * t));
		//System.out.println("R: "+r+", G: "+g+", B: "+b);
		return color;
	}

	// For cubicg, based on http://en.wikipedia.org/wiki/Linear_interpolation
	public static Vertex lerpAll(Vertex a, Vertex b, float t){
		float x = a.getX() + (b.getX() - a.getX())*t;
		float y = a.getY() + (b.getY() - a.getY())*t;

		int red = (int) (a.getR() + (b.getR() - a.getR()) * t);
		int green = (int) (a.getG() + (b.getG() - a.getG()) * t);
		int blue = (int) (a.getB() + (b.getB() - a.getB()) * t);
		int[] color = {red, green, blue, 255};

		Vertex v = new Vertex(x,y,red,green,blue);
		return v;
	}

	// Completed, based on http://en.wikipedia.org/wiki/Bresenham%27s_line_algorithm and http://en.wikipedia.org/wiki/Linear_interpolation
	// +5%
	public static void lineg(Vertex i1, Vertex i2) {
		int[] color1 = {i1.getR(), i1.getG(), i1.getB(), i1.getA()};
		int[] color2 = {i2.getR(), i2.getG(), i2.getB(), i2.getA()};

		int x1 = (int)i1.getX();
		int y1 = (int)i1.getY();
		int x2 = (int)i2.getX();
		int y2 = (int)i2.getY();

		int dx = Math.abs(x2 - x1);
		int dy = Math.abs(y2 - y1);
		int sx, sy;

		if (x1 < x2)
			sx = 1;
		else 
			sx = -1;
		if (y1 < y2)
			sy = 1;
		else
			sy = -1;
		int err = dx - dy;

		int xInit = x1;
		float n = 0.0f;

		while (true) {
			float t;
			if (dy > dx) {
				t = n/dy;
			} else {
				t = n/dx;
			}
			raster.setPixel(x1, y1, lerpColor(color1, color2, t));
			if (x1 == x2 && y1 == y2)
				break;
			int e2 = 2*err;
			if (e2 > -dy) {
				err -= dy;
				x1 += sx;
			}
			if (x1 == x2 && y1 == y2) {
				// Last pixel
				raster.setPixel(x1,y1,lerpColor(color1, color2, t));
				break;
			}
			if (e2 < dx) {
				err += dx;
				y1 += sy;
			}
			n += 1.0f;
		}
	}

	// Completed with minor differences
	// +20%
	public static void trig(Vertex i1, Vertex i2, Vertex i3) {

		Vertex[] vertices = new Vertex[3];
		vertices[0] = i1;
		vertices[1] = i2;
		vertices[2] = i3;

		int[] order = new int[3];

		// Find lowest y
		if (i1.getY() < i2.getY()){
			if (i1.getY() < i3.getY()){
				order[0] = 0;
			} else {
				order[0] = 2;
			}
		} else {
			if (i2.getY() < i3.getY()){
				order[0] = 1;
			} else {
				order[0] = 2;
			}
		}
		// Find highest y
		if (i1.getY() > i2.getY()){
			if (i1.getY() > i3.getY()){
				order[2] = 0;
			} else {
				order[2] = 2;
			}
		} else {
			if (i2.getY() > i3.getY()){
				order[2] = 1;
			} else {
				order[2] = 2;
			}
		}

		order[1] = 3 - (order[0] + order[2]);
		// 0 top
		// 1 middle
		// 2 bottom

		if (vertices[order[0]].getY() - vertices[order[2]].getY() != 0.0f){
		
			if (vertices[order[1]].getY() - vertices[order[2]].getY() == 0.0f){
				// Triangle has flat bottom

				float leftx = vertices[order[0]].getX();
				float rightx = vertices[order[0]].getX();

				float dxldy;
				float dxrdy;

				float dxdy1 = (vertices[order[1]].getX() - vertices[order[0]].getX()) / (vertices[order[1]].getY() - vertices[order[0]].getY());
				float dxdy2 = (vertices[order[2]].getX() - vertices[order[0]].getX()) / (vertices[order[2]].getY() - vertices[order[0]].getY());

				float dr1 = (float)(vertices[order[1]].getR() - vertices[order[0]].getR())/(vertices[order[1]].getY() - vertices[order[0]].getY());
				float dg1 = (float)(vertices[order[1]].getG() - vertices[order[0]].getG())/(vertices[order[1]].getY() - vertices[order[0]].getY());
				float db1 = (float)(vertices[order[1]].getB() - vertices[order[0]].getB())/(vertices[order[1]].getY() - vertices[order[0]].getY());

				float dr2 = (float)(vertices[order[2]].getR() - vertices[order[0]].getR())/(vertices[order[2]].getY() - vertices[order[0]].getY());
				float dg2 = (float)(vertices[order[2]].getG() - vertices[order[0]].getG())/(vertices[order[2]].getY() - vertices[order[0]].getY());
				float db2 = (float)(vertices[order[2]].getB() - vertices[order[0]].getB())/(vertices[order[2]].getY() - vertices[order[0]].getY());

				float drldy, dgldy, dbldy, drrdy, dgrdy, dbrdy;

				if (dxdy1 < dxdy2){
					dxldy = dxdy1;
					dxrdy = dxdy2;

					drldy = dr1; dgldy = dg1; dbldy = db1;
					drrdy = dr2; dgrdy = dg2; dbrdy = db2;
				} else {
					dxldy = dxdy2;
					dxrdy = dxdy1;

					drldy = dr2; dgldy = dg2; dbldy = db2;
					drrdy = dr1; dgrdy = dg1; dbrdy = db1;
				}

				float r_left = vertices[order[0]].getR();
				float r_right = vertices[order[0]].getR();
				float g_left = vertices[order[0]].getG();
				float g_right = vertices[order[0]].getG();
				float b_left = vertices[order[0]].getB();
				float b_right = vertices[order[0]].getB();

				// Fill top of triangle
				for (int y = (int) vertices[order[0]].getY(); y < (int) vertices[order[1]].getY(); y++){
					float dr = (r_right - r_left)/(rightx - leftx);
					float dg = (g_right - g_left)/(rightx - leftx);
					float db = (b_right - b_left)/(rightx - leftx);

					float pr = r_left;
					float pg = g_left;
					float pb = b_left;

					for(int x = (int)leftx; x < (int)rightx; x++){
						pr += dr;
						pg += dg;
						pb += db;
						int[] color = {(int)pr, (int)pg, (int)pb, 255};
						raster.setPixel(x, y, color);
					}

					leftx += dxldy;
					rightx += dxrdy;

					r_left += drldy;
					r_right += drrdy;
					g_left += dgldy;
					g_right += dgrdy;
					b_left += dbldy;
					b_right += dbrdy;
				}
			} else if(vertices[order[0]].getY() - vertices[order[1]].getY() == 0.0f){

				// Not working...

				// Triangle has flat top. Only need top loop.
				float leftx = vertices[order[2]].getX();
				float rightx = vertices[order[2]].getX();

				float dxldy;
				float dxrdy;

				// if (vertices[order[1]].getX() < vertices[order[0]].getX()){
				// 	leftx = vertices[order[1]].getX();
				// 	rightx = vertices[order[0]].getX();


				// } else {
				// 	leftx = vertices[order[0]].getX();
				// 	rightx = vertices[order[1]].getX();
				// }

				

				float dxdy1 = (vertices[order[2]].getX() - vertices[order[0]].getX()) / (vertices[order[2]].getY() - vertices[order[0]].getY());
				float dxdy2 = (vertices[order[2]].getX() - vertices[order[1]].getX()) / (vertices[order[2]].getY() - vertices[order[1]].getY());

				float dr1 = (float)(vertices[order[2]].getR() - vertices[order[0]].getR())/(vertices[order[2]].getY() - vertices[order[0]].getY());
				float dg1 = (float)(vertices[order[2]].getG() - vertices[order[0]].getG())/(vertices[order[2]].getY() - vertices[order[0]].getY());
				float db1 = (float)(vertices[order[2]].getB() - vertices[order[0]].getB())/(vertices[order[2]].getY() - vertices[order[0]].getY());


				float dr2 = (float)(vertices[order[2]].getR() - vertices[order[1]].getR())/(vertices[order[2]].getY() - vertices[order[1]].getY());
				float dg2 = (float)(vertices[order[2]].getG() - vertices[order[1]].getG())/(vertices[order[2]].getY() - vertices[order[1]].getY());
				float db2 = (float)(vertices[order[2]].getB() - vertices[order[1]].getB())/(vertices[order[2]].getY() - vertices[order[1]].getY());


				float drldy, dgldy, dbldy, drrdy, dgrdy, dbrdy; 

				if (dxdy1 < dxdy2){
					dxldy = dxdy1;
					dxrdy = dxdy2;

					drldy = dr1; dgldy = dg1; dbldy = db1; 
					drrdy = dr2; dgrdy = dg2; dbrdy = db2;
				} else {
					dxldy = dxdy2;
					dxrdy = dxdy1;

					drldy = dr2; dgldy = dg2; dbldy = db2; 
					drrdy = dr1; dgrdy = dg1; dbrdy = db1; 
				}



				float r_left;
				float r_right;
				float g_left;
				float g_right;
				float b_left;
				float b_right;

				if (vertices[order[1]].getX() < vertices[order[0]].getX()){
					r_left = vertices[order[1]].getR();
					r_right = vertices[order[0]].getR();
					g_left = vertices[order[1]].getG();
					g_right = vertices[order[0]].getG();
					b_left = vertices[order[1]].getB();
					b_right = vertices[order[0]].getB();


				} else {
					r_left = vertices[order[0]].getR();
					r_right = vertices[order[1]].getR();
					g_left = vertices[order[0]].getG();
					g_right = vertices[order[1]].getG();
					b_left = vertices[order[0]].getB();
					b_right = vertices[order[1]].getB();


				}


				// Fill bottom half
				if(dxdy1 < dxdy2){
					dxldy = (vertices[order[2]].getX() - vertices[order[0]].getX()) / (vertices[order[2]].getY() - vertices[order[0]].getY());

					drldy = (vertices[order[2]].getR() - vertices[order[0]].getR()) / (vertices[order[2]].getY() - vertices[order[0]].getY());
					dgldy = (vertices[order[2]].getG() - vertices[order[0]].getG()) / (vertices[order[2]].getY() - vertices[order[0]].getY());
					dbldy = (vertices[order[2]].getB() - vertices[order[0]].getB()) / (vertices[order[2]].getY() - vertices[order[0]].getY());
				}
				else {
					dxrdy = (vertices[order[1]].getX() - vertices[order[0]].getX()) / (vertices[order[1]].getY() - vertices[order[0]].getY());

					drrdy = (vertices[order[1]].getR() - vertices[order[0]].getR()) / (vertices[order[1]].getY() - vertices[order[0]].getY());
					dgrdy = (vertices[order[1]].getG() - vertices[order[0]].getG()) / (vertices[order[1]].getY() - vertices[order[0]].getY());
					dbrdy = (vertices[order[1]].getB() - vertices[order[0]].getB()) / (vertices[order[1]].getY() - vertices[order[0]].getY());
				}

				for(int y = (int) vertices[order[2]].getY(); y > vertices[order[0]].getY(); y--) {
					float dr = (r_right - r_left)/(rightx - leftx);
					float dg = (g_right - g_left)/(rightx - leftx);
					float db = (b_right - b_left)/(rightx - leftx);

					float pr = r_left;
					float pg = g_left;
					float pb = b_left;

					for(int x = (int)leftx; x < (int)rightx; x++){
						pr += dr;
						pg += dg;
						pb += db;

						int[] color = {255, 255, 255, 255};
						raster.setPixel(x, y, color);
					}

					leftx -= dxldy;
					rightx -= dxrdy;

					r_left -= drldy;
					r_right -= drrdy;
					g_left -= dgldy;
					g_right -= dgrdy;
					b_left -= dbldy;
					b_right -= dbrdy;
				}	
			} else {
				// Arbitrary triangle
				// Fill top half
				float leftx = vertices[order[0]].getX();
				float rightx = vertices[order[0]].getX();

				float dxldy;
				float dxrdy;

				float dxdy1 = (vertices[order[1]].getX() - vertices[order[0]].getX()) / (vertices[order[1]].getY() - vertices[order[0]].getY());
				float dxdy2 = (vertices[order[2]].getX() - vertices[order[0]].getX()) / (vertices[order[2]].getY() - vertices[order[0]].getY());

				float dr1 = (float)(vertices[order[1]].getR() - vertices[order[0]].getR())/(vertices[order[1]].getY() - vertices[order[0]].getY());
				float dg1 = (float)(vertices[order[1]].getG() - vertices[order[0]].getG())/(vertices[order[1]].getY() - vertices[order[0]].getY());
				float db1 = (float)(vertices[order[1]].getB() - vertices[order[0]].getB())/(vertices[order[1]].getY() - vertices[order[0]].getY());
				float da1 = (float)(vertices[order[1]].getA() - vertices[order[0]].getA())/(vertices[order[1]].getY() - vertices[order[0]].getY());

				float dr2 = (float)(vertices[order[2]].getR() - vertices[order[0]].getR())/(vertices[order[2]].getY() - vertices[order[0]].getY());
				float dg2 = (float)(vertices[order[2]].getG() - vertices[order[0]].getG())/(vertices[order[2]].getY() - vertices[order[0]].getY());
				float db2 = (float)(vertices[order[2]].getB() - vertices[order[0]].getB())/(vertices[order[2]].getY() - vertices[order[0]].getY());
				float da2 = (float)(vertices[order[2]].getA() - vertices[order[0]].getA())/(vertices[order[2]].getY() - vertices[order[0]].getY());
				float drldy, dgldy, dbldy, drrdy, dgrdy, dbrdy, daldy, dardy;

				if (dxdy1 < dxdy2){
					dxldy = dxdy1;
					dxrdy = dxdy2;

					drldy = dr1; dgldy = dg1; dbldy = db1; daldy = da1;
					drrdy = dr2; dgrdy = dg2; dbrdy = db2; dardy = da2;

				} else {
					dxldy = dxdy2;
					dxrdy = dxdy1;

					drldy = dr2; dgldy = dg2; dbldy = db2; daldy = da2;
					drrdy = dr1; dgrdy = dg1; dbrdy = db1; dardy = da1;
				}

				float r_left = vertices[order[0]].getR();
				float r_right = vertices[order[0]].getR();
				float g_left = vertices[order[0]].getG();
				float g_right = vertices[order[0]].getG();
				float b_left = vertices[order[0]].getB();
				float b_right = vertices[order[0]].getB();
				float a_left = vertices[order[0]].getA();
				float a_right = vertices[order[0]].getA();

				// Fill top of triangle
				for (int y = (int) vertices[order[0]].getY(); y < (int) vertices[order[1]].getY(); y++){
					float dr = (r_right - r_left)/(rightx - leftx);
					float dg = (g_right - g_left)/(rightx - leftx);
					float db = (b_right - b_left)/(rightx - leftx);
					float da = (a_right - a_left)/(rightx - leftx);

					float pr = r_left;
					float pg = g_left;
					float pb = b_left;
					float pa = a_left;

					for(int x = (int)leftx; x < (int)rightx; x++){
						pr += dr;
						pg += dg;
						pb += db;
						pa += da;
						int[] color = {(int)pr, (int)pg, (int)pb, (int)pa};
						raster.setPixel(x, y, color);
					}

					leftx += dxldy;
					rightx += dxrdy;

					r_left += drldy;
					r_right += drrdy;
					g_left += dgldy;
					g_right += dgrdy;
					b_left += dbldy;
					b_right += dbrdy;
					a_left += daldy;
					a_right += dardy;
				}

				// Fill bottom half
				if(dxdy1 < dxdy2){
					dxldy = (vertices[order[1]].getX() - vertices[order[2]].getX()) / (vertices[order[1]].getY() - vertices[order[2]].getY());

					drldy = (vertices[order[1]].getR() - vertices[order[2]].getR()) / (vertices[order[1]].getY() - vertices[order[2]].getY());
					dgldy = (vertices[order[1]].getG() - vertices[order[2]].getG()) / (vertices[order[1]].getY() - vertices[order[2]].getY());
					dbldy = (vertices[order[1]].getB() - vertices[order[2]].getB()) / (vertices[order[1]].getY() - vertices[order[2]].getY());
					daldy = (vertices[order[1]].getA() - vertices[order[2]].getA()) / (vertices[order[1]].getY() - vertices[order[2]].getY());
				}
				else {
					dxrdy = (vertices[order[1]].getX() - vertices[order[2]].getX()) / (vertices[order[1]].getY() - vertices[order[2]].getY());

					drrdy = (vertices[order[1]].getR() - vertices[order[2]].getR()) / (vertices[order[1]].getY() - vertices[order[2]].getY());
					dgrdy = (vertices[order[1]].getG() - vertices[order[2]].getG()) / (vertices[order[1]].getY() - vertices[order[2]].getY());
					dbrdy = (vertices[order[1]].getB() - vertices[order[2]].getB()) / (vertices[order[1]].getY() - vertices[order[2]].getY());
					dardy = (vertices[order[1]].getA() - vertices[order[2]].getA()) / (vertices[order[1]].getY() - vertices[order[2]].getY());
				}

				for(int y = (int) Math.floor(vertices[order[1]].getY()+0.5f); y < vertices[order[2]].getY(); y++) {
					float dr = (r_right - r_left)/(rightx - leftx);
					float dg = (g_right - g_left)/(rightx - leftx);
					float db = (b_right - b_left)/(rightx - leftx);
					float da = (a_right - a_left)/(rightx - leftx);

					float pr = r_left;
					float pg = g_left;
					float pb = b_left;
					float pa = a_left;
					for(int x = (int)Math.floor(leftx+1.0f); x < (int)(Math.floor(rightx+0.5f)); x++){
						pr += dr;
						pg += dg;
						pb += db;
						pa += da;
						int[] color = {(int)pr, (int)pg, (int)pb, (int)pa};
						raster.setPixel(x, y, color);
					}

					leftx += dxldy;
					rightx += dxrdy;

					r_left += drldy;
					r_right += drrdy;
					g_left += dgldy;
					g_right += dgrdy;
					b_left += dbldy;
					b_right += dbrdy;
					a_left += daldy;
					a_right += dardy;
				}				

			}
		} else {
			System.out.println("Trianlge has no height. Not drawing it.");
		}
	}

	// Completed but with minor differences, based on http://www.cubic.org/docs/bezier.htm and http://en.wikipedia.org/wiki/Linear_interpolation
	// +10%
	public static void cubicg(Vertex i1, Vertex i2, Vertex i3, Vertex i4){

		for (int i=0; i<1000; i++){
			float t = (float)i/999.0f;
			Vertex v1 = lerpAll(i1, i2, t);
			Vertex v2 = lerpAll(i2, i3, t);
			Vertex v3 = lerpAll(i3, i4, t);
			Vertex v4 = lerpAll(v1, v2, t);
			Vertex v5 = lerpAll(v2, v3, t);
			Vertex v6 = lerpAll(v4, v5, t);

			int[] color = {v6.getR(), v6.getG(), v6.getB(), 255};
			raster.setPixel((int)v6.getX(), (int)v6.getY(), color);
		}
	}

	// For aalinec
	public static int ipart(float x){
		return (int)Math.floor(x);
	}

	// For aalinec	
	public static int round(float x){
		return ipart(x+0.5f);
	}

	// For aalinec
	public static float fpart(float x){
		return (float)Math.round((x-Math.floor(x))*100)/(float)100;
	}

	// For aalinec
	public static float rfpart(float x){
		return 1 - fpart(x);
	}

	// Complete, algorithm based on https://en.wikipedia.org/wiki/Xiaolin_Wu%27s_line_algorithm
	// +15%
	public static void aalinec(Vertex i1, Vertex i2, String hex) {
		Color fromHex = new Color(
			Integer.valueOf(hex.substring(1,3),16), 
			Integer.valueOf(hex.substring(3,5),16), 
			Integer.valueOf(hex.substring(5,7),16));
		int r = fromHex.getRed();
		int g = fromHex.getGreen();
		int b = fromHex.getBlue();

		int x0 = (int) i1.getX();
		int y0 = (int) i1.getY();
		int x1 = (int) i2.getX();
		int y1 = (int) i2.getY();

		boolean steep = Math.abs(y1 - y0) > Math.abs(x1 - x0);
		int temp;
		if (steep) {
			//swap (x0, y0)
			temp = x0;
			x0 = y0;
			y0 = temp;

			//swap (x1, y1)
			temp = x1;
			x1 = y1;
			y1 = temp;
		}

		if (x0 > x1) {
			//swap (x0, x1)
			temp = x0;
			x0 = x1;
			x1 = temp;

			//swap (y0, y1)
			temp = y0;
			y0 = y1;
			y1 = temp;
		}

		int dx = x1 - x0;
		int dy = y1 - y0;
		float gradient = ((float) dy)/((float)dx);

		// First Endpoint
		int xend = round(x0);
		float yend = y0 + gradient * (xend - x0);
		float xgap = rfpart(x0 + 0.5f);
		int xpxl1 = xend;
		float ypxl1 = ipart(yend);
		if (steep){
			int[] color1 = {r, g, b, (int)(rfpart(yend)*xgap)};
			int[] color2 = {r, g, b, (int)(fpart(yend)*xgap)};

			raster.setPixel((int)ypxl1, xpxl1, color1);
			raster.setPixel((int)(ypxl1+1), xpxl1, color2);
		} else {
			int[] color1 = {r, g, b, (int)(rfpart(yend)*xgap)};
			int[] color2 = {r, g, b, (int)(fpart(yend)*xgap)};

			raster.setPixel(xpxl1, (int)ypxl1, color1);
			raster.setPixel(xpxl1, (int)(ypxl1+1), color2);
		}
		float intery = yend + gradient;

		// Second Endpoint
		xend = round(x1);
		yend = y1 + gradient * (xend - x1);
		xgap = fpart(x1+0.5f);
		int xpxl2 = xend;
		float ypxl2 = ipart(yend);
		if (steep){
			int[] color1 = {r, g, b, (int)(255 * rfpart(yend)*xgap)};
			int[] color2 = {r, g, b, (int)(255 * fpart(yend)*xgap)};

			raster.setPixel((int)ypxl2, xpxl2, color1);
			raster.setPixel((int)(ypxl2+1), xpxl2, color2);
		} else {
			int[] color1 = {r, g, b, (int)(255 * rfpart(yend)*xgap)};
			int[] color2 = {r, g, b, (int)(255 * fpart(yend)*xgap)};

			raster.setPixel(xpxl2, (int)ypxl2, color1);
			raster.setPixel(xpxl2, (int)(ypxl2+1), color2);
		}

		// Main loop
		for (int x = xpxl1+1; x < xpxl2; x++){
			if (steep){
				int[] color1 = {r, g, b, (int)(255 * rfpart(intery))};
				int[] color2 = {r, g, b, (int)(255 * fpart(intery))};

				raster.setPixel((int)ipart(intery), x, color1);
				raster.setPixel((int)(ipart(intery)+1), x, color2);
			} else {
				int[] color1 = {r, g, b, (int)(255 * rfpart(intery))};
				int[] color2 = {r, g, b, (int)(255 * fpart(intery))};

				raster.setPixel(x, (int)ipart(intery), color1);
				raster.setPixel(x, (int)(ipart(intery)+1), color2);
			}
			intery += gradient;
		}
	}

	// For beznc
	public static Vertex recursiveLerp(int n, Vertex[] vertices, float t){
		if (n==2){
			float x = vertices[0].getX() + (vertices[1].getX() - vertices[0].getX())*t;
			float y = vertices[0].getY() + (vertices[1].getY() - vertices[0].getY())*t;
			Vertex v = new Vertex(x,y);
			return v;
		}

		Vertex[] tmp = new Vertex[n-1];
		for (int j=0; j<n-1; j++){
			Vertex v = lerp(vertices[j], vertices[j+1], t);
			tmp[j] = v;
		}

		return recursiveLerp(n-1, tmp, t);
	}

	// Completed with minor differences, based on http://www.cubic.org/docs/bezier.htm
	// +10%
	public static void beznc(int n, Vertex[] points, String hex){
		Color fromHex = new Color(
			Integer.valueOf(hex.substring(1,3),16), 
			Integer.valueOf(hex.substring(3,5),16), 
			Integer.valueOf(hex.substring(5,7),16));
		int[] color = {
			fromHex.getRed(), 
			fromHex.getGreen(), 
			fromHex.getBlue(), 
			255	};
		for (int i=0; i<1000; i++){
			float t = (float)i/999.0f;

			Vertex v = recursiveLerp(n, points, t);


			raster.setPixel((int)v.getX(), (int)v.getY(), color);
		}
	}	

	// Extra, didn't really work for Wu Lines or Wu Circles, based on http://freespace.virgin.net/hugo.elias/graphics/x_wupixl.htm
	public static void aadot(Vertex i1){
		int x = (int) i1.getX();
		int y = (int) i1.getY();

		float fx = i1.getX() - x;
		float fy = i1.getY() - y;

		int atl = (int) ((1-fx) * (1-fy) * i1.getA());
		int atr = (int) ((fx) * (1-fy) * i1.getA());
		int abl = (int) ((1-fx) * (fy) * i1.getA());
		int abr = (int) ((fx) * (fy) * i1.getA());

		int[] ctl = {i1.getR(), i1.getG(), i1.getB(), atl};
		int[] ctr = {i1.getR(), i1.getG(), i1.getB(), atr};
		int[] cbl = {i1.getR(), i1.getG(), i1.getB(), abl};
		int[] cbr = {i1.getR(), i1.getG(), i1.getB(), abr};

		raster.setPixel( x, y, ctl);
		raster.setPixel( x+1, y, ctr);
		raster.setPixel( x, y+1, cbl);
		raster.setPixel( x+1, y+1, cbr);
	}

	// For lineca, based on https://en.wikipedia.org/wiki/Alpha_compositing
	public static int[] alphaBlend(int[] src, int[] dest){
		// Must convert alphas as decimals in function (0. - 1.)
		if (src[3] == 0){
			return dest;
		}

		if (dest[3] == 0){
			return src;
		}
		float srcA = (float) src[3] /255.0f;
		float destA = (float) dest[3]/255.0f;

		if (destA == 1.0f){
			float outA = 1.0f;

			float outR = (src[0]*srcA + dest[0]*(1-srcA));
			float outG = (src[1]*srcA + dest[1]*(1-srcA));
			float outB = (src[2]*srcA + dest[2]*(1-srcA));

			int[] blend = {(int)outR, (int)outG, (int)outB, (int)(255.0f*outA)};
			return blend;
		}

		float outA = srcA + destA*(1-srcA);

		float outR = (src[0]*srcA + dest[0]*destA*(1-srcA))/outA;
		float outG = (src[1]*srcA + dest[1]*destA*(1-srcA))/outA;
		float outB = (src[2]*srcA + dest[2]*destA*(1-srcA))/outA;

		int[] blend = {(int)outR, (int)outG, (int)outB, (int) (255.0f*outA)};
		return blend;
	}

	// For trica
	public static void lineca( Vertex i1, Vertex i2, String hex){
		// Bresenham algorithm
		int r = Integer.valueOf(hex.substring(1,3),16);
		int g = Integer.valueOf(hex.substring(3,5),16);
		int b = Integer.valueOf(hex.substring(5,7),16);
		int a = Integer.valueOf(hex.substring(7,9),16);
		int[] src = {r, g, b, a};

		int x1 = (int)i1.getX();
		int y1 = (int)i1.getY();
		int x2 = (int)i2.getX();
		int y2 = (int)i2.getY();
		int dx = Math.abs(x2 - x1);
		int dy = Math.abs(y2 - y1);
		int sx, sy;

		if (x1 < x2)
			sx = 1;
		else 
			sx = -1;

		if (y1 < y2)
			sy = 1;
		else
			sy = -1;

		int err = dx - dy;

		while (true) {
			int[] dest = new int[4];
			dest = raster.getPixel(x1,y1, dest);
			int[] blend = alphaBlend(src, dest);
			raster.setPixel(x1,y1,blend);
			if (x1 == x2 && y1 == y2) { break; }
			int e2 = 2*err;
			if (e2 > -dy) { err -= dy; x1 += sx; }
			if (x1 == x2 && y1 == y2) {
				dest = raster.getPixel(x1,y1, dest);
				blend = alphaBlend(src, dest);
				raster.setPixel(x1,y1,blend);
				break;
			}
			if (e2 < dx) { err += dx; y1 += sy; }

		}
	}

	// Completed with minor differences
	// +10%
	public static void trica(Vertex i1, Vertex i2, Vertex i3, String hex) {

		Vertex[] vertices = new Vertex[3];
		vertices[0] = i1;
		vertices[1] = i2;
		vertices[2] = i3;

		int[] order = new int[3];

		// Find lowest y
		if (i1.getY() < i2.getY()){
			if (i1.getY() < i3.getY()){
				order[0] = 0;
			} else {
				order[0] = 2;
			}
		} else {
			if (i2.getY() < i3.getY()){
				order[0] = 1;
			} else {
				order[0] = 2;
			}
		}
		// Find highest y
		if (i1.getY() > i2.getY()){
			if (i1.getY() > i3.getY()){
				order[2] = 0;
			} else {
				order[2] = 2;
			}
		} else {
			if (i2.getY() > i3.getY()){
				order[2] = 1;
			} else {
				order[2] = 2;
			}
		}

		order[1] = 3 - (order[0] + order[2]);

		if (vertices[order[0]].getY() - vertices[order[2]].getY() != 0.0f){
			if (vertices[order[1]].getY() - vertices[order[2]].getY() == 0.0f){		
				// Triangle has flat bottom.
				float slope1 = (vertices[order[1]].getX() - vertices[order[0]].getX()) / (vertices[order[1]].getY() - vertices[order[0]].getY());
				float slope2 = (vertices[order[2]].getX() - vertices[order[0]].getX()) / (vertices[order[2]].getY() - vertices[order[0]].getY());

				float temp;
				if (slope1 < slope2){
					temp = slope1;
					slope1 = slope2;
					slope2 = temp;
				}

				float x1 = vertices[order[0]].getX();
				float x2 = vertices[order[0]].getX();

				for (int y = (int) vertices[order[0]].getY(); y < (int) vertices[order[1]].getY(); y++){
					Vertex v1 = new Vertex((float)Math.floor(x1+0.5f), (float)y);
					Vertex v2 = new Vertex((float)Math.floor(x2+0.5f), (float)y);
					lineca(v1, v2, hex);
					x1 += slope1;
					x2 += slope2;
				}
			} else if(vertices[order[0]].getY() - vertices[order[1]].getY() == 0.0f){	
				// Triangle has flat top.
				float slope1 = (vertices[order[2]].getX() - vertices[order[0]].getX()) / (vertices[order[2]].getY() - vertices[order[0]].getY());
				float slope2 = (vertices[order[2]].getX() - vertices[order[1]].getX()) / (vertices[order[2]].getY() - vertices[order[1]].getY());

				float temp;
				if (slope1 < slope2){
					temp = slope1;
					slope1 = slope2;
					slope2 = temp;
				}

				float x1 = vertices[order[2]].getX();
				float x2 = vertices[order[2]].getX();

				for (int y = (int) vertices[order[2]].getY(); y > (int) vertices[order[0]].getY(); y--){
					x1 -= slope1;
					x2 -= slope2;
					Vertex v1 = new Vertex((float)Math.floor(x1+0.5f), (float)y);
					Vertex v2 = new Vertex((float)Math.floor(x2+0.5f), (float)y);
					lineca(v1, v2, hex);
				}
			} else {	
				Vertex mid =new Vertex(
					vertices[order[0]].getX() + ( vertices[order[1]].getY() - vertices[order[0]].getY() ) / ( vertices[order[2]].getY() - vertices[order[0]].getY() ) * ( vertices[order[2]].getX() - vertices[order[0]].getX() ),
					vertices[order[1]].getY());

				// Fill top half
				float slope1 = (vertices[order[1]].getX() - vertices[order[0]].getX()) / (vertices[order[1]].getY() - vertices[order[0]].getY());
				float slope2 = (vertices[order[2]].getX() - vertices[order[0]].getX()) / (vertices[order[2]].getY() - vertices[order[0]].getY());

				float temp;
				if (slope1 < slope2){
					temp = slope1;
					slope1 = slope2;
					slope2 = temp;
				}

				float x1 = vertices[order[0]].getX();
				float x2 = vertices[order[0]].getX();

				for (int y = (int) vertices[order[0]].getY(); y < (int) vertices[order[1]].getY(); y++){
					Vertex v1 = new Vertex((float)Math.floor(x1+0.5f)-0.5f, (float)y);
					Vertex v2 = new Vertex((float)Math.floor(x2+0.5f)-0.5f, (float)y);
					lineca(v1, v2, hex);
					x1 += slope1;
					x2 += slope2;
				}

				// Fill bottom half
				slope1 = (vertices[order[2]].getX() - vertices[order[0]].getX()) / (vertices[order[2]].getY() - vertices[order[0]].getY());
				slope2 = (vertices[order[2]].getX() - vertices[order[1]].getX()) / (vertices[order[2]].getY() - vertices[order[1]].getY());


				if (slope1 < slope2){
					temp = slope1;
					slope1 = slope2;
					slope2 = temp;
				}

				x1 = vertices[order[2]].getX();
				x2 = vertices[order[2]].getX();

				for (int y = (int) vertices[order[2]].getY(); y > (int) vertices[order[1]].getY(); y--){
					x1 -= slope1;
					x2 -= slope2;
					Vertex v1 = new Vertex(x1, (float)y);
					Vertex v2 = new Vertex(x2, (float)y);
					lineca(v1, v2, hex);
				}
			}
		} else {
			System.out.println("Trianlge has no height. Not drawing it.");
		}
	}	

}