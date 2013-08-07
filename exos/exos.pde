import controlP5.*;
import processing.pdf.*;

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

void setup() {
  size(1600,1024);
  smooth();
  controlP5 = new ControlP5(this);
  
  gridY = controlP5.addSlider("gridSizeY",5,100,27.0,20,0,100,15);  
  gridX = controlP5.addSlider("gridSizeX",5,100,27.0,20,20,100,15);
  percentGrid = controlP5.addSlider("percentageGrid",0.1,100,50,20,40,100,15);
  
  PVector tL, tR, bL, bR;
  
  tL = new PVector(20,200);
  tR = new PVector(tL.x+180,tL.y);
  bL = new PVector(tL.x, tL.y+200);
  bR = new PVector(tR.x, tL.y+200);  
  
  background_picker = controlP5.addColorPicker("background_color",20,100,100,15);
  
  toggleTopLeft = controlP5.addToggle("toggle_topleft",false,int(tL.x),int(tL.y-20),15,15);
  toggleTopLeft.setLabel("X");
  toggleTopLeft.captionLabel().style().marginTop = -15;
  toggleTopLeft.captionLabel().style().marginLeft = 20;
  sliderTopLeft = controlP5.addSlider("lineWeight_topleft",0.1,50,2,int(tL.x),int(tL.y),100,15);
  sliderTopLeft.setLabel("");
  topLeft = controlP5.addColorPicker("top_left",int(tL.x),int(tL.y+20),100,15);
  
  toggleTopRight = controlP5.addToggle("toggle_topright",false,int(tR.x),int(tR.y-20),15,15);
  toggleTopRight.setLabel("X"); 
  sliderTopRight = controlP5.addSlider("lineWeight_topright",0.1,50,2,int(tR.x),int(tR.y),100,15);
  sliderTopRight.setLabel("");
  topRight = controlP5.addColorPicker("top_right",int(tR.x),int(tR.y+20),100,15);
  toggleTopRight.captionLabel().style().marginTop = -15;
  toggleTopRight.captionLabel().style().marginLeft = 20;

  
  toggleBottomLeft = controlP5.addToggle("toggle_bottomleft",false,int(bL.x),int(bL.y-20),15,15);
  toggleBottomLeft.setLabel("X");
  sliderBottomLeft = controlP5.addSlider("lineWeight_bottomleft",0.1,50,2,int(bL.x),int(bL.y),100,15);
  sliderBottomLeft.setLabel("");
  bottomLeft = controlP5.addColorPicker("bottom_left",int(bL.x),int(bL.y+20),100,15);
  toggleBottomLeft.captionLabel().style().marginTop = -15;
  toggleBottomLeft.captionLabel().style().marginLeft = 20;

  
  toggleBottomRight = controlP5.addToggle("toggle_bottomright",false,int(bR.x),int(bR.y-20),15,15);
  toggleBottomRight.setLabel("X");
  sliderBottomRight = controlP5.addSlider("lineWeight_bottomright",0.1,50,2,int(bR.x),int(bR.y),100,15);
  sliderBottomRight.setLabel("");
  bottomRight = controlP5.addColorPicker("bottom_right",int(bR.x),int(bR.y+20),100,15);
  toggleBottomRight.captionLabel().style().marginTop = -15;
  toggleBottomRight.captionLabel().style().marginLeft = 20;

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

void draw() {
  
  if(record){
    beginRecord(PDF,"output" + recordFrame + ".pdf");
  }
  
  
  background(background_picker.getColorValue());
  
  float xStart = (width - globalGridWidth)/2.0;
  float yStart = (height - globalGridWidth)/2.0;
  
  float gridSpacingInterval = 0;
  
  if(gridSizeX>=gridSizeY){
    gridSpacingInterval = globalGridWidth/float(gridSizeX);  
  }
  else{
    gridSpacingInterval = globalGridWidth/float(gridSizeY);  
  }

  float gridDim = gridSpacingInterval * (percentageGrid/100);
  
  for(int i=0; i<gridSizeX; i++){
    for(int j=0;j<gridSizeY; j++){
    
      pushMatrix();
      
      translate(xStart + (i*gridSpacingInterval) + (gridSpacingInterval/2),yStart + (j*gridSpacingInterval) + (gridSpacingInterval/2));
      
      noStroke();
      
      float percent_x = float(i)/float(gridSizeX-1);
      float percent_y = float(j)/float(gridSizeY-1);
      
      
      color colorRowStart = lerpColor(topLeft.getColorValue(),bottomLeft.getColorValue(),percent_y);
      color colorRowEnd = lerpColor(topRight.getColorValue(),bottomRight.getColorValue(),percent_y);
      color currentColor = lerpColor(colorRowStart,colorRowEnd,percent_x);

      int topLeftMark = toggleTopLeft.getState()? (1==1?1:0): 0;
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

void keyPressed() {
  switch(key) {
    case('p'):
    record = true;
    break;

  }
}




