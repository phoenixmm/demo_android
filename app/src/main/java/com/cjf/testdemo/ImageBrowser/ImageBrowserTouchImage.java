package com.cjf.testdemo.ImageBrowser;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;


public class ImageBrowserTouchImage extends ImageView {

	static final float THRESHOLD_DISTANCE = 10.0f; //use as base threshold distance for some action trigger.
	static final int THRESHOLD_CLICK = 10; //use for double press in DOUBLE_PRESS_INTERVAL time
	static final long DOUBLE_PRESS_INTERVAL = 600; //use for double press
	static final float FRICTION = 0.9f; //for smooth zoom draw according to gesture velocity.
	static final float MAX_SCALE = 5; //for control max width scale value.


	// We can be in one of these 3 states
	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	int mode = NONE;

	float right, bottom; //last bitmap/drawable Show size Padding(not including redundantXSpace/redundantYSpace * saveScale)
	float origWidth, origHeight; //origin bitmap/drawable Show size
	float redundantXSpace, redundantYSpace; //origin bitmap/drawable Show top/left shift
	float bmWidth, bmHeight; //origin bitmap/drawable size.
	float width, height; //imageview size.

	PointF last = new PointF(); //last touch x,y
	PointF lastDelta = new PointF(0, 0); //last touch delta
	//	PointF mid = new PointF(); //use for no GestureDetector support os version to deal with zoom.
	PointF start = new PointF(); //first down pointer event x,y

	Matrix matrix = new Matrix(); //imageview matrix.
//	Matrix savedMatrix = new Matrix();
	float[] m; //var just help for getting matrixX, matrixY
	float matrixX, matrixY; //translate x,y value.

	float saveScale = 1f; //last scale value.
	float minScale = 1f; //min scale multiple for origin scale to plus.
	float maxScale = 2f; //max scale multiple for origin scale to plus.
	float oldDist = 1f; //multiple down pointer distance.

	float velocity = 0; //for smooth zoom draw according to gesture velocity.

	long lastDragTime = 0; //last touch time in drag mode
	boolean allowInert = false; //custom draw intercept.

	private Context mContext;
	private ScaleGestureDetector mScaleDetector;
//	float mDensity = 0f;

//	long lastPressTime = 0; //use for double press
//	private Timer mClickTimer;//use for double press
//	private OnClickListener mOnClickListener;
//	private Handler mTimerHandler = null;//use for double press

	// Scale mode on DoubleTap
//	private boolean zoomToOriginalSize = false;
//
//	public boolean isZoomToOriginalSize() {
//		return this.zoomToOriginalSize;
//	}
//
//	public void setZoomToOriginalSize(boolean zoomToOriginalSize) {
//		this.zoomToOriginalSize = zoomToOriginalSize;
//	}

	public boolean onLeftSide = false, onTopSide = false, onRightSide = false,
			onBottomSide = false; //use for drag to side sta


	public ImageBrowserTouchImage(Context context) {
		super(context);
		super.setClickable(true);
		this.mContext = context;

		init();
	}

	public ImageBrowserTouchImage(Context context, AttributeSet attrs) {
		super(context, attrs);
		super.setClickable(true);
		this.mContext = context;

		init();
	}

	private void init() {
//		mTimerHandler = new TimeHandler(this);
		matrix.setTranslate(1f, 1f);
		m = new float[9];
		setImageMatrix(matrix);
		setScaleType(ScaleType.MATRIX);
		Log.d("cjf---", " init matrix = " + matrix);
		mScaleDetector = new ScaleGestureDetector(mContext, new ScaleListener());
		DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
		//TODO can change according to design.
//		mDensity = displayMetrics.density;

	}

	public void resetScale() {
		fillMatrixXY();
		matrix.postScale(minScale / saveScale, minScale / saveScale, width / 2,
				height / 2);
		saveScale = minScale;

		calcPadding();
		checkAndSetTranslate(0, 0);

		scaleMatrixToBounds();

		setImageMatrix(matrix);
		invalidate();
	}

	public boolean pagerCanScroll() {
		if (mode != NONE)
			return false;
		return saveScale == minScale;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Log.d("cjf---", " onDraw matrix = " + matrix);

		super.onDraw(canvas);

		if (!allowInert) { //not in any drag or touch mode, no need to change img matrix, just return.
			return;
		}

		// for smooth translate
		final float deltaX = lastDelta.x * velocity;
		final float deltaY = lastDelta.y * velocity;
		velocity *= FRICTION;

		if (deltaX > width || deltaY > height) {
			return;
		}

		if (Math.abs(deltaX) < 0.1 && Math.abs(deltaY) < 0.1) {
			return;
		}

		checkAndSetTranslate(deltaX, deltaY);
		Log.d("cjf---", " onDraw 2 matrix = " + matrix);

		setImageMatrix(matrix);
		Log.d("cjf---", " onDraw 3 matrix = " + matrix);

	}

	private void checkAndSetTranslate(float deltaX, float deltaY) {
		Log.d("cjf---", " checkAndSetTranslate matrix = " + matrix);

		float scaleWidth = Math.round(origWidth * saveScale);
		float scaleHeight = Math.round(origHeight * saveScale);
		fillMatrixXY();

		if(scaleWidth < width && scaleHeight < height) {
			checkSiding();

			return;
		}

		if (scaleWidth < width) {
			deltaX = 0;
			if (matrixY + deltaY > 0) {
				deltaY = -matrixY;//set translate y to zero, that is bitmap's top
			} else if (matrixY + deltaY < -bottom) {
				deltaY = -(matrixY + bottom);//set translater y to -bottom, that is bitmap's bottom
			}
		} else if (scaleHeight < height) {
			deltaY = 0;
			if (matrixX + deltaX > 0) {
				deltaX = -matrixX;
			} else if (matrixX + deltaX < -right) {
				deltaX = -(matrixX + right);
			}
		} else {
			if (matrixX + deltaX > 0) {
				deltaX = -matrixX;
			} else if (matrixX + deltaX < -right) {
				deltaX = -(matrixX + right);
			}

			if (matrixY + deltaY > 0) {
				deltaY = -matrixY;
			} else if (matrixY + deltaY < -bottom) {
				deltaY = -(matrixY + bottom);
			}
		}
		matrix.postTranslate(deltaX, deltaY);
		Log.d("cjf---", " checkAndSetTransfer 2 matrix = " + matrix);

		checkSiding();
	}

	private void checkSiding() {
		fillMatrixXY();
		Log.d("cjf---", " checkSiding x: " + matrixX + " y: " + matrixY
				 + " left: " + right / 2 + " top:" + bottom / 2);

		float scaleWidth = Math.round(origWidth * saveScale);
		float scaleHeight = Math.round(origHeight * saveScale);
		onLeftSide = onRightSide = onTopSide = onBottomSide = false;

		if (-matrixX < THRESHOLD_DISTANCE) {
			onLeftSide = true;
		}

		if ((scaleWidth >= width && (matrixX + scaleWidth - width) < THRESHOLD_DISTANCE)
				|| (scaleWidth <= width && -matrixX + scaleWidth <= width)) {
			onRightSide = true;
		}

		if (-matrixY < THRESHOLD_DISTANCE) {
			onTopSide = true;
		}

		if (Math.abs(-matrixY + height - scaleHeight) < THRESHOLD_DISTANCE) {
			onBottomSide = true;
		}

		Log.d("cjf---", " checkSiding onLeftSide: " + onLeftSide + " onRightSide: " + onRightSide
				+ " onTopSide: " + onTopSide + " onBottomSide:" + onBottomSide);

	}

	private void calcPadding() {//bitmap size that
		right = width * saveScale - width - (2 * redundantXSpace * saveScale);
		bottom = height * saveScale - height
				- (2 * redundantYSpace * saveScale);
	}

	private void fillMatrixXY() {
		matrix.getValues(m);
		matrixX = m[Matrix.MTRANS_X];
		matrixY = m[Matrix.MTRANS_Y];
	}

	private void scaleMatrixToBounds() { //make sure zoom in will not
		if (Math.abs(matrixX + right / 2) > 0.5f) {
			matrix.postTranslate(-(matrixX + right / 2), 0);
		}
		if (Math.abs(matrixY + bottom / 2) > 0.5f) {
			matrix.postTranslate(0, -(matrixY + bottom / 2));
		}
	}

	@Override
	public void setImageBitmap(Bitmap bm) {
		Log.d("cjf---", " setImageBitmap matrix = " + matrix);

		super.setImageBitmap(bm);

		if(bmWidth <= 0) {
			bmWidth = bm.getWidth();
			bmHeight = bm.getHeight();
			Log.d("cjf---", " setImageBitmap bmWidth = " + bmWidth);
			Log.d("cjf---", " setImageBitmap bmHeight = " + bmHeight);
		}
	}

	@Override
	public void setImageDrawable(Drawable drawable) {
		Log.d("cjf---", " setImageDrawable matrix = " + matrix);

		if(drawable == null) {
			return;
		}

		Log.d("cjf---", " setImageDrawable getIntrinsicWidth = " + drawable.getIntrinsicWidth());
		Log.d("cjf---", " setImageDrawable getIntrinsicHeight = " + drawable.getIntrinsicHeight());

		super.setImageDrawable(drawable);

//		if(bmWidth <= 0) {
			bmWidth = drawable.getIntrinsicWidth();
			bmHeight = drawable.getIntrinsicHeight();
//			Log.d("cjf---", " setImageDrawable bmWidth = " + bmWidth);
//			Log.d("cjf---", " setImageDrawable bmHeight = " + bmHeight);
//		}

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		Log.d("cjf---", " onMeasure 11 matrix = " + matrix);

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		width = MeasureSpec.getSize(widthMeasureSpec);
		height = MeasureSpec.getSize(heightMeasureSpec);
		Log.d("cjf---", " onMeasure 11 width = " + width);
		Log.d("cjf---", " onMeasure 11 height = " + height);
		Log.d("cjf---", " onMeasure 11 bmWidth = " + bmWidth);
		Log.d("cjf---", " onMeasure 11 bmHeight = " + bmHeight);

		if(bmWidth <= 0 || bmHeight <= 0) {
			return;
		}
		// Fit to screen.
		float scale;
		float scaleX = width / bmWidth;
		float scaleY = height / bmHeight;
		//TODO we just try match width to screen-width.

//		scale = Math.min(Math.min(scaleX, scaleY), mDensity);
		scale = Math.min(scaleX, MAX_SCALE);

		Log.d("cjf---", " onMeasure 11 scaleX = " + scaleX);
		Log.d("cjf---", " onMeasure 11 scaleY = " + scaleY);
		Log.d("cjf---", " onMeasure 11 scale = " + scale);

		matrix.setScale(scale, scale);
		Log.d("cjf---", " onMeasure 12 matrix = " + matrix);

		setImageMatrix(matrix);
		saveScale = 1;

		// Center the image
		redundantYSpace = height - (scale * bmHeight);
		redundantXSpace = width - (scale * bmWidth);
		redundantYSpace /= (float) 2;
		redundantXSpace /= (float) 2;
		Log.d("cjf---", " onMeasure 12 redundantXSpace = " + redundantXSpace);
		Log.d("cjf---", " onMeasure 12 redundantYSpace = " + redundantYSpace);

		matrix.postTranslate(redundantXSpace, redundantYSpace);
		Log.d("cjf---", " onMeasure 13 matrix = " + matrix);

		origWidth = width - 2 * redundantXSpace;
		origHeight = height - 2 * redundantYSpace;
		Log.d("cjf---", " onMeasure 13 origWidth = " + origWidth);
		Log.d("cjf---", " onMeasure 13 origHeight = " + origHeight);

		calcPadding();
		setImageMatrix(matrix);
		Log.d("cjf---", " onMeasure 14 matrix = " + matrix);

	}

	private double distanceBetween(PointF left, PointF right) {
		return Math.sqrt(Math.pow(left.x - right.x, 2)
				+ Math.pow(left.y - right.y, 2));
	}

	/** Determine the space between the first two fingers */
	private float spacing(MotionEvent event) {
		// ...
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return (float) Math.sqrt(x * x + y * y);
	}

	/** Calculate the mid point of the first two fingers */
	private void midPoint(PointF point, MotionEvent event) {
		// ...
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}

	private PointF midPointF(MotionEvent event) {
		// ...
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		return new PointF(x / 2, y / 2);
	}

//	@Override
//	public void setOnClickListener(OnClickListener l) {
//		mOnClickListener = l;
//	}

//	private class Task extends TimerTask {
//		public void run() {
//			mTimerHandler.sendEmptyMessage(0);
//		}
//	}

	@SuppressLint("NewApi")
	private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
		@Override
		public boolean onScaleBegin(ScaleGestureDetector detector) {
			Log.d("cjf---", " onScaleBegin matrix = " + matrix);

			mode = ZOOM;
			return true;
		}

		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			Log.d("cjf---", " onScale matrix = " + matrix);

			float mScaleFactor = (float) Math.min(
					Math.max(.95f, detector.getScaleFactor()), 1.05);
			float origScale = saveScale;
			saveScale *= mScaleFactor;
			if (saveScale > maxScale) {
				saveScale = maxScale;
				mScaleFactor = maxScale / origScale;
			} else if (saveScale < minScale) {
				saveScale = minScale;
				mScaleFactor = minScale / origScale;
			}
			Log.d("cjf---", " onScale 0 mScaleFactor = " + mScaleFactor);

			//just like calcPadding() function.
			right = width * saveScale - width
					- (2 * redundantXSpace * saveScale);//img width - imgview width after zoom.(>0 img width big than screen)
			bottom = height * saveScale - height
					- (2 * redundantYSpace * saveScale);

			Log.d("cjf---", " onScale 0 right = " + right);
			Log.d("cjf---", " onScale 0 bottom = " + bottom);

			if (origWidth * saveScale <= width
					|| origHeight * saveScale <= height) {
				matrix.postScale(mScaleFactor, mScaleFactor,
						width / 2, height / 2);

				if (mScaleFactor < 1) {
					matrix.getValues(m);
					float x = m[Matrix.MTRANS_X]; // img left lift (>0 img top in screen)
					float y = m[Matrix.MTRANS_Y];
					Log.d("cjf---", " onScale 1 x = " + x);
					Log.d("cjf---", " onScale 1 y = " + y);

					if (y < -bottom && bottom > 0 ) {//img big than screen, but bottom in screen
						matrix.postTranslate(0, -(y + bottom));
					} else if (y > 0 && bottom > 0) {//img big than screen, but top in screen
						matrix.postTranslate(0, -y);
					}

					if (x < -right && right > 0) {//img big than screen, but right in screen
						matrix.postTranslate(-(x + right), 0);
					} else if (x > 0 && right > 0) {//img big than screen, but left in screen
						matrix.postTranslate(-x, 0);
					}

					matrix.getValues(m);
					x = m[Matrix.MTRANS_X];
					y = m[Matrix.MTRANS_Y];
					Log.d("cjf---", " onScale 2 x = " + x);
					Log.d("cjf---", " onScale 2 y = " + y);

//					if (mScaleFactor < 1) {//correct tanslate x,y, that may error after zoom in.
//						if (Math.round(origWidth * saveScale) < width) {
//							if (y < -bottom) {
//								matrix.postTranslate(0, -(y + bottom));
//							} else if (y > 0) {
//								matrix.postTranslate(0, -y);
//							}
//						} else {
//							if (x < -right) {
//								matrix.postTranslate(-(x + right), 0);
//							} else if (x > 0) {
//								matrix.postTranslate(-x, 0);
//							}
//						}
//					}
				}
			} else {
				matrix.postScale(mScaleFactor, mScaleFactor,
						detector.getFocusX(), detector.getFocusY());
				Log.d("cjf---", " onScale detector.getFocusX() = " + detector.getFocusX());
				Log.d("cjf---", " onScale detector.getFocusY() = " + detector.getFocusY());

				matrix.getValues(m);
				float x = m[Matrix.MTRANS_X];
				float y = m[Matrix.MTRANS_Y];
				if (mScaleFactor < 1) {
					if (x < -right)
						matrix.postTranslate(-(x + right), 0);
					else if (x > 0)
						matrix.postTranslate(-x, 0);
					if (y < -bottom)
						matrix.postTranslate(0, -(y + bottom));
					else if (y > 0)
						matrix.postTranslate(0, -y);
				}
			}

			Log.d("cjf---", " onScale 2 matrix = " + matrix);
			return true;

		}
	}

//	static class TimeHandler extends Handler {
//		private final WeakReference<ImageBrowserTouchImage> mService;
//
//		TimeHandler(ImageBrowserTouchImage view) {
//			mService = new WeakReference<ImageBrowserTouchImage>(view);
//
//		}
//
//		@Override
//		public void handleMessage(Message msg) {
//			mService.get().performClick();
//			if (mService.get().mOnClickListener != null)
//				mService.get().mOnClickListener.onClick(mService.get());
//		}
//	}

	// public interface SetOnToch {
	// public void onSetOnTochLeft();
	// public void onSetOnTochRight();
	// public void onSetOnTochCentre();
	// }


	@Override
	public boolean onTouchEvent(MotionEvent event) {
//		return super.onTouchEvent(event);


		Log.w("cjf---", "image onTouchEvent --> " + event.getAction()
				+ " getPointerCount=" + event.getPointerCount()
				+ " getActionIndex=" + event.getActionIndex());

		Log.w("cjf---", "image onTouchEvent --> " + event.getAction()
				+ " x=" + event.getX() + " y=" + event.getY());

//				WrapMotionEvent event = WrapMotionEvent.wrap(rawEvent);
		if (mScaleDetector != null) {
			mScaleDetector.onTouchEvent(event);
		}

		fillMatrixXY();
		PointF curr = new PointF(event.getX(), event.getY());

		switch (event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				allowInert = false;
//				savedMatrix.set(matrix);
				last.set(event.getX(), event.getY());
				start.set(last);
				mode = DRAG;
				Log.d("cjf---", "set mode = DRAG");
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				oldDist = spacing(event);
				 Log.d("", "oldDist=" + oldDist);
				if (oldDist > THRESHOLD_DISTANCE) {
//					savedMatrix.set(matrix);
//					midPoint(mid, event);
					mode = ZOOM;
					Log.d("cjf---", "set mode = ZOOM");
				}
				break;
			case MotionEvent.ACTION_UP:
				allowInert = true;
				mode = NONE;
				Log.d("cjf---", "set mode = NONE");
				int xDiff = (int) Math.abs(event.getX() - start.x);
				int yDiff = (int) Math.abs(event.getY() - start.y);

//				if (xDiff < THRESHOLD_CLICK && yDiff < THRESHOLD_CLICK) {
//
//					// Perform scale on double click
//					long pressTime = System.currentTimeMillis();
//					if (pressTime - lastPressTime <= DOUBLE_PRESS_INTERVAL) {
////							if (mClickTimer != null)
////								mClickTimer.cancel();
//						if (saveScale == 1) {
//							final float targetScale = maxScale / saveScale;
//							matrix.postScale(targetScale, targetScale,
//									start.x, start.y);
//							saveScale = maxScale;
//						} else {
//							matrix.postScale(minScale / saveScale, minScale
//									/ saveScale, width / 2, height / 2);
//							saveScale = minScale;
//						}
//						calcPadding();
//						checkAndSetTranslate(0, 0);
//						lastPressTime = 0;
//					} else {
//						lastPressTime = pressTime;
////							mClickTimer = new Timer();
////							mClickTimer.schedule(new Task(), 300);
//					}
//					if (saveScale == minScale) {
//						scaleMatrixToBounds();
//					}
//				}

				break;

			case MotionEvent.ACTION_POINTER_UP:
				mode = NONE;
				velocity = 0;
//				savedMatrix.set(matrix);
				oldDist = spacing(event);
				// Log.d(TAG, "mode = NONE");
				break;

			case MotionEvent.ACTION_MOVE:
				allowInert = false;
				if (mode == DRAG) {
					float deltaX = curr.x - last.x;
					float deltaY = curr.y - last.y;

					long dragTime = System.currentTimeMillis();

					velocity = (float) distanceBetween(curr, last)
							/ (dragTime - lastDragTime) * FRICTION;
					lastDragTime = dragTime;

					checkAndSetTranslate(deltaX, deltaY);
					lastDelta.set(deltaX, deltaY);
					last.set(curr.x, curr.y);
				} else if (mScaleDetector == null && mode == ZOOM) {
					Log.d("cjf---", " action move mode == zoom  with mScaleDetector is null" + matrix);

//					float newDist = spacing(event);
//					if (event.getPointerCount() < 2)
//						break;
//					// There is one serious trouble: when you scaling with
//					// two fingers, then pick up first finger of gesture,
//					// ACTION_MOVE being called.
//					// Magic number 50 for this case
//					if (THRESHOLD_DISTANCE > Math.abs(oldDist - newDist)
//							|| Math.abs(oldDist - newDist) > 50)
//						break;
//					float mScaleFactor = newDist / oldDist;
//					oldDist = newDist;
//
//					float origScale = saveScale;
//					saveScale *= mScaleFactor;
//					if (saveScale > maxScale) {
//						saveScale = maxScale;
//						mScaleFactor = maxScale / origScale;
//					} else if (saveScale < minScale) {
//						saveScale = minScale;
//						mScaleFactor = minScale / origScale;
//					}
//
//					calcPadding();
//					if (origWidth * saveScale <= width
//							|| origHeight * saveScale <= height) {
//						matrix.postScale(mScaleFactor, mScaleFactor,
//								width / 2, height / 2);
//						if (mScaleFactor < 1) {
//							fillMatrixXY();
//							if (mScaleFactor < 1) {
//								scaleMatrixToBounds();
//							}
//						}
//					} else {
//						PointF mid = midPointF(event);
//						matrix.postScale(mScaleFactor, mScaleFactor, mid.x,
//								mid.y);
//						fillMatrixXY();
//						if (mScaleFactor < 1) { //zoom in may lead to translate not right for bitmap show, need correct.
//							if (matrixX < -right)
//								matrix.postTranslate(-(matrixX + right), 0);
//							else if (matrixX > 0)
//								matrix.postTranslate(-matrixX, 0);
//							if (matrixY < -bottom)
//								matrix.postTranslate(0, -(matrixY + bottom));
//							else if (matrixY > 0)
//								matrix.postTranslate(0, -matrixY);
//						}
//					}
//					checkSiding();
				}
				break;
		}

		setImageMatrix(matrix);
		invalidate();
//		return false;
		return true;
	}

	public void setImgSize(int width, int height) {
//		bmWidth = width;
//		bmHeight = height;
//
//		Log.d("cjf---", " setImgSize bmWidth = " + bmWidth);
//		Log.d("cjf---", " setImgSize bmHeight = " + bmHeight);
	}
}
