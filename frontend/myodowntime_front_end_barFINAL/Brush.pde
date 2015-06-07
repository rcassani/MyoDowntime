class Brush
{
  float xPos, yPos;
  float rot;
  float lenth;
  color col;

  Brush(float tempx, float tempy, float temprot, color tempcol)
  {
    xPos = tempx;
    yPos = tempy;
    rot = temprot;

    lenth = 5;
    col = tempcol;
  }
  void display()
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

