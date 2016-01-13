package com.example.satelite;

import javax.net.ssl.SSLEngineResult.Status;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
public class ArcMenu  extends ViewGroup implements   OnClickListener {
         
	 private static final String TAG = null;
	private   int  mridus;    //半径
     private    Position  mposition = Position.RIGHT_BOTTOM;
     private  state  mcurrentstate = state.CLOSE;
	private  OnMenuItemCLickListener   listener;
     //菜单的主按钮
      private View mcbutton; 
     
     //菜单的位置
	     public  enum   Position{
	    	  LEFT_TOP , LEFT_BOTTOM,RIGHT_TOP,RIGHT_BOTTOM
	     }
	     //打开关闭状态
	     public  enum  state{
	    	   OPEN,CLOSE
	     }
	 //回调接口,点击子菜单的回调接口
	  public   interface   OnMenuItemCLickListener{
		    void    onClick(View view , int position);
	  }
	//第一个构造函数通过this调用第2个构造函数
	
	public ArcMenu(Context context) {
		     this(context,null);
		// TODO Auto-generated constructor stub
	}
	
	public ArcMenu(Context context, AttributeSet attrs) {
		  this(context, attrs,0);
		  //第2个构造函数通过this调用第三个构造函数
		// TODO Auto-generated constructor stub
	}
	
	public ArcMenu(Context context, AttributeSet attrs, int defStyleAttr) {
	     //在此初始化代码
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	    mridus =(int)  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100,getResources().getDisplayMetrics());
	  
		// 获取自定义属性的值，也就是位置
	    TypedArray  a  = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ArcMenu	, defStyleAttr, 0);
	   int    pos = a. getInt(R.styleable.ArcMenu_position, 3);
	    switch (pos) {
		case 0:
			 mposition = Position.LEFT_TOP;
			break;
		case 1:
			 mposition = Position.LEFT_BOTTOM;
			break;
		case 2:
			 mposition = Position.RIGHT_TOP;
			 break;
		case 3:
			 mposition = Position.RIGHT_BOTTOM;
			break;
	
		}
	    //初始化半径
	     mridus  = (int) a.getDimension(R.styleable.ArcMenu_radius	, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100,getResources().getDisplayMetrics()));
	    
	    Log.i(TAG, "position"+ mposition +"radius"+mridus);
	    
	    a.recycle();   //回收
	}
	
	  
	
	/**
	 * 用来测定组件的大小，它在界面上所占的空间的大小
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		 //用for循环测量所有的child(也就是子组件)
		//  得到任何一个子组件后都会给它一个所需要的分配位置的大小
		int  count  = getChildCount();
		for(int i =0 ; i < count ; i++){
			//测量child
			measureChild(getChildAt(i), widthMeasureSpec	, heightMeasureSpec);      //第一个参数是子组件后两个参数是宽度和高度
		}
		
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
    //用来定位组件的位置
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		//如果发生改变，就调用执行代码  ,用来设定子组件的位置
		if(changed){
			    //定位主button的位置
			        locatezhuanniu();
			        //定位其他组件的位置
			        int count = getChildCount();
			        for(int  i = 0 ; i< count  - 1; i++){ 
			        	 
			        	 View child =  getChildAt(i+1);
			        	 child.setVisibility(View.GONE);     //刚开始隐藏所有的按钮
                    //设置6个按钮的坐标位置
			        	 int  cl =  (int) (mridus  * Math.sin(Math.PI /2 / (count -2 ) * i ));    //x坐标
			        	 int  ct = (int )(mridus * Math.cos(Math.PI/2/(count - 2) * i));       //y坐标
			        	 int  cwidth = child.getMeasuredWidth();
			        	 int  cheight = child.getMeasuredHeight();
			        	 //如果菜单在左下，或右下
			       	 if(mposition == Position.LEFT_BOTTOM || mposition == Position.RIGHT_BOTTOM){
			        		     ct = getMeasuredHeight() - cheight- ct;
			        	 }
			      	 //如果菜单在左上，或右下
			        	 if(mposition == Position.LEFT_TOP ||   mposition == Position.RIGHT_BOTTOM){
			        		  cl = getMeasuredWidth() - cwidth - cl;
			        	 }
			        	
			        	  
			        	 child.layout(cl, ct, cl + cwidth, ct + cheight);
			        }
		   }
	}
	/*
	 * 定位主菜单的按钮
	 */
	public  void   locatezhuanniu(){
		       mcbutton = getChildAt(0);
		      mcbutton.setOnClickListener(this);
		       int  l =0 ;   //距离左边
		       int  t = 0;   //距离上面
		       
		       int  width = mcbutton.getMeasuredWidth();    //获得当前组件的宽度
		       int  height = mcbutton.getMeasuredHeight();  //获得当前组件的高度
		       
		       switch (mposition) {
		      	case  LEFT_TOP:
		      		       l = 0;
		      		       t = 0;
			     	break;
		      	case  LEFT_BOTTOM:
		      		       l = 0;
		      		       t = getMeasuredHeight() - height;
			     	break;
		      	case  RIGHT_TOP:
		      		    l = getMeasuredWidth() - width;
		      		    t = 0;
			     	break;
		      	case  RIGHT_BOTTOM:
		      		   t = getMeasuredHeight() - height;
		      		   l = getMeasuredWidth() - width;
			     	break;
	       }
		       //getMeasureHeight是指获得整个界面的高度 getMeasureWidth是指获得整个界面的宽度
		       //知道t和l后直接进行button的定位 
		 //      l 和 t 是组件左边缘和上边缘相对于父类组件左边缘和上边缘的距离
		 //      r 和 b是空间右边缘和下边缘相对于父类组件左边缘和上边缘的距离
		       mcbutton.layout(l, t, l+width,t+ width);
		   
	}
    public  void  setOnMenuItem( OnMenuItemCLickListener  hhh){
    	      if(hhh != null){
    	    	   this.listener = hhh;
    	      }
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		   //添加主按钮旋转动画
		rotatazhubutton(v,0f,360f,300);
		//菜单打开，则关闭，菜单关闭则打开切换菜单
		togglemenu(300);
		
		
		
	}

	public  void togglemenu(int duration) {
		// TODO  Auto-generated method stub
		      //为menuitem添加平移动画和旋转动画
		    int  count   = getChildCount();
		    for (int i =0 ; i < count -1  ; i++){
		    	final  View  childview = getChildAt(i+1);
		      	 final 	int  pos = i +1;
		    	    childview.setVisibility(View.VISIBLE);
		    	//end 0 , 0
		    	//start的坐标
		    	 int  cl =  (int) (mridus  * Math.sin(Math.PI /2 / (count -2 ) * i ));
	        	 int  ct = (int )(mridus * Math.cos(Math.PI/2/(count - 2) * i));
	        	 int xflag = 1;
	        	 int yflag =1 ;
	        	 if(mposition == Position.LEFT_TOP ||  mposition == Position.LEFT_BOTTOM)
	        	 {
	        		  xflag = -1;
	        	 }
	        	 if(mposition == Position.LEFT_TOP || mposition == Position.RIGHT_TOP){
	        		   yflag = -1;
	        	 }
		    		//两个动画一起添加
	        	 AnimationSet   animset = new AnimationSet(true);
	        	 Animation  tranAnim = null;      //平移动画
	         //打开按钮
	        	 if(mcurrentstate ==  state.CLOSE){
	        		 tranAnim = new   TranslateAnimation(xflag * cl		, 0	, yflag * ct	, 0);
	        		 childview.setClickable(true);
	        		 childview.setFocusable(true);
	        		 Log.i(TAG, "打开状态");
	        	 }
	        	 else{   //to close
	        		     tranAnim = new  TranslateAnimation(0	, xflag * cl, 0, yflag* ct);
	        		     childview.setClickable(false);
		        		 childview.setFocusable(false);
	        	 }
	        	 tranAnim.setFillAfter(true);
        		 tranAnim.setDuration(duration);
        		 tranAnim.setStartOffset(( i * 100) /count);   //平移的时间不同的组件开始时间不同
        		 
        		 tranAnim.setAnimationListener(new AnimationListener() {
			     //监听动画结束，把按钮的图片隐藏
					@Override
					public void onAnimationStart(Animation animation) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationEnd(Animation animation) {
						// TODO Auto-generated method stub
						     if(mcurrentstate == state.CLOSE){
						    	 childview.setVisibility(View.GONE);
						    	 Log.i(TAG, "关闭状态");
						     }
					}
				});
        		 
        		  //旋转动画
     		    RotateAnimation    rotate = new RotateAnimation(0, 1080, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
     		    rotate.setFillAfter(true);
     		    rotate.setDuration(duration);
     		    animset.addAnimation(rotate);     //动画先添加旋转再添加平移
     		    animset.addAnimation(tranAnim);
        	
		 
		       childview.startAnimation(animset);   //启动动画
		       childview.setOnClickListener(new OnClickListener() {
			
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					  if(listener != null){
						    listener.onClick(childview, pos );
						     
						     menuItemAnim(pos -1 );     //当前menu变大，其他的变小
						     Log.i(TAG, "sdfsd");
						     changestatus();
					  }
				}

			
			});
		       
		    }
		  //切换菜单状态
		       changestatus();
		 }

	private void changestatus() {
		// TODO Auto-generated method stub
		   mcurrentstate = (mcurrentstate == state.CLOSE ? state.OPEN:state.CLOSE);
	}

	private void rotatazhubutton(View v, float start ,float end, int duration) {
		// TODO Auto-generated method stub
		          //rotate动画
		RotateAnimation  anmitation  =  new  RotateAnimation(start, end, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
	   anmitation.setDuration(duration);
      anmitation.setFillAfter(true);
      v.startAnimation(anmitation);
	} 
	//添加menuItem的点击动画
	private void menuItemAnim(int pos) {
		// TODO Auto-generated method stub
		    for(int  j = 0 ;   j < getChildCount() - 1 ; j++){
		    	        View  childview =  getChildAt(j+1);
		    	        if(j == pos){
		    	        	 childview.startAnimation(scaleBigAnim(300));            //增大动画
		    	        	 Log.i(TAG, "sdfsdfdf");
		    	        }
		    	        else{
		    	        	   childview.startAnimation(scaleSmallAnim(300));      //减小动画
		    	        	   Log.i(TAG, "llllll");
		    	        }
		    	        childview.setClickable(false);
		    	        childview.setFocusable(false);
		    }
	}

	private Animation scaleSmallAnim(int duration) {
		// TODO Auto-generated method stub
		  AnimationSet    animset = new AnimationSet(true);
		     //放大
		    ScaleAnimation   scale = new  ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		    //透明度
		    AlphaAnimation   alpha = new AlphaAnimation(1f, 0.0f);
	      animset.addAnimation(scale);
	      animset.addAnimation(alpha);
      animset.setDuration(duration);
      animset.setFillAfter(true);
      return   animset;
		  
	}
  /**
   * 为当前点击的item变大，透明度降低的动画
   * @param duration
   * @return
   */
	private Animation scaleBigAnim(int duration) {
		// TODO Auto-generated method stub
		    AnimationSet    animset = new AnimationSet(true);
		     //放大
		    ScaleAnimation   scale = new  ScaleAnimation(1.0f, 4.0f, 1.0f, 4.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		    //透明度
		    AlphaAnimation   alpha = new AlphaAnimation(1f, 0.0f);
	      animset.addAnimation(scale);
	      animset.addAnimation(alpha);
         animset.setDuration(duration);
         animset.setFillAfter(true);
         return   animset;
         
	      
	}
	
}
