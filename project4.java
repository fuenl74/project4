////project 4
////luis fuentes
////cst 112
//// Arrays of objects.


int nb=16;
Ball[] b=  new Ball[nb];         // Array of Ball objects.

int nh=10;
Button[] h=  new Button[nh];    // Array of Button objects.

float left, right, top, bottom, middle;

WTable t;
Cloud[] cloudList;

//// SETUP:  create arrays, initialize, ec.
void setup() {
  size(640, 480);
   t= new WTable();
   t.left=   50;
   t.right=  width-50;
   t.top=    150;
   t.bottom= height-50;
   t.middle= t.left + (t.right-t.left) / 2;
   t.horizon= (height/4)-20;
   t.wall= false ;
  // Create cue and 15 balls.
  b[0]=  new Ball( 0, 250, 250, 250 );
  b[0].x=  200;
  b[0].y=  290;
  b[0].dx=  0;
  b[0].dy=  0;
  b[0].r=  180;
  b[0].g=  180;
  b[0].b=  255;
  for (int i=1; i<nb; i++) {
    b[i]=  new Ball( 0, 250, 250, 250 );
  }
  h[0]=  new Button( 20, 25 );
  for (int i=1; i<nh; i++) {
    h[i]=  new Button( 120,25 );
  }
    cloudList= new Cloud[7];
  float cloudX=50;
  for( int i=0; i<cloudList.length; i++) {
     cloudList[i]=  new Cloud( cloudX,random(t.horizon-100,t.horizon-25));
     cloudX += 100;
  }
}

//// NEXT:  Draw ball, after moving and colliding.
void draw() {
  background( 100,150,250 );
  drawClouds();
  t.tableDisplay();
  scene();
  action();
  show();
  messages();
 
  
}

//// Table, buttons, etc.
void scene() {
}

//// ACTION:  move, collide, etc.
void action() {
  for( int i=0; i<nb-1; i++) {
    for( int j=i+1; j<nb; j++) {    
      collide( b[i], b[j] );
    }
  }
  //// Move all balls.
  for( int i=0; i<nb; i++) {
   b[i].move();
  }
}
//// Elastic collisions -- swap dx,dy
void collide( Ball p, Ball q ) {
  if ( p.hit( q.x,q.y ) ) {
    float tmp;
    tmp=p.dx;  p.dx=q.dx;  q.dx=tmp;     
    tmp=p.dy;  p.dy=q.dy;  q.dy=tmp;
    noStroke();
    fill(250,250,250,250);
    ellipse(b[0].x,b[0].y,40,40);
    
  }//++++
}

//// Display 
void show() {
  for( int i=0; i<nb; i++) {
    b[i].show();
  }
  for( int i=0; i<nh; i++) {
    h[i].buttonDisplay();}
}

//// ????
void messages() {
}


 ///showing the clouds
void drawClouds(){
   for (int i=0; i<cloudList.length; i++) {

    cloudList[i].showClouds();
  }
}


//////// CLASSES ////////
class Ball {
  float x,y, dx,dy;
  float r,g,b;
  int number;
  //// CONSTRUCTORS:
  Ball( int n ) {
    number=  n;
    randomize();
  }
  Ball( int n, float r, float g, float b ) {
    number=  n;
    this.r=  r;
    this.g=  g;
    this.b=  b;
    randomize();
  }
  Ball( int n, float x, float y ) {
    number=  n;
    randomize();
  }
  void randomize() {
    r=  random(255);
    g=  random(255);
    b=  random(255);
    x=  random( width/2, width-100);
    y=  random( height/4, height*3/4);
    dx=  random( -2, 2 );
    dy=  random( -2, 2 );
  }
  
  //// METHODS ////
  /*void move() {
    //+++++
    x += dx;
    y += dy;
  }*/
    void move() {
    if (t.wall) {                        //if the wall is there
      if (x>t.right-4 || x<t.middle+20) 
      {  
        dx=  -dx;
      }
    } else {                                                
      if (x>t.right-4 || x<t.left+4) 
      {  
        dx=  -dx;
      }
    }
    if (y>t.bottom-4 || y<t.top+4) 
    {  
      dy=  -dy;
    }
    x=  x+dx;
    y=  y+dy;
  }
  void show() {
    //+++++
    noStroke();
    fill(r,g,b);
    ellipse( x, y, 15, 15 );
  }
   boolean hit( float x, float y ) {                        
    if (  dist( x,y, this.x,this.y ) < 15 ) return true;   
    else return false;
  }
}
class WTable {
  //PROPERTIES
  float left, right, top, bottom, middle;
  float horizon;
  boolean wall;
  
  //METHODS
  void tableDisplay(){
    noStroke();
    fill( 50, 200, 50 );    
    strokeWeight(20);
    stroke( 100, 27, 20 );      // walls
    rectMode( CORNERS );
    rect( left-20, top-20, right+20, bottom+20 );
    stroke(0);
    strokeWeight(1);
    
    if (wall) {
      stroke( 100, 27, 20 );
      strokeWeight(10);
      line( middle,top-10, middle,bottom+10 );
    }
  }
} 

class Button {
  //PROPERTIES
  float x,y;
  String words;
  boolean counter;
  int buffer;
  //CONSTRUCTOR if any
  Button(float tempX, float tempY) {
    x = tempX;
    y = tempY;
    counter = false;
    buffer = 0;
  }
  
  void buttonDisplay(){
    fill(100,120,130);
    noStroke();
    rectMode( CORNER );
    rect(x, y, 80, 40);/*
   fill(255);
    textSize(16);
    text( words, x+25, y+25);
    textSize(13);*/
  }
  
  
}

/*class Rat {   float x,y,DX,DY;
  boolean crawl;
  boolean scoreBuffer;
  int scoreBufferCounter;
  
  Rat() {
    crawl = false;
    scoreBuffer = false;
    x = -50;
    y = random(170,height-50);
  }
  
  void moveRat(){
    if (crawl){
      DX=random(0,5);
      DY=random(-5,5);
      x+=DX;
      y+=DY;
      if (x>width+50){
        crawl = false;
        x = -50;
        y = random(170,height-50);
      }
    }
  }
  
  void showRat(){
      noStroke();                        //only display mouse if true
      fill(150,150,150);
      ellipse(x, y,45,40);//body
      ellipse(x+27, y+5,25,20); //head 
      ellipse(x-33, y,35,7);//tail
      ellipse(x+36, y+5,15,14);//nose
      ellipse(x-15, y+20,5,20);//leg
      ellipse(x+15, y+20,5,20);//leg
      fill(100,100,125);//leg
      ellipse(x+27, y+5,4,4);//eye
       
      
  
    }
 

  void clickRat(){
    if (dist(mouseX,mouseY,x,y)<20){
         crawl = false;
        x = -50;
        y = random(170,height-50);
        score +=50;
    }
  }
      
  void scoreFix(){
    if (scoreBuffer == true){
      scoreBufferCounter +=1;
      if (scoreBufferCounter>10){
        scoreBuffer = false;
      }
    }
  }  
}*/

class Cloud{
  
  float x, y; 
  float fillColor=100;  
  
  Cloud( float x, float y){
    this.x=x;  this.y=y;
  }
 
  void showClouds() {
    stroke(0);
    // Change fill every second
    if (frameCount % 30 == 0) {
      fillColor=  random(50,220);
    }
    fill(fillColor);
    noStroke();
    ellipse(x+22,y-16,25,25);
    ellipse(x+11,y-7,25,25);
    ellipse(x+33,y-7,25,25);
    ellipse(x+22,y-7,25,25);
    stroke(random(100,250),random(100,250),0);
    strokeWeight(0.8);
    rectMode(CORNER);
    rect(x,y-5,0,16);
    rect(x+10,y+3,0,16);
    rect(x+20,y+5,0,16);
    rect(x+30,y+3,0,16);
    rect(x+43,y-2,0,16);
    stroke(0,0,0);
    noStroke();
    x++;
    if (x>width+10){
      x= random(-200,-100);
      
    }
  }
}
/*class Bird {
  //// PROPERTIES:  position, speed, color, etc. ////   (What a Bird "has".)
  float x=0,y=50,by,bDY;
  float w=60;
  int r,g,b;
  int number;
  boolean wingUp=false;
  
  boolean fly,drop;
  
  //// CONSTRUCTORS (if any). ////
  Bird(){
  fly=false;
  drop=false;
  x= -50;
  bDY=2;
  }
  
  //// METHODS:  show, move, detect a "hit", etc. ////  (What a Ball "does".)
  void show() {
    strokeWeight(0);
    fill(0,50,100);
    triangle( x,y, x-w,y-10, x-w,y+10 );
    // Wing
    wingUp=  int(frameCount/30) %2 >0;
    fill(100);
    if (wingUp) {
      triangle( x-w/3,y, x-w*2/3,y, x-w/2,y-40 );
    }else{
      triangle( x-w/3,y, x-w*2/3,y, x-w/2,y+40 );
    }
  }
  
 void moveBird(){
    if (fly)
    x +=4;
    if (x>width+50){
      x= -50;
      fly = false;             
      drop =false;             
      three.counter = false;   
      three.buffer = 0;        
      by = 70;
      bDY = 2;
      
    }
  }
   void bombDrop(){
    if (drop==true){
        noStroke();
        fill(105);
        rect(x,by-50,25,50);
        fill(30);
        rect(x+9,by,10,15);
        fill(250,55,0);
        rect(x+7,by-71,7,23);
        rect(x+21,by-57,7,19);
        rect(x-3,by-57,7,19);
        by += bDY;
        bDY += .25;
    }
  }

  boolean hit( float x, float y ) {
    if (  dist( x,y, this.x,this.y ) < 30 ) return true;
    else return true;
  }
  
}*/
