package com.example.dllo.thirtysixkr.web.richtext;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import java.io.InputStream;

/**
 * Created by ChenFengYao on 16/7/29.
 */
public class HtmlTextView extends TextView {
    public HtmlTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public HtmlTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HtmlTextView(Context context) {
        super(context);
    }

    /**
     * @param is
     * @return
     */
    static private String convertStreamToString(InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");

        return s.hasNext() ? s.next() : "";
    }

    /**
     * Parses String containing HTML to Android's Spannable format and displays
     * it in this TextView.
     *
     * @param html String containing HTML, for example: "<b>Hello world!</b>"
     */
    public void setHtmlFromString(String html) {
        Html.ImageGetter imgGetter;

            imgGetter = new UrlImageGetter( getContext(),this);

        setText(Html.fromHtml(html, imgGetter, null));
        // make links and image work
        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                int what = msg.what;
                if (what == 200) {
                    MessageSpan ms = (MessageSpan) msg.obj;
                    Object[] spans = (Object[]) ms.getObj();
                    for (Object span : spans) {
                        if (span instanceof ImageSpan) {
                            Log.d("Sysout", "点击了图片"+((ImageSpan) span).getSource());
                            //处理自己的逻辑
                        }
                    }
                }
            }
        };
        setMovementMethod(LinkMovementMethodExt.getInstance(handler, ImageSpan.class));

        // make links work
       // setMovementMethod(LinkMovementMethod.getInstance());
        //text.setTextColor(getResources().getColor(android.R.color.secondary_text_dark_nodisable));
    }
}
