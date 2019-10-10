package com.example.calcularion.Userdata;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calcularion.Configure.CustomBarChart;
import com.example.calcularion.Configure.LineChartManager;
import com.example.calcularion.Configure.MyImageLoader;
import com.example.calcularion.MYsqldata.DBManager;
import com.example.calcularion.R;
import com.example.calcularion.exitsystem;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeActivity extends exitsystem implements View.OnClickListener {
    //界面控件
    private Banner mbanner;
    private Button bt_count,bt_check,bt_cal,bt_user;
    private TextView tv_count,tv_check,tv_cal,tv_user;
    private TextView home_date;
    private LinearLayout customBarChart1;
    //轮播图的数据
    private MyImageLoader myImageLoader;
    private ArrayList<Integer> photos;

    private long accnum;
    private Calendar calendar;
    private String nowdate;
    private DBManager db;

    private LineChart lineChart1,lineChart2;
    private LineData lineData;
    private long exitTime=0;
    String ym;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Bundle bundle=getIntent().getBundleExtra("bundle");
        accnum=bundle.getLong("acc_num");
        initData();
        initView();
        Barchar();
        paintzhe();
    }
    //界面初始化
    private void initView(){
        mbanner=(Banner)findViewById(R.id.banner);
        //设置轮播的样式
        mbanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//CIRCLE_INDICATOR_TITLE
        //设置图片加载器
        mbanner.setImageLoader(myImageLoader);
        //设置轮播的动画效果,里面有很多种特效,可以都看看效果。
        mbanner.setBannerAnimation(Transformer.ZoomOutSlide);//ZoomOutSlide
        //设置轮播间隔时间
        mbanner.setDelayTime(3000);
        //设置是否为自动轮播，默认是true
        mbanner.isAutoPlay(true);
        //设置指示器的位置，小点点，居中显示
        mbanner.setIndicatorGravity(BannerConfig.CENTER);
        //设置图片加载地址
        mbanner.setImages(photos)
                //开始调用的方法，启动轮播图。
                .start();

        bt_count=(Button)findViewById(R.id.bt_count);
        bt_check=(Button)findViewById(R.id.bt_check);
        bt_cal=(Button)findViewById(R.id.bt_cal);
        bt_user=(Button)findViewById(R.id.bt_user);
        tv_count=(TextView)findViewById(R.id.tv_count);
        tv_check=(TextView)findViewById(R.id.tv_check);
        tv_cal=(TextView)findViewById(R.id.tv_cal);
        tv_user=(TextView)findViewById(R.id.tv_user);
        home_date=(TextView)findViewById(R.id.home_date);
        customBarChart1=(LinearLayout)findViewById(R.id.customBarChart1) ;
        //控件的监听
        bt_count.setOnClickListener(this);
        bt_check.setOnClickListener(this);
        bt_cal.setOnClickListener(this);
        bt_user.setOnClickListener(this);
        tv_count.setOnClickListener(this);
        tv_check.setOnClickListener(this);
        tv_cal.setOnClickListener(this);
        tv_user.setOnClickListener(this);

        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;
        int day=calendar.get(Calendar.DAY_OF_MONTH);

        StringBuffer sb=new StringBuffer();
        sb.append(year).append("年").append(month).append("月").append(day).append("日");
        nowdate=sb.toString();
        home_date.setText(nowdate);

        lineChart2 = (LineChart) findViewById(R.id.line_chart);

        StringBuffer sb1=new StringBuffer();
        sb1.append(year).append("年").append(month);
         ym=sb1.toString();

        double Zhichu=db.searchzongw(accnum,ym,"支出");
        double Shouru=db.searchzongw(accnum,ym,"收入");

        new AlertDialog.Builder(HomeActivity.this)
                .setTitle("温馨提示：")
                .setMessage("主人，根据你的记账情况总结如下："+"\n"+"当月你的总支出为 "+String.valueOf(Zhichu)+"元"+"\n"+"总收入为 "+String.valueOf(Shouru)+"元")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }
    //数据初始化
    private void initData(){
        db=new DBManager(this);
        calendar=Calendar.getInstance();
        myImageLoader=new MyImageLoader();
        //将轮播的图片放在photos 那里
        photos=new ArrayList<Integer>();
        photos.add(R.mipmap.photo2);
        photos.add(R.mipmap.photo1);
        photos.add(R.mipmap.photo3);
        photos.add(R.mipmap.photo4);
        photos.add(R.mipmap.photo5);
        photos.add(R.mipmap.photo6);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_count:
            case R.id.tv_count:
                Bundle bundle=new Bundle();
                bundle.putLong("acc_num",accnum);
                Intent intent1=new Intent(HomeActivity.this,CountActivity.class);
                intent1.putExtra("bundle",bundle);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                break;
            case R.id.bt_check:
            case  R.id.tv_check:
                Bundle bundle1=getIntent().getBundleExtra("bundle");
                Intent intent2=new Intent(HomeActivity.this,CheckActivity.class);
                intent2.putExtra("bundle",bundle1);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
                break;

            case R.id.bt_cal:
            case R.id.tv_cal:
                Bundle bundle2=getIntent().getBundleExtra("bundle");
                Intent intent3=new Intent(HomeActivity.this,CalActivity.class);
                intent3.putExtra("bundle",bundle2);
                intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent3);
                break;

            case R.id.bt_user:
            case R.id.tv_user:
                Bundle bundle4=new Bundle();
                bundle4.putLong("acc_num",accnum);
                Intent intent4=new Intent(HomeActivity.this,UserActivity.class);
                intent4.putExtra("bundle",bundle4);
                intent4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent4);
                break;
            default:
                break;
        }
    }
    //描绘双柱状图
    private void Barchar(){
        String[] xLabel = {"","凌晨", "上午", "中午", "晚上"};
        String[] yLabel = {"0", "1000", "2000", "3000", "4000", "5000", "6000", "7000", "8000", "9000"};
        String result=db.judgeproper(accnum,nowdate);
        if (result.equals("yes")){
            int id1=db.searchproperid("收入",accnum,nowdate);
            int id2=db.searchproperid("支出",accnum,nowdate);
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
            customBarChart1.addView(new CustomBarChart(this, xLabel, yLabel, data, color));
        }
        else{
            Toast.makeText(HomeActivity.this,"今天还没记录记账数据",Toast.LENGTH_LONG).show();
        }
    }
    //描绘双折线图
    public void paintzhe(){
        //设置图表的描述
        lineChart2.setDescription("近七天的记账情况");
        //设置x轴的数据
        int numX = 7;
        String s_result=db.judgeproperid(accnum,nowdate,"收入");
        int tt_id=0;
        if (s_result.equals("yes")){
             tt_id=db.searchproperid("收入",accnum,nowdate);
        }
        else{
            int t_id=db.searchid();
            db.adddataintotal(t_id,accnum,nowdate,"收入",0,0,0,0,0);
             tt_id=db.searchproperid("收入",accnum,nowdate);
        }
        //设置y轴的数据
        float[] sdata1=new float[7];
        float[] datas1 = db.searchdatedataone(tt_id,accnum,"收入");//数据  收入

        int size=db.searchcountone(tt_id,accnum,"收入");
        int i=0;
        int j=0;
        if (size<7){
            for (i=0;i<7;i++){
                if (i<7-size){
                    sdata1[i]=0;
                }
                else
                {
                    sdata1[i]=datas1[j];
                    j++;
                }
            }
        }
        else{
            sdata1=datas1;
        }

        String z_result=db.judgeproperid(accnum,nowdate,"支出");
        int zt_id=0;
        if (z_result.equals("yes")){
            zt_id=db.searchproperid("支出",accnum,nowdate);
            System.out.println("zt_id"+zt_id);
            System.out.println("总支出="+db.inoutdata(accnum,nowdate,"支出"));
        }
        else{
            int t_id=db.searchid();
            db.adddataintotal(t_id,accnum,nowdate,"支出",0,0,0,0,0);
            zt_id=db.searchproperid("支出",accnum,nowdate);
        }
        float[] zdata1=new float[7];
        float[] datas2 =db.searchdatedataone(zt_id,accnum,"支出") ;//数据  支出
        int zsize=db.searchcountone(tt_id,accnum,"收入");
        int w=0;
        int z=0;
        if (zsize<7){
            for (w=0;w<7;w++){
                if (w<7-zsize){
                  zdata1[w]=0;
                }
                else{
                    zdata1[w]=datas2[z];
                    z++;
                }
            }
        }
        else{
            zdata1=datas2;
        }

        //设置折线的名称
        LineChartManager.setLineName("支出");
        //设置第二条折线y轴的数据
        LineChartManager.setLineName1("收入");
        ArrayList<String> xva=new ArrayList<String>();

        xva.add("今天");
        xva.add("昨天");
        xva.add("前天");
        xva.add("大前天");
        xva.add("前三天");
        xva.add("前四天");
        xva.add("前五天");

        float[] test1=new float[7];
        int l1=6;
        for (int k1=0;k1<7;k1++){
            test1[k1]=sdata1[l1];
            l1--;
        }
       float[] test2=new float[7];
        int l2=6;
        for (int k2=0;k2<7;k2++){
            test2[k2]=zdata1[l2];
            l2--;
        }
        //创建两条折线的图表
        lineData = LineChartManager.initDoubleLineChart(this, lineChart1, xva,numX, test2, test1);
        LineChartManager.initDataStyle(lineChart2, lineData, this);
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
