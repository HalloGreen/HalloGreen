package com.example.jiaha.wtf;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainPage extends ListActivity {

//    //private List<Map<String, Object>> mData;
    List<Datalist> datalist = new ArrayList<Datalist>();
    int wDieingMax = 80;
    int wDieingMin = 20;
    int wWarningMax = 60;
    int wWarningMin = 40;

    int lDieingMax = 80;
    int lDieingMin = 20;
    int lWarningMax =60;
    int lWarningMin =40;

    int tDieingMax = 80;
    int tDieingMin = 20;
    int tWarningMax = 60;
    int tWarningMin = 40;

    private List<Map<String, Object>> mData;
    private ImageButton add;
    private ImageButton Settings;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        add = (ImageButton)findViewById(R.id.add);
        Settings = (ImageButton)findViewById(R.id.Settings);
        Datalist data=new Datalist();
        data.setData("Flower1", 15+"MONTHS", R.drawable.flower1, 90, 50, 50);
        datalist.add(data);
        data = new Datalist();
        data.setData("Flower2", 5+"MONTHS", R.drawable.test, 80, 81, 82);
        datalist.add(data);
        data = new Datalist();
        data.setData("Flower3", 4+"MONTHS", R.drawable.flower1, 49, 50, 50);
        datalist.add(data);
        data = new Datalist();
        data.setData("Flower4", 2+"MONTHS", R.drawable.test, 51, 50, 50);
        datalist.add(data);
        data = new Datalist();
        data.setData("Flower5", 11+"MONTHS", R.drawable.flower1, 20, 20, 20);
        datalist.add(data);
        mData = getData();
        MyAdapter adapter = new MyAdapter(this);
        this.setListAdapter(adapter);
        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent("com.example.jiaha.wtf.add"));
            }
        });
        Settings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
    }
    // ListView 中某项被选中后的逻辑
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Log.v("clicked", (String) mData.get(position).get("title"));
    }

    /**
     * listview中点击按键弹出对话框
     */
    public void showInfo(){
        new AlertDialog.Builder(this)
                .setTitle("Mylistview")
                .setMessage("Info...")
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }



    public final class ViewHolder{
        public ImageView img;
        public ImageView img1;
        public TextView title;
        public TextView info;
        public ImageButton viewBtn;
    }


    public class MyAdapter extends BaseAdapter{

        private LayoutInflater mInflater;


        public MyAdapter(Context context){
            this.mInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mData.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if (convertView == null) {

                holder=new ViewHolder();

                convertView = mInflater.inflate(R.layout.list_item, null);
                holder.img = (ImageView)convertView.findViewById(R.id.pic);
                holder.img1 = (ImageView)convertView.findViewById(R.id.pic2);
                holder.title = (TextView)convertView.findViewById(R.id.title);
                holder.info = (TextView)convertView.findViewById(R.id.info);
                holder.viewBtn = (ImageButton)convertView.findViewById(R.id.img);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder)convertView.getTag();
            }


            holder.img.setBackgroundResource((Integer) mData.get(position).get("img"));
            holder.img1.setBackgroundResource((Integer) mData.get(position).get("pic"));
            holder.title.setText((String) mData.get(position).get("title"));
            holder.info.setText((String) mData.get(position).get("info")+"个月");
            holder.viewBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                }
            });


            return convertView;
        }

    }

    public class Datalist{
        String Name;
        String Age;
        int PictureOfPlant;
        int state;
        public void setData(
                String N,
                String A,
                int P,
                int W,
                int L,
                int T){
            Name=N;
            Age=A;
            PictureOfPlant = P;
            if(W>wDieingMax||L>lDieingMax||T>tDieingMax||W<wDieingMin||L<lDieingMin||T<tDieingMin) state = 2;
            else if(W>wWarningMax||L>lWarningMax||T>tWarningMax||W<wWarningMin||L<lWarningMin||T<tWarningMin) state = 1;
            else state = 0;
        }
    }

    public List<Map<String, Object>> getData() {

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();

        for(int i=0;i<datalist.size();i++) {
            map.put("title", datalist.get(i).Name);
            map.put("info", datalist.get(i).Age);
            if (datalist.get(i).state == 0 ) {
                map.put("img", R.drawable.good);
            }
            if (datalist.get(i).state == 1) {
                map.put("img", R.drawable.warning);
            }
            if (datalist.get(i).state == 2) {
                map.put("img", R.drawable.dieing);
            }
            map.put("pic", datalist.get(i).PictureOfPlant);
            list.add(map);
            map = new HashMap<String,Object>();
        }
        return list;
    }
}
