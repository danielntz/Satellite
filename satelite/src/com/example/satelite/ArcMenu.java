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
	private   int  mridus;    //�뾶
     private    Position  mposition = Position.RIGHT_BOTTOM;
     private  state  mcurrentstate = state.CLOSE;
	private  OnMenuItemCLickListener   listener;
     //�˵�������ť
      private View mcbutton; 
     
     //�˵���λ��
	     public  enum   Position{
	    	  LEFT_TOP , LEFT_BOTTOM,RIGHT_TOP,RIGHT_BOTTOM
	     }
	     //�򿪹ر�״̬
	     public  enum  state{
	    	   OPEN,CLOSE
	     }
	 //�ص��ӿ�,����Ӳ˵��Ļص��ӿ�
	  public   interface   OnMenuItemCLickListener{
		    void    onClick(View view , int position);
	  }
	//��һ�����캯��ͨ��this���õ�2�����캯��
	
	public ArcMenu(Context context) {
		     this(context,null);
		// TODO Auto-generated constructor stub
	}
	
	public ArcMenu(Context context, AttributeSet attrs) {
		  this(context, attrs,0);
		  //��2�����캯��ͨ��this���õ��������캯��
		// TODO Auto-generated constructor stub
	}
	
	public ArcMenu(Context context, AttributeSet attrs, int defStyleAttr) {
	     //�ڴ˳�ʼ������
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	    mridus =(int)  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100,getResources().getDisplayMetrics());
	  
		// ��ȡ�Զ������Ե�ֵ��Ҳ����λ��
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
	    //��ʼ���뾶
	     mridus  = (int) a.getDimension(R.styleable.ArcMenu_radius	, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100,getResources().getDisplayMetrics()));
	    
	    Log.i(TAG, "position"+ mposition +"radius"+mridus);
	    
	    a.recycle();   //����
	}
	
	  
	
	/**
	 * �����ⶨ����Ĵ�С�����ڽ�������ռ�Ŀռ�Ĵ�С
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		 //��forѭ���������е�child(Ҳ���������)
		//  �õ��κ�һ��������󶼻����һ������Ҫ�ķ���λ�õĴ�С
		int  count  = getChildCount();
		for(int i =0 ; i < count ; i++){
			//����child
			measureChild(getChildAt(i), widthMeasureSpec	, heightMeasureSpec);      //��һ������������������������ǿ�Ⱥ͸߶�
		}
		
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
    //������λ�����λ��
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		//��������ı䣬�͵���ִ�д���  ,�����趨�������λ��
		if(changed){
			    //��λ��button��λ��
			        locatezhuanniu();
			        //��λ���������λ��
			        int count = getChildCount();
			        for(int  i = 0 ; i< count  - 1; i++){ 
			        	 
			        	 View child =  getChildAt(i+1);
			        	 child.setVisibility(View.GONE);     //�տ�ʼ�������еİ�ť
                    //����6����ť������λ��
			        	 int  cl =  (int) (mridus  * Math.sin(Math.PI /2 / (count -2 ) * i ));    //x����
			        	 int  ct = (int )(mridus * Math.cos(Math.PI/2/(count - 2) * i));       //y����
			        	 int  cwidth = child.getMeasuredWidth();
			        	 int  cheight = child.getMeasuredHeight();
			        	 //����˵������£�������
			       	 if(mposition == Position.LEFT_BOTTOM || mposition == Position.RIGHT_BOTTOM){
			        		     ct = getMeasuredHeight() - cheight- ct;
			        	 }
			      	 //����˵������ϣ�������
			        	 if(mposition == Position.LEFT_TOP ||   mposition == Position.RIGHT_BOTTOM){
			        		  cl = getMeasuredWidth() - cwidth - cl;
			        	 }
			        	
			        	  
			        	 child.layout(cl, ct, cl + cwidth, ct + cheight);
			        }
		   }
	}
	/*
	 * ��λ���˵��İ�ť
	 */
	public  void   locatezhuanniu(){
		       mcbutton = getChildAt(0);
		      mcbutton.setOnClickListener(this);
		       int  l =0 ;   //�������
		       int  t = 0;   //��������
		       
		       int  width = mcbutton.getMeasuredWidth();    //��õ�ǰ����Ŀ��
		       int  height = mcbutton.getMeasuredHeight();  //��õ�ǰ����ĸ߶�
		       
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
		       //getMeasureHeight��ָ�����������ĸ߶� getMeasureWidth��ָ�����������Ŀ��
		       //֪��t��l��ֱ�ӽ���button�Ķ�λ 
		 //      l �� t ��������Ե���ϱ�Ե����ڸ���������Ե���ϱ�Ե�ľ���
		 //      r �� b�ǿռ��ұ�Ե���±�Ե����ڸ���������Ե���ϱ�Ե�ľ���
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
		   //�������ť��ת����
		rotatazhubutton(v,0f,360f,300);
		//�˵��򿪣���رգ��˵��ر�����л��˵�
		togglemenu(300);
		
		
		
	}

	public  void togglemenu(int duration) {
		// TODO  Auto-generated method stub
		      //Ϊmenuitem���ƽ�ƶ�������ת����
		    int  count   = getChildCount();
		    for (int i =0 ; i < count -1  ; i++){
		    	final  View  childview = getChildAt(i+1);
		      	 final 	int  pos = i +1;
		    	    childview.setVisibility(View.VISIBLE);
		    	//end 0 , 0
		    	//start������
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
		    		//��������һ�����
	        	 AnimationSet   animset = new AnimationSet(true);
	        	 Animation  tranAnim = null;      //ƽ�ƶ���
	         //�򿪰�ť
	        	 if(mcurrentstate ==  state.CLOSE){
	        		 tranAnim = new   TranslateAnimation(xflag * cl		, 0	, yflag * ct	, 0);
	        		 childview.setClickable(true);
	        		 childview.setFocusable(true);
	        		 Log.i(TAG, "��״̬");
	        	 }
	        	 else{   //to close
	        		     tranAnim = new  TranslateAnimation(0	, xflag * cl, 0, yflag* ct);
	        		     childview.setClickable(false);
		        		 childview.setFocusable(false);
	        	 }
	        	 tranAnim.setFillAfter(true);
        		 tranAnim.setDuration(duration);
        		 tranAnim.setStartOffset(( i * 100) /count);   //ƽ�Ƶ�ʱ�䲻ͬ�������ʼʱ�䲻ͬ
        		 
        		 tranAnim.setAnimationListener(new AnimationListener() {
			     //���������������Ѱ�ť��ͼƬ����
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
						    	 Log.i(TAG, "�ر�״̬");
						     }
					}
				});
        		 
        		  //��ת����
     		    RotateAnimation    rotate = new RotateAnimation(0, 1080, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
     		    rotate.setFillAfter(true);
     		    rotate.setDuration(duration);
     		    animset.addAnimation(rotate);     //�����������ת�����ƽ��
     		    animset.addAnimation(tranAnim);
        	
		 
		       childview.startAnimation(animset);   //��������
		       childview.setOnClickListener(new OnClickListener() {
			
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					  if(listener != null){
						    listener.onClick(childview, pos );
						     
						     menuItemAnim(pos -1 );     //��ǰmenu��������ı�С
						     Log.i(TAG, "sdfsd");
						     changestatus();
					  }
				}

			
			});
		       
		    }
		  //�л��˵�״̬
		       changestatus();
		 }

	private void changestatus() {
		// TODO Auto-generated method stub
		   mcurrentstate = (mcurrentstate == state.CLOSE ? state.OPEN:state.CLOSE);
	}

	private void rotatazhubutton(View v, float start ,float end, int duration) {
		// TODO Auto-generated method stub
		          //rotate����
		RotateAnimation  anmitation  =  new  RotateAnimation(start, end, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
	   anmitation.setDuration(duration);
      anmitation.setFillAfter(true);
      v.startAnimation(anmitation);
	} 
	//���menuItem�ĵ������
	private void menuItemAnim(int pos) {
		// TODO Auto-generated method stub
		    for(int  j = 0 ;   j < getChildCount() - 1 ; j++){
		    	        View  childview =  getChildAt(j+1);
		    	        if(j == pos){
		    	        	 childview.startAnimation(scaleBigAnim(300));            //���󶯻�
		    	        	 Log.i(TAG, "sdfsdfdf");
		    	        }
		    	        else{
		    	        	   childview.startAnimation(scaleSmallAnim(300));      //��С����
		    	        	   Log.i(TAG, "llllll");
		    	        }
		    	        childview.setClickable(false);
		    	        childview.setFocusable(false);
		    }
	}

	private Animation scaleSmallAnim(int duration) {
		// TODO Auto-generated method stub
		  AnimationSet    animset = new AnimationSet(true);
		     //�Ŵ�
		    ScaleAnimation   scale = new  ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		    //͸����
		    AlphaAnimation   alpha = new AlphaAnimation(1f, 0.0f);
	      animset.addAnimation(scale);
	      animset.addAnimation(alpha);
      animset.setDuration(duration);
      animset.setFillAfter(true);
      return   animset;
		  
	}
  /**
   * Ϊ��ǰ�����item���͸���Ƚ��͵Ķ���
   * @param duration
   * @return
   */
	private Animation scaleBigAnim(int duration) {
		// TODO Auto-generated method stub
		    AnimationSet    animset = new AnimationSet(true);
		     //�Ŵ�
		    ScaleAnimation   scale = new  ScaleAnimation(1.0f, 4.0f, 1.0f, 4.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		    //͸����
		    AlphaAnimation   alpha = new AlphaAnimation(1f, 0.0f);
	      animset.addAnimation(scale);
	      animset.addAnimation(alpha);
         animset.setDuration(duration);
         animset.setFillAfter(true);
         return   animset;
         
	      
	}
	
}
