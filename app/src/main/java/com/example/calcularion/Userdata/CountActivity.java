package com.example.calcularion.Userdata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import java.util.Calendar;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calcularion.MYsqldata.DBManager;
import com.example.calcularion.R;
import com.example.calcularion.exitsystem;

public class CountActivity extends exitsystem implements View.OnClickListener{

    //界面控件
    TextView count_back;
    RadioGroup count_type;
    RadioButton rb_out,rb_in;
    EditText et_count,count_remark;
    Button count_sure;
    //数据库
    private DBManager db;
    //填入数据库的用户表的数据
    double money;//金额
    int type=0;//0 支出  1 收入
    String date;//记账的日期 年 月 日
    String timedetail;//记账的详细时间点
    String remark;//备注的信息
    long accnum;
    String stype="支出";
    private Calendar calendar;
    private long exitTime=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        db=new DBManager(this);
        Bundle bundle=getIntent().getBundleExtra("bundle");
        accnum=bundle.getLong("acc_num");
        initView();
    }
    //控件初始化
    private void initView(){
        count_back=(TextView)findViewById(R.id.count_back);
        count_type=(RadioGroup)findViewById(R.id.count_type);
        rb_out=(RadioButton)findViewById(R.id.rb_out);
        rb_in=(RadioButton)findViewById(R.id.rb_in);
        et_count=(EditText)findViewById(R.id.et_count);
        count_remark=(EditText)findViewById(R.id.count_remark);
        count_sure=(Button)findViewById(R.id.count_sure);

        //对于数据金额的edittext只能数据数字和小数点
        et_count.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        count_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i==rb_out.getId())
                    type=0;
                else if (i==rb_in.getId())
                    type=1;
            }
        });
        count_back.setOnClickListener(this);
        count_sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.count_back:
                Bundle bundle=new Bundle();
               bundle.putLong("acc_num",accnum);
                Intent intent=new Intent(CountActivity.this,HomeActivity.class);
                intent.putExtra("bundle",bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.count_sure:
                int id=db.countuid();

                money=Double.valueOf(et_count.getText().toString());
                remark=count_remark.getText().toString();
                calendar=Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH)+1;
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                StringBuffer sb_date=new StringBuffer();
                sb_date.append(year).append("年").append(month).append("月").append(day).append("日");
                date=sb_date.toString();
                int hour=calendar.get(Calendar.HOUR_OF_DAY);
                int minute=calendar.get(Calendar.MINUTE);
                int seconds=calendar.get(Calendar.SECOND);
                StringBuffer sb_timeing=new StringBuffer();
                sb_timeing.append(hour).append(":").append(minute).append(":").append(seconds);
                timedetail=sb_timeing.toString();
                if (type==0)
                    stype="支出";
                else
                    stype="收入";
            boolean result=   db.addusrData(id,accnum,stype,money,date,timedetail,remark);//添加数据到用户表
                int flag=judgetime(hour);
                String t_result=db.judgeproperid(accnum,date,stype);
                System.out.println("t_result"+t_result);
                boolean b_result=false;
                if (t_result.equals("no")){
                    int t_id=db.searchid();
                    db.adddataintotal(t_id,accnum,date,stype,0,0,0,0,0);
                    int tt_id=db.searchproperid(stype,accnum,date);
                    System.out.println("flag="+flag+"tt_id"+tt_id+"money"+money);
                    b_result= db.updatetotaldata(flag,tt_id,money);
                }
                else{
                    int tt_id=db.searchproperid(stype,accnum,date);
                    System.out.println("flag="+flag+"tt_id"+tt_id+"money"+money);
                    b_result= db.updatetotaldata(flag,tt_id,money);
                }
                Bundle bundle1=getIntent().getBundleExtra("bundle");
                if (result && b_result)
                {
                    Toast.makeText(CountActivity.this,"记录成功",Toast.LENGTH_LONG).show();
                    double jizhang=db.inoutdata(accnum,date,stype);
                    System.out.println("总账："+jizhang);
                    Intent intent1=new Intent(CountActivity.this,HomeActivity.class);
                    intent1.putExtra("bundle",bundle1);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                }
                else
                {
                    Toast.makeText(CountActivity.this,"记录失败",Toast.LENGTH_LONG).show();
                    Intent intent1=new Intent(CountActivity.this,HomeActivity.class);
                    intent1.putExtra("bundle",bundle1);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                }
                break;
            default:
                break;
        }
    }
    //判断当前记录的时间段 凌晨 1，上午 2，中午 3，晚上 4
    private int judgetime(int hour){
        int flag=0;
        if (hour>=0&&hour<6)
            flag=1;
        else if (hour>=6 && hour<12)
            flag=2;
        else if (hour>=12 && hour<18)
            flag=3;
        else
            flag=4;
        return flag;
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
