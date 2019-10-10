package com.example.calcularion.UserControl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calcularion.Configure.PhoneEditText;
import com.example.calcularion.Configure.QR_code;
import com.example.calcularion.Forgetpassword.Forgetone;
import com.example.calcularion.Forgetpassword.Forgettwo;
import com.example.calcularion.MYsqldata.DBManager;
import com.example.calcularion.R;
import com.example.calcularion.Userdata.UserActivity;
import com.example.calcularion.exitsystem;

public class Edit_accnum extends exitsystem implements View.OnClickListener {

    //界面控件
    TextView accnum_back;
    EditText accnum_username,accnum_qrcode;
    PhoneEditText accnum_acc;
    ImageView accnum_ivcode;
    Button accnum_next;
    String getcode;//二维码

    private DBManager db;
    private long accnum;
    private long exitTime=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_accnum);
        db=new DBManager(this);
        Bundle bundle=getIntent().getBundleExtra("bundle");
        accnum=bundle.getLong("acc_num");
        initView();
    }
    //界面初始化
    private void initView(){
        accnum_back=(TextView)findViewById(R.id.accnum_back);
        accnum_username=(EditText)findViewById(R.id.accnum_username);
        accnum_qrcode=(EditText)findViewById(R.id.accnum_qrcode);
        accnum_ivcode=(ImageView)findViewById(R.id.accnum_ivcode);
        accnum_next=(Button)findViewById(R.id.accnum_next);
        accnum_acc=(PhoneEditText) findViewById(R.id.accnum_acc);

        accnum_ivcode.setImageBitmap(QR_code.getInstance().createBitmap());
        getcode=QR_code.getInstance().getCode().toLowerCase();

        accnum_back.setOnClickListener(this);
        accnum_ivcode.setOnClickListener(this);
        accnum_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.accnum_back:
                Bundle bundle1=getIntent().getBundleExtra("bundle");
                Intent intent=new Intent(Edit_accnum.this, UserActivity.class);
                intent.putExtra("bundle",bundle1);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.accnum_ivcode:
                accnum_ivcode.setImageBitmap(QR_code.getInstance().createBitmap());
                getcode=QR_code.getInstance().getCode().toLowerCase();
                break;
            case R.id.accnum_next:
                String qrcode=accnum_qrcode.getText().toString();
                String zhanghao=String.valueOf(getIntent().getBundleExtra("bundle").getLong("acc_num"));
                Toast.makeText(Edit_accnum.this,zhanghao,Toast.LENGTH_LONG).show();
                if (qrcode.equals(getcode)){
                    if (Long.valueOf(accnum_acc.getText().toString().replaceAll(" " ,"") )==accnum){
                        int size=db.countid();
                        int id=db.searchaccandname(accnum_username.getText().toString(),accnum);
                        if (id<size){
                            Bundle bundle=new Bundle();
                            bundle.putInt("id",id);
                            bundle.putLong("acc_num",accnum);
                            Intent intent1=new Intent(Edit_accnum.this,Edit_accnumlast.class);
                            intent1.putExtra("bundle",bundle);
                            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent1);
                        }
                        else{
                            Toast.makeText(Edit_accnum.this,"用户名与账号不匹配",Toast.LENGTH_LONG).show();
                            accnum_acc.setText("");
                            accnum_qrcode.setText("");
                            accnum_username.setText("");
                            accnum_ivcode.setImageBitmap(QR_code.getInstance().createBitmap());
                            getcode=QR_code.getInstance().getCode().toLowerCase();
                        }
                    }
                    else{
                        Toast.makeText(Edit_accnum.this,"输入账号并非当前登录的账号",Toast.LENGTH_LONG).show();
                        accnum_acc.setText("");
                        accnum_qrcode.setText("");
                        accnum_username.setText("");
                        accnum_ivcode.setImageBitmap(QR_code.getInstance().createBitmap());
                        getcode=QR_code.getInstance().getCode().toLowerCase();
                    }
                }
                else{
                    Toast.makeText(Edit_accnum.this,"输入的验证码不正确",Toast.LENGTH_LONG).show();
                    accnum_acc.setText("");
                    accnum_qrcode.setText("");
                    accnum_username.setText("");
                    accnum_ivcode.setImageBitmap(QR_code.getInstance().createBitmap());
                    getcode=QR_code.getInstance().getCode().toLowerCase();
                }
                break;
            default:
                break;
        }
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
