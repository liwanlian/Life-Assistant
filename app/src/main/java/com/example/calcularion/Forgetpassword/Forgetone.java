package com.example.calcularion.Forgetpassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calcularion.Configure.PhoneEditText;
import com.example.calcularion.MYsqldata.DBManager;
import com.example.calcularion.MainActivity;
import com.example.calcularion.R;
import com.example.calcularion.exitsystem;

public class Forgetone extends exitsystem {

    //界面控件
    EditText forgetone_username;
    PhoneEditText forgetone_acc;
    Button bt_forgetone;
    DBManager dbManager;
    TextView tv_backone;
    private long exitTime=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetone);
        init();
        dbManager=new DBManager(this);
        bt_forgetone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username;
                String accnum;
                long acc;
                username=forgetone_username.getText().toString();
                accnum=forgetone_acc.getText().toString().replaceAll(" " ,"");
                acc=Long.valueOf(accnum);
                int size=dbManager.countid();
                int id=dbManager.searchaccandname(username,acc);
                if (id<size){
                    Bundle bundle=new Bundle();
                    bundle.putInt("id",id);
                    bundle.putLong("acc_num",acc);
                    Intent intent=new Intent(Forgetone.this,Forgettwo.class);
                    intent.putExtra("bundle",bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(Forgetone.this,"用户名或者账号输入有误",Toast.LENGTH_LONG).show();
                }
            }
        });
        tv_backone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Forgetone.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    //控件初始化
    private void init(){
        forgetone_username=(EditText)findViewById(R.id.forgetone_username);
        forgetone_acc=(PhoneEditText)findViewById(R.id.forgetone_acc);
        bt_forgetone=(Button)findViewById(R.id.bt_forgetone);
        tv_backone=(TextView)findViewById(R.id.tv_backone);
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
