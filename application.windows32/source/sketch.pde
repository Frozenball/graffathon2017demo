float x,y,z;
float timeStart = 0;
int[][] colors = {  {231, 76, 60},
                     {52, 152, 219},
                     {46, 204, 113},
                     {26, 188, 156},
                     {241, 196, 15}
                  };
PFont f;
import processing.sound.*;
SoundFile file;

Amplitude rms;

// Declare a scaling factor
float scale = 5.0;

// Declare a smooth factor
float smoothFactor = 0.25;

// Used for smoothing
float sum;


void setup() {
  //size(720, 480, P3D);
  size(1980, 1080, P3D);
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

float time() {
 return millis() - timeStart; 
}


void stageOne() {
   int colorI = (int) (time()*0.004) % 5;
  
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
  rotateX(sin(time()*0.0004245));
  rotateY(sin(time()*0.0007217));
  rotateZ(sin(time()*0.0008532));
  z = 0 + 300*sin(time()*0.001);
  
  fill(colors[colorI][0], colors[colorI][1], colors[colorI][2]);
  stroke(colors[colorI][0], colors[colorI][1], colors[colorI][2]);
  
  // Smooth the rms data by smoothing factor
  //sum += (rms.analyze() - sum) * smoothFactor;  

  // rms.analyze() return a value between 0 and 1. It's
  // scaled to height/2 and then multiplied by a scale factor
  //float rmsScaled = sum * (height/2) * scale;
  float rmsScaled = 0;
  rect(0,0,100 + rmsScaled*0.3 + 20*sin(time()*0.01)*cos(time()*0.003),100 + rmsScaled*0.3 + 20*sin(time()*0.007)*cos(time()*0.002));
}

float smoothAfter(float x, float xStart) {
  if (x < xStart) return 0.0;
  return min(1.0, 0.001*(x - xStart));
}
float smoothBefore(float x, float xEnd) {
  if (x < xEnd) return 1.0;
  else {
    return max(0, 1 - 0.001*(x - xEnd));
  }
}

int mod(int a, int b) {
 int p = a % b;
 if (p < 0) p += b;
 return p;
}

void stageTwo() {
  
  background(colors[0][0], colors[0][1], colors[0][2]);
  background(0);
  fill(colors[1][0], colors[1][1], colors[1][2]);
  noStroke();
  //stroke(colors[1][0], colors[1][1], colors[1][2]);
  translate(0, 0, -700 + 300*sin(time()*0.001)*smoothAfter(time(), 10000));
  rotateX(sin(time()*0.001)*0.2 * smoothAfter(time(), 14000) );
  rotateY(sin(time()*0.000953)*0.2 * smoothAfter(time(), 14000));
  rotateZ(sin(time()*0.000573)*0.2 * smoothAfter(time(), 14000));
  for (int x = -5*2; x < 7*4; x++) {
    for (int y = -5*2; y < 6*4; y++) {
      float k = sin(1234.43257*x)*0.1;
      pushMatrix();
      fill(colors[1][0], colors[1][1], colors[1][2]);
      translate(65 + 120*x, 65 + 120*y, 0);
      rectMode(CENTER);
      if (time() >= 12000) rotateY(smoothBefore(time(), 20000) * smoothAfter(time(), 12000) * sin(time()*0.04245*k));
      if (time() >= 12000 && (x + 2*y) % 5 == 0) {
        int randomColor = 0 + mod((int)(time()*0.004 + 21*x + 421*y), 5);
        if (time() <= 8000) randomColor = 1;
        
        
        fill(colors[randomColor][0], colors[randomColor][1], colors[randomColor][2]);
        rotateX(smoothAfter(time(), 18000 + x*400) * smoothAfter(time(), 14000) * sin(time()*0.005245 + 12389.634624*x));
      }
      if (time() >= 22000 && (3*x + y) % 5 == 0) {
        int randomColor = 0 + mod((int)(time()*0.004 + 11*x + 5*y), 5);
        if (time() <= 8000) randomColor = 1;
        fill(colors[randomColor][0], colors[randomColor][1], colors[randomColor][2]);
        rotateZ(smoothAfter(time(), 22000 + y*400) * smoothAfter(time(), 22000) * sin(time()*0.005245 + 12389.634624*y));
      }
      if (time() <= 14000 && time() >= (6000 + 6000*sin(12345.5785*x*532.12412*y + 132.453252*x + 124648.8473*y))) {
        int randomColor = 0 + mod((int)(time()*0.004 + 11*x + 5*y), 5);
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

void draw() {
  /* esittäjän Kevin MacLeod kappale Ether Disco on suojattu lisenssillä Creative Commons Attribution (https://creativecommons.org/licenses/by/4.0/)
Lähde: http://incompetech.com/music/royalty-free/index.html?isrc=USUAN1100237
Esittäjä: http://incompetech.com/
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