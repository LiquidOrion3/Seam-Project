package Main;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


/***
 * 
 * Main Class 
 *
 */
public class Main {

	public static void main(String args[]){
		new Main("res/five.png","test.png",5,false);	
	}

	private int []columns,rows;
	private int h=0;
	private File f; // saved image file 


	int count;
	private int [][] matrix = new int [400][400];


	node head= new node();

	public Main(String source,String output,int test,boolean value){ 
		f = new File(output);
		BufferedImage img = null; 

		try {
			img = ImageIO.read(new File(source));
			h=img.getHeight();
			columns= new int[h];
			rows= new int[h];

			for(int i=0;i<h;i++){
				columns[i]=rows[i]=0;
			}

			if(test==1){
				test(h,h,img,value);
			}
			else if(test==2){
				test2(h,h,img,value);
			}
			else if(test==3){
				test3(h,h,img,value);
			}
			else if(test==4){
				test4(h,h,img,value);
			}
			else if(test==5){
				test5(h,h,img,value);
			}
			else{}
			ImageIO.write(img, "PNG", f);
			System.out.println("complete");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void test(int height,int width,BufferedImage img,boolean gradient){
		int pixel=0;int right=0;int down=0;
		int startColor=0;
		int setBackground=0xffffff;

		for(int y=0;y<height-1;y++){
			for(int x=0;x<width-1;x++){
				pixel=img.getRGB(x, y);
				right=img.getRGB(x+1, y);
				down=img.getRGB(x, y+1);
				int a=Math.abs((pixel-right));
				int b=Math.abs((pixel-down));
				if((a>1)|(b>1)){
					int setColor=startColor;
					int val=0;

					if(gradient){
						if(a>b){
							val=a;
						}
						else {
							val=b;
						}



						while(val>0){
							setColor+=0x00fff;
							val-=10;
						}
					}
					img.setRGB(x, y, setColor);	
				}

				else{
					img.setRGB(x, y, setBackground);				
				}
			}
		}
	}


	public void test2(int height,int width,BufferedImage img,boolean direction){
		int color1=0;int color2=0;int color3=0;

		for(int y=0;y<height-1;y++){
			for(int x=0;x<width-1;x++){

				color1=img.getRGB(x, y);
				color2=img.getRGB(x+1, y);
				color3=img.getRGB(x, y+1);
				int a=Math.abs((color1-color2));
				int b=Math.abs((color1-color3));
				if((a>1)|(b>1)){

					int val=0;
					if(a>b){val=a;}
					else {val=b;}

					columns[y]+=val;
					rows[x]+=val;
				}
			}
		}
		slicePainter(height, width, img, direction);		

	}
	public void slicePainter(int height,int width, BufferedImage img,boolean direction){
		for(int i=0;i<h;i++){
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

	public void test3(int height,int width,BufferedImage img,boolean direction){

		int total=0;
		for(int y=1;y<height-1;y++){
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

	public void test4(int height,int width,BufferedImage img,boolean direction){


		Thread p1 = new Thread();
		p1.setup(height, width, img, direction, 0, height/2, f);
		p1.run();
		Thread p2= new Thread();
		p2.setup(height, width, img, direction, height/2, height-1, f);
		p2.run();

	}
	public void test5(int height,int width,BufferedImage img,boolean direction){


		int color1=0;int color2=0;int color3=0;

		for(int y=0;y<height-1;y++){
			for(int x=0;x<width-1;x++){

				color1=img.getRGB(x, y);
				color2=img.getRGB(x+1, y);
				color3=img.getRGB(x, y+1);
				int a=Math.abs((color1-color2));
				int b=Math.abs((color1-color3));
				if((a>1)|(b>1)){

					int val=0;
					if(a>b){val=a;}
					else {val=b;}

					matrix[x][y]=val;
				}
			}
		}


		head = new node();
		//head.setxy(0,0);
		//head.setnumber(matrix[0][0]);
		down(head);



		node temp=head;
		while(temp.getnode()!=null){
			System.out.println(temp.xy());
			img.setRGB(temp.getx(), temp.gety(), 0xf0f0f0);
			temp=temp.getnode();
		}





	}
	public void down(node node){
		int x=node.getx();
		int y=node.gety();
		System.out.println(x+" "+y);



		if(y==(h-1)){
			node.setnumber(matrix[x][y]);
			System.out.println("base");
		}
		else if((x==0)){

			node down = new node();
			down.setxy(x, y+1);
			if(down.getnumber()==-11)
				down(down);


			node right = new node();
			right.setxy(x+1, y+1);
			if(right.getnumber()==-11)
				down(right);

			int b= down.getnumber();
			int c= right.getnumber();

			if(b<c){
				node.setnode(down);
				node.setnumber(matrix[x][y]+b);
			}
			else{
				node.setnode(right);
				node.setnumber(matrix[x][y]+c);
			}

		}
		else if(x==(h-1)){
			node left = new node();
			left.setxy(x-1,y+1);
			if(left.getnumber()==-11)
				down(left);

			node down = new node();
			down.setxy(x, y+1);
			if(down.getnumber()==-11)
				down(down);


			int a = left.getnumber();
			int b= down.getnumber();

			if(a<b){
				node.setnode(left);
				node.setnumber(matrix[x][y]+a);

			}
			else {
				node.setnode(down);
				node.setnumber(matrix[x][y]+b);
			}



		}
		else{	
			node left = new node();
			left.setxy(x-1,y+1);
			if(left.getnumber()==-11)
				down(left);

			node down = new node();
			down.setxy(x, y+1);
			if(down.getnumber()==-11)
				down(down);


			node right = new node();
			right.setxy(x+1, y+1);
			if(right.getnumber()==-11)
				down(right);

			int a = left.getnumber();
			int b= down.getnumber();
			int c= right.getnumber();
			if(a<b & a<b){
				node.setnode(left);
				node.setnumber(matrix[x][y]+a);

			}
			else if(b<a& b<c){
				node.setnode(down);
				node.setnumber(matrix[x][y]+b);
			}
			else {
				node.setnode(right);
				node.setnumber(matrix[x][y]+c);

			}

		}





	}

	public int low(int x, int y, int height){
		System.out.println(x+"--"+y);
		if(y==height){
			return matrix[x][y-1];
		}
		else{
			low(x,y+1,height);
		}
		return 0;

	}
	public int lowest(int x, int y,int height){

		//System.out.println(count++);
		System.out.println(x+" "+y);
		if(y==height-1){
			return matrix[x][y];
		}
		else if (x==0){

			int a=lowest(x,y+1,height);
			int b=lowest(x+1,y+1,height);

			if(a<b)
				return a;
			else return b; 
		}
		else if(x==height-1){

			int a=lowest(x,y+1,height);
			int b=lowest(x-1,y+1,height);

			if(a<b)
				return a;
			else return b; 

		}
		else{

			int a=lowest(x,y+1,height);
			int b=lowest(x-1,y+1,height);
			int c=lowest(x+1,y+1,height);

			if(a<b &a<c){
				return a; 
			}
			else if(b<a & b<c){
				return b;
			}
			else return c;

		}




	}



}


