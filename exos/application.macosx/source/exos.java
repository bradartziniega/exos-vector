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

public int gridSizeX,gridSizeY;
public float lineWeight_topleft, lineWeight_topright, lineWeight_bottomleft, lineWeight_bottomright;
public float percentageGrid;
public float globalGridWidth = 1000;
public float numberboxValue = 100;

int recordFrame = 0;
boolean record;


public void setup() {
  size(1600,1024);
  smooth();
  controlP5 = new ControlP5(this);
  
  gridY = controlP5.addSlider("gridSizeY",5,100,27.0f,20,0,100,15);  
  gridX = controlP5.addSlider("gridSizeX",5,100,27.0f,20,20,100,15);
  percentGrid = controlP5.addSlider("percentageGrid",0.1f,100,50,20,40,100,15);
  
  PVector tL, tR, bL, bR;
  
  tL = new PVector(20,200);
  tR = new PVector(tL.x+180,tL.y);
  bL = new PVector(tL.x, tL.y+200);
  bR = new PVector(tR.x, tL.y+200);  
  
  background_picker = controlP5.addColorPicker("background_color",20,100,100,15);
  
  toggleTopLeft = controlP5.addToggle("toggle_topleft",false,PApplet.parseInt(tL.x),PApplet.parseInt(tL.y-20),15,15);
  toggleTopLeft.setLabel("");
  sliderTopLeft = controlP5.addSlider("lineWeight_topleft",0.1f,50,2,PApplet.parseInt(tL.x),PApplet.parseInt(tL.y),100,15);
  sliderTopLeft.setLabel("Line Weight");
  topLeft = controlP5.addColorPicker("top_left",PApplet.parseInt(tL.x),PApplet.parseInt(tL.y+20),100,15);
  
  toggleTopRight = controlP5.addToggle("toggle_topright",false,PApplet.parseInt(tR.x),PApplet.parseInt(tR.y-20),15,15);
  toggleTopRight.setLabel(""); 
  sliderTopRight = controlP5.addSlider("lineWeight_topright",0.1f,50,2,PApplet.parseInt(tR.x),PApplet.parseInt(tR.y),100,15);
  sliderTopRight.setLabel("Line Weight");
  topRight = controlP5.addColorPicker("top_right",PApplet.parseInt(tR.x),PApplet.parseInt(tR.y+20),100,15);
  
  toggleBottomLeft = controlP5.addToggle("toggle_bottomleft",false,PApplet.parseInt(bL.x),PApplet.parseInt(bL.y-20),15,15);
  toggleBottomLeft.setLabel("");
  sliderBottomLeft = controlP5.addSlider("lineWeight_bottomleft",0.1f,50,2,PApplet.parseInt(bL.x),PApplet.parseInt(bL.y),100,15);
  sliderBottomLeft.setLabel("Line Weight");
  bottomLeft = controlP5.addColorPicker("bottom_left",PApplet.parseInt(bL.x),PApplet.parseInt(bL.y+20),100,15);
  
  toggleBottomRight = controlP5.addToggle("toggle_bottomright",false,PApplet.parseInt(bR.x),PApplet.parseInt(bR.y-20),15,15);
  toggleBottomRight.setLabel("");
  sliderBottomRight = controlP5.addSlider("lineWeight_bottomright",0.1f,50,2,PApplet.parseInt(bR.x),PApplet.parseInt(bR.y),100,15);
  sliderBottomRight.setLabel("Line Weight");
  bottomRight = controlP5.addColorPicker("bottom_right",PApplet.parseInt(bR.x),PApplet.parseInt(bR.y+20),100,15);

  controlP5.setColorLabel(0xffffff);
  controlP5.enableShortcuts();
  
  background_picker.setColorValue(color(31,31,33,255));
  background_picker.setLabel("Background Color");
  
  percentGrid.setValue(50);
  gridX.setValue(25);
  gridY.setValue(25);
  sliderTopLeft.setValue(2);
  sliderTopRight.setValue(2);
  sliderBottomLeft.setValue(2);
  sliderBottomRight.setValue(2);
  
}

public void draw() {
  
  if(record){
    beginRecord(PDF,"output" + recordFrame + ".pdf");
  }
  
  
  background(background_picker.getColorValue());
  
  float xStart = (width - globalGridWidth)/2.0f;
  float yStart = (height - globalGridWidth)/2.0f;
  
  float gridSpacingInterval = 0;
  
  if(gridSizeX>=gridSizeY){
    gridSpacingInterval = globalGridWidth/PApplet.parseFloat(gridSizeX);  
  }
  else{
    gridSpacingInterval = globalGridWidth/PApplet.parseFloat(gridSizeY);  
  }

  float gridDim = gridSpacingInterval * (percentageGrid/100);
  
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

      int topLeftMark = toggleTopLeft.getState()? 1 : 0;
      int topRightMark = toggleTopRight.getState()? 1 : 0;
      int bottomLeftMark = toggleBottomLeft.getState()? 1 : 0;
      int bottomRightMark = toggleBottomRight.getState()? 1 : 0;

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
      
      popMatrix();
      
    }
  }
    
  if(record){
   endRecord();
   recordFrame++;
   record = false;
  }
  
}

public void keyPressed() {
  switch(key) {
    case('p'):
    record = true;
    break;

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
