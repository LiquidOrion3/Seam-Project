package Main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Thread implements Runnable{

	private int []columns,rows;
	
	private int height,width;
	private BufferedImage img;
	private boolean direction;
	
	int start;int end;
	File f;
	
	public void setup(int height,int width,BufferedImage img,Boolean direction,int start,int end,File f ){
		this.height=height;
		this.width=width;
		this.img=img;
		this.direction=direction;
		
		this.start=start;
		this.end=end;
		this.f=f;
	}		
	
	public void run(){
		
		int total=0;
		for(int y=start;y<end;y++){
			for(int x=1;x<width-1;x++){

				int center=img.getRGB(x, y);
				int cLeft =img.getRGB(x-1,y);
				int cRight =img.getRGB(x+1,y);

				int Ucenter=img.getRGB(x, y-1);
				int ULeft =img.getRGB(x-1,y-1);
				int URight =img.getRGB(x+1,y-1);

				int Dcenter=img.getRGB(x, y+1);
				int DLeft =img.getRGB(x-1,y+1);
				int DRight =img.getRGB(x+1,y+1);


				int t=Math.abs(center-cLeft);
				if(t>0){total+=t;}

				t=Math.abs(center-cRight);
				if(t>0){total+=t;}

				t=Math.abs(center-ULeft);
				if(t>0){total+=t;}

				t=Math.abs(center-URight);
				if(t>0){total+=t;}

				t=Math.abs(center-Ucenter);
				if(t>0){total+=t;}

				t=Math.abs(center-DLeft);
				if(t>0){total+=t;}

				t=Math.abs(center-DRight);
				if(t>0){total+=t;}

				t=Math.abs(center-Dcenter);
				if(t>0){total+=t;}


				columns[y]+=total;
				rows[x]+=total;


			}
		}

		slicePainter(height, width, img, direction);
		
		
	}
		
		
		
		
		
		
		
	
public void slicePainter(int height,int width, BufferedImage img,boolean direction){
	for(int i=0;i<height;i++){
		int color=0;
		if(direction){
			while(columns[i]>0){
				color+=0x0000ff;
				columns[i]-=100;
			}
			columns[i]=color;
		}
		else{
			while(rows[i]>0){
				color+=0x0000ff;
				rows[i]-=100;
			}
			rows[i]=color;
		}
	}
	if(direction){
		for(int y=0;y<height-1;y++){
			for(int x=0;x<width-1;x++){
				img.setRGB(x,y,columns[y]);
			}
		}
	}
	else {
		for(int y=0;y<height-1;y++){
			for(int x=0;x<width-1;x++){
				img.setRGB(x,y,rows[x]);
			}
		}
	}

}

}
