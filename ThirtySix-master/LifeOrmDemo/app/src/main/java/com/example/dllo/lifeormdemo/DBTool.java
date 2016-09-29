package com.example.dllo.lifeormdemo;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.litesuits.orm.LiteOrm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DBTool {
    private static DBTool sDBTool;
    private LiteOrm mLiteOrm;
    private ExecutorService threadPool;
    private Handler mHandler; // 用来做线程切换的

    private DBTool() {
        mLiteOrm = LiteOrm.newSingleInstance(MyApp.getContext(), "dataBase.db");
        threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);


        // 在构建Handler的时候, 参数传
        // Looper.getMainLooper()
        // 这样可以保证 该Handler 一定属于主线程
        mHandler = new Handler(Looper.getMainLooper());
        Message message = mHandler.obtainMessage();

    }

    public static DBTool getInstance() {
        if (sDBTool == null) {
            synchronized (DBTool.class) {
                if (sDBTool == null) {
                    sDBTool = new DBTool();
                }
            }
        }
        return sDBTool;
    }

    // 插入数据
    public void insertPerson(final Person person) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                mLiteOrm.insert(person);
            }
        });

    }

    public void insertPerson(final List<Person> persons) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                mLiteOrm.insert(persons);
            }
        });
    }

    public void getAllPerson(final QueryListener<Person> queryListener) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                ArrayList<Person> persons = mLiteOrm.query(Person.class);
                // handler 的post 方法可以把一个runnable
                // 发送到主线程去执行
                mHandler.post(new HandlerRunnable<Person>(persons,queryListener));

            }
        });

    }
    class QueryRunnable<T> implements Runnable{

        private Class<T> mTClass;
        private QueryListener<T> queryListener;

        public QueryRunnable(Class<T> mTClass, QueryListener<T> queryListener) {
            this.mTClass = mTClass;
            this.queryListener = queryListener;
        }

        @Override
        public void run() {
            ArrayList<T> tArrayList = mLiteOrm.query(mTClass);
            mHandler.post(new HandlerRunnable<>(tArrayList,queryListener));
        }
    }

    class HandlerRunnable<T> implements Runnable{

        private List<T> mList;
        private QueryListener<T> mTQueryListener;

        public HandlerRunnable(List<T> mList, QueryListener<T> mTQueryListener) {
            this.mList = mList;
            this.mTQueryListener = mTQueryListener;
        }

        @Override
        public void run() {
            mTQueryListener.onQuery(mList);

        }
    }

    // 查询完成后 的回调接口
    public interface QueryListener<T> {
        // 当查询完成后, 将查到的数据作为data, 返回给Activity等
        void onQuery(List<T> data);
    }
}
