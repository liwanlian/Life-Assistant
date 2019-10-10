package com.example.calcularion.Userdata;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.calcularion.Configure.CustomBarChart;
import com.example.calcularion.Configure.LineChartManager;
import com.example.calcularion.MYsqldata.DBManager;
import com.example.calcularion.R;
import com.example.calcularion.exitsystem;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class CheckActivity extends exitsystem {

//界面控件
    Spinner sp_year,sp_month,sp_date;
    TextView check_totalout,check_totalin;
    Spinner sp_check;
    ListView lv_message;
    TextView chech_back;
    LinearLayout customBarChart2;
    TextView chech_sure;
    LinearLayout linearLayout,linearLayout1,linearLayout2;

    private LineChart lineChart1,lineChart2,lineChart3;
    private LineData lineData;


    private DBManager db;

    private Calendar calendar;
    private ArrayList<String> data_year;
    private ArrayList<String> data_month;
    private ArrayList<String> data_date;
    private ArrayAdapter<String> adapter_year;
    private ArrayAdapter<String> adapter_month;
    private ArrayAdapter<String> adapter_date;
    private long check_acc_num;
    private String check_date;
    private View dataview;
   private List<Map<String, Object>> listItems;
   private int cposition=0;
   int[] iddatas;
   TextView currentmongth;
   private long exitTime=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        initData();
        initView();
        function_spinner();
    }
    private void initView(){
        sp_check=(Spinner)findViewById(R.id.sp_check);
        sp_year=(Spinner)findViewById(R.id.dp_year);
        sp_month=(Spinner)findViewById(R.id.dp_month);
        sp_date=(Spinner)findViewById(R.id.dp_date);
        check_totalin=(TextView) findViewById(R.id.check_totalin);
        check_totalout=(TextView)findViewById(R.id.check_totalout);
        lv_message=(ListView)findViewById(R.id.lv_message);
        customBarChart2=(LinearLayout)findViewById(R.id.customBarChart2) ;
        chech_sure=(TextView)findViewById(R.id.chech_sure);
        lineChart2 = (LineChart) findViewById(R.id.line_chart1);
        lineChart3=(LineChart)findViewById(R.id.line_chart2);
        chech_back=(TextView)findViewById(R.id.chech_back);
        chech_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=getIntent().getBundleExtra("bundle");
                Intent intent=new Intent(CheckActivity.this,HomeActivity.class);
                intent.putExtra("bundle",bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        currentmongth=(TextView)findViewById(R.id.check_currentmonth);
        currentmongth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuffer sb=new StringBuffer();
                String syear=sp_year.getSelectedItem().toString();
                String smonth=sp_month.getSelectedItem().toString();
                String sday=sp_date.getSelectedItem().toString();

                sb.append(syear).append("年").append(smonth).append("月").append(sday).append("日");
                check_date=sb.toString();

                String ym=check_date.substring(0,check_date.indexOf("月"));
                double Zhichu=db.searchzongw(check_acc_num,ym,"支出");
                 double Shouru=db.searchzongw(check_acc_num,ym,"收入");
                new AlertDialog.Builder(CheckActivity.this)
                .setTitle("温馨提示：")
                .setMessage("主人，根据你的记账情况总结如下："+"\n"+"当月你的总支出为 "+String.valueOf(Zhichu)+"元"+"\n"+"总收入为 "+String.valueOf(Shouru)+"元")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
            }
        });

        linearLayout=(LinearLayout)findViewById(R.id.linera);
        linearLayout1=(LinearLayout)findViewById(R.id.linera1);
        linearLayout2=(LinearLayout)findViewById(R.id.linera2);

        linearLayout.setVisibility(View.INVISIBLE);
        linearLayout1.setVisibility(View.INVISIBLE);
        linearLayout2.setVisibility(View.INVISIBLE);
        check_totalin.setVisibility(View.INVISIBLE);
        check_totalout.setVisibility(View.INVISIBLE);
        lv_message.setVisibility(View.INVISIBLE);
        lv_message.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle=new Bundle();
                int id=iddatas[i];
                bundle=db.searchbundblebyid(id);
                Intent intent=new Intent(CheckActivity.this,EditszActivity.class);
                intent.putExtra("bundle",bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void initData(){
        db=new DBManager(this);
        calendar=Calendar.getInstance();
        data_year=new ArrayList<String>();
        data_month=new ArrayList<String>();
        data_date=new ArrayList<String>();
        check_acc_num=getIntent().getBundleExtra("bundle").getLong("acc_num");
    }
    private void function_spinner(){
        //年
        for (int i=0;i<60;i++){
            data_year.add("" + (calendar.get(Calendar.YEAR) - 30 + i));
        }
        adapter_year = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, data_year);
        adapter_year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_year.setAdapter(adapter_year);
        sp_year.setSelection(30);// 默认选中今年

        //月
        for (int i = 1; i <= 12; i++) {
            data_month.add("" + (i < 10 ? i : i));
        }
        adapter_month = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, data_month);
        adapter_month.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_month.setAdapter(adapter_month);
        int month=calendar.get(Calendar.MONTH);
        sp_month.setSelection(month);

        //日
        adapter_date = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, data_date);
        adapter_date.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_date.setAdapter(adapter_date);



        sp_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                data_date.clear();
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, Integer.valueOf(sp_year.getSelectedItem().toString()));
                cal.set(Calendar.MONTH, arg2);
                int dayofm = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                for (int i = 1; i <= dayofm; i++) {
                    data_date.add("" + (i < 10 ? i : i));
                }
                adapter_date.notifyDataSetChanged();
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                sp_date.setSelection(day-1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

       chech_sure.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               sp_jian();
               iddatas=db.searchiddata(cposition,check_acc_num,check_date);
               listItems=db.searchlistdata(check_acc_num,check_date,cposition);

               SimpleAdapter simpleAdapter=new SimpleAdapter(CheckActivity.this, listItems, R.layout.item1_listview, new String[]{"title","date","remark"},
                       new int[]{R.id.lv_title,R.id.lv_time,R.id.lv_remark});
               lv_message.setAdapter(simpleAdapter);
           }
       });

        //筛选按钮
        List<String> typeinout=new ArrayList<String>();
        typeinout.add("全部");
        typeinout.add("收入");
        typeinout.add("支出");
        ArrayAdapter<String> adapter_type=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,typeinout);
        adapter_type.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sp_check.setAdapter(adapter_type);
        sp_check.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               cposition=i;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    //描绘双柱状图
    private void Barchar(){
        String[] xLabel = {"","凌晨", "上午", "中午", "晚上"};
        String[] yLabel = {"0", "1000", "2000", "3000", "4000", "5000", "6000", "7000", "8000", "9000"};
        String result=db.judgeproper(check_acc_num,check_date);
        if (result.equals("yes")){
            customBarChart2.removeView(dataview);
            int id1=db.searchproperid("收入",check_acc_num,check_date);
            int id2=db.searchproperid("支出",check_acc_num,check_date);
            int size=(db.searchid()-1);
            int[] indata=db.searchcurrentdata(id1);
            int[] outdata=db.searchcurrentdata(id2);
            List<int[]> data = new ArrayList<>();
            data.add(indata);
            data.add(outdata);
            List<Integer> color = new ArrayList<>();
            color.add(R.color.color14);
            color.add(R.color.color15);
            color.add(R.color.color11);
            dataview=new CustomBarChart(this, xLabel, yLabel, data, color);
            customBarChart2.addView(dataview);

        }
        else{
            Toast.makeText(CheckActivity.this,"当天还没记录记账数据",Toast.LENGTH_LONG).show();
            customBarChart2.removeView(dataview);

        }
    }
    //spinner的简化代码
    private void sp_jian(){
        StringBuffer sb=new StringBuffer();
        String syear=sp_year.getSelectedItem().toString();
        String smonth=sp_month.getSelectedItem().toString();
        String sday=sp_date.getSelectedItem().toString();

       sb.append(syear).append("年").append(smonth).append("月").append(sday).append("日");
        check_date=sb.toString();
        String judge=db.judgeproper(check_acc_num,check_date);
        if (judge.equals("yes")) {
            linearLayout.setVisibility(View.VISIBLE);
            linearLayout1.setVisibility(View.VISIBLE);
            linearLayout2.setVisibility(View.VISIBLE);
            check_totalin.setVisibility(View.VISIBLE);
            check_totalout.setVisibility(View.VISIBLE);
            lv_message.setVisibility(View.VISIBLE);
            check_totalin.setText("总收入：" + String.valueOf(db.inoutdata(check_acc_num, check_date, "收入"))+"元");
            check_totalout.setText("总支出： "+String.valueOf(db.inoutdata(check_acc_num,check_date,"支出"))+"元 ");
            Barchar();
            paintzhe();
            paintzhe2();
        }
        else
        {
            Toast.makeText(CheckActivity.this,"当天没有记录数据",Toast.LENGTH_LONG).show();
            linearLayout.setVisibility(View.INVISIBLE);
            linearLayout1.setVisibility(View.INVISIBLE);
            linearLayout2.setVisibility(View.INVISIBLE);
            check_totalin.setVisibility(View.INVISIBLE);
            check_totalout.setVisibility(View.INVISIBLE);
            lv_message.setVisibility(View.INVISIBLE);
            customBarChart2.removeView(dataview);
            paintzhe();
            paintzhe2();
        }
    }
    //描绘单折线图  收入
    public void paintzhe(){
        //设置图表的描述
        lineChart2.setDescription("当天的收入情况");
        //设置x轴的数据
        int numX = 0;
        String[] xdata;
        ArrayList<String> xva=new ArrayList<String>();
        //y轴的数据
        float[] ydata;
        String s_result=db.judgeproperid(check_acc_num,check_date,"收入");
        int tt_id=0;
        if (s_result.equals("yes")){
            numX=db.searchsizeindata(check_acc_num,check_date,"收入");
          xdata=new String[numX];
          xdata=db.searchcheckx(check_acc_num,check_date,"收入");
          for (int i=0;i<numX;i++) {
              xva.add(xdata[i]);
          }
          ydata=new float[numX];
          ydata=db.searchchecky(check_acc_num,check_date,"收入");
        }
        else{
            numX=24;
            for (int j=0;j<24;j++)
            xva.add((j) + ":00");
            ydata=new float[numX];
            for (int k=0;k<24;k++)
                ydata[k]=0;
        }

        lineData=LineChartManager.initSingleLineChart(this,lineChart1,xva,numX,ydata,1);

        LineChartManager.initDataStyle(lineChart2, lineData, this);
    }
    //描绘单折线图  支出
    public void paintzhe2(){
        //设置图表的描述
        lineChart3.setDescription("当天的支出情况");
        //设置x轴的数据
        int numX = 0;
        String[] xdata;
        ArrayList<String> xva=new ArrayList<String>();
        //y轴的数据
        float[] ydata;
        String s_result=db.judgeproperid(check_acc_num,check_date,"支出");
        int tt_id=0;
        if (s_result.equals("yes")){
            numX=db.searchsizeindata(check_acc_num,check_date,"支出");
            xdata=new String[numX];
            xdata=db.searchcheckx(check_acc_num,check_date,"支出");
            for (int i=0;i<numX;i++) {
                xva.add(xdata[i]);
            }
            ydata=new float[numX];
            ydata=db.searchchecky(check_acc_num,check_date,"支出");
        }
        else{
            numX=24;
            for (int j=0;j<24;j++)
                xva.add((j) + ":00");
            ydata=new float[numX];
            for (int k=0;k<24;k++)
                ydata[k]=0;
        }

        lineData=LineChartManager.initSingleLineChart(this,lineChart1,xva,numX,ydata,2);

        LineChartManager.initDataStyle(lineChart3, lineData, this);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if ((Intent.FLAG_ACTIVITY_CLEAR_TOP & intent.getFlags()) != 0) {
            finish();
            System.exit(0);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode== KeyEvent.KEYCODE_BACK){
            exit();
            return false;
        }
        return super.onKeyDown(keyCode,event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(),
                    "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        }
        else{
            Intent intent = new Intent();
            intent.setAction(exitsystem.SYSTEM_EXIT);
            sendBroadcast(intent);
        }
    }
}
