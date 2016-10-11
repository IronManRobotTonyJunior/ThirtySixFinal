package view;

import java.util.ArrayList;
import java.util.List;

public class View {
    private List<OnClickListener> mListenerList;

    public View() {
        mListenerList = new ArrayList<>();
    }

    private OnClickListener mListener;

    public void performClick(){
        if (mListener != null){
            mListener.onClick(this);
        }
    }

    public void setOnClickListener(OnClickListener listener) {
        this.mListener = listener;
    }

    public interface OnClickListener {
        void onClick(View view);
    }


}
