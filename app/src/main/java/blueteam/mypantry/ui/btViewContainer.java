package blueteam.mypantry.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.view.*;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import blueteam.mypantry.ui.viewcontainer.btView_Pantry;
import blueteam.mypanty.R;

import java.util.ArrayList;

public class btViewContainer extends Activity {

    @Override
    protected void onCreate( Bundle SavedInstanceState ) {
        super.onCreate( SavedInstanceState );
        setContentView( R.layout.btui_view_container );

        // Bind container reference.
        HorizontalScrollViewRoot = (HorizontalScrollView)findViewById( R.id.HorizontalScrollViewRoot );
        InnerLay = (LinearLayout)findViewById( R.id.innerLay );

        // Bind our previous and next views.
        PreviousView = (LinearLayout)getLayoutInflater().inflate( R.layout.btui_view_pantry, null ); //(LinearLayout)findViewById( R.id.LinearLayoutPantry );
        NextView = (LinearLayout)getLayoutInflater().inflate( R.layout.btui_view_shopping_list, null );//(LinearLayout)findViewById( R.id.LinearLayoutShoppingList );

        GestureWatcher = new GestureDetector( new InternalGestureDetector() );

        HomeView = (LinearLayout)getLayoutInflater().inflate( R.layout.btui_view_home, null );//(LinearLayout)findViewById( R.id.LinearLayoutHome );
        ShoppingListView = (LinearLayout)getLayoutInflater().inflate( R.layout.btui_view_shopping_list, null );//(LinearLayout)findViewById( R.id.LinearLayoutShoppingList );
        PantryView = (LinearLayout)getLayoutInflater().inflate( R.layout.btui_view_pantry, null );//(LinearLayout)findViewById( R.id.LinearLayoutPantry );

        // Setup our view's to dynamically size to the current size of the local display.
        Display LocalDisplay = getWindowManager().getDefaultDisplay();
        DisplayWidth = LocalDisplay.getWidth();
        ViewWidth = DisplayWidth;

        Views = new ArrayList< LinearLayout >();

        LayoutParameters = new LinearLayout.LayoutParams( LocalDisplay.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT );

        HomeView.setLayoutParams( LayoutParameters );
        ShoppingListView.setLayoutParams( LayoutParameters );
        PantryView.setLayoutParams( LayoutParameters );

        // Views are inserted in the order in which tßßhey will be displayed; i.e, these are index
        // mapped.ßßßßßßßß

        Views.add( PantryView );
        Views.add( HomeView );
        Views.add( ShoppingListView );
        InnerLay.addView( PantryView );
        InnerLay.addView( HomeView );
        InnerLay.addView( ShoppingListView );

        NextView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View CallingView ) {
                new Handler().postDelayed( new Runnable() {
                    @Override
                    public void run() {
                        HorizontalScrollViewRoot.smoothScrollTo( ( (int)HorizontalScrollViewRoot.getScaleX() + ViewWidth ),
                                (int)HorizontalScrollViewRoot.getScaleY() );
                    }
                }, 100 );

            }
        } );

        PreviousView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View CallingView ) {
                new Handler().postDelayed( new Runnable() {
                    @Override
                    public void run() {
                        HorizontalScrollViewRoot.smoothScrollTo( ( (int)HorizontalScrollViewRoot.getScaleX() - ViewWidth ),
                                (int)HorizontalScrollViewRoot.getScaleY() );
                    }
                }, 100 );

            }
        } );

        HorizontalScrollViewRoot.setOnTouchListener( new View.OnTouchListener() {
            @Override
            public boolean onTouch( View CallingView, MotionEvent Event ) {
                if( GestureWatcher.onTouchEvent( Event ) ) {
                    return true;
                }

                return false;
            }
        } );
    }

    public int SlideToView( String Direction ) {
        Rect CollisionRect = new Rect();
        int HitPosition = 0;
        int RightCounter = 0;

        for( int Index = 0; Index < Views.size(); Index++ ) {
            if( Views.get( Index ).getLocalVisibleRect( CollisionRect ) ) {
                if( Direction.equals( "Left" ) ) {
                    HitPosition = Index;
                    break;
                } else if( Direction.equals( "Right" ) ) {
                    RightCounter++;
                    HitPosition = Index;

                    if( RightCounter == 2 ) {
                        break;
                    }
                }
            }
        }

        return HitPosition;
    }

    class InternalGestureDetector extends SimpleOnGestureListener {
        @Override
        public boolean onFling( MotionEvent MotionEvent1, MotionEvent MotionEvent2, float VelocityX, float VelocityY ) {
            if( MotionEvent1.getX() < MotionEvent2.getX() ) {
                CurrentPosition = SlideToView( "Left" );
            } else {
                CurrentPosition = SlideToView( "Right" );
            }

            HorizontalScrollViewRoot.smoothScrollTo( Views.get( CurrentPosition ).getLeft(), 0 );

            switch( CurrentPosition ) {
                case 0: {
                    btView_Pantry.GetBuilt().Build();
                }
            }

            return true;
        }
    }


    private HorizontalScrollView HorizontalScrollViewRoot;
    private LinearLayout InnerLay;
    private GestureDetector GestureWatcher;

    private ArrayList< LinearLayout > Views;

    private int LeftViewIndex;
    private int RightViewIndex;

    private int DisplayWidth;
    private int ViewWidth;

    private int CurrentPosition;
    private int PreviousPosition;

    private LinearLayout NextView;
    private LinearLayout PreviousView;

    private LayoutParams LayoutParameters;

    // Managed views.
    LinearLayout ShoppingListView;
    LinearLayout PantryView;
    LinearLayout HomeView;
}

