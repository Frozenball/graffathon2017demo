import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.sound.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class sketch extends PApplet {

float x,y,z;
float timeStart = 0;
int[][] colors = {  {231, 76, 60},
                     {52, 152, 219},
                     {46, 204, 113},
                     {26, 188, 156},
                     {241, 196, 15}
                  };
PFont f;

SoundFile file;

Amplitude rms;

// Declare a scaling factor
float scale = 5.0f;

// Declare a smooth factor
float smoothFactor = 0.25f;

// Used for smoothing
float sum;


public void setup() {
  //size(720, 480, P3D);
  
  x = width/2;
  y = height/2;
  z = 0;
  timeStart = millis();
  
  file = new SoundFile(this, "Ether_Disco.mp3");
  file.play();

  
  // Create and patch the rms tracker
  //rms = new Amplitude(this);
  //rms.input(file);
  
  
  f = createFont("Arial",16,true); 
}

public float time() {
 return millis() - timeStart; 
}


public void stageOne() {
   int colorI = (int) (time()*0.004f) % 5;
  
  if (time() <= 42000) colorI = 0;
  
  int colorP = (colorI - 1) % 5;
  if (colorP < 0) colorP = 4;
  background(colors[colorP][0], colors[colorP][1], colors[colorP][2]);
  
  
  textFont(f);       
  fill(0);
  
  textAlign(LEFT);
  text(time(),30,30); 

  
  
  translate(x,y,z);
  rectMode(CENTER);
  rotateX(sin(time()*0.0004245f));
  rotateY(sin(time()*0.0007217f));
  rotateZ(sin(time()*0.0008532f));
  z = 0 + 300*sin(time()*0.001f);
  
  fill(colors[colorI][0], colors[colorI][1], colors[colorI][2]);
  stroke(colors[colorI][0], colors[colorI][1], colors[colorI][2]);
  
  // Smooth the rms data by smoothing factor
  //sum += (rms.analyze() - sum) * smoothFactor;  

  // rms.analyze() return a value between 0 and 1. It's
  // scaled to height/2 and then multiplied by a scale factor
  //float rmsScaled = sum * (height/2) * scale;
  float rmsScaled = 0;
  rect(0,0,100 + rmsScaled*0.3f + 20*sin(time()*0.01f)*cos(time()*0.003f),100 + rmsScaled*0.3f + 20*sin(time()*0.007f)*cos(time()*0.002f));
}

public float smoothAfter(float x, float xStart) {
  if (x < xStart) return 0.0f;
  return min(1.0f, 0.001f*(x - xStart));
}
public float smoothBefore(float x, float xEnd) {
  if (x < xEnd) return 1.0f;
  else {
    return max(0, 1 - 0.001f*(x - xEnd));
  }
}

public int mod(int a, int b) {
 int p = a % b;
 if (p < 0) p += b;
 return p;
}

public void stageTwo() {
  
  background(colors[0][0], colors[0][1], colors[0][2]);
  background(0);
  fill(colors[1][0], colors[1][1], colors[1][2]);
  noStroke();
  //stroke(colors[1][0], colors[1][1], colors[1][2]);
  translate(0, 0, -700 + 300*sin(time()*0.001f)*smoothAfter(time(), 10000));
  rotateX(sin(time()*0.001f)*0.2f * smoothAfter(time(), 14000) );
  rotateY(sin(time()*0.000953f)*0.2f * smoothAfter(time(), 14000));
  rotateZ(sin(time()*0.000573f)*0.2f * smoothAfter(time(), 14000));
  for (int x = -5*2; x < 7*4; x++) {
    for (int y = -5*2; y < 6*4; y++) {
      float k = sin(1234.43257f*x)*0.1f;
      pushMatrix();
      fill(colors[1][0], colors[1][1], colors[1][2]);
      translate(65 + 120*x, 65 + 120*y, 0);
      rectMode(CENTER);
      if (time() >= 12000) rotateY(smoothBefore(time(), 20000) * smoothAfter(time(), 12000) * sin(time()*0.04245f*k));
      if (time() >= 12000 && (x + 2*y) % 5 == 0) {
        int randomColor = 0 + mod((int)(time()*0.004f + 21*x + 421*y), 5);
        if (time() <= 8000) randomColor = 1;
        
        
        fill(colors[randomColor][0], colors[randomColor][1], colors[randomColor][2]);
        rotateX(smoothAfter(time(), 18000 + x*400) * smoothAfter(time(), 14000) * sin(time()*0.005245f + 12389.634624f*x));
      }
      if (time() >= 22000 && (3*x + y) % 5 == 0) {
        int randomColor = 0 + mod((int)(time()*0.004f + 11*x + 5*y), 5);
        if (time() <= 8000) randomColor = 1;
        fill(colors[randomColor][0], colors[randomColor][1], colors[randomColor][2]);
        rotateZ(smoothAfter(time(), 22000 + y*400) * smoothAfter(time(), 22000) * sin(time()*0.005245f + 12389.634624f*y));
      }
      if (time() <= 14000 && time() >= (6000 + 6000*sin(12345.5785f*x*532.12412f*y + 132.453252f*x + 124648.8473f*y))) {
        int randomColor = 0 + mod((int)(time()*0.004f + 11*x + 5*y), 5);
        fill(colors[randomColor][0], colors[randomColor][1], colors[randomColor][2]);
      }
      if (time() >= 30000) {
        int randomColor = 0 + mod((int)(5*sin(y)), 5);
        if (time() < 30500+(10*100+x*100)) {
          fill(0);
        } else {
          fill(colors[randomColor][0], colors[randomColor][1], colors[randomColor][2]);
        }
      }
      rect(0, 0, 100, 100);
      popMatrix();
    }
  }
}

public void draw() {
  /* esitt\u00e4j\u00e4n Kevin MacLeod kappale Ether Disco on suojattu lisenssill\u00e4 Creative Commons Attribution (https://creativecommons.org/licenses/by/4.0/)
L\u00e4hde: http://incompetech.com/music/royalty-free/index.html?isrc=USUAN1100237
Esitt\u00e4j\u00e4: http://incompetech.com/
  */
  //stageOne();
  if (time() <= 40000) stageTwo();
  else stageOne();
  if (time() >= 50000) exit();
  //stageTwo();
  //if ((int)(time()/1000) % 2 == 0) stageOne();
  //else stageTwo();
    /*
  if (time() <= 20000) stageTwo();
  else {
    if ((int)(time()/1000) % 2 == 0) stageOne();
    else stageTwo();
    stageOne();
  }*/
  
}
  public void settings() {  size(1980, 1080, P3D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "sketch" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
