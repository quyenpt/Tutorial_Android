package com.platform.quyenpt.tutorial;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookActivity;

import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.share.internal.LikeActionController;
import com.facebook.share.internal.LikeButton;
import com.facebook.share.internal.LikeContent;
import com.facebook.share.internal.LikeDialog;
import com.facebook.share.internal.ShareConstants;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.LikeView;
import com.facebook.share.widget.ShareButton;

public class ShareFacebook extends AppCompatActivity {
    CallbackManager callbackManager;

    LikeButton buttonLike;
    TextView states;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_facebook);
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("http://dantri.com.vn/the-gioi/mot-nu-nghi-pham-co-bieu-hien-nhiem-doc-giong-ong-kim-jong-nam-20170224115409916.htm"))
                .build();
        final ShareButton shareButton = (ShareButton)findViewById(R.id.fb_share_button);
        shareButton.setShareContent(content);

        Button btnShare = (Button) findViewById(R.id.share_button);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shareButton.performClick();
            }
        });

        //------------like
        callbackManager = CallbackManager.Factory.create();
        LikeView likeView = (LikeView) findViewById(R.id.likeView);
        //likeView.setVisibility(View.GONE);
        likeView.setLikeViewStyle(LikeView.Style.BUTTON);
        likeView.setAuxiliaryViewPosition(LikeView.AuxiliaryViewPosition.INLINE);
        likeView.setObjectIdAndType(
                "http://dantri.com.vn/kinh-doanh/o-to-gia-re-tran-ve-doanh-nghiep-noi-cuong-cuong-cat-giam-dong-xe-20170301055556619.htm",
                LikeView.ObjectType.PAGE);
        LinearLayout layoutLikeView = (LinearLayout) likeView.getChildAt(0);

        buttonLike = (LikeButton) layoutLikeView.getChildAt(0);
        states = (TextView) layoutLikeView.getChildAt(1);
        System.out.println(likeView.getBackground().toString());

        final Button btnLike = (Button) findViewById(R.id.like_button);
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonLike.performClick();
            }
        });
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000);
                    String state = buttonLike.getText().toString();
                    //compare to String like = getResources().getString(R.string.com_facebook_like_button_liked)
                    //or getResources().getString(R.string.com_facebook_like_button_not_liked)
                    if (state == getResources().getString(R.string.com_facebook_like_button_liked)) {
                        btnLike.post(new Runnable() {
                            public void run() {
                                btnLike.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                            }
                        });
                    } else {
                        if (state == getResources().getString(R.string.com_facebook_like_button_liked)) {
                            btnLike.post(new Runnable() {
                                public void run() {
                                    btnLike.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                }
                            });
                        }
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Handle Facebook Login Result
        callbackManager.onActivityResult(requestCode, resultCode, data);

//        Bundle bundle = data.getExtras();
//        for (String key : bundle.keySet()) {
//            Object value = bundle.get(key);
//            System.out.println(key+" "+value.toString());
//        }
//        System.out.println("---------------------------------------");
//        Bundle test = (Bundle) bundle.get("com.facebook.platform.protocol.RESULT_ARGS");
//        for (String key : test.keySet()) {
//            Object value = test.get(key);
//            System.out.println(key+" "+value.toString());
//        }
    }
}
