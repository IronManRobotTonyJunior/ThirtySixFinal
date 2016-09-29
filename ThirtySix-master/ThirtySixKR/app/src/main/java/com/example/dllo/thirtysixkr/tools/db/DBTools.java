package com.example.dllo.thirtysixkr.tools.db;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.example.dllo.thirtysixkr.MyApp;
import com.example.dllo.thirtysixkr.search.HistoryBean;
import com.litesuits.orm.LiteOrm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DBTools {

    private static DBTools sDBTool;
    private final ExecutorService threadPool;
    private LiteOrm mLiteOrm;

    private final Handler mHandler;

    private DBTools() {
        mLiteOrm = LiteOrm.newSingleInstance(MyApp.getContext(), DBValues.SQL_NAME);
        threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
        mHandler = new Handler(Looper.getMainLooper());
        Message message = mHandler.obtainMessage();
    }

    public static DBTools getInstance() {
        if (sDBTool == null) {
            synchronized (DBTools.class) {
                if (sDBTool == null) {
                    sDBTool = new DBTools();
                }
            }
        }
        return sDBTool;
    }

    public void insertDB(final HistoryBean content) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                _insertDB(content);
            }
        });


    }

    private void _insertDB(HistoryBean content){
        ArrayList<HistoryBean> historyBeen = querySync();
        for (int i = 0; i < historyBeen.size(); i++) {
            if (historyBeen.get(i).getHistoryQuery().contains(content.getHistoryQuery())) {
                return;
            }
        }

        mLiteOrm.insert(content);
    }



    private ArrayList<HistoryBean> querySync() {
        ArrayList<HistoryBean> beans = mLiteOrm.query(HistoryBean.class);
        return beans;
    }

    public void deleteAllDB() {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                _deleteDB();
            }
        });
    }

    private void _deleteDB(){
        mLiteOrm.delete(HistoryBean.class);
    }

    public void queryALLDB(final QueryListener<HistoryBean> queryListener) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
//                ArrayList<HistoryBean> beans = mLiteOrm.query(HistoryBean.class);
                ArrayList<HistoryBean> beans = querySync();
                mHandler.post(new HandlerRunnable<HistoryBean>(beans, queryListener));

            }
        });

    }

    public interface QueryListener<T> {
        public void onQuery(List<T> data);
    }

    class HandlerRunnable<T> implements Runnable {
        private List<T> mList;
        private QueryListener<T> mTQueryListener;

        public HandlerRunnable(ArrayList<T> beans, QueryListener<T> mTQueryListener) {
            this.mList = beans;
            this.mTQueryListener = mTQueryListener;

        }

        @Override
        public void run() {

            mTQueryListener.onQuery(mList);

        }
    }


}
