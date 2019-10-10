package com.example.calcularion.Userdata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calcularion.MYsqldata.DBManager;
import com.example.calcularion.R;
import com.example.calcularion.exitsystem;

public class EditszActivity extends exitsystem {

    //界面控件
    TextView jizhang_back;
    TextView jizhang_time;
    TextView jizhang_type;
    EditText jizhang_jine,jizhang_remark;
    Button jizhang_sure;
    private DBManager db;
    private long exitTime=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editsz);
        db=new DBManager(this);
        inirView();
        function_control();
    }
    private void inirView(){
        jizhang_back=(TextView)findViewById(R.id.jizhang_back);
        jizhang_time=(TextView)findViewById(R.id.jizhang_time);
        jizhang_type=(TextView)findViewById(R.id.jizhang_type);
        jizhang_jine=(EditText)findViewById(R.id.jizhang_jine);
        jizhang_remark=(EditText)findViewById(R.id.jizhang_remark);
        jizhang_sure=(Button)findViewById(R.id.jizhang_sure);

        jizhang_jine.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);//只能输入小数点 和数字
        Bundle bundle=getIntent().getBundleExtra("bundle");
        StringBuffer sb=new StringBuffer();
        sb.append(bundle.getString("date")).append(bundle.getString("timedetail"));
        String datetime=sb.toString();
        jizhang_time.setText(datetime);
        String type=bundle.getString("type");
        jizhang_type.setText(type);
        jizhang_jine.setText(String.valueOf(bundle.getDouble("money")));
        jizhang_remark.setText(bundle.getString("remark"));



    }
    private void function_control(){
        jizhang_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=getIntent().getBundleExtra("bundle");
                Intent intent=new Intent(EditszActivity.this,CheckActivity.class);
                intent.putExtra("bundle",bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        jizhang_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double money=Double.valueOf(jizhang_jine.getText().toString());
                String remark=jizhang_remark.getText().toString();
                int id=getIntent().getBundleExtra("bundle").getInt("id");
                boolean result=db.updatejizhang(id,money,remark);//更新回到 分表

                //将数据更新回到总表
                String ss=getIntent().getBundleExtra("bundle").getString("timedetail");
                String[] sstr=ss.split(":");
                int flag=judgetime(Integer.valueOf(sstr[0]));
                long acc_num=getIntent().getBundleExtra("bundle").getLong("acc_num");
                String date=getIntent().getBundleExtra("bundle").getString("date");
                double zhong=getIntent().getBundleExtra("bundle").getDouble("money");
                double newdate=Double.valueOf(jizhang_jine.getText().toString());
                String type=getIntent().getBundleExtra("bundle").getString("type");
                boolean rresult=db.updatetotaldatatwo(flag,acc_num,date,type,zhong,newdate);
                if (result && rresult){
                    Toast.makeText(EditszActivity.this,"更新成功",Toast.LENGTH_LONG).show();
                    Bundle b=new Bundle();
                    b.putLong("acc_num",getIntent().getBundleExtra("bundle").getLong("acc_num"));
                    Intent intent=new Intent(EditszActivity.this,CheckActivity.class);
                    intent.putExtra("bundle",b);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }
                else{
                    Toast.makeText(EditszActivity.this,"更新失败",Toast.LENGTH_LONG).show();
                    Bundle b=new Bundle();
                    b.putLong("acc_num",getIntent().getBundleExtra("bundle").getLong("acc_num"));
                    Intent intent=new Intent(EditszActivity.this,CheckActivity.class);
                    intent.putExtra("bundle",b);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });
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
