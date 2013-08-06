import controlP5.*;
import processing.pdf.*;

ControlP5 controlP5;
ColorPicker topLeft,topRight,bottomLeft,bottomRight;
Toggle toggleTopLeft, toggleTopRight, toggleBottomLeft, toggleBottomRight;
Slider sliderTopLeft, sliderTopRight, sliderBottomLeft, sliderBottomRight;

public int gridSize,gridSizeY;
public float lineWeight_topleft, lineWeight_topright, lineWeight_bottomleft, lineWeight_bottomright;
public float percentageGrid;
public float globalGridWidth = 1000;
public float numberboxValue = 100;


boolean record;


void setup() {
  size(1600,1024);
  smooth();
  controlP5 = new ControlP5(this);
    
  controlP5.addSlider("gridSize",5,100,27.0,20,20,100,15);
  controlP5.addSlider("gridSizeY",5,100,27.0,20,0,100,15);
  controlP5.addSlider("percentageGrid",0.1,100,20,40,100,15);
  
  PVector tL, tR, bL, bR;
  
  tL = new PVector(20,100);
  tR = new PVector(200,100);
  bL = new PVector(20,300);
  bR = new PVector(200, 300);  
  
  toggleTopLeft = controlP5.addToggle("toggle_topleft",false,int(tL.x),int(tL.y-20),15,15);
  toggleTopLeft.setLabel("");
  sliderTopLeft = controlP5.addSlider("lineWeight_topleft",0.1,50,2,int(tL.x),int(tL.y),100,15);
  sliderTopLeft.setLabel("Line Weight");
  topLeft = controlP5.addColorPicker("top_left",int(tL.x),int(tL.y+20),100,15);
  
  toggleTopRight = controlP5.addToggle("toggle_topright",false,int(tR.x),int(tR.y-20),15,15);
  toggleTopRight.setLabel(""); 
  sliderTopRight = controlP5.addSlider("lineWeight_topright",0.1,50,2,int(tR.x),int(tR.y),100,15);
  sliderTopRight.setLabel("Line Weight");
  topRight = controlP5.addColorPicker("top_right",int(tR.x),int(tR.y+20),100,15);
  
  toggleBottomLeft = controlP5.addToggle("toggle_bottomleft",false,int(bL.x),int(bL.y-20),15,15);
  toggleBottomLeft.setLabel("");
  sliderBottomLeft = controlP5.addSlider("lineWeight_bottomleft",0.1,50,2,int(bL.x),int(bL.y),100,15);
  sliderBottomLeft.setLabel("Line Weight");
  bottomLeft = controlP5.addColorPicker("bottom_left",int(bL.x),int(bL.y+20),100,15);
  
  toggleBottomRight = controlP5.addToggle("toggle_bottomright",false,int(bR.x),int(bR.y-20),15,15);
  toggleBottomRight.setLabel("");
  sliderBottomRight = controlP5.addSlider("lineWeight_bottomright",0.1,50,2,int(bR.x),int(bR.y),100,15);
  sliderBottomRight.setLabel("Line Weight");
  bottomRight = controlP5.addColorPicker("bottom_right",int(bR.x),int(bR.y+20),100,15);

  controlP5.setColorLabel(0x000000);
  controlP5.enableShortcuts();
  
}

void draw() {
  
  if(record){
    beginRecord(PDF,"test.pdf");
  }
  
  
  background(31,31,33);
  
  float xStart = (width - globalGridWidth)/2.0;
  float yStart = (height - globalGridWidth)/2.0;
  
  float gridSpacingInterval = 0;
  
  if(gridSize>=gridSizeY){
    gridSpacingInterval = globalGridWidth/float(gridSize);  
  }
  else{
    gridSpacingInterval = globalGridWidth/float(gridSizeY);  
  }

  float gridDim = gridSpacingInterval * (percentageGrid/100);
  
  for(int i=0; i<gridSize; i++){
    for(int j=0;j<gridSize; j++){
    
      pushMatrix();
      
      translate(xStart + (i*gridSpacingInterval) + (gridSpacingInterval/2),yStart + (j*gridSpacingInterval) + (gridSpacingInterval/2));
      
      noStroke();
      
      float percent_x = float(i)/float(gridSize-1);
      float percent_y = float(j)/float(gridSize-1);
      
      
      color colorRowStart = lerpColor(topLeft.getColorValue(),bottomLeft.getColorValue(),percent_y);
      color colorRowEnd = lerpColor(topRight.getColorValue(),bottomRight.getColorValue(),percent_y);
      color currentColor = lerpColor(colorRowStart,colorRowEnd,percent_x);

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
   record = false;
  }
  
}

void keyPressed() {
  switch(key) {
    case('p'):
    record = true;
    break;
    case('l'):
    controlP5.load("exos.properties");
    break;
    case('s'):
    controlP5.save("exos.properties");
    break;
  }
}




