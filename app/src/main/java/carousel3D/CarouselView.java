package carousel3D;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;

public class CarouselView extends Gallery {

    /**
     * Graphics Camera used for transforming the matrix of ImageViews
     */
	private Camera mCamera = new Camera();
	/**
	 * 	The maximum angle the Child ImageView will be rotated by
	 */    
	private int mMaxRotationAngle = 60; //60
		/**
	 * 	The Centre of the Coverflow 
	 */   
    private int mCoveflowCenter;
   
    public CarouselView(Context context) {
    	super(context);
    	this.setStaticTransformationsEnabled(true);
    }
    public CarouselView(Context context, AttributeSet attrs) {
    	super(context, attrs);
        this.setStaticTransformationsEnabled(true);
    }
 
    public CarouselView(Context context, AttributeSet attrs, int defStyle) {
    	super(context, attrs, defStyle);
    	this.setStaticTransformationsEnabled(true);   
    }
  
    /**
     * Get the max rotational angle of the image
     * @return the mMaxRotationAngle
     */
    public int getMaxRotationAngle() {
    	return mMaxRotationAngle;
    }

    /**
     * Set the max rotational angle of each image
     * @param maxRotationAngle the mMaxRotationAngle to set
     */
    public void setMaxRotationAngle(int maxRotationAngle) {
    	mMaxRotationAngle = maxRotationAngle;
    }


     protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    	 mCoveflowCenter = (getWidth() - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft();
    	 super.onSizeChanged(w, h, oldw, oldh);
     }

    protected boolean getChildStaticTransformation(View child, Transformation t) {
    	CarouselViewItem s = (CarouselViewItem)child;
    	final int childCenter = s.getLeft() + s.getMaxW()/2;
    	final int childWidth = s.getMaxW();
    	
    	int rotationAngle = 0;
  
    	t.clear();
    	t.setTransformationType(Transformation.TYPE_MATRIX);
 
        if (childCenter == mCoveflowCenter) {
        	transformImageBitmap(s, t, 0);
        } else {      
        	rotationAngle = (int) (((float) (mCoveflowCenter - childCenter)/ childWidth) *  mMaxRotationAngle);
            if (Math.abs(rotationAngle) > mMaxRotationAngle)
            	rotationAngle = (rotationAngle < 0) ? -mMaxRotationAngle : mMaxRotationAngle;   
            transformImageBitmap(s, t, rotationAngle);         
        }     
        return true;
    }

     private void transformImageBitmap(CarouselViewItem s, Transformation t, int rotationAngle) {
    	 mCamera.save();
    	 final Matrix imageMatrix = t.getMatrix();
    	 
    	 final int imageWidth = s.getMaxW();
    	 final int imageHeight = s.getMaxH();
    	
    	 final int rotation = Math.abs(rotationAngle);
         //As the angle of the view gets less, zoom in     
    	 float zoomAmount = (float) (rotation * 3);
    	 mCamera.translate(0.0f, 0.0f, zoomAmount);          
    	 
    	 mCamera.rotateY(rotationAngle);
    	 mCamera.getMatrix(imageMatrix);               
    	 imageMatrix.preTranslate(-(imageWidth/2), -(imageHeight/2)); 
    	 imageMatrix.postTranslate((imageWidth/2), (imageHeight/2));
    	 mCamera.restore();
     }
}
