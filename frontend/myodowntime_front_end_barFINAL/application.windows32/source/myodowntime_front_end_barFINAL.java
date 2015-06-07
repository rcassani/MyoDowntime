import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class myodowntime_front_end_barFINAL extends PApplet {

 

PImage bg;
PImage bar;

//brush shenanigans
int col;
int picked=0;
int[] colArray = new int[9]; 
//int splats = 1;
float r, g, b, a;

Brush[] segments;
float del = 4;
float targetX;
float targetY;
float targetSize = 15;
float e;



// Declare a Random number generator object
Random generator;

public void setup() {
   ellipseMode(CENTER);
  size(1920, 1080);
  background(155);
  bg = loadImage("background.png");
  image(bg,0,0);
bar = loadImage("bar.png");
image(bar,0,780);

  colArray[0] = color(2, 97, 85, 155);
  colArray[1] = color(0, 90, 85, 155);
  colArray[2] = color(13, 154, 170, 155);
  colArray[3] = color(96, 3, 3, 155);
  colArray[4] = color(29, 3, 103, 155);
  colArray[5] = color(52,3, 103, 155);
  colArray[6] = color(11, 11, 11, 155);
  colArray[7] = color(12,132, 16, 155);
  colArray[8] = color(234, 234, 234, 155);



  segments = new Brush[50];
  for (int i=0; i<segments.length; i++) {
    segments[i] = new Brush(200.0f, 200.0f+i*10, 0.0f, 0);
  }
  generator = new Random();   // Initialize generator
  smooth();
}

public void draw() {

  mousePressed();
  mouseReleased();
   if (keyPressed) {
    if (key == ' ' || key == ' ') {
        image(bg,0,0);
    }
}

image(bar,0,780);
fill(color(colArray[picked]));

ellipse(213,968,105,105);
}

public void mousePressed() {

  if (mouseButton == LEFT) {
    brushDraws();
  }
}

public void mouseReleased() {
  if (mouseButton == RIGHT) {
    if (picked <8) {
      picked++;
      col = color(colArray[picked]);
    } else {
      gaussColor(r, g, b, a);
      col = color(r, g, b, a);
      picked = 0;
      col = color(colArray[picked]);
    }
  }
}





//BRUSH ACTION
public void brushDraws() {
  targetX += (mouseX-targetX)/del;
  targetY += (mouseY-targetY)/del;
  for (int i=segments.length-1; i>=0; i--)
  {

    mouseWheel();
    mouseReleased();

    segments[i].lenth += (targetSize-segments[i].lenth)/del;
    if (targetSize <= 5)
    {
      targetSize = 5;
    }
    if (i<segments.length-1)
    {

      segments[i].rot = atan2(segments[i+1].yPos-segments[i].yPos, segments[i+1].xPos-segments[i].xPos);
      segments[i].xPos = segments[i+1].xPos-cos(segments[i].rot)*segments[i].lenth;
      segments[i].yPos = segments[i+1].yPos-sin(segments[i].rot)*segments[i].lenth;
      segments[i].col = color(colArray[picked]);
    }
    if (i==segments.length-1)
    {
      segments[segments.length-1].rot = atan2(targetY-segments[segments.length-1].yPos, targetX-segments[segments.length-1].xPos);
      segments[segments.length-1].xPos = targetX-cos(segments[segments.length-1].rot)*segments[segments.length-1].lenth;
      segments[segments.length-1].yPos = targetY-sin(segments[segments.length-1].rot)*segments[segments.length-1].lenth;
      segments[i].col = color(colArray[picked]);
    }
    segments[i].display();
  }
}

//toggle brush size
public void mouseWheel(MouseEvent event) {
  float e = event.getCount();
  println(e);
  if (e < 0)
  {
    targetSize -= 1;
  }
  if (e>0)
  {
    targetSize += 1;
  }
}


public void gaussColor(float rloc, float gloc, float bloc, float aloc) {
  // Get a gaussian random number w/ mean of 0 and standard deviation of 1.0
  rloc = (float) generator.nextGaussian();
  gloc = (float) generator.nextGaussian();
  bloc = (float) generator.nextGaussian();
  aloc = (float) generator.nextGaussian();

  float sd = 150;                // Define a standard deviation
  float mean = 255/2;         // Define a mean value (middle of the screen along the x-axis)
  r = ( rloc * sd ) + mean;  // Scale the gaussian random number by standard deviation and mean
  g = ( gloc * sd ) + mean;  // Scale the gaussian random number by standard deviation and mean
  b = ( bloc * sd ) + mean;  // Scale the gaussian random number by standard deviation and mean
  a = ( aloc * sd ) + mean;  // Scale the gaussian random number by standard deviation and mean
}

class Brush
{
  float xPos, yPos;
  float rot;
  float lenth;
  int col;

  Brush(float tempx, float tempy, float temprot, int tempcol)
  {
    xPos = tempx;
    yPos = tempy;
    rot = temprot;

    lenth = 5;
    col = tempcol;
  }
  public void display()
  {
    pushMatrix();

    translate(xPos, yPos);
    rotate(rot);
    strokeWeight(1);
    stroke(col);
    line(0, 0, lenth, 0);

    popMatrix();
  }
}

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--full-screen", "--bgcolor=#666666", "--hide-stop", "myodowntime_front_end_barFINAL" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
