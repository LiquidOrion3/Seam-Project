package Main;

public class node {

private node node=null;	
private int number=-11;
	
private int x;
private int y;

public node(){
	
}
public void setnode(node node){
	this.node=node;
}
public void setnumber(int number){
	this.number=number;
}
public void setxy(int x, int y ){
	this.x=x;
	this.y=y;
}


public node getnode(){
	return node;
}
public int getnumber(){
	return number;
}
public int getx(){
	return x;	
}
public int gety(){
	return y;
}
public String xy(){
	return " "+x+" "+y;
}

}
