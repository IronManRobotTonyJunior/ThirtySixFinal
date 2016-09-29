package com.example.dllo.thirtysixkr.web.richtext;

import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.view.MotionEvent;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by ChenFengYao on 16/7/29.
 * 单例
 */
public class LinkMovementMethodExt extends android.text.method.LinkMovementMethod {
    private static final AtomicReference<LinkMovementMethodExt> INSTANCE = new AtomicReference<LinkMovementMethodExt>();

    public static LinkMovementMethodExt getInstance(Handler _handler, Class _spanClass) {
        for (; ; ) {
            LinkMovementMethodExt current = INSTANCE.get();
            if (current != null) {
                return current;
            }
            current = new LinkMovementMethodExt(_handler,_spanClass);
            if (INSTANCE.compareAndSet(null, current)) {
                return current;
            }
        }
    }

    private LinkMovementMethodExt(Handler _handler, Class _spanClass) {
        handler = _handler;
        spanClass = _spanClass;
    }

    private Handler handler;
    private Class spanClass;

    int x1;
    int x2;
    int y1;
    int y2;
    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            x1 = (int) event.getX();
            y1 = (int) event.getY();
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            x2 = (int) event.getX();
            y2 = (int) event.getY();
            if (Math.abs(x1 - x2) < 10 && Math.abs(y1 - y2) < 10) {
                x2 -= widget.getTotalPaddingLeft();
                y2 -= widget.getTotalPaddingTop();
                x2 += widget.getScrollX();
                y2 += widget.getScrollY();
                Layout layout = widget.getLayout();
                int line = layout.getLineForVertical(y2);
                int off = layout.getOffsetForHorizontal(line, x2);
                /**             * get you interest span             */
                Object[] spans = buffer.getSpans(off, off, spanClass);
                if (spans.length != 0) {
                    Selection.setSelection(buffer,
                            buffer.getSpanStart(spans[0]),
                            buffer.getSpanEnd(spans[0]));
                    MessageSpan obj = new MessageSpan();
                    obj.setObj(spans);
                    obj.setView(widget);
                    Message message = handler.obtainMessage();
                    message.obj = obj;
                    message.what = 200;
                    message.sendToTarget();
                    return true;
                }
            }
        }
        return super.onTouchEvent(widget, buffer, event);
    }
}
