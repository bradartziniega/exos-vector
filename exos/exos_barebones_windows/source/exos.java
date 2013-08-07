import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import controlP5.*; 
import processing.pdf.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class exos extends PApplet {




ControlP5 controlP5;
ColorPicker topLeft, topRight, bottomLeft, bottomRight, background_picker;
Toggle toggleTopLeft, toggleTopRight, toggleBottomLeft, toggleBottomRight;
Slider sliderTopLeft, sliderTopRight, sliderBottomLeft, sliderBottomRight, gridX, gridY, percentGrid;
Toggle ccwTopLeft, ccwTopRight, ccwBottomLeft, ccwBottomRight;
Toggle isInteractive;

public int gridSizeX,gridSizeY;
public float lineWeight_topleft, lineWeight_topright, lineWeight_bottomleft, lineWeight_bottomright;
public float percentageGrid;
public float globalGridWidth = 1400;
public float numberboxValue = 100;

int recordFrame = 0;
boolean record;

float xStart, yStart;
float gridSpacingInterval;
float mousePercentX, mousePercentY;
float[] dim;
float[] dimLine;

boolean barebones = true;

public void setup() {
  
  size(1600,1024);
  smooth();
  controlP5 = new ControlP5(this);
  
  PVector tL, tR, bL, bR;
  
  tL = new PVector(20,200);
  tR = new PVector(tL.x+180,tL.y);
  bL = new PVector(tL.x, tL.y+200);
  bR = new PVector(tR.x, tL.y+200);  
  
  xStart = (width - globalGridWidth)/2.0f;
  yStart = (height - globalGridWidth)/2.0f;
  
  background_picker = controlP5.addColorPicker("background_color",20,100,100,15);
  
  //TOP LEFT
  //--------------------------------------------------------
  //X or + 
  toggleTopLeft = controlP5.addToggle("toggle_topleft",false,PApplet.parseInt(tL.x),PApplet.parseInt(tL.y-20),15,15);
  toggleTopLeft.setLabel("X");
  toggleTopLeft.captionLabel().style().marginTop = -15;
  toggleTopLeft.captionLabel().style().marginLeft = 20;
  //CW or CCW
  ccwTopLeft = controlP5.addToggle("toggle_topleftCCW",false,PApplet.parseInt(tL.x)+40,PApplet.parseInt(tL.y-20),15,15);
  ccwTopLeft.setLabel("CW");
  ccwTopLeft.captionLabel().style().marginTop = -15;
  ccwTopLeft.captionLabel().style().marginLeft = 20;
  //Lineweight
  sliderTopLeft = controlP5.addSlider("lineWeight_topleft",0.1f,50,2,PApplet.parseInt(tL.x),PApplet.parseInt(tL.y),100,15);
  sliderTopLeft.setLabel("");
  //Color
  topLeft = controlP5.addColorPicker("top_left",PApplet.parseInt(tL.x),PApplet.parseInt(tL.y+20),100,15);
  //--------------------------------------------------------
  
  //TOP RIGHT
  //--------------------------------------------------------
  //X or + 
  toggleTopRight = controlP5.addToggle("toggle_topright",false,PApplet.parseInt(tR.x),PApplet.parseInt(tR.y-20),15,15);
  toggleTopRight.setLabel("X"); 
  toggleTopRight.captionLabel().style().marginTop = -15;
  toggleTopRight.captionLabel().style().marginLeft = 20;
  //CW or CCW
  ccwTopRight = controlP5.addToggle("toggle_toprightCCW",false,PApplet.parseInt(tR.x)+40,PApplet.parseInt(tR.y-20),15,15);
  ccwTopRight.setLabel("CW"); 
  ccwTopRight.captionLabel().style().marginTop = -15;
  ccwTopRight.captionLabel().style().marginLeft = 20;
  //Lineweight
  sliderTopRight = controlP5.addSlider("lineWeight_topright",0.1f,50,2,PApplet.parseInt(tR.x),PApplet.parseInt(tR.y),100,15);
  sliderTopRight.setLabel("");
  //Color
  topRight = controlP5.addColorPicker("top_right",PApplet.parseInt(tR.x),PApplet.parseInt(tR.y+20),100,15);
  //--------------------------------------------------------
  
  //BOTTOM LEFT
  //--------------------------------------------------------
  //X or + 
  toggleBottomLeft = controlP5.addToggle("toggle_bottomleft",false,PApplet.parseInt(bL.x),PApplet.parseInt(bL.y-20),15,15);
  toggleBottomLeft.setLabel("X");
  toggleBottomLeft.captionLabel().style().marginTop = -15;
  toggleBottomLeft.captionLabel().style().marginLeft = 20;
  //CW or CCW
  ccwBottomLeft = controlP5.addToggle("toggle_bottomleftCCW",false,PApplet.parseInt(bL.x)+40,PApplet.parseInt(bL.y-20),15,15);
  ccwBottomLeft.setLabel("CW");
  ccwBottomLeft.captionLabel().style().marginTop = -15;
  ccwBottomLeft.captionLabel().style().marginLeft = 20;  
  //Lineweight
  sliderBottomLeft = controlP5.addSlider("lineWeight_bottomleft",0.1f,50,2,PApplet.parseInt(bL.x),PApplet.parseInt(bL.y),100,15);
  sliderBottomLeft.setLabel("");
  //Color
  bottomLeft = controlP5.addColorPicker("bottom_left",PApplet.parseInt(bL.x),PApplet.parseInt(bL.y+20),100,15);
  //--------------------------------------------------------

   //BOTTOM RIGHT
  //--------------------------------------------------------
  //X or + 
  toggleBottomRight = controlP5.addToggle("toggle_bottomright",false,PApplet.parseInt(bR.x),PApplet.parseInt(bR.y-20),15,15);
  toggleBottomRight.setLabel("X");
  toggleBottomRight.captionLabel().style().marginTop = -15;
  toggleBottomRight.captionLabel().style().marginLeft = 20;  
  //CW or CCW
  ccwBottomRight = controlP5.addToggle("toggle_bottomrightCCW",false,PApplet.parseInt(bR.x)+40,PApplet.parseInt(bR.y-20),15,15);
  ccwBottomRight.setLabel("CW");
  ccwBottomRight.captionLabel().style().marginTop = -15;
  ccwBottomRight.captionLabel().style().marginLeft = 20;    
  //Lineweight
  sliderBottomRight = controlP5.addSlider("lineWeight_bottomright",0.1f,50,2,PApplet.parseInt(bR.x),PApplet.parseInt(bR.y),100,15);
  sliderBottomRight.setLabel("");
  //Color
  bottomRight = controlP5.addColorPicker("bottom_right",PApplet.parseInt(bR.x),PApplet.parseInt(bR.y+20),100,15);
  //--------------------------------------------------------

  controlP5.setColorLabel(0xffffff);
  controlP5.enableShortcuts();
  
  isInteractive = controlP5.addToggle("toggle_interactive",false,20,20,15,15);
  isInteractive.setLabel("Interactive");
  isInteractive.captionLabel().style().marginTop = -15;
  isInteractive.captionLabel().style().marginLeft = 20;
  
  gridX = controlP5.addSlider("gridSizeX",5,100,27.0f,20,40,100,15);
  gridY = controlP5.addSlider("gridSizeY",5,100,27.0f,20,60,100,15);  
  percentGrid = controlP5.addSlider("percentageGrid",0.1f,100,50,20,80,100,15);
  
  background_picker.setColorValue(color(31,31,33,255));
  background_picker.setLabel("Background Color");
  
  initializeInteractiveModeSettings();
  
  if(barebones){
    controlP5.hide();
    isInteractive.setState(true);  
  }
}

public void draw() {
  
  if(record){
    beginRecord(PDF,"output" + recordFrame + ".pdf");
  }
  
  background(background_picker.getColorValue());
  
  gridSpacingInterval = 0;
  
  if(gridSizeX>=gridSizeY){
    gridSpacingInterval = globalGridWidth/PApplet.parseFloat(gridSizeX);  
  }
  else{
    gridSpacingInterval = globalGridWidth/PApplet.parseFloat(gridSizeY);  
  }

  float gridDim = gridSpacingInterval * (percentageGrid/100);
  
  xStart = (width - gridSizeX*gridSpacingInterval)/2.0f;
  yStart = (height - gridSizeY*gridSpacingInterval)/2.0f;
  
  
  
  for(int i=0; i<gridSizeX; i++){
    for(int j=0;j<gridSizeY; j++){
    
      pushMatrix();
      translate(xStart + (i*gridSpacingInterval) + (gridSpacingInterval/2),yStart + (j*gridSpacingInterval) + (gridSpacingInterval/2));
      noStroke();
      
      float percent_x = PApplet.parseFloat(i)/PApplet.parseFloat(gridSizeX-1);
      float percent_y = PApplet.parseFloat(j)/PApplet.parseFloat(gridSizeY-1);
      
      int colorRowStart = lerpColor(topLeft.getColorValue(),bottomLeft.getColorValue(),percent_y);
      int colorRowEnd = lerpColor(topRight.getColorValue(),bottomRight.getColorValue(),percent_y);
      int currentColor = lerpColor(colorRowStart,colorRowEnd,percent_x);

      int topLeftMark = toggleTopLeft.getState()? (ccwTopLeft.getState()? 1 :-1) : 0;
      int topRightMark = toggleTopRight.getState()? (ccwTopRight.getState()? 1 :-1) : 0;
      int bottomLeftMark = toggleBottomLeft.getState()? (ccwBottomLeft.getState()? 1 :-1) : 0;
      int bottomRightMark = toggleBottomRight.getState()? (ccwBottomRight.getState()? 1 :-1) : 0;
      
      if(isInteractive.getState()){   
        
        int currentQuadrant = 0;
        
        if(percent_x<=mousePercentX && percent_y<mousePercentY){
          currentQuadrant = 1;
        }
        else if(percent_x>mousePercentX && percent_y<mousePercentY){
          currentQuadrant = 2;
        }
        else if(percent_x<=mousePercentX && percent_y>=mousePercentY){
          currentQuadrant = 3;
        }
        else if(percent_x>mousePercentX && percent_y>=mousePercentY){
          currentQuadrant = 4;
        }

        float currentRotation = evaluateBiLinear(currentQuadrant,percent_x,percent_y,mousePercentX,mousePercentY,0,1);
        float lineWeight = evaluateBiLinear(currentQuadrant,percent_x,percent_y,mousePercentX,mousePercentY,lineWeight_topleft,lineWeight_topright);
        currentColor = lerpColor(topLeft.getColorValue(),topRight.getColorValue(),currentRotation);

        int index = j*gridSizeX + i;
        float dx = currentRotation - dim[index];
        float dL = lineWeight - dimLine[index];
        
        float closeness = sqrt((abs(mousePercentX-percent_x)*abs(mousePercentY-percent_y)));
        float easing = pow(1 - closeness,10);
        if (easing<=0.01f) easing = 0.01f;

        dim[index] += dx*easing;
        dimLine[index] += dL*easing;
  
        rotate(radians(dim[index]*45));
        fill(currentColor);
        lineWeight = dimLine[index];
        rect(-lineWeight/2,-gridDim/2,lineWeight,gridDim);
        rect(-gridDim/2,-lineWeight/2,gridDim,lineWeight);
      
      }
      
      else{
        float rotationRowStart = lerp(topLeftMark,bottomLeftMark,percent_y);
        float rotationRowEnd = lerp(topRightMark,bottomRightMark,percent_y);
        float currentRotation = lerp(rotationRowStart,rotationRowEnd,percent_x);
        float lineWeightRowStart = lerp(lineWeight_topleft,lineWeight_bottomleft,percent_y); 
        float lineWeightRowEnd = lerp(lineWeight_topright,lineWeight_bottomright,percent_y);
        float lineWeight = lerp(lineWeightRowStart,lineWeightRowEnd,percent_x);
        rotate(radians(currentRotation*45));
        fill(currentColor);            
        rect(-lineWeight/2,-gridDim/2,lineWeight,gridDim);
        rect(-gridDim/2,-lineWeight/2,gridDim,lineWeight);
      }
      
      popMatrix();
      
    }
  }
  if(record){
   endRecord();
   recordFrame++;
   record = false;
  }
}

public void initializeInteractiveModeSettings(){
  
  topLeft.setColorValue(color(137,61,255));
  topRight.setColorValue(color(0,255,43)); 
  percentGrid.setValue(68);
  gridX.setValue(43);
  gridY.setValue(30);
  sliderTopRight.setValue(6);
  sliderTopLeft.setValue(0.59f);
  sliderBottomLeft.setValue(0.59f);
  sliderBottomRight.setValue(0.59f);
  
  dim = new float[gridSizeX*gridSizeY];
  dimLine = new float[gridSizeX*gridSizeY];
  for(int i=0;i<gridSizeX;i++){
    for(int j=0;j<gridSizeY;j++){
      int index = j*gridSizeX + i;
      dim[index]=0;  
      dimLine[index]=0;
    }
  }
  
}

public void toggle_interactive(boolean theFlag){
 if(theFlag==true){
   initializeInteractiveModeSettings();
 }
 else{
   println("int disabled");
 }
}

public void controlEvent(ControlEvent theEvent){
  if(theEvent.controller().equals(gridX) || theEvent.controller().equals(gridY)){
    dim = new float[gridSizeX*gridSizeY];
  dimLine = new float[gridSizeX*gridSizeY];
  for(int i=0;i<gridSizeX;i++){
    for(int j=0;j<gridSizeY;j++){
      int index = j*gridSizeX + i;
      dim[index]=0;  
      dimLine[index]=0;
    }
  }
  }  
}


public float evaluateBiLinear(int quadrant,float percent_x,float percent_y,float mousePercentX,float mousePercentY,float valueToGoFrom, float valueToGetTo){
  float x1, x2, y1, y2, Q11, Q21, Q12, Q22;
  x1 = 0;
  x2 = 0;
  y1 = 0;
  y2 = 0;
  Q11 = 0;
  Q21 = 0;
  Q12 = 0;
  Q22 = 0;
  float x = percent_x;
  float y = percent_y;
  
  switch(quadrant){
   case(1):
    x1 = 0;
    x2 = mousePercentX;
    y1 = mousePercentY;
    y2 = 0;
    Q11 = valueToGoFrom;
    Q21 = valueToGetTo;
    Q22 = valueToGoFrom;
    Q12 = valueToGoFrom;
   break;
   case(2):
     x1 = mousePercentX;
     x2 = 1;
     y1 = mousePercentY;
     y2 = 0;
     Q11 = valueToGetTo;
     Q21 = valueToGoFrom;
     Q22 = valueToGoFrom;
     Q12 = valueToGoFrom;
   break;
   case(3):
     x1 = 0;
     x2 = mousePercentX;
     y1 = 1;
     y2 = mousePercentY;
     Q11 = valueToGoFrom;
     Q21 = valueToGoFrom;
     Q22 = valueToGetTo;
     Q12 = valueToGoFrom;
   break;
   case(4):
     x1 = mousePercentX;
     x2 = 1;
     y1 = 1;
     y2 = mousePercentY;
     Q11 = valueToGoFrom;
     Q21 = valueToGoFrom;
     Q22 = valueToGoFrom;
     Q12 = valueToGetTo;
   break;
  }
  float A = 1/((x2-x1)*(y2-y1));
  float B = (Q11*(x2-x)*(y2-y) + Q21*(x-x1)*(y2-y) + Q12*(x2-x)*(y-y1) + Q22*(x-x1)*(y-y1));
  return A*B;  
}

public void keyPressed() {
  switch(key) {
    case('p'):
    record = true;
    break;
  }
}

public boolean isInGrid(int xPos, int yPos){
 if(xPos>=xStart && xPos<=xStart+gridSizeX*gridSpacingInterval && yPos>=yStart && yPos<=yStart+gridSizeY*gridSpacingInterval){
   return true; 
 }
 else{
   return false;
 }
}

public void mouseMoved(){
  if(isInGrid(mouseX,mouseY)){
      mousePercentX = (mouseX-xStart)/(gridSizeX*gridSpacingInterval);
      mousePercentY = (mouseY-yStart)/(gridSizeY*gridSpacingInterval);
   }
  else{
    mousePercentX = .5f;
     mousePercentY = .5f;
   } 
}


  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "exos" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
