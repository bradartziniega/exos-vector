import controlP5.*;
import processing.pdf.*;

ControlP5 controlP5;
ColorPicker topLeft, topRight, bottomLeft, bottomRight, background_picker;
Toggle toggleTopLeft, toggleTopRight, toggleBottomLeft, toggleBottomRight;
Slider sliderTopLeft, sliderTopRight, sliderBottomLeft, sliderBottomRight, gridX, gridY, percentGrid;
Toggle ccwTopLeft, ccwTopRight, ccwBottomLeft, ccwBottomRight;
Toggle isInteractive;

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
  
  PVector tL, tR, bL, bR;
  
  tL = new PVector(20,200);
  tR = new PVector(tL.x+180,tL.y);
  bL = new PVector(tL.x, tL.y+200);
  bR = new PVector(tR.x, tL.y+200);  
  
  background_picker = controlP5.addColorPicker("background_color",20,100,100,15);
  
  //TOP LEFT
  //--------------------------------------------------------
  //X or + 
  toggleTopLeft = controlP5.addToggle("toggle_topleft",false,int(tL.x),int(tL.y-20),15,15);
  toggleTopLeft.setLabel("X");
  toggleTopLeft.captionLabel().style().marginTop = -15;
  toggleTopLeft.captionLabel().style().marginLeft = 20;
  //CW or CCW
  ccwTopLeft = controlP5.addToggle("toggle_topleftCCW",false,int(tL.x)+40,int(tL.y-20),15,15);
  ccwTopLeft.setLabel("CW");
  ccwTopLeft.captionLabel().style().marginTop = -15;
  ccwTopLeft.captionLabel().style().marginLeft = 20;
  //Lineweight
  sliderTopLeft = controlP5.addSlider("lineWeight_topleft",0.1,50,2,int(tL.x),int(tL.y),100,15);
  sliderTopLeft.setLabel("");
  //Color
  topLeft = controlP5.addColorPicker("top_left",int(tL.x),int(tL.y+20),100,15);
  //--------------------------------------------------------
  
  //TOP RIGHT
  //--------------------------------------------------------
  //X or + 
  toggleTopRight = controlP5.addToggle("toggle_topright",false,int(tR.x),int(tR.y-20),15,15);
  toggleTopRight.setLabel("X"); 
  toggleTopRight.captionLabel().style().marginTop = -15;
  toggleTopRight.captionLabel().style().marginLeft = 20;
  //CW or CCW
  ccwTopRight = controlP5.addToggle("toggle_toprightCCW",false,int(tR.x)+40,int(tR.y-20),15,15);
  ccwTopRight.setLabel("CW"); 
  ccwTopRight.captionLabel().style().marginTop = -15;
  ccwTopRight.captionLabel().style().marginLeft = 20;
  //Lineweight
  sliderTopRight = controlP5.addSlider("lineWeight_topright",0.1,50,2,int(tR.x),int(tR.y),100,15);
  sliderTopRight.setLabel("");
  //Color
  topRight = controlP5.addColorPicker("top_right",int(tR.x),int(tR.y+20),100,15);
  //--------------------------------------------------------
  
  //BOTTOM LEFT
  //--------------------------------------------------------
  //X or + 
  toggleBottomLeft = controlP5.addToggle("toggle_bottomleft",false,int(bL.x),int(bL.y-20),15,15);
  toggleBottomLeft.setLabel("X");
  toggleBottomLeft.captionLabel().style().marginTop = -15;
  toggleBottomLeft.captionLabel().style().marginLeft = 20;
  //CW or CCW
  ccwBottomLeft = controlP5.addToggle("toggle_bottomleftCCW",false,int(bL.x)+40,int(bL.y-20),15,15);
  ccwBottomLeft.setLabel("CW");
  ccwBottomLeft.captionLabel().style().marginTop = -15;
  ccwBottomLeft.captionLabel().style().marginLeft = 20;  
  //Lineweight
  sliderBottomLeft = controlP5.addSlider("lineWeight_bottomleft",0.1,50,2,int(bL.x),int(bL.y),100,15);
  sliderBottomLeft.setLabel("");
  //Color
  bottomLeft = controlP5.addColorPicker("bottom_left",int(bL.x),int(bL.y+20),100,15);
  //--------------------------------------------------------

   //BOTTOM RIGHT
  //--------------------------------------------------------
  //X or + 
  toggleBottomRight = controlP5.addToggle("toggle_bottomright",false,int(bR.x),int(bR.y-20),15,15);
  toggleBottomRight.setLabel("X");
  toggleBottomRight.captionLabel().style().marginTop = -15;
  toggleBottomRight.captionLabel().style().marginLeft = 20;  
  //CW or CCW
  ccwBottomRight = controlP5.addToggle("toggle_bottomrightCCW",false,int(bR.x)+40,int(bR.y-20),15,15);
  ccwBottomRight.setLabel("CW");
  ccwBottomRight.captionLabel().style().marginTop = -15;
  ccwBottomRight.captionLabel().style().marginLeft = 20;    
  //Lineweight
  sliderBottomRight = controlP5.addSlider("lineWeight_bottomright",0.1,50,2,int(bR.x),int(bR.y),100,15);
  sliderBottomRight.setLabel("");
  //Color
  bottomRight = controlP5.addColorPicker("bottom_right",int(bR.x),int(bR.y+20),100,15);
  //--------------------------------------------------------

  controlP5.setColorLabel(0xffffff);
  controlP5.enableShortcuts();
  
  isInteractive = controlP5.addToggle("toggle_interactive",false,20,20,15,15);
  isInteractive.setLabel("Interactive");
  isInteractive.captionLabel().style().marginTop = -15;
  isInteractive.captionLabel().style().marginLeft = 20;
  
  gridX = controlP5.addSlider("gridSizeX",5,100,27.0,20,40,100,15);
  gridY = controlP5.addSlider("gridSizeY",5,100,27.0,20,60,100,15);  
  percentGrid = controlP5.addSlider("percentageGrid",0.1,100,50,20,80,100,15);
  
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

      int topLeftMark = toggleTopLeft.getState()? (ccwTopLeft.getState()? 1 :-1) : 0;
      int topRightMark = toggleTopRight.getState()? (ccwTopRight.getState()? 1 :-1) : 0;
      int bottomLeftMark = toggleBottomLeft.getState()? (ccwBottomLeft.getState()? 1 :-1) : 0;
      int bottomRightMark = toggleBottomRight.getState()? (ccwBottomRight.getState()? 1 :-1) : 0;

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

void mouseMoved(){
 
  
  
}




