package org.firstinspires.ftc.teamcode;


/**
 * A basic 2D Vector class.  It currently only has basic vector
 * addition, subtraction, and scalar multiplication and division.
 * It also has rotation, magnitude, and unit vectors.
 */
public class Vector2{
    public double x;
    public double y;


    /**
     * Create a new vector.
     * @param x The x component.
     * @param y The y component.
     */
    public Vector2(double x,double y){
        this.x=x;
        this.y=y;
    }


    public void addAssign(Vector2 other){
        x+=other.x;
        y+=other.y;
    }

    /**
     * Add 2 vectors.
     * @param other The vector to add.
     * @return the newly added vector
     */
    public Vector2 add(Vector2 other){
        return new Vector2(x+other.x,y+other.y);
    }

    public void subAssign(Vector2 other){
        x-=other.x;
        y-=other.y;
    }

    /**
     * Subtract 2 vectors
     * @param other the vector to subtract.
     * @return The newly subtracted vector.
     */
    public Vector2 sub(Vector2 other){
        return new Vector2(x-other.x,y-other.y);
    }

    public void multAssign(double other){
        x*=other;
        y*=other;
    }

    /**
     * Multiply a vector by a scalar.
     * @param other the number to multiply by.
     * @return the multiplied vector.
     */
    public Vector2 mult(double other){
        return new Vector2(x*other,y*other);
    }

    public void divAssign(double other){
        x/=other;
        y/=other;
    }

    /**
     * Divide a vector by a scalar.
     * @param other the number to divide by.
     * @return The divided vector
     */
    public Vector2 div(double other){
        return new Vector2(x/other,y/other);
    }

    /**
     * Get the magnitude of the vector.
     * @return The magnitude of the vector.
     */
    public double mag(){
        return Math.sqrt((x*x)+(y*y));
    }

    /**
     * Get the unit vector.
     * @return A new vector with a magnitude of 1.
     */
    public Vector2 unit(){
        if(this.mag()==0){return new Vector2(0,0);} // prevent divide by 0 error
        return this.div(this.mag());
    }

    /**
     * Set the magnitude of a vector.
     * @param mag The magnitude to set
     * @return The updated vector
     */
    public Vector2 setMag(double mag){
        return this.unit().mult(mag);
    }

    /**
     * Clamp a vector's magnitude to a min and max value.
     * @param min the min value
     * @param max the max value
     * @return the clamped vector
     */
    public Vector2 clamp(double min,double max){


        Vector2 temp=this;
        if (temp.mag()>max){
            temp.setMag(max);
        }
        if (temp.mag()<Util.clamp(min,0,max)){
            temp.setMag(min);
        }
        return temp;
    }


    /**
     * Rotate a vector counterclockwise.
     * @param rad The amount to rotate, in radians.
     * @return The rotated vector.
     */
    public Vector2 rotate(double rad){
        double x=Math.cos(rad)*this.x - Math.sin(rad)*this.y;
        double y=Math.sin(rad)*this.x + Math.cos(rad)*this.y;
        return new Vector2(x,y);
    }
}
