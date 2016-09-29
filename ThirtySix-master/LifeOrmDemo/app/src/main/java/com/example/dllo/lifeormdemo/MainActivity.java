package com.example.dllo.lifeormdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.litesuits.orm.LiteOrm;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LiteOrm mLiteOrm;
    private ArrayList<Person> query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.add_btn).setOnClickListener(this);
        findViewById(R.id.query_btn).setOnClickListener(this);

        mLiteOrm =  LiteOrm.newSingleInstance(this,"myDataBase.db");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_btn:
                Person firstPerson = new Person();
                firstPerson.setName("张三");
                firstPerson.setAge(18);
                // 插入单条数据
                mLiteOrm.insert(firstPerson);
                List<Person> persons = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    Person single  = new Person();
                    single.setName("这是名字"+i);
                    single.setAge(i);
                    persons.add(single);
                }
                mLiteOrm.insert(persons);
                break;
            case R.id.query_btn:
                query = mLiteOrm.query(Person.class);

                for (Person person : query) {
                    Log.d("MainActivity", "person:" + person);
                }

                break;
        }
    }
}
